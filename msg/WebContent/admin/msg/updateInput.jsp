<%@page import="info.model.Message"%>
<%@page import="info.dao.DAOFactory"%>
<%@page import="info.dao.IMessageDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新留言</title>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	IMessageDao msgDao = DAOFactory.getMessage();
	Message msg = msgDao.load(id);
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
		<jsp:param value="更新" name="op"/>
	</jsp:include>
	<form id="form" action="update.jsp" method="post">
		<input type="hidden" name="id" value="<%=msg.getId() %>"/>
		<table width="800" align="center" border="1">
			<tr>
				<td width="130">标题</td><td><input type="text" name="title" size="80" value="<%=msg.getTitle()%>"/></td>
			</tr>
			<tr>
				<td colspan="2">内容</td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea rows="20" cols="100" name="content" id="content">
					<%=msg.getContent() %>
					</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="save" value="更新留言" />
					<input type="reset" name="reset" value="重置" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>