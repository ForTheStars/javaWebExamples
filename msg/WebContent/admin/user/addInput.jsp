<%@page import="info.util.ValidateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
</head>
<body>
<jsp:include page="inc.jsp">
	<jsp:param value="添加" name="op"/>
</jsp:include>
<br/>
<br/>
<%
	String initUsername = request.getParameter("username");
	String initNickname = request.getParameter("nickname");
	if(initNickname == null){
		initNickname = "";
	}
	if(initUsername == null){
		initUsername = "";
	}
%>
<form action="add.jsp" method="post" style="text-align:center">
	<table align="center">
		<tr align="left">
			<td>用户名称:</td>
			<td>
			<input type="text" name="username" value="<%=initUsername %>"/><%=ValidateUtil.showError(request, "username") %>
			</td>
		</tr>
		<br/>
		<tr align="left">
			<td>用户密码:</td>
			<td>
			<input type="text" name="password" /><%=ValidateUtil.showError(request, "password") %>
			</td>
		</tr>
		<br/>
		<tr align="left">
			<td>用户昵称:</td>
			<td>
			<input type="text" name="nickname" value="<%=initNickname %>"/><%=ValidateUtil.showError(request, "nickname") %>
			</td>
		</tr>
		<br/>
		<tr>
			<td colspan="2">
			<input type="submit" value="添加用户"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>