package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.ReviewVO;



public interface ReviewDAO {
	
	List<ReviewVO> selectList(HashMap<String, Integer> map);
	ReviewVO selectByIdx(int idx);
	int selectCount();
	void insert(ReviewVO reviewVO);
	void update(ReviewVO reviewVO);
	void updateHit(int idx);
	
	// 삭제
	void delete1(int idx); // del비교
	void delete2(int idx); // del비교X
	void updateDel(int idx);
	List<ReviewVO> selectDelList();
	
	// 해당 리뷰 작성한 mb_idx만 불러오기
	int selectMb_idx(int idx);
	// 댓글 개수 세기
	int selectCoCount(int idx);
	
	List<ReviewVO> selectSearchList(HashMap<String, Object> map);
	int selectSearchCount(CommonVO commVO);
}
