<%@page import="info.dao.DAOFactory"%>
<%@page import="info.model.MsgException"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.model.User"%>
<%@page import="info.util.ValidateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String nickname = request.getParameter("nickname");
	boolean validate = ValidateUtil.validateNull(request, new String[]{"username","password","nickname"});
	if(!validate){
		%>
		<<jsp:forward page="addInput.jsp"/>
		<%
	}
	User user = new User();
	user.setNickname(nickname);
	user.setPassword(password);
	user.setUsername(username);
	user.setType(0);
	user.setStatus(1);
	IUserDao userDao = DAOFactory.getUserDao();
	try{
		userDao.add(user);
		response.sendRedirect("list.jsp");
		return;
	} catch(MsgException e) {
		%>
		<h2 style="color:red">对不起，添加用户失败！发生错误：<%=e.getMessage() %></h2>
		<%
	}
%>