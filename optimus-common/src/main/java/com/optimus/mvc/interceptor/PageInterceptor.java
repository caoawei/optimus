package com.optimus.mvc.interceptor;

import com.optimus.common.mybatis.PageParam;
import com.optimus.common.mybatis.ThreadPageUtil;
import com.optimus.common.mybatis.annotation.PageConfig;
import com.optimus.mvc.constant.Constant;
import com.optimus.utils.Utils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  处理{@link PageConfig}注解
 */
public class PageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Utils.isEmpty(request.getRequestURI()) || !request.getRequestURI().endsWith(Constant.REQUEST_URI_SUFFIX_JSON)){
            return Boolean.TRUE;
        }
        if(Utils.isNull(handler) || !(handler instanceof HandlerMethod) || !((HandlerMethod) handler).getMethod().isAnnotationPresent(PageConfig.class)){
            return Boolean.TRUE;
        }
        resolvePageParam(handler,request);
        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadPageUtil.removePageParam();
    }

    private void resolvePageParam(Object handler,HttpServletRequest request) {
        String $start = request.getParameter(Constant.PAGE_CONFIG_PARAM_START);
        String $limit = request.getParameter(Constant.PAGE_CONFIG_PARAM_LIMIT);
        int start;
        int limit;
        if(Utils.isEmpty($start) || Utils.isEmpty($limit)){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PageConfig pc = handlerMethod.getMethod().getAnnotation(PageConfig.class);
            start = pc.start();
            limit = pc.limit();
        } else {
            start = Math.max(Integer.valueOf($start),0);
            limit = Math.min(Integer.valueOf($limit),Integer.MAX_VALUE);
        }

        PageParam pageParam = PageParam.initWithOffset(start,limit);
        ThreadPageUtil.putPageParam(pageParam);
    }
}
