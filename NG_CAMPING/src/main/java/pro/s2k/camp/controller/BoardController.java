package pro.s2k.camp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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

import com.google.gson.JsonObject;
import com.mysql.fabric.xmlrpc.base.Member;

import lombok.extern.slf4j.Slf4j;
import pro.s2k.camp.dao.QnADAO;
import pro.s2k.camp.service.CommentService;
import pro.s2k.camp.service.MemberService;
import pro.s2k.camp.service.QnAService;
import pro.s2k.camp.service.ReviewService;
import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.MemberVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;
import pro.s2k.camp.vo.ReviewVO;



@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private QnAService qnaService;
	

	// SummerNote 컨트롤러 ---------------------------------------------------
	   @RequestMapping(value = "/imageUpload.do", produces = "text/plain;charset=UTF-8",method = RequestMethod.POST)
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
	//	@RequestMapping(value="/imageUpload.do", produces = "application/json; charset=utf8")
//	@ResponseBody
//	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, MultipartHttpServletRequest request )  {
//		JsonObject jsonObject = new JsonObject();
//		
//        /*
//		 * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
//		 */
//		
//		// 내부경로로 저장
//		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
//		String fileRoot = contextRoot+"resources/fileupload/";
//		
//		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
//		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
//		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
//		
//		File targetFile = new File(fileRoot + savedFileName);	
//		try {
//			InputStream fileStream = multipartFile.getInputStream();
//			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
//			jsonObject.addProperty("url", "/summernote/resources/fileupload/"+savedFileName); // contextroot + resources + 저장할 내부 폴더명
//			jsonObject.addProperty("responseCode", "success");
//				
//		} catch (IOException e) {
//			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
//			jsonObject.addProperty("responseCode", "error");
//			e.printStackTrace();
//		}
//		String a = jsonObject.toString();
//		return a;
//	}
//	// 다운로드가 가능하게 하자
//	@RequestMapping(value = "/download.do")
//	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
//		String ofileName = (String) params.get("of");
//		String sfileName = (String) params.get("sf");
//		mv.setViewName("downlaodView");
//		mv.addObject("ofileName",ofileName);
//		mv.addObject("sfileName",sfileName);
//		return mv;
//	}
//	
//	// 섬머노트의 큰 이미지를 처리하기위한 주소 1개를 생성해 주어야 한다.
//	@RequestMapping(value = "/imageUpload.do", method = RequestMethod.GET)
//	@ResponseBody
//	public String imageUpload(MultipartHttpServletRequest request) {
//		String saveName="";
//		@SuppressWarnings("deprecation")
//		String realPath = request.getRealPath("upload");
//		
//		// 모든 파일을 리스트로 받기
//		List<MultipartFile> list = request.getFiles("file");
//		
//		if(list!=null && list.size()>0) { // 리스트에 내용이 있다면
//			for(MultipartFile file : list) { // 반복
//				// 파일 받기
//				if(file!=null && file.getSize()>0) { // 파일이 존재 한다면
//					try {
//						// 이름 중복 처리를 하기위해 UUID로 중복되지않는 문자열을 생성하고 뒤에 원본이름을 붙여준다.
//						saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//						// 저장할 파일 객체 생성
//						File   saveFile = new File(realPath, saveName);
//						// 파일을 복사
//						FileCopyUtils.copy(file.getBytes(), saveFile);
//					}catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		System.out.println(request.getContextPath() + "/upload/" + saveName);
//		return request.getContextPath() + "/upload/" + saveName; // 실제 그림이 저장된 경로를 리턴해주게 만든다.
//	}
	// SummerNote 컨트롤러 ---------------------------------------------------
	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
