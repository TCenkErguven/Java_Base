package com.java.base.filter;

import com.java.base.config.redis.RedisConfiguration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(RedisConfiguration.class);
    }
}
