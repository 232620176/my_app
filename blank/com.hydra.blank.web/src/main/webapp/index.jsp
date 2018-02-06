<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页</title>
		<script type='text/javascript' src='./lib/jquery-1.4.js'></script>
		<script type='text/javascript' src='./lib/utils.js'></script>
		<script type="text/javascript">
			//绑定键盘按下事件
			$(document).keypress(function(e) {
				// 回车键事件
				if(e.which == 13) {
					var txt = $('#source').val();
					if(txt || txt != 0){
						processStr('upper');
					}else{
						$('#source').focus();
					}
				}
			});
			
			function sign(url){
				var txt = processURIStr("text");
				var key = processURIStr("key");
				url += "/" + txt;
				if(null !== key && key !== undefined && key !== ''){
					url += "/" + key;
				}
				query(url);
			}
			
			function processURIStr(key){
				var txt = $("#" + key).val();
				return escape(txt);
			}
			
			function queryParam(url){
				url += "/" + $("#paramName").val();
				query(url);
			}
			
			function query(url){
				$.ajax({
					type:'GET',
					url:url,
					cache:'false',
					success:function(data){
						addMSG(data);
					}
				});
			}
			
			function processStr(typ, flg){
				var msg = '';
				$.ajax({
					type:'GET',
					url:'str',
					data:'type=' + typ + '&source=' + $('#source').val() 
						+ '&pattern=' + processURIStr('pattern') + '&target=' + processURIStr('target'),
					cache:'false',
					success:function(data){
						msg += data.replace(/[\r\n]/g, '');
						addMSG(msg);
						if(flg){
							copyToClipBoard(msg);
						}
					}
				});
			}
			
			function addMSG(data){
				var res = $('#res').html();
				$('#res').html(data + "<br />" + res);
			}
			
			function clearMSG(){
				$('#res').html('');
			}
		</script>
	</head>
	<body>
		<form action="#">
			<table width="100%">
				<tr>
					<td width='20%'>
						<li><a href="bank" target="_blank">生成卡</a></li>
					</td>
					<td width='40%'>
						<input type='text' id='source' name='source' placeholder='source' autofocus=true />
						<input type='button' onclick="processStr('upper');" value='大写' />
						<input type='button' onclick="processStr('lower');" value='小写' />
						<input type='button' onclick="processStr('upper', '1');" value='大写并复制' />
						<input type='button' onclick="processStr('lower', '1');" value='小写并复制' />
					</td>
					<td>
						<input id="paramName" alt="参数名" placeholder="参数名" />
						<input type="button" onclick="queryParam('param/query')" value="参数查询"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<li><a href="logInfo.jsp" target="_blank">查日志</a></li>
					</td>
					<td>
						<input type='text' id='pattern' name='pattern' placeholder='pattern' />
						<input type='text' id='target' name='target' placeholder='target' />
						<input type='button' onclick="processStr('process');" value='替换' /> *支持正则表达式
					</td>
					<td>
						<input id="text" alt="文本" placeholder="文本" />
						<input id="key" alt="秘钥" placeholder="秘钥" />
						<input type="button" onclick="sign('sign')" value="MD5"/>
					</td>
				</tr>
				<tr>
					<td>
						<li><a href="query.jsp" target="_blank">表查询</a></li>
					</td>
					<td colspan="2">
						<li><a href="anslog.html" target="_blank">日志分析</a></li>
					</td>
				</tr>
			</table>
		</form>
		<hr />
		<input type="button" onclick="clearMSG()" value="clear"/><br />
		<div id="res"></div>
	</body>
</html>
