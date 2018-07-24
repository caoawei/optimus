package com.optimus.mvc;

public class JsonResult {

    private boolean success;

    private Integer code;

    private String errorMsg;

    private Object data;

    public JsonResult(Object data){
        this.success = true;
        this.data = data;
        this.code = 200;
    }

    public JsonResult(Exception e){
        this.success = false;
        this.data = data;
        this.errorMsg = e.getMessage();
    }

    public JsonResult(){
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
