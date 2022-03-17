package PRO.S2K.CAMP.CONTROLLER;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	
	
	// SummerNote 필요한 컨트롤러 
	
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
	
	@RequestMapping(value = "summernote.do", method = RequestMethod.GET)
	public String summernote() {
		return "summernote";
	}
	
	@RequestMapping(value = "result.do")
	public String result() {
		return "result";
	}
	
	// 섬머노트의 큰 이미지를 처리하기위한 주소 1개를 생성해 주어야 한다.
		@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
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
}
