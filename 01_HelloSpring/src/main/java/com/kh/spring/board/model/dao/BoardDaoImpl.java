package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

@Repository
public class BoardDaoImpl implements BoardDao {

	
	
	
	@Override
	public List<Attachment> selectAttach(SqlSessionTemplate session, int boardNo) {
		// TODO Auto-generated method stub
		return session.selectList("board.selectAttach",boardNo);
	}

	@Override
	public Board selectBoard(SqlSessionTemplate session, int boardNo) {
		// TODO Auto-generated method stub
		return session.selectOne("board.selectBoard",boardNo);
	}

	@Override
	public int insertBoard(SqlSessionTemplate session, Board board) {
		// TODO Auto-generated method stub
		return session.insert("board.insertBoard",board);
	}

	@Override
	public int insertAttachment(SqlSessionTemplate session, Attachment a) {
		// TODO Auto-generated method stub
		return session.insert("board.insertAttachment",a);
	}

	@Override
	public int selectBoardCount(SqlSessionTemplate session) {
		return session.selectOne("board.selectBoardCount");
	}

	@Override
	public List<Board> selectBoardList(SqlSessionTemplate session, int cPage, int numPerPage) {
		RowBounds row=new RowBounds((cPage-1)*numPerPage,numPerPage);//페이징처리 알아서 해주는 애들
		return session.selectList("board.selectBoardList",null,row) ;
	}

	/*
	 * @Override public List<Board> selectBoard(SqlSessionTemplate session,Board
	 * board) { return session.selectList("board.selectBoard",board); }
	 */
 
	

	
}
