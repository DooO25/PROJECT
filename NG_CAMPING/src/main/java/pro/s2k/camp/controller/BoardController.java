package pro.s2k.camp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.service.ReviewService;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.FileUploadVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;



@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private ReviewService reviewService;

	// SummerNote 컨트롤러 ---------------------------------------------------
	// 다운로드가 가능하게 하자
	@RequestMapping(value = "/download.do")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of");
		String sfileName = (String) params.get("sf");
		mv.setViewName("downlaodView");
		mv.addObject("ofileName",ofileName);
		mv.addObject("sfileName",sfileName);
		return mv;
	}
	
	// 섬머노트의 큰 이미지를 처리하기위한 주소 1개를 생성해 주어야 한다.
	@RequestMapping(value = "/imageUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public String imageUpload(MultipartHttpServletRequest request) {
		String saveName="";
		@SuppressWarnings("deprecation")
		String realPath = request.getRealPath("upload");
		
		// 모든 파일을 리스트로 받기
		List<MultipartFile> list = request.getFiles("file");
		
		if(list!=null && list.size()>0) { // 리스트에 내용이 있다면
			for(MultipartFile file : list) { // 반복
				// 파일 받기
				if(file!=null && file.getSize()>0) { // 파일이 존재 한다면
					try {
						// 이름 중복 처리를 하기위해 UUID로 중복되지않는 문자열을 생성하고 뒤에 원본이름을 붙여준다.
						saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
						// 저장할 파일 객체 생성
						File   saveFile = new File(realPath, saveName);
						// 파일을 복사
						FileCopyUtils.copy(file.getBytes(), saveFile);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(request.getContextPath() + "/upload/" + saveName);
		return request.getContextPath() + "/upload/" + saveName; // 실제 그림이 저장된 경로를 리턴해주게 만든다.
	}
	// SummerNote 컨트롤러 ---------------------------------------------------
	
	// 리뷰 목록보기
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/review.do")
	public String review(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
		}
		PagingVO<ReviewVO> pv = reviewService.selectList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
//		List<ReviewVO> list = reviewService.selectList();
//		model.addAttribute("list", list);
//		model.addAttribute("newLine", "\n");
//		model.addAttribute("br", "<br/>");
		return "review";
	}
	
	// 저장 
	@RequestMapping(value = "/reviewInsert.do")
	public String reviewInsert(@ModelAttribute CommonVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "reviewInsert";
	}
//	@RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.GET)
//	public String reviewInsertOk() {
//		return "redirect:/review";
//	}

	@RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.GET)
	public String reviewInserOkPOST(
		@ModelAttribute CommonVO commVO,
		@ModelAttribute ReviewVO reviewVO, 
		HttpServletRequest request, Model model,
		RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
	// 일단 VO로 받고
	reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
	log.info(reviewVO.getRv_ip()+"##################################################################");
	log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);

//	 // 넘어온 파일 처리를 하자
//	List<FileUploadVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
//	
//	List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
//	if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
//		for(MultipartFile multipartFile : multipartFiles) {
//			if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
//				FileUploadVO fileUploadVO = new FileUploadVO(); // 객체 생성하고
//				// 파일 저장하고
//				try {
//					// 저장이름
//					String realPath = request.getRealPath("upload");
//					String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
//					// 저장
//					File target = new File(realPath, saveName);
//					FileCopyUtils.copy(multipartFile.getBytes(), target);
//					// vo를 채우고
//					fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
//					fileUploadVO.setSaveName(saveName);
//					// 리스트에 추가하고
//					fileList.add(fileUploadVO); 
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	reviewVO.setFileList(fileList);
//	// 서비스를 호출하여 저장을 수행한다.
	reviewService.insert(reviewVO);
	
	// redirect시 GET전송 하기
	// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
	// redirect시 POST전송 하기
	// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
	Map<String, String> map = new HashMap<>();
	map.put("p", "1");
	map.put("s", commVO.getPageSize() + "");
	map.put("b",commVO.getBlockSize() + "");
	redirectAttributes.addFlashAttribute("map", map);
	return "redirect:/review.do";
	}

	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reviewView.do")
	public String view(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model) {
		log.info("{}의 view호출 : {}", this.getClass().getName(), commVO);
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
			commVO.setIdx(Integer.parseInt(params.get("idx")));
		}
		
		ReviewVO reviewVO = reviewService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", reviewVO);
		model.addAttribute("cv", commVO);
		return "reviewView";
	}
	
	// 수정하기
