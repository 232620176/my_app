<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Query</title>
		<script type='text/javascript' src='./lib/jquery-1.4.js'></script>
		<script type="text/javascript">
		function query(){
			var url = "tableName=" + $('#tableName').val();
			url += "&col=" + $('#col').val();
			url += "&k0=" + $('#k0').val();
			url += "&v0=" + $('#v0').val();
			url += "&k1=" + $('#k1').val();
			url += "&v1=" + $('#v1').val();
			url += "&k2=" + $('#k2').val();
			url += "&v2=" + $('#v2').val();
			url += "&lk0=" + $('#lk0').val();
			url += "&lv0=" + $('#lv0').val();
			url += "&lk1=" + $('#lk1').val();
			url += "&lv1=" + $('#lv1').val();
			url += "&lk2=" + $('#lk2').val();
			url += "&lv2=" + $('#lv2').val();
			$.ajax({
				type:'GET',
				url:'query',
				data:url,
				cache:'false',
				success:function(data){
					$('#res').html(data);
				}
			});
		}
		</script>
	</head>
	<body>
		表：<input id="tableName" name="tableName" value="parameters" /> <input type='button' onclick="query();" value='查询' /><br />
		列：<input id="col" name="col" /><br />
		键：<input id="k0" name="k0" />值：<input id="v0" name="v0" /><br />
		键：<input id="k1" name="k1" />值：<input id="v1" name="v1" /><br />
		键：<input id="k2" name="k2" />值：<input id="v2" name="v2" /><br />
		键：<input id="lk0" name="lk0" />值：<input id="lv0" name="lv0" /><br />
		键：<input id="lk1" name="lk1" />值：<input id="lv1" name="lv1" /><br />
		键：<input id="lk2" name="lk2" />值：<input id="lv2" name="lv2" /><br />
		<hr />
		<div id="res"></div>
	</body>
</html>