//		@RequestMapping(value = "/imageUpload.do")
//		@ResponseBody
//		public String imageUpload(MultipartHttpServletRequest request, MultipartFile file) {
//			log.info("{}의 imageUpload 호출 : {}",this.getClass().getName(),request + "\n" + file);
//			String filePath = "";
//			String realPath = request.getRealPath("upload");
//			// 파일은 MultipartFile 객체가 받아준다.
//			if(file!=null && file.getSize()>0) { // 파일이 존재 한다면
//				try {
//					// 저장이름
//					String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//					// 저장
//					File target = new File(realPath, saveName);
//					FileCopyUtils.copy(file.getBytes(), target);
//					filePath = request.getContextPath() + "/upload/" + 	saveName;			
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			log.info("{}의 imageUpload 리턴 : {}",this.getClass().getName(),filePath);
//			return filePath;
//		}

	
//
//	@RequestMapping(value = "/board/insertOk", method = RequestMethod.POST)
//	public String insertOkPost(
//			@ModelAttribute CommVO commVO,
//			@ModelAttribute FileBoardVO fileBoardVO, 
//			MultipartHttpServletRequest request, Model model,
//			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
//		// 일단 VO로 받고
//		fileBoardVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
//		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + fileBoardVO);
//
//		// 넘어온 파일 처리를 하자
//		List<FileBoardFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
//		
//		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
//		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
//			for(MultipartFile multipartFile : multipartFiles) {
//				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
//					FileBoardFileVO fileBoardFileVO = new FileBoardFileVO(); // 객체 생성하고
//					// 파일 저장하고
//					try {
//						// 저장이름
//						String realPath = request.getRealPath("upload");
//						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
//						// 저장
//						File target = new File(realPath, saveName);
//						FileCopyUtils.copy(multipartFile.getBytes(), target);
//						// vo를 채우고
//						fileBoardFileVO.setOriName(multipartFile.getOriginalFilename());
//						fileBoardFileVO.setSaveName(saveName);
//						// 리스트에 추가하고
//						fileList.add(fileBoardFileVO); 
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		fileBoardVO.setFileList(fileList);
//		// 서비스를 호출하여 저장을 수행한다.
//		fileBoardService.insert(fileBoardVO);
//		
//		// redirect시 GET전송 하기
//		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
//		// redirect시 POST전송 하기
//		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
//		Map<String, String> map = new HashMap<>();
//		map.put("p", "1");
//		map.put("s", commVO.getPageSize() + "");
//		map.put("b",commVO.getBlockSize() + "");
//		redirectAttributes.addFlashAttribute("map", map);
//		return "redirect:/board/list";
//	}
	
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
		
		return "review";
	}
	
	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/reviewView.do", method = RequestMethod.POST)
	public String reviewView(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model) {
		log.info("{}의 view호출 : {}", this.getClass().getName(), commVO);
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
			commVO.setRv_idx(Integer.parseInt(params.get("rv_idx")));
		}
		ReviewVO reviewVO = reviewService.selectByIdx(commVO.getRv_idx());
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commVO);
		CommentVO commentVO = commentService.selectByIdx(commVO.getRv_idx());
		model.addAttribute("f", commentVO); // 이걸로 한개를 불러옴.
		PagingVO<CommentVO> vo = commentService.selectList(commVO.getRv_idx());
		model.addAttribute("cm", vo);
		int mbIdx = reviewService.selectMb_idx(commVO.getRv_idx());
		model.addAttribute("mi", mbIdx);
		return "reviewView";
	}
	
	
	// 저장 
	@RequestMapping(value = "/replyInsertOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyInsertOk(
		@ModelAttribute CommonVO commVO,
		@ModelAttribute CommentVO commentVO, 
		HttpServletRequest request, Model model,
		RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
	// 일단 VO로 받고
	commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
	commentService.insert(commentVO);
	Map<String, String> map = new HashMap<>();
	map.put("p",commVO.getCurrentPage() + "");
	map.put("s",commVO.getPageSize() + "");
	map.put("b",commVO.getBlockSize() + "");
	map.put("rv_idx",commVO.getRv_idx() + "");
	redirectAttributes.addFlashAttribute("map", map);
	return map;
	}
	
	 @RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.POST)
     public String reviewInserOkPOST(
        @ModelAttribute CommonVO commVO,
        @ModelAttribute ReviewVO reviewVO, 
        HttpServletRequest request, Model model,
        RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
     // 일단 VO로 받고
     reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
     log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);

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
	
	
	// 저장 
	@RequestMapping(value = "/rereply.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> rereply(
		@ModelAttribute CommonVO commVO,
		@ModelAttribute CommentVO commentVO, 
		HttpServletRequest request, Model model,
		RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
	// 일단 VO로 받고
	commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
	commentService.reply(commentVO);
	Map<String, String> map = new HashMap<>();
	map.put("p",commVO.getCurrentPage() + "");
	map.put("s",commVO.getPageSize() + "");
	map.put("b",commVO.getBlockSize() + "");
	map.put("rv_idx",commVO.getRv_idx() + "");
	redirectAttributes.addFlashAttribute("map", map);
	return map;
	}

	// 저장 
	@RequestMapping(value = "/reviewInsert.do")
	public String reviewInsert(@ModelAttribute CommonVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "reviewInsert";
	}
	@RequestMapping(value = "/reviewInsertOk.do", method = RequestMethod.GET)
	public String reviewInsertOk() {
		return "redirect:/review.do";
	}



	// 수정하기
