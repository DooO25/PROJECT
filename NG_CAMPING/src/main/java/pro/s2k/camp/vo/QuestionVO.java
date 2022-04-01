package pro.s2k.camp.vo;


import java.util.Date;

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
public class QuestionVO {
	private int mb_idx;
	private int q_idx;
	private int ref;
	private int seq;
	private int lev;
	private String q_title;
	private String q_content;
	private String mb_nick;
	private Date q_regDate;
	private Date q_modiDate;
	private int q_hit;
	private int del;
}
