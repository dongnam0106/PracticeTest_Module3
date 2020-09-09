<%--
  Created by IntelliJ IDEA.
  User: dongn
  Date: 9/9/2020
  Time: 9:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm mới sản phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
</head>
<body>

<div class="row" style="clear: both">
    <div class="col-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <p class="card-title" style="margin-bottom: 20px; float: right">Thêm mới sản phẩm</p>
                <form class="forms-sample" method="post" style="clear: both">
                    <div class="form-group">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id"
                               value="${product.getProductId()}"
                               name="productId" readonly>
                    </div>
                    <div class="form-group">
                        <label for="name">Tên sản phẩm</label>
                        <input type="text" class="form-control" id="name"
                        <c:choose>
                        <c:when test="${product.getName() == null}">
                               placeholder="Tên sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getName()}"
                        </c:otherwise>
                        </c:choose>
                               name="productName">
                    </div>
                    <div class="form-group">
                        <label for="price">Giá</label>
                        <input type="text" class="form-control" id="price"
                        <c:choose>
                        <c:when test="${product.getPrice() == null}">
                               placeholder="Giá sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getPrice()}"
                        </c:otherwise>
                        </c:choose>
                               name="productPrice">
                    </div>
                    <div class="form-group">
                        <label for="quantity">Số lượng</label>
                        <input type="text" class="form-control" id="quantity"
                        <c:choose>
                        <c:when test="${product.getPrice() == null}">
                               placeholder="Số lượng sản phẩm không được để trống"
                        </c:when>
                        <c:otherwise>
                               value="${product.getQuantity()}"
                        </c:otherwise>
                        </c:choose>
                               name="productQuantity">
                    </div>
                    <div class="form-group">
                        <label for="color">Màu sắc</label>
                        <input type="text" class="form-control" id="color"
                               value="${product.getColor()}"
                               name="productColor">
                    </div>
                    <div class="form-group">
                        <label for="category">Hãng</label>
                        <select class="form-control" id="category" name="categoryId">
                            <c:forEach items="${categoryList}" var="category">
                                <option value="${category.getId()}"
                                        <c:if test="${product.getCategory().getId() == category.getId()}">
                                            selected
                                        </c:if>
                                >${category.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <textarea class="form-control" id="description" rows="4"
                                  value="${product.getDescription()}"
                                  name="productDes">${product.getDescription()}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary mr-2">
                        <c:choose>
                            <c:when test="${action == 'create'}">
                                Thêm mới
                            </c:when>
                        </c:choose>
                    </button>
                    <button class="btn btn-light" type="reset" href="/home?">Huỷ bỏ</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
