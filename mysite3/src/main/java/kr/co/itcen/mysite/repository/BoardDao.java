package kr.co.itcen.mysite.repository;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;		
	}
	
	public Boolean replyInsert(BoardVo vo) {
		sqlSession.update("board.replyInfoUpdate");
		int count = sqlSession.insert("board.replyInsert", vo);
		return count == 1;
	}
	
	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}
	
	public List<BoardVo> getList() {
		List<BoardVo> result = sqlSession.selectList("board.getList");
		return result;
	}
	
	public List<BoardVo> getList(String kwd) {
		List<BoardVo> result = sqlSession.selectList("board.getSerchList", kwd);
		return result;
	}
	
	public int getCount() {
		int count = sqlSession.selectOne("board.getCount");
		return count;
	}
	
	public int getCount(String kwd) {
		int count = sqlSession.selectOne("board.getSerchCount", kwd);
		return count;
	}

	public BoardVo getView(Long no) {
		sqlSession.update("board.hitUpdate", no);
		BoardVo result = sqlSession.selectOne("board.getView", no);
		return result;
	}
	
	public void delete(BoardVo vo) {
		sqlSession.delete("board.delete", vo);	
	}
}
