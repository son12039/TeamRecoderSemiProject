<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>탈퇴 안내</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/memberDelete.css" />
</head>
<body>
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<jsp:include page="/WEB-INF/header/mypageHeader.jsp" />

		<div class="container">
			<h1>탈퇴 안내</h1>
			<p>회원탈퇴를 신청하기 전에 안내 사항을 꼭 확인해주세요.</p>

			<section class="warning">
				<div>
					<h2>중요 사항</h2>
					<ul>
						<li>사용하고 계신 아이디 <strong>( ${member.id} ) </strong>은/는 탈퇴할 경우
							재사용 및 복구가 불가능합니다.
						</li>
						<li>탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니 신중하게 선택하시기 바랍니다.</li>
						<li>탈퇴 후 회원정보 및 서비스 이용기록은 모두 삭제됩니다.</li>
					</ul>
				</div>
			</section>

			<section class="details">
				<div>
					<h2>삭제되는 내용</h2>
					<ul>
						<li><strong>계정:</strong> 계정 삭제</li>
						<li><strong>클럽:</strong> 클럽 및 게시물 삭제</li>
						<li><strong>주소록:</strong> 저장된 주소및 연락처 삭제</li>
						<li><strong>나의 정보:</strong> 저장된 활동 기록</li>
					</ul>
				</div>
			</section>
			<section class="post-retention">
				<div>
					<h2>※ 주의사항 ※</h2>
					<p>
						탈퇴 후에도 게시판 서비스에 등록한 게시물은 그대로 남아 있습니다. <strong>가입한 클럽에 올린
							게시글 및 댓글은 탈퇴 시 자동 삭제되지 않고 그대로 남아 있습니다.</strong> 삭제를 원하는 게시글이 있다면 반드시 <strong>탈퇴
							전 비공개 처리하거나 삭제하시기 바랍니다.</strong> 탈퇴 후에는 회원정보가 삭제되어 본인 여부를 확인할 수 있는 방법이 없어,
						게시글을 임의로 삭제해드릴 수 없습니다.
					</p>
					<h3>
						<strong>(단, 질문자/답변자 아이디가 비공개 처리됨)</strong>
					</h3>
					<c:if test="${fn:length(list) != 0}">
						<c:forEach items="${list}" var="listItems">
							<h2>가입되어있는 클럽명 : (${listItems.membership.membershipName} )</h2>
							<div>
								<a href="/club/${listItems.membership.membershipCode}">
									<img class="membership-img" src="http://192.168.10.51:8081/membership/
									${listItems.membership.membershipCode}/${listItems.membership.membershipImg}"
								alt="Membership Image"/>
								</a>
							</div>
							<h2>회원님의 등급 : ( ${listItems.listGrade} )</h2>
						</c:forEach>
					</c:if>
					<p>
						탈퇴 후에는 <strong>해당 아이디 로 다시 가입할 수 없으며 아이디와 데이터는 복구할 수
							없습니다.</strong> 게시판 서비스에 남아 있는 게시글은 탈퇴 후 삭제할 수 없습니다.
					</p>
				</div>
			</section>
			<section class="confirmation">
				<div>
					<form action="/memberStatus" method="post" id="form">
					<label for="checkbox">
						<p>
							안내 사항을 모두 확인하였으며, 이에 동의합니다. 
							<input type="checkbox" id="checkbox"
								name="checkbox">
						</p>
						</label>
						<div>

							<input type="button" id="button" value="회원탈퇴" />
						</div>
					</form>
				</div>
			</section>
		</div>
	</sec:authorize>
	<script>
		$("#button").click(() => {
			if (!$("#checkbox").is(":checked")) {
                alert("동의란에 체크해주세요");
                return; 
            }
			$.ajax({
				type: "post",
				url: "/memberStatus",
				data: $("#form").serialize(),
				success: function (result){
					if(result){
						// true면 마지막 비밀번호 체크 페이지 이동
						alert("회원탈퇴 완료되었습니다");
						window.location.href = "/";
					} else {
						alert("회원님의 등급이 호스트입니다 계시물을 확인해주세요");
						location.reload();
					}
				},
			});
		});
	</script>
</body>
</html>






