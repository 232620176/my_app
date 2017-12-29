<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>日志信息查询</title>
		<script type='text/javascript' src='./lib/jquery-1.4.js'></script>
		<script type='text/javascript' src='./lib/utils.js'></script>
		<script type="text/javascript">
			//绑定键盘按下事件
			$(document).keypress(function(e) {
				// 回车键事件
				if(e.which == 13) {
					autoFocus('keyWord', queryLog);
				}
			});
			
			function killProcess(){
				var keyWord = "clearGetLog";
				getLogInfo(keyWord);
			}
			
			function queryLog(){
				var keyWord = $('#keyWord').val();
				getLogInfo(keyWord);
			}
			
			function queryExactLog(){
				var keyWord = 'exact@' + $('#keyWord').val();
				getLogInfo(keyWord);
			}
			
			function getLogInfo(keyWord){
				clearMSG();
				var msg;
				$.ajax({
					type:'GET',
					url:'qryLogInfo',
					data:'keyWord=' + keyWord,
					cache:'false',
					success:function(data){
						msg = data;
						$('#res').html(msg);
					}
				});
			}
			
			function clearMSG(){
				$('#res').html(' ');
			}
		</script>
		<style type="text/css">
			xmp {
				white-space: pre-wrap;
				word-wrap: break-word;
			}
		</style>
	</head>
	<body>
		<input type='text' name='keyWord' id='keyWord' autofocus=true />
		<input type='button' onclick="queryLog();" value='执行' />
		<input type='button' onclick="queryExactLog();" value='直查' />
		<input type='button' onclick="killProcess();" value='杀进程' />
		<hr>
		<xmp id="res"></xmp>
	</body>
</html>
