package pro.s2k.camp.service;


import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.ReviewVO;

public interface ReviewService {
	// 목록보기
	PagingVO<ReviewVO> selectList(CommonVO commVO);
	// 내용보기 - 글 1
	ReviewVO selectByIdx(int idx);
	// 원본글 저장
	void insert(ReviewVO reviewVO);
//	// 답변저장
//	void reply(ReviewVO reviewVO);
	// 수정
	void update(ReviewVO reviewVO);
	// 삭제
	void delete(ReviewVO reviewVO);

	int selectMb_idx(int idx);

	PagingVO<ReviewVO> selectSearchList(CommonVO commVO);

}
