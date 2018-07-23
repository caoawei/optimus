package com.luntan.module.admin.web.rpc;

import com.optimus.common.exception.BizException;
import com.optimus.common.mybatis.annotation.PageConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminRpc {


    @PageConfig
    @RequestMapping("/admin/admin/test.json")
    public Object test(){
        throw new BizException("系统异常");
    }
}
