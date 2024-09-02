<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/meetingDetail.css" />



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>

</head>
<body>

<h1>${meet.meetTitle}</h1>
<h1>미트코드 : ${meet.meetCode}</h1>
<h1>작성자 : ${meet.id}</h1>

 <div id="meetInfo" >${meet.meetInfo}</div>


<c:forEach items="${list}" var="list" >
<c:if test="${list.meetAgreeYn == true }">
<p>참석여부 현황 : ${list.member.nickname} ---- 참여</p> 
</c:if>
<c:if test="${list.meetAgreeYn == false }">
<p>참석여부 현황 : ${list.member.nickname} ---- 불참</p> 
</c:if>
	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />
<c:if test="${member.nickname == list.member.nickname}">
<form id="go">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="hidden" name="id" value="${member.id}">
<input type="hidden" name="meetAgreeYn" value="${list.meetAgreeYn}"> 

<c:choose>
    <c:when test="${list.meetAgreeYn == false}">
        <input class="agree" type="submit" value="참가">
    </c:when>
    <c:otherwise>
        <input class="agree" type="submit" value="참가취소">
    </c:otherwise>
</c:choose>
</form>
</c:if>
</sec:authorize>

</c:forEach>
	
			<c:set var="memberGrade" value="none" />
			<c:forEach items="${member.memberListDTO}" var="list">
				<c:if
					test="${list.membershipCode == meet.membershipCode}">
					<c:set var="memberGrade" value="${list.listGrade}" />

				</c:if>
			</c:forEach>
<form action="/meetingDelete">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="submit" value="삭제">

