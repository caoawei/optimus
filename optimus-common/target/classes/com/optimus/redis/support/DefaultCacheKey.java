package com.optimus.redis.support;

import com.optimus.common.exception.BizException;
import com.optimus.redis.CacheKey;
import com.optimus.utils.Utils;

/**
 * Created by on 2018/4/26.
 */
public class DefaultCacheKey implements CacheKey {
    private static final String BLANK = "";
    private static final String ENCODE = "UTF-8";
    private StringBuilder key;

    private DefaultCacheKey(String key){
        this.key = new StringBuilder(key);
    }

    public static CacheKey initWithKey(String key) {
        if(Utils.isEmpty(key)){
            throw new BizException("The key does't be null or empty.");
        }
        return new DefaultCacheKey(key);
    }

    @Override
    public CacheKey befor(String prefix) {
        this.key.insert(0,Utils.isEmpty(prefix) ? BLANK : prefix);
        return this;
    }

    @Override
    public CacheKey after(String suffix) {
        this.key.append(Utils.isEmpty(suffix) ? BLANK : suffix);
        return this;
    }

    @Override
    public String get() {
        return this.key.toString();
    }

    @Override
    public byte[] bytes() {
        try {
            return this.key.toString().getBytes(ENCODE);
        } catch (Exception e){
            throw new BizException(e);
        }
    }
}
