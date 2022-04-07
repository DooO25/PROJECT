package pro.s2k.camp.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
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
import pro.s2k.camp.service.CommentService;
import pro.s2k.camp.service.NoticeService;
import pro.s2k.camp.service.QnAService;
import pro.s2k.camp.service.ReviewService;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.FileUploadVO;
import pro.s2k.camp.vo.NoticeVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;
import pro.s2k.camp.vo.ReviewVO;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private QnAService qnaService; 

// 403 오류 -----------------------------	
	@RequestMapping("/403.do")
	public String denied(Model model, Authentication auth, HttpServletRequest req) {
		AccessDeniedException ade = (AccessDeniedException) req.getAttribute(WebAttributes.ACCESS_DENIED_403);
		log.info("ex : {}", ade);
		model.addAttribute("auth", auth);
		model.addAttribute("errMsg", ade);
		return "/403";
	}

	// 공지사항 notice======================================================

	   @RequestMapping(value="/board/notice.do")
	   public String noticeSelectList(@ModelAttribute CommonVO commonVO,NoticeVO noticeVO, Model model) {
	     PagingVO<NoticeVO> pv = noticeService.selectList(commonVO);
	     model.addAttribute("pv", pv);
	     model.addAttribute("cv", commonVO);
	     return "/board/notice";
	   }
	   
	   @RequestMapping(value="/board/noticeView.do", method = RequestMethod.GET)
	   public String noticeView() {
	      return "/board/noticeView";
	   }
	   
	   @RequestMapping(value="/board/noticeView.do", method = RequestMethod.POST)
	   public String noticeView(@ModelAttribute CommonVO commonVO, NoticeVO noticeVO,Model model) {
		  noticeVO = noticeService.selectByIdx(noticeVO.getNt_idx());
	      model.addAttribute("nv", noticeVO);
	      model.addAttribute("cv", commonVO);
	      return "board/noticeView";
	   }
	   
	   @RequestMapping(value="/board/noticeInsert.do")
	   public String noticeInsert(@ModelAttribute CommonVO commonVO, Model model) {
	      model.addAttribute("cv",commonVO);
	      return "board/noticeInsert";
	   }
	   
	   @RequestMapping(value="/board/noticeInsertOK.do", method = RequestMethod.GET)
	   public String noticeInsertOk() {
	      return "403";
	   }
	   
	   @RequestMapping(value="/board/noticeInsertOk.do", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
	   public String noticeInsertOK(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO, MultipartHttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
	      
	      noticeVO.setNt_ip(request.getRemoteAddr());
	      List<FileUploadVO> fileList = new ArrayList<>();
	      List<MultipartFile> multipartFiles = request.getFiles("uploadFile");
	      log.info(request.getFiles("uploadFile")+"######$");
	      log.info(multipartFiles+"##########1");
	      if(multipartFiles!= null && multipartFiles.size()>0) {
	         for(MultipartFile multipartFile: multipartFiles) {
	            if(multipartFile!=null && multipartFile.getSize()>0) {
	               FileUploadVO fileUploadVO = new FileUploadVO();               
	               try {
	                  String realPath = request.getSession().getServletContext().getRealPath("upload");
	                  log.info(realPath+"realpath");
	                  String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
	                  File target = new File(realPath, saveName);
	                  File folder = new File(realPath);
	                  if (!folder.exists()) {
	                     try{
	                        folder.mkdir();
	                     }catch(Exception e){
	                        e.getStackTrace();
	                     }
	                  }
	                  FileCopyUtils.copy(multipartFile.getBytes(), target);
	                  fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
	                  fileUploadVO.setSaveName(saveName);
	                  fileList.add(fileUploadVO);
	                  System.out.println("fileList : "+fileList);
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	            }
	         }
	      }
	      noticeVO.setFileList(fileList);
	      noticeService.insert(noticeVO);
	      
	      Map<String, String> map = new HashMap<>();
	      map.put("p", "1");
	      map.put("s", commonVO.getPageSize() + "");
	      map.put("b", commonVO.getBlockSize() + "");
	      redirectAttributes.addFlashAttribute("map", map);
	      return "redirect:/board/notice.do";
	   }
	   @RequestMapping(value="/board/noticeUpdate.do", method = RequestMethod.POST)
	   public String noticeUpdate(@ModelAttribute CommonVO commonVO, NoticeVO noticeVO, Model model) {
	      noticeVO = noticeService.selectByIdx(noticeVO.getNt_idx());
	      model.addAttribute("nv", noticeVO);
	      model.addAttribute("cv",commonVO);
	      return "/board/noticeUpdate";
	   }
	   
	   @RequestMapping(value="/board/noticeUpdate.do", method = RequestMethod.GET)
	   public String noticeUpdate() {
	      return "403";
	   }
	   
	   @RequestMapping(value="/board/noticeUpdateOk.do", method= RequestMethod.POST, headers = ("content-type=multipart/*"))
	   public String noticeUpdateOk(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO, MultipartHttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
	      
	      noticeVO.setNt_ip(request.getRemoteAddr());
	      List<FileUploadVO> fileList = new ArrayList<>();
	      List<MultipartFile> multipartFiles = request.getFiles("uploadFile");
	      
	      if(multipartFiles!= null && multipartFiles.size()>0) {
	         for(MultipartFile multipartFile: multipartFiles) {
	            if(multipartFile!=null && multipartFile.getSize()>0) {
	               FileUploadVO fileUploadVO = new FileUploadVO();               
	               try {
	                  String realPath = request.getSession().getServletContext().getRealPath("upload");
	                  File folder = new File(realPath);
	                  if (!folder.exists()) {
	                     try{
	                        folder.mkdir();
	                     }catch(Exception e){
	                        e.getStackTrace();
	                     }
	                  }
	                  String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
	                  File target = new File(realPath, saveName);
	                  FileCopyUtils.copy(multipartFile.getBytes(), target);
	                  fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
	                  fileUploadVO.setSaveName(saveName);
	                  fileList.add(fileUploadVO);
	                  System.out.println("fileList : "+ fileList);
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	            }
	         }
	      }
	      noticeVO.setFileList(fileList);
	      String[] delFiles = request.getParameterValues("delFile");
	      String realPath = request.getSession().getServletContext().getRealPath("upload");
	      noticeService.update(noticeVO, realPath, delFiles);
	      Map<String, String> map = new HashMap<>();
	      map.put("p", "1");
	      map.put("s", commonVO.getPageSize() + "");
	      map.put("b", commonVO.getBlockSize() + "");
	      redirectAttributes.addFlashAttribute("map", map);
	      return "redirect:/board/notice.do";
	   }
	   
	   @RequestMapping(value="/board/noticeDelete.do", method = RequestMethod.GET)
	   public String noticeDelete() {
	      return "403";
	   }
	   
	   @RequestMapping(value = "/board/noticeDelete.do",method = RequestMethod.POST)
	   public String noticeDelete(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	      String realPath = request.getSession().getServletContext().getRealPath("upload");
	      noticeService.delete(noticeVO, realPath);
	      Map<String, String> map = new HashMap<>();
	      map.put("p", commonVO.getCurrentPage() + "");
	      map.put("s", commonVO.getPageSize() + "");
	      map.put("b",commonVO.getBlockSize() + "");
	      redirectAttributes.addFlashAttribute("map", map);
	      return "redirect:/board/notice.do";
	   }

	   
		@RequestMapping(value = "/selectSearchNotice.do")
		public String selectSearchNotice (@RequestParam String searchType, @RequestParam String searchText,HttpServletRequest request,
				@ModelAttribute CommonVO commVO, Model model) {
			PagingVO<NoticeVO> pv = noticeService.selectSearchList(commVO);
			pv.setSearchType(searchType);
			pv.setSearchText(searchText);
			model.addAttribute("pv", pv);
			model.addAttribute("cv", commVO);

			return "/board/notice";
			
		}
	   
	   

//캠핑후기 ===============================================================================================

	// 리뷰 목록보기
	@RequestMapping(value = "/board/review.do")
	public String review(HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model) {
		PagingVO<ReviewVO> pv = reviewService.selectList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "/board/review";
	}

	// 내용보기 : 글 1개를 읽어서 보여준다
	@RequestMapping(value = "/board/reviewView.do", method = RequestMethod.POST)
	public String reviewView(HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model, ReviewVO reviewVO, CommentVO commentVO) {
		reviewVO = reviewService.selectByIdx(reviewVO.getRv_idx());
		log.info(reviewService.selectByIdx(reviewVO.getRv_idx())+"!!!1");
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commVO);
		PagingVO<CommentVO> vo = commentService.selectList(reviewVO.getRv_idx());
		model.addAttribute("cm", vo);
		int mbIdx = reviewService.selectMb_idx(reviewVO.getRv_idx());
		model.addAttribute("mi", mbIdx);
		return "/board/reviewView";
	}

	// 저장
	@RequestMapping(value = "/board/replyInsertOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyInsertOk(@ModelAttribute CommonVO commVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해
		// 일단 VO로 받고
		commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고
		commentService.insert(commentVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("rv_idx",commentVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}

	@RequestMapping(value = "/board/reviewInsertOk.do", method = RequestMethod.POST)
	public String reviewInserOkPOST(@ModelAttribute CommonVO commVO, @ModelAttribute ReviewVO reviewVO,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해
																								// RedirectAttributes 변수
																								// 추가
		// 일단 VO로 받고
		reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고
		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);

		reviewService.insert(reviewVO);

		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" +
		// commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/review.do";
	}

	// 저장
	@RequestMapping(value = "/board/rereply.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> rereply(@ModelAttribute CommonVO commVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) { 
		// 일단 VO로 받고
		commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고
		commentService.reply(commentVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("rv_idx", commentVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}

	// 저장
	@RequestMapping(value = "/board/reviewInsert.do")
	public String reviewInsert(@ModelAttribute CommonVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "/board/reviewInsert";
	}

	@RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.GET)
	public String reviewInsertOk() {
		return "redirect:/review.do";
	}

	@RequestMapping(value = "/board/reviewUpdate.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updatePost(HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model,ReviewVO reviewVO ) {
		reviewVO = reviewService.selectByIdx(reviewVO.getRv_idx());
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commVO);
		return "/board/reviewUpdate";
	}

	@RequestMapping(value = "/board/reviewUpdateOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> updateOkPost(@ModelAttribute CommonVO commVO, @ModelAttribute ReviewVO reviewVO,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고
		reviewService.update(reviewVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("rv_idx", reviewVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}

	@RequestMapping(value = "/board/reviewDelete.do", method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommonVO commVO, Model model, ReviewVO reviewVO) {
		reviewVO = reviewService.selectByIdx(reviewVO.getRv_idx());
		model.addAttribute("fv", reviewVO);
		model.addAttribute("cv", commVO);
		return "reviewDelete";
	}

	
	@RequestMapping(value = "/board/reviewDeleteOk.do",method = RequestMethod.GET)
	public String deleteOk(@ModelAttribute CommonVO commVO,Model model) {
		return "redirect:/board/reviewDelete";
	}
	@RequestMapping(value = "/board/reviewDeleteOk.do",method = RequestMethod.POST)
	public String deleteOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute ReviewVO reviewVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		reviewService.delete(reviewVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/review.do";
	}
	
	@RequestMapping(value = "/board/replyUpdateOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyUpdateOk(@ModelAttribute CommonVO commVO,@ModelAttribute CommentVO commentVO, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		commentService.update(commentVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commentVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}
	
	@RequestMapping(value = "/board/replyDeleteOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyDeleteOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute CommentVO commentVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 실제 경로 구하고
		String realPath = request.getSession().getServletContext().getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		commentService.delete(commentVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commentVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}
	
	@RequestMapping(value = "/selectSearchReview.do")
	public String selectSearchReview (@RequestParam String searchType, @RequestParam String searchText,HttpServletRequest request,
		@ModelAttribute CommonVO commVO, Model model) {
		PagingVO<ReviewVO> pv = reviewService.selectSearchList(commVO);
		pv.setSearchType(searchType);
		pv.setSearchText(searchText);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);

		return "/board/review";
		
	}
	
	
	
	
	
	// QnA--------------------------------------------------------------------
	@RequestMapping(value = "/board/QnA.do")
	public String QnA(HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model) {
		PagingVO<QnAVO> qnaVO = qnaService.selectList(commVO);
		model.addAttribute("pv", qnaVO); 
		model.addAttribute("cv", commVO);
		return "/board/QnA";
	}
	
		// 원본글 인서트 폼
		@RequestMapping(value = "/board/QnAInsert.do")
		public String QnAInsert(@ModelAttribute CommonVO commVO, Model model) {
			model.addAttribute("cv", commVO);
			return "/board/QnAInsert";
		}
		
		@RequestMapping(value = "/board/QnAInsertOk.do")
		public String QnAInsertOk(
			@ModelAttribute CommonVO commVO,
			@ModelAttribute QnAVO qnaVO, 
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
		// 일단 VO로 받고
		qnaVO.setQna_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		qnaService.insert(qnaVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/QnA.do";
		}
		
		@RequestMapping(value = "/board/QnAUpdate.do",method = RequestMethod.POST , produces = "text/plain;charset=UTF-8")
		public String QnAUpdate(HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model, QnAVO qnaVO) {
			qnaVO = qnaService.selectByIdx(qnaVO.getQna_idx());
			model.addAttribute("qv", qnaVO);
			model.addAttribute("cv", commVO);
			return "/board/QnAUpdate";
		}
		
		@RequestMapping(value = "/board/QnAUpdateOk.do",method = RequestMethod.POST , produces = "application/json; charset=UTF8")
		@ResponseBody
		public Map<String, String> QnAUpdateOk(@RequestParam Map<String, String> params, HttpServletRequest request,
				@ModelAttribute CommonVO commVO,QnAVO qnaVO, Model model, RedirectAttributes redirectAttributes) {
			qnaVO.setQna_ip(request.getRemoteAddr());
			qnaService.update(qnaVO);
			Map<String, String> map = new HashMap<>();
			map.put("p", commVO.getCurrentPage() + "");
			map.put("s", commVO.getPageSize() + "");
			map.put("b",commVO.getBlockSize() + "");
			map.put("qna_idx",qnaVO.getQna_idx() + "");
			redirectAttributes.addFlashAttribute("map", map);
			return map;
		}
	
		
		
		@RequestMapping(value = "/board/QnAView.do") 
		public String QnAView(@RequestParam String role, HttpServletRequest request, Model model, @ModelAttribute CommonVO commVO, QnAVO qnaVO, QnAVO qnaVO2 ) {
			PagingVO<QnAVO> pagingVO = qnaService.selectList(commVO);
			model.addAttribute("pv", pagingVO);
			qnaVO = qnaService.selectByIdx(qnaVO.getQna_idx());
			model.addAttribute("qv", qnaVO);
			model.addAttribute("cv", commVO);
			qnaVO2 = qnaService.selectByIdxAnswer(qnaVO2.getQna_idx());
			model.addAttribute("qv2", qnaVO2);
			int mbIdx = qnaService.selectMb_idx(qnaVO.getQna_idx());
			model.addAttribute("mi", mbIdx);
			if(qnaVO.getQna_read()==0 && role.equals("ROLE_ADMIN")) {
				qnaVO.setQna_read(qnaVO.getQna_read()+1);
				qnaService.updateRead(qnaVO.getQna_idx());
			}
		
			return "/board/QnAView";
		}
		
		// 저장 
		@RequestMapping(value = "/board/answerInsertOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
		@ResponseBody
		public Map<String, String> answerInsertOk(
			@RequestParam String role,
			@ModelAttribute CommonVO commVO,
			@ModelAttribute QnAVO qnaVO, 
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
			
			// 일단 VO로 받고
		qnaVO.setQna_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		qnaService.answer(qnaVO);
		Map<String, String> map = new HashMap<>();
		map.put("p",commVO.getCurrentPage() + "");
		map.put("s",commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("qna_idx",qnaVO.getQna_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
		}
		
		// 저장 
		@RequestMapping(value = "/board/answerUpdateOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
		@ResponseBody
		public Map<String, String> answerUpdateOk(
				@RequestParam String role,
				@ModelAttribute CommonVO commVO,
				@ModelAttribute QnAVO qnaVO, 
				HttpServletRequest request, Model model,
				RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
			
			// 일단 VO로 받고
			qnaVO.setQna_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
			qnaService.updateAnswer(qnaVO);
			Map<String, String> map = new HashMap<>();
			map.put("p",commVO.getCurrentPage() + "");
			map.put("s",commVO.getPageSize() + "");
			map.put("b",commVO.getBlockSize() + "");
			map.put("qna_idx",qnaVO.getQna_idx() + "");
			redirectAttributes.addFlashAttribute("map", map);
			return map;
		}
		
		
		@RequestMapping(value = "/board/QnADeleteOk.do")
		public String QnADeleteOk(@RequestParam int qna_idx, @RequestParam int mb_idx, QnAVO qnaVO,CommonVO commVO, HttpSession session,
				HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes ) throws IOException {
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if(mb_idx==qnaService.selectMb_idx(qna_idx)) {
				qnaService.delete(qnaVO);
				out.println("<script language='javascript'>");
				out.println("alert('정상적으로 삭제되었습니다.');");
				out.println("location.href='/board/QnA.do';");
				out.println("</script>");
				out.close();
			}else {	
				out.println("<script language='javascript'>");
				out.println("alert('에러');");
				out.println("location.href='/board/QnA.do';");
				out.println("</script>");
				out.close();
			}

			return "QnA";
		}
		
		@RequestMapping(value = "/selectSearchQnA.do")
		public String selectSearchQnA (@RequestParam String searchType, @RequestParam String searchText,HttpServletRequest request,
				@ModelAttribute CommonVO commVO, Model model) {
			PagingVO<QnAVO> pv = qnaService.selectSearchList(commVO);
			pv.setSearchType(searchType);
			pv.setSearchText(searchText);
			model.addAttribute("pv", pv);
			model.addAttribute("cv", commVO);

			return "/board/QnA";
			
		}
		
		
// 다운로드 서머노트	=========================================
			

			@RequestMapping(value = "/download.do")
			public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
				String ofileName = (String) params.get("of"); // 원본이름
				String sfileName = (String) params.get("sf"); // 저장이름
				mv.setViewName("downloadView");
				mv.addObject("ofileName", ofileName);
				mv.addObject("sfileName", sfileName);
				return mv;
			}
			
			// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
			@RequestMapping(value = "/imageUpload.do", produces = "text/plain;charset=UTF-8")
			@ResponseBody
			public String imageUpload(HttpServletRequest request, MultipartFile file) {
				log.info("{}의 imageUpload 호출 : {}",this.getClass().getName(),request + "\n" + file);
				
				String filePath = "";
				String realPath = request.getSession().getServletContext().getRealPath("contentfile");
				File folder = new File(realPath);
				if (!folder.exists()) {
					try{
					      folder.mkdir();
					    System.out.println("폴더가 생성되었습니다.");
				        } 
				        catch(Exception e){
					    e.getStackTrace();
					}        
			         }else {
					System.out.println("이미 폴더가 생성되어 있습니다.");
				}
				// 파일은 MultipartFile 객체가 받아준다.
				if(file!=null && file.getSize()>0) { // 파일이 존재 한다면
					try {
						// 저장이름
						String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(file.getBytes(), target);
						filePath = request.getContextPath() + "\\contentfile\\" + saveName;			
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				log.info("{}의 imageUpload 리턴 : {}",this.getClass().getName(),filePath);
				return filePath;
			}
		}


