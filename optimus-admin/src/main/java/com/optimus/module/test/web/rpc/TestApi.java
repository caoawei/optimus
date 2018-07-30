package com.optimus.module.test.web.rpc;

import com.optimus.module.account.api.AccountApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/30.
 */
@FeignClient("user")
@Service
public interface TestApi extends AccountApi {
}
