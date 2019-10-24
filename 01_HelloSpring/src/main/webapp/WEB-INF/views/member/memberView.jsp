<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value=""/>
</jsp:include>
<section id="content">
<div id="enroll-container">
			<form name="memberEnrollFrm" action="${pageContext.request.contextPath}/member/memberEnrollEnd.do" method="post" onsubmit="return validate();" >
				<input type="text" class="form-control" placeholder="아이디 (4글자이상)" name="userId" id="userId_" value="${member.userId }"readonly="readonly">
				<!-- userId를 ajax처리:     -->
				<script>
					$("#userId_").keyup(function(){
						var value=$(this).val().trim();
						if(value.length<4){
							return;
						}
						//아닐떈 밑에서 처리
						$.ajax({
							url:"${path}/member/checkId.do",
							data:{"userId":value},
							success:function(data){
								console.log(data);
							}
						});
					})
				</script>				
				<input type="text" value="${member.userName }" readonly="readonly" class="form-control" placeholder="이름" name="userName" id="userName" required>
				<input type="number" value="${member.age }" readonly="readonly" class="form-control" placeholder="나이" name="age" id="age">
				<input type="email" value="${member.email } "readonly="readonly" class="form-control" placeholder="이메일" name="email" id="email" required>
				<input type="tel" value="${member.phone } "readonly="readonly" class="form-control" placeholder="전화번호 (예:01012345678)" name="phone" id="phone" maxlength="11" required>
				<input type="text" value="${member.address } "readonly="readonly" class="form-control" placeholder="주소" name="address" id="address">
			
				<br />
				
			</form>
		</div>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

