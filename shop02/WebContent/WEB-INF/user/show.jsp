<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.nickname }的用户信息</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<table width="800" class="thin-border" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>用户名：${user.username }</td>
	</tr>
	<tr>
		<td>用户昵称：${user.nickname }</td>
	</tr>
	<tr>
		<td>
		</td>
	</tr>
</table>
</body>
</html>