-- drop schema damoim;
-- create schema damoim;

CREATE TABLE member ( -- 회원가입
    id VARCHAR(50) PRIMARY KEY, -- 아이디
    pwd VARCHAR(255) NOT NULL, -- 비밀번호
    addr VARCHAR(255), -- 주소
    phone VARCHAR(20), -- 전화번호
    email VARCHAR(100) UNIQUE, -- 이메일
    name VARCHAR(50), -- 이름
    age INT, -- 나이
    gender CHAR(1), -- 성별 M/F
    status BOOLEAN DEFAULT TRUE -- 유저 가입,탈퇴 여부
);
CREATE TABLE type_category_large ( -- 유형대분류
    type_la_code INT PRIMARY KEY auto_increment, -- 대분류코드 mc 메인카테고리 줄임말
    type_la_name VARCHAR(50) UNIQUE -- 대분류이름
);
CREATE TABLE type_category_small  ( -- 유형소분류
    type_s_code INT PRIMARY KEY auto_increment, -- 소분류코드
    type_s_name VARCHAR(50) UNIQUE, -- 소분류이름
    type_la_code INT -- 대분류코드 /외래키
    
);

CREATE TABLE location_category_large ( -- 위치대분류
    loc_la_code INT PRIMARY KEY auto_increment, -- 위치대분류코드 -- 대분류코드 ml 메인지역 줄임말
    loc_la_name VARCHAR(50) -- 위치대분류이름
);
CREATE TABLE location_category_small ( -- 위치소분류
    loc_s_code INT PRIMARY KEY auto_increment, -- 위치소분류코드
    loc_s_name VARCHAR(50), -- 위치소분류이름
    loc_la_code INT -- 위치대분류코드 /외래키
);


CREATE TABLE user ( -- 유저
    user_code INT PRIMARY KEY auto_increment, -- 유저코드
    id VARCHAR(50), -- 유저아이디 /외래키
    user_manner DECIMAL(4,2) DEFAULT (36.50), -- 유저온도 0.00 ~ 99.99까지가능
    user_name VARCHAR(50), -- 닉네임
    user_img VARCHAR(255), -- 유저프로필사진
    user_hobby TEXT, -- 유저 관심사
    user_info TEXT, -- 유저 간단한 자기소개
    user_location VARCHAR(100), -- 유저선호지역
    user_type VARCHAR(100) -- 유저 선호만남유형
);


CREATE TABLE membership ( -- 클럽
    membership_code INT PRIMARY KEY auto_increment, -- 클럽코드
    membership_name VARCHAR(100) UNIQUE, -- 클럽이름
    membership_img TEXT, -- 클럽메인사진
	membership_info TEXT, -- 클럽소개
    membership_date DATE DEFAULT(current_date), -- 클럽생성날짜
    membership_grade DECIMAL(2,1) DEFAULT(0), -- 클럽 별점
    membership_max INT -- 클럽최대인원

);


CREATE TABLE membership_type  ( -- 클럽 유형 리스트
    mem_type_code INT PRIMARY KEY auto_increment, -- 클럽 유형 리스트 코드
    type_s_code INT, -- 소분류이름 / 외래키
    membership_code INT -- 클럽 /외래키
);


CREATE TABLE membership_location  ( -- 클럽 지역 리스트
    mem_loc_code INT PRIMARY KEY auto_increment, -- 클럽 지역 리스트 코드
    loc_s_code INT, -- 소분류 / 외래키
    membership_code INT -- 클럽 /외래키
);


CREATE TABLE membership_user_list( -- 클럽유저정보
    list_code INT PRIMARY KEY auto_increment, -- 클럽가입한 멤버코드
    list_grade VARCHAR(50), -- 멤버등급 호스트, 관리자, 일반회원, 비회원(신청만한사람)
    user_code INT, -- 유저코드 /외래키
    membership_code INT -- 클럽코드 /외래키
);


CREATE TABLE channel ( -- 클럽채널
    chan_code INT PRIMARY KEY auto_increment, -- 클럽채널코드
    chan_name VARCHAR(100), -- 채널명
    chan_img TEXT, -- 채널 사진
    user_code INT, -- 유저코드 /외래키
    membership_code INT -- 클럽코드 /외래키
);



CREATE TABLE membership_meetings ( -- 클럽모임게시판
    meet_code INT PRIMARY KEY auto_increment, -- 모임게시판코드
    membership_code INT, -- 클럽코드 /외래키
    meet_date_start DATE, -- 모임 시작일
    meet_date_end DATE, -- 모임 종료일
    meet_agree_code INT, -- 참여여부 테이블 연결
    meet_info TEXT, -- 모임관련 정보
    meet_creat_date DATE default(current_date)
);
CREATE TABLE meetings_agree (-- 클럽 모임게시판 - 클럽 회원 리스트 참여여부 테이블 
	meet_agree_code INT PRIMARY KEY auto_increment,
    meet_agree_yn BOOLEAN DEFAULT(false),
    list_code INT
);



