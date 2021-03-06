package com.kubernetes.client.internal;

import com.kubernetes.client.KubernetesClientException;
import com.kubernetes.client.util.Utils;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import okio.ByteString;

/**
 * 证书
 */
public class CertUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CertUtils.class);
    public static String TRUST_STORE_SYSTEM_PROPERTY = "javax.net.ssl.trustStore";
    public static String TRUST_STORE_PASSWORD_SYSTEM_PROPERTY = "javax.net.ssl.trustStorePassword";
    public static String KEY_STORE_SYSTEM_PROPERTY = "javax.net.ssl.keyStore";
    public static String KEY_STORE_PASSWORD_SYSTEM_PROPERTY = "javax.net.ssl.keyStorePassword";


    public static InputStream getInputStreamFromDataOrFile(String data, String file) throws IOException {
        if (data != null) {
            byte[] bytes = null;
            //base64解码
            ByteString decoded = ByteString.decodeBase64(data);
            if (decoded != null) {
                bytes = decoded.toByteArray();
            } else {
                bytes = data.getBytes();
            }

            return new ByteArrayInputStream(bytes);
        }
        if (file != null) {
            return new ByteArrayInputStream(new String(Files.readAllBytes(Paths.get(file))).trim().getBytes());
        }
        return null;
    }

    /**
     * keystore 证书
     * 更具 config caCertData/caCertFile
     *
     * @param caCertData
     * @param caCertFile
     * @param trustStoreFile
     * @param trustStorePassphrase
     * @return
     * @throws IOException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     */
    public static KeyStore createTrustStore(String caCertData,
                                            String caCertFile,
                                            String trustStoreFile,
                                            String trustStorePassphrase) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        try (InputStream pemInputStream = getInputStreamFromDataOrFile(caCertData, caCertFile)) {
            return createTrustStore(pemInputStream, trustStoreFile, getTrustStorePassphrase(trustStorePassphrase));
        }
    }

    private static char[] getTrustStorePassphrase(String trustStorePassphrase) {
        if (Utils.isNullOrEmpty(trustStorePassphrase)) {
            return System.getProperty(TRUST_STORE_PASSWORD_SYSTEM_PROPERTY, "changeit").toCharArray();
        }
        return trustStorePassphrase.toCharArray();
    }

    //keyStore
    public static KeyStore createTrustStore(InputStream pemInputStream, String trustStoreFile, char[] trustStorePassphrase) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        KeyStore trustStore = KeyStore.getInstance("JKS");

        if (Utils.isNotNullOrEmpty(trustStoreFile)) {
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassphrase);
        } else {
            loadDefaultTrustStoreFile(trustStore, trustStorePassphrase);
        }

        while (pemInputStream.available() > 0) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(pemInputStream);
            String alias = cert.getSubjectX500Principal().getName() + "_" + cert.getSerialNumber().toString(16);
            trustStore.setCertificateEntry(alias, cert);
        }
        return trustStore;
    }


    /**
     * keyStore
     *
     * @param clientCertData
     * @param clientCertFile
     * @param clientKeyData
     * @param clientKeyFile
     * @param clientKeyAlgo
     * @param clientKeyPassphrase
     * @param keyStoreFile
     * @param keyStorePassphrase
     * @return
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws KeyStoreException
     */
    public static KeyStore createKeyStore(String clientCertData, String clientCertFile, String clientKeyData,
                                          String clientKeyFile, String clientKeyAlgo, String clientKeyPassphrase, String keyStoreFile,
                                          String keyStorePassphrase)
            throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException {
        try (InputStream certInputStream = getInputStreamFromDataOrFile(clientCertData, clientCertFile);
             InputStream keyInputStream = getInputStreamFromDataOrFile(clientKeyData, clientKeyFile)) {
            return createKeyStore(certInputStream, keyInputStream, clientKeyAlgo, clientKeyPassphrase.toCharArray(),
                    keyStoreFile, getKeyStorePassphrase(keyStorePassphrase));
        }
    }

    public static KeyStore createKeyStore(InputStream certInputStream, InputStream keyInputStream,
                                          String clientKeyAlgo, char[] clientKeyPassphrase,
                                          String keyStoreFile, char[] keyStorePassphrase) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X509");
        Collection<? extends Certificate> certificates = certFactory.generateCertificates(certInputStream);
        PrivateKey privateKey = loadKey(keyInputStream, clientKeyAlgo);

        KeyStore keyStore = KeyStore.getInstance("JKS");
        if (Utils.isNotNullOrEmpty(keyStoreFile)) {
            keyStore.load(new FileInputStream(keyStoreFile), keyStorePassphrase);
        } else {
            loadDefaultKeyStoreFile(keyStore, keyStorePassphrase);
        }

        String alias = certificates.stream().map(cert -> ((X509Certificate) cert).getIssuerX500Principal().getName()).collect(Collectors.joining("_"));
        keyStore.setKeyEntry(alias, privateKey, clientKeyPassphrase, certificates.toArray(new Certificate[0]));

        return keyStore;
    }

    private static PrivateKey loadKey(InputStream keyInputStream, String clientKeyAlgo) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (clientKeyAlgo == null) {
            clientKeyAlgo = "RSA"; // by default let's assume it's RSA
        }
        if (clientKeyAlgo.equals("EC")) {
            return handleECKey(keyInputStream);
        } else if (clientKeyAlgo.equals("RSA")) {
            return handleOtherKeys(keyInputStream, clientKeyAlgo);
        }

        throw new InvalidKeySpecException("Unknown type of PKCS8 Private Key, tried RSA and ECDSA");
    }

    private static PrivateKey handleECKey(InputStream keyInputStream) throws IOException {
        // Let's wrap the code to a callable inner class to avoid NoClassDef when loading this class.
        try {
            return new Callable<PrivateKey>() {
                @Override
                public PrivateKey call() {
                    try {
                        if (Security.getProvider("BC") == null) {
                            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                        }
                        PEMKeyPair keys = (PEMKeyPair) new PEMParser(new InputStreamReader(keyInputStream)).readObject();
                        return new
                                JcaPEMKeyConverter().
                                getKeyPair(keys).
                                getPrivate();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    return null;
                }
            }.call();
        } catch (NoClassDefFoundError e) {
            throw new KubernetesClientException("JcaPEMKeyConverter is provided by BouncyCastle, an optional dependency. To use support for EC Keys you must explicitly add this dependency to classpath.");
        }
    }

    private static PrivateKey handleOtherKeys(InputStream keyInputStream, String clientKeyAlgo) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = decodePem(keyInputStream);
        KeyFactory keyFactory = KeyFactory.getInstance(clientKeyAlgo);
        try {
            // First let's try PKCS8
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (InvalidKeySpecException e) {
            // Otherwise try PKCS8
            RSAPrivateCrtKeySpec keySpec = PKCS1Util.decodePKCS1(keyBytes);
            return keyFactory.generatePrivate(keySpec);
        }
    }

    /**
     * load 系统默认 storeFile
     *
     * @param keyStore
     * @param trustStorePassphrase
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void loadDefaultTrustStoreFile(KeyStore keyStore, char[] trustStorePassphrase)
            throws CertificateException, NoSuchAlgorithmException, IOException {

        File trustStoreFile = getDefaultTrustStoreFile();

        if (!loadDefaultStoreFile(keyStore, trustStoreFile, trustStorePassphrase)) {
            keyStore.load(null);
        }
    }

    /**
     * 获取当前系统 默认 StoreFile
     *
     * @return
     */
    private static File getDefaultTrustStoreFile() {
        String securityDirectory =
                System.getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator;

        String trustStorePath = System.getProperty(TRUST_STORE_SYSTEM_PROPERTY);
        if (Utils.isNotNullOrEmpty(trustStorePath)) {
            return new File(trustStorePath);
        }

        File jssecacertsFile = new File(securityDirectory + "jssecacerts");
        if (jssecacertsFile.exists() && jssecacertsFile.isFile()) {
            return jssecacertsFile;
        }

        return new File(securityDirectory + "cacerts");
    }

    private static void loadDefaultKeyStoreFile(KeyStore keyStore, char[] keyStorePassphrase)
            throws CertificateException, NoSuchAlgorithmException, IOException {

        String keyStorePath = System.getProperty(KEY_STORE_SYSTEM_PROPERTY);
        if (Utils.isNotNullOrEmpty(keyStorePath)) {
            File keyStoreFile = new File(keyStorePath);
            if (loadDefaultStoreFile(keyStore, keyStoreFile, keyStorePassphrase)) {
                return;
            }
        }

        keyStore.load(null);
    }

    /**
     * keyStore 加载默认 storeFile 和 passphrase
     *
     * @param keyStore
     * @param fileToLoad
     * @param passphrase
     * @return
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static boolean loadDefaultStoreFile(KeyStore keyStore, File fileToLoad, char[] passphrase)
            throws CertificateException, NoSuchAlgorithmException, IOException {

        String notLoadedMessage = "There is a problem with reading default keystore/truststore file %s with the passphrase %s "
                + "- the file won't be loaded. The reason is: %s";

        if (fileToLoad.exists() && fileToLoad.isFile() && fileToLoad.length() > 0) {
            try {
                keyStore.load(new FileInputStream(fileToLoad), passphrase);
                return true;
            } catch (Exception e) {
                String passphraseToPrint = passphrase != null ? String.valueOf(passphrase) : null;
                LOG.info(String.format(notLoadedMessage, fileToLoad, passphraseToPrint, e.getMessage()));
            }
        }
        return false;
    }


    private static char[] getKeyStorePassphrase(String keyStorePassphrase) {
        if (Utils.isNullOrEmpty(keyStorePassphrase)) {
            return System.getProperty(KEY_STORE_PASSWORD_SYSTEM_PROPERTY, "changeit").toCharArray();
        }
        return keyStorePassphrase.toCharArray();
    }

    private static byte[] decodePem(InputStream keyInputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(keyInputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("-----BEGIN ")) {
                    return readBytes(reader, line.trim().replace("BEGIN", "END"));
                }
            }
            throw new IOException("PEM is invalid: no begin marker");
        } finally {
            reader.close();
        }
    }

    private static byte[] readBytes(BufferedReader reader, String endMarker) throws IOException {
        String line;
        StringBuffer buf = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            if (line.indexOf(endMarker) != -1) {
                return ByteString.decodeBase64(buf.toString()).toByteArray();
            }
            buf.append(line.trim());
        }
        throw new IOException("PEM is invalid : No end marker");
    }

}
