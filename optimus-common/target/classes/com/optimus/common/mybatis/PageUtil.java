package com.optimus.common.mybatis;

/**
 * 分页工具类
 * 此类用于在数据库事务查询开始之前调用 {@link PageUtil#turnOn()}方法,
 * 改造过的mybatis在执行sql时会自动添加 分页参数
 * @author caoawei
 */
public class PageUtil {

    private static final ThreadLocal<PageParam> pageParamContainer = new ThreadLocal<>();

    /**
     * 使用方式
     * {
     *     //方法首部(第一次dubbo服务调用之前)
     *     PageUtil.hold();
     *
     *     //dubbo 调用,此步的dubbo调用会将RpcContext的参数清除,因此
     *     //需要将其中的分页参数设置到 ThreadLoal里
     *     a.invoke();
     *
     *     //service 调用,且是需要分页查询的
     *     service.invoke();
     * }
     */
    public static void hold(){
        PageParam pageParam = getPageParam();
        pageParamContainer.set(pageParam);
    }

    /**
     * 设置启用分页的开关.
     * 使用方式(建议在 manager层使用)
     * <code>
     *     ...
     *     PageUtil.turnOn();
     *     try{
     *         mapper.selectByExample(...);
     *     }finally{
     *         PageUtil.turnOff();
     *     }
     * </code>
     *
     */
    public static void turnOn(){
        PageParam pageParam = getPageParam();
        if(pageParam == null){
            pageParam = PageParam.initWithOffset(0,20);
            pageParamContainer.set(pageParam);
        }
    }

    /**
     * 清楚和分页相关的参数
     */
    public static void turnOff(){
        pageParamContainer.remove();
        ThreadPageUtil.removePageParam();
    }

    /**
     * 是否需要分页查询
     * @return
     */
    public static boolean needPage(){
        PageParam pageParam = pageParamContainer.get();
        return pageParam != null;
    }

    /**
     * 获取分页参数
     * @return
     */
    public static PageParam getPageParam(){
        PageParam pageParam = pageParamContainer.get();
        if(pageParam == null){
            pageParam = ThreadPageUtil.getPageParam();
        }

        return pageParam;
    }
}
