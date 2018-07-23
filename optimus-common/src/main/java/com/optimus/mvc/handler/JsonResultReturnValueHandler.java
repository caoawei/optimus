package com.optimus.mvc.handler;

import com.optimus.common.exception.BizException;
import com.optimus.mvc.JsonResult;
import com.optimus.mvc.JsonResultFactory;
import com.optimus.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * INFO:
 * HTTP响应结果 json数据结构定制
 */
public class JsonResultReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static final Logger logger = LoggerFactory.getLogger(JsonResultReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Class cls = returnType.getContainingClass();
        return cls.isAnnotationPresent(RestController.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletRequest request = getNativeRequest(webRequest);
        HttpServletResponse response = getNativeResponse(webRequest);
        writeWithMessageConverters(returnValue, returnType, request, response);
    }

    private void writeWithMessageConverters(Object returnValue, MethodParameter returnType, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jr = JsonResultFactory.create(returnValue);
        String rs = Utils.toJson(jr);
        String encoding = request.getCharacterEncoding();
        try {
            response.setCharacterEncoding(encoding);
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
    }

    private HttpServletRequest getNativeRequest(NativeWebRequest webRequest){
        return webRequest.getNativeRequest(HttpServletRequest.class);
    }

    private HttpServletResponse getNativeResponse(NativeWebRequest webRequest){
        return webRequest.getNativeResponse(HttpServletResponse.class);
    }
}
