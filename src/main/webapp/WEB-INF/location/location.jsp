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
			<div class="locationCategory">${Location.locLaName}</div>
		</c:forEach>
      </div>
      <div class="locationTableChild">
	       <div class="locationSamllCategory"></div>
	  </div>
    </div>
    
    
<script>
$(document).ready(function() {
    $(".locationCategory").click(function() {
        let locationName = $(this).text();
        $.ajax({	
        	type:"get",
        	url:"/locationCategory",
        	data: "location=" + locationName,
        	
        	success : function(result){
        		console.log(result);
        		$(".locationSamllCategory").text(result);
        	}
        })
    });
});
</script>

<!-- 
<c:forEach items="${LocationSmallList}" var="Location">
    <div>
        <p>Location Name: ${Location.locSName}</p>
    </div>
</c:forEach>


<c:forEach items="${LocationSmallList}" var="Location">
    <div>
        <p>Location Name: ${Location.locSName}</p>
    </div>
</c:forEach>
 -->
 
</body>
</html>