<%--
  Created by IntelliJ IDEA.
  User: dongn
  Date: 9/9/2020
  Time: 9:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
</head>
<body>

<div class="row" style="clear: both">
    <div class="col-lg-12 grid-margin stretch-card">
        <div class="card">
            <div class="card-body">
                <p class="card-title" style="margin-bottom: 20px">Danh sách sản phẩm</p>
                <a href="${pageContext.request.contextPath}/home?action=create"
                   class="nav-link" style="margin-bottom: 10px"><i class="icon-plus"></i> Thêm mới</a>
                <form class="search-form d-none d-md-block"
                      action="${pageContext.request.contextPath}/home?action=search" method="post">
                    <input type="search" class="form-control" placeholder="Tìm ở đây" title="Tìm ở đây" name="search-input">
                </form>
                <table class="table table-striped ">
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Màu sắc</th>
                        <th>Mô tả</th>
                        <th>Nhãn hàng</th>
                        <th colspan="2">Thao tác</th>
                    </tr>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td><c:out value="${product.getName()}"/></td>
                            <td><c:out value="${product.getPrice()}"/></td>
                            <td><c:out value="${product.getQuantity()}"/></td>
                            <td><c:out value="${product.getColor()}"/></td>
                            <td><c:out value="${product.getDescription()}"/></td>
                            <td><c:out value="${product.getCategory().getName()}"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/home?action=edit&id=${product.getProductId()}">Sửa</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/home?action=delete&id=${product.getProductId()}">Xoá</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    function confirmDelete() {
        if (confirm("Bạn có chắc chắn muốn xoá ?")) {
            document.location.href = '${pageContext.request.contextPath}/home?action=delete&id=${product.getProductId()}'
        }
    }
</script>
</body>
</html>