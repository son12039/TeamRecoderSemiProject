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

<style>

</style>
</head>
<body>

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
							<div class="locationList"># ${location.locLaName} ${location.locSName}</div>
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
				<label for="textbox"> ${member.nickname} : </label> <input
					id="textbox" type="text" name="mainCommentText"
					placeholder="댓글을 입력하세요"> <input type="hidden" name="id"
					value="${member.id}"> <input type="hidden"
					name="membershipCode" value="${main.membership.membershipCode}">
				<button id="submit-comment" type="button">댓글 등록</button>
			</div>
		</form>
		</sec:authorize>
		<c:choose>
			<c:when test="${fn:length(comment) == 0}">

				<p>현재 등록된 댓글이 없습니다.</p>
			</c:when>

			<c:otherwise>

				<c:forEach items="${comment}" var="com">
					<div id="comm-${com.mainCommentCode}" class="comment">
						<div><h1>${com.nickname}</h1>${com.mainCommentDate}</div>
						댓글 내용 ${com.mainCommentText} <br />
					
						<c:if test="${com.nickname == member.nickname}">
							<button type="button" onclick="updateForm(${com.mainCommentCode})">수정</button>
							<div id="update-form-${com.mainCommentCode}" class="update-form">
								<form id="comment-frm-${com.mainCommentCode}">
									<div id="comment-box-update-${com.mainCommentCode}">
										<label for="textbox-update${com.mainCommentCode}">
											${member.nickname} : </label> <input
											id="textbox-update${com.mainCommentCode}" type="text"
											name="mainCommentText" value="${com.mainCommentText}">
										<button type="button"
											onclick="updateComment(event,${com.mainCommentCode})">댓글
											수정</button>
									</div>
								</form>
							</div>
						</c:if>
						<c:if
							test="${com.nickname == member.nickname || memberGrade == 'host'}">
							<button type="button"
								onclick="deleteComment(event, ${com.mainCommentCode})">삭제</button>
						</c:if>
						<button type="button"
							onclick="showReplyForm(${com.mainCommentCode})">
							<img
								src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEMAAABDCAYAAADHyrhzAAAACXBIWXMAAAsTAAALEwEAmpwYAAAD3klEQVR4nO1bSYhcVRR9DRJjUJGoaCRQIVTXO+fcX3SknBChcWc0ghBdiIIgKCpiTAgOIM5Ig8EhunHhQtCNA24EF4oYEEFxoBGjgkayUVFQSCuJJEZ51W2M9JD+VfXrvuqqA4cPxS849/CG+9+7N4QRRhhhhAwh6dRmjE0zbDHgDgkPGTll5PNGvjD3nJr7/fb0Xno//S8MOoqiASO3GfmKyK9M/MvEv8tS4hERe414uSDvasYYwyCA5MVGPmfi950EXsKgfUbslnRRyAn1ev10I3dK/LpKAxY1htgrYUeM8TQ3EwCcaeQTIn7zMGEBU36V8LiktX0zYXJy8iQJt0r82duAJUy5V9KqSo2QtMnEL7wDXp4pnDaziSp8GEtuG/Gnd5AlecjIe5L+nrhQq9VWp+0xg8C6GSWvt1qtNV0ZURTFORI/8Q6mN4bg4031+tkdGdFoNM4alPWhxAiZTrtgB7kDPvcWXxE/LZPijxnxWgaiKyTeXNaiKuE+f7F9ILlzSSMANNrbkbfQ/vCQmXHx6SHsyUBk3yjyvQWnSwFc5S3OxRDpivlrBfmBtzAfYs//jDCzC/1F+ZHk+f+ZQe7yFuRKcuq47ZT73AU5UsS3c0boXG8xlgGTD6F9Wp2BGHMmyc0p49zhLcRyIHB3MuMxdyHKgOSjIR25uwtRBiR2p53kaXchyoDkrpRjPOIuRP6U8GAoyJu9hVgGlHBTMIuXeguxDNi+qqzX6yebeHCojSD+SD7MfpsI73oLciXx9vFHfbe4C3JkWjePmTExMXGGETNDOipm5p2WG/mUuzAHSnhy3klXjPG8oRsdxMyit2wS7h+yUfHMkrUXRnw0HEbwSLM5vjGcsDBNPOAttk+3aicGyc3JOX/B1ZHkNWG5sFSLKR71Fl1VqdOxjLOEIdtWoiESXixlxL8wi1tX2reLpBtCpyiKeIGJ33gH0SvGGDeEbnDJ+vWnSHjWO5CuSfwYegVJ1xqx3z2oDinx/dBL1Gq11RK2m/iLd3CV5Rcd1o0/bOIPA2TGO6FKTKY03rBF5Bu5F86K/C70C41UMtnOT/CZd+CL8Gjp8sdeAEBh5AOpIDWr5M3i1p4E2CkArCvIG1P2l4aq61QRXwo5AcA6Sde1+9Mq7lxagAfS4h9yRbM5vjGNnHbjXqriJQ5XaUhB3hYGBa1Wa42ky+YW41dF/NTTqUJOh0FGjHHDbAbMqXTX03VLmMXLwwrCGMlxCden032JH5apcE7lnmElQ9Kq1D6WPtNnm4b5lhFfivh9IUNIXhmGEZLWtvvsLF4t4c70KZGe3rpGGGGEMJD4Bxew+s340mLFAAAAAElFTkSuQmCC">
						</button>
						<div id="reply-form-${com.mainCommentCode}" class="reply-form">
						<sec:authorize access="isAuthenticated()">
							<form id="comment-frm-${com.mainCommentCode}">
								<div id="comment-box-${com.mainCommentCode}">
									<label for="textbox${com.mainCommentCode}">
										${member.nickname} : </label> <input
										id="textbox${com.mainCommentCode}" type="text"
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
						<c:if test="${fn:length(com.recoment) != 0}">
							<c:forEach items="${com.recoment}" var="recom">
								<div id="comm-${recom.mainCommentCode}" class="re-comment">
									<div><h1>${recom.nickname}</h1>${recom.mainCommentDate}</div>
									주인님 코드 ${recom.mainParentsCommentCode} <br /> 대댓글 내용 ${recom.mainCommentText}
									 <br />
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
									<c:if
										test="${recom.nickname == member.nickname || memberGrade == 'host'}">
										<button type="button"
											onclick="deleteComment(event, ${recom.mainCommentCode})">삭제</button>
									</c:if>
								</div>
							</c:forEach>
						</c:if>

					</div>
				</c:forEach>

			</c:otherwise>
		</c:choose>


		
	</div>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>