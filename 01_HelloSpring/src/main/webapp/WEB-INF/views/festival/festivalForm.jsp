<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap" rel="stylesheet">
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value="축제알리미"/>
</jsp:include>
<style>
 h3 {border-style: dotted solid double;}
 section#content {font-family: 'Do Hyeon', sans-serif; }
</style>  
<section id="content">
<script>


</script>
<section id="content">
	<div id="board-container">
		<form action="">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<input type="text" name="fastivalTitle">
			<select name="">
				<option value="진행">진행</option>
				<option value="종료">종료</option>
			</select>
		</form>   
    </div>
        <button id="btn-Inquire">1:1문의</button>
		<span>:</span>
		<span class="blinkEle">추가적인 문의는 아래 1:1 상담문의로 부탁드립니다.</span>
</section>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

