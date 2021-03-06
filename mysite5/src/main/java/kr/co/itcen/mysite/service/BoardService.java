package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	//일반
	public List<BoardVo> list(int curPageNum){
		return boardDao.getList(curPageNum);
	}
	
	//키워드용
	public List<BoardVo> list(String kwd, int curPageNum){
		return boardDao.getList(kwd, curPageNum);
	}
	
	public void insert(BoardVo vo) {
		boardDao.insert(vo);
	}
	
	public void replyInsert(BoardVo vo) {
		boardDao.replyInsert(vo);
	}
	
	public BoardVo view(Long no) {
		return boardDao.getView(no);
	}
	
	public void update(BoardVo vo) {
		boardDao.update(vo);
	}
	
	public void delete(Long no) {
		boardDao.delete(no);
	}
	
	public void replyDelete(Long no) {
		boardDao.replyDelete(no);
	}
	
	public int getCount() {
		return boardDao.getCount();
	}
	
	public int getCount(String kwd) {
		return boardDao.getCount(kwd);
	}
	
	public int getReplyCount(int gNo) {
		return boardDao.getCount(gNo);
	}
}
