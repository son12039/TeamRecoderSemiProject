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
<script type="module"
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
<script src="${pageContext.request.contextPath}/js/mypage.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 캘린더 라이브러리 -->
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>
</head>
<body>
	<jsp:include page="../header/header.jsp" />

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

	<!-- 프로필 업데이트 -->
	<div class="box">
		<div class="container">
			<div class="profile_update">

				<form action="/updateMember" method="post" id="form"
					enctype="multipart/form-data">
					<h1 class="profile">프로필 수정</h1>
					<!-- 프로필 이미지 -->
					<div class="profile_img">
						<c:choose>
							<c:when test="${member.memberImg != null}">
								<div id="image_container">
									<img
										src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}"
										alt="Profile Image">
								</div>
							</c:when>
							<c:otherwise>
								<img src="http://192.168.10.51:8081/기본프사.jpg"
									alt="Default Profile Image">
							</c:otherwise>
						</c:choose>
					</div>

					<!-- 파일 선택 input -->
					<div class="profile_file">
						<input class="form-control" name="file" type="file"
							accept="image/*" id="file" onchange="imgShow(event)"> <label
							for="file" class="profile_file_button">사진 선택</label>
						<!-- 기본 사진으로 변경 -->
						<input type="submit" id="defualt-file" name="defualt-file">
						<label for="defualt-file" class="profile_file_button">기본
							사진으로 변경</label>
					</div>

					<div class="profile_manner">
						<div class="profile_manner_info">
							<i class="fa-solid fa-user"></i>
							<h1>${member.nickname}</h1>
						</div>
						<div class="profile_manner_info">
							<c:choose>
								<c:when test="${member.memberManner < 30}">
									<i class="fa-solid fa-temperature-quarter"></i>
									<p>${member.memberManner}℃</p>
									<span style="color: red"><i
										class="fa-solid fa-face-angry fa-2x"></i></span>
								</c:when>
								<c:when
									test="${member.memberManner >= 30 && member.memberManner <= 40}">
									<i class="fa-solid fa-temperature-half"></i>
									<p>${member.memberManner}℃</p>
									<span style="color: rgb(252, 177, 3)"><i
										class="fa-solid fa-face-smile fa-2x"></i></span>
								</c:when>
								<c:otherwise>
									<i class="fa-solid fa-temperature-three-quarters"></i>
									<p>${member.memberManner}℃</p>
									<span style="color: green"><i
										class="fa-solid fa-face-grin fa-2x"></i></span>
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<!-- 프로필 정보 -->
					<div class="profile_info">
						<span>한줄소개 : </span> <input type="text" id="memberInfo"
							name="memberInfo" value="${member.memberInfo}"
							placeholder="소개글을 입력해주세요">
					</div>
					<div class="profile_info">
						<span>취미 : </span> <input type="text" id="memberHobby"
							name="memberHobby" value="${member.memberHobby}"
							placeholder="취미를 입력해주세요">
					</div>
					<div class="profile_submit">

						<input type="button" id="submit" value="수정"> <a
							href="/updateMemberInfo" id="updateCheck">회원정보 수정</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="club-button">
			<a id="all-club-button">가입 중인 모든 클럽</a> <a id="manage-club-button">내가
				관리중인 클럽</a> <a id="wait-club-button">가입 대기중인 클럽</a> <a
				id="all-meet-button">내 모임 정보</a>
		</div>
		<!-- 가입 대기중인 클럽 -->
		<div class="membership-card" id="wait-club">
			<div class="list_grade_text">
				<h1>가입 대기중인 클럽</h1>
				<i class=" fa-solid fa-user-plus"></i>
			</div>
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
							<h4>${mem.membership.membershipName}</h4>
							<p>${mem.membership.membershipSimpleText}</p>
							<c:if test="${mem.membership.membershipSimpleText == null}">
								<p>클럽의 소개글이 없습니다</p>
							</c:if>
							<button class="btn"
								onclick="deleteList('${guestClub}',${mem.membership.membershipCode})">신청
								취소</button>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>


		<!-- 관리중인 클럽 보기 -->
		<div class="membership-card" id="manage-club">
			<div class="list_grade_text">
				<h1>관리중인 클럽</h1>
				<i class="fa-solid fa-crown"></i>

			</div>
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
					<div class="membership-each">
						<div>
							<img class="membership-img"
								src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
								alt="Membership Image">
						</div>
						<div class="membership-String">
							<h4>${mem.membership.membershipName}</h4>
							<p>${mem.membership.membershipSimpleText}</p>
							<c:if test="${mem.membership.membershipSimpleText == null}">
								<p>클럽의 소개글이 없습니다</p>
							</c:if>
							<c:if test="${adminClub != 'host'}">
								<button class="btn"
									onclick="deleteList('${adminClub}',${mem.membership.membershipCode})">탈퇴</button>
							</c:if>
						</div>
					</div>

				</c:if>
			</c:forEach>
		</div>



		<!-- 가입 된 클럽 보기 -->
		<div class="membership-card" id="all-club" style="display: block;">
			<div class="list_grade_text">
				<h1>가입 된 클럽</h1>
				<i class="fa-solid fa-users"></i>
			</div>
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

								<h4>${mem.membership.membershipName}</h4>
								<p>${mem.membership.membershipSimpleText}</p>
								<c:if test="${mem.membership.membershipSimpleText == null}">
									<p>클럽의 소개글이 없습니다</p>
								</c:if>
								<c:if test="${myClub != 'host'}">
									<button class="btn"
										onclick="deleteList('${myClub}',${mem.membership.membershipCode})">탈퇴</button>
								</c:if>

							</div>
						</div>
					</a>
				</c:if>
			</c:forEach>
		</div>
		<!-- 캘린더 div -->
		<div class="membership-card" id="all-meet">
			<div id="calendar"></div>
		</div>
		<c:choose>
			<c:when test="${hasHost}">
					<p>저 클럽 카드 정보주에 내가 호스트인 클럽 1개 보여주면서 inpuit 비밀번호 받는거 어쩌구</p>
						<form action="/updateMembership" class="toggle_form">
							<button id="update-club" type="submit" value="클럽수정">클럽
								정보 수정</button>
						</form>
			</c:when>
			<c:otherwise>
				<div id="icon" onclick="show()">
					<h1>클럽 생성 및 수정</h1>
					<ion-icon name="chevron-down-outline" id="arrow"></ion-icon>
				</div>
				<div id="toggle_menu">
					<div id="menu">
						<form action="/makeMembership" class="toggle_form">
							<input type="hidden" name="id" value="${mem.id}">
							<button id="make-club" type="submit" value="클럽생성">클럽 만들기</button>
						</form>

					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<script type="module"
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
<script>
    const allDates = [];
    let allMeet = {};
    const endDate = [];
    <c:forEach items="${meetings}" var="item" varStatus="status">
    const a${status.index} = new Date("${item.meetDateEnd}")
   a${status.index}.setDate(a${status.index}.getDate()+1)
    a${status.index}1 = a${status.index}.toISOString().split('T')[0];
   endDate.push(a${status.index}1);
    </c:forEach>
    <c:forEach items="${meetings}" var="item" varStatus="status">
    	allMeet.title = "${item.meetTitle}";
    	
    	allMeet.start = "${item.meetDateStart}"; 	
    	allMeet.end = endDate[${status.index}];
    	allMeet.color = "${item.color}";
    	allMeet.meetCode= "${item.meetCode}";
    	<c:if test="${item.meetTitle != null}">
    	allDates.push(allMeet);
    	</c:if>
    	allMeet = {};
    </c:forEach>
    </script>
