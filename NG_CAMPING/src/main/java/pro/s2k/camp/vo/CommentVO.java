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

public class CommentVO {
	private int rv_idx;
	private int co_idx;
	private int co_ref;
	private int co_seq;
	private int co_lev;
	private String co_content;
	private Date co_regDate;
	private Date co_modiDate;
	private String co_ip;
	private int del2;
	
	private String mb_nick;
}
