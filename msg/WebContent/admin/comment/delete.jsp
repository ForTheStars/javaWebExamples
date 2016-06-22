<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.ICommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int id = Integer.parseInt(request.getParameter("id"));
	int msgId = Integer.parseInt(request.getParameter("msgId"));
	ICommentDao commentDao = DAOFactory.getComment();
	commentDao.delete(id);
	response.sendRedirect(request.getContextPath()+"/msg/show.jsp?id="+msgId);
%>