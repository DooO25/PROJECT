package pro.s2k.camp.service;

import java.util.Map;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.FileUploadVO;
import pro.s2k.camp.vo.NoticeVO;
import pro.s2k.camp.vo.PagingVO;

public interface NoticeService {
	// 리스트 얻기
	PagingVO<NoticeVO> selectList(CommonVO commonVO);

	// 글 1개 상세 얻기
	NoticeVO selectByIdx(Map<String, Integer> map);

	// 저장하기
	void insert(NoticeVO noticeVO);

	// 수정하기
	void update(NoticeVO noticeVO, String path, String[] deleteFile);

	// 삭제하기
	void delete(NoticeVO noticeVO, String path);

	// 검색하기
	PagingVO<NoticeVO> selectSearchList(CommonVO commVO);

}
