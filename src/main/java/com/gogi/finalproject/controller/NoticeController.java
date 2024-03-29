package com.gogi.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gogi.finalproject.domain.Criteria;
import com.gogi.finalproject.domain.NoticeDTO;
import com.gogi.finalproject.domain.NoticeVO;
import com.gogi.finalproject.domain.PageDTO;
import com.gogi.finalproject.exception.ControllerException;
import com.gogi.finalproject.exception.ServiceException;
import com.gogi.finalproject.service.NoticeService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@RequestMapping("/notice/")
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService service;

	@GetMapping("/list")
	public void listPerPage(Criteria cri, Model model) throws ControllerException {
		log.trace("list() invoked.");
		
		try {
			List<NoticeVO> list = this.service.getList(cri);
			model.addAttribute("list", list);
			
			PageDTO pageDTO = new PageDTO(cri, this.service.getTotal());
			model.addAttribute("pageMaker", pageDTO);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}

	} // listPerPage
	
	@GetMapping("/getNotice")
	public void getNotice(NoticeDTO dto, Model model) throws ControllerException {
		log.trace("getNotice({}, {}) invoked.", dto, model);
		
		try {
			NoticeVO vo = this.service.getNotice(dto);
			
			model.addAttribute("notice", vo);
		} catch(Exception e) {
			throw new ControllerException(e);
		}
		
	} // getNotice
	
	@GetMapping("/new")
	public void registerPage() {
		log.trace("register() invoked.");
	} // registerPage
	
	@PostMapping("/register")
	public String register(NoticeDTO dto, RedirectAttributes rttrs) throws ControllerException {
		log.trace("register() invoked.");
		
		try {
			boolean isRegister = this.service.register(dto);

			rttrs.addAttribute("result", (isRegister)? "작성완료" : "잠시 후 다시 시도해주세요.");			

			return "redirect:/notice/list";
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	} // register

} // end class
