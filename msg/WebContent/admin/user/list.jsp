<%@page import="java.util.List"%>
<%@page import="info.model.User"%>
<%@page import="info.model.Pager"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IUserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%
	String  search = request.getParameter("search");
	if(search == null){
		search = "";
	}
	IUserDao userDao = DAOFactory.getUserDao();
	Pager<User> pages = userDao.list(search);
	int pageIndex = pages.getPageIndex();
	List<User> users = pages.getDatas();
	User loginUser = (User)session.getAttribute("loginUser");
%>
</head>
<body>
<jsp:include page="inc.jsp">
	<jsp:param value="列表" name="op"/>
</jsp:include>

<table align="center" border="1" width="1000">
	<tr>
		<td colspan="7">
			<form action="list.jsp" style="text-align:right">
				输入用户名或者昵称：<input type="text" name="search" value="<%=search %>"/>
				<input type="submit" value="查询"/>
			</form>
		</td>
	</tr>
	<tr>
	<td>用户标识</td><td>用户名</td><td>用户密码</td><td>用户昵称</td>
	<td>用户类型</td><td>用户状态</td>
	<td>操作</td>
	</tr>
	<%
	for(User u:users){
		%>
		<tr>
		<td><%=u.getId() %></td>
		<td><%=u.getUsername() %></td>
		<td><%=u.getPassword() %></td>
		<td><%=u.getNickname() %></td>
		<td>
		<%
		if(u.getType() == 0){
			%>
			普通用户
			<%
			if(loginUser.getType() == 1){
				%>
				<a href="setType.jsp?id=<%=u.getId() %>">设置管理员</a>
				<%
			}
		}else{
			%>
			管理员
			<%
			if(loginUser.getType() == 1){
				%>
				<a href="setType.jsp?id=<%=u.getId() %>">取消管理员</a>
				<%
			}
		}
		%>
		</td>
		<td>
		<%
		if(u.getStatus() == 1){
			%>
			启用
			<%
			if(loginUser.getType() == 1){
				%>
				<a href="setStatus.jsp?id=<%=u.getId() %>">停用</a>
				<%
			}
		}else{
			%>
			停用
			<%
			if(loginUser.getType() == 1){
				%>
				<a href="setStatus.jsp?id=<%=u.getId() %>">启用</a>
				<%
			}
		}
		%>
		</td>
		<td>
		<%
		if(loginUser.getType() == 1){
			%>
			<a href="delete.jsp?id=<%=u.getId()%>">删除</a>&nbsp;<a href="updateInput.jsp?id=<%=u.getId()%>">更新</a>&nbsp;</td>
			<%
		}else if(loginUser.getId() == u.getId()){
			%>
			<a href="updateSelfInput.jsp?id=<%=u.getId()%>">更新</a>&nbsp;</td>
			<%
		}
		%>
		</tr>
		<%
	}
	%>
	<tr>
		<td colspan="7" style="font-size:14px">
			<a href="list.jsp?pageIndex=1&search=<%=search%>">首页</a>
			<%
				if(pages.getPageIndex()>1) {
			%>
				<a href="list.jsp?pageIndex=<%=(pageIndex-1)%>&search=<%=search%>">上一页</a>
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
				<a href="list.jsp?pageIndex=<%=i%>&search=<%=search%>">[<%=i %>]</a>
			<%		
				}
			}
			%> 
			<%
			if(pageIndex<totalPage) {
			%>
				<a href="list.jsp?pageIndex=<%=(pageIndex+1)%>&search=<%=search%>">下一页</a>
			<%	
			}
			%>
			<a href="list.jsp?pageIndex=<%=totalPage%>&search=<%=search%>">尾页</a>
		</td>
	</tr>
</table>
</body>
</html>