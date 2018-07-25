package com.optimus.mvc.handler;

import com.optimus.common.exception.BizException;
import com.optimus.mvc.JsonResult;
import com.optimus.mvc.JsonResultFactory;
import com.optimus.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    @ExceptionHandler
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        JsonResult jr = JsonResultFactory.create(ex);
        String rs = "";//Utils.toJson(jr);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(rs);
            response.getWriter().flush();
        } catch (Exception e) {
            logger.error("HttpServletResponse 写数据失败,ERROR:{}",e.getMessage(),e);
            throw new BizException("Response 写数据失败",e);
        } finally {
            try {
                response.getWriter().close();
            } catch (Exception e) {
                logger.error("[HttpResponse-ERROR-Witer close failure-ERRPR:{}]",e.getMessage(),e);
            }
        }
        return null;
    }
}
