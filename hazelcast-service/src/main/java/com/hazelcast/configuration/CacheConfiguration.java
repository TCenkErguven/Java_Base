package com.hazelcast.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientConnectionStrategyConfig;
import com.hazelcast.client.config.ConnectionRetryConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(createHazelcastInstance());
    }

    @Bean
    public HazelcastInstance createHazelcastInstance() {
        HazelcastInstance instance = HazelcastClient.newHazelcastClient(createClientConfig());
        instance.getConfig().addMapConfig(new MapConfig("save-dto").setTimeToLiveSeconds(15));
        return instance;
    }



    private ClientConfig createClientConfig(){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("dev");
        clientConfig.getNetworkConfig().addAddress("localhost:5701");
        clientConfig.setConnectionStrategyConfig(connectionStrategyConfig());

        return clientConfig;
    }

    private ConnectionRetryConfig connectionRetryConfig(){
        ConnectionRetryConfig connectionRetryConfig = new ConnectionRetryConfig();
        connectionRetryConfig.setInitialBackoffMillis(1000);
        connectionRetryConfig.setMaxBackoffMillis(5000);
        connectionRetryConfig.setClusterConnectTimeoutMillis(5000);
        connectionRetryConfig.setMultiplier(2);
        return connectionRetryConfig;
    }

    private ClientConnectionStrategyConfig connectionStrategyConfig(){
        ClientConnectionStrategyConfig connectionStrategyConfig = new ClientConnectionStrategyConfig();
        connectionStrategyConfig.setConnectionRetryConfig(connectionRetryConfig());
        return connectionStrategyConfig;
    }

}
