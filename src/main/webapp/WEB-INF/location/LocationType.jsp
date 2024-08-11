<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/locationType.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="locationTypeBody">
	<!-- 위치 버튼 -->
      <button class="Locationbutton">지역</button>
		<div class="locationType">
			<div class="LocationTypeBox">
			<!-- 위치 리스트 -->
			<c:forEach items="${allLocationLarge}" var="location">
				<div class="allLocationBox">
					<div class="allLocation">${location.locLaName}</div>
				</div>
			</c:forEach>
			</div>
		</div>
		
		
		
	<div class="locationTable">
      <div class="type">  
		<!-- 타입 리스트 -->
		<c:forEach items="${allType}" var="type">
		<div class="typebox">
			<div class="typeCategory">${type.typeLaName}</div>
		</div>
		</c:forEach>
      </div>
    </div>
    
   <div class="typeTableChild">
    	<div class="typeSmallCategory"></div>
 	</div>
</div>	

<!-- 이 부분은 나중에 조인해서 가져오면 됨 -->
<div class="ClubTableBody">
	<c:forEach items="${allLocation}" var="location">
		<div class="ClubTable">
			<div>${location.locLaName}</div>
			<div>${location.locSName}</div>
			<div>여기는 큰 타입</div>
			<div>여기는 큰 작은타입</div>
		</div>
	</c:forEach>
</div>
<script>
	$(function(){
		$(document).ready(function() {
			// 지역 버튼
		    $(".Locationbutton").click(function() {
		        $(".allLocationBox").toggle(); 
		    });
		    
		    // 중앙 박스
		    $(".typebox").click(function() {
		        let typeName = $(this).find(".typeCategory").text();
		        console.log(typeName)
		        $.ajax({	
		        	type:"get",
		        	url:"/locationtype",
		        	data: "type=" + typeName,
		        	success : function(result){
		        		$(".typeSmallCategory").empty();
		        		result.forEach(function(item) {	
		        		    $(".typeSmallCategory").append("<div>" + item + "</div>");
		        		});
		        	}
		        })
		    });
		    
		    /* 눌렀을때 이벤트
		    $(".allLocation").click(function() {
		        var locationName = $(this).data("location");

		        $.ajax({
		            url: "/location/" + locationName,
		            method: "GET",
		            success: function(data) {
		                var clubTableBody = $(".ClubTableBody");
		                clubTableBody.empty(); // 기존 내용을 지웁니다.

		                $.each(data, function(index, location) {
		                    var clubTableHtml = `
		                        <div class="ClubTable">
		                            <div>${location.locLaName}</div>
		                            <div>${location.locSName}</div>
		                            <div>여기는 큰 타입</div>
		                            <div>여기는 큰 작은타입</div>
		                        </div>
		                    `;
		                    clubTableBody.append(clubTableHtml);
		                });
		            },
		        });
		    });
		    
		    */
		    
		    
		});
	});
</script>
</body>
</html>