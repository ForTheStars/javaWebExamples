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
	int pageIndex = pages.getPageIndex();
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
		<td colspan="4" style="font-size:14px">
			<a href="list.jsp?pageIndex=1">首页</a>
			<%
				if(pages.getPageIndex()>1) {
			%>
				<a href="list.jsp?pageIndex=<%=(pageIndex-1)%>">上一页</a>
			<%		
				}
			%>
			<%
			int totalPage = pages.getTotalPage();
			for(int i=1;i<=totalPage;i++) {
				if(i==pageIndex) {
			%>
				<%=i %>
			<%		
				} else {
			%>
				<a href="list.jsp?pageIndex=<%=i%>">[<%=i %>]</a>
			<%		
				}
			}
			%> 
			<%
			if(pageIndex<totalPage) {
			%>
				<a href="list.jsp?pageIndex=<%=(pageIndex+1)%>">下一页</a>
			<%	
			}
			%>
			<a href="list.jsp?pageIndex=<%=totalPage%>">尾页</a>
		</td>
	</tr>
</table>
</body>
</html>