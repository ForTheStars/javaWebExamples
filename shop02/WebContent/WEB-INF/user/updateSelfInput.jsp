<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息修改</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="user.do?method=updateSelf" method="post">
<input type="hidden" name="id" value="${user.id }"/>
<table width="600" class="thin-border" align="center">
	<tr>
	<td>用户名：</td><td>${user.username}</td>
	</tr>
	<tr>
	<td>用户密码：</td><td><input type="password" name="password" value="${user.password }"/><span class="errorContainer">${errors.password }</span></td>
	</tr>
	<tr>
	<td>用户昵称：</td><td><input type="text" name="nickname"  value="${user.nickname }"/></td>
	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="用户修改"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>