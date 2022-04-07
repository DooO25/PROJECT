package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.QnAVO;

public interface QnADAO {
	List<QnAVO> selectList(HashMap<String, Integer> map);
	QnAVO selectByIdx(int idx);
	QnAVO selectByIdxAnswer(int idx);
	int selectCount();
	void insert(QnAVO qnaVO);
	void answer(QnAVO qnaVO);
	void update(QnAVO qnaVO);
	void delete(int idx);
	// 0,1,2 로 구분지어, 답변 상태 확인
	void updateRead(int idx);
	// idx 와 ref값을 같은 수로 만들어줌으로 answer인지 판
	void updateRef();
	int selectMb_idx(int idx);
	void updateAnswer(QnAVO qnaVO);
	
	List<QnAVO> selectSearchList(HashMap<String, Object> map);
	int selectSearchCount(CommonVO commVO);
	
}
