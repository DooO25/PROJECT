drop table camp_question ;
drop table camp_review ;

CREATE TABLE `camp_qna` (
  `mb_idx` int(11) NOT null ,
  qna_idx int not null auto_increment,
  qna_ref int default 0,
--  seq int default 0,
--  lev int default 0,
  `qna_title` varchar(100) not NULL,
  `qna_content` text not NULL,
--  `mb_nick` varchar(30) NOT NULL,
  `qna_regDate` timestamp default now(),
  `qna_modiDate` timestamp default now(),
	qna_ip varchar(20) NOT NULL,
  --  `q_hit` int(11) default 0,
--  del int default 1,
	qna_read int DEFAULT 0,
  PRIMARY KEY (`qna_idx`),
  CONSTRAINT `camp_question_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);


-- CREATE TABLE `camp_answer` (
--  `mb_nick` varchar(30) NOT NULL,
--  `mb_idx` int(11) NOT NULL,
--  ref int default 0,
--  seq int default 0,
--  `q_title` varchar(100) not NULL,
--  `q_content` text not NULL,
--  `a_idx` int(11) not NULL,
--  `a_title` varchar(100) not NULL,
--  `a_content` text not NULL,
--  `a_regDate` timestamp default now(),
--  `a_modiDate` timestamp default now(),
--  CONSTRAINT `camp_answer_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_question` (`mb_idx`)
-- );

drop table camp_review;
CREATE TABLE `camp_review` ( 
 `mb_idx` int(11) NOT NULL,
  rv_idx int not null auto_increment,
--  ref int default 0,
--  seq int default 0,
--  lev int default 0,
--  `mb_nick` varchar(30) NOT NULL,
--   rv_password int not null,
  `rv_title` varchar(100) not NULL,
  `rv_content` text not NULL,
  `rv_regDate` timestamp default now(),
  `rv_modiDate` timestamp default now(),
    rv_ip varchar(20) not null,
  `rv_hit` int(11) default 0,
  del int default 1,
  PRIMARY KEY (`rv_idx`),
 CONSTRAINT `camp_review_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);

ALTER table camp_review ADD rv_password int not null ;
 drop table camp_comment ;
CREATE TABLE `camp_comment` (
--  `mb_idx` int(11) NOT NULL,
 `rv_idx` int NOT NULL,  
co_idx int NOT NULL auto_increment,
  co_ref int DEFAULT 0,
  co_seq int DEFAULT 0, 
  co_lev int DEFAULT 0,
  `co_content` text not NULL,
  `co_regDate` timestamp default now(),
  `co_modiDate` timestamp default now(),
  co_ip varchar(20) NOT NULL,
  del2 int DEFAULT 1,
  PRIMARY KEY (`co_idx`),
  CONSTRAINT `camp_comment_FK` FOREIGN KEY (`rv_idx`) REFERENCES `camp_review` (`rv_idx`) ON UPDATE CASCADE
 );
