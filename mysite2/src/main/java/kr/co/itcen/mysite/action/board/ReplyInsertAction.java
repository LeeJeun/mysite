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

public class ReplyInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo sessionVo = (UserVo)session.getAttribute("authUser");
		Long userNo = sessionVo.getNo();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int groupNo = Integer.parseInt(request.getParameter("gNo"));
		int orderNo = Integer.parseInt(request.getParameter("oNo"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(userNo);
		vo.setgNo(groupNo);
		vo.setoNo(orderNo);
		vo.setDepth(depth);
		
		new BoardDao().replyInsert(vo);
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?page="+page);
	}

}
