<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><dec:title default="Đăng nhập" /></title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="<c:url value='/template/login/style.css' />" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div class="container">
    <!-- <h1 class="form-heading">login Form</h1> -->
    <div class="login-form">
        <div class="main-div">
            <div class="panel">
                <h2>Admin login</h2>
                <p>Please enter your email and password</p>
            </div>

            <form action="<c:url value="/dang-nhap"/>" id="formLogin" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" id="inputEmail" name="userName" placeholder="Tên đăng nhập">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Mật khẩu">
                </div>
                <input type="hidden" value="login" id="action" name="action"/>
                <button type="submit" class="btn btn-primary" >Đăng nhập</button>
            </form>

            <%--message for login--%>
            <c:if test="${not empty message}">
                <div class="alert alert-${alert}">
                        ${message}
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>