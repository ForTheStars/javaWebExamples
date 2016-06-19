<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IUserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/inc/adminCheck.jsp" %>
<%
	try{
		User user = (User)session.getAttribute("loginUser");
		int id = Integer.parseInt(request.getParameter("id"));
		if(id == user.getId()){
			throw new MsgException("对不起！管理员非法操作");
		}
		IUserDao userDao = DAOFactory.getUserDao();
		userDao.delete(id);
		response.sendRedirect("list.jsp");
	} catch(MsgException e){
		%>
		<h2 style="color:red">发生错误：<%=e.getMessage() %></h2>
		<%
	}
%>