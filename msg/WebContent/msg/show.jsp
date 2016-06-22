<%@page import="info.util.ValidateUtil"%>
<%@page import="info.util.MsgUtil"%>
<%@page import="info.model.User"%>
<%@page import="info.model.Message"%>
<%@page import="info.model.Comment"%>
<%@page import="info.model.Pager"%>
<%@page import="info.dao.IUserDao"%>
<%@page import="info.dao.ICommentDao"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IMessageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>留言显示</title>
<%
	IMessageDao msgDao = DAOFactory.getMessage();
	ICommentDao commentDao = DAOFactory.getComment();
	IUserDao userDao = DAOFactory.getUserDao();
	int id = Integer.parseInt(request.getParameter("id"));
	Pager<Comment> comments = commentDao.list(id);
	Message msg = msgDao.load(id);
	User loginUser = (User)session.getAttribute("loginUser");
%>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/xheditor_lang/zh-cn.js"></script>
<script type="text/javascript">
$(pageInit);
function pageInit()
{
	$('#content').xheditor(
			{
				tools:'mfull',
				emots:{
					pidgin:{name:'Pidgin',width:22,height:25,line:8,list:{smile:'΢Ц',cute:'�ɰ�',wink:'գ��',laugh:'��Ц',victory:'ʤ��',sad:'����',cry:'����',angry:'����',shout:'����',curse:'����',devil:'ħ��',blush:'����',tongue:'����ͷ',envy:'��Ľ',cool:'ˣ��',kiss:'��',shocked:'����',sweat:'��',sick:'����',bye:'�ټ�',tired:'��',sleepy:'˯��',question:'����',rose:'õ��',gift:'����',coffee:'����',music:'����',soccer:'����',good:'��ͬ',bad:'����',love:'��',brokenheart:'����'}},
					ipb:{name:'IPB',width:20,height:25,line:8,list:{smile:'΢Ц',joyful:'����',laugh:'Ц',biglaugh:'��Ц',w00t:'����',wub:'��ϲ',depres:'��ɥ',sad:'����',cry:'����',angry:'����',devil:'ħ��',blush:'����',kiss:'��',surprised:'����',wondering:'�ɻ�',unsure:'��ȷ��',tongue:'����ͷ',cool:'ˣ��',blink:'գ��',whistling:'������',glare:'����',pinch:'��',sideways:'����',sleep:'˯��',sick:'����',ninja:'����',bandit:'ǿ��',police:'����',angel:'��ʹ',magician:'ħ��ʦ',alien:'������',heart:'�Ķ�'}},
				}
			});
	function submitForm(){$('#form').submit();}
}
</script>
</head>
<body>
<jsp:include page="/msg/inc.jsp">
	<jsp:param value="显示" name="op"/>
</jsp:include>
<table width="900" align="center" border="1">
	<tr>
	<td align="center"><h3><%=msg.getTitle() %></h3></td>
	</tr>
	<tr>
		<td>
		发表日期:<%=MsgUtil.formatDate(msg.getPostDate()) %>
		&nbsp;&nbsp;&nbsp;&nbsp;
		发布人:<%=userDao.load(msg.getUserId()).getNickname() %>
		&nbsp;&nbsp;
		<%
		if(ValidateUtil.checkAdminOrSelf(session, msg.getUserId())){
			%>
			<a href="<%=request.getContextPath()%>/admin/msg/updateInput.jsp?id=<%=msg.getId()%>">更新</a>
			&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/admin/msg/delete.jsp?id=<%=msg.getId()%>">删除</a>
			<%
		}
		%>
		</td>
	</tr>
	<tr>
		<td><%=msg.getContent() %></td>
	</tr>
</table>
<table  width="900" align="center" border="1">
	<%
	for(Comment com:comments.getDatas()){
		%>
		<tr>
			<td width="600px">
				<%=com.getContent() %>
			</td>
			<td>
				发表日期:<%=MsgUtil.formatDate(com.getPostDate()) %>
				&nbsp;
				发布人:<%=userDao.load(com.getUserId()).getNickname() %>
				&nbsp;
				<%
				if(ValidateUtil.checkAdminOrSelf(session, com.getUserId())){
					%>
					&nbsp;<a href="<%=request.getContextPath()%>/admin/comment/delete.jsp?id=<%=com.getId()%>&msgId=<%=msg.getId()%>">删除</a>
					<%
				}
				%>
			</td>
		</tr>
		<%
	}
	%>
	<tr>
		<td colspan="2">
			<jsp:include page="/inc/pager.jsp">
				<jsp:param value="<%=comments.getTotalRecord() %>" name="items"/>
				<jsp:param value="id" name="params"/>
			</jsp:include>
		</td>
	</tr>
</table>
	<%
	if(loginUser != null){
		%>
		<form id="form" action="<%=request.getContextPath() %>/admin/comment/add.jsp"  method="get">
			<input type="hidden" name="msgId" value="<%=msg.getId() %>">
			<table width="900" align="center" border="1">
				<tr>
					<td align="center">添加回复</td>
				</tr>
				<tr>
					<td colspan="2">
					<textarea rows="10" cols="107" id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" name="save" value="添加留言" />&nbsp;
						<input type="reset" name="reset" value="重置" />
					</td>
				</tr>
			</table>
		</form>
		<%
	}
	%>
</body>
</html>