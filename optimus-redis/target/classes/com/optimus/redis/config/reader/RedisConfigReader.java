package com.optimus.redis.config.reader;

import com.optimus.redis.RedisConfigRegistry;

public interface RedisConfigReader {

    void read(RedisConfigRegistry registry);
}
