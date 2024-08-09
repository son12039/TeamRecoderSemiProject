
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>클럽 회원 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/membershipPage.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <main>
        <h1>${main.membership.membershipName}</h1>
        <img id="mainImg" src="${main.membership.membershipImg}" alt="클럽 이미지">
        <h2>${main.membership.membershipInfo}</h2>
        <p>인원 현황 : ${membershipUserCount}/${main.membership.membershipMax}</p>
        <h4>호스트 : ${main.member.nickname} 
            <img id="hostImg" src="${main.member.memberImg}" alt="호스트 이미지">
        </h4>

        <c:forEach items="${allMember}" var="cMember">
            <div class="memberTable">
                <c:choose>
                    <c:when test="${cMember.listGrade == 'guest'}">
                        <ul> 
                            <li>${cMember.member.nickname} - 가입 대기중</li>
                            <li><img class="allmemberImg" src="${cMember.member.memberImg}" alt="회원 이미지"></li>
                            <c:if test="${main.member.id == mem.id}">
                                <form action="/agreeMember" method="post">
                                    <input type="hidden" name="id" value="${cMember.member.id}">
                                    <input type="hidden" name="listGrade" value="regular">
                                    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
                                    <input type="submit" value="가입 승인하기">
                                </form>
                            </c:if>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul> 
                            <li>${cMember.member.nickname}</li>
                            <li><img class="allmemberImg" src="${cMember.member.memberImg}" alt="회원 이미지"></li>
                            <li>${cMember.listGrade}</li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
        <a href="/" id="toIndex">메인페이지로 가기</a>
    </main>
    <script src="${pageContext.request.contextPath}/js/membershipPage.js"></script>
</body>
</html>
