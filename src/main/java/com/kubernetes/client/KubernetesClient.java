package com.kubernetes.client;

import com.kubernetes.api.model.HasMetadata;
import com.kubernetes.api.model.KubernetesResourceList;
import com.kubernetes.client.dsl.ApiextensionsAPIGroupDSL;
import com.kubernetes.client.dsl.CertificatesAPIGroupDSL;
import com.kubernetes.client.dsl.MixedOperation;
import com.kubernetes.client.dsl.Resource;

import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * k8s main client
 */
public interface KubernetesClient extends Client {

    /**
     * api extensions api group /v1 /v1beta1
     *
     * @return
     */
    ApiextensionsAPIGroupDSL apiextensions();

    /**
     * api 证书 api group
     * /v1 /v1beta1
     *
     * @return
     */
    CertificatesAPIGroupDSL certificates();

    /**
     * 自定义资源
     *
     * @param resourceType
     * @param <T>
     * @return
     */
    <T extends CustomResource> MixedOperation<T, KubernetesResourceList<T>, Resource<T>> customResources(Class<T> resourceType);

    <T extends CustomResource, L extends KubernetesResourceList<T>> MixedOperation<T, L, Resource<T>> customResources(Class<T> resourceType, Class<L> listClass);

    /**
     * 发现 api group
     *
     * @return
     */
    DiscoveryAPIGroupDSL discovery();

    /**
     * 扩展 api group
     *
     * @return
     */
    ExtensionsAPIGroupDSL extensions();

    /**
     * 版本
     *
     * @return
     */
    VersionInfo getVersion();


    RawCustomResourceOperationsImpl customResource(CustomResourceDefinitionContext customResourceDefinition);


    AdmissionRegistrationAPIGroupDSL admissionRegistration();

    AppsAPIGroupDSL apps();

    AutoscalingAPIGroupDSL autoscaling();

    NetworkAPIGroupDSL network();

    StorageAPIGroupDSL storage();

    BatchAPIGroupDSL batch();

    MetricAPIGroupDSL top();

    PolicyAPIGroupDSL policy();

    RbacAPIGroupDSL rbac();

    SchedulingAPIGroupDSL scheduling();

    MixedOperation<ComponentStatus, ComponentStatusList, Resource<ComponentStatus>> componentstatuses();

    ParameterNamespaceListVisitFromServerGetDeleteRecreateWaitApplicable<HasMetadata> load(InputStream is);

    ParameterNamespaceListVisitFromServerGetDeleteRecreateWaitApplicable<HasMetadata> resourceList(String s);

    NamespaceListVisitFromServerGetDeleteRecreateWaitApplicable<HasMetadata> resourceList(KubernetesResourceList list);

    NamespaceListVisitFromServerGetDeleteRecreateWaitApplicable<HasMetadata> resourceList(HasMetadata... items);

    NamespaceListVisitFromServerGetDeleteRecreateWaitApplicable<HasMetadata> resourceList(Collection<HasMetadata> items);

    <T extends HasMetadata> NamespaceVisitFromServerGetWatchDeleteRecreateWaitApplicable<T> resource(T is);

    NamespaceVisitFromServerGetWatchDeleteRecreateWaitApplicable<HasMetadata> resource(String s);

    MixedOperation<Binding, KubernetesResourceList<Binding>, Resource<Binding>> bindings();

    MixedOperation<Endpoints, EndpointsList, Resource<Endpoints>> endpoints();

    NonNamespaceOperation< Namespace, NamespaceList, Resource<Namespace>> namespaces();

    NonNamespaceOperation<Node, NodeList, Resource<Node>> nodes();

    NonNamespaceOperation<PersistentVolume, PersistentVolumeList, Resource<PersistentVolume>> persistentVolumes();

    MixedOperation<PersistentVolumeClaim, PersistentVolumeClaimList, Resource<PersistentVolumeClaim>> persistentVolumeClaims();

    MixedOperation<Pod, PodList, PodResource<Pod>> pods();

    MixedOperation<ReplicationController, ReplicationControllerList, RollableScalableResource<ReplicationController>> replicationControllers();

    MixedOperation<ResourceQuota, ResourceQuotaList, Resource<ResourceQuota>> resourceQuotas();

    MixedOperation<Secret, SecretList, Resource<Secret>> secrets();


    MixedOperation<Service, ServiceList, ServiceResource<Service>> services();

    MixedOperation<ServiceAccount, ServiceAccountList, Resource<ServiceAccount>> serviceAccounts();

    MixedOperation<APIService, APIServiceList, Resource<APIService>> apiServices();

    KubernetesListMixedOperation lists();

    MixedOperation<ConfigMap, ConfigMapList, Resource<ConfigMap>> configMaps();

    MixedOperation<LimitRange, LimitRangeList, Resource<LimitRange>> limitRanges();

    AuthorizationAPIGroupDSL authorization();

    Createable<TokenReview> tokenReviews();

    SharedInformerFactory informers();

    SharedInformerFactory informers(ExecutorService executorService);

    <C extends Namespaceable<C> & KubernetesClient> LeaderElectorBuilder<C> leaderElector();

    MixedOperation<Lease, LeaseList, Resource<Lease>> leases();

    V1APIGroupDSL v1();

    RunOperations run();

    NonNamespaceOperation<RuntimeClass, RuntimeClassList, Resource<RuntimeClass>> runtimeClasses();
}
