package com.optimus.mvc;


public class JsonResultFactory {

    public static JsonResult create(){
        return new JsonResult();
    }

    public static JsonResult create(Object data){
        return new JsonResult(data);
    }

    public static JsonResult create(Exception ex){
        return new JsonResult(ex);
    }
}