//	@RequestMapping(value = "/reviewUpdate.do", method = RequestMethod.GET)
//	public String update(ModelAttribute CommonVO commVO,Model model) {
//		log.info("updateGet 호출");
//		return "redirect:/review";
//	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reviewUpdate.do",method = RequestMethod.POST , produces = "text/plain;charset=UTF-8")
	public String updatePost(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
			commVO.setRv_idx(Integer.parseInt(params.get("rv_idx")));
		}
		ReviewVO reviewVO = reviewService.selectByIdx(commVO.getRv_idx());
		model.addAttribute("rv", reviewVO);
		model.addAttribute("cv", commVO);
		return "reviewUpdate";
	}
	


//	@RequestMapping(value = "/reviewUpdateOk.do",method = RequestMethod.GET)
//	public String updateOk(@ModelAttribute CommonVO commVO,Model model) {
//		return "redirect:/review";
//	}
	
	
	@RequestMapping(value = "/reviewUpdateOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> updateOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute ReviewVO reviewVO, 
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		reviewVO.setRv_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + reviewVO);

		reviewService.update(reviewVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/replyUpdate.do",method = RequestMethod.POST , produces = "text/plain;charset=UTF-8")
	public String replyUpdate(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
			commVO.setCo_idx(Integer.parseInt(params.get("co_idx")));
		}
		CommentVO commentVO = commentService.selectByIdx(commVO.getCo_idx());
		model.addAttribute("f", commentVO);
		model.addAttribute("cv", commVO);
		return "reviewUpdate";
	}
	
	@RequestMapping(value = "/replyUpdateOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyUpdateOk(@ModelAttribute CommonVO commVO,
			@ModelAttribute CommentVO commentVO, 
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		commentVO.setCo_ip(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		
		commentService.update(commentVO);
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("co_idx",commVO.getCo_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
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
		// 서비스를 호출하여 삭제를 수행하고
		
		reviewService.delete(reviewVO);
		log.info(reviewVO+"히히 성공");
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/review.do";
	}
	
	@RequestMapping(value = "/replyDeleteOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	@ResponseBody
	public Map<String, String> replyDeleteOkPost(@ModelAttribute CommonVO commVO,
			@ModelAttribute CommentVO commentVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + commentVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		commentService.delete(commentVO);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("rv_idx",commVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
	}
	
	

	
	// QnA--------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/QnA.do")
	public String QnA(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
		}
		PagingVO<QnAVO> qnaVO = qnaService.selectList(commVO);
		model.addAttribute("pv", qnaVO); 
		model.addAttribute("cv", commVO);
		return "QnA";
	}
	
		// 원본글 인서트 폼
		@RequestMapping(value = "/QnAInsert.do")
		public String QnAInsert(@ModelAttribute CommonVO commVO, Model model) {
			model.addAttribute("cv", commVO);
			return "QnAInsert";
		}
		
		@RequestMapping(value = "/QnAInsertOk.do")
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
		return "redirect:/QnA.do";
		}
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/QnAUpdate.do",method = RequestMethod.POST , produces = "text/plain;charset=UTF-8")
		public String QnAUpdate(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommonVO commVO,Model model) {
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if(flashMap!=null) {
				params = (Map<String, String>) flashMap.get("map");
				commVO.setP(Integer.parseInt(params.get("p")));
				commVO.setS(Integer.parseInt(params.get("s")));
				commVO.setB(Integer.parseInt(params.get("b")));
				commVO.setQna_idx(Integer.parseInt(params.get("qna_idx")));
			}
			QnAVO qnaVO = qnaService.selectByIdx(commVO.getQna_idx());
			model.addAttribute("qv", qnaVO);
			model.addAttribute("cv", commVO);
			return "QnAUpdate";
		}
		
		@RequestMapping(value = "/QnAUpdateOk.do",method = RequestMethod.POST , produces = "application/json; charset=UTF8")
		@ResponseBody
		public Map<String, String> QnAUpdateOk(@RequestParam Map<String, String> params, HttpServletRequest request,
				@ModelAttribute CommonVO commVO,QnAVO qnaVO, Model model, RedirectAttributes redirectAttributes) {
			qnaVO.setQna_ip(request.getRemoteAddr());
			qnaService.update(qnaVO);
			Map<String, String> map = new HashMap<>();
			map.put("p", commVO.getCurrentPage() + "");
			map.put("s", commVO.getPageSize() + "");
			map.put("b",commVO.getBlockSize() + "");
			map.put("qna_idx",commVO.getQna_idx() + "");
			redirectAttributes.addFlashAttribute("map", map);
			return map;
		}
	
		
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/QnAView.do") 
		public String QnAView(@RequestParam Map<String, String> params, @RequestParam String role, HttpServletRequest request, Model model, 
				@ModelAttribute CommonVO commVO ) {
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if(flashMap!=null) {
				params = (Map<String, String>) flashMap.get("map");
				commVO.setP(Integer.parseInt(params.get("p")));
				commVO.setS(Integer.parseInt(params.get("s")));
				commVO.setB(Integer.parseInt(params.get("b")));
				commVO.setQna_idx(Integer.parseInt(params.get("qna_idx")));
			}
			PagingVO<QnAVO> pagingVO = qnaService.selectList(commVO);
			model.addAttribute("pv", pagingVO);
			QnAVO qnaVO = qnaService.selectByIdx(commVO.getQna_idx());
			model.addAttribute("qv", qnaVO);
			model.addAttribute("cv", commVO);
			QnAVO qnaVO2 = qnaService.selectByIdxAnswer(commVO.getQna_idx());
			log.info(commVO.getQna_idx()+"##############");
			model.addAttribute("qv2", qnaVO2);
			int mbIdx = qnaService.selectMb_idx(commVO.getQna_idx());
			model.addAttribute("mi", mbIdx);
		
			if(qnaVO.getQna_read()==0 && role.equals("ROLE_ADMIN")) {
				qnaVO.setQna_read(qnaVO.getQna_read()+1);
				qnaService.updateRead(qnaVO.getQna_idx());
			}
		
			return "QnAView";
		}
		
		// 저장 
		@RequestMapping(value = "/answerInsertOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
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
		map.put("qna_idx",commVO.getRv_idx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return map;
		}
		
		// 저장 
		@RequestMapping(value = "/answerUpdateOk.do",method = RequestMethod.POST, produces = "application/json; charset=UTF8")
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
			map.put("qna_idx",commVO.getRv_idx() + "");
			redirectAttributes.addFlashAttribute("map", map);
			return map;
		}
		
//		@RequestMapping(value = "/QnADeleteOk.do",produces = "text/plain;charset=UTF-8")
//		@ResponseBody
//		public String QnADeleteOk(@RequestParam int qna_idx, @RequestParam int mb_idx, QnAVO qnaVO, HttpSession session,
//				HttpServletRequest request, HttpServletResponse response ) throws IOException {
//	        response.setHeader("Content-Type", "text/html;charset=utf-8");
//			String result ="";
//			if(mb_idx==(qnaService.selectMb_Idx(qna_idx))) {
//				log.info("성공!");
//				qnaService.delete(qnaVO);
//				result+=("<script>alert('정상적으로 삭제되었습니다.'); </script>");
////				location.href=\\\"redirect:/QnA.do\\\";
//			}else {
//				log.info("실패!");
//				result+=("<script>alert('본인만 삭제 할 수 있습니다.');</script>");
//			}
//			return result;
//		}
//		
		@RequestMapping(value = "/QnADeleteOk.do")
		public String QnADeleteOk(@RequestParam int qna_idx, @RequestParam int mb_idx, QnAVO qnaVO,CommonVO commVO, HttpSession session,
				HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes ) throws IOException {
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if(mb_idx==qnaService.selectMb_idx(qna_idx)) {
				qnaService.delete(qnaVO);
				out.println("<script language='javascript'>");
				out.println("alert('정상적으로 삭제되었습니다.');");
				out.println("location.href='/QnA.do';");
				out.println("</script>");
				out.close();
			}else {	
				out.println("<script language='javascript'>");
				out.println("alert('에러');");
				out.println("location.href='/QnA.do';");
				out.println("</script>");
				out.close();
			}

			return "QnA";
		}
		
		
		
	
	
	
	
	// QnA--------------------------------------------------------------------

	
	
	
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