//	@RequestMapping(value = "/reviewUpdate.do", method = RequestMethod.GET)
//	public String update(ModelAttribute CommonVO commVO,Model model) {
//		log.info("updateGet 호출");
//		return "redirect:/review";
//	}
	
	@RequestMapping(value = "/reviewUpdate.do", method = RequestMethod.POST)
	public String updatePost(@ModelAttribute CommonVO commVO,Model model) {
		ReviewVO reviewVO = reviewService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", reviewVO);
		model.addAttribute("cv", commVO);
		return "reviewUpdate";
	}

	@RequestMapping(value = "/reviewUpdateOk",method = RequestMethod.GET)
	public String updateOk(@ModelAttribute CommonVO commVO,Model model) {
		return "redirect:/review";
	}
	
	@RequestMapping(value = "/reviewUpdateOk",method = RequestMethod.POST)
	public String updateOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute ReviewVO reviewVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);

//		// 넘어온 파일 처리를 하자
//		List<FileUploadVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
//		
//		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
//		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
//			for(MultipartFile multipartFile : multipartFiles) {
//				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
//					FileUploadVO fileUploadVO = new FileUploadVO(); // 객체 생성하고
//					// 파일 저장하고
//					try {
//						// 저장이름
//						String realPath = request.getRealPath("upload");
//						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
//						// 저장
//						File target = new File(realPath, saveName);
//						FileCopyUtils.copy(multipartFile.getBytes(), target);
//						// vo를 채우고
//						fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
//						fileUploadVO.setSaveName(saveName);
//						// 리스트에 추가하고
//						fileList.add(fileUploadVO); 
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		reviewVO.setFileList(fileList);
		// 삭제할 파일 번호를 받아서 삭제할 파일을 삭제해 주어야 한다.
//		String[] delFiles = request.getParameterValues("delfile");
//		// 서비스를 호출하여 저장을 수행한다.
//		String realPath = request.getRealPath("upload");
//		reviewService.update(reviewVO, delFiles, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("idx",commVO.getIdx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/reviewView";
	}
	
	// 글삭제
//	@RequestMapping(value = "/reviewDelete.do", method = RequestMethod.GET)
//	public String delete(@ModelAttribute ReviewVO reviewVO) {
//		log.info("deleteGet 호출");
//		return "redirect:/review";
//	}
	
	@RequestMapping(value = "/reviewDelete.do", method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommonVO commVO,Model model) {
		ReviewVO reviewVO = reviewService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", reviewVO);
		model.addAttribute("cv", commVO);
		return "reviewDelete";
	}
	
	@RequestMapping(value = "/reviewDeleteOk.do",method = RequestMethod.GET)
	public String deleteOk(@ModelAttribute CommonVO commVO,Model model) {
		return "redirect:/reviewDeleteOk";
	}
	@RequestMapping(value = "/reviewDeleteOk.do",method = RequestMethod.POST)
	public String deleteOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute ReviewVO reviewVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		reviewService.delete(reviewVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/review";
	}
	
	
	
	// 댓글 저장
//	@RequestMapping(value = "/reviewReply.do", method = RequestMethod.GET)
//	public String reply(@ModelAttribute ReviewVO reviewVO) {
//		log.info("replyGet 호출");
//		return "redirect:/review";
//	}
	@RequestMapping(value = "/reviewReply.do", method = RequestMethod.POST)
	public String replyPost(@ModelAttribute ReviewVO reviewVO, HttpServletRequest request) {
		log.info("replyPost 호출 : " + reviewVO);
		reviewVO.setRv_ip(request.getRemoteAddr()); // ip저장
		reviewService.reply(reviewVO);
		return "redirect:/review";
	}
	
	
	
	

	
	
	
	@RequestMapping(value = "/notice.do", method = RequestMethod.GET)
	public String notice() {
		return "notice";
	}
	@RequestMapping(value = "/noticeInsert.do", method = RequestMethod.GET)
	public String noticeInsert() {
		return "noticeInsert";
	}
	
	@RequestMapping(value = "result.do")
	public String result() {
		return "result";
	}

}