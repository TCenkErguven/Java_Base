package org.example.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final String cacheName;
    private final String clusterName;
    private final String networkAddress;

    public CacheConfiguration(@Value("${cache.cache-name}")String cacheName, @Value("${cache.cluster-name}")String clusterName, @Value("${cache.network-address}")String networkAddress) {
        this.cacheName = cacheName;
        this.clusterName = clusterName;
        this.networkAddress = networkAddress;
    }


    @Bean
    public CacheManager cacheManager()   {
        return new HazelcastCacheManager(createHazelcastInstance());
    }

    /**
     * This @Bean is excessive if we don't use the hazelcast's own save functions and if this
     * case is valid @Bean annotation needed to be removed
     * @return
     */
    @Bean
    public HazelcastInstance createHazelcastInstance(){
       HazelcastInstance instance = HazelcastClient.newHazelcastClient(createClientConfig());
       instance.getConfig().addMapConfig(new MapConfig(cacheName).setTimeToLiveSeconds(15));
       return instance;
    }

    public ClientConfig createClientConfig(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        clientConfig.getNetworkConfig().addAddress(networkAddress);
        return clientConfig;
    }


}
