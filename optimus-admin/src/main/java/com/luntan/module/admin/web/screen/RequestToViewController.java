package com.luntan.module.admin.web.screen;

import com.optimus.mvc.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.VolatileImage;

@Controller
public class RequestToViewController {

    @RequestMapping("/request/toView.ftl")
    public String toView(HttpServletRequest request){
        String viewName = (String) request.getAttribute(Constant.VIEW_ATTR_NAME);
        request.removeAttribute(Constant.VIEW_ATTR_NAME);
        return viewName;
    }
}
