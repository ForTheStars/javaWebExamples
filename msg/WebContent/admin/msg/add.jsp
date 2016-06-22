<%@page import="java.util.Date"%>
<%@page import="info.model.Message"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IMessageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
try{
	IMessageDao msgDao = DAOFactory.getMessage();
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	int userId = Integer.parseInt(request.getParameter("userId"));
	Message msg = new Message();
	msg.setContent(content);
	msg.setTitle(title);
	msg.setPostDate(new Date());
	msgDao.add(msg, userId);
	response.sendRedirect(request.getContextPath()+"/msg/list.jsp");
} catch(Exception e){
	%>
	<h1 style="color:red">发生异常:<%=e.getMessage() %></h1>
	<%
}
%>