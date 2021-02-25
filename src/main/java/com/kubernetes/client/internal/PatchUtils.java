package com.kubernetes.client.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kubernetes.api.model.ObjectMeta;
import com.kubernetes.client.internal.patchmixins.ObjectMetaMixIn;

public class PatchUtils {
    private PatchUtils() {
    }

    private static class SingletonHolder {
        public static final ObjectMapper patchMapper;

        static {
            patchMapper = new ObjectMapper();
            patchMapper.addMixIn(ObjectMeta.class, ObjectMetaMixIn.class);
            patchMapper.setConfig(patchMapper.getSerializationConfig().without(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS));
        }
    }

    public static void addMixInToMapper(Class<?> target, Class<?> mixInSource) {
        SingletonHolder.patchMapper.addMixIn(target, mixInSource);
    }

    public static ObjectMapper patchMapper() {
        return SingletonHolder.patchMapper;
    }
}

