package com.optimus.mvc.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExtensionHelper {

    @Autowired
    private ApplicationContextExtension contextExtension;

    @PostConstruct
    public void init(){
        contextExtension.init();
    }
}
