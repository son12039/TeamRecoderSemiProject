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
<body>
 <jsp:include page="../header/header.jsp" />
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
	<div id="main-container">
	
		<div class="accordion" id="accordionExample">
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button " type="button"
							data-bs-toggle="collapse" data-bs-target="#collapseOne"
							aria-expanded="true" aria-controls="collapseOne" >
							함께하는 멤버들</button>
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
                            <li class="member-grade"><span><i class="fa-solid fa-crown"></i></span></li>
                            </c:if>
                            <c:if test="${listMember.listGrade == 'regular' }">
                         <li class="member-grade">   일반회원 </li>
                            </c:if>
                            <c:if test="${listMember.listGrade == 'admin' }">
                           <li class="member-grade">   관리자 </li>
                            </c:if>
                           </div>
                            <div class="member-img">
                            <c:if test="${listMember.member.memberImg != null}">
                            <li><img class="allmemberImg" src="http://192.168.10.51:8081/member/${cMember.member.id}/${cMember.member.memberImg}" alt="회원 이미지"></li>
                            </c:if>
                            <c:if test="${listMember.member.memberImg == null}">
                            <img class="allmemberImg" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg" alt="회원 이미지">
                            </c:if>
                            </div>
                            </div>
                            <div class="nickname-manner">
                            <div class="nickname">
                            ${listMember.member.nickname}
                            </div>
                           <div class="manner">
                           <c:if test="${listMember.member.memberManner < 36.5}">
                           <p> ${listMember.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-meh fa-2x"></i></span>
                           </c:if>
                           <c:if test="${listMember.member.memberManner == 36.5}">
                           <p> ${listMember.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-smile fa-2x"></i></span>
                           </c:if>
                           <c:if test="${listMember.member.memberManner > 36.5}">
                           <p> ${listMember.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-grin fa-2x"></i></span>
                           </c:if>
                        </div>
                          </div>
                          </div>
                        </ul>
            </div> <!--  멤버 테이블 반복 출력  -->
        </c:forEach>
					
					
					
						</div> <!-- 여기까지가 아코디언 바디임  -->
					</div>
				</div>
			
			</div>

	
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
			
			<div id="option">
			
			
			 <div id="calendar" style= "width: 60%"     ></div>
			<div id="links">
				<div class="dropdown">
				<a class="btn btn-warning dropdown-toggle" href="#" role="button"
					data-bs-toggle="dropdown" aria-expanded="false"> <i class="fa-solid fa-bars"></i> </a>
				<ul class="dropdown-menu">
					<li><a
						href="/club/${main.membership.membershipCode}/membershipPromotionDetail"
						class="dropdown-item">홍보글 작성</a></li>
					<li><a href="/updateMembership" class="dropdown-item">정보 수정하기</a></li>
					<li><a href="/write?membershipCode=${main.membership.membershipCode}" class="dropdown-item">모임게시판작성하러가기</a></li>				
					<li><a id="management"  class="dropdown-item" href="/management?membershipCode=${main.membership.membershipCode}"   > 멤버관리페이지 </a></li>
					
				</ul>
			</div>
		
			<div>
					<a
						href="/chatserver?membershipCode=${main.membership.membershipCode}">채팅서버가기</a>
				</div>
			
			</div>
			
		
  </div>
		
		
		</div>
		
		
		
		
		</div>
	
	<jsp:include page="../footer/footer.jsp" />
	</sec:authorize>
	
 <script>
    const allDates = [];
    let allMeet = {};
    <c:forEach items="${allmeet}" var="item">
    	allMeet.title = "${item.meetTitle}";
    	allMeet.start = "${item.meetDateStart}";
    	allMeet.end = "${item.meetDateEnd}";
    	allMeet.color = "${item.color}";
    	allMeet.meetCode= "${item.meetCode}";
    	allDates.push(allMeet);
    	allMeet = {};
    </c:forEach>
    </script>
      <script src="${pageContext.request.contextPath}/js/calendar.js"></script>
       <script src="${pageContext.request.contextPath}/js/management.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
		crossorigin="anonymous"></script>
		
		
		
<script>
$("#management").click(()=>{
	
	
	
	
 	$.ajax({
		url: "/management",
		type: 'post',
		data:
			 {membershipCode: $("#management").val()},
		success: function() {
			
			alert("?")
			
			}
						
		
	});	
})
</script>
</body>
</html>