<!DOCTYPE html>
<html lang="en">
<head>
    <title>登录</title>
    <#include "./common/meta-layout.ftl"/>
</head>
<style>
    /*input{*/
        /*width: 200px;*/
    /*}*/
</style>
<body>
    <div class="container">
        <div class="row align-items-center" style="margin-top: 20%;background-color: #ffffff;">
            <form role="form" class="form-inline">
                <div class="col-lg-4 align-self-start" style="display: inline">
                    <div class="form-group">
                    <#--<label for="phone" class="sr-only">手机号</label>-->
                        <input name="phone" type="text" class="form-control input-large" placeholder="请输入手机号"/>
                    </div>
                </div>
                <div class="col-lg-4 align-self-center" style="display: inline">
                    <div class="form-group">
                    <#--<label for="password" class="sr-only">密码</label>-->
                        <input name="password" type="password" class="form-control input-large" placeholder="请输入密码"/>
                    </div>
                </div>
                <div class="col-lg-4 align-self-end" style="display: inline">
                    <button type="submit" class="btn btn-success">登录</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>