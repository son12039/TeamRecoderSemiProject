<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   		 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/meetingWrite.css" />
    <title>모임 게시판 글 작성</title>
      <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
 
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/jodit@3.6.9/build/jodit.min.css"
    />
    <style>

    </style>
    
  </head>
  <body>
   <jsp:include page="../header/header.jsp" />
   <div id="box">
   <div id="container">
   <div id="input-box">
   <div id="input-left">
   <h1>모임 게시판 글 수정</h1>
  <input type="text" id="title" name="meetTitle" placeholder="제목을 입력해 주세요." autofocus="autofocus" value="${meetingInfo.meetTitle}" />
    </div>
    <div id="input-right">
    <h2>달력 색상 선택 : </h2><input type="color" id="colors" name="color" value="${meetingInfo.color}" />  
    <h2>시작 날짜 : </h2><input class="day" type="date" id="start" name="meetDateStart" value="${meetingInfo.meetDateStart}"/>
    <h2>종료 날짜 : </h2><input class="day" type="date" id="end" name="meetDateEnd" value="${meetingInfo.meetDateEnd }" />
    <input  type="hidden" id="code" name="membershipCode" value="${membershipCode}" />
	</div>
	</div>
    <textarea id="editor" name="editor">${meetingInfo.meetInfo}</textarea>

    <!-- 제출 버튼 -->
    <div id="button-box">
     <button class="button" id="submitButton">수정</button>
    <a class="button" id="back-button" href="/club/${memInfo.membershipCode}">뒤로가기</a>
   
    </div>
     </div>
   </div>
    <jsp:include page="../footer/footer.jsp" />

    <!-- Jodit JS 라이브러리 -->
    <script src="https://cdn.jsdelivr.net/npm/jodit@3.6.9/build/jodit.min.js"></script>
    <script>
      // Jodit 에디터 초기화
      const editor = new Jodit("#editor", {
        uploader: {
          insertImageAsBase64URI: true, // 이미지 업로드 시 base64로 변환
          // 업로드 핸들러 URL 설정 (서버 측에서 처리)
          url: "upload_image.php", // 실제 파일 업로드를 처리할 서버 URL
        },
        height: 500, // 에디터의 높이 설정
        toolbar: true, // 툴바 표시
        buttons: [
        	"bold",
            "italic",
            "underline",
            "strikethrough",
            "|",
            "ul",
            "ol",
            "|",
            "outdent",
            "indent",
            "|",
            "link",
            "image",
          'fontsize'
        ], // 툴바에 표시할 버튼들
      });

      // 제출 버튼 클릭 핸들러
      document
        .getElementById("submitButton")
        .addEventListener("click", function () {
          const content = editor.getEditorValue(); // 에디터의 내용을 가져옴
          const title = $("#title").val();
          const start = $("#start").val();
          const end = $("#end").val();
          const color = $("#colors").val();
          const code = $("#code").val();
    
          // 콘솔에 내용 출력 (테스트 용도)
          console.log(content);
          console.log(title == "");
          console.log(start);
          console.log(end);
          console.log(color);
       if(title == ""){
    	   alert("제목을 입력해주세요")
       }else if( start == ""){
        alert("날짜를 확인해주세요 ")  
       }else if ( end == ""){
    	   alert("종료 날짜를 확인해주세요")
       }else if (start > end ){
    	   alert("날짜를 확인해주세요")
       } else if (content == ""){
    	   alert("내용을 입력해주세요!")
       } else {
          $.ajax({
            url: "/meetingUpdate", // 요청을 보낼 URL
            type: "POST",
            data: {
              meetTitle: title,
              meetInfo: content,
              meetDateStart: start,
              meetDateEnd: end,
              color: color,
              membershipCode: code,
            }, 
            success: function (response) {
              alert("수정 완료되었습니다 !!");
              window.location.href = '/club/'+code;
            },
            error: function (xhr, status, error) {
              alert("오류가 발생했습니다.");
              console.error(xhr.responseText);
            },
          });
          
       }
        });
    </script>
  </body>
</html>