<script src="${pageContext.request.contextPath}/js/calendar.js"></script>
<script src="${pageContext.request.contextPath}/js/mypage.js"></script>

<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
		function imgShow(event) {
			 var reader = new FileReader();
			    
			    reader.onload = function(event) {
			        var container = document.getElementById('image_container');
			        container.innerHTML = '';

			        var img = document.createElement('img');
			        img.setAttribute('src', event.target.result);
			        container.appendChild(img);
			    };
			   
			    if (event.target.files.length > 0) {
			        reader.readAsDataURL(event.target.files[0]);
			    }
		}
		
		// 기본 사진으로 변경
		$("#defualt-file").click(() => {
			const formData = new FormData();
			formData.append("file", $("#defualt-file")[0].files[0]);
			$.ajax({
				type: "post",
				url: "/defualt-file",
				data: formData,
				 contentType: false,
	                processData: false,
	                success: function(result) {
	                	console.log(result);
	                    if (result) {
	                        alert("기본 사진으로 변경되었습니다");
	                        window.location.href = "/";
	                    } else {
	                    	alert("정보수정 실패");
	                    }
	                }
			});
		});
		
        // 파일 업로드 및 정보 수정 처리
        $("#submit").click(() => {
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
                	console.log(result);
                    if (result) {
                        alert("정보가 수정되었습니다");
                        window.location.href = "/";
                    } else {
                    	alert("정보수정 실패");
                    }
                }
            });
        });
        
    </script>
</html>