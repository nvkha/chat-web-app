<%--
  Created by IntelliJ IDEA.
  User: Kha
  Date: 1/29/2022
  Time: 8:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/templates/common/taglib.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <%@include file="/templates/common/web/meta.jsp" %>
    <link rel="stylesheet" href="/staitc/css/signin.css">
</head>

<body class="text-center">
<form class="form-signin" action="/login" method="POST">
    <img class="mb-4" src="https://img.icons8.com/doodle/96/000000/chat.png" alt="">
    <div class="row">
        <input autocomplete="off" name="inputName" class="form-control mb-3" placeholder="Enter your name" required>
        <button class="btn btn-primary btn-block" type="submit">Login</button>
    </div>
</form>
</body>
</html>
