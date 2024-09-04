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
<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/memberDelete.css" />
</head>
<body>
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<jsp:include page="../header/header.jsp" />
		<div class="box">
			<div class="container">
				<h1>탈퇴 안내</h1>
				<p>회원탈퇴를 신청하기 전에 안내 사항을 꼭 확인해주세요.</p>
				<section class="warning">
					<div>
						<div class="warning_text">
							<i class="fa-solid fa-circle-exclamation"></i>
							<h2>중요 사항</h2>
						</div>
						<ul>
							<li><p>사용하고 계신 아이디 <strong>( ${member.id} ) </strong>은/는 탈퇴할 경우
								재사용 및 복구가 불가능합니다.</p>
							</li>
							<li><p>탈퇴한 아이디는 본인과 타인 모두 재사용 및 복구가 불가하오니 신중하게 선택하시기 바랍니다.</p></li>
							<li><p>탈퇴 후 회원정보 및 서비스 이용기록은 모두 삭제됩니다.</p></li>
						</ul>
					</div>
				</section>

				<section class="details">
					<div class="details_text">
						<div class="warning_text">
							<i class="fa-solid fa-circle-exclamation"></i>
							<h2>삭제되는 내용</h2>
						</div>
						<div class="details_info">
							<ul class="details_ul">
								<li><strong>계정 : </strong><span>계정 삭제</span></li>
								<li><strong>클럽 : </strong><span>클럽 및 게시물 삭제</span></li>
								<li><strong>개인정보 : </strong><span>저장된 주소및 연락처 삭제</span></li>
								<li><strong>나의 정보 : </strong><span>저장된 활동 기록</span></li>
							</ul>
						</div>
					</div>
				</section>
				<section class="post-retention">
					<div>
						<div class="warning_text">
							<i class="fa-solid fa-circle-exclamation"></i>
							<h2>주의사항</h2>
						</div>
						<p>
							탈퇴 후에도 게시판 서비스에 등록한 게시물은 그대로 남아 있습니다. <strong>가입한 클럽에 올린
								게시글 및 댓글은 회원탈퇴 시 비공개 처리 됩니다.</strong> 삭제를 원하는 게시글이 있다면 반드시 <strong>탈퇴
								전 비공개 처리하거나 삭제하시기 바랍니다.</strong> 탈퇴 후에는 회원정보가 삭제되어 본인 여부를 확인할 수 있는 방법이
							없어, 게시글을 임의로 삭제해드릴 수 없습니다.
						</p>
						<c:if test="${list != null}">
								<div class="warning_text">
									<div class="warning_img">
										<i class="fa-solid fa-crown"></i> <a
											href="/club/${list.membershipCode}"> <img
											class="membership-img"
											src="http://192.168.10.51:8081/membership/
									${list.membershipCode}/${list.membershipImg}" />
										</a>
									</div>
									<div class="warning_info">
										<h3>
											회원님은 <strong>${list.membershipName}</strong> 의
											호스트입니다
										</h3>
									</div>
								</div>
							
						</c:if>
						<div class="warning_text">
							<p>탈퇴 후에는 <strong>해당 아이디 로 다시 가입할 수 없으며 아이디와 데이터는 복구할 수
							없습니다.</strong> 게시판 서비스에 남아 있는 게시글은 탈퇴 후 삭제할 수 없습니다.
							</p>
						</div>
					</div>
				</section>
				<section class="confirmation">
					<div>
						<form action="/memberStatus" method="post" id="form">
							<label for="checkbox">
								<p>
									안내 사항을 모두 확인하였으며, 이에 동의합니다. <input type="checkbox"
										id="checkbox" name="checkbox">
								</p>
							</label>
							<div>

								<input type="button" id="button" value="회원탈퇴" />
							</div>
						</form>
					</div>
				</section>
			</div>
		</div>
		<jsp:include page="../footer/footer.jsp" />
	</sec:authorize>
</body>
<script>
		$("#button").click(() => {
			// 체크박스가 체크될시 리턴 
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
</html>