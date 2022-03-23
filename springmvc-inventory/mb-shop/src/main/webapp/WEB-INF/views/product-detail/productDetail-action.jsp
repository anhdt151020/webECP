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
                        <form:form modelAttribute="mProductDetail" cssClass="form-horizontal form-label-left"
                                   servletRelativeAction="/product-detail/save" method="POST"
                                   enctype="multipart/form-data">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="productId">ProductInfo <span class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <form:select path="productId" cssClass="form-control">
                                                <form:options items="${mapProductInfo}"/>
                                            </form:select>
                                            <div class="has-error">
                                                <form:errors path="productId" cssClass="help-block"></form:errors>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="productInfoByProductId.code" disabled="true"
                                                        cssClass="form-control col-md-7 col-xs-12"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="receiptcode">Receipt Code <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <form:select path="receiptcode" cssClass="form-control">
                                                <form:options items="${mapReceiptCode}"/>
                                            </form:select>
                                            <div class="has-error">
                                                <form:errors path="receiptcode" cssClass="help-block"></form:errors>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="receiptcode" disabled="true"
                                                        cssClass="form-control col-md-7 col-xs-12"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="issuecode">Issue Code <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <c:choose>
                                        <c:when test="${!viewOnly}">
                                            <form:select path="issuecode" cssClass="form-control">
                                                <form:options items="${mapIssueCode}"/>
                                            </form:select>
                                            <div class="has-error">
                                                <form:errors path="issuecode" cssClass="help-block"></form:errors>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="issuecode" disabled="true"
                                                        cssClass="form-control col-md-7 col-xs-12"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="serial">Serial <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="serial" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="serial" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="name" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="name" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="description"
                                       class="control-label col-md-3 col-sm-3 col-xs-12">Description</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:textarea path="description" cssClass="form-control col-md-7 col-xs-12"
                                                   disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="description" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="battery">Battery <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="battery" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="battery" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="camera">Camera <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="camera" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="camera" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="os">Operation System <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="os" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="os" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="wireless">Wireless <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="wireless" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="wireless" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="memory">Memory <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="memory" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="memory" cssClass="help-block"></form:errors>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="size">Size <span
                                        class="required">*</span>
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <form:input path="size" cssClass="form-control col-md-7 col-xs-12"
                                                disabled="${viewOnly}"/>
                                    <div class="has-error">
                                        <form:errors path="size" cssClass="help-block"></form:errors>
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
    $(document).ready(
        function () {
            $('#product-detaillistId').addClass('current-page').siblings()
                .removeClass('current-page');
            var parent = $('#product-detaillistId').parents('li');
            parent.addClass('active').siblings().removeClass('active');
            $('#product-detaillistId').parents().show();
        });

    function cancel() {
        window.location.href = '<c:url value="/product-detail/list"/>'
    }
</script>