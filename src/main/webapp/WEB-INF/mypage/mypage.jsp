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
<script
	src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<jsp:include page="../header/header.jsp" />
	<!-- 프로필 업데이트 -->
	<div class="box">
		<sec:authorize access="isAuthenticated()" var="principal">
			<sec:authentication property="principal" var="member" />
			<div class="container">
				<div class="profile_update">
					<form action="/updateMember" method="post" id="form"
						enctype="multipart/form-data">
						<h1 class="profile">프로필 수정</h1>
						<!-- 프로필 이미지 -->
						<div class="profile_img">
							<c:choose>
								<c:when test="${member.memberImg != null}">
									<div class="image_container">
										<img
											src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}"
											alt="Profile Image">
									</div>
								</c:when>
								<c:otherwise>
									<div class="image_container">
										<img src="http://192.168.10.51:8081/기본프사.jpg"
											alt="Default Profile Image">
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- 파일 선택 input -->
						<div class="profile_file">
							<input class="form-control" name="file" type="file"
								accept="image/*" id="file" onchange="imgShow(event)"> <label
								for="file" class="profile_file_button">사진 선택</label>
							<!-- 기본 사진으로 변경 -->
							<input type="submit" id="defualt-file" name="defualt-file"
								class="form-control"> <label for="defualt-file"
								class="profile_file_button">기본 사진으로 변경</label>
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

							<input type="submit" id="submit" value="수정"> <a
								href="/updateMemberInfo" id="updateCheck">회원정보 수정</a>
						</div>
					</form>
				</div>
			</div>



			<div class="container">
				<div class="club-button">
					<a id="all-club-button">가입 한 클럽</a> <a id="manage-club-button">
						관리중인 클럽</a> <a id="wait-club-button">가입 신청 목록</a> <a
						id="all-meet-button">내 모임 정보</a>
				</div>
				<c:set var="guestCount" value="0" />
				<c:set var="allCount" value="0" />
				<c:set var="manageCount" value="0" />
				<c:forEach items="${member.memberListDTO}" var="loginMemberGrade">
					<c:if test="${loginMemberGrade.listGrade == 'geust'}">
						<c:set var="guestCount" value="${guestCount + 1}" />
					</c:if>
					<c:if test="${loginMemberGrade.listGrade != 'geust'}">
						<c:set var="allCount" value="${allCount + 1}" />
					</c:if>
					<c:if
						test="${loginMemberGrade.listGrade == 'host' || loginMemberGrade.listGrade == 'admin'}">
						<c:set var="manageCount" value="${manageCount + 1}" />
					</c:if>
				</c:forEach>

				<!-- 가입 된 클럽 보기 -->
				<div class="membership-card" id="all-club" style="display: block;">
					<div class="list_grade_text">
						<h1>
							가입 된 클럽 <i class="fa-solid fa-users"></i>
						</h1>

						<c:if test="${allCount == 0}">
							<p class="if-text">현재 가입된 클럽이 존재하지 않습니다.</p>
						</c:if>

					</div>
					<c:forEach items="${mypage}" var="mem">
						<c:set var="myGrade" value="${mem.listGrade}" />
						<c:if test="${myGrade != 'guest'}">
							
								<div class="membership-each">
									<div>
									<a href="/club/${mem.membership.membershipCode}">
										<c:if test="${mem.membership.membershipImg != null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
												alt="Membership Image">
										</c:if>
										<c:if test="${mem.membership.membershipImg == null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
										</c:if>
									</div>
									</a>
									<div class="membership-String">
										<div class="member-grade">
											<c:if test="${myGrade == 'host'}">
												<h5>호스트</h5>
												<i class="fa-solid fa-crown"></i>
											</c:if>
											<c:if test="${myGrade == 'admin'}">
												<h4>관리자</h4>
												<i class="fa-solid fa-screwdriver-wrench"></i>
											</c:if>
										</div>
										<h4>${mem.membership.membershipName}</h4>
										<p>${mem.membership.membershipSimpleText}</p>
										<div class="membership-count">
											<i class="fa-solid fa-users"></i>
											<p>${mem.count}</p>
											/
											<p>${mem.membership.membershipMax}</p>
										</div>
										<c:if test="${mem.membership.membershipSimpleText == null}">
											<p>클럽의 소개글이 없습니다</p>
										</c:if>
										<!-- 클럽 정보 수정 -->
										<div class="Management-button-group">
											<c:if test="${myGrade == 'host'}">
												<form action="/updateMembership"
													style="margin: 0px; padding: 0px">
													<button class="Management-button" id="update-club"
														type="submit" value="클럽수정">클럽 정보 수정</button>
												</form>
												<button class="Management-button" id="deleteButton">클럽삭제</button>
											</c:if>
											<c:if test="${myGrade != 'host'}">
												<button class="Management-button"
													onclick="deleteList('${loginMemberGrade}',${mem.membership.membershipCode})">탈퇴</button>
											</c:if>
										</div>
									</div>
								</div>
							
						</c:if>
					</c:forEach>
				</div>

				<!-- 관리중인 클럽 보기 -->
				<div class="membership-card" id="manage-club">
					<div class="list_grade_text">
						<h1>
							내가 관리중인 클럽 <i class="fa-solid fa-user-tie"></i>
						</h1>

						<c:if test="${manageCount == 0}">
							<p class="if-text">현재 관리중인 클럽이 존재하지 않습니다.</p>
						</c:if>
					</div>
					<c:forEach items="${mypage}" var="mem">
						<c:set var="myGrade" value="${mem.listGrade}" />
						<c:if test="${myGrade == 'host' || myGrade == 'admin'}">
							
								<div class="membership-each">
								<a href="/club/${mem.membership.membershipCode}">
									<div>
										<c:if test="${mem.membership.membershipImg != null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
												alt="Membership Image">
										</c:if>
										<c:if test="${mem.membership.membershipImg == null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
										</c:if>
									</div>
									</a>
									<div class="membership-String">
										<div class="member-grade">
											<c:if test="${myGrade == 'host'}">
												<h5>호스트</h5>
												<i class="fa-solid fa-crown"></i>
											</c:if>
											<c:if test="${myGrade == 'admin'}">
												<h4>관리자</h4>
												<i class="fa-solid fa-screwdriver-wrench"></i>
											</c:if>
										</div>
										<h4>${mem.membership.membershipName}</h4>
										<p>${mem.membership.membershipSimpleText}</p>
										<div class="membership-count">
											<i class="fa-solid fa-users"></i>
											<p>${mem.count}</p>
											/
											<p>${mem.membership.membershipMax}</p>
										</div>

										<!-- 클럽 정보 수정 -->
										<div class="Management-button-group">
											<c:if test="${myGrade == 'host'}">

												<form action="/updateMembership"
													style="margin: 0px; padding: 0px">
													<button class="Management-button" id="update-club"
														type="submit" value="클럽수정">클럽 정보 수정</button>
												</form>
												<button id="deleteButton" class="Management-button">클럽삭제</button>
											</c:if>
											<c:if test="${myGrade != 'host'}">
												<button class="Management-button"
													onclick="deleteList('${loginMemberGrade}',${mem.membership.membershipCode})">탈퇴</button>
											</c:if>
										</div>
									</div>
								</div>
							
						</c:if>
					</c:forEach>
				</div>

				<!-- 가입 대기중인 클럽 -->
				<div class="membership-card" id="wait-club">
					<div class="list_grade_text">
						<h1>
							가입 신청한 클럽 <i class=" fa-solid fa-user-plus"></i>
						</h1>

						<c:if test="${guestCount == 0}">
							<p class="if-text">현재 가입신청한 클럽이 존재하지 않습니다.</p>
						</c:if>
					</div>
					<c:forEach items="${mypage}" var="mem">
						<c:set var="myGrade" value="${mem.listGrade}" />
						<c:if test="${myGrade == 'guest'}">
							
								<div class="membership-each">
								<a href="/${mem.membership.membershipCode}">
									<div>
										<c:if test="${mem.membership.membershipImg != null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"
												alt="Membership Image">
										</c:if>
										<c:if test="${mem.membership.membershipImg == null}">
											<img class="membership-img"
												src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
										</c:if>
									</a>
									</div>
									<div class="membership-String">
										<h4>${mem.membership.membershipName}</h4>
										<p>${mem.membership.membershipSimpleText}</p>
										<div class="membership-count">
											<i class="fa-solid fa-users"></i>
											<p>${mem.count}</p>
											/
											<p>${mem.membership.membershipMax}</p>
										</div>
										<c:if test="${mem.membership.membershipSimpleText == null}">
											<p>클럽의 소개글이 없습니다</p>
										</c:if>
										<button class="btn"
											onclick="deleteList('${loginMemberGrade}',${mem.membership.membershipCode})">신청
											취소</button>
									</div>
								</div>
							
						</c:if>
					</c:forEach>

				</div>
				<!-- 내모임정보 (캘린더)  -->
				<div class="membership-card" id="all-meet">
					<div id="calendar"></div>
				</div>

				<!-- boolean 선언후  ======================================== -->
				<c:set var="host" value="${false}" />
				<c:forEach items="${member.memberListDTO}" var="loginMemberGrade">
					<c:if test="${loginMemberGrade.listGrade == 'host'}">
						<!-- grade가 호스트이면 set 변경 -->
						<c:set var="host" value="${true}" />
						<c:set var= "hostCode" value="${loginMemberGrade.membershipCode}"/>
							<c:forEach items="${mypage}" var="mem">
								<c:if test="${hostCode == mem.membership.membershipCode}">
								<c:set var="hostCount" value="${mem.count == 1}" />
								</c:if>
							</c:forEach>
					</c:if>
				</c:forEach>



				<c:if test="${!host}">
					<!-- 호스트 아닐때 -->

					<div id="create-menu">


						<a href="/makeMembership" class="create-btn">클럽 만들기</a>
					</div>

				</c:if>

			</div>
			<c:set var="textColor">
				<c:choose>
					<c:when test="${!hostCount}">
            red
        </c:when>
					<c:otherwise>
            black
        </c:otherwise>
				</c:choose>
			</c:set>
			<div id="deleteMembership" style="display: none">
				<div id="deleteContainer">
					<div id="container-title">
						<span id="title">클럽 삭제 창</span>
					</div>
					<div id="deleteCancle">
						<button id="cancle">
							<i class="fa-solid fa-x"></i>
						</button>
					</div>
				</div>
				<div id="container-main">
					<div id="delete-text">
						<span style="color : ${textColor}">클럽원이 본인만 남아있는 클럽만</span> 삭제할 수
						있으며 해당 클럽에 대한 모든 데이터는 삭제 처리 됩니다 그래도 삭제하시겠습니까?
					</div>

					<div id="passwordCheck">
						비밀번호 확인 : <input type="password" name="pwdCheck" id="pwdCheck">
					</div>
					<div id="container-button">
						<c:if test="${hostCount}">.

					<button id="deleteBtn" class="btn" onclick="allDeleteMembership()">클럽
								삭제</button>

						</c:if>
						<c:if test="${!hostCount}">
							<button id="deleteBtn" class="btn">삭제 불가</button>
						</c:if>

					</div>

				</div>
			</div>
		</sec:authorize>
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
<script src="${pageContext.request.contextPath}/js/moving.js"></script>
<script src="${pageContext.request.contextPath}/js/mypage.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(".mainMenu").mouseenter((e) => {
		  let contents = $(e.target).siblings(); // 형제들
	
		 
		  if (contents.css("display") === "none") {
		    contents.slideDown();
		  }
		
		});
	
	
	$("#menu").mouseleave((e) => {
		  let contents = $(".mainMenu").siblings(); // 본인 기준 바로 다음
	
		 
		 
			  contents.slideUp();
			  
		  
		});
	
	
	$("#deleteButton").click(()=>{
		if($("#deleteMembership").css('display') == 'none'){
			$("#deleteMembership").show();
			
		} else {
			
			$("#deleteMembership").hide();
		}
		
	})
	
	$("#cancle").click(()=>{
		$("#deleteMembership").hide();
	})
	
	

	</script>

