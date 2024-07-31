

INSERT INTO type_category_large(type_la_name) VALUES("액티비티");
INSERT INTO type_category_large(type_la_name) VALUES("푸드,드링크");
INSERT INTO type_category_large(type_la_name) VALUES("취미");
INSERT INTO type_category_large(type_la_name) VALUES("문화예술");
INSERT INTO type_category_large(type_la_name) VALUES("스터디");
START TRANSACTION;
-- 액티비티의 type_la_code를 찾기
SET @activity_code = (SELECT type_la_code FROM type_category_large WHERE type_la_name = '액티비티');
-- 푸드, 드링크의 type_la_code를 찾기
SET @food_drink_code = (SELECT type_la_code FROM type_category_large WHERE type_la_name = '푸드,드링크');
-- 취미의 type_la_code를 찾기
SET @hobby_code = (SELECT type_la_code FROM type_category_large WHERE type_la_name = '취미');
-- 문화예술의 type_la_code를 찾기
SET @culture_arts_code = (SELECT type_la_code FROM type_category_large WHERE type_la_name = '문화예술');
-- 스터디의 type_la_code를 찾기
SET @study_code = (SELECT type_la_code FROM type_category_large WHERE type_la_name = '스터디');
-- 액티비티 관련 데이터 삽입
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('자전거', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('배드민턴', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('등산', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('야구', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('서핑', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('클라이밍', @activity_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('농구', @activity_code);
-- 푸드, 드링크 관련 데이터 삽입
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('맛집투어', @food_drink_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('베이킹', @food_drink_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('카페', @food_drink_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('위스키', @food_drink_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('전통주', @food_drink_code);
-- 취미 관련 데이터 삽입
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('공예', @hobby_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('보드게임', @hobby_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('드로잉', @hobby_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('PC게임', @hobby_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('모바일게임', @hobby_code);
-- 문화예술 관련 데이터 삽입
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('영화', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('전시', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('페스티벌', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('힙합', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('미술', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('대중가요', @culture_arts_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('콘서트', @culture_arts_code);
-- 스터디 관련 데이터 삽입
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('코딩', @study_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('공무원', @study_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('자격증', @study_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('영어', @study_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('중국어', @study_code);
INSERT INTO type_category_small(type_s_name, type_la_code) VALUES ('일본어', @study_code);
COMMIT;

SELECT *
FROM type_category_small
JOIN type_category_large USING (type_la_code);
SELECT *
FROM type_category_large;
