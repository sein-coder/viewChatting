package com.kh.spring.board.model.service;

import java.util.List;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

public interface BoardService {
	
	/* List<Board> selectBoard(Board board); */
	//강사님방식
	int selectBoardCount();
	List<Board> selectBoardList(int cPage,int numPerPage);
	int insertBoard(Board board, List<Attachment> list);
}