</form>
<div id="comment-container">
		<sec:authorize access="isAuthenticated()">
			<form id="comment-frm">
				<div id="comment-box">
					<div class="prof">
						<label for="textbox"> ${member.nickname}</label>
						<c:choose>
							<c:when test="${member.memberImg != null}">
								<img class="user-img"
									src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
							</c:when>

							<c:otherwise>
								<img class="user-img"
									src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
							</c:otherwise>
						</c:choose>
					</div>
					<input id="textbox" type="text" name="meetCommentText"
						placeholder="댓글을 입력하세요"  > <input type="hidden" name="id"
						value="${member.id}"> <input type="hidden"
						name="meetCode" value="${meet.meetCode}">
					<button id="submit-comment" type="button">댓글 등록</button>
				</div>
			</form>
		</sec:authorize>
		<c:if test="${fn:length(comment) == 0}">
			<p>현재 등록된 댓글이 없습니다.</p>
		</c:if>
		<c:if test="${fn:length(comment) != 0}">
			<c:forEach items="${comment}" var="com">
				<div id="comm-${com.meetCommentCode}" class="comment">
					<c:if test="${com.meetCommentText == null}">
						<p>삭제된 댓글입니다.</p>
					</c:if>
					<c:if test="${com.meetCommentText != null}">
						<div class="comment-head">
						
						<a href="/userInfo/${com.nickname}">
						
							<div class="prof" >${com.nickname}
							
								<c:choose>
									<c:when test="${com.memberImg != null}">
										<img class="user-img"
											src="http://192.168.10.51:8081/member/${com.id}/${com.memberImg}">
									</c:when>

									<c:otherwise>
										<img class="user-img"
											src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
										
									</c:otherwise>
								</c:choose>
							</div></a>${com.meetCommentDate}</div>
						<div class="comment-text">${com.meetCommentText}</div>
						<c:if
							test="${com.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
							<button type="button"
								onclick="deleteComment(event, ${com.meetCommentCode})">삭제</button>
						</c:if>
						<c:if test="${com.nickname == member.nickname}">
							<button type="button" class="btn"
								onclick="updateForm(${com.meetCommentCode})">수정</button>
							<div id="update-form-${com.meetCommentCode}" class="update-form">
								<form id="comment-frm-${com.meetCommentCode}">
									<div id="comment-box-update-${com.meetCommentCode}">
										<div class="prof">
											<label for="textbox-update-${com.meetCommentCode}">
												${member.nickname}</label>
											<c:choose>
												<c:when test="${member.memberImg != null}">
													<img class="user-img"
														src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
												</c:when>

												<c:otherwise>
													<img class="user-img"
														src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
												</c:otherwise>
											</c:choose>
										</div>
										<input id="textbox-update-${com.meetCommentCode}" type="text"
											name="meetCommentText" value="${com.meetCommentText}" onclick="updateKey(${com.meetCommentCode})">
										<button type="button"
											onclick="updateComment(event,${com.meetCommentCode})">댓글
											수정</button>
									</div>
								</form>
							</div>
						</c:if>
					</c:if>
					<button type="button" class="btn"
						onclick="showReplyForm(${com.meetCommentCode})">
						답글
						<c:if test="${fn:length(com.recoment) != 0}">(${fn:length(com.recoment)})</c:if>
					</button>
					<div class="recomment-box"
						id="recomment-box-${com.meetCommentCode}">
						<c:if test="${fn:length(com.recoment) != 0}">
							<c:forEach items="${com.recoment}" var="recom">
								<div id="comm-${recom.meetCommentCode}" class="re-comment">
									<div class="comment-head">
										<a href="/userInfo/${recom.nickname}">
										<div class="prof">${recom.nickname}
											<c:choose>
												<c:when test="${recom.memberImg != null}">
													<img class="user-img"
														src="http://192.168.10.51:8081/member/${recom.id}/${recom.memberImg}">
												</c:when>

												<c:otherwise>
													<img class="user-img"
														src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
												</c:otherwise>
											</c:choose>
										</div></a>${recom.meetCommentDate}</div>
									<br />
									<div class="comment-text">${recom.meetCommentText}</div>
									<c:if
										test="${recom.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
										<button type="button"
											onclick="deleteComment(event, ${recom.meetCommentCode})">삭제</button>
									</c:if>
									<c:if test="${recom.nickname == member.nickname}">
										<button type="button" class="btn"
											onclick="updateForm(${recom.meetCommentCode})">수정</button>
										<div id="update-form-${recom.meetCommentCode}"
											class="update-form">
											<form id="comment-frm-${recom.meetCommentCode}">
												<div id="comment-box-update-${recom.meetCommentCode}">
													<label for="textbox-update-${recom.meetCommentCode}">
														${member.nickname} : </label> <input
														id="textbox-update-${recom.meetCommentCode}" type="text"
														name="mainCommentText" value="${recom.meetCommentText}"
														onclick="updateKey(${recom.meetCommentCode})">
													<button type="button"
														onclick="updateComment(event,${recom.meetCommentCode})">댓글
														수정</button>
												</div>
											</form>
										</div>
									</c:if>
								</div>
							</c:forEach>
						</c:if>
						<div id="reply-form-${com.meetCommentCode}" class="reply-form">
							<sec:authorize access="isAuthenticated()">
								<form id="comment-frm-${com.meetCommentCode}">
									<div id="comment-box-${com.meetCommentCode}">

										<div class="prof">
											<label for="textbox${com.meetCommentCode}">
												${member.nickname}</label>
											<c:choose>
												<c:when test="${member.memberImg != null}">
													<img class="user-img"
														src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
												</c:when>

												<c:otherwise>
													<img class="user-img"
														src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
												</c:otherwise>
											</c:choose>
										</div>
										<input id="textbox${com.meetCommentCode}" type="text"
											name="meetCommentText" placeholder="대댓글을 입력하세요" onclick="reCommentKey(${com.meetCommentCode})"> 
											<input type="hidden" name="id" value="${member.id}"> 
											<input type="hidden" name="meetCode" value="${com.meetCode}">
										<button type="button"
											onclick="recomment(event, ${com.meetCommentCode})">댓글
											등록</button>
									</div>
								</form>
							</sec:authorize>
						</div>
					</div>

				</div>
			</c:forEach>
		</c:if>
	</div>
<script src="${pageContext.request.contextPath}/js/meetingDetail.js"></script>
  <script>
  
  
  
        $(document).ready(function() {
            $('#go').on('submit', function(event) {
                event.preventDefault(); // 폼의 기본 제출을 막습니다.
                
                $.ajax({
                    url: '/go', // 요청을 보낼 URL
                    type: 'POST',
                    data: $(this).serialize(), // 폼의 데이터를 직렬화합니다.
                    success: function(response) {
                        alert($(".agree").val()+" 완료되었습니다 !!");
                         location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert('오류가 발생했습니다.');
                        console.error(xhr.responseText);
                    }
                });
            });
        });
    </script>
</body>
</html>