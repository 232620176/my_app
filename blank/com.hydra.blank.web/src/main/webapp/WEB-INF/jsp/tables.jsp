<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Query</title>
	</head>
	<body>
		<div width="100%;overflow:auto;">
			<table border="1" width="95%" cellpadding="2" cellspacing="1" style="table-layout:fixed;">
				<thead>
					<tr>
						<th width="40px">编号</th>
						<c:forEach items="${theader}" var="item">
							<th>${item}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tbody}" var="item" varStatus= "idx">
						<tr>
							<td>${idx.index + 1}</td>
							<c:forEach items="${theader}" var="cn">
								<td style="word-wrap:break-word;">${item[cn]}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>
