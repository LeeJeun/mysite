package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String list(Model model) {
		List<BoardVo> vo = boardService.list();
		model.addAttribute("list", vo);
		return "board/list";
	}

//	@RequestMapping("/{page}")
//	public String list(@PathVariable("page") int page, Model model) {
//		List<BoardVo> vo = boardService.list();
//		model.addAttribute("list", vo);
//		return "board/list";
//	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@RequestMapping(value = "/write/{no}", method = RequestMethod.GET)
	public String write(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo",vo);
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpSession session) {
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		Long userNo = sessionVo.getNo();
		vo.setUserNo(userNo);
		if (vo.getgNo() == 0) {
			boardService.insert(vo);
		} else {
			boardService.replyInsert(vo);
		}
		return "redirect:/board"; 
	}

	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		System.out.println(vo);
		return "board/view";
	}
	
	@RequestMapping(value = "/delete/{no}/{gNo}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, @PathVariable("gNo") int gNo) {
		int count = boardService.getReplyCount(gNo);
		if(count>1) {
			boardService.replyDelete(no);
		} else {
			boardService.delete(no);
		}
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.POST)
	public String modify(@PathVariable("no") Long no, BoardVo vo) {
		boardService.update(vo);
		return "redirect:/board/view/"+no;
	}
}
