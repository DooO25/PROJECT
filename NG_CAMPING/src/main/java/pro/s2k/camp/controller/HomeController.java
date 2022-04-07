package pro.s2k.camp.controller;


import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import pro.s2k.camp.service.NoticeService;
import pro.s2k.camp.service.QnAService;
import pro.s2k.camp.service.ReviewService;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.NoticeVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;
import pro.s2k.camp.vo.ReviewVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private QnAService qnaService; 

	@RequestMapping(value ={"/main.do" , "/mainOK.do","/main", "/"}, method = RequestMethod.GET)
	public String home(HttpServletRequest request,Locale locale, Model model, CommonVO commonVO) {
		 PagingVO<NoticeVO> nv = noticeService.selectList(commonVO);
	     	model.addAttribute("nv", nv);
	     PagingVO<QnAVO> qv = qnaService.selectList(commonVO);
			model.addAttribute("qv", qv); 
		 PagingVO<ReviewVO> rv = reviewService.selectList(commonVO);
	     	model.addAttribute("rv", rv);
		return "main";
	}

	
}
