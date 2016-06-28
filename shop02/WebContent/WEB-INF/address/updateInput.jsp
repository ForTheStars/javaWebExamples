<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地址修改</title>
</head>
<body>
<form action="address.do?method=update" method="post">
<input type="hidden" name="id" value="${address.id }"/>
<table width="700" class="thin-border" align="center">
	<tr>
	<td>修改地址用户:</td><td>${address.user.nickname }</td>
	</tr>
	<tr>
	<td>详细地址：</td><td><input type="text" name="name" value="${address.name }" size="60"/><span class="errorContainer">${errors.name }</span></td>
	</tr>
	<tr>
	<td>联系电话：</td><td><input type="text" name="phone"  value="${address.phone }"/><span class="errorContainer">${errors.phone }</span></td>
	</tr>
	<tr>
	<td>邮政编码：</td><td><input type="text" name="postcode"  value="${address.postcode }"/></td>
	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="修改地址"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>