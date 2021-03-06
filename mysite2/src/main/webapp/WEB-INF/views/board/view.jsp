<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% pageContext.setAttribute("replaceChar", "\n"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<c:forEach items="${vo }" var="vo" varStatus="status">
					<c:set var = "no" value="${vo.no }"/>
					<c:set var = "userNo" value="${vo.userNo }"/>
					<c:set var = "groupNo" value="${vo.gNo }"/>
					<c:set var = "orderNo" value="${vo.oNo }"/>
					<c:set var = "depth" value="${vo.depth }"/>
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.contents, replaceChar, "<br/>")}
							</div>
						</td>
					</tr>
					</c:forEach>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board?page=${param.page }">글목록</a>
					<c:if test="${!empty authUser.no }">
						<a href="${pageContext.servletContext.contextPath }/board?a=writeform&gNo=${groupNo}&oNo=${orderNo}&depth=${depth}&page=${param.page }">답글쓰기</a>
					</c:if>
					<c:if test="${authUser.no eq userNo }">
						<a href="${pageContext.servletContext.contextPath }/board?a=modifyform&no=${no}&page=${param.page }">글수정</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>