<script>
			function imgShow(event) {
				 var reader = new FileReader();
				 var imageContainer = document.querySelector("div.image_container");
					 /* 미리보기 하면 원래 넣어놨던 이미지 없앤뒤 다시 미리보기 */
				 	 imageContainer.innerHTML = '';
				 	
				    reader.onload = function(event) {
				        var img = document.createElement('img');
				        /* img객체 불러온후 reader.onload 이벤트 setAttribute로 넣음*/
				        img.setAttribute('src', event.target.result);
				        /* 그리고 imageContainer 안에 appendChile로 img 넣음*/
				        imageContainer.appendChild(img);
						console.log(img);
				    };
				   
				    console.log("파일 길이" , event.target.files.length);
				    
				    /* reader.onload 이벤트의 파일 길이가 0 이상이면 ??? */
				    if (event.target.files.length > 0) {
				        reader.readAsDataURL(event.target.files[0]);
				    }
				    
			}
			
			// 기본 사진으로 변경
			$("#defualt-file").click((event) => {
				 event.preventDefault();
				$.ajax({
					type: "post",
					url: "/defualtFile",
					 contentType: false,
		             processData: false,
		             success: function(result) {
		                	console.log(result);
		                    if (result) {
		                        alert("기본 사진으로 변경되었습니다");
		                        window.location.href = "/mypage";
		                    } else {
		                    	alert("정보수정 실패");
		                    }
		                }
				});
			});
			
			
	        // 파일 업로드 및 정보 수정 처리
	        $("#submit").click((event) => {
	        	event.preventDefault();
	        	
	            const formData = new FormData();
	            formData.append("memberInfo", $("#memberInfo").val());
	            formData.append("memberHobby", $("#memberHobby").val());
	            formData.append("file", $("#file")[0].files[0]);
	            console.log(formData);
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
	                        window.location.href = "/mypage";
	                    } else {
	                    	alert("정보수정 실패");
	                    }
	                }
	            });
	        });
	</script>
</html>