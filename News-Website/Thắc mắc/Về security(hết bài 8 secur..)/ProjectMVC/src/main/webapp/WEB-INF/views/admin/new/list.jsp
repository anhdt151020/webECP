<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Danh sach bai viet</title>
</head>
<body>
<!-- main-content -->
<div class="main-content">
    <form action="#" id="formsubmit" method="get">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Trang chủ</a>
                    </li>
                </ul><!-- /.breadcrumb -->
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <c:if test="${not empty messageResponse}">
                            <div class="alert alert-${alert}">
                                    ${messageResponse}
                            </div>
                        </c:if>
                        <%--add+del icon--%>
                        <div class="widget-box table-filter">
                            <div class="table-btn-controls">
                                <div class="pull-right tableTools-container">
                                    <div class="dt-buttons btn-overlap btn-group">
                                        <%--btnAdd--%>
                                        <a flag="info"
                                           class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
                                           data-toggle="tooltip" title='Thêm bài viết'
                                           href='#'>
															<span>
																<i class="fa fa-plus-circle bigger-110 purple"></i> <%--font awesome--%>
															</span>
                                        </a>
                                        <%--btnDelete--%>
                                        <button id="btnDelete" type="button"
                                                class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
                                                data-toggle="tooltip" title='Xóa bài viết'>
																<span>
																	<i class="fa fa-trash-o bigger-110 pink"></i>   <%--font awesome--%>
																</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--(end)add+del icon--%>


                        <div class="table-responsive">
                            <table class="table table-condensed">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="checkAll"></th>
                                    <th>Tên bài viết</th>
                                    <th>Mô tả ngắn</th>
                                    <th>Thao tác</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${model.listResult}" var="item">
                                    <tr>
                                        <th><input type="checkbox" id="checkbox_${item.id}" value="${item.id}"></th>
                                        <td>${item.title}</td>
                                        <td>${item.shortDescription}</td>
                                        <td>
                                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                               title="Cập nhật bài viết" href='#'><i class="fa fa-pencil-square-o"
                                                                                     aria-hidden="true"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- /.main-content -->

<!--paging function-->
<script>

</script>
<!--END paging function-->
</body>
</html>
