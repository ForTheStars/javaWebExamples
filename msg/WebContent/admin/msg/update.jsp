<%@page import="info.model.Message"%>
<%@page import="info.dao.MessageDao"%>
<%@page import="info.dao.IMessageDao"%>
<%@page import="info.dao.DAOFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int id = Integer.parseInt(request.getParameter("id"));
	IMessageDao msgDao = DAOFactory.getMessage();
	Message msg = msgDao.load(id);
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	msg.setContent(content);
	msg.setTitle(title);
	msgDao.update(msg);
	response.sendRedirect(request.getContextPath()+"/msg/show.jsp?id="+msg.getId());
%>