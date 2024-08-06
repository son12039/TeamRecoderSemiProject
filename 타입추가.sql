INSERT INTO type_category (type_la_name, type_s_name) VALUES
('액티비티', '자전거'),
('액티비티', '배드민턴'),
('액티비티', '등산'),
('액티비티', '야구'),
('액티비티', '서핑'),
('액티비티', '클라이밍'),
('액티비티', '농구');

-- 푸드, 드링크 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('푸드,드링크', '맛집투어'),
('푸드,드링크', '베이킹'),
('푸드,드링크', '카페'),
('푸드,드링크', '위스키'),
('푸드,드링크', '전통주');

-- 취미 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('취미', '공예'),
('취미', '보드게임'),
('취미', '드로잉'),
('취미', 'PC게임'),
('취미', '모바일게임');

-- 문화예술 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('문화예술', '영화'),
('문화예술', '전시'),
('문화예술', '페스티벌'),
('문화예술', '힙합'),
('문화예술', '미술'),
('문화예술', '대중가요'),
('문화예술', '콘서트');

-- 스터디 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('스터디', '코딩'),
('스터디', '공무원'),
('스터디', '자격증'),
('스터디', '영어'),
('스터디', '중국어'),
('스터디', '일본어');

SELECT * FROM type_category;