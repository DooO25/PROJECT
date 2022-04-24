package pro.s2k.camp.vo;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 `mb_idx` int(11) NOT null ,
  q_idx int not null auto_increment,
  ref int default 0,
  seq int default 0,
  lev int default 0,
  `q_title` varchar(100) not NULL,
  `q_content` text not NULL,
  `mb_nick` varchar(30) NOT NULL,
  `q_regDate` timestamp default now(),
  `q_modiDate` timestamp default now(),
  `q_hit` int(11) default 0,
  del int default 1,
  PRIMARY KEY (`q_idx`),
  CONSTRAINT `camp_question_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnAVO {				// Q&A 객체
	private int mb_idx;				// 유저 고유 번호
	private int qna_idx;			// Q&A 고유 번호
	private int qna_ref;			// Q&A 질문 참조 번호
	private String qna_title;		// Q&A 제목
	private String qna_content;		// Q&A 내용
	private Date qna_regDate;		// Q&A 등록일
	private Date qna_modiDate;		// Q&A 수정일
	private String qna_ip;			// Q&A 작성자 IP
	private int qna_read;			// Q&A 답변 여부 (0 = 미조회, 1 = 조회, 2 = 답변완료) 
	private String mb_nick;			// 유저 닉네임
}
