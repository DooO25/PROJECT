package pro.s2k.camp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pro.s2k.camp.service.CampService;
import pro.s2k.camp.vo.CampInfoVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;



@Controller
public class CampController {

	@Autowired
	private CampService campService;
	
	// 캠핑장 찾기 수행
	@RequestMapping(value ={"/camp/campsite.do"}, method = RequestMethod.GET)
	public String campsite(@ModelAttribute CommonVO commonVO, Model model,HttpServletRequest request ,HttpSession session) {
		// 현재위치 좌표값을 받아와 세션에 넣어주고, null일경우 세션에서 받아온다
		if(request.getParameter("lat")!=null) {
			commonVO.setLat(Double.parseDouble(request.getParameter("lat")));
			commonVO.setLon(Double.parseDouble(request.getParameter("lon")));
			session.setAttribute("lat",Double.parseDouble(request.getParameter("lat")));
			session.setAttribute("lon",Double.parseDouble(request.getParameter("lon")));
		}else {
			commonVO.setLat((double)session.getAttribute("lat"));
			commonVO.setLon((double)session.getAttribute("lon"));
		}
		// 캠장 찾기 에서 리스트 받아오기
		PagingVO<CampInfoVO> pv = campService.selectCampSitel(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO); 
		return "/camp/campsite";
	}
	
	// 캠핑장 검색해서 찾기
	@RequestMapping(value = "/selectSearchCamp.do", produces = "application/json; charset=UTF-8")
	public String selectSearchCamp (HttpServletRequest request, HttpSession session,CommonVO commonVO, Model model) throws JsonProcessingException, ParseException {
		// 클라이언트 좌표값 세션에서 받아오기
		commonVO.setLat((double)session.getAttribute("lat"));
		commonVO.setLon((double)session.getAttribute("lon"));
		// RequestParam으로 받으면 null일경우 아예 받을 수 가없으므로 getParameter 로 해야함
		// 넘어온 값 받아주고
		String searchType = request.getParameter("searchType");
		String searchType2 = request.getParameter("searchType2");
		String searchText = request.getParameter("searchText");
		String animalCheck = request.getParameter("animalCheck");
		// 서비스 인수값으로 보내기위해 vo에 담아준다
		commonVO.setSearchType(searchType);
		commonVO.setSearchType2(searchType2);
		commonVO.setSearchText(searchText);
		commonVO.setAnimalCheck(animalCheck);
		// 지도 찍어주는 컨트롤러에서 사용하기 위해 세션에 담아둠
		session.setAttribute("searchType", searchType);
		session.setAttribute("searchType2", searchType2);
		session.setAttribute("searchText", searchText);
		session.setAttribute("animalCheck", animalCheck);
		PagingVO<CampInfoVO> pv = campService.selectSearchList(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO);
		return "/camp/campsite";
	}
	
	// 캠핑장 찾기 지도를 표시해주는 컨트롤러
	@RequestMapping(value ={"/camp/selectCampSitel.do"}, method = RequestMethod.POST)
	@ResponseBody
	public String selectCampsitel(@RequestHeader(value = "p", required = false)int p, // ajax에서 before send로 보낸 현재 페이지 값을 받는다
			@ModelAttribute CommonVO commonVO, HttpServletRequest request, Model model,HttpSession session) throws Exception  {
		// 객체를 직렬화(문자열)로 바꿔주기위해 사용 
		ObjectMapper mapper = new ObjectMapper();
		// 직렬화 된 문자열을 json으로 파싱
		JSONParser jsonParser = new JSONParser();
		// if 와 else 에서 사용하기 때문에 전역변수로 둠
		Object objectJson = null;
		// 세션에 담긴 검색 항목들을 담는다
		String searchType = (String) session.getAttribute("searchType");
		String searchType2 = (String) session.getAttribute("searchType2");
		String searchText = (String) session.getAttribute("searchText");
		String animalCheck = (String) session.getAttribute("animalCheck");
			// commonVO에 담아준다
			commonVO.setLon((double)session.getAttribute("lon"));
			commonVO.setLat((double)session.getAttribute("lat"));
			commonVO.setSearchText(searchText);
			commonVO.setSearchType(searchType);
			commonVO.setSearchType2(searchType2);
			commonVO.setAnimalCheck(animalCheck);
			commonVO.setP(p); 
		// searchType 이 null 이 아니면 검색을 했다는 의미이므로 검색 서비스로
		if(searchType!=null) {
				PagingVO<CampInfoVO> pv = campService.selectSearchList(commonVO);
				String jsonParse = mapper.writeValueAsString(pv);
				objectJson = jsonParser.parse(jsonParse);
				JSONObject jsonList = (JSONObject) objectJson;
				String list = (String)(jsonList.get("list")+"");
				session.removeAttribute("searchType");
				return list;
		// null이 아니면 기존의 거리순으로 표기
		}else {
			PagingVO<CampInfoVO> pv = campService.selectCampSitel(commonVO);
			// pv값을 직렬화 하여준다
			String jsonParse = mapper.writeValueAsString(pv);
			// json으로 파싱
			objectJson = jsonParser.parse(jsonParse);
			// Object로 만들어 주고 
			JSONObject jsonList = (JSONObject)objectJson;
			// 리스트에 찍어주고
			String list = (String)(jsonList.get("list")+"");
			// 세션에 담아준 searchType만 if문 판단을 위해 지워준다
			session.removeAttribute("searchType");
			return list;
		}
	}
	// 해당 캠핑장 상세 페이지
	@RequestMapping(value={"/camp/detailPage.do"}, method = RequestMethod.POST)
	public String selectinfo(@RequestParam("var") String facltNm,Model model) {	    
		CampInfoVO pv = campService.selectCamplInfo(facltNm);
		model.addAttribute("result",pv);
		return "/camp/detailPage";
	}
}