<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类别修改</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="category.do?method=update" method="post">
<input type="hidden" name="id" value="${category.id }">
<table width="600" class="thin-border" align="center">
	<tr>
	<td>商品名称：</td><td><input type="text" name="name" value="${category.name }"/><span class="errorContainer">${errors.name}</span></td>

	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="修改"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>