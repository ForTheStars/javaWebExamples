<%@page import="info.util.MsgUtil"%>
<%@page import="info.model.Message"%>
<%@page import="info.model.Pager"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IMessageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>留言列表</title>
<%
	IMessageDao msgDao = DAOFactory.getMessage();
	IUserDao userDao = DAOFactory.getUserDao();
	Pager<Message> pages = msgDao.list();
	int totalRecord = pages.getTotalRecord();
%>
</head>
<body>
<jsp:include page="/msg/inc.jsp">
	<jsp:param value="列表" name="op"/>
</jsp:include>
<table align="center" width="900" border="1">
	<tr>
		<td>标题</td><td>发表时间</td><td>发布人</td><td>评论数量</td>
	</tr>
	<%
	for(Message msg:pages.getDatas()){
		%>
		<tr>
		<td><a href="show.jsp?id=<%=msg.getId()%>"><%=msg.getTitle() %></a></td>
		<td><%=MsgUtil.formatDate(msg.getPostDate())%></td>
		<td><%=userDao.load(msg.getUserId()).getNickname() %></td>
		<td><%=msgDao.getCommentCount(msg.getId()) %></td>
		</tr>
		<%
	}
	%>
	<tr>
		<td colspan="4">
			<jsp:include page="/inc/pager.jsp">
				<jsp:param value="<%=totalRecord %>" name="items"/>
			</jsp:include>
		</td>
	</tr>
</table>
</body>
</html>