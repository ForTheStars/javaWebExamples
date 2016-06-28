<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车列表</title>
</head>
<body>
<form method="post" action="orders.do">
<table style="margin-top:10px;" width="999" align="center" cellpadding="0" cellspacing="0" class="thin-border">
	<tr>
	<td>商品id</td><td>商品数量</td><td>商品价格</td>
	<td>操作</td>
	<c:if test="${empty shopCart||shopCart.isEmpty }">
	<tr><td colspan="4">购物车中没有任何商品，请<a href="product.do?method=list">选择商品</a></td></tr>
	</c:if>
	</tr>
	<c:forEach items="${shopCart.products}" var="sc">
	<c:set var="totalPrice" value="${totalPrice+sc.price }"></c:set>
	<tr>
	<td>${sc.product.name }</td>
	<td>${sc.number }</td><td>${sc.price }</td>
	<td>
	<a href="orders.do?method=clearProduct&pid=${sc.product.id }">清除</a>&nbsp;
	<a href="orders.do?method=productAddNumberInput&pid=${sc.pid }">添加数量</a>
	</td>
	</tr>
	</c:forEach>
	<tr><td colspan="4">选择收货地址</td></tr>
	<tr>
	<td colspan="4">
	<c:if test="${empty addresses }">
		<tr><td colspan="4">
		还没有任何的收获地址<a href="address.do?method=addInput&userId=${loginUser.id }">添加收货地址</a>
		</td></tr>
	</c:if>
	<c:forEach items="${addresses}" var="address" varStatus="vs">
	<c:if test="${vs.index eq 0 }">
		${address.name }[${address.phone }]<input type="radio" name="address" checked="checked" value="${address.id }"/><br/>
	</c:if>
	<c:if test="${vs.index ne 0 }">
		${address.name }[${address.phone }]<input type="radio" name="address" value="${address.id }"/><br/>
	</c:if>
	</c:forEach>	
	</td>
	</tr>
	<tr>
	<td colspan="4">
	<a href="orders.do?method=clearShopCart">清空购物车</a>&nbsp;
	<c:if test="${not empty shopCart && not shopCart.isEmpty && not empty addresses }">
	当前商品总价:${totalPrice }[<input type="submit" value="提交订单"/>]
	</c:if>
	<input type="hidden" name="price" value="${totalPrice }"/>
	<input type="hidden" name="method" value="addOrders"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>