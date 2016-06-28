<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品列表</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<table style="margin-top:10px;" width="999" align="center" cellpadding="0" cellspacing="0" class="thin-border">
	<tr>
	<td>商品图片</td><td>商品名称</td><td>商品价格</td><td>商品的库存</td>
	<td>商品状态</td><td>操作</td>
	</tr>
	<c:forEach items="${products.datas }" var="p">
	<tr>
	<td><img src="<%=request.getContextPath()%>/img/${p.img}" width="130" height="130"/></td>
	<td><a href="product.do?method=show&id=${p.id }">${p.name }</a></td><td>${p.price }</td>
	<td>${p.stock }<c:if test="${not empty loginUser and loginUser.type eq 1 }"><a href="product.do?method=addStockInput&id=${p.id }">添加</a></c:if></td>
	<td>
		<c:if test="${p.status eq 1 }">可购买</c:if>
		<c:if test="${p.status eq -1 }"><span style="color:#832">已下架</span></c:if>
		<c:if test="${not empty loginUser and loginUser.type eq 1 }">
			<a href="product.do?method=changeStatus&id=${p.id }">变更</a>
		</c:if>
	</td>
	<td>
	<c:if test="${not empty loginUser and loginUser.type eq 1 }">
	<a href="product.do?method=updateInput&id=${p.id }">修改</a> <a href="product.do?method=delete&id=${p.id }">删除</a>
	</c:if>
	<c:if test="${p.status eq 1 }"><a href="orders.do?method=addToCart&id=${p.id}">加入购物车</a></c:if>
	&nbsp;
	</td>
	</tr>
	</c:forEach>
	<tr>
	<td colspan="6">
		<jsp:include page="/inc/pager.jsp">
			<jsp:param value="product.do" name="url"/>
			<jsp:param value="${products.totalRecord }" name="items"/>
			<jsp:param value="method,name,cid,status" name="params"/>
		</jsp:include>
	</td>
	</tr>
</table>
</body>
</html>