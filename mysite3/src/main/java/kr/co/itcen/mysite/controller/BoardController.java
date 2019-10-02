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
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.Paging;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping({"","list"})
	public String list(@RequestParam(value="page", required=false, defaultValue="1") int page, 
			@RequestParam(value="kwd", required=false, defaultValue="") String kwd, 
			Model model) {
		
		// Paging
		int curPageNum = page;	
		
		Paging paging = new Paging(curPageNum);
	
		if(kwd.isEmpty()) {
			int count = boardService.getCount();
			paging.makeBlock(curPageNum);
			paging.makeLastPageNum(count);
			model.addAttribute("paging", paging);
						
			List<BoardVo> vo = boardService.list(curPageNum);
			model.addAttribute("list", vo);
			model.addAttribute("paging", paging);
		} else {
			int count = boardService.getCount(kwd);
			paging.makeBlock(curPageNum);
			paging.makeLastPageNum(kwd, count);
			model.addAttribute("paging", paging);
						
			List<BoardVo> vo = boardService.list(kwd, curPageNum);
			model.addAttribute("list", vo);
			model.addAttribute("kwd", kwd);
		}
		return "board/list";
	}

	//새글
	@RequestMapping(value = "/write/{page}", method = RequestMethod.GET)
	public String write(@PathVariable("page") int page) {
		return "board/write";
	}

	//답글
	@RequestMapping(value = "/write/{no}/{page}", method = RequestMethod.GET)
	public String write(@PathVariable("no") Long no, @PathVariable("page") int page, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo",vo);
		return "board/write";
	}

	@RequestMapping(value = "/write/{page}", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpSession session, @PathVariable("page") int page) {
		UserVo sessionVo = (UserVo) session.getAttribute("authUser");
		Long userNo = sessionVo.getNo();
		vo.setUserNo(userNo);
		if (vo.getgNo() == 0) {
			page = 1;
			boardService.insert(vo);
		} else {
			boardService.replyInsert(vo);
		}
		return "redirect:/board?page="+page; 
	}

	@RequestMapping(value = "/view/{no}/{page}", method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, @PathVariable("page") int page, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value = "/delete/{no}/{gNo}/{page}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, @PathVariable("gNo") int gNo, @PathVariable("page") int page) {
		int count = boardService.getReplyCount(gNo);
		if(count>1) {
			boardService.replyDelete(no);
		} else {
			boardService.delete(no);
		}
		return "redirect:/board?page="+page;
	}
	
	@RequestMapping(value = "/modify/{no}/{page}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, @PathVariable("page") int page, Model model) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/modify/{no}/{page}", method = RequestMethod.POST)
	public String modify(@PathVariable("no") Long no, @PathVariable("page") int page, BoardVo vo) {
		boardService.update(vo);
		return "redirect:/board/view/"+no+"/"+page;
	}
}
