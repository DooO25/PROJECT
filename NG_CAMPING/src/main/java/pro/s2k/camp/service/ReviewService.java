package pro.s2k.camp.service;

import java.util.Map;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;

public interface ReviewService {
	// 목록보기
	PagingVO<ReviewVO> selectList(CommonVO commVO);

	// 내용보기 - 글 1
	ReviewVO selectByIdx(Map<String, Integer> map);

	// 원본글 저장
	void insert(ReviewVO reviewVO);

	// 수정
	void update(ReviewVO reviewVO);

	// 삭제
	void delete(ReviewVO reviewVO);

	// 작성자 여부 점검하기 위한 유저 고유번호
	int selectMb_idx(int idx);

	// 후기 검색
	PagingVO<ReviewVO> selectSearchList(CommonVO commonVO);
}