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
                        <form:form modelAttribute="mUserRole" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/userrole/save" method="POST"
                                   enctype="multipart/form-data">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="usersByUserId.username">Username <span class="required">*</span></label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select path="userId" cssClass="form-control">
                                        <form:options items="${mapUsers}"/>
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="userId" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="roleByRoleId.name">Role <span class="required">*</span></label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select path="roleId" cssClass="form-control">
                                        <form:options items="${mapRoles}"/>
                                    </form:select>
                                    <div class="has-error">
                                        <form:errors path="roleId" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>

                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
                                    <button class="btn btn-primary" type="reset">Reset</button>
                                    <button type="submit" class="btn btn-success">Submit</button>
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
    $(document).ready(
        function () {
            $('#userrolelistId').addClass('current-page').siblings()
                .removeClass('current-page');
            var parent = $('#userrolelistId').parents('li');
            parent.addClass('active').siblings().removeClass('active');
            $('#userrolelistId').parents().show();
        });

    function cancel() {
        window.location.href = '<c:url value="/userrole/list"/>'
    }
</script>