<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap" rel="stylesheet">
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="pageTitle" value=""/>
</jsp:include>
<style>
 section {font-family: 'Do Hyeon', sans-serif; }
 h3 {border-style: dotted solid double;}
 section {font-family: 'Do Hyeon', sans-serif; }
 #proceeding-btn:hover{border-bottom-right-radius: 5px; color:white; background-color: red;
					 border: 1px solid skyblue;	 }
					 
</style>  
<section id="content">
<div id="board-container">           	

           	<input type="text" class="form-control" name="boardTitle" id="boardTitle" value="${festival.boardTitle}" readonly="readonly">
            <input type="text" class="form-control" name="festival_Pic" id="festival_Pic" value="${festival.festival_Pic }" readonly="readonly">
           	
            <textarea class="form-control" name="boardContent" placeholder="내용" readonly="readonly">${festival.boardContent }</textarea>
            <br />
            <div id="board-add">
	           <table>
	           <tr>
	              <th>상세정보&nbsp;&nbsp;</th>
	           <tr>
	           		<td>
	           			<button id="proceeding-btn" ><c:out value="${festival.festival_Proceeding }"/></button>
		            </td>
	            </tr>
	            </table>
	               <h3></h3>
		           <table id="table-container">

		             <tr>
 					<td>시작일<input type="text" readonly="readonly" class="form-control"  name="festival_StartDate"  value="${festival.festival_StartDate}"  ></td>
					<td>종료일<input type="text"  readonly="readonly" class="form-control" name="festival_EndDate" value="${festival.festival_EndDate}"    ></td>
					</tr>
					<tr>
						<td>전화번호<input type="text" readonly="readonly" class="form-control" name="festival_Phone" value="${festival.festival_Phone}"></td>
						<td>홈페이지<input type="text" readonly="readonly" class="form-control" name="festival_Homepage">${festival.festival_Homepage}</td>
					</tr>
					<tr>	
						<td>주소<input type="text" readonly="readonly" class="form-control" name="festival_Address" value="${festival.festival_Address}"></td>
						<td>주최<input type="text" readonly="readonly" class="form-control"  name="festival_Host" value="${festival.festival_Host}"/></td>
					</tr>
					<tr>
						<td>주관<input type="text" readonly="readonly" class="form-control"  name="festival_Sub" value="${festival.festival_Sub}"/></td>
						<td>이용요금<input type="text" readonly="readonly" class="form-control"  name="festival_Price"  value="${festival.festival_Price}"/></td>
					</tr>
					</table>
				</div>
			 <h3></h3>    
            
         
	        <input type="button" value="목록" id='btn-list' class='btn btn-list' onclick='location.href="${path}/festival/festivalList"'/>

        <br>
        <br>
        <br>
        <br>
        <br>
        <button id="Inquire">1:1문의</button>
		<span>:</span>
		<span class="blinkEle">추가적인 문의는 아래 1:1 상담문의로 부탁드립니다.</span>
    </div>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