-- 클럽 모임 게시판에 댓글기능 추가 
CREATE TABLE meetings_comment (
    meet_comment_code INT PRIMARY KEY auto_increment, -- 댓글코드
    meet_comment_text TEXT, -- 모임게시판 댓글 내용
    meet_comment_date DATE DEFAULT(current_date), -- 댓글 게시시간
	user_code INT, --  /외래키  누가 ?
	meet_code INT, -- /왜래키 어떤클럽 홍보 게시판?
	meet_parents_comment_code INT -- /대댓글

);



CREATE TABLE main ( -- 클럽홍보게시판
	main_code INT PRIMARY KEY auto_increment, -- 홍보게시판코드
    membership_code INT UNIQUE, -- 클럽코드 /외래키
    main_name VARCHAR(255), -- 홍보글제목
    main_text TEXT, -- 홍보내용
    main_views INT, -- 조회수
    main_date DATE DEFAULT(current_date), -- 홍보글게시날짜
    user_code INT  -- 유저 코드 / 외래키

);


-- 확정
CREATE TABLE main_comment (
    main_comment_code INT PRIMARY KEY auto_increment, -- 댓글코드
    main_comment_text TEXT, -- 홍보게시판 댓글 내용
    main_comment_date DATE DEFAULT(current_date), -- 댓글 게시시간
	user_code INT, --  /외래키  누가 ?
	main_code INT, -- /왜래키 어떤클럽 홍보 게시판?
	main_parents_comment_code INT -- /대댓글

);



 -- 확정
CREATE TABLE image ( -- 사진 테이블
    img_code INT PRIMARY KEY auto_increment, -- 사진코드
	img_url VARCHAR(255), -- 사진
	main_code INT, -- 홍보게시판코드 / 외래키
    meet_code INT -- 모임게시판코드 / 외래키
);

ALTER TABLE type_category_small ADD FOREIGN KEY (type_la_code) REFERENCES type_category_large(type_la_code); 

ALTER TABLE location_category_small ADD FOREIGN KEY (loc_la_code) REFERENCES location_category_large(loc_la_code);


ALTER TABLE membership_type ADD  FOREIGN KEY (type_s_code) REFERENCES type_category_small(type_s_code);
ALTER TABLE membership_type ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

ALTER TABLE membership_location ADD  FOREIGN KEY (loc_s_code) REFERENCES location_category_small(loc_s_code);
ALTER TABLE membership_location ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

ALTER TABLE membership_user_list ADD  FOREIGN KEY (user_code) REFERENCES user(user_code);
ALTER TABLE membership_user_list ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

ALTER TABLE channel ADD  FOREIGN KEY (user_code) REFERENCES user(user_code);
ALTER TABLE channel ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

ALTER TABLE membership_meetings ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);
ALTER TABLE membership_meetings ADD  FOREIGN KEY (meet_agree_code) REFERENCES meetings_agree(meet_agree_code);

ALTER TABLE meetings_agree ADD  FOREIGN KEY (list_code) REFERENCES membership_user_list(list_code);

ALTER TABLE meetings_comment ADD  FOREIGN KEY (user_code) REFERENCES user(user_code);
ALTER TABLE meetings_comment ADD  FOREIGN KEY (meet_code) REFERENCES membership_meetings(meet_code);
ALTER TABLE meetings_comment ADD  FOREIGN KEY (meet_parents_comment_code) REFERENCES meetings_comment(meet_comment_code);

ALTER TABLE main ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);
ALTER TABLE main ADD  FOREIGN KEY (user_code) REFERENCES user(user_code);



ALTER TABLE main_comment ADD  FOREIGN KEY (user_code) REFERENCES user(user_code);
ALTER TABLE main_comment ADD  FOREIGN KEY (main_code) REFERENCES main(main_code);
ALTER TABLE main_comment ADD  FOREIGN KEY (main_parents_comment_code) REFERENCES main_comment(main_comment_code);



ALTER TABLE image ADD  FOREIGN KEY (meet_code) REFERENCES membership_meetings(meet_code);
ALTER TABLE image ADD  FOREIGN KEY (main_code) REFERENCES main(main_code);

ALTER TABLE membership_meetings ADD list_code INT;

ALTER TABLE membership_meetings ADD  FOREIGN KEY (list_code) REFERENCES membership_user_list(list_code);



