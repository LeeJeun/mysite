package kr.co.itcen.web;

import kr.co.itcen.mysite.repository.BoardDao;

public class Paging {
    private final static int pageCount = 5;
    private int blockStartNum = 0;
    private int blockLastNum = 0;
    private int lastPageNum = 0;
    private int curPageNum = 1;
    public Paging() {
    	
    }
    public Paging(int curPageNum) {
    	this.curPageNum = curPageNum;
    }
    public int getBlockStartNum() {
        return blockStartNum;
    }
    public void setBlockStartNum(int blockStartNum) {
        this.blockStartNum = blockStartNum;
    }
    public int getBlockLastNum() {
        return blockLastNum;
    }
    public void setBlockLastNum(int blockLastNum) {
        this.blockLastNum = blockLastNum;
    }
    public int getLastPageNum() {
        return lastPageNum;
    }
    public void setLastPageNum(int lastPageNum) {
        this.lastPageNum = lastPageNum;
    }
    public int getCurPageNum() {
		return curPageNum;
	}
	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}
	
	// block을 생성
    // 현재 페이지가 속한 block의 시작 번호, 끝 번호를 계산
    public void makeBlock(int curPage){
        int blockNum = 0;

        blockNum = (int)Math.floor((curPage-1)/ pageCount);
        blockStartNum = (pageCount * blockNum) + 1;
        blockLastNum = blockStartNum + (pageCount-1);
    }

    // 총 페이지의 마지막 번호
    public void makeLastPageNum() {
        BoardDao dao = new BoardDao();
        int total = dao.getCount();
      
        if( total % pageCount == 0 ) {
            lastPageNum = (int)Math.floor(total/pageCount);
        }
        else {
            lastPageNum = (int)Math.floor(total/pageCount) + 1;
        }
    }

    // 검색을 했을 때 총 페이지의 마지막 번호
    public void makeLastPageNum(String kwd) {
    	BoardDao dao = new BoardDao();
        int total = dao.getCount(kwd);

        if( total % pageCount == 0 ) {
            lastPageNum = (int)Math.floor(total/pageCount);
        }
        else {
            lastPageNum = (int)Math.floor(total/pageCount) + 1;
        }
    }
	@Override
	public String toString() {
		return "Paging [blockStartNum=" + blockStartNum + ", blockLastNum=" + blockLastNum + ", lastPageNum="
				+ lastPageNum + "]";
	}
    
    
}