package pro.s2k.camp.dao;

import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.QnAVO;

public interface QnADAO {
	List<QnAVO> selectList(HashMap<String, Integer> map);
	QnAVO selectByIdx(int idx);
	int selectCount();
	void insert(QnAVO qnaVO);
	void update(QnAVO qnaVO);
	void delete(int idx);
	void updateRead(int idx);
	
	
	
	
	
}
