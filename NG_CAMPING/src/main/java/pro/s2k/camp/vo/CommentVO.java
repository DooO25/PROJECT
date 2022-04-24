package pro.s2k.camp.vo;

import java.sql.Date;

import lombok.Data;

/*
 CREATE TABLE `camp_comment` (
  `rv_idx` int NOT NULL,
  co_idx int NOT NULL auto_increment,
  co_seq int DEFAULT 0, 
  co_lev int DEFAULT 0,
  `co_content` text not NULL,
  `co_regDate` timestamp default now(),
  `co_modiDate` timestamp default now(),
  co_ip varchar(20) NOT NULL,
  del int DEFAULT 1,
  PRIMARY KEY (`co_idx`),
  CONSTRAINT `camp_comment_FK` FOREIGN KEY (`rv_idx`) REFERENCES `camp_review` (`rv_idx`) ON UPDATE CASCADE
 );
  
 */


@Data
public class CommentVO {		// 후기 댓글 객체
	private int rv_idx;			// 후기 고유 번호
	private int co_idx;			// 댓글 고유 번호
	private int co_ref;			// 후기 참조 번호
	private int co_seq;			// 댓글 정렬 순서
	private int co_lev;			// 댓글 계층 (댓글, 대댓글 ...)
	private String co_content;	// 댓글 내용
	private Date co_regDate;	// 댓글 등록일
	private Date co_modiDate;	// 댓글 수정일
	private String co_ip;		// 작성 IP
	private int del2;			// 댓글 삭제 여부
	private String mb_nick;		// 작성자 닉네임
}
