<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/xhEditor/common.css" type="text/css" media="screen" />
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
/* 	
	$('#elm1').xheditor({tools:'full'});
	$('#elm2').xheditor({tools:'mfull'});
	$('#elm3').xheditor({tools:'simple'});
	$('#elm4').xheditor({tools:'mini'});
	$('#elm5').xheditor({tools:'Cut,Copy,Paste,Pastetext,|,Source,Fullscreen,About'});
	$('#elm6').xheditor({tools:'Cut,Copy,Paste,Pastetext,/,Source,Fullscreen,About'}); */
}
</script>
</head>
<body>
<form id="form" action="">
	<textarea rows="10" cols="50" name="content" id="content"></textarea><br/>
	<input type="submit" name="save" value="Submit" />
	<input type="reset" name="reset" value="Reset" />
</form>
</body>
</html>