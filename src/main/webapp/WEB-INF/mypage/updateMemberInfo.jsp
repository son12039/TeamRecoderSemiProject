<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions"%> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원 정보 수정</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/reset.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/updateMemberInfo.css"
    />
  </head>
  <body>
    <sec:authentication property="principal" var="member" />
    <jsp:include page="/WEB-INF/header/mypageHeader.jsp" />

    <!-- (동문) -->
    <div class="container">
      <h1>회원 정보 수정</h1>
      <div class="form-group">
        <form
          action="/updateMemberInfo"
          method="post"
          id="form"
          onsubmit="return validate()"
        >
          <div class="form-group">
            <label for="nickname"
              ><span style="color: red">*</span>닉네임
              <span class="result" id="nicknameResult"></span
            ></label>
            <input
              type="text"
              id="nickname"
              name="nickname"
              placeholder="닉네임을 입력하십시오"
              value="${member.nickname}"
            />
          </div>
          <div class="form-group">
            <label for="pwd"
              ><span style="color: red">*</span> 비밀번호
              <span class="result" id="pwdResult"></span
            ></label>
            <input
              type="password"
              id="pwd"
              name="pwd"
              placeholder="비밀번호를 입력하십시오."
            />
          </div>
          <div class="form-group">
            <label for="pwdc"
              ><span style="color: red">*</span> 비밀번호
              <span class="result" id="pwdcResult"></span
            ></label>
            <input
              type="password"
              id="pwdc"
              placeholder="위와 같은 비밀번호를 입력하십시오."
            />
          </div>
          <div class="form-group">
            <label for="name"
              ><span style="color: red">*</span>이름<span
                class="result"
                id="nameResult"
              ></span
            ></label>
            <input
              type="text"
              id="name"
              name="name"
              placeholder="이름을 입력하십시오."
              value="${member.name}"
              required
            />
          </div>
          <div class="form-group">
            <label for="phone">연락처</label>
            <input
              type="text"
              id="phone"
              name="phone"
              placeholder="전화번호를 입력하십시오."
              value="${member.phone}"
            />
          </div>
          <div class="form-group">
            <label for="addr">주소</label>
            <div id="addrDetail-box">
              <c:set var="addressParts" value="${fn:split(member.addr, '#')}" />
              <c:choose>
                <c:when test="${fn:length(addressParts) == 2}">
                  <div class="form-group">
                    <input
                      type="text"
                      id="sample5_address"
                      name="addr"
                      value="${addressParts[0]}"
                      placeholder="주소"
                    />
                    <input
                      type="button"
                      id="addr-btn"
                      onclick="sample5_execDaumPostcode()"
                      value="주소 검색"
                    />
                    <input
                      type="text"
                      id="addrDetail"
                      name="addrDetail"
                      value="${addressParts[1]}"
                      placeholder="상세주소를 입력해주세요"
                    />
                  </div>
                </c:when>
              </c:choose>
            </div>
          </div>
          <div class="form-group">
            <label for="email"
              ><span style="color: red">*</span>이메일
              <span class="result" id="emailResult"></span
            ></label>
            <input
              type="text"
              id="email"
              name="email"
              placeholder="이메일을 입력해주십시오."
              value="${member.email}"
            />
          </div>
          <div class="form-group">
            <label for="age"
              ><span style="color: red">*</span>나이<span
                class="result"
                id="ageResult"
              ></span
            ></label>
            <input
              type="text"
              id="age"
              name="age"
              placeholder="나이를 입력해 주십시오."
              value="${member.age}"
            />
          </div>
          <div class="form-group">
            <label for="beforePwd"
              ><span style="color: red">*</span>이전 비밀번호
              <span style="color: red" class="result" id="beforePwdResult"
                >${text}</span
              ></label
            >
            <input
              type="text"
              id="beforePwd"
              name="beforePwd"
              placeholder="이전 비밀번호를 입력하세요"
            />
          </div>
          <div class="button_box">
            <button type="submit" id="updateSubmit">변경</button>
            <i class="fa-solid fa-xmark"></i
            ><a href="/memberDelete" class="resign">회원탈퇴</a>
          </div>
        </form>
      </div>
    </div>
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>

    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="${pageContext.request.contextPath}/js/updateMemberInfo.js"></script>
  </body>
</html>
