<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品查询</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<table width="666" class="thin-border" align="center" style="margin-top:20px">
	<tr>
	<td colspan="2"><img src="<%=request.getContextPath()%>/img/${product.img}"/></td>
	</tr>
	<tr>
	<td>商品名称：</td><td>${product.name }</td>
	</tr>
	<tr>
	<td>商品价格：</td><td>${product.price }</td>
	</tr>
	<tr>
	<td>商品库存：</td><td>${product.stock }</td>
	</tr>
	<tr>
	<td colspan="2">
		<c:if test="${product.status eq 1 }"><a href="">加入购物车</a></c:if>
		<c:if test="${product.status eq -1 }">该商品已经下架</c:if>
	</td>
	</tr>
	<tr>
	<td colspan="2">商品介绍：</td>
	</tr>
	<tr>
	<td colspan="2">${product.intro}</td>
	</tr>
</table>
</body>
</html>