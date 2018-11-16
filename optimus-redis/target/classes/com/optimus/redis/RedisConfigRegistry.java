package com.optimus.redis;

import com.optimus.redis.config.RedisConfig;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RedisConfigRegistry {

    private List<RedisConfig> registry = new ArrayList<>();

    public void register(RedisConfig redisConfig) {
        registry.add(redisConfig);
    }
}
