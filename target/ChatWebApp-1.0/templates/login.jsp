<%--
  Created by IntelliJ IDEA.
  User: Kha
  Date: 1/25/2022
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/templates/common/taglib.jsp" %>
<html lang="en">
<head>
    <%@include file="/templates/common/web/meta.jsp" %>
</head>
<body>
<%@include file="/templates/common/web/header.jsp" %>
<!-- Container -->
<div class="container mt-3">
    <form action="/login" method="POST">
        <div class="row g-3">
            <div class="col-auto">
                <label class="col-form-label">Name</label>
            </div>
            <div class="col-auto">
                <input autocomplete="off" name="inputName" type="text" class="form-control" placeholder="Name">
            </div>
            <div class="col-auto"></div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </div>
    </form>
</div>
<%@include file="/templates/common/web/footer.jsp" %>
</body>
</html>