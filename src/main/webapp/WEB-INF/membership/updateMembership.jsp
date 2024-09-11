<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>클럽 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/updateMembership.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
.image{
background: white;
}

</style>

</head>
<body>
	<jsp:include page="/WEB-INF/header/header.jsp" />

	<input id="hiddenImg" type="hidden"
		value="http://192.168.10.51:8081/membership/${membership.membershipCode}/${membership.membershipImg}">
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
	</sec:authorize>
	<div class="container">
		<form enctype="multipart/form-data">
			<div id="div1">
				<div class="bar" id="bar1">
				<input type="hidden" id="membershipCode" name="membershipCode" value="${membership.membershipCode}" >
					<label for="membershipName"> 다모임 클럽명 <span class="name"
						id="name"></span></label> <input type="text" id="membershipName"
						name="membershipName" maxlength="50"
						value="${membership.membershipName}">
				</div>
				 
				<div class="bar" id="bar3">
					가입조건 <input type="text" id="membershipAccessionText"
						name="membershipAccessionText"
						value="${membership.membershipAccessionText}">
				</div>
				<div id="textmax">
					<div class="bar" id="bar5">
						다모임 최대인원 <span>현재 인원 : </span><span id="count">${count}</span> <span class="max" id="max"></span>
						<input type="number"
							for="membershipMax" id="membershipMax" name="membershipMax"
							value="${membership.membershipMax}">
					</div>
					<div class="bar" id="bar4">
						한줄소개 <input type="text" id="membershipSimpleText"
							name="membershipSimpleText"
							value="${membership.membershipSimpleText}">
					</div>
				</div>
			</div>
			<div class="bar" id="bar2">
			사진첨부: <input type="file" name="file" id="file" accept="image/*" onchange="imgShow(event)"><br>
				<button type="button" id="deleteImg" onclick="asd()">기본 이미지로 바꾸기 </button>
				
				</div>
				
			<div id="memberImg">
				<div id="image_container">
								<img id="default" alt="" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg"  >
									<c:if test="${membership.membershipImg != null}">
									<img 
										src="http://192.168.10.51:8081/membership/${membership.membershipCode}/${membership.membershipImg}"
										alt="Profile Image">
										</c:if>
										<c:if test="${membership.membershipImg == null}">
									<img 
										src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg"
										alt="Profile Image">
										</c:if>
										
								</div>
			</div>

			<div id="locLaText">지역을 선택해주세요</div>
			
			<div class="locLabox">
				<c:forEach items="${locLaNameList}" var="locLN">
					<input type="checkbox" value="${locLN}" id="${locLN}"
						name="locationLaName">
					<label for="${locLN}" class="locLNCss">${locLN}</label>
				</c:forEach>
			</div>
			<div class="locSbox" style="display: none;"></div>
			<div id="typeLaText">유형을 선택해주세요</div>
			<div class="typeLabox">
				<c:forEach items="${typeLaNameList}" var="typeLN">
					<input type="checkbox" value="${typeLN}" id="${typeLN}"
						name="typeLaName">
					<label for="${typeLN}" class="typeLNCss">${typeLN}</label>
				</c:forEach>
			</div>
			<div class="typeSbox" style="display: none;"></div>
			<!-- locationSList typeSName -->
			<!-- 도시별 지역별 태그 선택 ============================================================== -->

			<!--  -->
			<button class="insert" type="button" id="updateClub">클럽수정</button>
			<a href="/" class="cancel" id="toIndex">생성취소</a>

		</form>

	</div>
	<jsp:include page="../footer/footer.jsp" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

	<script src="https://kit.fontawesome.com/a076d05399.js"></script>


</body>

<script src="${pageContext.request.contextPath}/js/updateMembership.js"></script>

<script src="https://kit.fontawesome.com/a076d05399.js"></script>


<script>
const locLaName = $(`.locLabox input[type=checkbox][value="${locButton[0].locLaName}"]`)[0];
$(locLaName).prop("checked", true);
let locS = "";
$.ajax({
	url: '/locationSList',
	method: 'GET',
	data: { laName: "${locButton[0].locLaName}" },
	success: function(re) {
		$(".locSbox").html();
		for (const i of re) {
			locS += "<input type='checkbox' value='" + i + "' id='"+ i + "' name='locLN' ";
			<c:forEach items="${locButton}" var="item">
				if("${item.locSName}" === i) {
					locS += "checked";
				}
			</c:forEach>
			locS += "> <label for='" + i +"' class='locLNCss'>" + i + "</label>";
		}
		$(".locSbox").show().html(locS);
	},
})

const typeLaName = $(`.typeLabox input[type=checkbox][value="${typeButton[0].typeLaName}"]`)[0];
$(typeLaName).prop("checked", true);
let typeS = "";
$.ajax({
	url: '/typeSName',
	method: 'GET',
	data: { typeLaName: "${typeButton[0].typeLaName}" },
	success: function(re) {
		$(".typeSbox").html();
		console.log(re);
		for (const i of re) {
			typeS += "<input type='checkbox' value='" + i + "' id='"+ i + "' name='typeLN' ";
			<c:forEach items="${typeButton}" var="item">
				if("${item.typeSName}" === i) {
					typeS += "checked";
				}
			</c:forEach>
			typeS += "> <label for='" + i +"' class='typeLNCss'>" + i + "</label>";
		}
		$(".typeSbox").show().html(typeS);
	},
})



</script>
</body>
</html>
