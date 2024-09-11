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

    // 비밀번호 생성용
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

    // 임시 비밀번호를 데이터베이스에 업데이트
    public void updatePassword(Member member) {
    	member.setPwd(bcpe.encode(member.getPwd())); // 비밀번호 암호화후
        memberMapper.updatePassword(member); // DB업데이트
    }
    // 이메일로 임시 비밀번호 전송
    public void sendTemporaryPasswordEmail(String email, String tempPassword) {
        String subject = "Damoim 사이트 임시 비밀번호 안내";
        String body = "당신의 임시 비밀번호는: " + tempPassword;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // 수신자
        message.setSubject(subject); // 제목
        message.setText(body); // 이메일 내용
        message.setFrom("dol9991@naver.com"); // 발신자 이메일 주소 설정
        try {
            mailSender.send(message);  // 발송
        } catch (Exception e) {

        }
        
    }
    // 임시 비밀번호를 생성하고 이메일로 전송 및 데이터베이스 업데이트
    public void processPasswordReset(Member member) {
            String tempPassword = generateTemporaryPassword();
            member.setPwd(tempPassword);
            updatePassword(member);
            sendTemporaryPasswordEmail(member.getEmail(), tempPassword);
    }
    // 회원 찾기
    public Member memberEmailIdcheck(Member member) {
    	return memberMapper.memberEmailIdcheck(member);
    } 
}
