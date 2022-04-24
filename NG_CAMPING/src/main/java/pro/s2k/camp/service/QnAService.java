package pro.s2k.camp.service;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.PagingVO;
import pro.s2k.camp.vo.QnAVO;

public interface QnAService {

	// 목록보기
	PagingVO<QnAVO> selectList(CommonVO commVO);

	// 내용보기
	QnAVO selectByIdx(int idx);
	// 답변 보기
	QnAVO selectByIdxAnswer(int idx);

	// 원본글 저장
	void insert(QnAVO qnaVO);

	// 답변저장
	void answer(QnAVO qnaVO);

	// 수정
	void update(QnAVO qnaVO);

	// 삭제
	void delete(QnAVO qnaVO);

	// 질문글 조회 여부(미조회, 조회, 답변)
	void updateRead(int idx);

	// 질문글 작성자 여부 체크(삭제시)
	int selectMb_idx(int idx);

	// 답변 수정
	void updateAnswer(QnAVO qnaVO);

	// qna 검색
	PagingVO<QnAVO> selectSearchList(CommonVO commVO);
}
