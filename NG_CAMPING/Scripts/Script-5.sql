insert into	camp_notice 
 			(mb_idx, nt_password, nt_title, nt_content, nt_regDate, nt_ip) 
 		values 
 			(2, "123456","테스트입니다", "테스트중", now(), "127.0.0.1");

insert into camp_member (
			mb_ID, mb_password, mb_name, mb_nick, mb_email, mb_tel, mb_birth, mb_address1, mb_address2, mb_zipcode, mb_use)
		values 
			("admin", "!Q@W3e4r", "관리자", "관리자", "nomed516@gmail.com", "01042928988","1989-08-08" ,"일단은 주소", "상세 주소", "12345", "1");
insert into member_role 
			(mb_ID, gr_role)
		values
			("admin", "ROLE_ADMIN");

alter table camp_upload auto_increment = 1;
alter table camp_notice auto_increment = 1;

insert into	camp_notice 
 			(mb_idx, mb_nick, nt_title, nt_content, nt_regDate, nt_ip) 
 		values 
 			(1, "관리자" , "테스트", "테스트트트", now(), "127.0.0.1");
 			
 		
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