<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品库存添加</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="product.do" method="post">
<input type="hidden" name="method" value="addStock"/>
<input type="hidden" name="id" value="${c.id }"/>
<table width="600" class="thin-border" align="center">
	<Tr>
	<td>产品名称:</td><td>${c.name }</td>
	</Tr>
	<tr>
	<td>增加数量：</td><td><input type="text" name="number" value="${param.number }"/><span class="errorContainer">${errors.number}</span></td>
	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="添加"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>