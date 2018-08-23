package com.optimus.mvc.customize;

import com.optimus.common.exception.BizException;
import com.optimus.mvc.JsonResult;
import com.optimus.mvc.JsonResultFactory;
import com.optimus.utils.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 * 定制JSON返回结果,简易的实现,如果应用了Spring的特性,可能兼容的不是很好
 * Created by caoawei on 2018/7/30.
 */
public class JsonHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) || returnType.hasMethodAnnotation(ResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletRequest request = getNativeRequest(webRequest);
        HttpServletResponse response = getNativeResponse(webRequest);
        writeWithMessageConverters(returnValue, returnType, request, response);
    }

    private static void writeWithMessageConverters(Object returnValue, MethodParameter returnType, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jr = JsonResultFactory.create(returnValue);
        String rs = Utils.toJson(jr);
        String encoding = request.getCharacterEncoding();
        try {
            response.setCharacterEncoding(encoding);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(rs);
            response.getWriter().flush();
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    private static HttpServletRequest getNativeRequest(NativeWebRequest webRequest){
        return webRequest.getNativeRequest(HttpServletRequest.class);
    }

    private static HttpServletResponse getNativeResponse(NativeWebRequest webRequest){
        return webRequest.getNativeResponse(HttpServletResponse.class);
    }
}
