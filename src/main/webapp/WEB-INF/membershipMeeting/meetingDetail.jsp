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

	<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js"></script>  
    
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/meetingDetail.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>

</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>

<div id="box">
<div class="accordion" id="accordionExample">
				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed " type="button"
							data-bs-toggle="collapse" data-bs-target="#collapseOne"
							aria-expanded="false" aria-controls="collapseOne" >
							참여하는 멤버들</button>
					</h2>
					<div id="collapseOne" class="accordion-collapse collapse "
						data-bs-parent="#accordionExample">
						<div class="accordion-body">
						
					    <c:forEach items="${agree}" var="list">
					    
            <div class="memberTable">
         
              
                        <ul> 
                         <c:if test="${list.listGrade == 'host'}">
                            <li class="member-grade"><span><i class="fa-solid fa-crown"></i></span></li>
                            </c:if>
                            <c:if test="${list.listGrade == 'regular' }">
                         <li class="member-grade">   일반회원 </li>
                            </c:if>
                            <c:if test="${list.listGrade == 'admin' }">
                           <li class="member-grade">   관리자 </li>
                            </c:if>
                           
                            <div class="member-img-info-hobby-location">
                            <div class="member-img">
                            <c:if test="${list.member.memberImg != null}">
                            <li><img class="allmemberImg" src="http://192.168.10.51:8081/member/${list.member.id}/${list.member.memberImg}" alt="회원 이미지"></li>
                            </c:if>
                            <c:if test="${list.member.memberImg == null}">
                            <img class="allmemberImg" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg" alt="회원 이미지">
                            </c:if>
                            </div>
                            <div class="member-info-hobby-location">
                            <div class="member-info">
                            ${list.member.memberInfo}
                            </div>
                            <div class="member-hobby-location">
                            ${list.member.memberHobby} / ${list.member.memberLocation}
                            </div>
                            </div>
                            </div>
                            <div class="nickname-age-fm-manner">
                            <div class="nickname-age-fm">
                            <div class="nickname">
                            ${list.member.nickname}
                            </div>
                         
                            <div class="age-fm">
                            
                            <div class="member-age">
                            ${list.member.age}
                            </div>
                            <div class="member-fm">
                     <c:if test="${list.member.gender eq 'M'.charAt(0)}">
                       <span id="man"> <i class="fa-solid fa-person"></i></span>
                          </c:if>
                         
                          <c:if test="${list.member.gender eq 'F'.charAt(0)}">
                     <span id="femail">   <i class="fa-solid fa-person-dress"></i></span>
                          </c:if>
                          </div>
                             </div>
                             </div>
                           <div class="manner">
                           <c:if test="${list.member.memberManner < 36.5}">
                           <p> ${list.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-meh fa-2x"></i></span> 
                           </c:if>
                           <c:if test="${list.member.memberManner == 36.5}">
                           <p> ${list.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-smile fa-2x"></i></span> 
                           </c:if>
                           <c:if test="${list.member.memberManner > 36.5}">
                           <p> ${list.member.memberManner}℃</p> <span style="color:rgb(252, 177, 3)" ><i class="fa-solid fa-face-grin fa-2x"></i></span> 
                           </c:if>
                         
                        </div>
                          </div>
                        
                        </ul>
                       
             
            </div>
        </c:forEach>
					
					
					
						</div>
					</div>
				</div>
			
			</div>





















<div id="box-container">
	<div id="container">

	<img id="membershipMain-img"
src="http://192.168.10.51:8081/membership/${allInfo.get(0).membership.membershipCode}/${allInfo.get(0).membership.membershipImg}">

		<div id="container-top">
			<div id="hostImg">
				<c:choose>
					<c:when test="${writer.memberImg != null}">
						<img class="host-img"
							src="http://192.168.10.51:8081/member/${main.member.id}/${main.member.memberImg}">
					</c:when>

					<c:otherwise>
						<img class="host-img"
							src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
					</c:otherwise>
				</c:choose>
			</div>
			<div id="hostName">
				<h2>${writer.nickname}</h2>
			</div>
			<div id="membershipTitle">
				<h1>${meet.meetTitle }</h1>
			</div>
			
		</div>
		<div id="info-container">${allInfo.get(0).membership.membershipInfo }</div>
		<div id="meetInfo" >${meet.meetInfo}</div>
		
		
		<c:forEach items="${list}" var="list" >

	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />
<c:if test="${member.nickname == list.member.nickname}">
<form id="go">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="hidden" name="id" value="${member.id}">
<input type="hidden" name="meetAgreeYn" value="${list.meetAgreeYn}"> 

<c:choose>
    <c:when test="${list.meetAgreeYn == false}">
        <input class="agree" type="submit" value="참가">
    </c:when>
    <c:otherwise>
        <input class="agree" type="submit" value="참가취소">
    </c:otherwise>
</c:choose>
</form>
</c:if>







</sec:authorize>

</c:forEach>



<c:choose>
     <c:when test="${ writer.id == member.id }">
     
     <form action="/meetingDelete">
     
     <input type="hidden" name="meetCode" value="${meet.meetCode}">
     <button type="submit">삭제</button>
  </form>

<form action="/meetingUpdate">
  <input type="hidden" name="meetCode" value="${meet.meetCode}">
  <button type="submit">수정</button> 
  </form>
 </c:when>
 
 <c:when test="${member.id == allInfo.get(0).member.id }">
 
   <form action="/meetingDelete">
     
     <input type="hidden" name="meetCode" value="${meet.meetCode}">
     <button type="submit">삭제</button>
  </form>
 </c:when>
</c:choose>


		</div>

		








	
<div id="comment-container">
		<sec:authorize access="isAuthenticated()">
			<form id="comment-frm">
				<div id="comment-box">
					<div class="prof">
						<label for="textbox"> ${member.nickname}</label>
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
					<input id="textbox" type="text" name="meetCommentText"
						placeholder="댓글을 입력하세요"  > <input type="hidden" name="id"
						value="${member.id}"> <input type="hidden"
						name="meetCode" value="${meet.meetCode}">
					<button id="submit-comment" type="button">댓글 등록</button>
				</div>
			</form>
		</sec:authorize>
		<c:if test="${fn:length(comment) == 0}">
			<p>현재 등록된 댓글이 없습니다.</p>
		</c:if>
		<c:if test="${fn:length(comment) != 0}">
			<c:forEach items="${comment}" var="com">
				<div id="comm-${com.meetCommentCode}" class="comment">
					<c:if test="${com.meetCommentText == null}">
						<p>삭제된 댓글입니다.</p>
					</c:if>
					<c:if test="${com.meetCommentText != null}">
						<div class="comment-head">
						
						<a href="/userInfo/${com.nickname}">
						
							<div class="prof" >${com.nickname}
							
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
							</div></a>${com.meetCommentDate}</div>
						<div class="comment-text">${com.meetCommentText}</div>
						<c:if
							test="${com.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
							<button type="button"
								onclick="deleteComment(event, ${com.meetCommentCode})">삭제</button>
						</c:if>
						<c:if test="${com.nickname == member.nickname}">
							<button type="button" class="btn"
								onclick="updateForm(${com.meetCommentCode})">수정</button>
							<div id="update-form-${com.meetCommentCode}" class="update-form">
								<form id="comment-frm-${com.meetCommentCode}">
									<div id="comment-box-update-${com.meetCommentCode}">
										<div class="prof">
											<label for="textbox-update-${com.meetCommentCode}">
												${member.nickname}</label>
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
										<input id="textbox-update-${com.meetCommentCode}" type="text"
											name="meetCommentText" value="${com.meetCommentText}" onclick="updateKey(${com.meetCommentCode})">
										<button type="button"
											onclick="updateComment(event,${com.meetCommentCode})">댓글
											수정</button>
									</div>
								</form>
							</div>
						</c:if>
					</c:if>
					<button type="button" class="btn"
						onclick="showReplyForm(${com.meetCommentCode})">
						답글
						<c:if test="${fn:length(com.recoment) != 0}">(${fn:length(com.recoment)})</c:if>
					</button>
					<div class="recomment-box"
						id="recomment-box-${com.meetCommentCode}">
						<c:if test="${fn:length(com.recoment) != 0}">
							<c:forEach items="${com.recoment}" var="recom">
								<div id="comm-${recom.meetCommentCode}" class="re-comment">
									<div class="comment-head">
										<a href="/userInfo/${recom.nickname}">
										<div class="prof">${recom.nickname}
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
										</div></a>${recom.meetCommentDate}</div>
									<br />
									<div class="comment-text">${recom.meetCommentText}</div>
									<c:if
										test="${recom.nickname == member.nickname || memberGrade == 'host' || memberGrade == 'admin'}">
										<button type="button"
											onclick="deleteComment(event, ${recom.meetCommentCode})">삭제</button>
									</c:if>
									<c:if test="${recom.nickname == member.nickname}">
										<button type="button" class="btn"
											onclick="updateForm(${recom.meetCommentCode})">수정</button>
										<div id="update-form-${recom.meetCommentCode}"
											class="update-form">
											<form id="comment-frm-${recom.meetCommentCode}">
												<div id="comment-box-update-${recom.meetCommentCode}">
													<label for="textbox-update-${recom.meetCommentCode}">
														${member.nickname} : </label> <input
														id="textbox-update-${recom.meetCommentCode}" type="text"
														name="mainCommentText" value="${recom.meetCommentText}"
														onclick="updateKey(${recom.meetCommentCode})">
													<button type="button"
														onclick="updateComment(event,${recom.meetCommentCode})">댓글
														수정</button>
												</div>
											</form>
										</div>
									</c:if>
								</div>
							</c:forEach>
						</c:if>
						<div id="reply-form-${com.meetCommentCode}" class="reply-form">
							<sec:authorize access="isAuthenticated()">
								<form id="comment-frm-${com.meetCommentCode}">
									<div id="comment-box-${com.meetCommentCode}">

										<div class="prof">
											<label for="textbox${com.meetCommentCode}">
												${member.nickname}</label>
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
										<input id="textbox${com.meetCommentCode}" type="text"
											name="meetCommentText" placeholder="대댓글을 입력하세요" onclick="reCommentKey(${com.meetCommentCode})"> 
											<input type="hidden" name="id" value="${member.id}"> 
											<input type="hidden" name="meetCode" value="${com.meetCode}">
										<button type="button"
											onclick="recomment(event, ${com.meetCommentCode})">댓글
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
	
	</div>
	
	
	<div id="calendar"    ></div>
	
	</div>
	
	<jsp:include page="../footer/footer.jsp" />

	
	<script src="${pageContext.request.contextPath}/js/meetingDetail.js"></script>
		<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
		crossorigin="anonymous"></script>
		
		 <script>
  
  
  
        $(document).ready(function() {
            $('#go').on('submit', function(event) {
                event.preventDefault(); // 폼의 기본 제출을 막습니다.
                
                $.ajax({
                    url: '/go', // 요청을 보낼 URL
                    type: 'POST',
                    data: $(this).serialize(), // 폼의 데이터를 직렬화합니다.
                    success: function(response) {
                        alert($(".agree").val()+" 완료되었습니다 !!");
                         location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert('오류가 발생했습니다.');
                        console.error(xhr.responseText);
                    }
                });
            });
        });
    </script>
    
    
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
</body>
</html>