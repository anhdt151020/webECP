<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="apiURL" value="/api/new"/>
<c:url var ="newListURL" value="/admin/news/list"/>
<c:url var ="newEditURL" value="/admin/news/edit"/>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">Chỉnh sửa bài viết</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty message}">
                        <div class="alert alert-${alert}">
                                ${message}
                        </div>
                    </c:if>
                    <form:form class="form-horizontal" role="form" id="formSubmit" modelAttribute="model">
                        <div class="form-group">
                            <label for="categoryCode" class="col-sm-3 control-label no-padding-right">Thể loại</label>
                            <div class="col-sm-4">
                                <%--items ở đây yêu cầu type là Map, chú ý options --%>
                                <form:select path="categoryCode" >
                                    <form:option value="" label="-- Chọn thể loại --"/>
                                    <form:options items="${categories}"/>
                                </form:select>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Tiêu đề</label>
                            <div class="col-sm-9">
                                <form:input path="title" cssClass="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Ảnh đại diện</label>
                            <div class="col-sm-9">
                                <%--<input type="file" class="col-xs-10 col-sm-5" id="thumbnail" name="thumbnail" value="${model.thumbnail}"/>--%>
                                <form:input path="thumbnail" cssClass="col-xs-10 col-sm-5"/>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group">
                            <label for="shortDescription" class="col-sm-3 control-label no-padding-right">Mô tả ngắn</label>
                            <div class="col-sm-9">
                                <%--<textarea class="form-control" rows="2" cols="10" id="shortDescription" name="shortDescription">${model.shortDescription}</textarea>--%>
                                <form:textarea path="shortDescription" cssClass="form-control" rows="2" cols="10" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="content" class="col-sm-3 control-label no-padding-right">Nội dung</label>
                            <div class="col-sm-9">
                                <form:textarea path="content" cssClass="form-control" rows="2" cols="10" />
                            </div>
                        </div>
                        <form:hidden path="id" id="newId"/>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <c:if test="${not empty model.id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Update
                                    </button>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <button class="btn btn-info" type="button" id="btnAddOrUpdateNew">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        Add
                                    </button>
                                </c:if>
                                &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        var id = $('#newId').val();
        var formData = $('#formSubmit').serializeArray();
        var data = {};
        $.each(formData, function (index, field) {
            data[field.name] = field.value;
        })
        if (id == "") {
            addNew(data);
        } else {
            updateNew(data);
        }
    });
    function addNew(data) {
        $.ajax({
            url: '${apiURL}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = "${newEditURL}?id="+result.id+"&message=insert_success";
            },
            error: function () {
                window.location.href = "${newListURL}?page=1&limit=2&message=error_system";
            }
        });

    };
    function updateNew(data) {
        $.ajax({
            url: '${apiURL}',               /*ko dc truyền trực tiếp url*/
            type: 'PUT',
            contentType: 'application/json',/*kiểu dlieu server trả về cho client */
            data: JSON.stringify(data),     /*parse từ javacript object > json */
            dataType: 'json',               /*kiểu dlieu của data từ client đẩy lên server*/
            success: function (result) {    /*cần có cả success & error để thông báo */
                window.location.href = "${newEditURL}?id="+result.id+"&message=update_success";
            },
            error: function (error) {
                window.location.href = "${newEditURL}?id="+$('#newId').val()+"&message=error_system";
            }
        });

    }
</script>
</body>
</html>
