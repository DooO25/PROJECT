package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommentVO;

public interface CommentDAO {

	// 원본글에 따른 모두얻기
	List<CommentVO> selectList(int idx);

	// 댓글 1개 상세얻기
	CommentVO selectByIdx(int idx);

	// 원본글에서 댓글 개수 구하기
	int selectCount(int idx);

	// 댓글 저장하기
	void insert(CommentVO commentVO);

	// 댓글 수정하기
	void update(CommentVO commentVO);

	// 나보다 seq 큰값 모두 1 증가시킴
	void updateSeq(HashMap<String, Integer> map);

	// 삭제하기
	void delete1(int idx); // del비교
	
	// 바로 삭제하기
	void delete2(int idx); // del비교X
	
	// 나와 ref가 같으면서 seq가 크거나 같은것 뽑기
	List<CommentVO> selectSeqList(HashMap<String, Integer> map);

	// 삭제 표시로 변경
	void updateDel(int idx);
	
	// 같은 co_ref중 제일 큰 co_seq값 얻기 
	int selectMaxSeq(int idx);

	// 대댓글 저장
	void reply(CommentVO commentVO);

	// 댓글 = 댓글 참조글 번호 같게 업데이트
	void refEqalIdx();

	// 같은 co_ref, co_seq에서 레벨값 구하기
	int selectLev(HashMap<String, Integer> map);
	
	// 같은 co_ref, co_seq에서 레벨개수 구하기 (같은 seq중 자기보다 큰 lev있나 판단)
	Integer selectComment(HashMap<String, Integer> map);
}
