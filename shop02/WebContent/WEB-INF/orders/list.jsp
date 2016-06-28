<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
</head>
<body>
<table style="margin-top:10px;" width="999" align="center" cellpadding="0" cellspacing="0" class="thin-border">
	<tr>
	<td colspan="5">
	<form action="orders.do">
	<input type="hidden" name="method" value="list"/>
	<select name="status">
	<option>选择状态</option>
	<option value="1" <c:if test="${param.status eq 1}">selected="selected"</c:if>>刚下订单</option>
	<option value="2" <c:if test="${param.status eq 2}">selected="selected"</c:if>>已付款</option>
	<option value="3" <c:if test="${param.status eq 3}">selected="selected"</c:if>>已发货</option>
	<option value="4" <c:if test="${param.status eq 4}">selected="selected"</c:if>>已确认 </option>
	</select>
	<input type="submit" value="筛选"/>
	</form>
	</td>
	</tr>
	<tr>
	<td>订单编号</td><td>购买日期</td><td>订单状态</td><td>订单用户</td>
	<td>操作</td>
	</tr>
	<c:forEach items="${orders.datas }" var="o">
	<tr>
	<td>${o.id }<a href="orders.do?method=show&id=${o.id }">查看订单</a></td>
	<td><fmt:formatDate value="${o.buyDate }" pattern="yyyy-MM-dd"/></td>
	<td>
	<c:if test="${o.status eq 1 }">下订单</c:if>
	<c:if test="${o.status eq 2 }">已付款</c:if>
	<c:if test="${o.status eq 3 }">已发货</c:if>
	<c:if test="${o.status eq 4 }">已确认</c:if>
	</td>
	<td>
	${o.user.nickname}<a href="orders.do?method=userList&id=${o.user.id }">查询用户订单</a>
	</td>
	<td>
	<c:if test="${o.status eq 1 }">
	<a href="orders.do?method=delete&id=${o.id }">删除订单</a>
	<c:if test="${o.user.id eq loginUser.id }">
	<a href="orders.do?method=pay&id=${o.id }">付款</a>
	</c:if>
	</c:if>
	<c:if test="${o.status eq 2 && loginUser.type eq 1}">
	<a href="orders.do?method=sendProduct&id=${o.id }">发货</a>
	</c:if>
	
	<c:if test="${o.status eq 3 && loginUser.id eq o.user.id}">
	<a href="orders.do?method=sendProduct&id=${o.id }">确认</a>
	</c:if>
	</td>
	</tr>
	</c:forEach>
	<tr>
	<td colspan="6">
		<jsp:include page="/inc/pager.jsp">
			<jsp:param value="orders.do" name="url"/>
			<jsp:param value="${orders.totalRecord }" name="items"/>
			<jsp:param value="status" name="params"/>
		</jsp:include>
	</td>
	</tr>
</table>
</body>
</html>