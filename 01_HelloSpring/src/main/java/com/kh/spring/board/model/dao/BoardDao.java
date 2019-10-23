package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

public interface BoardDao {

	/* List<Board> selectBoard(SqlSessionTemplate session,Board borad); */
	int selectBoardCount(SqlSessionTemplate session);
	List<Board> selectBoardList(SqlSessionTemplate session,int cPage,int numPerPage);
	int insertBoard(SqlSessionTemplate session, Board board);
	int insertAttachment(SqlSessionTemplate sesion, Attachment a);
	
	Board selectBoard(SqlSessionTemplate session,int boardNo);
	List<Attachment> selectAttach(SqlSessionTemplate session,int boardNo);

}
