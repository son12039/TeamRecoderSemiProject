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
<title>${main.membership.membershipName}</title>
   <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>
     <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
     
        <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
   
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/membershipPage.css" />
<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>
</head>
<style>




</style>
<body>
 <jsp:include page="../header/header.jsp" />
 

		<sec:authentication property="principal" var="member" />
		<c:forEach items="${member.memberListDTO}" var="loginMember">
				<c:if
					test="${loginMember.membershipCode == main.membership.membershipCode}">
					<c:set var="memberGrade" value="${loginMember.listGrade}" />

				</c:if>
			</c:forEach>
	<div id="main-container">

		<div class="accordion" id="accordionExample">
			<div class="accordion-item">
				<h2 class="accordion-header">
					<button class="accordion-button collapsed " type="button"
						data-bs-toggle="collapse" data-bs-target="#collapseOne"
						aria-expanded="true" aria-controls="collapseOne">함께하는
						멤버들</button>
				</h2>
				<div id="collapseOne" class="accordion-collapse collapse "
					data-bs-parent="#accordionExample">
					<div class="accordion-body">

						<c:forEach items="${allMember}" var="listMember">

							<div class="memberTable">
								<ul>
									<div class="member-img-icon-nickname-manner">
										<div class="member-img-icon">
											<div class="member-icon">
												<c:if test="${listMember.listGrade == 'host'}">
													<li class="member-grade"><span><i
															class="fa-solid fa-crown"></i></span></li>
												</c:if>
												<c:if test="${listMember.listGrade == 'regular' }">
													<li class="member-grade">일반회원</li>
												</c:if>
												<c:if test="${listMember.listGrade == 'admin' }">
													<li class="member-grade">관리자</li>
												</c:if>
											</div>
											<div class="member-img">
												<c:if test="${listMember.member.memberImg != null}">
													<li><img class="allmemberImg"
														src="http://192.168.10.51:8081/member/${listMember.member.id}/${listMember.member.memberImg}"
														alt="회원 이미지"></li>
												</c:if>
												<c:if test="${listMember.member.memberImg == null}">
													<img class="allmemberImg"
														src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg"
														alt="회원 이미지">
												</c:if>
											</div>
										</div>


										<div class="nickname-manner">

											<div class="nickname">${listMember.member.nickname}</div>


											<div class="manner">
												<c:if test="${listMember.member.memberManner < 30}">
													<p>${listMember.member.memberManner}℃</p>
													<span style="color: red"><i
														class="fa-solid fa-face-angry fa-2x"></i></span>
												</c:if>
												<c:if
													test="${listMember.member.memberManner >= 30 && listMember.member.memberManner <= 40}">
													<p>${listMember.member.memberManner}℃</p>
													<span style="color: rgb(252, 177, 3)"><i
														class="fa-solid fa-face-smile fa-2x"></i></span>
												</c:if>
												<c:if test="${listMember.member.memberManner > 40}">
													<p>${listMember.member.memberManner}℃</p>
													<span style="color: green"><i
														class="fa-solid fa-face-grin fa-2x"></i></span>
												</c:if>
											</div>

										</div>
									</div>
								</ul>

							</div>
							<!--  멤버 테이블 반복 출력  -->
						</c:forEach>



					</div>
					<!-- 여기까지가 아코디언 바디임  -->
				</div>
			</div>
			
			<c:if test="${memberGrade == 'host' || memberGrade == 'admin'}">	
			  <div class="accordion-item">
    <h2 class="accordion-header">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
      클럽 관리 
      </button>
    </h2>
    <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
      <div class="accordion-body">
    
		<div id="menu">
		<ul>
					
					<li><a href="/club/${main.membership.membershipCode}/membershipPromotionDetail" >홍보글 작성</a></li>		
					<li><a href="/write?membershipCode=${main.membership.membershipCode}">모임게시판작성하러가기</a></li>				
					<li><a id="management"  href="/management?membershipCode=${main.membership.membershipCode}">멤버관리페이지	<c:if test="${newMember > 0}">
					
					<p class="ifNew">New</p>
				
					
					 </c:if> </a></li>
						<c:if test="${memberGrade == 'host'}">
						<li><a href="/updateMembership">정보 수정하기</a></li>
						<li><button id="deleteButton">클럽삭제 </button></li>
					</c:if>
				</ul>
		</div>
		
	
      </div>
    </div>
  </div>
  </c:if>


		</div>


		<div id="container">
		<c:if test="${main.membership.membershipImg != null}">
			<img id="mainImg"
				src="http://192.168.10.51:8081/membership/${main.membership.membershipCode}/${main.membership.membershipImg}">
			</c:if>	
			<c:if test="${main.membership.membershipImg == null}">
			<img id="mainImg"
				src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
			</c:if>	
				
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
					<h2>${main.member.nickname} </h2>
				</div>
				<div id="membershipTitle">
					<h1>${main.membership.membershipName }</h1>
				</div>
				<div id="userCount">
					<i class="fa-solid fa-user-group"></i>
					${main.count}/${main.membership.membershipMax}
				</div>
			</div>
			
			
			
			
		<div id="calendar-info">
		 
		 <p>일정 한눈에 보기</p>
		<div id="calendar"></div>
		
		</div>
			
		

		
			<div class="alink-container"><a class="main-link" href="/${main.membership.membershipCode}">우리 클럽 홍보 게시판</a></div>
		</div>
		
			
		
		

		
		
		
		
		
		
		
	
		
		</div>
		
		<c:set var="textColor">
    <c:choose>
        <c:when test="${main.count > 1}">
            red
        </c:when>
        <c:otherwise>
            black
        </c:otherwise>
    </c:choose>
