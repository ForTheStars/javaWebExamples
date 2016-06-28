<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类别列表</title>
<style>
span.category {
	color:#fff;
	margin:10px;
}
#container {
	width:900px;
	border:1px solid;
}
a.category_link:link {
	text-decoration: none;
	background:#872;
	padding:5px;
	font-size:14px;
	margin:10px;
	float:left;
}
a.category_link:hover {
	background:#e22;
}
</style>
</head>
<body>
<jsp:include page="inc.jsp"></jsp:include>
<p style="align:center">
<form action="category.do">
<input type="hidden" name="method" value="list"/>
输入筛选条件:<input type="text" name="name" value="${param.name }"/><input type="submit" value="搜索"/><br/>
</form>
<c:if test="${empty cs }">还没有任何商品分类</c:if>
<c:if test="${not empty cs }">
<c:forEach items="${cs }" var="category">
	<a href="category.do?method=show&id=${category.id }" class="category_link"><span class="category">${category.name }</span></a>
</c:forEach>
</c:if>
</p>
</body>
</html>