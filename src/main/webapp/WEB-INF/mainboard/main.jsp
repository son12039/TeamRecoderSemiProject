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
	href="${pageContext.request.contextPath}/css/main.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>

</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>

	<div id="box">
		<div id="container">

			<img id="mainImg"
				src="http://192.168.10.51:8081/membership/${main.membership.membershipCode}/${main.membership.membershipImg}">
			<div id="container-top">
				<div id="hostImg">
					<c:choose>
						<c:when test="${main.member.memberImg != null}">
							<img class="user-img"
								src="http://192.168.10.51:8081/member/${main.member.id}/${main.member.memberImg}">
						</c:when>

						<c:otherwise>
							<img class="user-img"
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
						</c:otherwise>
					</c:choose>
				</div>
				<div id="hostName">
					<h2>${main.member.nickname}</h2>
				</div>
				<div id="membershipTitle">
					<h1>${main.membership.membershipName }</h1>
				</div>
			</div>
			<div id="info-container">${main.membership.membershipInfo }</div>
			<div id="middle-container">

				<div id="middle-container-top">

					<p>멤버소개</p>

				</div>

				<div id="hostInfo">
					<div id="hostInfo-img">
						<span style="color: rgb(252, 177, 3)"><i
							class="fa-solid fa-crown"></i></span>
						<c:choose>
							<c:when test="${main.member.memberImg != null}">
								<img class="host-user-img"
									src="http://192.168.10.51:8081/member/${main.member.id}/${main.member.memberImg}">
							</c:when>

							<c:otherwise>
								<img class="host-user-img"
									src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
							</c:otherwise>
						</c:choose>

					</div>
					<div id="name-and-info">
						<div id="hostInfo-name">
							<p>${main.member.nickname}</p>
						</div>
						<div idv="hostInfo-Info">
							<p>${main.member.memberInfo}</p>
						</div>

					</div>

				</div>

				<div id="together">
					<p>함께하는 멤버</p>
				</div>
				<ul class="image-container" id="imageContainer">
					<c:forEach items="${allMember}" var="memberList" varStatus="status">


						<c:if test="${status.index < 5 }">
							<c:choose>
								<c:when test="${memberList.member.memberImg != null}">
									<li class="image-item"><img class="allmemberImg"
										src="http://192.168.10.51:8081/member/${memberList.member.id}/${memberList.member.memberImg}"
										alt="회원 이미지" />
								</c:when>
								<c:otherwise>
									<li class="image-item"><img class="user-img"
										src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
									</li>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${status.index == 5}">
							<c:choose>
								<c:when test="${memberList.member.memberImg != null}">

									<li class="image-item" id="last-item"><img
										class="allmemberImg" id="last-img2"
										src="http://192.168.10.51:8081/member/${memberList.member.id}/${memberList.member.memberImg}"
										alt="회원 이미지" />
										<div id="last-img" class="allmemberImg">
											<i class="fa-solid fa-ellipsis"></i>
										</div></li>

								</c:when>

								<c:otherwise>
									<li class="image-item" id="last-item"><img
										class="allmemberImg" id="last-img2"
										src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg"
										alt="회원 이미지" /> <c:if test="${allMember.size() > 6}">
											<div id="last-img" class="allmemberImg">
												<i class="fa-solid fa-ellipsis"></i>
											</div>
										</c:if></li>

								</c:otherwise>

							</c:choose>
						</c:if>

					</c:forEach>
				</ul>

			</div>

			<!-- 08-22 채승훈 로케이션타입 추가함 -->

			<div id="asd">자세한 정보</div>

			<div class="locTyBox">
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
				<p>
					<i class="fa-solid fa-user-group"></i>&nbsp;인원 현황 :
					${main.count}/${main.membership.membershipMax}
				</p>

				<p>가입조건 : 사지멀쩡한 남녀노소 누구나!!</p>

			</div>














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
							<input type="submit" value="가입 신청하기"> <input
								type="hidden" name="membershipCode"
								value="${main.membership.membershipCode}"> <input
								type="hidden" name="id" value="${member.id}"> <input
								type="hidden" name="listGrade" value="guest">
						</form>
					</c:when>
				</c:choose>
			</sec:authorize>
		</div>
	</div>
	
	
	
	<div id="comment-container">
		<sec:authorize access="isAuthenticated()">
			<form id="comment-frm">
				<div id="comment-box">
					<div class="prof">
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
					<div id="textboxBox">
						<div class=commentNick>
							<label for="textbox">${member.nickname}</label>
						</div>
						<input id="textbox" type="text" name="mainCommentText"
							placeholder="댓글을 입력하세요.."> <input type="hidden" name="id"
							value="${member.id}"> <input type="hidden"
							name="membershipCode" value="${main.membership.membershipCode}">

					</div>
					<button class="btn" id="submit-comment" type="button">댓글
						등록</button>
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

							<a href="/userInfo/${com.nickname}">

								<div class="prof">
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
									<div class=commentNick>${com.nickname}</div>
								</div>
							</a>${com.mainCommentDate}</div>
						<div class="comment-text">${com.mainCommentText}</div>
						<c:if
							test="${com.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
							<button type="button" class="btn"
								onclick="deleteComment(event, ${com.mainCommentCode})">삭제</button>
						</c:if>
						<c:if test="${com.nickname == member.nickname}">
							<button type="button" class="btn"
								onclick="updateForm(${com.mainCommentCode})">수정</button>
							<div id="update-form-${com.mainCommentCode}" class="update-form">
								<form id="comment-frm-${com.mainCommentCode}">
									<div id="comment-box-update-${com.mainCommentCode}">
										<div class="prof">
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
										<input id="textbox-update-${com.mainCommentCode}" type="text"
											name="mainCommentText" value="${com.mainCommentText}"
											onclick="updateKey(${com.mainCommentCode})">
										<button type="button" class="btn"
											onclick="updateComment(event,${com.mainCommentCode})">댓글
											수정</button>
									</div>
								</form>
							</div>
						</c:if>
					</c:if>
					<button type="button" class="btn"
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
										<div class="profBox">
											<a href="/userInfo/${recom.nickname}">
												<div class="prof">
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
													<div class="nickCommentBox">
														<div class=commentNick>${recom.nickname}</div>
														<div class="comment-text">${recom.mainCommentText}</div>
													</div>
												</div>
											</a>
											<div></div>
											<div class="textBtnBox">
												<div class="tetxDate">${recom.mainCommentDate}</div>
												<div class="textBtn">
													<c:if
														test="${recom.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
														<button type="button" class="btn"
															onclick="deleteComment(event, ${recom.mainCommentCode})">삭제</button>
													</c:if>
													<c:if test="${recom.nickname == member.nickname}">
														<button type="button" class="btn"
															onclick="updateForm(${recom.mainCommentCode})">수정</button>
													</c:if>
												</div>
											</div>
										</div>
										<c:if test="${recom.nickname == member.nickname}">
											<div id="update-form-${recom.mainCommentCode}"
												class="update-form">
												<form id="comment-frm-${recom.mainCommentCode}">
													<div id="comment-box-update-${recom.mainCommentCode}">
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
														<div class = "text-update-box">
															<label for="textbox-update-${recom.mainCommentCode}">
																${member.nickname} : </label> <input
																id="textbox-update-${recom.mainCommentCode}" type="text"
																name="mainCommentText" value="${recom.mainCommentText}"
																onclick="updateKey(${recom.mainCommentCode})">
														</div>
														<button type="button" class="btn"
															onclick="updateComment(event,${recom.mainCommentCode})">댓글
															수정</button>
													</div>
												</form>
											</div>
										</c:if>
									</div>

								</div>
							</c:forEach>
						</c:if>
						<div id="reply-form-${com.mainCommentCode}" class="reply-form">
							<sec:authorize access="isAuthenticated()">
								<form id="comment-frm-${com.mainCommentCode}">
									<div id="comment-box-${com.mainCommentCode}">

										<div class="prof">
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
										<div class="textBoxImg">
											<label for="textbox${com.mainCommentCode}">${member.nickname}</label>
											<input id="textbox${com.mainCommentCode}" type="text"
												name="mainCommentText" placeholder="대댓글을 입력하세요"
												onclick="reCommentKey(${com.mainCommentCode})"> <input
												type="hidden" name="id" value="${member.id}"> <input
												type="hidden" name="membershipCode"
												value="${com.membershipCode}">
										</div>
										<button type="button" class="btn"
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
	
	
	
	
	
	
	
	<jsp:include page="../footer/footer.jsp" />
	<script src="${pageContext.request.contextPath}/js/main.js"></script>

	<script>

	</script>
</body>
</html>