package com.optimus;

public interface CacheKey {

    /**
     * 将 prefix加在key之前
     * @param prefix 缓存key前缀
     * @return 当前缓存key对象
     */
    CacheKey befor(String prefix);

    /**
     * 将 suffix加在key之后
     * @param suffix 缓存key后缀
     * @return 当前缓存key对象
     */
    CacheKey after(String suffix);

    /**
     * 返回字符串形式的key
     * @return
     */
    String get();

    /**
     * 以字节数组形式返回key
     * @return
     */
    byte[] bytes();
}
