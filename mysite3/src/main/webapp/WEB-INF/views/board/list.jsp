<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<div id="board">
				<form id="search_form" action="board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
						<table class="tbl-ex">
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>&nbsp;</th>
							</tr>	
							<c:set var="count" value='${fn:length(list) }' ></c:set>
							<c:forEach items="${list }" var="vo" varStatus="status">
								<tr>
									<td>[${count - status.index }]</td>
									<td style='padding-left:${15*vo.depth }px;text-align:left;'>
										<c:if test="${vo.depth gt 0 }">
											<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'/>
										</c:if>
										<c:if test="${vo.statusNo eq 2 }">
											${vo.title }
										</c:if>
										<c:if test="${vo.statusNo eq 0 || vo.statusNo eq 1 }">
											<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}/${paging.curPageNum}">${vo.title }</a>
										</c:if>
									</td>
									<td>${vo.userName }</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>
									<td><c:if test="${authUser.no eq vo.userNo }"><a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}/${vo.gNo}/${param.page}" class="del">삭제</a></c:if></td>
								</tr>
							</c:forEach>
						</table>
				<!-- pager 추가 -->
				<div class="pager">
				    <ul>
				        <c:if test="${ paging.curPageNum > 5 && !empty kwd }">
				            <li><a href="${pageContext.servletContext.contextPath }/board?page=${ paging.blockStartNum - 1 }&kwd=${ kwd }">◀</a></li>
				        </c:if>
				        
				        <c:if test="${ paging.curPageNum > 5 }">
				            <li><a href="${pageContext.servletContext.contextPath }/board?page=${ paging.blockStartNum - 1 }">◀</a></li>
				        </c:if>
				        
				        <c:forEach var="i" begin="${ paging.blockStartNum }" end="${ paging.blockLastNum }">
				            <c:choose>
				                <c:when test="${ i > paging.lastPageNum }">
				                    <li>${ i }</li>
				                </c:when>
				                <c:when test="${ i == paging.curPageNum }">
				                    <li class="selected">${ i }</li>
				                </c:when>
				                <c:when test="${ !empty kwd}">
				                    <li><a href="${pageContext.servletContext.contextPath }/board?page=${ i }&kwd=${ kwd }">${ i }</a></li>
				                </c:when>
				                <c:otherwise>
				                    <li><a href="${pageContext.servletContext.contextPath }/board?page=${ i }">${ i }</a></li>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
				        
				        <c:if test="${ paging.lastPageNum > paging.blockLastNum && !empty kwd }">
				            <li><a href="${pageContext.servletContext.contextPath }/board?page=${ paging.blockLastNum + 1 }&kwd=${ kwd }">▶</a></li>
				        </c:if>
				        
				        <c:if test="${ paging.lastPageNum > paging.blockLastNum }">
				            <li><a href="${pageContext.servletContext.contextPath }/board?page=${ paging.blockLastNum + 1 }">▶</a></li>
				        </c:if>
				    </ul>
				</div> 					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${!empty authUser.no }">
						<a href="${pageContext.servletContext.contextPath }/board/write/${paging.curPageNum }" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>