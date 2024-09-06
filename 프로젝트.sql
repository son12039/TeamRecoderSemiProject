-- drop schema damoim;
-- create schema damoim;
-- INSERT INTO member(id,pwd,addr,phone,email,name,age,gender) VALUES ('asd','123','경기도','010-1111-2222','sdm@gmail.com','감자',22 ,'M');

-- _은 자바 타입으로 매핑 해줘야 한다 

INSERT INTO member(id,pwd,addr,phone,email,name,age,gender) VALUES ('asd','123','경기도','010-1111-2222','sdm@gmail.com','감자',22 ,'M');

-- _은 자바 타입으로 매핑 해줘야 한다 

CREATE TABLE member ( -- 회원가입
    id VARCHAR(50) PRIMARY KEY, -- 아이디
    pwd VARCHAR(255) NOT NULL, -- 비밀번호
    addr VARCHAR(255), -- 주소
    phone VARCHAR(20), -- 전화번호
    email VARCHAR(100) UNIQUE, -- 이메일
    name VARCHAR(50), -- 이름
    age INT, -- 나이
    gender CHAR(1), -- 성별 M/F
    status BOOLEAN DEFAULT TRUE, -- 유저 가입,탈퇴 여부
	member_manner DECIMAL(4,2) DEFAULT (36.50), -- 유저온도 0.00 ~ 99.99까지가능
    nickname VARCHAR(50) UNIQUE, -- 닉네임
    member_img VARCHAR(255), -- 유저프로필사진
    member_hobby TEXT, -- 유저 관심사
    member_info TEXT, -- 유저 간단한 자기소개
    last_recommendation_time TIMESTAMP NULL DEFAULT NULL ,-- 유저의 마지막 추천 시간
    deleted_at DATETIME DEFAULT NULL -- 정보 삭제까지의 유예기간
    
);
select * from member;

CREATE TABLE type_category ( -- 유형 분류
    type_code INT PRIMARY KEY auto_increment, -- 소분류코드
    type_la_name VARCHAR(50),
    type_s_name VARCHAR(50) -- 소분류이름
);

CREATE TABLE location_category ( -- 위치 분류
    loc_code INT PRIMARY KEY auto_increment, -- 위치 분류 코드
	loc_la_name VARCHAR(50), -- ex) 서울 , 경기, 부산, 강원
    loc_s_name VARCHAR(50) -- 위치소분류 ex) 서울이면 강남구, 서초구  경기면 성남시, 부천시 이런식으로
);

CREATE TABLE membership ( -- 클럽
    membership_code INT PRIMARY KEY auto_increment, -- 클럽코드
    membership_name VARCHAR(100) UNIQUE, -- 클럽이름
    membership_img TEXT, -- 클럽메인사진
	membership_info LONGTEXT, -- 클럽소개
    membership_date DATE DEFAULT(current_date), -- 클럽생성날짜
    membership_max INT, -- 클럽최대인원
    membership_accession_text text,
	membership_simple_text VARCHAR(255)
);
create schema damoim_beta_test;
select * from membership;
CREATE TABLE membership_type  ( -- 클럽 유형 리스트 다 vs 대 풀어주기 
    mem_type_code INT PRIMARY KEY auto_increment, -- 클럽 유형 리스트 코드
    type_code INT, -- 소분류이름 / 외래키
    membership_code INT -- 클럽 /외래키
);
select * from membership_type;
CREATE TABLE membership_location  ( -- 클럽 지역 리스트 다 vs 대 풀어주기
    mem_loc_code INT PRIMARY KEY auto_increment, -- 클럽 지역 리스트 코드
    loc_code INT, -- 소분류 / 외래키
    membership_code INT -- 클럽 /외래키
);
select * from membership_location;

CREATE TABLE membership_user_list( -- 클럽유저정보 다 vs 대 풀어주기
    list_code INT PRIMARY KEY auto_increment, -- 클럽가입한 멤버코드
    list_grade VARCHAR(50), -- 멤버등급 호스트, 관리자, 일반회원, 비회원(신청만한사람)
    id VARCHAR(50), -- 유저코드 /외래키
    membership_code INT -- 클럽코드 /외래키
);

select * from membership_user_list;


CREATE TABLE membership_meetings ( -- 클럽모임게시판
    meet_code INT PRIMARY KEY auto_increment, -- 모임게시판코드
    membership_code INT, -- 클럽코드 /외래키
    meet_title VARCHAR(50), -- 글 타이틀
    meet_date_start DATE, -- 모임 시작일
    meet_date_end DATE, -- 모임 종료일
    meet_agree_code INT, -- 참여여부 테이블 연결
    meet_info LONGTEXT, -- 모임관련 정보
    id VARCHAR(50), -- 작성자
    color VARCHAR(50) -- 선택한 색깔 
);
select * from membership_meetings;

CREATE TABLE meetings_agree (-- 클럽 모임게시판 - 클럽 회원 리스트 참여여부 테이블 
	meet_agree_code INT PRIMARY KEY auto_increment,
    meet_agree_yn BOOLEAN DEFAULT(false),
    id VARCHAR(50),
    meet_code INT
);
select * from meetings_agree;


