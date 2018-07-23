package com.optimus.common.mybatis;

import com.optimus.common.constant.DubboKey;
import com.optimus.utils.DubboUtil;

public class ThreadPageUtil {

    private static final ThreadLocal<PageParam> pageParamContainer = new ThreadLocal<>();

    public static PageParam getPageParam(){
        PageParam pageParam = pageParamContainer.get();
        if(pageParam == null){
            pageParam = DubboUtil.getPageParam(DubboKey.Page_List);
        }

        return pageParam;
    }

    public static void putPageParam(PageParam pageParam){
        if(pageParam == null){
            pageParam = PageParam.initWithOffset(-1,-1);
        }
        pageParamContainer.set(pageParam);
        DubboUtil.setPageParam(pageParam);
    }

    public static void removePageParam(){
        pageParamContainer.remove();
        DubboUtil.removeAttachment(DubboKey.Page_List.getKey());
    }

}
