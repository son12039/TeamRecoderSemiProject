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
<body>
 <jsp:include page="../header/header.jsp" />
 

		<sec:authentication property="principal" var="member" />
	<div id="main-container">
	
		<div class="accordion" id="accordionExample">
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed " type="button"
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
							<c:if test="${listMember.member.memberManner < 30}">
								<p>${listMember.member.memberManner}℃</p>
								<span style="color: red"><i
									class="fa-solid fa-face-angry fa-2x"></i></span>
							</c:if>
							<c:if test="${listMember.member.memberManner >= 30 && listMember.member.memberManner <= 40}">
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
			
			
			
			
		<div id="calendar-info">
		
		 <p>일정 한눈에 보기</p>
		<div id="calendar"></div>
		</div>
			
		

		
		
		</div>
		

		
		
		
		<div id="menu">
		<ul>
		<li class="mainMenu">MENU</li>
		                <c:set var="memberGrade" value="none" />
			<c:forEach items="${member.memberListDTO}" var="loginMember">
				<c:if
					test="${loginMember.membershipCode == main.membership.membershipCode}">
					<c:set var="memberGrade" value="${loginMember.listGrade}" />

				</c:if>
			</c:forEach>
<c:if test="${memberGrade == 'host' || memberGrade == 'admin'}">
					<li><a
						href="/club/${main.membership.membershipCode}/membershipPromotionDetail"
						>홍보글 작성</a></li>
					<li><a href="/updateMembership">정보 수정하기</a></li>
					<li><a href="/write?membershipCode=${main.membership.membershipCode}">모임게시판작성하러가기</a></li>				
					<li><a id="management"  class="dropdown-item" href="/management?membershipCode=${main.membership.membershipCode}"   > 멤버관리페이지 </a></li>
					</c:if>
					<li><a
						href="/chatserver?membershipCode=${main.membership.membershipCode}" >채팅서버가기</a></li>
				
				</ul>
		</div>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		</div>
	
	<jsp:include page="../footer/footer.jsp" />
	
	
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
	
	
	
	
	
	
	
	
	</script>	


</body>
</html>