package com.damoim.service;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    // 난수 생성에 사용할 문자 집합 정의
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";

    // 모든 문자 집합을 하나로 결합
    private static final String ALL_CHARACTERS = UPPER_CASE + LOWER_CASE + DIGITS + SPECIAL_CHARACTERS;

    // 난수 생성기
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        String recipientEmail = "recipient@example.com"; // 수신자 이메일 주소
        String senderEmail = "your-email@example.com"; // 발신자 이메일 주소
        String senderPassword = "your-email-password"; // 발신자 이메일 비밀번호
        String host = "smtp.example.com"; // SMTP 서버 주소
        int port = 587; // SMTP 서버 포트 번호 (예: 587, 465)

        try {
            // 임시 비밀번호 생성
            String tempPassword = generatePassword(12);

            // 이메일 내용 설정
            String subject = "임시 비밀번호 발송";
            String body = "안녕하세요,\n\n임시 비밀번호는 다음과 같습니다:\n" + tempPassword + "\n\n감사합니다.";

            // 이메일 전송
            sendEmail(senderEmail, senderPassword, recipientEmail, subject, body, host, port);

            System.out.println("임시 비밀번호가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 주어진 길이의 랜덤 비밀번호를 생성합니다.
     * 
     * @param length 비밀번호의 길이
     * @return 생성된 비밀번호
     */
    public static String generatePassword(int length) {
        if (length < 12) {
            throw new IllegalArgumentException("Password length must be at least 12 characters.");
        }

        StringBuilder password = new StringBuilder(length);

        // 각 문자 집합에서 하나씩 문자 선택
        password.append(randomCharacter(UPPER_CASE));
        password.append(randomCharacter(LOWER_CASE));
        password.append(randomCharacter(DIGITS));
        password.append(randomCharacter(SPECIAL_CHARACTERS));

        // 나머지 문자 랜덤으로 추가
        for (int i = 4; i < length; i++) {
            password.append(randomCharacter(ALL_CHARACTERS));
        }

        // 비밀번호의 순서를 무작위로 섞기
        return shuffleString(password.toString());
    }

    /**
     * 주어진 문자 집합에서 랜덤한 문자 하나를 선택합니다.
     * 
     * @param characterSet 문자 집합
     * @return 선택된 문자
     */
    private static char randomCharacter(String characterSet) {
        int index = RANDOM.nextInt(characterSet.length());
        return characterSet.charAt(index);
    }

    /**
     * 문자열의 문자를 무작위로 섞습니다.
     * 
     * @param input 입력 문자열
     * @return 섞인 문자열
     */
    private static String shuffleString(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            char temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return new String(array);
    }

    /**
     * 이메일을 전송합니다.
     * 
     * @param from 발신자 이메일 주소
     * @param password 발신자 이메일 비밀번호
     * @param to 수신자 이메일 주소
     * @param subject 이메일 제목
     * @param body 이메일 내용
     * @param host SMTP 서버 주소
     * @param port SMTP 서버 포트 번호
     */
    private static void sendEmail(String from, String password, String to, String subject, String body, String host, int port) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
