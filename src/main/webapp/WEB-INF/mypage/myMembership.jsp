<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myMembership.css" />

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
.membership-card {
	display: none;
}
</style>
</head>

<body>
	<nav>
		<div>
			<a id="LOGO" href="/">다모임</a>
		</div>
		<div>
			<a id="all-club-button">가입 중인 모든 클럽</a> <a id="manage-club-button">내가
				관리중인 클럽</a> <a id="wait-club-button">가입 대기중인 클럽</a>
		</div>
		<div>
			<a href="/">HOME</a> <a href="/logout">로그아웃</a>
		</div>
	</nav>





	<%-- 로그인 정보에 DTO에  host인게 있다면 --%>
	<c:set var="hasHost" value="${false}" />
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />

		<c:set var="memberGrade" value="none" />
		<c:forEach items="${member.memberListDTO}" var="list">
			<c:if test="${list.listGrade == 'host'}">
				<c:set var="hasHost" value="${true}" />
			</c:if>
		</c:forEach>
	</sec:authorize>





	<div class="membership-card" id="wait-club">
		<h1>가입 대기중인 클럽 보기</h1>
		<c:forEach items="${membership}" var="mem">
			<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
				<c:forEach items="${member.memberListDTO}" var="list">
					<c:if
						test="${list.membershipCode == mem.membership.membershipCode}">
						<c:set var="guestClub" value="${list.listGrade}" />
					</c:if>
				</c:forEach>
			</sec:authorize>
			<c:if test="${guestClub == 'guest'}">

				<div class="membership-each">
					<div>
						<img class="membership-img"
							src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}">
					</div>
					<div class="membership-String">
						<div>
							<p>${mem.membership.membershipName}</p>
						</div>
						<div>
							<p>${mem.membership.membershipInfo}</p>
						</div>
					</div>
				</div>

			</c:if>

		</c:forEach>
	</div>

	<div class="membership-card" id="manage-club">
		<h1>관리중인 클럽 보기</h1>
		<c:forEach items="${membership}" var="mem">
			<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
				<c:forEach items="${member.memberListDTO}" var="list">
					<c:if
						test="${list.membershipCode == mem.membership.membershipCode}">
						<c:set var="adminClub" value="${list.listGrade}" />
					</c:if>

				</c:forEach>
			</sec:authorize>
			<c:if test="${adminClub == 'host' || adminClub == 'admin'}">
				<a href="/club/${mem.membership.membershipCode}">
					<div class="membership-each">
						<div>
							<img class="membership-img"
								src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}">
						</div>
						<div class="membership-String">
							<div>
								<p>${mem.membership.membershipName}</p>
							</div>
							<div>
								<p>${mem.membership.membershipInfo}</p>
							</div>
						</div>
					</div>
				</a>
			</c:if>

		</c:forEach>
	</div>
	<div class="membership-card" id="all-club" style="display: block;">
		<h1>가입 된 클럽 보기</h1>
		<c:forEach items="${membership}" var="mem">
			<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
				<c:forEach items="${member.memberListDTO}" var="list">
					<c:if
						test="${list.membershipCode == mem.membership.membershipCode}">
						<c:set var="myClub" value="${list.listGrade}" />
					</c:if>

				</c:forEach>
			</sec:authorize>
			<c:if
				test="${myClub == 'regular'|| myClub == 'host' || myClub == 'admin'}">
				<a href="/club/${mem.membership.membershipCode}">
					<div class="membership-each">
						<div>
							<img class="membership-img"
								src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}">
						</div>
						<div class="membership-String">
							<div>
								<p>${mem.membership.membershipName}</p>
							</div>
							<div>
								<p>${mem.membership.membershipInfo}</p>
							</div>
						</div>
					</div>
				</a>
			</c:if>

		</c:forEach>
	</div>
	
	
	
	<div>
		<c:choose>
			<c:when test="${!hasHost}">
				<form action="/makeMembership">
					<input type="hidden" name="id" value="${mem.id}">
					<button id="make-club" type="submit" value="클럽생성">클럽 만들기</button>
				</form>
				<form action="/updateMembership">
					<button id="update-club" type="submit" value="클럽수정">클럽 정보
						수정</button>
				</form>
			</c:when>
			<c:otherwise>

				<p>클럽 생성 기능이 활성화되지 않았습니다. 이미 보유중인 클럽이 있습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>

	<script src="${pageContext.request.contextPath}/js/myMembership.js">
		
	</script>
</body>
</html>