<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Damoim 페이지!!</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/locationType.css" />
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>

<body>

	<!-- 인덱스 헤드만 헤더1 -->
	<jsp:include page="header/headerIndex.jsp" />
	<div class="body_img_box_body">
		<div class="body_img_box">
			<img class="body_img" src="http://192.168.10.51:8081/mainImg2.jpg" />
			<div class=body_text>
				<p class="text_event1">다모임</p>
				<p class="text_event2">우리들의 만남은 지금부터 시작입니다.</p>
				<p class="text_event3">사랑하는 사람들과 함께 좋은 추억을 만들어봐요.</p>
				<p class="text_event4">"별이 총총한 밤, 바람이 속삭이는 순간, 우리의 눈이 마주치고 두 손이
					살짝 맞닿을 때, 마치 운명처럼 모든 것이 정해진 듯한 따뜻한 감동이 퍼진다. 그 만남이 만들어내는 작은 기적, 우리만의
					특별한 순간."</p>
			</div>
		</div>
	</div>


	</div>
	<div class= "rank-container" style="display: none;  flex-direction:column; align-items: center;  width:280px; top:91px; left: 15px; position:fixed;">
	
		<div class="rank-head" >	
		<div class="sort">온도 랭킹</div> <button class="select" value="manner-rank"><i class="fa-solid fa-chevron-up fa-2x"></i></button>
		</div>
	<div id="manner-rank" class="rank-box" style="display: flex;"  >
		<button class="toggle-btn"><i class="fa-solid fa-angle-left fa-2x"></i></button>
			<ul class="rank-body" >
				
				<c:forEach items="${mannerRank}" var="rank">
				<ul class="rankInfo" style="display:  flex;">
				<c:if test="${rank.rank == 0}">
					<li><i style="color: gold;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 1}">
					<li><i style="color: silver;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 2}">
					<li><i style="color: #EEB999;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 3}">
					<li>4등</li>
				</c:if>
				<c:if test="${rank.rank == 4}">
					<li>5등</li>
				</c:if>
				
				
			
				
				
				<li class="nickname"><a href="/userInfo/${rank.nickname}">${rank.nickname}</a></li>
					<li class="score">${rank.memberManner}°C</li>
					</ul>
				</c:forEach>
			</ul>
			<button class="toggle-btn"><i class="fa-solid fa-angle-right fa-2x"></i></button>
	</div>
	<div id="meet-rank" class="rank-box" style="display: none; "  >
		<button class="toggle-btn"><i class="fa-solid fa-angle-left fa-2x"></i></button>
		<ul class="rank-body">
		
