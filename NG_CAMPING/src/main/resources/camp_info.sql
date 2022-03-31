drop table camp_info;

CREATE TABLE `camp_info` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `firstImageUrl` text ,
  `zipcode` char(7) ,
  `addr1` text ,
  `addr2` text ,
  `facltNm` text ,
  `lineIntro` text ,
  `intro` text ,
  `featureNm` text ,
  `lctCl` text ,
  `doNm` text ,
  `sigunguNm` text ,
  `allar` int(11) ,
  `gnrlSiteCo` int(11) ,
  `autoSiteCo` int(11) ,
  `glampSiteCo` int(11) ,
  `caravSiteCo` int(11) ,
  `indvdlCaravSiteCo` int(11) ,
  `glampInnerFclty` text ,
  `caravInnerFclty` text ,
  `trlerAcmpnyAt` char(1) ,
  `caravAcmpnyAt` char(1) ,
  `eqpmnLendCl` text ,
  `sbrsCl` text ,
  `sbrsEtc` text ,
  `posblFcltyCl` text ,
  `posblFcltyEtc` text ,
  `themaEnvrnCl` text ,
  `animalCmgCl` text ,
  `toiletCo` int(11) ,
  `swrmCo` int(11) ,
  `wtrplCo` int(11) ,
  `brazierCl` text ,
  `siteBottomCl1` int(11) ,
  `siteBottomCl2` int(11) ,
  `siteBottomCl3` int(11) ,
  `siteBottomCl4` int(11) ,
  `siteBottomCl5` int(11) ,
  `homepage` text ,
  `resveUrl` text ,
  `tel` varchar(15) ,
  `induty` text ,
  `mapX` double ,
  `mapY` double ,
  PRIMARY KEY (`idx`)
);

drop table camp_member ;
drop table member_role ;
CREATE TABLE `camp_member` (
  `mb_idx` int(11) NOT NULL auto_increment primary key,
  `mb_ID` varchar(12) not NULL,
  `mb_password` varchar(60) not NULL,
  `mb_name` varchar(15) not NULL,
  `mb_nick` varchar(30) NOT NULL,
  `mb_email` varchar(30) not NULL,
  `mb_tel` varchar(13) not NULL,
  `mb_birth` date not null,
  `mb_zipcode` int not null,
  `mb_address1` varchar(200) not NULL,
  `mb_address2` varchar(300) not NULL,
  `mb_use` int(11) default 0,
  `authkey` varchar(200) not null
);

CREATE TABLE `member_role` (
  `gr_idx` int(11) NOT null AUTO_INCREMENT primary key,
   `mb_ID` varchar(12) not NULL,
  `gr_role` varchar(15) not NULL
);
RENAME TABLE campdb.camp_role TO campdb.member_role;

drop table camp_notice;

CREATE TABLE `camp_notice` (
  `mb_nick` varchar(30) NOT NULL,
  nt_idx int not null auto_increment ,
  `mb_idx` int(11) NOT null,
  `nt_title` varchar(100) not NULL,
  `nt_content` text not NULL,
  `nt_regDate` timestamp default now(),
  `nt_modiDate` timestamp default now(),
  `nt_hit` int(11) default 0,
  PRIMARY KEY (`nt_idx`),
  CONSTRAINT `camp_notice_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE
);


drop table camp_question;

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

drop table camp_upload ;
CREATE TABLE `camp_upload` (
  `up_idx` int(11) NOT NULL AUTO_INCREMENT,
  `up_ref` int(11) DEFAULT 0,
  `saveName` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `originalName` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`up_idx`)
);


INSERT INTO camp_grade values(0,'미인증','user');
INSERT INTO camp_grade values(1,'인증','member');
INSERT INTO camp_grade values(2,'관리자','admin');

drop table sns_kakao;
CREATE TABLE `sns_kakao` (
	k_idx int primary key auto_increment,
    k_nick varchar(20) not null,
    k_email varchar(50) not null
);
  CONSTRAINT `sns_kakao_FK` FOREIGN KEY (`mb_idx`) REFERENCES `camp_member` (`mb_idx`) ON UPDATE CASCADE

		update camp_member a 
			join member_role mr on 
			a.mb_ID = mr.mb_ID
		set 
			mr.gr_role ='ROLE_USER'
		where 
			mr.mb_ID='rkdendh' and a.authkey='b516e506-649a-4ff7-99e7-112088b65491';

alter table camp_member add column sns_email varchar (200);
alter table camp_comment  auto_increment =1;