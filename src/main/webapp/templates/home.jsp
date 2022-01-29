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
    <link href="/staitc/css/home.css" rel="stylesheet" />
</head>
<body>
<%@include file="/templates/common/web/header.jsp" %>

<!-- Container -->
<div class="container">
    <div id="chat" style="overflow: auto" class="chat over"></div>
    <div class="input-group">
        <input id="msg" type="text" class="form-control" placeholder="Message">
        <div class="input-group-append">
            <button id="sendBtn" class="btn btn-success" type="button">Send</button>
        </div>
    </div>
</div>
<%@include file="/templates/common/web/footer.jsp" %>
</body>
<script src="/staitc/js/home.js"></script>
</html>
