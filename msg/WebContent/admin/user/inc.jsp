<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/inc/top.jsp" %>
<h2 align="center">
	用户<%=request.getParameter("op") %>功能
</h2>
<a href="addInput.jsp">添加用户</a>&nbsp;
<a href="list.jsp">用户列表</a>