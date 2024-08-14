package com.damoim.service;

import com.damoim.model.vo.Member;
import mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EmailService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private JavaMailSender mailSender;
    
    private BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 비밀번호 생성에 사용할 문자 집합과 길이
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int PASSWORD_LENGTH = 12;

    // 임시 비밀번호 생성 메서드
    private String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    // 비밀번호를 데이터베이스에 업데이트
    public void updatePassword(Member member) {
    	System.out.println("임시 비밀번호로 변경시작" + member.getPwd());
    	member.setPwd(bcpe.encode(member.getPwd()));
    	System.out.println("임시 비밀번호 암호화 : " + member.getPwd());
        memberMapper.updatePassword(member);
        System.out.println("임시 비밀번호로 변경끝");
    }



    // 이메일로 임시 비밀번호 전송
    public void sendTemporaryPasswordEmail(String email, String tempPassword) {
        String subject = "Dimoim 사이트 임시 비밀번호 안내";
        String body = "당신의 임시 비밀번호는: " + tempPassword;
        System.out.println("메일 전송 로직 도착");
        System.out.println("임시 비밀번호 생성완료! " + tempPassword);
        System.out.println("보낼 주소 : " + email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // 수신자
        message.setSubject(subject); // 제목
        message.setText(body); // 이메일 내용
        message.setFrom("dol9991@naver.com"); // 발신자 이메일 주소 설정

        try {
            mailSender.send(message);
            System.out.println("임시 비밀번호 이메일이 성공적으로 발송되었습니다!");
        } catch (Exception e) {
            // 예외 메시지를 로그로 남기거나 적절한 처리
            System.err.println("이메일 전송 오류: " + e.getMessage());
        }
        
    }
    public Member memberEmailIdcheck(Member member) {
    	return memberMapper.memberEmailIdcheck(member);
    } 

    // 임시 비밀번호를 생성하고 이메일로 전송 및 데이터베이스 업데이트

    public void processPasswordReset(Member member) {
    
        System.out.println("이메일 메서드로 보내는 서비스 도착");
 
        
        System.out.println("회원 조회 결과 : " + member);
        if (member != null) { // 이메일 아이디 체크
            String tempPassword = generateTemporaryPassword();
            member.setPwd(tempPassword);
            updatePassword(member);
            sendTemporaryPasswordEmail(member.getEmail(), tempPassword);
        } else {
            // 사용자 정보가 존재하지 않는 경우 처리 로직 추가
            System.out.println("사용자 없음!");
        }
    }
}
