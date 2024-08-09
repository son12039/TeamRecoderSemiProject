<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/location.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <div class="locationTable">
	      <div class="location">
			<c:forEach items="${allLocation}" var="Location">
			<div class="loctionbox">
				<div class="locationCategory">${Location.locLaName}</div>
			</div>
			</c:forEach>
	      </div>
    </div>
    <div class="locationTableChild">
    	<div class="locationSamllCategory"></div>
 	</div>
    
    
<script>
$(document).ready(function() {
    $(".loctionbox").click(function() {
        let locationName = $(this).find(".locationCategory").text();
        $.ajax({	
        	type:"get",
        	url:"/locationCategory",
        	data: "location=" + locationName,
        	success : function(result){
        		$(".locationSamllCategory").empty();
        		result.forEach(function(item) {	
        		    $(".locationSamllCategory").append("<div>" + item + "</div>");
        		});
        	}
        })
    });
});
</script>

 
</body>
</html>