package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		if(request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
		} 
		request.setAttribute("page", page);

		Long boardNo = Long.parseLong(request.getParameter("no"));
		List<BoardVo> vo = new BoardDao().getView(boardNo);
		request.setAttribute("vo", vo);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
