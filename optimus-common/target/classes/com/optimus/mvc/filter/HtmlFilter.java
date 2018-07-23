package com.optimus.mvc.filter;

import com.optimus.mvc.constant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.htm","*.html"})
public class HtmlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hst = (HttpServletRequest) request;
        String uri = hst.getRequestURI();
        String contextPath = hst.getContextPath();
        request.setAttribute(Constant.VIEW_ATTR_NAME,uri.substring(contextPath.length()+1,uri.lastIndexOf(".")));
        request.getRequestDispatcher(Constant.COMMON_HTML_URI).forward(request,response);
        //chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
