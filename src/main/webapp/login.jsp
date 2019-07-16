<%--
Created by IntelliJ IDEA.
User: Administrator
Date: 2019-07-15
Time: 10:08
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("[name=loginAct]").focus();
            $("[name=loginAct]").val("");
            $("#loginBt").click(login);
            $("#testBt").click(test);
        });

        //表单检验
        function check() {
            var loginAct = $.trim($("[name=loginAct]").val());
            var loginPwd = $.trim($("[name=loginPwd]").val());
            var $msg = $("#msg");
            if (loginAct === "") {
                $msg.html("用户名不能空");
            } else if (loginPwd === "") {
                $msg.html("密码不能空");
            } else {
                $msg.html("");
            }
        }

        //提交表单
        function login() {
            check();
            var $msg = $("#msg");
            if ($msg.html() === "") {
                $(":hidden").val(new Date().getTime());
                $.ajax({
                    url: "setting/user/login.do",
                    type: "get",
                    dataType: "json",
                    data: $("form").serialize(),
                    success: function (data) {
                        if (data.success) {
                            $msg.html("成功登陆");
                            window.location.href = "workbench/index.jsp";
                        } else {
                            $msg.html(data.msg);
                        }
                    }
                });
            }
        }

        function test() {
            console.log(document.cookie)
        }
    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" name="loginAct" type="text" placeholder="用户名">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" name="loginPwd" type="password" placeholder="密码">
                </div>
                <div style="width: 350px; position: relative;top: 30px;">
                    <label>免登陆：
                        <input class="form-inline" name="unLogin" type="checkbox">
                    </label>
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: orangered"></span>

                </div>
                <input type="hidden" name="Gm" value="">
                <input type="button" id="loginBt" value="登录" class="btn btn-primary btn-lg btn-block"
                       style="width: 350px; position: relative;top: 45px;">
                <input type="button" id="testBt" value="跳转" class="btn btn-primary btn-lg btn-block"
                       style="width: 350px; position: relative;top: 45px;">
            </div>
        </form>
    </div>
</div>
</body>
</html>