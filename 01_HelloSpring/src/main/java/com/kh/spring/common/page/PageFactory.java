package com.kh.spring.common.page;

public class PageFactory {
	
	public static String getPageBar(int totalCount, int cPage, int numPerPage ,String url) {
		String pageBar="";
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		int totalPage=(int)Math.ceil((double)totalCount/numPerPage);
		
		pageBar+="<ul class='pagination "
				+ " justify-content-center "
				+ " pagination-sm'>";
		if(pageNo==1) {
			pageBar+="<li class='page-item disabled'>";
			pageBar+="<a class='page-link' "
					+ "href='#' tabindex='1'>이전</a>";
			pageBar+="</li>";
		}else {
			pageBar+="<li class='page-item'>";
			pageBar+="<a class='page-link' "
					+ "href='javascript:fn_paging("+(pageNo-1)+")'>이전</a>";
			pageBar+="</li>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<li class='page-item active'>";
				pageBar+="<a class='page-link'>"+pageNo+"</a>";
				pageBar+="</li>";
			}else {
				pageBar+="<li class='page-item'>";
				pageBar+="<a class='page-link' "
						+ "href='javascript:fn_paging("+(pageNo)+")'>"+pageNo+"</a>";
				pageBar+="</li>";
			}
			pageNo++;//이구문을 뺴면 무한루프가 돌아 페이지가 안뜰 수 있음
		}
		
		if(pageNo>totalPage) {
			pageBar+="<li class='page-item disabled'>";
			pageBar+="<a class='page-link' "
					+ "href='#' tabindex='1'>다음</a>";
			pageBar+="</li>";
		}else {
			pageBar+="<li class='page-item'>";
			pageBar+="<a class='page-link' "
					+ "href='javascript:fn_paging("+(pageNo)+")'>다음</a>";
			pageBar+="</li>";
		}
		pageBar+="</ul>";
		
		pageBar+="<script>";
		pageBar+="function fn_paging(cPage){";//예약어 함수명칭(매개변수명)
		pageBar+="location.href='"+url+"?cPage='+cPage";//자바스크립트구문=>/spring/board/boardList 로 바꿔짐+매개변수(주의해서쓸것)
		//"?cPage='+cPage": 앞에는 보낼 파라미터명 cPage, 뒤에는 자바스크립트에서 받아온 변수 cPage/function의 매개변수 cPage이기도 함 
		pageBar+="}</script>";
		return pageBar;
	
	}

}
