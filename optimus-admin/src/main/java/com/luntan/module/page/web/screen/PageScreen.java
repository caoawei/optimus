package com.luntan.module.page.web.screen;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

@Controller
public class PageScreen {

    @RequestMapping("/page/index.ftl")
    public String page(ModelMap modelMap,HttpServletRequest request){
        modelMap.addAllAttributes(Collections.emptyList());
        return "index";
    }
}
