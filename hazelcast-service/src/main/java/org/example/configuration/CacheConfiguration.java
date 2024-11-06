package org.example.configuration;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
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
    public CacheManager cacheManager()   {
        return new HazelcastCacheManager(createHazelcastInstance());
    }

    @Bean
    public HazelcastInstance createHazelcastInstance(){
        ClientConfig clientConfig = new ClientConfig();
       return HazelcastClient.newHazelcastClient(clientConfig);
    }


}
