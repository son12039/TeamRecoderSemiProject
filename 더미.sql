

SELECT *
FROM type_category_small
JOIN type_category_large USING (type_la_code);
SELECT *
FROM type_category_large;

SELECT *
FROM member;

select *
from user
left join member using(id);

insert into member (id,pwd,name)
values ('asd123', '123' , '감자');

insert into user (id, user_name, user_info)
values ('asd123','감자대마왕', '저는감자를좋아해요');