<c:forEach items="${meetRank}" var="rank">
				<ul class="rankInfo" style="display:  flex;">
				<c:if test="${rank.rank == 0}">
					<li><i style="color: gold;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 1}">
					<li><i style="color: silver;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 2}">
					<li><i style="color: #EEB999;" class="fa-solid fa-medal"></i></li>
				</c:if>
				<c:if test="${rank.rank == 3}">
					<li>4등</li>
				</c:if>
				<c:if test="${rank.rank == 4}">
					<li>5등</li>
				</c:if>
				
				
			
				
				
					<li class="nickname"><a href="/userInfo/${rank.nickname}">${rank.nickname}</a></li>
					<li class="score">${rank.meetCount}회</li>
					</ul>
				</c:forEach>
		</ul>	
		<button class="toggle-btn"><i class="fa-solid fa-angle-right fa-2x"></i></button>
	</div>
	</div>
	<!-- 08-20 채승훈 -->
	<div class="locationTypeBody">
	<!-- 지역별 -->
		<div class="locationTypeBodyBox">
			<form id="locationLaNameForm">
				<div class="locationLaBox">
					<div class="locationLaStar">도시별&nbsp;</div>
						<input type="checkbox" value="" id="locLaNameAll" />
				</div>
				<div class="locationLaDiv">
					<label class="locLaNameLabel" for="locLaNameAll">전체보기</label>
					<c:forEach items="${locLaNameList}" var="locLaName">
						<input type="checkbox" value="${locLaName}" id="${locLaName}"
							name="locationLaName" >
						<label for="${locLaName}" class="locationLaCss">${locLaName}</label>
					</c:forEach>
				</div>
			</form>
	
			<form id="locationSNameForm">
				<div class="locationSBox">
					<div class="locationSStar">지역별&nbsp;</div>
				</div>
					<div class="locationSDiv">
						<c:forEach items="${locSNameList}" var="locSName">
							<input type="checkbox" value="${locSName}" id="${locSName}"
								name="locationSName" onchange="locationSelect(event)">
							<label for="${locSName}" class="locationSCss">${locSName}</label>
						</c:forEach>
					</div>
			</form>
			<!-- 타입별 -->
			<form id="typeLaNameSelect">
				<div class="typeLaBox">
					<div class="typeLaStar">타입별&nbsp;</div>
						<input type="checkbox" value="" id="typeLaNameAll" />
				</div>
				<div class="typeLaDiv">
					<label class="typeLaNameLabel" for="typeLaNameAll">전체보기</label>
					<c:forEach items="${typeLaNameList}" var="typeLaName">
						<input type="checkbox" value="${typeLaName}" id="${typeLaName}"
							name="typeLaName" >
						<label for="${typeLaName}" class="typeLaCss">${typeLaName}</label>
					</c:forEach>
				</div>
			</form>
			

			<form id="typeSNameForm">
				<div class="typeSBox">
					<div class="typeSStar">분류별&nbsp;</div>
				</div>
					<div class="typeSDiv">
					<c:forEach items="${typeSNameList}" var="typeSName">
						<input type="checkbox" value="${typeSName}" id="${typeSName}"
							name="typeSName" onchange="typeSelect(event)">
						<label for="${typeSName}" id="typeSCss">${typeSName}</label>
					</c:forEach>
				</div>
			</form>
			<input type="button" class="resetBth" onclick="reset()" value="초기화">
		</div>
		<div class="locationTypeSearch">
			<input type="text" name="keyword" id="keyword" placeholder="검색.."/>
			<button type="button" onclick="search()">검색</button>
		</div>
	</div>


	<br>
	<div class="membership-list">
	<c:if test="${fn:length(list) == 0}">
			<div class="noMember">해당 정보가 없습니다.. 😥</div>
		</c:if>

	<c:if test="${fn:length(list) != 0}">
		<c:forEach items="${list}" var="info" varStatus="status">

		
			<div class="membership-card">
				
			<c:if test="${info.membershipDate >=  today30 &&  today >= info.membershipDate}">
			<img alt="" src="http://192.168.10.51:8081/sungil/2%ED%8A%B8.png" class="new">
		
				</c:if>
				<div class="membership-img">
			
				
					<a href="/${info.membershipCode}">
					 <c:choose>
							<c:when test="${info.membershipImg != null}">
								<img
									src="http://192.168.10.51:8081/membership/${info.membershipCode}/${info.membershipImg}">
							</c:when>
							<c:otherwise>
								<img
									src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
							</c:otherwise>
						</c:choose>
					</a>
				</div>
				<div class="membership-info">
					<h1 class="membership-name">${info.membershipName}</h1>

					<h2>${info.membershipSimpleText}</h2>
					<a href="/userInfo/${info.nickname}">
						<div class="host">
						<div class="hostImg">
							<span style="color:rgb(252, 177, 3)"><i class="fa-solid fa-crown"></i></span>
							<c:choose>
								<c:when test="${info.memberImg != null}">
									<img class="user-img"
										src="http://192.168.10.51:8081/member/${info.id}/${info.memberImg}">
								</c:when>
								<c:otherwise>
									<img class="user-img"
										src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
								</c:otherwise>
							</c:choose>
						</div>
							<h2>호스트 : ${info.nickname}</h2> 
							<input type="hidden" name="code" value="${info.membershipCode}">
								<div class="memberMannerIndex">
									<c:if test="${info.memberManner < 30}">
										<span style="color: red"><i
											class="fa-solid fa-face-angry fa-2x"></i></span>
										<p>${info.memberManner}℃</p>
									</c:if>
									<c:if test="${info.memberManner >= 30 && info.memberManner <= 40}">
										<span style="color: rgb(252, 177, 3)"><i
											class="fa-solid fa-face-smile fa-2x"></i></span>
										<p>${info.memberManner}℃</p>
									</c:if>
									<c:if test="${info.memberManner > 40}">
										<span style="color: green"><i
											class="fa-solid fa-face-grin fa-2x"></i></span>
										<p>${info.memberManner}℃</p>
									</c:if>
								</div>
						</div>
					</a>
					<h3><i class="fa-solid fa-users"></i> : ${info.count}/${info.membershipMax}</h3>
					<div class="locationTypeBox">
						<div class="location">
							<c:forEach items="${info.locations}" var="location">
								<div class="locationList"># ${location.locLaName}
									${location.locSName}</div>
							</c:forEach>
						</div>
						<br>
						<div class="type">
							<c:forEach items="${info.types}" var="type">
								<div class="typeList">${type.typeSName}</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			
		</c:forEach>
		</c:if>
	</div>
	
	<jsp:include page="chatting/chattingIndex.jsp" />
	<jsp:include page="footer/footerIndex.jsp" />
	
	 


	<!-- <a href="/dummyUpdate">!!!!!!!!!!!주의!!!!!!!!!!!! 기존 유저들 비밀번호 암호화하는거임 건드리지 말것</a> -->
	<!-- <div class="membership-list"> -->
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="${pageContext.request.contextPath}/js/index.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationTypePaging.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
	

</body>
</html>