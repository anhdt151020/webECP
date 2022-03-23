<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2 style="margin-right: 20px;"><a href="<c:url value="/menu/permission"/>" class="btn btn-app"><i class="fa fa-plus"></i>Permission</a></h2>
                    <h1>Menu List</h1>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">

                    <div class="container" style="padding: 50px;">
                        <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/menu/list/1" method="POST">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="url">Url </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="url" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="name" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button type="submit" class="btn btn-success">Search</button>
                                    <button class="btn btn-primary" type="reset">Reset</button>
                                </div>
                            </div>
                        </form:form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th rowspan="2" class="column-title" style="border-left: 2px solid">#</th>
                                <th rowspan="2" class="column-title" style="border-left: 2px solid">Url</th>
                                <th rowspan="2" class="column-title" style="border-left: 2px solid">Name</th>
                                <th rowspan="2" class="column-title" style="border-left: 2px solid">Active/Block</th>
                                <th class="column-title text-center" colspan="${roles.size()}" style="border-left: 2px solid;">Role</th>
                            </tr>
                            <tr>
                                <c:forEach var="role" items="${roles}">
                                    <th class="column-title" style="border-left: 2px solid">${role.name}</th>
                                </c:forEach>
                            </tr>
                            </thead>

                            <tbody>
                                <c:forEach items="${menus}" var="menu" varStatus="loop">
                                    <c:choose>
                                        <c:when test="${loop.index%2==0 }">
                                            <tr class="even pointer">
                                        </c:when>
                                        <c:otherwise>
                                            <tr class="odd pointer">
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${pageInfo.getOffset()+loop.index+1}</td>
                                    <td>${menu.url }</td>
                                    <td>${menu.name }</td>
                                    <c:choose>
                                        <c:when test="${menu.activeFlag==1}">
                                            <td><a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag});" class="btn
                                            btn-round btn-primary">Active</a></td></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag});" class="btn
                                            btn-round btn-danger">Block</a></td></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:forEach items="${menu.mapAuth}" var="auth">
                                        <c:choose>
                                            <c:when test="${auth.value==1}">
                                                <td class="text-center"><i class="fa fa-check" style="color: green"></i></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="text-center"><i class="fa fa-times" style="color:red;"></i></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
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
        $('#searchForm').attr('action', '<c:url value="/menu/list/"/>' + page);
        $('#searchForm').submit();
    }
    function confirmChange(id,flag){
        var msg = flag==1 ? 'Do you want block this menu ?' : 'Do you want active this menu ?';
        if(confirm(msg)){
            window.location.href = '<c:url value="/menu/change-status/"/>'+id;
        }
    }

</script>