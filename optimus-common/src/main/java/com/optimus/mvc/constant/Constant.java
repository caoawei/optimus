package com.optimus.mvc.constant;

/**
 * 常量信息
 */
public class Constant {

    /**
     * 请求后缀: .json 表示有数据返回,且Controller类是被RestController注解的,返回数据结构是固定的json格式
     * .htm或.html    类型的请求是页面请求,服务端会返回页面和数据
     */
    public static final String REQUEST_URI_SUFFIX_JSON = ".json";

    /**
     * 分页参数:开始位置
     */
    public static final String PAGE_CONFIG_PARAM_START = "page_start";

    /**
     * 分页参数:每页大小
     */
    public static final String PAGE_CONFIG_PARAM_LIMIT = "page_limit";

    /**
     * 统一的页面处理器
     */
    public static final String COMMON_HTML_URI = "/request/toView.ftl";

    /**
     * 存储在Request.getAttribute()实际页面路径的属性名称
     */
    public static final String VIEW_ATTR_NAME = "com.luntan.module.web.service";
}
