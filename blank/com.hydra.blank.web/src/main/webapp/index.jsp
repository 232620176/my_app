<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页</title>
		<script type='text/javascript' src='./lib/jquery-1.4.js'></script>
		<script type="text/javascript">
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
			console.log(url);
			$.ajax({
				type:'GET',
				url:url,
				cache:'false',
				success:function(data){
					var res = $('#res').html();
					console.log(res);
					$('#res').html(data + "<br />" + res);
				}
			});
		}
		function clear(){
			$('#res').html('');
		}
		</script>
	</head>
	<body>
		<a href="query.jsp" target="_blank">表查询</a><br />
		<input id="paramName" alt="参数名" placeholder="参数名" /> <input type="button" onclick="queryParam('param/query')" value="参数查询"/><br />
		<input id="text" alt="文本" placeholder="文本" /> <input id="key" alt="秘钥" placeholder="秘钥" /> <input type="button" onclick="sign('sign')" value="MD5"/><br />
		<hr />
		<input type="button" onclick="clear()" value="clear"/><br />
		<div id="res"></div>
	</body>
</html>
