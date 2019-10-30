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
        <form name="FestivalFrm" action="${pageContext.request.contextPath}/festival/festivalFormEnd" method="post" >
         
           
            <input type="text" class="form-control" placeholder="제목" name="boardTitle" id="boardTitle" required>
            <input type="text" class="form-control" placeholder="그림" name="festival_Pic" id="festival_Pic" required>
           	
           	<input type="hidden" value="admin" name="boardWriter">
           	<input type="hidden" value="테스트용데이터들1" name="festival_Thumbnail" id="festival_Thumbnail">
           	<input type="hidden" value="테스트용데이터들3" name="festival_Hashtag" id="festival_Hashtag">
           	<input type="hidden" value="테스트용데이터들3" name="festival_Hashtag" id="festival_Hashtag">
           	
           	
            <textarea class="form-control" name="boardContent" id="festival_Pic" placeholder="내용" required></textarea>
            <br />
            <div id="board-add">
            <table>
	           <tr>
		           <td><th>상세정보  &nbsp;</th></td>
		           <td>
		            	<select class="form-control2" name="festival_Proceeding" required>
		            		<option type="hidden" value="" disabled selected>진행상태</option>
		            		<option value="진행">진행</option>
		            		<option value="종료">종료</option>
		            	</select>
		            </td>
	            </tr>
	            </table>
	               <h3></h3>
	           <table id="table-container">
	            <tr>
 					<td>시작일<input type="date" class="form-control" placeholder="시작일" name="festival_StartDate" pattern="yyyy-MM-dd"></td>
					<td>종료일<input type="date" class="form-control" placeholder="종료일" name="festival_EndDate" pattern="yyyy-MM-dd"></td>
				</tr>
				<tr>
					<td>전화번호<input type="text" class="form-control" placeholder="전화번호" name="festival_Phone"></td>
					<td>홈페이지<input type="text" class="form-control" placeholder="홈페이지" name="festival_Homepage"></td>
				</tr>
				<tr>	
					<td>주소<input type="text" class="form-control" placeholder="주소" name="festival_Address"></td>
					<td>주최<input type="text" class="form-control" placeholder="주최" name="festival_Host"></td>
				</tr>
				<tr>
					<td>주관<input type="text" class="form-control" placeholder="주관" name="festival_Sub"></td>
					<td>이용요금<input type="text" class="form-control" placeholder="이용요금" name="festival_Price"></td>
				</tr>
				</table>
				</div>
			 <h3></h3>    
           
            <input type="submit" class="btn btn-outline-success" value="저장" >
	        <input type="button" class="btn btn-outline-success" value="목록" onclick='location.href="${path}/festival/festivalList"'/>
        </form>
        <br>
        <br>
        <br>
        <br>
        <br>
        <button id="btn-Inquire">1:1문의</button>
		<span>:</span>
		<span class="blinkEle">추가적인 문의는 아래 1:1 상담문의로 부탁드립니다.</span>
    </div>
</section>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

