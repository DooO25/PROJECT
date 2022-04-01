package pro.s2k.camp.dao;


import java.util.List;

import pro.s2k.camp.vo.FileUploadVO;

public interface FileUploadDAO {
	// <!-- 1. 첨부파일 저장 -->
	void insert(FileUploadVO fileUploadVO);
	// <!-- 2. 수정시 첨부파일 저장 : 이때는 원본글의 ref가 별도로 존재한다. -->
	void updateInsert(FileUploadVO fileUploadVO);
	// <!-- 3. 첨부파일 삭제 -->
	void deleteByIdx(int up_idx);
	// <!-- 4. 원본글의 첨부파일 모두 읽기 -->
	List<FileUploadVO> selectList(int up_ref);
	// <!-- 5. 원본글의 첨부파일 모두 삭제하기 -->
	void deleteByRef(int up_ref);
	// <!-- 6. 글 1개 가져오기 -->
	FileUploadVO selectByIdx(int up_idx) ;
}	
