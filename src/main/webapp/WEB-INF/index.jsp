<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Damoim 페이지!!</title>
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
               <sec:authorize access="!isAuthenticated()">
               <a href="/loginPage">로그인</a>
               </sec:authorize>
               <sec:authorize access="isAuthenticated()">
              <a href="/logout">로그아웃</a>
              <a>${member}</a>
               </sec:authorize>
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
        
        <div>
         <button class="select" name="all">전체</button>   
        <button class="select" name="액티비티">액티비티</button>    
        <button class="select" name="푸드,드링크">푸드,드링크</button>  
        <button class="select" name="취미">취미</button>  
        <button class="select" name="문화예술">문화예술</button>
        <button class="select" name="스터디">스터디</button>        
        </div>
      
        <div class="membership-list">
        
       <c:forEach   items="${list}"  var="id" varStatus="status" >
      
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
        <script>
        $(document).ready(function() {
            // Function to load the membership list
            function loadMemberships(type) {
                $.ajax({
                    type: 'GET',
                    url: '/type',
                    data: { value: type },
                    success: function(typeList) {
                        let html = "";
                        
                        $.each(typeList, function(index, item) {
                        	
                        	 console.log(item.member.memberImg == '');
                        	
                            html += '<div class="membership-card">';
                            html += '<div class="membership-img">';
                            html += '<a href="/' + item.membership.membershipCode + '">';
                            html += '<img src="' + item.membership.membershipImg + '">';
                            html += '</a>';
                            html += '</div>';
                            html += '<div class="membership-info">';
                            html += '<h1 class="membership-name">' + item.membership.membershipName + '</h1>';
                            html += '<h2>' + item.membership.membershipInfo + '</h2>';
                            html += '<h2>호스트 : ' + item.member.nickname + '</h2>';
                            html += '<input type="hidden" name="code" value="' + item.membership.membershipCode + '">';
                            html += '<h3>인원 현황 ' + item.count + '/' + item.membership.membershipMax + '</h3>';
                            if(item.member.memberImg == ''){
                            html += '<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg" alt="Default User Image">';
                            }
                            else {html += '<img class="user-img" src="' + item.member.memberImg + '" alt="User Image">'}
                            html += '</div>';
                            html += '</div>';
                        });
                       
                        $('.membership-list').html(html);
                    }
                });
            }

            // Load all memberships by default
          //  loadMemberships('all');

            // Set up click event for buttons
            $(".select").click(function(e) {
                const type = $(e.currentTarget).attr("name");
                loadMemberships(type);
            });
        });
    </script>
      </body>
    </html>

