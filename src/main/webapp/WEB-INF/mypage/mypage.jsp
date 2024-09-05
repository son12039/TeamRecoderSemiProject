<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypage.css" />
<title>Document</title>
<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<style>
.membership-card {
	display: none;
}
</style>
<body>

	<jsp:include page="/WEB-INF/header/mypageHeader.jsp" />

	<!-- 로그인 정보에 따라 헤더와 메뉴 표시 -->
	<c:set var="hasHost" value="${false}" />
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<c:forEach items="${member.memberListDTO}" var="list">
			<c:if test="${list.listGrade == 'host'}">
				<c:set var="hasHost" value="${true}" />
			</c:if>
		</c:forEach>
	</sec:authorize>


	<!-- 프로필 수정 폼 -->
	<div class="info_container">
		<form action="/updateMember" method="post" id="form"
			enctype="multipart/form-data">
			<h1 class="profile">프로필 수정</h1>
			<!-- 프로필 이미지 -->
			<div class="profile_img">
				<c:choose>
					<c:when test="${member.memberImg != null}">
						<div>
						<img src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}"
							alt="Profile Image" id="image_container">
						</div>
						
					</c:when>
					<c:otherwise>
						<img src="http://192.168.10.51:8081/기본프사.jpg"
							alt="Default Profile Image">
					</c:otherwise>
				</c:choose>
			</div>
			<!-- 프로필 업데이트 -->
			<div class="profile_update">
				<input class="form-control" name="file" type="file" accept="image/*"
					id="file" onchange="imgShow(event)">
				<div class="profile_manner">
					<h1>${member.nickname}</h1>
					<c:if test="${member.memberManner < 30}">
						<p>${member.memberManner}℃</p>
						<span style="color: red"><i
							class="fa-solid fa-face-angry fa-2x"></i></span>
					</c:if>
					<c:if
						test="${member.memberManner >= 30 && member.memberManner <= 40}">
						<p>${member.memberManner}℃</p>
						<span style="color: rgb(252, 177, 3)"><i
							class="fa-solid fa-face-smile fa-2x"></i></span>
					</c:if>
					<c:if test="${member.memberManner > 40}">
						<p>${member.memberManner}℃</p>
						<span style="color: green"><i
							class="fa-solid fa-face-grin fa-2x"></i></span>
					</c:if>
				</div>
				<div class="profile_info">
					<span>한줄소개 : </span> <input type="text" id="memberInfo"
						name="memberInfo" value="${member.memberInfo}">
				</div>
				<div class="profile_info">
					<span>취미 : </span> <input type="text" id="memberHobby"
						name="memberHobby" value="${member.memberHobby}">
				</div>
				<div class="profile_submit">
					<input type="button" id="submit" value="수정"> <a
						href="/updateMemberInfo" id="updateCheck">회원정보 수정</a>
				</div>
			</div>
		</form>
	</div>

	<!-- 가입 대기중인 클럽 보기 -->
	<div class="container">
		<div class="club-button">
			<a id="all-club-button">가입 중인 모든 클럽</a> <a id="manage-club-button">내가
				관리중인 클럽</a> <a id="wait-club-button">가입 대기중인 클럽</a>
		</div>
		<div class="membership-card" id="wait-club">
			<h1>가입 대기중인 클럽</h1>
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
								src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
								alt="Membership Image">
						</div>
						<div class="membership-String">
							<div>
								<p>${mem.membership.membershipName}</p>
								<button class="btn" onclick="deleteList('${guestClub}',${mem.membership.membershipCode})" >신청 취소</button>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>

		<!-- 관리중인 클럽 보기 -->
		<div class="membership-card" id="manage-club">
			<h1>관리중인 클럽</h1>
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
									src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
									alt="Membership Image">
									
							</div>
							<div class="membership-String">
								<div>
									<p>${mem.membership.membershipName}</p>
									<c:if test="${adminClub != 'host'}">
									<button  class="btn" onclick="deleteList('${adminClub}',${mem.membership.membershipCode})" >탈퇴</button>
									</c:if>
									
									
										
									
								</div>
							</div>
						</div>
					</a>
					비밀번호 확인<input type="password" name="pwdCheck" id="pwdCheck">
										<button class="btn" onclick="allDeleteMembership()">클럽 삭제</button>
				</c:if>
			</c:forEach>
		</div>

		<!-- 가입 된 클럽 보기 -->
		<div class="membership-card" id="all-club" style="display: block;">

			<h1>가입 된 클럽</h1>
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
					test="${myClub == 'regular' || myClub == 'host' || myClub == 'admin'}">
					<a href="/club/${mem.membership.membershipCode}">
						<div class="membership-each">
							<div>
								<img class="membership-img"
									src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
									alt="Membership Image">
							</div>
							<div class="membership-String">
								<div>
									<p>${mem.membership.membershipName}</p>
									<c:if test="${myClub != 'host'}">
									<button class="btn" onclick="deleteList('${myClub}',${mem.membership.membershipCode})" >탈퇴</button>
									</c:if>
								</div>
							</div>
						</div>
					</a>
				</c:if>
			</c:forEach>
		</div>
	</div>

	<!-- 토글 -->
	</div>
	<div class="container">
		<c:choose>
			<c:when test="${hasHost}">
				<p>클럽 생성 기능이 활성화되지 않았습니다. 이미 보유중인 클럽이 있습니다.</p>
			</c:when>
			<c:otherwise>
				<input type="checkbox" id="toggle" hidden>
				<label for="toggle" id="label">클럽 생성및 수정 <ion-icon
						name="chevron-down-outline" id="arrow"></ion-icon>
				</label>
				<ul id="menu">
					<a href="/makeMembership">클럽 만들기</a>
					<form action="/updateMembership">
						<button id="update-club" type="submit" value="클럽수정">클럽 정보
							수정</button>
					</form>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<script type="module"
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
<script src="${pageContext.request.contextPath}/js/mypage.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

		function imgShow(event) {
		    var reader = new FileReader();
		
		    reader.onload = function(event) {
		        var img = document.createElement("img");
		        img.setAttribute("src", event.target.result);
		        document.querySelector("div#image_container").appendChild(img);
		    };
		    
		    if (event.target.files.length > 0) {
		        reader.readAsDataURL(event.target.files[0]);
		    }
		}


        // 파일 업로드 및 정보 수정 처리
        $("#updateSubmit").click(() => {
            const formData = new FormData();
            formData.append("memberInfo", $("#memberInfo").val());
            formData.append("memberHobby", $("#memberHobby").val());
            formData.append("file", $("#file")[0].files[0]);
            
            $.ajax({
                type: "post",
                url: "/updateMember",
                data: formData,
                contentType: false,
                processData: false,
                success: function(result) {
                    if (result) {
                        alert("정보가 수정되었습니다");
                        window.location.href = "/";
                    }
                }
            });
        });
    </script>
</body>
</html>