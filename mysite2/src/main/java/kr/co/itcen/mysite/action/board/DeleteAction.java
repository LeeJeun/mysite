package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		UserVo sessionVo = (UserVo)session.getAttribute("authUser");
//		Long userNo = sessionVo.getNo();
		
		String no = request.getParameter("no");
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		int page = Integer.parseInt(request.getParameter("page"));

		BoardVo vo = new BoardVo();
		vo.setNo(Long.parseLong(no));
		
		BoardDao boardDao = new BoardDao();
		int count = boardDao.getCount(gNo);
		
		// 답글이 있으면
		if(count>1) {
			boardDao.replyDelete(vo);
		} 
		// 답글이 없으면
		else {
			boardDao.delete(vo);
		}
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?page="+page);
	}

}
