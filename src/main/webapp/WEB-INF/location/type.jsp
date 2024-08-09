<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/type.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="locationTable">
      <div class="type">
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
 	<script>
	$(document).ready(function() {
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
	});
</script>
 	
</body>
</html>