
CREATE TABLE camp_member (
  mb_idx int(11) NOT NULL auto_increment primary key,
  mb_ID varchar(12) not NULL,
  mb_password varchar(41) not NULL,
  mb_name varchar(15) not NULL,
  mb_nick varchar(30) NOT NULL,
  mb_email varchar(30) not NULL,
  mb_tel varchar(13) not NULL,
  mb_birth date not null,
  mb_zipcode int not null,
  mb_address1 varchar(200) not NULL,
  mb_address2 varchar(300) not NULL,
  mb_use int(11) default 0,
  authkey varchar(200) not null
);
CREATE TABLE camp_role (
  gr_idx int(11) NOT null auto_increment primary key,
  mb_ID varchar(12) not NULL,
  gr_role varchar(15) not NULL
);