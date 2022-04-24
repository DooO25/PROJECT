package pro.s2k.camp.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.s2k.camp.controller.BoardController;
import pro.s2k.camp.dao.FileUploadDAO;
import pro.s2k.camp.dao.NoticeDAO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.FileUploadVO;
import pro.s2k.camp.vo.NoticeVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private NoticeDAO noticeDAO;

	@Autowired
	private FileUploadDAO fileUploadDAO;

	@Override
	public PagingVO<NoticeVO> selectList(CommonVO commonVO) { // 공지 게시물 리스트
		PagingVO<NoticeVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = noticeDAO.selectCount(); // 전체 개수 카운트 qnaMapper selectCount 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commonVO.getCurrentPage(), commonVO.getPageSize(), commonVO.getBlockSize(),
					totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo()); // 시작 글번호
			map.put("pageSize", pagingVO.getPageSize()); // 페이지당 글의 개수
			
			List<NoticeVO> list = noticeDAO.selectList(map);// noticeMapper selectList 쿼리 실행
			// 해당 글들의 첨부파일 정보를 넣어준다.
			if (list != null && list.size() > 0) {
				for (NoticeVO noticeVO : list) {
					// 해당 글의 첨부파일 목록을 가져온다.
					List<FileUploadVO> fileList = fileUploadDAO.selectList(noticeVO.getNt_idx()); // fileMapper
																									// selectList 쿼리 실행
					// vo에 넣는다.
					noticeVO.setFileList(fileList);
				}
			}
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public NoticeVO selectByIdx(Map<String, Integer> map) { // 공지 게시글 조회
		NoticeVO noticeVO = noticeDAO.selectByIdx(map.get("nt_idx")); // noticeMapper selectByIdx 쿼리 실행
		if (noticeVO != null) {
			List<FileUploadVO> list = fileUploadDAO.selectList(map.get("nt_idx")); // fileMapper selectList 쿼리 실행
			noticeVO.setFileList(list); // vo에 파일리스트 저장
			// mode 인수가 값이 있으면,
			if (map.get("mode") != null) {
				// 조회수 증가
				noticeDAO.incrementHits(map.get("nt_idx")); // noticeMapper incrementHits 쿼리 실행 
				noticeVO.setNt_hit(noticeVO.getNt_hit() + 1); // 즉각 반영을 위해 vo에서 가져온 값을 +1 해서 vo에 다시 세팅
			}

		}
		return noticeVO;
	}

	@Override
	public void insert(NoticeVO noticeVO) {		// 공지 작성
		if(noticeVO!=null) {
			// 1. 글을 조장한다.
			noticeDAO.insert(noticeVO);
			// 저장을 했으면 저장된 idx값을 얻어온다	(파일저장 참조)
			int ref = noticeDAO.selectSeq();	// noticeMapper selectSeq 쿼리 실행
			// 2. 첨부 파일의 정보도 저장해 주어야 한다.
			List<FileUploadVO> list = noticeVO.getFileList();	// 파일리스트 가져오기 
			if(list!=null && list.size()>0) {
				for(FileUploadVO fileUploadVO : list) {
					fileUploadVO.setUp_ref(ref); // 원본글번호
					fileUploadDAO.insert(fileUploadVO);		// fileMapper insert 쿼리 실행
				}
			}
		}
	}
	
	@Override
	public void update(NoticeVO noticeVO, String path, String[] deleteFile) {
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getNt_idx());
		if (dbVO != null) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 1. 글수정
			noticeDAO.update(noticeVO);
			// 2. 새롭게 첨부된 첨부 파일의 정보도 저장해 주어야 한다.
			List<FileUploadVO> list = noticeVO.getFileList();
			if (list != null && list.size() > 0) {
				for (FileUploadVO vo : list) {
					vo.setUp_ref(noticeVO.getNt_idx()); // 원본글번호
					fileUploadDAO.updateInsert(vo); // fileMapper updateInsert 쿼리 실행
				}
			}
			// 3, 이미 첨부되었던 파일 삭제
			if (deleteFile != null && deleteFile.length > 0) {
				for (String t : deleteFile) {
					int idx = Integer.parseInt(t);
					if (idx > 0) {
						// 실제 파일을 삭제하려면
						// 1. 해당 글번호의 글을 읽어와서
						FileUploadVO fileUploadVO = fileUploadDAO.selectByIdx(idx);
						if (fileUploadVO != null) {
							// 2. 실제 서버의 파일을 삭제해 주어야 한다.
							File file = new File(path + File.separator + fileUploadVO.getSaveName());
							file.delete(); // 실제 파일삭제
							fileUploadDAO.deleteByIdx(idx); // DB에서만 삭제된다.
						}
					}
				}
			}
		}
	}

	@Override
	public void delete(NoticeVO noticeVO, String path) {	// 공지 삭제
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getNt_idx());	// noticeMapper selectByIdx 실행
		if(dbVO!=null) {
			// 글삭제
			noticeDAO.delete(noticeVO.getNt_idx());	// noticeMapper delete 실행
			// 모든 첨부파일의 목록을 읽어온다.
			List<FileUploadVO> list = fileUploadDAO.selectList(noticeVO.getNt_idx()); // fileMapper selectList 쿼리 실행
			if(list!=null && list.size()>0) {
				for(FileUploadVO vo : list) {
					// DB 파일 삭제
					fileUploadDAO.deleteByIdx(vo.getUp_idx());	// fileMapper deleteByIdx 실행
					// 경로에 있는 실제 파일삭제
					File file = new File(path + File.separator + vo.getSaveName());
					file.delete();
				}
			}
		}
	}
	
	@Override
	public PagingVO<NoticeVO> selectSearchList(CommonVO commVO) {	// 공지 검색
		PagingVO<NoticeVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = noticeDAO.selectSearchCount(commVO);	// 전체 개수 카운트 qnaMapper selectCount 쿼리 실행
			// 페이지 계산(현재페이지, 페이지당 글 개수, 하단에 표시할 페이지 수, 전체글수)
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("startNo", pagingVO.getStartNo());		// 시작 글번호
			map.put("pageSize", pagingVO.getPageSize());	// 페이지당 글의 개수
			map.put("searchText",commVO.getSearchText());	// 검색 
			map.put("searchType",commVO.getSearchType());	// 검색 셀렉트
		
			List<NoticeVO> list = noticeDAO.selectSearchList(map);	// noticeMapper SelectSearchList 쿼리 실행
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
}
