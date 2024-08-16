<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅</title>

<link rel="stylesheet" href="/css/chatting.css">

</head>
<body>
	<form action="">
		<a href="/">메인화면가기</a>
	</form>
	
	<!-- 채팅방 목록 -->
	<main>
        <h1>채팅방</h1>
        <button class="new_chat">미완성</button>
        <nav>
        	<span>방 제목</span>
        	<span>인원</span>
        </nav>
       	<hr>

        <ul>
            <li>
            </li>
        </ul>
    </main>
	 			 <div id="ch1" class="character"></div>
  				 <div id="ch2" class="character"></div>
   				 <div id="ch3" class="character"></div>
   				 <div id="ch4" class="character"></div>
  				 <div id="ch5" class="character"></div>
   				 <div id="ch6" class="character"></div>
   				 <div id="ch7" class="character"></div>
  				 <div id="ch8" class="character"></div>
   				 <div id="ch9" class="character"></div>
   				 <div id="ch10" class="character"></div>
  				 <div id="ch11" class="character"></div>
   				 <div id="ch12" class="character"></div>
   				 <div id="ch13" class="character"></div>
  				 <div id="ch14" class="character"></div>
   				 <div id="ch15" class="character"></div>
   				 <div id="ch16" class="character"></div>
  				 <div id="ch17" class="character"></div>
   				 <div id="ch18" class="character"></div>
   				 <div id="ch1" class="character"></div>
  				 <div id="ch2" class="character"></div>
   				 <div id="ch3" class="character"></div>
   				 <div id="ch4" class="character"></div>
  				 <div id="ch5" class="character"></div>
   				 <div id="ch6" class="character"></div>
   				 <div id="ch7" class="character"></div>
  				 <div id="ch8" class="character"></div>
   				 <div id="ch9" class="character"></div>
   				 <div id="ch10" class="character"></div>
  				 <div id="ch11" class="character"></div>
   				 <div id="ch12" class="character"></div>
   				 <div id="ch13" class="character"></div>
  				 <div id="ch14" class="character"></div>
   				 <div id="ch15" class="character"></div>
   				 <div id="ch16" class="character"></div>
  				 <div id="ch17" class="character"></div>
   				 <div id="ch18" class="character"></div>
	<!-- 채팅방 입장 -->
    <div class="chat">
   		<div>
   			<div class="chat_body">
		        <h2 class="chat_title">1번방</h2>
		        <button class="chat_back">◀</button>

		        <ul class="chat_list">
		            <li>
		            </li>
		        </ul>
				 
		        <div class="chat_input">
		            <div class="chat_input_area">
		                <textarea></textarea>
		            </div>

		            <div class="chat_button_area">
		                <button>전송</button>
		            </div>
		        </div>
	        </div>

	        <div class="chat_users">
	        	<h2>
	        		참가인원
	        		<span class="user"></span>
	        	</h2>

	        	<div class="chat_nickname">
	        		<ul>
	        			<li>
	       				</li>
	        		</ul>
	        	</div>
	        </div>
   		</div>
    </div>
    
    
    
    <!-- 테스트 입장 -->
	
    <!-- sock js -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.2/sockjs.min.js"></script>
	<!-- STOMP -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="/js/chatting.js"></script>
</body>
</html>