-- 클럽 모임 게시판에 댓글기능 추가 
CREATE TABLE meetings_comment (
    meet_comment_code INT PRIMARY KEY auto_increment, -- 댓글코드
    meet_comment_text TEXT, -- 모임게시판 댓글 내용
    meet_comment_date DATE DEFAULT(current_date), -- 댓글 게시시간
	id VARCHAR(50), --  /외래키  누가 ?
	meet_code INT, -- /왜래키 어떤클럽 홍보 게시판?
	meet_parents_comment_code INT -- /대댓글

);
select * from meetings_comment;

-- 확정
CREATE TABLE main_comment (
    main_comment_code INT PRIMARY KEY auto_increment, -- 댓글코드
    main_comment_text TEXT, -- 홍보게시판 댓글 내용
    main_comment_date DATE DEFAULT(current_date), -- 댓글 게시시간
	id VARCHAR(50), --  /외래키  누가 ?
	membership_code INT, -- /왜래키 어떤클럽 홍보 게시판?
	main_parents_comment_code INT -- /대댓글

);
select * from main_comment;


--   타입코드 - 맴버쉽 타입 - 맴버쉽 코드 연결 
ALTER TABLE membership_type ADD  FOREIGN KEY (type_code) REFERENCES type_category(type_code);
ALTER TABLE membership_type ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);
--   로케이션 - 맴버쉽 로케이션 - 맴버쉽 코드 연결 
ALTER TABLE membership_location ADD  FOREIGN KEY (loc_code) REFERENCES location_category(loc_code);
ALTER TABLE membership_location ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

-- 유저 id - 맴버쉽 유저리스트 - 맴버쉽 코드 연결
ALTER TABLE membership_user_list ADD  FOREIGN KEY (id) REFERENCES member(id);
ALTER TABLE membership_user_list ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);

-- 맴버쉽 미팅 - 맴버쉽 코드 // 미팅 코드와 agree 미팅 삭제시 동의도 삭제 //  게시글 작성자인 ID는 편의상 끊어둠 
ALTER TABLE membership_meetings ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code);
ALTER TABLE meetings_agree ADD FOREIGN KEY (meet_code) REFERENCES membership_meetings (meet_code) ON DELETE CASCADE;
-- 미팅게시글 댓글 - 작성자 // 게시글 - 댓글은 연결이고 게시글 삭제시 댓글 삭제
ALTER TABLE meetings_comment ADD  FOREIGN KEY (id) REFERENCES member(id);
ALTER TABLE meetings_comment ADD  FOREIGN KEY (meet_code) REFERENCES membership_meetings(meet_code) ON DELETE CASCADE;

-- 홍보게시글 댓글 - 작성자 // 게시글 - 댓글은 연결이고 게시글 삭제시 댓글 삭제
ALTER TABLE main_comment ADD  FOREIGN KEY (id) REFERENCES member(id);
ALTER TABLE main_comment ADD  FOREIGN KEY (membership_code) REFERENCES membership(membership_code) ON DELETE CASCADE;


 -- 이메일에 유니크 제약 조건 추가 -- 
 alter table member ADD unique key (email);
 
 -- 유저간 추천 쿨타임 24시간 이벤트
CREATE EVENT update_recommendations_test2
ON SCHEDULE EVERY 5 minute
DO
  UPDATE member
  SET last_recommendation_time = NULL
  WHERE last_recommendation_time IS NOT NULL
    AND last_recommendation_time < NOW() - INTERVAL 1 day;

-- 프로젝트 application.properties에 한국시간 맞춰야함
-- (요놈들 추가)
-- jdbc:mysql://localhost:3306/damoim?serverTimezone=Asia/Seoul
-- spring.datasource.url=jdbc:mysql://192.168.10.51:3306/damoim?serverTimezone=Asia/Seoul
-- 트리거 ===================================================================================
-- 트리거 CREATE 하려면 선생님한테 부탁해야함 (권한없어서)
-- status 가 false 되면 delete_at 이 현재 시간으로 찍힘
DELIMITER //
CREATE TRIGGER set_deleted_at
BEFORE UPDATE ON member
FOR EACH ROW
BEGIN
    IF NEW.status = false AND OLD.status <> false THEN
        SET NEW.deleted_at = NOW();
    END IF;
END //
DELIMITER ;

-- 회원 정보 삭제 이벤트

CREATE EVENT resigned_member_over30Day -- 이벤트 이름
ON SCHEDULE EVERY 1 day
COMMENT '회원탈퇴'
DO
UPDATE member
SET pwd = 'Resigned', -- pwd는 not null 조건있어서 string으로 update
    addr = null,      -- 이메일은 null처리 안함
    phone = null,
    name = null,
    nickname = null,
    age = null,
    gender = null,
    member_manner = null,
    member_img = null,
    member_hobby = null,
    member_info = null,
    member_location = null,
    member_type = null,
    last_recommendation_time = null
WHERE status = 0  
AND TIMESTAMPDIFF(day, deleted_at , now()) > 90;

