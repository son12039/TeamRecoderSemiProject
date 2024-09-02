

SELECT *
FROM type_category;

SELECT *
FROM membership_location;

SELECT *
FROM location_category;

SELECT id, member_manner, last_recommendation_time
FROM member
WHERE id IN('user010', 'user006');
UPDATE member 
	SET member_manner = member_manner - 0.5
	WHERE id = user006;
	
select *
from member ;

		SELECT list_grade, id, membership_code 
		FROM membership_user_list
		WHERE id = 'asd123';
select* 
FROM main_comment;
select * from membership;


ALTER TABLE membership
DROP COLUMN memership_secret_text;

ALTER TABLE membership
CHANGE COLUMN memership_accession_text membership_accession_text text;

create table test_col(
	info text
);
select * from test_col;
drop table test_col;

INSERT INTO test_col(info)
SELECT
    membership_info
FROM
    membership
WHERE
    membership_info != 0;
    
UPDATE membership
SET membership_simple_text = membership_info;   
select *
from membership ;

SHOW COLUMNS FROM membership;

SELECT *
FROM membership_user_list
JOIN membership USING (membership_code)
JOIN member USING (id)
WHERE membership_code = 1;

SELECT * FROM membership_user_list;

SELECT count(membership_code) as count
FROM membership_user_list
WHERE membership_code = 1
GROUP BY membership_code
;

insert into member (id, pwd, addr, phone, email, name, age, gender, nickname, member_img, member_hobby, member_info, member_location, member_type)
values ('asd123', '123' , '감자왕국',"010-0000-1234",'감자',5,'M','감자대마왕', '');

insert into user (id, user_name, user_info)
values ('asd123','감자대마왕', '저는감자를좋아해요');

SELECT * FROM membership_us;

 SELECT
            ms.membership_code AS membershipCode,
            ms.Membership_name AS membershipName,
            ms.Membership_img AS membershipImg,
            ms.Membership_info AS membershipInfo,
            ms.Membership_date AS membershipDate,
            ms.Membership_grade AS membershipGrade,
            ms.Membership_max AS membershipMax,
            COUNT(mul.membership_code) AS count,
            host.id AS hostId,
            host.name AS hostName,
            host.nickname AS hostNickname,
            host.member_img AS hostMemberImg
        FROM
            membership ms
        LEFT JOIN
            membership_user_list mul ON ms.membership_code = mul.membership_code
            AND mul.list_grade = 'member'
        LEFT JOIN
            membership_user_list host_ul ON ms.membership_code = host_ul.membership_code
            AND host_ul.list_grade = 'host'
        LEFT JOIN
            member host ON host_ul.id = host.id
        GROUP BY
            ms.membership_code,
            ms.Membership_name,
            ms.Membership_img,
            ms.Membership_info,
            ms.Membership_date,
            ms.Membership_grade,
            ms.Membership_max,
            host.id,
            host.name,
            host.nickname,
            host.member_img
