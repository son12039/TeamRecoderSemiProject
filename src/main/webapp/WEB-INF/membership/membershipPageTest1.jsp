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
 	
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		

	
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
				<div id="userCount">
					<i class="fa-solid fa-user-group"></i>
					${main.count}/${main.membership.membershipMax}
				</div>
			</div>
			
			<div id="option">
			
			
			 <div id="calendar" style= "width: 60%"     ></div>
			<div id="links">
				<div class="dropdown">
				<a class="btn btn-secondary dropdown-toggle" href="#" role="button"
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
			<div class="accordion" id="accordionExample">
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button " type="button"
							data-bs-toggle="collapse" data-bs-target="#collapseOne"
							aria-expanded="true" aria-controls="collapseOne">
							함께하는 멤버들</button>
					</h2>
					<div id="collapseOne" class="accordion-collapse collapse "
						data-bs-parent="#accordionExample">
						<div class="accordion-body">
						
					    <c:forEach items="${allMember}" var="listMember">
            <div class="memberTable">
         
                 <c:choose> 
             
                    <c:when test="${listMember.listGrade == 'guest'}">
                        <ul> 
                       
                            <li>${listMember.member.nickname} - 가입 대기중</li>
                            
                           
                            <c:if test="${listMember.member.memberImg != null}">
                            <li><img class="allmemberImg" src="http://192.168.10.51:8081/member/${cMember.member.id}/${cMember.member.memberImg}" alt="회원 이미지"></li>
                            </c:if>
                            <c:if test="${listMember.member.memberImg == null}">
                            <img class="allmemberImg" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg" alt="회원 이미지">
                             </c:if>                          
                              <%-- 현제 호스트만 수락버튼 보이게 해둬서 조건 이런데 나중에 바꿔야함 --%>
                            <c:if test="${main.member.id == member.id && !(membershipUserCount >= main.membership.membershipMax)}">
                        	
                                <form id="agreefrm${listMember.listCode}">
                                    <input type="hidden" name="id" value="${listMember.member.id}">
                                    <input type="hidden" name="listGrade" value="regular">
                                    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
                                    <button type="button" class="agreeMember"   value="${listMember.listCode}">가입 승인</button>
                                   
                                    
                                </form>
                            </c:if>
                             
                           
                            
                        </ul>
                    </c:when>
                      
                    <c:otherwise>
                        <ul> 
                        <c:if test="${listMember.listGrade == 'host'}">
                            <li class="member-grade"><span><i class="fa-solid fa-crown"></i></span></li>
                            </c:if>
                            <c:if test="${listMember.listGrade == 'regular' }">
                         <li class="member-grade">   일반회원 </li>
                            </c:if>
                            <c:if test="${listMember.listGrade == 'admin' }">
                           <li class="member-grade">   관리자 </li>
                            </c:if>
                            <div class="member-img-info-hobby-location">
                            <div class="member-img">
                            <c:if test="${listMember.member.memberImg != null}">
                            <li><img class="allmemberImg" src="http://192.168.10.51:8081/member/${cMember.member.id}/${cMember.member.memberImg}" alt="회원 이미지"></li>
                            </c:if>
                            <c:if test="${listMember.member.memberImg == null}">
                            <img class="allmemberImg" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg" alt="회원 이미지">
                            </c:if>
                            </div>
                            <div class="member-info-hobby-location">
                            <div class="member-info">
                            ${listMember.member.memberInfo}
                            </div>
                            <div class="member-hobby-location">
                            ${listMember.member.memberHobby} / ${listMember.member.memberLocation}
                            </div>
                            </div>
                            </div>
                            <div class="nickname-age-fm-manner">
                            <div class="nickname-age-fm">
                            <div class="nickname">
                            ${listMember.member.nickname}
                            </div>
                         
                            <div class="age-fm">
                            
                            <div class="member-age">
                            ${listMember.member.age}
                            </div>
                            <div class="member-fm">
                     <c:if test="${listMember.member.gender eq 'M'.charAt(0)}">
                       <span id="man"> <i class="fa-solid fa-person"></i></span>
                          </c:if>
                         
                          <c:if test="${listMember.member.gender eq 'F'.charAt(0)}">
                     <span id="femail">   <i class="fa-solid fa-person-dress"></i></span>
                          </c:if>
                          </div>
                             </div>
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
                        </ul>
                        
                    </c:otherwise>
                </c:choose>
                 
            </div>
        </c:forEach>
					
					
					
						</div>
					</div>
				</div>
			
			</div>

		


 
		</div>
		
	
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