package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.QnAVO;

public interface QnADAO {
	// 페이지 얻기
	List<QnAVO> selectList(HashMap<String, Integer> map);

	// 1개 상세 얻기
	QnAVO selectByIdx(int idx);

	// 답변 상세 얻기
	QnAVO selectByIdxAnswer(int idx);

	// 개수 세기
	int selectCount();

	// 질문 저장하기
	void insert(QnAVO qnaVO);

	// 답변 저장하기
	void answer(QnAVO qnaVO);

	// 수정하기
	void update(QnAVO qnaVO);

	// 답변 수정하기
	void updateAnswer(QnAVO qnaVO);

	// 삭제하기
	void delete(int idx);

	// 0,1,2 로 구분지어, 답변 상태 확인
	void updateRead(int idx);

	// idx 와 ref값을 같은 수로 만들어줌으로 질문인지 답변인지 판단
	void updateRef();

	// 회원 번호 불러와 본인글인지 판단
	int selectMb_idx(int idx);

	// 검색한것만 얻기
	List<QnAVO> selectSearchList(HashMap<String, Object> map);

	// 검색한것 개수 얻기
	int selectSearchCount(CommonVO commVO);

}
