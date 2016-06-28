<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类别查询</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<table width="600" class="thin-border" align="center">
	<tr>
	<td>商品名称：${category.name }</td>

	</tr>
	<tr>
	<td>
	<a href="category.do?method=updateInput&id=${category.id }">修改</a>
	&nbsp;
	<a href="category.do?method=delete&id=${category.id }">删除</a>
	</td>
	</tr>
</table>
</body>
</html>