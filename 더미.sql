

SELECT *
FROM type_category_small
JOIN type_category_large USING (type_la_code);
SELECT *
FROM type_category_large;

SELECT *
FROM member;

select *
from member ;


select *
from membership ;

insert into member (id, pwd, addr, phone, email, name, age, gender, nickname, member_img, member_hobby, member_info, member_location, member_type)
values ('asd123', '123' , '감자왕국',"010-0000-1234",'감자',5,'M','감자대마왕', '');

insert into user (id, user_name, user_info)
values ('asd123','감자대마왕', '저는감자를좋아해요');