<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-admin-new"/>
<c:url var="NEWurl" value="/admin-new"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Danh sach bai viet</title>
</head>
<body>
<!-- main-content -->
    <div class="main-content">
        <form action="<c:url value="/admin-new" />" id="formsubmit" method="get">
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
                    <div class="row" >
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
                                               class="dt-button buttons-colvis btn btn-white btn-primary btn-bold" data-toggle="tooltip" title='Thêm bài viết'
                                               href='<c:url value="/admin-new?type=edit"/>'>
															<span>
																<i class="fa fa-plus-circle bigger-110 purple"></i> <%--font awesome--%>
															</span>
                                            </a>
                                            <%--btnDelete--%>
                                            <button id="btnDelete" type="button"
                                                    class="dt-button buttons-html5 btn btn-white btn-primary btn-bold" data-toggle="tooltip" title='Xóa bài viết'>
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
                                                <c:url var="editURL" value="/admin-new">
                                                    <c:param name="type" value="edit"/>
                                                    <c:param name="id" value="${item.id}"/>
                                                </c:url>
                                                <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                   title="Cập nhật bài viết" href='${editURL}'><i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <ul class="pagination" id="pagination"></ul> <%--paging--%>
                                <input type="hidden" id="pagePos" value="" name="page" />
                                <input type="hidden" id="maxPageItem" value="" name="maxPageItem" />
                                <input type="hidden" id="sortName" value="" name="sortName" />
                                <input type="hidden" id="sortBy" value="" name="sortBy" />
                                <input type="hidden" id="type" value="" name="type"/>
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
    var total = ${model.totalPage};
    var current = ${model.page};
    var limit = 2;
    $(function () {
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: total,
            visiblePages: 3,
            startPage: current,
            onPageClick: function (event, pageVal) {
                if (current != pageVal) {
                    /*set value cho id*/
                    $('#maxPageItem').val(limit);
                    $('#pagePos').val(pageVal);
                    $('#sortName').val('title');
                    $('#sortBy').val('desc');
                    $('#type').val('list');
                    $('#formsubmit').submit();
                }
            }
        });
    });
    $('#btnDelete').click(function () {
        var idsArr = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        var data ={};
        data['ids'] = idsArr;
        deleteNew(data);
    });
    function deleteNew(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            /*vì trả về void nên ko cần set dataType*/ 
            success: function () {
                window.location.href = "${NEWurl}?type=list&maxPageItem=2&page=1&message=delete_success";
            },
            error: function () {
                window.location.href = "${NEWurl}?type=list&maxPageItem=2&page=1&message=error_system";
            }
        });
    }

</script>
<!--END paging function-->
</body>
</html>
