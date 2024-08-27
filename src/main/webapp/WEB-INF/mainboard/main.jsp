<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클럽 홍보게시판</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

   <script
      src="https://kit.fontawesome.com/ef885bd654.js"
      crossorigin="anonymous"
    ></script>
<style>

</style>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	

	<div id="container">
		<h1>${main.membership.membershipName }</h1>
		<img id="mainImg"
			src="http://192.168.10.51:8081/membership/${main.membership.membershipCode}/${main.membership.membershipImg}">
		<h2>${main.membership.membershipInfo }</h2>
		<p>인원 현황 : ${main.count}/${main.membership.membershipMax}</p>

		<!-- 08-22 채승훈 로케이션타입 추가함 -->
		<div class="locationTypeBox">
			<div class="location">
				<c:forEach items="${location}" var="location">
					<div class="locationText">
						<div class="locationList"># ${location.locLaName}
							${location.locSName}</div>
					</div>
				</c:forEach>
			</div>
			<br>
			<div class="type">
				<c:forEach items="${type}" var="type">
					<div class="typeText">
						<div class="typeList">${type.typeSName}</div>
					</div>
				</c:forEach>
			</div>
		</div>


		<h2>호스트 : ${main.member.nickname}</h2>
		<p>가입조건 : 사지멀쩡한 남녀노소 누구나!!</p>



		<sec:authorize access="isAuthenticated()" var="principal">
			<sec:authentication property="principal" var="member" />

			<c:set var="memberGrade" value="none" />
			<c:forEach items="${member.memberListDTO}" var="list">
				<c:if
					test="${list.membershipCode == main.membership.membershipCode}">
					<c:set var="memberGrade" value="${list.listGrade}" />
				</c:if>
			</c:forEach>

			<c:choose>


				<c:when test="${memberGrade == 'guest'}">
					<p>가입 대기중인 클럽입니다</p>
				</c:when>
				<c:when
					test="${memberGrade == 'regular' || memberGrade == 'host' || memberGrade == 'admin'}">
					<a href="/club/${main.membership.membershipCode}">☞ 내 클럽 페이지로
						이동하기</a>
				</c:when>
				<c:when
					test="${memberGrade == 'none' && main.count >= main.membership.membershipMax}">
					<h2>최대 인원에 도달한 클럽입니다 신청할수 없습니다.</h2>
				</c:when>
		

				<c:when
					test="${memberGrade == 'none' && main.count < main.membership.membershipMax }">

					<form action="/membershipApply" method="post">
						<input type="submit" value="가입 신청하기"> <input type="hidden"
							name="membershipCode" value="${main.membership.membershipCode}">
						<input type="hidden" name="id" value="${member.id}"> <input
							type="hidden" name="listGrade" value="guest">
					</form>
				</c:when>
			</c:choose>
		</sec:authorize>
	</div>
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
					<input id="textbox" type="text" name="mainCommentText"
						placeholder="댓글을 입력하세요"> <input type="hidden" name="id"
						value="${member.id}"> <input type="hidden"
						name="membershipCode" value="${main.membership.membershipCode}">
					<button id="submit-comment" type="button">댓글 등록</button>
				</div>
			</form>
		</sec:authorize>
		<c:if test="${fn:length(comment) == 0}">
			<p>현재 등록된 댓글이 없습니다.</p>
		</c:if>
		<c:if test="${fn:length(comment) != 0}">
			<c:forEach items="${comment}" var="com">
				<div id="comm-${com.mainCommentCode}" class="comment">
					<c:if test="${com.mainCommentText == null}">
						<p>삭제된 댓글입니다.</p>
					</c:if>
					<c:if test="${com.mainCommentText != null}">
						<div class="comment-head">
							<div class="prof">${com.nickname}
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
							</div>${com.mainCommentDate}</div>
						<div class="comment-text">${com.mainCommentText}</div>
						<c:if
							test="${com.nickname == member.nickname || memberGrade == 'host'}">
							<button type="button"
								onclick="deleteComment(event, ${com.mainCommentCode})">삭제</button>
						</c:if>
						<c:if test="${com.nickname == member.nickname}">
							<button type="button"
								onclick="updateForm(${com.mainCommentCode})">수정</button>
							<div id="update-form-${com.mainCommentCode}" class="update-form">
								<form id="comment-frm-${com.mainCommentCode}">
									<div id="comment-box-update-${com.mainCommentCode}">
										<div class="prof">
											<label for="textbox-update${com.mainCommentCode}">
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
										<input id="textbox-update${com.mainCommentCode}" type="text"
											name="mainCommentText" value="${com.mainCommentText}">
										<button type="button"
											onclick="updateComment(event,${com.mainCommentCode})">댓글
											수정</button>
									</div>
								</form>
							</div>
						</c:if>
					</c:if>
					<button id="re-comment" type="button"
						onclick="showReplyForm(${com.mainCommentCode})">
						답글
						<c:if test="${fn:length(com.recoment) != 0}">(${fn:length(com.recoment)})</c:if>
					</button>
					<div class="recomment-box"
						id="recomment-box-${com.mainCommentCode}">
						<c:if test="${fn:length(com.recoment) != 0}">
							<c:forEach items="${com.recoment}" var="recom">
								<div id="comm-${recom.mainCommentCode}" class="re-comment">
									<div class="comment-head">
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
										</div>${recom.mainCommentDate}</div>
									<br />
									<div class="comment-text">${recom.mainCommentText}</div>
									<c:if
										test="${recom.nickname == member.nickname || memberGrade == 'host'}">
										<button type="button"
											onclick="deleteComment(event, ${recom.mainCommentCode})">삭제</button>
									</c:if>
									<c:if test="${recom.nickname == member.nickname}">
										<button type="button"
											onclick="updateForm(${recom.mainCommentCode})">수정</button>
										<div id="update-form-${recom.mainCommentCode}"
											class="update-form">
											<form id="comment-frm-${recom.mainCommentCode}">
												<div id="comment-box-update-${recom.mainCommentCode}">
													<label for="textbox-update${recom.mainCommentCode}">
														${member.nickname} : </label> <input
														id="textbox-update${recom.mainCommentCode}" type="text"
														name="mainCommentText" value="${recom.mainCommentText}">
													<button type="button"
														onclick="updateComment(event,${recom.mainCommentCode})">댓글
														수정</button>
												</div>
											</form>
										</div>
									</c:if>
								</div>
							</c:forEach>
						</c:if>
						<div id="reply-form-${com.mainCommentCode}" class="reply-form">
							<sec:authorize access="isAuthenticated()">
								<form id="comment-frm-${com.mainCommentCode}">
									<div id="comment-box-${com.mainCommentCode}">

										<div class="prof">
											<label for="textbox${com.mainCommentCode}">
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
										<input id="textbox${com.mainCommentCode}" type="text"
											name="mainCommentText" placeholder="대댓글을 입력하세요"> <input
											type="hidden" name="id" value="${member.id}"> <input
											type="hidden" name="membershipCode"
											value="${com.membershipCode}">
										<button type="button"
											onclick="recomment(event, ${com.mainCommentCode})">댓글
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

	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>