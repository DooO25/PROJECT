package pro.s2k.camp.service;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;

public interface QnAService {

	// 목록보기
	PagingVO<QnAVO> selectList(CommonVO commVO);
	// 내용보기
	QnAVO selectByIdx(int idx);
	QnAVO selectByIdxAnswer(int idx);
	// 원본글 저장
	void insert(QnAVO qnaVO);
	// 답변저장
	void answer(QnAVO qnaVO);
	// 수정
	void update(QnAVO qnaVO);
	// 삭제
	void delete(QnAVO qnaVO);
	
	void updateRead(int idx);
	
	int selectMb_idx(int idx);
	
	void updateAnswer(QnAVO qnaVO);
}
