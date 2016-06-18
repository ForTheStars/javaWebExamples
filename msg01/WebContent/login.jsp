<%@page import="info.model.User"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.model.MsgException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登入</title>
</head>
<body>
<%
	try{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		IUserDao userDao = DAOFactory.getUserDao();
		User user = userDao.login(username, password);
		session.setAttribute("loginUser", user);
		response.sendRedirect(request.getContextPath()+"/admin/user/list.jsp");
	} catch(MsgException e){
		%>
			<h2 style="color:red">发生错误：<%=e.getMessage() %></h2>
		<%
	}
%>
</body>
</html>