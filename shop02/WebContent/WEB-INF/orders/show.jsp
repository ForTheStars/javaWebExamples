<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单查询</title>
</head>
<body>
<table width="999" class="thin-border" align="center" style="margin-top:20px">
	<tr>
	<td>订单编号:${o.id }&nbsp;
	订单状态:
	<c:if test="${o.status eq 1 }">下订单</c:if>
	<c:if test="${o.status eq 2 }">已付款[<fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd"/>]</c:if>
	<c:if test="${o.status eq 3 }">已发货</c:if>
	<c:if test="${o.status eq 4 }">已确认[<fmt:formatDate value="${o.confirmDate}" pattern="yyyy-MM-dd"/>]</c:if>
	&nbsp;
	购买日期:<fmt:formatDate value="${o.buyDate}" pattern="yyyy-MM-dd"/>
	</td>
	</tr>
	<tr>
	<td>
	购买人:${o.user.nickname }
	&nbsp;发货地址${o.address.name }(${o.address.postcode })[${o.address.phone }]
	</td>
	</tr>
	<tr>
	<td>
	购买商品,总价${o.price}
	</td>
	</tr>
	<tr>
	<td>
	<table width="999" class="thin.boder">
	<tr>
	<td>商品图片</td><td>名称</td><td>数量</td><td>价格</td>
	</tr>
	<c:forEach items="${o.products }" var="p">
	<tr>
	<td><img src="<%=request.getContextPath() %>/img/${p.product.img}" width="100" height="100"/></td>
	<td>${p.product.name}</td>
	<td>${p.number}</td>
	<td>${p.price}</td>
	</tr>
	</c:forEach>
	</table>
	</td>
	</tr>
</table>

</body>
</html>