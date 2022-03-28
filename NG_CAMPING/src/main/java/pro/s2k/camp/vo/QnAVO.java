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
public class QnAVO {
	private int mb_idx;
	private int qna_idx;
	private int qna_ref;
	private String qna_title;
	private String qna_content;
	private Date qna_regDate;
	private Date qna_modiDate;
	private String qna_ip;
	
	private String mb_nick;
}
