

-- SELECT * FROM location_category_large
-- join location_category_small using (loc_la_code);


-- 위치대분류 데이터 삽입
INSERT INTO location_category_large (loc_la_name) VALUES
('서울특별시'),('부산광역시'),('대구광역시'),('인천광역시'),('광주광역시'),('대전광역시'),('경기도'),
('강원도'),('충청북도'),('충청남도'),('전라북도'),('전라남도'),('경상북도'),('경상남도'),('제주도');

-- 서울특별시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('종로구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('용산구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('성동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('광진구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('동대문구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('중랑구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('성북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('강북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('도봉구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('노원구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('은평구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('서대문구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('마포구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('양천구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('강서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('구로구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('금천구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('영등포구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('동작구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('관악구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('서초구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('강남구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('송파구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시')),
('강동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '서울특별시'));


-- 부산광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('영도구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('부산진구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('동래구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('남구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('해운대구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('사하구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('금정구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('강서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('연제구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('수영구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시')),
('사상구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '부산광역시'));

-- 대구광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('남구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('수성구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('달서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시')),
('달성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대구광역시'));

-- 인천광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('미추홀구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('연수구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('남동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('부평구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('계양구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시')),
('서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '인천광역시'));

-- 광주광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '광주광역시')),
('서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '광주광역시')),
('남구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '광주광역시')),
('북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '광주광역시')),
('광산구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '광주광역시'));

-- 대전광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대전광역시')),
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대전광역시')),
('서구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대전광역시')),
('유성구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대전광역시')),
('대덕구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '대전광역시'));

-- 울산광역시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('중구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '울산광역시')),
('남구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '울산광역시')),
('동구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '울산광역시')),
('북구', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '울산광역시')),
('울주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '울산광역시'));

-- 세종특별자치시 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('세종시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '세종특별자치시'));

-- 경기도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('수원시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('성남시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('용인시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('고양시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('화성시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('평택시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('안양시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('부천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('광명시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('과천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('오산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('의정부시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('구리시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('포천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('남양주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('동두천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('양주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('가평군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('연천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('여주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('이천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도')),
('안성시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경기도'));

-- 강원도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('춘천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('원주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('강릉시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('동해시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('속초시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('삼척시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('홍천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('횡성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('영월군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('평창군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('정선군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('철원군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('화천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('양구군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('인제군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('고성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도')),
('양양군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '강원도'));

-- 충청북도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('청주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('충주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('제천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('보은군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('옥천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('영동군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('진천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('괴산군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('음성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도')),
('단양군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청북도'));

-- 충청남도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('천안시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('공주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('보령시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('아산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('서산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('논산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('계룡시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('당진시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('금산군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('부여군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('서천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('청양군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('홍성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('예산군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도')),
('천안시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '충청남도'));

-- 전라북도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('전주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('익산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('군산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('정읍시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('남원시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('김제시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('완주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('진안군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('무주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('장수군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('임실군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('순창군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('고창군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도')),
('부안군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라북도'));

-- 전라남도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('광양시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('목포시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('순천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('여수시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('담양군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('장흥군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('강진군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('고흥군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('곡성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('구례군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('나주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('무안군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('신안군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('영광군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('영암군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('완도군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('장성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도')),
('함평군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '전라남도'));

-- 경상북도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('경산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('경주', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('구미시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('김천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('문경시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('상주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('안동시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('영주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('영천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('포항시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('군위군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('군산군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('봉화군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('상주군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('울진군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도')),
('울릉군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상북도'));

-- 경상남도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('거제시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('거창군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('고성군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('김해시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('밀양시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('사천시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('양산시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('진주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('창원시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('통영시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('함안군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('함양군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('산청군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('거창군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도')),
('합천군', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '경상남도'));

-- 제주도 소분류
INSERT INTO location_category_small (loc_s_name, loc_la_code) VALUES
('제주시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '제주도')),
('서귀포시', (SELECT loc_la_code FROM location_category_large WHERE loc_la_name = '제주도'));