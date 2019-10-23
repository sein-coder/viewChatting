package com.kh.spring.board.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	
	
	/*
	 * @Override
	 * 
	 * @Transactional //RuntimeException 처리를 해주기 public int insertBoard(Board b,
	 * List<Attachment> list) throws RuntimeException { int result=0;
	 * result=dao.insertBoard(session, b); for(Attachment a:list) {
	 * a.setBoardNo(b.getBoardNo()); //여기엔, 제목/내용/작성자만 있는데 Board가
	 * 있으니까!b.getBoardNo에서 갖고올 수 있다. result=dao.insertAttachment(session, a); }
	 * return result; }
	 * 
	 * 아래처럼 exception처리해주기
	 */
	
	/*어노테이션 방식으로 트랜잭션 처리 방법
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class) //Exception 처리를 해주기 public int
	 * insertBoard(Board b, List<Attachment> list) throws Exception { int result=0;
	 * result=dao.insertBoard(session, b); result=0; for(Attachment a:list) {
	 * a.setBoardNo(b.getBoardNo()); //여기엔, 제목/내용/작성자만 있는데 Board가
	 * 있으니까!b.getBoardNo에서 갖고올 수 있다. //result=dao.insertAttachment(session, a); 아래처럼
	 * 처리해보기 dao.insertAttachment(session, a);
	 * //////////////////////////////////////여기까지 하고 EXCEPTION된것!그래서 파일이 저장 1개만 됨
	 * 
	 * //그래서 @Transactional(rollbackFor = Exception.class)게 써주기 if(result==0) throw
	 * new Exception();
	 * 
	 * 
	 * }
		return result;
	}
	 */

@Override
	public Board selectBoard(int boardNo) {
		return dao.selectBoard(session,boardNo);
	}




	//선언적 방식으로 트랜잭션 처리
	@Override
	//@Transactional(rollbackFor = Exception.class)
	//Exception 처리를 해주기
	public int insertBoard(Board b, List<Attachment> list) throws Exception {
		int result=0;
		result=dao.insertBoard(session, b);
		result=0;
		for(Attachment a:list) {
			a.setBoardNo(b.getBoardNo());
			//여기엔, 제목/내용/작성자만 있는데 Board가 있으니까!b.getBoardNo에서 갖고올 수 있다.
			//result=dao.insertAttachment(session, a); 아래처럼 처리해보기
			dao.insertAttachment(session, a);
			//////////////////////////////////////여기까지 하고 EXCEPTION된것!그래서 파일이 저장 1개만 됨
			
			//그래서 @Transactional(rollbackFor = Exception.class)게 써주기
			if(result==0) throw new Exception();
		}
		return result;
	}
		

	@Override
	public List<Board> selectBoardList(int cPage, int numPerPage) {
		return dao.selectBoardList(session, cPage,numPerPage);
	}




	@Override
	public List<Attachment> selectAttach(int boardNo) {
		// TODO Auto-generated method stub
		return dao.selectAttach(session,boardNo);
	}
	
	/*
	 * @Override public List<Board> selectBoard(Board board) { return
	 * dao.selectBoard(session,board); }
	 */
	
	
	
	
}
