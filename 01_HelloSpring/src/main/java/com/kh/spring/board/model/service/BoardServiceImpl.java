package com.kh.spring.board.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao dao;
	@Autowired
	private SqlSessionTemplate session;
	@Override
	public int selectBoardCount() {
		return dao.selectBoardCount(session);
	}
	
	
	
	@Override
	public int insertBoard(Board b, List<Attachment> list) {
		int result=0;
		result=dao.insertBoard(session, b);
		for(Attachment a:list) {
			a.setBoardNo(b.getBoardNo());
			//여기엔, 제목/내용/작성자만 있는데 Board가 있으니까!b.getBoardNo에서 갖고올 수 있다.
			result=dao.insertAttachment(session, a);
		}
		return result;
	}



	@Override
	public List<Board> selectBoardList(int cPage, int numPerPage) {
		return dao.selectBoardList(session, cPage,numPerPage);
	}
	
	/*
	 * @Override public List<Board> selectBoard(Board board) { return
	 * dao.selectBoard(session,board); }
	 */
	
	
	
}
