<%--
  Created by IntelliJ IDEA.
  User: tsuki
  Date: 2017/4/21
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <style>
        *{
            margin:0;
            padding:0;}
        body{
            text-align: center;
        } a
          {text-decoration:none;
              color:#000;}
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
            border:#1263be solid 1px;
            background:#1b85fd;
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
<form action="UpdateActionServlet" method="POST">
    <div class="login_main">
        用户名： <input type="text" name="username" value="${user.username }" /><br /> <br />
        密&nbsp;&nbsp;码： <input type="password" name="password" size="12" maxlength="12" value="${user.password}"/><br /><br />
        姓&nbsp;&nbsp;名： <input type="text" name="fullname"value="${user.fullname}"/><br /> <br />
        年&nbsp;&nbsp;龄： <input type="text" name="age" value="${user.age}" /><br /> <br />
        住&nbsp;&nbsp;址： <input type="text" name="address"size="40" value="${user.address}" /><br /> <br />
    </div>
    <div class="login_btn">
        <input type="hidden" name="id" value="${user.id }"/><br/>
        <input type="submit" value=" 更新 " />
    </div>
</form>

</body>
</html>
