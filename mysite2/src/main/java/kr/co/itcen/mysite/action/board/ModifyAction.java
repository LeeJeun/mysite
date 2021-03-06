package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		if(request.getParameter("page") != "") {
			page = Integer.parseInt(request.getParameter("page"));
			request.setAttribute("page", page);
		} 
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Long boardNo = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setNo(boardNo);
		new BoardDao().update(vo);
		
		WebUtils.redirect(request, response, request.getContextPath() + "/board?page="+page);
	}

}
