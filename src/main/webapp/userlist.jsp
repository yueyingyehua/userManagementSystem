<%--
  Created by IntelliJ IDEA.
  User: tsuki
  Date: 2017/4/20
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>用户清单</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
</head>
<body>
    <p>${FULLNAME}，欢迎您，下面是所有用户信息：</p>
    <table border="1" bordercolor="cc0000" cellpadding="5" style="border-collapse: collapse">
        <tr bgcolor="#e5e5e5" align="center">
            <td>序号</td>
            <td>账户名</td>
            <td>密码</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>住址</td>
            <td>注册时间</td>
            <td>锁定状态</td>
            <td>删除</td>
            <td>更新</td>
        </tr>

        <c:forEach items="${users}" var="user" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.fullname}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.createdTime}</td>
                <td>${user.locked}</td>
                <td><a href="DeleteActionServlet?id=${user.id }">删除</a></td>
                <td><a href="UpdatePServlet?id=${user.id }">更新</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
