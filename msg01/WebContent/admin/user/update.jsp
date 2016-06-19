<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.util.ValidateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/inc/adminCheck.jsp" %>
<% 
	int id = Integer.parseInt(request.getParameter("id"));
	String username = request.getParameter("username");
	System.out.print(username);
	String password = request.getParameter("password");
	String nickname = request.getParameter("nickname");
	boolean validate = ValidateUtil.validateNull(request, new String[]{"password","nickname"});
	if(!validate){
		%>
		<<jsp:forward page="updateInput.jsp"></jsp:forward>
		<%
	}
	IUserDao userDao = DAOFactory.getUserDao();
	User user = userDao.load(id);
	user.setUsername(username);
	user.setNickname(nickname);
	user.setPassword(password);
	try{
		userDao.update(user);
		response.sendRedirect("list.jsp");
	} catch(MsgException e){
		%>
		<h2 style="color:red">发生错误：<%=e.getMessage() %></h2>
		<%
	}
%>