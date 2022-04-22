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
		// 스프링 시큐리티 에서의 권한 오류시 작동할 로직
		AccessDeniedException ade = (AccessDeniedException) req.getAttribute(WebAttributes.ACCESS_DENIED_403);
		log.info("ex : {}", ade);
		model.addAttribute("auth", auth);
		model.addAttribute("errMsg", ade);
		return "403";
	}

	// 공지사항 notice======================================================
	// 공지사항 list
	@RequestMapping(value = "/board/notice.do")
	public String noticeSelectList(@ModelAttribute CommonVO commonVO, Model model) {
		PagingVO<NoticeVO> pv = noticeService.selectList(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO);
		return "/board/notice";
	}

	// 공지사항 1개 보기
	@RequestMapping(value = "/board/noticeView.do")
	public String noticeView(@ModelAttribute CommonVO commonVO, NoticeVO noticeVO, Model model) {
		noticeVO = noticeService.selectByIdx(noticeVO.getNt_idx());
		model.addAttribute("nv", noticeVO);
		model.addAttribute("cv", commonVO);
		return "/board/noticeView";
	}

	// 공지사항 새글쓰기
	@RequestMapping(value = "/board/noticeInsert.do", method = RequestMethod.GET)
	public String noticeInsert() {
		return "403";
	}

	// 공지사항 새글쓰기
	@RequestMapping(value = "/board/noticeInsert.do", method = RequestMethod.POST)
	public String noticeInsert(@ModelAttribute CommonVO commonVO, Model model) {
		return "/board/noticeInsert";
	}

	// 공지사항 get으로 받으면 403으로 보냄
	@RequestMapping(value = "/board/noticeInsertOK.do", method = RequestMethod.GET)
	public String noticeInsertOk() {
		return "403";
	}

	// 공지사항 새글쓰기 수행
	@RequestMapping(value = "/board/noticeInsertOk.do", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public String noticeInsertOK(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO,
			MultipartHttpServletRequest request, Model model) {
		// noticeVO에 ip set
		noticeVO.setNt_ip(request.getRemoteAddr());
		// 파일 리스트 배열로 만들고
		List<FileUploadVO> fileList = new ArrayList<>();
		// uploadFile로 된 파일 받아오기
		List<MultipartFile> multipartFiles = request.getFiles("uploadFile");
		// 파일이 존재하면
		if (multipartFiles != null && multipartFiles.size() > 0) {
			for (MultipartFile multipartFile : multipartFiles) {
				if (multipartFile != null && multipartFile.getSize() > 0) {
					FileUploadVO fileUploadVO = new FileUploadVO();
					try {
						// upload절대경로 체크하여 폴더 있는지 여부 판단 변수
						String realPath = request.getSession().getServletContext().getRealPath("upload");
						// 랜덤으로 저장 이름 만들어주기
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						File target = new File(realPath, saveName);
						File folder = new File(realPath);
						// 폴더가 없으면
						if (!folder.exists()) {
							try {
								// 만들어준다
								folder.mkdir();
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
						// 파일 크기 확인, 기존 경로 이름으로 저장
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// 오리지널 이름 vo에 저장
						fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
						// 저장 이름 vo에 저장
						fileUploadVO.setSaveName(saveName);
						// 파일리스트에 추가
						fileList.add(fileUploadVO);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 추가된 파일 vo에 넣고
		noticeVO.setFileList(fileList);
		// db 저장
		noticeService.insert(noticeVO);
		return "redirect:/board/notice.do";
	}

	// 공지사항 수정폼으로 이동
	@RequestMapping(value = "/board/noticeUpdate.do", method = RequestMethod.POST)
	public String noticeUpdate(@ModelAttribute CommonVO commonVO, NoticeVO noticeVO, Model model) {
		noticeVO = noticeService.selectByIdx(noticeVO.getNt_idx());
		model.addAttribute("nv", noticeVO);
		model.addAttribute("cv", commonVO);
		return "/board/noticeUpdate";
	}

	// 겟으로 접근시 403으로 보
	@RequestMapping(value = "/board/noticeUpdate.do", method = RequestMethod.GET)
	public String noticeUpdate() {
		return "403";
	}

	// 공지사항 수정 수행
	@RequestMapping(value = "/board/noticeUpdateOk.do", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public String noticeUpdateOk(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO,
			MultipartHttpServletRequest request, Model model) {
		// noticeVO에 ip set
		noticeVO.setNt_ip(request.getRemoteAddr());
		// 파일 리스트 배열로 만들고
		List<FileUploadVO> fileList = new ArrayList<>();
		// uploadFile로 된 파일 받아오기
		List<MultipartFile> multipartFiles = request.getFiles("uploadFile");
		// upload절대경로 변수
		String realPath = request.getSession().getServletContext().getRealPath("upload");
		// 파일이 존재하면
		if (multipartFiles != null && multipartFiles.size() > 0) {
			for (MultipartFile multipartFile : multipartFiles) {
				if (multipartFile != null && multipartFile.getSize() > 0) {
					FileUploadVO fileUploadVO = new FileUploadVO();
					try {

						// 랜덤으로 저장 이름 만들어주기
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						File target = new File(realPath, saveName);
						File folder = new File(realPath);
						// 폴더가 없으면
						if (!folder.exists()) {
							try {
								// 만들어준다
								folder.mkdir();
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
						// 파일 크기 확인, 기존 경로 이름으로 저장
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// 오리지널 이름 vo에 저장
						fileUploadVO.setOriginalName(multipartFile.getOriginalFilename());
						// 저장 이름 vo에 저장
						fileUploadVO.setSaveName(saveName);
						// 파일리스트에 추가
						fileList.add(fileUploadVO);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 추가된 파일 vo에 넣고
		noticeVO.setFileList(fileList);
		String[] delFiles = request.getParameterValues("delFile");
		// db 수정
		noticeService.update(noticeVO, realPath, delFiles);
		return "redirect:/board/notice.do";
	}

	// 겟으로 오면 403으로 보냄
	@RequestMapping(value = "/board/noticeDelete.do", method = RequestMethod.GET)
	public String noticeDelete() {
		return "403";
	}

	// 공지사항 삭제
	@RequestMapping(value = "/board/noticeDelete.do", method = RequestMethod.POST)
	public String noticeDelete(@ModelAttribute CommonVO commonVO, @ModelAttribute NoticeVO noticeVO,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// upload절대경로 변수
		String realPath = request.getSession().getServletContext().getRealPath("upload");
		// 삭제
		noticeService.delete(noticeVO, realPath);
		return "redirect:/board/notice.do";
	}

	// 공지사항 검색 로직
	@RequestMapping(value = "/selectSearchNotice.do")
	public String selectSearchNotice(HttpServletRequest request, @ModelAttribute CommonVO commonVO, Model model) {
		log.info("#######" + commonVO);
		// 검색한 내용에 맞춰 리스트 해준다
		PagingVO<NoticeVO> pv = noticeService.selectSearchList(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO);
		return "/board/notice";
	}

//캠핑후기 ===============================================================================================

	// 리뷰 목록보기
	@RequestMapping(value = "/board/review.do")
	public String review(HttpServletRequest request, @ModelAttribute CommonVO commonVO, Model model) {
		// 리뷰 list
		PagingVO<ReviewVO> pv = reviewService.selectList(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO);
		return "/board/review";
	}

	// 내용보기 : 글 1개를 읽어서 보여준다
	@RequestMapping(value = "/board/reviewView.do", method = RequestMethod.POST)
	public String reviewView(HttpServletRequest request, @ModelAttribute CommonVO commonVO, Model model,
			ReviewVO reviewVO, CommentVO commentVO) {
		// 리뷰 1개 보기
		// 한개의 후기글의 정보를 가져와서
		reviewVO = reviewService.selectByIdx(reviewVO.getRv_idx());
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commonVO);
		// 댓글,대댓글 리스트로 가져오기
		PagingVO<CommentVO> cm = commentService.selectList(reviewVO.getRv_idx());
		model.addAttribute("cm", cm);
		// 해당 원본글의 글쓴이면, 수정 삭제 버튼 활성화 시키기위해 mb_idx를 불러옴
		int mbIdx = reviewService.selectMb_idx(reviewVO.getRv_idx());
		model.addAttribute("mi", mbIdx);
		return "/board/reviewView";
	}

	// 리뷰 새글쓰기
	@RequestMapping(value = "/board/reviewInsertOk.do", method = RequestMethod.POST)
	public String reviewInserOkPOST(@ModelAttribute CommonVO commonVO, @ModelAttribute ReviewVO reviewVO,
			HttpServletRequest request, Model model) {
		// ip를 넣어준다
		reviewVO.setRv_ip(request.getRemoteAddr());
		// 저장
		reviewService.insert(reviewVO);
		return "redirect:/board/review.do";
	}

	// 댓글 저장
	@RequestMapping(value = "/board/replyInsertOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyInsertOk(@ModelAttribute CommonVO commonVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, Model model) {
		// 댓글 작성시 ip받아서 넣기
		commentVO.setCo_ip(request.getRemoteAddr());
		// 댓글 저장
		commentService.insert(commentVO);
		// 댓글 저장후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commonVO.getCurrentPage() + "");
		map.put("s", commonVO.getPageSize() + "");
		map.put("b", commonVO.getBlockSize() + "");
		map.put("rv_idx", commentVO.getRv_idx() + "");
		return map;
	}

	// 대댓글 저장
	@RequestMapping(value = "/board/rereply.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> rereply(@ModelAttribute CommonVO commonVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, Model model) {
		// 댓글 작성시 ip받아서 넣기
		commentVO.setCo_ip(request.getRemoteAddr());
		// 대댓글 저장
		commentService.reply(commentVO);
		// 대댓글 저장후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commonVO.getCurrentPage() + "");
		map.put("s", commonVO.getPageSize() + "");
		map.put("b", commonVO.getBlockSize() + "");
		map.put("rv_idx", commentVO.getRv_idx() + "");
		return map;
	}

	// 리뷰 저장폼으로 이동
	@RequestMapping(value = "/board/reviewInsert.do")
	public String reviewInsert(@ModelAttribute CommonVO commonVO, Model model) {
		return "/board/reviewInsert";
	}

	// 겟으로 접근하면 리뷰 리스트로 보내버린다
	@RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.GET)
	public String reviewInsertOk() {
		return "redirect:/review.do";
	}

	// 리뷰 수정폼으로 이동
	@RequestMapping(value = "/board/reviewUpdate.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updatePost(HttpServletRequest request, @ModelAttribute CommonVO commonVO, Model model,
			ReviewVO reviewVO) {
		// 리뷰글 번호를 가져와 정보 가져오기
		reviewVO = reviewService.selectByIdx(reviewVO.getRv_idx());
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commonVO);
		return "/board/reviewUpdate";
	}

	// 리뷰 수정
	@RequestMapping(value = "/board/reviewUpdateOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> updateOkPost(@ModelAttribute CommonVO commonVO, @ModelAttribute ReviewVO reviewVO,
			HttpServletRequest request, Model model) {
		// ip 넣어주고
		reviewVO.setRv_ip(request.getRemoteAddr());
		// 리뷰 수정
		reviewService.update(reviewVO);
		// 리뷰 수정후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commonVO.getCurrentPage() + "");
		map.put("s", commonVO.getPageSize() + "");
		map.put("b", commonVO.getBlockSize() + "");
		map.put("rv_idx", reviewVO.getRv_idx() + "");
		return map;
	}

	// 겟으로 접근시 리뷰 리스트로 보냄
	@RequestMapping(value = "/board/reviewDeleteOk.do", method = RequestMethod.GET)
	public String deleteOk() {
		return "redirect:/board/review";
	}

	// 리뷰 삭제
	@RequestMapping(value = "/board/reviewDeleteOk.do", method = RequestMethod.POST)
	public String deleteOk(@ModelAttribute CommonVO commonVO, @ModelAttribute ReviewVO reviewVO,
			HttpServletRequest request) {
		reviewService.delete(reviewVO);
		return "redirect:/board/review.do";
	}

	// 댓글 수정 수행
	@RequestMapping(value = "/board/replyUpdateOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyUpdateOk(@ModelAttribute CommonVO commonVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, Model model) {
		// 일단 VO로 받고
		commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고
		commentService.update(commentVO);
		// 댓글 수정후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commonVO.getCurrentPage() + "");
		map.put("s", commonVO.getPageSize() + "");
		map.put("b", commonVO.getBlockSize() + "");
		map.put("rv_idx", commentVO.getRv_idx() + "");
		return map;
	}

	// 댓글 삭제 수행
	@RequestMapping(value = "/board/replyDeleteOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyDeleteOkPost(@ModelAttribute CommonVO commonVO, @ModelAttribute CommentVO commentVO,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		// 댓글 삭제 수행
		commentService.delete(commentVO);
		// 댓글 삭제후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commonVO.getCurrentPage() + "");
		map.put("s", commonVO.getPageSize() + "");
		map.put("b", commonVO.getBlockSize() + "");
		map.put("rv_idx", commentVO.getRv_idx() + "");
		return map;
	}

	// 리뷰에서 검색 기능
	@RequestMapping(value = "/selectSearchReview.do")
	public String selectSearchReview(HttpServletRequest request, @ModelAttribute CommonVO commonVO, Model model) {
		PagingVO<ReviewVO> pv = reviewService.selectSearchList(commonVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commonVO);
		return "/board/review";
	}

	// QnA--------------------------------------------------------------------
	// QnA게시판 list
	@RequestMapping(value = "/board/QnA.do")
	public String QnA(HttpServletRequest request, @ModelAttribute CommonVO commVO, Model model) {
		PagingVO<QnAVO> qnaVO = qnaService.selectList(commVO);
		model.addAttribute("pv", qnaVO);
		model.addAttribute("cv", commVO);
		return "/board/QnA";
	}

	// 질문 글쓰기폼으로 이동
	@RequestMapping(value = "/board/QnAInsert.do")
	public String QnAInsert(@ModelAttribute CommonVO commVO, Model model) {
		return "/board/QnAInsert";
	}
	
	// 질문 글쓰기 수행
	@RequestMapping(value = "/board/QnAInsertOk.do")
	public String QnAInsertOk(@ModelAttribute CommonVO commVO, @ModelAttribute QnAVO qnaVO, HttpServletRequest request,
			Model model) { 
		// ip 추가
		qnaVO.setQna_ip(request.getRemoteAddr()); 
		qnaService.insert(qnaVO);
		return "redirect:/board/QnA.do";
	}
	
	// 질문 수정폼 이동
	@RequestMapping(value = "/board/QnAUpdate.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String QnAUpdate(HttpServletRequest request, @ModelAttribute CommonVO commVO, Model model, QnAVO qnaVO) {
		qnaVO = qnaService.selectByIdx(qnaVO.getQna_idx());
		model.addAttribute("qv", qnaVO);
		model.addAttribute("cv", commVO);
		return "/board/QnAUpdate";
	}
	
	// 질문 수정 이행
	@RequestMapping(value = "/board/QnAUpdateOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> QnAUpdateOk(@RequestParam Map<String, String> params, HttpServletRequest request,
			@ModelAttribute CommonVO commVO, QnAVO qnaVO, Model model) {
		qnaVO.setQna_ip(request.getRemoteAddr());
		qnaService.update(qnaVO);
		// 질문 수정후 같은 페이지에 머물기 위해 값들을 받아온다
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("qna_idx", qnaVO.getQna_idx() + "");
		return map;
	}
	// 답변 1개보기
	@RequestMapping(value = "/board/QnAView.do")
	public String QnAView(@RequestParam String role, HttpServletRequest request, Model model,
			@ModelAttribute CommonVO commVO, QnAVO qnaVO, QnAVO qnaVO2) {
		PagingVO<QnAVO> pagingVO = qnaService.selectList(commVO);
			model.addAttribute("pv", pagingVO);
		// 해당글 상세 내역
		qnaVO = qnaService.selectByIdx(qnaVO.getQna_idx());
			model.addAttribute("qv", qnaVO);
			model.addAttribute("cv", commVO);
		// 해당글의 답변 내역 불러오기
		qnaVO2 = qnaService.selectByIdxAnswer(qnaVO2.getQna_idx());
			model.addAttribute("qv2", qnaVO2);
		// 본인이 쓴 글인지 판단하기위해서 사용
		int mbIdx = qnaService.selectMb_idx(qnaVO.getQna_idx());
			model.addAttribute("mi", mbIdx);
		// 관리자가 해당 글을 읽었을 경우 관리자가 읽었는지 보여주기위해 +1 함
		if (qnaVO.getQna_read() == 0 && role.equals("ROLE_ADMIN")) {
			qnaVO.setQna_read(qnaVO.getQna_read() + 1);
			qnaService.updateRead(qnaVO.getQna_idx());
		}
		return "/board/QnAView";
	}

	// 답변 저장 수행
	@RequestMapping(value = "/board/answerInsertOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> answerInsertOk(@RequestParam String role, @ModelAttribute CommonVO commVO,
			@ModelAttribute QnAVO qnaVO, HttpServletRequest request, Model model) { 
		// ip 추가
		qnaVO.setQna_ip(request.getRemoteAddr());
		qnaService.answer(qnaVO);
		Map<String, String> map = new HashMap<>();
		// 답변 저장후 같은 페이지에 머물기 위해 값들을 받아온다
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("qna_idx", qnaVO.getQna_idx() + "");
		return map;
	}

	// 답변 수정 수행
	@RequestMapping(value = "/board/answerUpdateOk.do", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> answerUpdateOk(@RequestParam String role, @ModelAttribute CommonVO commVO,
			@ModelAttribute QnAVO qnaVO, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가

		// ip 추가
		qnaVO.setQna_ip(request.getRemoteAddr()); 
		qnaService.updateAnswer(qnaVO);
		Map<String, String> map = new HashMap<>();
		// 답변 수정후 같은 페이지에 머물기 위해 값들을 받아온다
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b", commVO.getBlockSize() + "");
		map.put("qna_idx", qnaVO.getQna_idx() + "");
		return map;
	}

	// 질문 삭제 수행
	@RequestMapping(value = "/board/QnADeleteOk.do")
	public String QnADeleteOk(@RequestParam int qna_idx, @RequestParam int mb_idx, QnAVO qnaVO, CommonVO commVO,
			HttpSession session, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 글쓴이가 로그인한 사람이면 삭제 가능
		if (mb_idx == qnaService.selectMb_idx(qna_idx)) {
			qnaService.delete(qnaVO);
			out.println("<script language='javascript'>");
			out.println("alert('정상적으로 삭제되었습니다.');");
			out.println("location.href='/board/QnA.do';");
			out.println("</script>");
			out.close();
		} else {
			out.println("<script language='javascript'>");
			out.println("alert('에러');");
			out.println("location.href='/board/QnA.do';");
			out.println("</script>");
			out.close();
		}

		return "QnA";
	}

	// QnA게시판 검색 기능 
	@RequestMapping(value = "/selectSearchQnA.do")
	public String selectSearchQnA(HttpServletRequest request, @ModelAttribute CommonVO commVO, Model model) {
		PagingVO<QnAVO> pv = qnaService.selectSearchList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "/board/QnA";

	}

	// 다운로드 서머노트	=========================================
	// 서머노트
	@RequestMapping(value = "/download.do")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of"); // 원본이름
		String sfileName = (String) params.get("sf"); // 저장이름
		// 모델앤뷰 이름 세팅하고, 사용하게끔 값을 넣는다
		mv.setViewName("downloadView");
		mv.addObject("ofileName", ofileName);
		mv.addObject("sfileName", sfileName);
		return mv;
	}

	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
	@RequestMapping(value = "/imageUpload.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String imageUpload(HttpServletRequest request, MultipartFile file) {
		String filePath = "";
		// 절대경로 설정
		String realPath = request.getSession().getServletContext().getRealPath("contentfile");
		// 폴더가 없으면 폴더를 만들어준다
		File folder = new File(realPath);
		if (!folder.exists()) {
			try {
				folder.mkdir();
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
		// 파일은 MultipartFile 객체가 받아준다
		// 파일이 존재 한다면
		if (file != null && file.getSize() > 0) { 
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
		return filePath;
	}
}
