package pro.s2k.camp.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pro.s2k.camp.dao.CampDAO;
import pro.s2k.camp.service.CampService;
import pro.s2k.camp.service.NoticeService;
import pro.s2k.camp.service.QnAService;
import pro.s2k.camp.service.ReviewService;
import pro.s2k.camp.vo.CampInfoVO;
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
	
	@Autowired
	private CampDAO campDAO; 

	@Autowired
	private CampService campService; 
	

	@RequestMapping(value ={"/main.do" , "/mainOK.do","/main", "/"}, method = RequestMethod.GET)
	public String home(HttpServletRequest request,Locale locale, Model model, CommonVO commonVO) {
		// 공지사항게시판 리스트 표기 
		PagingVO<NoticeVO> nv = noticeService.selectList(commonVO);
	     	model.addAttribute("nv", nv);
     	// QnA게시판 리스트 표기 
	    PagingVO<QnAVO> qv = qnaService.selectList(commonVO);
			model.addAttribute("qv", qv); 
		// 후기게시판 리스트 표기 
		PagingVO<ReviewVO> rv = reviewService.selectList(commonVO);
	     	model.addAttribute("rv", rv);
	    // 캠핑장의 총 개수 찍기
		int total = campDAO.selectCountTotal();
			model.addAttribute("total", total);
		// 캠핑장의 일반야영장 개수 찍기
		int campSite = campDAO.selectCountCampsitel();
			model.addAttribute("campSite", campSite);
		// 캠핑장의 자동차 야영장 개수 찍기
		int carCamp = campDAO.selectCountCar();
			model.addAttribute("carCamp", carCamp);
		// 캠핑장의 글램핑 및  카라반 개수 찍기
		int glamping = campDAO.selectCountGlamping();
			model.addAttribute("glamping", glamping);
		// 랜덤으로 캠핑장 표기
		PagingVO<CampInfoVO> pv = campService.selectRandom(commonVO);
			model.addAttribute("pv", pv);
		return "main";
	}

	
}
