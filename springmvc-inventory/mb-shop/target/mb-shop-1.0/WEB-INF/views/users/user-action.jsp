<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h2>${titlePage}</h2>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">

                    <div class="x_content">
                        <br/>
                        <form:form modelAttribute="mUsers" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/user/save" method="POST">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="username">Username <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="username" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="username" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${mUsers.id == sUserInfo.id || mUsers.id == 0}">
                                <div class="form-group">
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">Password
                                        <span class="required">*</span>
                                    </label>
                                    <div class="col-md-6 col-sm-6 col-xs-12">
                                        <form:input type="password" path="password" cssClass="form-control col-md-7 col-xs-12"/>
                                        <div class="has-error">
                                            <form:errors path="password" cssClass="help-block"></form:errors>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">Email <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="email" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="email" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name"
                                       class="control-label col-md-3 col-sm-3 col-xs-12">Name</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="name" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="name" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
                                    <c:if test="${!viewOnly }">
                                        <button class="btn btn-primary" type="reset">Reset</button>
                                        <button type="submit" class="btn btn-success">Submit</button>
                                    </c:if>
                                </div>
                            </div>

                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    // url: /category/list -> MenuModel.idMenu = categorylistId, menu nay nam trong <li class="active"> <div class="current-page">
    $(document).ready(
        function () {
            $('#userlistId').addClass('current-page').siblings()
                .removeClass('current-page');
            var parent = $('#userlistId').parents('li');
            parent.addClass('active').siblings().removeClass('active');
            $('#userlistId').parents().show();
        });

    function cancel() {
        window.location.href = '<c:url value="/user/list"/>'
    }
</script>