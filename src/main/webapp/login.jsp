<%--
  Created by IntelliJ IDEA.
  User: tsuki
  Date: 2017/4/20
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <style>
        *{
            margin:0;
            padding:0;}
        body{
            text-align: center;
        } a
          {
              text-decoration:none;
              color:#000;}
        .login_main{
            margin-top: 20px;
        }
        .login_main input{
            margin: 0;
            width:400px;
            padding: 1em 2em 1em 5.4em;
            -webkit-border-radius:.3em;
            -moz-border-radius: .3em;
            border: 1px solid #999;
        }
        .login_btn{
            width:300px;
            margin:40px auto 0 550px;
        }
        .login_btn input{
            width:100%;
            margin:0;
            padding:.5em 0;
            -webkit-border-radius:.3em;
            -moz-border-radius: .3em;
            border:#1263be solid 1px;background:#1b85fd;
            color:#FFF;
            font-size:17px;
            font-weight:bolder;
            letter-spacing:1em;
        }
        .login_btn input:hover{
            cursor: pointer;
        }
    </style>
</head>
<body>
    <a href="register.jsp">新用户注册</a>
    <p />
    <form action="LoginActionServlet" method="POST">
        <div class="login_main">
            <span>用户名：</span><input type="text" name="username" /><br />
            <br />
            <span>密&nbsp;码：</span><input type="password" name="password" />
        </div>
        <div class="login_btn">
            <input type="submit" value="登录" />
        </div>
    </form>

</body>
</html>
