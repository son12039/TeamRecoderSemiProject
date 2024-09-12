<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous"> -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
 	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/management.css" />
</head>



<body>
 <jsp:include page="../header/header.jsp"></jsp:include>
<sec:authentication property="principal" var="member" />
    


  
<div class="box">
<div class="box-container">
 
    <table>
    <thead>
    <tr>
       <th style="background: black">아이디</th>
                    <th>닉네임</th>
                    <th style="background: black">성별</th>
                    <th>나이</th>
                    
                    <th style="background: black">등급</th>    
                    <th class="last">등급설정</th>

    </tr>
    </thead>
    <tbody>
     <!-- 데이터 행을 여기에 추가합니다 -->
                <c:forEach items="${allMember}" var="list" >
                <c:set var="memberGrade" value="none" />
                <c:set var="code" value="${list.membership.membershipCode}"/>
			<c:forEach items="${member.memberListDTO}" var="loginMember">
				<c:if
					test="${loginMember.membershipCode == list.membership.membershipCode}">
					<c:set var="memberGrade" value="${loginMember.listGrade}" />

				</c:if>
			</c:forEach>
                <tr>
                    <td id="${list.member.id}" class="${list.membership.membershipCode}" style="background: beige">${list.member.id}</td>
                   <td><a href="/userInfo/${list.member.nickname}">${list.member.nickname}</a></td>
                    <td class="gender">${list.member.gender }</td>
                    <td>${list.member.age }</td>
                   
                    <c:if test="${list.listGrade == 'host' }">
                    <td class="grade">호스트</td>
                    </c:if>
                   <c:if test="${list.listGrade == 'regular' }">
                    <td class="grade">일반회원</td>
                    </c:if>       
                     <c:if test="${list.listGrade == 'admin' }">
                    <td class="grade">관리자</td>
                    </c:if>
                     <c:if test="${list.listGrade == 'guest' }">
                    <td class="grade">가입대기중</td>
                    </c:if>
                
                    <!--  관리자이면서 , 다른 멤버쉽의 호스트가 아닌경우  -->
                       <c:if test="${list.listGrade == 'admin' && not fn:contains(otherHost, list.member.id)  }">
                       <!--  접속자가 호스트 인경우 -->
                        <c:if test="${memberGrade == 'host'}">
                  <form id="id${list.member.id}" class="fom">            
                    <td class="buttones">
                   
                    <input type="hidden" name="id" value="${list.member.id}">
                    <input type="hidden" name="membershipCode" value="${list.membership.membershipCode}">
                  
                     
                    <button class="btn btn-dark btn-sm" name="listGrade" value="${list.member.id}" data-value="regular">일반회원</button>
                    <button class="btn btn-danger btn-sm" name="listGrade" value="${list.member.id}" data-value="delete">추방</button>
                    
                    <button class="btn btn-warning btn-sm" name="listGrade" value="${list.member.id}" data-value="host">호스트</button>
                    </td>
                    </form>   
                    </c:if>
                    <!-- 접속자가 관리자인경우  -->
                    <c:if test="${memberGrade == 'admin'}">
            
                    <td>관리자</td>
          
                    </c:if>
                    
                       
                    </c:if>
                    
                    <!-- 관리자 이면서 다른 멤버쉽의 호스트인 경우  -->
                       <c:if test="${list.listGrade == 'admin' &&  fn:contains(otherHost, list.member.id)  }">
                       <!--  접속자가 호스트인 경우  -->
                       
                       <c:if test="${memberGrade == 'host'}">
                       <td class="buttones">
                    <form id="id${list.member.id}" class="fom">            
                    
                   
                    <input type="hidden" name="id" value="${list.member.id}">
                    <input type="hidden" name="membershipCode" value="${list.membership.membershipCode}">
                  
                    
                    <button class="btn btn-dark btn-sm" name="listGrade" value="${list.member.id}" data-value="regular">일반회원</button>
                    <button class="btn btn-danger btn-sm" name="listGrade" value="${list.member.id}" data-value="delete">추방</button>
                    
                    
                    </form> 
                    </td>
                    </c:if> 
                    
                     <!--  접속자가 관리자인 경우  -->
                     <c:if test="${memberGrade == 'admin'}">
                     
                      <td>관리자</td>
                    
                    
                    
                    </c:if> 
                       
                    </c:if>
                    
                    <!--  게스트 인경우  -->
                        <c:if test="${list.listGrade == 'guest'  }">
                  
                    <form id="id${list.member.id}" class="fom">            
                    <td class="buttones">
                   
                    <input type="hidden" name="id" value="${list.member.id}">
                    <input type="hidden" name="membershipCode" value="${list.membership.membershipCode}">
                  
                    
                    <button class="btn btn-dark btn-sm" name="listGrade" value="${list.member.id}" data-value="regular">일반회원</button>
                    <button class="btn btn-danger btn-sm" name="listGrade" value="${list.member.id}" data-value="delete">추방</button>
                    
                    </td>
                    </form>      
                    </c:if>
                    
                   
                    
                    
                    
                    <!--  일반회원인경우  -->
                  <c:if test="${list.listGrade == 'regular'}">
                       <form id="id${list.member.id}" class="fom">            
                    <td class="buttones">
                   
                    <input type="hidden" name="id" value="${list.member.id}">
                    <input type="hidden" name="membershipCode" value="${list.membership.membershipCode}">
                  <c:if test="${memberGrade == 'host'}">
                      <button class="btn btn-primary btn-sm" name="listGrade" value="${list.member.id}" data-value="admin">관리자</button>
                    </c:if>
                    <button class="btn btn-danger btn-sm" name="listGrade" value="${list.member.id}" data-value="delete">추방</button>
                    
                    </td>
                    </form>      
                 
                      </c:if>
                    
                    
                 
                    <c:if test="${list.listGrade == 'host'}">                   
                    <td>호스트</td>
                    </c:if>
                </tr>   
                </c:forEach>         
   
    </tbody>
</table>
<div style="margin-top: 20px; "><a id="back-btn" class="btn" href="/club/${code}">뒤로가기</a></div>
</div>
</div>







  
  
  
  <script>
 
 $(document).ready(function() {
	 
     $('.fom').on('submit', function(e) {
         e.preventDefault();  // 폼의 기본 제출 동작을 방지합니다.
     });
 
$("button").click((e)=>{
	  var buttonText = $(e.target).attr("data-value");
	  var buttonValue = $(e.target).val();
     
 
      var formData = $("#id"+buttonValue).serialize(); 
      
      var resultData = formData + '&listGrade=' +buttonText;
      
     
      if(confirm('정말 변경하시겠습니까?'))  {
    	  
          $.ajax({
      		url: "/gradeUpdate",
      		type: 'post',
      		data: resultData,
      		success: function(data) {
      			
              
      				location.href= "management?membershipCode=" +data;
      			   alert("변경이 완료되었습니다")
      			}
      			
      						
      		
      	});	
    	  
    	  
    	  
    	  
    	  
    	  return true;
      } else {
    	  
    	  
    	  
    	  
    		return false;
      }
    		
     
    	
    
      




});

 })
 

 </script>
 
 
 

</body>
</html>