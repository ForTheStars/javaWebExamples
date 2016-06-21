<%@page import="info.model.MsgException"%>
<%@page import="info.model.User"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.util.ValidateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	int id = Integer.parseInt(request.getParameter("id"));
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