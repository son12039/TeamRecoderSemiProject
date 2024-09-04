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

-- DROP TRIGGER IF EXISTS set_deleted_at; 트리거 삭제
SHOW TRIGGERS; -- 트리거 확인

-- 스케쥴러 ==================================================================================
DROP event resigned_member_over30Day; -- 이벤트 스케쥴러 삭제
select * from information_schema.events; -- 이벤트 스케쥴러 확인
-- SHOW VARIABLES LIKE 'event%'; -- 스케쥴러 on / off 체크
-- SET GLOBAL event_scheduler = ON; -- off 상태면 on 으로


CREATE EVENT resigned_member_over30Day -- 이벤트 이름
ON SCHEDULE EVERY 1 minute
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
WHERE status = 0  -- status가 false면
-- TIMESTAMPDIFF(시간단위, 시간 , 시간) <== 두 날짜 사이의 차이를 계산한다
AND TIMESTAMPDIFF(minute, deleted_at , now()) > 1;
-- 편의상 1분당 삭제로 설정중