package pro.s2k.camp.service;

import pro.s2k.camp.vo.CommentVO;
import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;

public interface CommentService {
	
	//목록보기
	CommentVO selectList(CommonVO commonVO);
	// 리뷰 답글 보기
	CommentVO selectByIdx(int idx);
	// 답글 저장
	void insert(CommentVO commentVO);
	// 수정
	void update(CommentVO commentVO, String[] delFiles, String realPath);
	// 삭제
	void delete(CommentVO commentVO, String uploadPath);
	
	
	
	
	
	
}