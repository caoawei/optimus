package com.optimus.utils;

import com.alibaba.dubbo.rpc.RpcContext;
import com.optimus.common.constant.DubboKey;
import com.optimus.common.mybatis.PageParam;
import com.optimus.utils.Utils;

public class DubboUtil {

    public static RpcContext getRpcContext(){
        return RpcContext.getContext();
    }

    public static void setAttachemnet(String key,Object val){
        RpcContext.getContext().setAttachment(key, Utils.toJson(val));
    }

    public static void removeAttachment(String key){
        RpcContext.getContext().removeAttachment(key);
    }

    public static String getAttachment(String key){
        return RpcContext.getContext().getAttachment(key);
    }

    public static void setPageParam(PageParam pageParam){
        setAttachemnet(DubboKey.Page_List.getKey(),pageParam);
    }

    public static PageParam getPageParam(DubboKey dubboKey){
        String val = getAttachment(dubboKey.getKey());
        PageParam pageParam = Utils.fromJson(val,PageParam.class);
        return pageParam;
    }
}
