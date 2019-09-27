package kr.co.itcen.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.guestbook.GuestbookActionFactory;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;


public class GuestbookServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String actionName = request.getParameter("a");
		ActionFactory actionFactory = new GuestbookActionFactory();
		Action action = actionFactory.getAction(actionName);
		action.execute(request, response);
		
		
//		//request.setCharacterEncoding("utf-8");
//
//		String action = request.getParameter("a");
//		
//		if ("add".equals(action)) {
//			String name = request.getParameter("name");
//			String password = request.getParameter("password");
//			String content = request.getParameter("content");
//			
//			GuestbookVo vo = new GuestbookVo();
//			vo.setName(name);
//			vo.setPassword(password);
//			vo.setContents(content);
//			
//			new GuestbookDao().insert(vo);
//			
//			response.sendRedirect(request.getContextPath() + "/guestbook");
//		} else if ("deleteform".equals(action)) {
//			String no = request.getParameter("no");
//			
//			request.setAttribute("no", no);
//			
//			//forwarding
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
//			rd.forward(request, response);
//		} else if ("delete".equals(action)) {
//			String no = request.getParameter("no");
//			String password = request.getParameter("password");
//			
//			GuestbookVo vo = new GuestbookVo();
//			vo.setNo(Long.parseLong(no));
//			vo.setPassword(password);
//			
//			new GuestbookDao().delete(vo);
//			
//			response.sendRedirect(request.getContextPath() + "/guestbook");
//		} else {
//			/* index(list) */
//			List<GuestbookVo> list = new GuestbookDao().getList();
//			request.setAttribute("list", list);
//			
//			//forwarding
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/index.jsp");
//			rd.forward(request, response);
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

}
