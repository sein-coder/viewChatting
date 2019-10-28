<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value=""/>
</jsp:include>
<section id="content">
<div id="board-container">
        <input type="text" class="form-control" placeholder="제목" name="boardTitle" id="boardTitle" value="${festival.boardTitle }" required>
        <input type="text" class="form-control" name="boardWriter" value="${festival.boardDate }" readonly required>
		<input type="text" class="form-control" name="boardCount" value="${festival.boardCount }" readonly required>

    </div>
    <button type="button" class="btn btn-outline-success btn-list">목록</button>


</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

