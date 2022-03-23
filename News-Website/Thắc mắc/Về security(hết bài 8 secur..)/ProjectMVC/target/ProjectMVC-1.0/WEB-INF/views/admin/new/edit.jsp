<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="APIurl" value="/api-admin-new"/>
<c:url var="NEWurl" value="/admin-new"/>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
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
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-${alert}">
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form id="formSubmit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Thể loại</label>
                            <div class="col-sm-9">
                                <select class="form-control" id="categoryCode" name="categoryCode">
                                    <c:if test="${empty model.categoryCode}">
                                        <option value="">Chọn thể loại của bài viết</option>
                                        <c:forEach items="${categories}" var="item">
                                            <option value="${item.code}">${item.name}</option>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${not empty model.categoryCode}">
                                        <c:forEach items="${categories}" var="item">
                                            <option value="${item.code}"
                                                    <c:if test="${item.code == model.categoryCode}">selected="selected"</c:if> >
                                                    ${item.name}
                                            </option>
                                        </c:forEach>
                                    </c:if>

                                </select>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Tiêu đề</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="title" name="title" value="${model.title}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Hình đại diện</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="thumbnail" name="thumbnail"
                                       value="${model.thumbnail}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Mô tả ngắn</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="shortDescription" name="shortDescription"
                                       value="${model.shortDescription}"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">Nội dung</label>
                            <div class="col-sm-9">
                                <textarea rows="" cols="" id="content" name="content"
                                          style="width: 775px; height: 130px">${model.content}</textarea>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <input type="hidden" id="id" name="id" value="${model.id}"/>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${not empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Cập nhật bài viết" id="btnAddOrUpdateNew"/>
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Thêm bài viết" id="btnAddOrUpdateNew"/>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var editor = "";
    $(document).ready(function () {
        editor = CKEDITOR.replace('content');
    });

    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        /*
        -thay vì set value vào từng id thủ công
        var title = $('#title').val();
        var shortDescription = $('#shortDescription').val();
        ...
        -thì ta dùng array để lưu
        field là mảng gồm id,value
        index là vị trí
        khi đó hàm nó sẽ tìm name(ko phải id ) & value trong thẻ form nha
        */
        var data = {};
        var formData = $('#formSubmit').serializeArray();
        $.each(formData, function (index, field) {
            /*console.log(field);*/
            data[field.name] = field.value;
        });
        data["content"] = editor.getData();
        /*console.log(JSON.stringify(data));*/
        var id = $('#id').val();
        if (id == "") {
            addNew(data);
        } else {
            updateNew(data);
        }

    });

    function addNew(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
                window.location.href = "${NEWurl}?type=edit&id=" + result.id + "&message=insert_success";
            },
            error: function () {
                window.location.href = "${NEWurl}?type=list&maxPageItem=2&page=1&message=error_system";
            }
        });

    };

    function updateNew(data) {
        $.ajax({
            url: '${APIurl}',               /*ko dc truyền trực tiếp url*/
            type: 'PUT',
            contentType: 'application/json',/*kiểu dlieu server trả về cho client */
            data: JSON.stringify(data),     /*parse từ javacript object > json */
            dataType: 'json',               /*kiểu dlieu của data từ client đẩy lên server*/
            success: function (result) {    /*cần có cả success & error để thông báo */
                window.location.href = "${NEWurl}?type=edit&id=" + result.id + "&message=update_success";
            },
            error: function (error) {
                window.location.href = "${NEWurl}?type=list&maxPageItem=2&page=1&message=error_system";
            }
        });

    }
</script>
</body>
</html>