</c:set>
		
			<div id="deleteMembership"  style="display: none">
			<div id="deleteContainer">
			<div id="container-title"><span id="title">클럽 삭제 창</span></div> <div id="deleteCancle"><button id="cancle"><i class="fa-solid fa-x"></i></button></div>
			</div>
			<div id="container-main">
			<div id="delete-text"><span style="color : ${textColor}" >클럽원이 본인만 남아있는 클럽만</span> 삭제할 수 있으며 해당 클럽에 대한 모든 데이터는 삭제 처리 됩니다 그래도 삭제하시겠습니까?</div>
				  
					<div id="passwordCheck">비밀번호 확인 : <input type="password" name="pwdCheck" id="pwdCheck"> </div>
						<div id="container-button">	
						<c:if test="${main.count == 1 }">
						<button id="deleteBtn"class="btn" onclick="allDeleteMembership()">클럽 삭제</button> 
						</c:if>
						<c:if test="${main.count > 1 }">
						<button id="deleteBtn"class="btn" >삭제 불가</button>
						</c:if>
						
						 </div>
							
							</div>
							</div>
	<jsp:include page="../chatting/chattingIndex.jsp" />
	<jsp:include page="../footer/footer.jsp" />
	
	
	<script>
	function allDeleteMembership() {
		let pwdCheck = $("#pwdCheck").val();
		if (confirm("클럽원이 본인만 남아있는 클럽만 삭제할 수 있으며 해당 클럽에 대한 모든 데이터는 삭제 처리 됩니다 그래도 삭제하시겠습니까?")) {
			$.ajax({
						url: '/allDeleteMembership',
						type: 'POST',
						data: {
							pwdCheck: pwdCheck
						},
						success: function(bo) {
							if(bo){
							alert("클럽 삭제 완료");
							location.href="/";
							}else {
								alert("클럽 삭제 실패");
								location.reload();
							}
							
						},
						error : function(){
							alert("클럽 삭제 실패")
							location.reload();
						}
			}
			)}
	};
	</script>
	
 <script>
    const allDates = [];
   
    let allMeet = {};
    const endDate = [];
    <c:forEach items="${allmeet}" var="item" varStatus="status">
    const a${status.index} = new Date("${item.meetDateEnd}")
   a${status.index}.setDate(a${status.index}.getDate()+1)
    a${status.index}1 = a${status.index}.toISOString().split('T')[0];
   endDate.push(a${status.index}1);
    </c:forEach>

    <c:forEach items="${allmeet}" var="item" varStatus="status">
    
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
       <script src="${pageContext.request.contextPath}/js/membershipPage.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
		crossorigin="anonymous"></script>
		
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


</body>
</html>