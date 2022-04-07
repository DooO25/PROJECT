package pro.s2k.camp.dao;


import java.util.HashMap;
import java.util.List;

import pro.s2k.camp.vo.CommonVO;
import pro.s2k.camp.vo.NoticeVO;

public interface NoticeDAO {
	// <!-- 1. 개수얻기 -->
	int selectCount();
	// <!-- 2. 1개 얻기 -->
	NoticeVO selectByIdx(int idx);
	// <!-- 3. 1페이지 얻기 -->
	List<NoticeVO> selectList(HashMap<String, Object> map);
	// <!-- 4. 저장하기 -->
	void insert(NoticeVO noticeVO);
	// <!-- 5. 수정하기 -->
	void update(NoticeVO noticeVO);
	// <!-- 6. 삭제하기 -->
	void delete(int idx);
	// <!-- 7. 현재 저장한 idx값 알아내기 (현재 Sequence값) -->
	int selectSeq();
	
	int incrementHits(int idx);
	
	String selectNick();
	
	List<NoticeVO> selectSearchList(HashMap<String, Object> map);
	int selectSearchCount(CommonVO commVO);
}
