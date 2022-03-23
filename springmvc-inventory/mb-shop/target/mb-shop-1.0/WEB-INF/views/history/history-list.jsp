<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h1>Product In Stock List</h1>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="container" style="padding: 50px;">
                        <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/history/list/1" method="POST">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="id">ID</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="id" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="productInfoByProductId.code">Product Code </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="productInfoByProductId.code"
                                                cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Invoice code </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="code"
                                                cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="qty">Quantity</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="qty" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="price">Price</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="price" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="action">Action User</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="action" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">Type</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:select path="type" cssClass="form-control">
                                        <form:options items="${mapType}"/>
                                    </form:select>
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
                                <th class="column-title">Id</th>
                                <th class="column-title">Product Code</th>
                                <th class="column-title">Invoice Code</th>
                                <th class="column-title">Qty</th>
                                <th class="column-title">Price</th>
                                <th class="column-title">Date</th>
                                <th class="column-title">Action User</th>
                                <th class="column-title">Description</th>
                                <th class="column-title">Type</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${histories}" var="his" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${his.id}</td>
                                <td class=" ">${his.productInfoByProductId.code}</td>
                                <td class=" ">${his.code}</td>
                                <td class="">${his.qty}</td>
                                <td class="">${his.price}</td>
                                <td class="">${his.modifiedDate}</td>
                                <td class="">${his.action}</td>
                                <td class="">${his.description}</td>
                                <c:choose>
                                    <c:when test="${his.type==1}">
                                        <td>Goods Receipt</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>Goods Issues</td>
                                    </c:otherwise>
                                </c:choose>
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
        $('#searchForm').attr('action', '<c:url value="/history/list/"/>' + page);
        $('#searchForm').submit();
    }
</script>
