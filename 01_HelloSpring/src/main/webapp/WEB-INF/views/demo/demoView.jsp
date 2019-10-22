<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value="demo결과확인"/>
</jsp:include>	
<section id="content">
	<style>
	table#tbl-dev{  
		margin:0 auto;
		width:50%;
	}
	</style>
	<table class="table" id="tbl-dev">
		<tr>
			<th scope="col">이름</th>
			<td><c:out value="${dev.devName }"/></td>
		</tr>
		<tr>
			<th scope="col">나이</th>
			<td><c:out value="${dev.devAge }"/></td>
		</tr>
		<tr>
			<th scope="col">이메일</th>
			<td><c:out value="${dev.devEmail }"/></td>
		</tr>
		<tr>
			<th scope="col">성별</th>
			<td><c:out value='${dev.devGender eq "M"?"남":"여" }'/></td>
		</tr>
		<tr>
			<th scope="col">개발가능언어</th>
			<td>
				<c:forEach items="${dev.devLang }" var="l" varStatus="v">
					${v.index!=0?",":"" }<c:out value="${l }"/>
				</c:forEach>
			</td>
		</tr>
	</table>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
