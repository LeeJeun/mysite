package kr.co.itcen.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.user.UserActionFactory;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		ActionFactory actionFactory = new UserActionFactory();
		Action action = actionFactory.getAction(actionName);
		action.execute(request, response);
		
//		request.setCharacterEncoding("UTF-8");
//		
//		String action = request.getParameter("a");
//		if("joinform".equals(action)) {
//			WebUtils.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
//		} else {
//			WebUtils.redirect(request, response, request.getContextPath());
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
