package kr.co.itcen.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user") //URL user로 시작함
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		model.addAttribute("userVo", vo);
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(UserVo vo, HttpSession session, Model model) {
//		UserVo userVo = userService.getUser(vo);
//		if(userVo == null) {
//			model.addAttribute("result","fail");
//			return "user/login";
//		}
//		
//		// 로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
	
//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		// 접근제어 ACL (권한 없는 사람이 logout으로 들어오는것을 막음)
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/";
//		}
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}

//	@RequestMapping(value="/update", method=RequestMethod.GET)
//	public String update(HttpSession session, Model model) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		UserVo userVo = userService.getUser(authUser.getNo());
//
//		model.addAttribute("vo",userVo);
//		
//		return "user/update";
//	}
//	
//	@RequestMapping(value="/update", method=RequestMethod.POST)
//	public String update(@ModelAttribute UserVo vo, Model model) {
//		userService.update(vo);
//		return "redirect:/";
//	}
	
	@Auth("USER")
	//ADMIN -> Auth class에 enum{}
	//role=Auth.Role.ADMIN ->Auth class에 role
	//role=Role.ADMIN -> enum class
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		authUser = userService.getUser(authUser.getNo());
		model.addAttribute("authUser", authUser);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute @Valid UserVo vo) {
		userService.update(vo);
		return "redirect:/";
	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		return "error/exception";
//	}
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {
		
	}
}
