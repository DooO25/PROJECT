create table camp_upload(
	idx int auto_increment primary key,
	ref int default 0,
	saveName varchar(500) not null,
	originalName varChar(500) not null
);