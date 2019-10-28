<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value="목록"/>
</jsp:include>
<section id="content">
<p>총 ${boardCount }건의 게시물이 있습니다.</p>
        <input type="button" value="글쓰기" id='btn-add' class='btn btn-outline-success' onclick='location.href="${path}/festival/festivalForm.do"'/>
        <table id="tbl-board" class="table table-striped table-hover">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
			<c:if test="${not empty list }">
				<c:forEach items="${list }" var="festival" >
				<tr>
					<td><c:out value="${festival.boardNo}"/></td>
					<td><a href="${path }/festival/festivalView?boardNo=${festival.boardNo }"><c:out value="${festival.boardTitle}"/></a></td>
					<td><c:out value="${festival.boardDate}"/></td>
					<td><c:out value="${festival.boardCount}"/></td>
				</tr>
				</c:forEach>
			</c:if>
        </table> 
        ${pageBar }

</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

