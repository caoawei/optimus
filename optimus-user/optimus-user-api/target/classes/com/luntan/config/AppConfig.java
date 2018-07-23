package com.luntan.config;

import com.optimus.mvc.config.DataSourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author caoawei
 * Created by on 2018/4/25.
 */

@Configuration
@Import({DataSourceConfig.class})
public class AppConfig {
}
