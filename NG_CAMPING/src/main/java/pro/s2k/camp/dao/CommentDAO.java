package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.ReviewVO;

public interface CommentDAO {
	
	// rv_idx에 따른 모두얻기
	List<CommentVO> selectList(int idx);
	// rv_idx에 따른 1개얻기
	CommentVO selectByIdx(int idx);
	// 1개에서 댓글 개수 구하기
	int selectCount(int idx);
	// 저장하기
	void insert(CommentVO commentVO);
	// 수정하기
	void update(CommentVO commentVO);
	// 나보다 seq 큰값 모두 1 증가시킴
	void updateSeq(HashMap<String, Integer> map);
	// 삭제하
	void delete1(int idx); // del비교
	void delete2(int idx); // del비교X
	// 나와 ref가 같으면서 seq가 크거나 같은것 뽑기
	List<ReviewVO> selectSeqList(HashMap<String, Integer> map);
	// 삭제 표시로 변경
	void updateDel(int idx);
	// 삭제표시값이 0인 모든 데이터 가져오기
	List<ReviewVO> selectDelList();
	
	// 저장한 idx값 알아내기 (현재 Sequence값)
	int selectSeq();
	
	
	
}
