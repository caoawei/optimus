package com.optimus.config;

import com.optimus.mvc.config.DataSourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by caoawei on 2018/7/25.
 */
@Configuration
@Import(DataSourceConfig.class)
public class AppConfig {
}
