package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		request.setAttribute("authUser", authUser);

		int curPageNum = 1;
		if (request.getParameter("page") != null) {
			curPageNum = Integer.parseInt(request.getParameter("page"));
		}
		
		Paging paging = new Paging(curPageNum);
		
		if(request.getParameter("kwd")!=null) {
			paging.makeBlock(curPageNum);
			paging.makeLastPageNum(request.getParameter("kwd"));
			request.setAttribute("kwd", request.getParameter("kwd"));
			request.setAttribute("paging", paging);
						
			List<BoardVo> list = new BoardDao().getList(request.getParameter("kwd"), curPageNum);
			request.setAttribute("list", list);
		} else {
			paging.makeBlock(curPageNum);
			paging.makeLastPageNum();
			request.setAttribute("paging", paging);
						
			List<BoardVo> list = new BoardDao().getList(curPageNum);
			request.setAttribute("list", list);
		}
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
