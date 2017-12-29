<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>卡号生成器</title>
		<script type='text/javascript' src='./lib/jquery-1.4.js'></script>
		<script type="text/javascript">
			$(document).ready(getCards);
			
			function getCards(){
				var sBankName = $('#bankList').val();
				var sCardType = $('#cardType').val() == 1 ? '储蓄卡' : '信用卡';
				var msg = sBankName + '&nbsp;' + sCardType + ': <br/>';
				$.ajax({
					type:'GET',
					url:'cardList',
					data:'bankName=' + sBankName + '&cardType=' + $('#cardType').val(),
					cache:'false',
					success:function(data){
						msg += data;
						$('#res').html(msg);
					}
				});
			}
		</script>
	</head>
	<body>
		${bankList}
		<form action="#">
			<select name="bankList" id="bankList" onchange="getCards();">
				<c:forEach items="${bankList}" var="item" varStatus="i">
					<option value="${item}">${item}</option>
				</c:forEach>
			</select>
			<select name="cardType" id="cardType" onchange="getCards();">
				<option value="0">信用卡</option>
				<option value="1">储蓄卡</option>
			</select>
			<input type='button' onclick="getCards();" value='生成' />
		</form>
		<hr>
		<div id="res"></div>
	</body>
</html>
