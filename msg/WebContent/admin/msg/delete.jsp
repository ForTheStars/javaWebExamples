<%@page import="info.util.ValidateUtil"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IMessageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int id = Integer.parseInt(request.getParameter("id"));
	IMessageDao msgDao = DAOFactory.getMessage();
	boolean flag = ValidateUtil.checkAdminOrSelf(session, msgDao.load(id).getUserId());
	if(!flag){
		%>
		<h2 style="color:red">你没有权限删除该留言</h2>
		<%
	}else{
		msgDao.delete(id);
		response.sendRedirect(request.getContextPath()+"/msg/list.jsp");
	}
%>