

INSERT INTO type_category (type_la_name, type_s_name) VALUES
('종교', '기독교'),
('종교', '불교'),
('종교', '원불교'),
('종교', '천주교'),
('종교', '기타 종교');
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('금융', '투자 세미나'),
('금융', '주식 투자'),
('금융', '부동산 투자'),
('금융', '암호화폐'),
('금융', '재무 계획'),
('금융', '기타');

INSERT INTO type_category (type_la_name, type_s_name) VALUES
('취미', '공예'),
('취미', '독서'),
('취미', '보드게임'),
('취미', '드로잉'),
('취미', 'PC게임'),
('취미', '모바일게임'),
('취미', '요리'),
('취미', '기타');

-- 문화예술 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('문화예술', '영화'),
('문화예술', '전시'),
('문화예술', '페스티벌'),
('문화예술', '힙합'),
('문화예술', '미술'),
('문화예술', '대중가요'),
('문화예술', '콘서트'),
('문화예술', '기타');

-- 스터디 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('스터디', '코딩'),
('스터디', '공무원'),
('스터디', '자격증'),
('스터디', '영어'),
('스터디', '중국어'),
('스터디', '일본어'),
('스터디', '토론'),
('스터디', '기타');

-- 푸드 & 드링크 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('푸드 & 드링크', '맛집투어'),
('푸드 & 드링크', '베이킹'),
('푸드 & 드링크', '카페'),
('푸드 & 드링크', '위스키'),
('푸드 & 드링크', '전통주'),
('푸드 & 드링크', '기타');

-- 여행 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('여행', '해외여행'),
('여행', '국내여행'),
('여행', '캠핑'),
('여행', '기타');

-- 스포츠 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('스포츠', '축구'),
('스포츠', '야구'),
('스포츠', '농구'),
('스포츠', '배구'),
('스포츠', '당구'),
('스포츠', '배드민턴'),
('스포츠', '스키'),
('스포츠', '수영'),
('스포츠', '기타');

-- 건강 & 웰빙 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('건강 & 웰빙', '요가'),
('건강 & 웰빙', '명상'),
('건강 & 웰빙', '헬스'),
('건강 & 웰빙', '피트니스'),
('건강 & 웰빙', '다이어트'),
('건강 & 웰빙', '기타');



-- 사회적 활동 대분류 및 소분류 데이터 삽입
INSERT INTO type_category (type_la_name, type_s_name) VALUES
('사회적 활동', '자원봉사'),
('사회적 활동', '사회적 기업'),
('사회적 활동', '지역 사회 활동'),
('사회적 활동', '커뮤니티 행사'),
('사회적 활동', '기타');

SELECT * FROM type_category;