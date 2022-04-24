package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.ReviewVO;

public interface ReviewDAO {
	// 페이지 얻기
	List<ReviewVO> selectList(HashMap<String, Integer> map);

	// 글번호로 상세 얻기
	ReviewVO selectByIdx(int idx);

	// 개수 얻기
	int selectCount();

	// 저장하기
	void insert(ReviewVO reviewVO);

	// 수정하기
	void update(ReviewVO reviewVO);

	// 조회수 증가
	void updateHit(int idx);

	// 삭제 표시 된것만 삭제
	void delete1(int idx); // del비교
	// 완전 삭제

	void delete2(int idx); // del비교X
	// 삭제표시로 수정

	void updateDel(int idx);

	// 해당 리뷰 작성한 mb_idx만 불러오기
	int selectMb_idx(int idx);

	// 검색한것만 얻기
	List<ReviewVO> selectSearchList(HashMap<String, Object> map);

	// 검색한 개수 얻기
	int selectSearchCount(CommonVO commVO);
}
