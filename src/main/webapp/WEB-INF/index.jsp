<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Damoim 페이지</title>
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index.css"
        />
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
       
      </head>
      <body>
     
        <div class="header">
          <div class="header_left">
          <a href="/"> <div class="LOGO">DAMOIM</div></a> 
            <div class="menu">menu1</div>
            <div class="menu">menu2</div>
            <div class="menu">menu3</div>
            <div class="menu">menu4</div>
            <div class="header_right">
          <c:if test="${empty mem}">
              <div class="header_right_menu">
                <a href="/signUp">회원가입</a>
                <a href="/loginPage">로그인</a>
              </div>
            
              </c:if>
              <c:if test="${not empty mem}">
              <div class="header_right_menu">
                <a href="/mypage">마이페이지</a> 
               
              <a href="/myMembership?id=${mem.id}">나의 모임</a>  
            
                <a href="/logout">로그아웃</a>
              </div>
              </c:if>
            </div>
          </div>
        </div>
     
              
            </div>
          </div>      
          
        </div>
        
        
        <div class="membership-list">
        
       <c:forEach  items="${list}" var="id" varStatus="status" >
      
       <div class="membership-card">
       <div class="membership-img">
       <a href="/${id.membership.membershipCode}">
       <img  src="${id.membership.membershipImg}">    
       </a>
       </div>  
       <div class="membership-info">
       <h1 class="membership-name"> ${id.membership.membershipName}</h1>
       <h2>${id.membership.membershipInfo} </h2>   
       <h2>호스트 : ${id.member.nickname}</h2>
       <input type="hidden" name="code" value="${id.membership.membershipCode}">
       <h3> 인원 현황 ${countList.get(status.index)}/${id.membership.membershipMax}</h3>
      

        <c:choose>
        
        <c:when test="${id.member.memberImg == ''}">
        <img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
        </c:when>
        <c:otherwise>
       <img class="user-img" src="${id.member.memberImg}"> 
       </c:otherwise>
       </c:choose>
          
     
       </div>
       </div>
      
       </c:forEach>
        </div>
      
        
         
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <script src="login.js"></script>
     <!--    <script >
        
        
         $(".btn").click(()=>{
        	
        	   const id = $("#id").val();          // 아이디 입력값 가져오기
        	    const pwd = $("#pwd").val();        // 비밀번호 입력값 가져오기
        	
        	$.ajax({
        		// 요청 
        		type : "post",
        		url : "/loginCheck",
        		   data: {
        	            id: id,
        	            pwd: pwd
        	        },
       		// 응답 
       		    success : function(result){
       		    	
       		    	if(result){
       		    		alert("성공")
       		    	}else{
       		    		alert("실패")
       		    		$(".loginCheck").text("아이디 또는 비밀번호가 틀렸습니다")
       		    	}
       		    
       		    }
        	});
        	
        	
        });
    
        
        
        </script> --> 
      </body>
    </html>

