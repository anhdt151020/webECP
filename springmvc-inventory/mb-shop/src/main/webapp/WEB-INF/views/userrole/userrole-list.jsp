<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2 style="margin-right: 20px;"><a href="<c:url value="/userrole/add"/>" class="btn btn-app"><i class="fa fa-plus"></i>Add</a></h2>
                    <h1>User-Role List</h1>

                    <div class="clearfix"></div>
                </div>


                <div class="x_content">
                    <div class="container" style="padding: 50px;">
                        <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/userrole/list/1" method="POST">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12"
                                       for="roleByRoleId.name">Role Name </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="roleByRoleId.name"
                                                cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12"
                                       for="usersByUserId.username">Username </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="usersByUserId.username"
                                                cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button type="submit" class="btn btn-success">Search</button>
                                </div>
                            </div>

                        </form:form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">#</th>
                                <th class="column-title">Role</th>
                                <th class="column-title">Username</th>
                                <th class="column-title">Active/Block</th>
                                <th class="column-title">Action</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${userRoles}" var="userrole" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${userrole.roleByRoleId.name }</td>
                                <td class=" ">${userrole.usersByUserId.username }</td>
                                <c:choose>
                                    <c:when test="${userrole.activeFlag==1}">
                                        <td><a href="javascript:void(0);"
                                               onclick="confirmChange(${userrole.id},${userrole.activeFlag});" class="btn
                                            btn-round btn-primary">Active</a></td>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><a href="javascript:void(0);"
                                               onclick="confirmChange(${userrole.id},${userrole.activeFlag});" class="btn
                                            btn-round btn-danger">Block</a></td>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td class=" "><a href="javascript:void(0);"
                                                           onclick="confirmDelete(${userrole.id});"
                                                           class="btn btn-round btn-danger">Delete</a></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                        <jsp:include page="../layout/paging.jsp"></jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function gotoPage(page) {
        $('#searchForm').attr('action', '<c:url value="/userrole/list/"/>' + page);
        $('#searchForm').submit();
    }
    function confirmDelete(id) {
        if (confirm('Do you want delete this record?')) {
            window.location.href = '<c:url value="/userrole/delete/"/>' + id;
        }
    }

    function confirmChange(id, flag) {
        var msg = flag == 1 ? 'Do you want block this user-role ?' : 'Do you want active this user-role ?';
        if (confirm(msg)) {
            window.location.href = '<c:url value="/userrole/change-status/"/>' + id;
        }
    }

</script>