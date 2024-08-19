<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>클럽 생성</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
      select {
        display: block;
        width: 200px;
        padding: 10px;
        margin: 10px 0;
        border: 2px solid #333;
        border-radius: 5px;
        background-color: #f0f0f0;
      }
    </style>
  </head>
  <body>
  	<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
				
    <form action="/makeMembership" method="post" enctype="multipart/form-data">
      클럽명 : <input type="text" name="membershipName" /> 사진첨부:
      <input type="file" name="file" accept="image/*" /> 클럽소개 :<input
        type="text"
        name="membershipInfo"
      />
      최대 인원 : <input type="text" name="membershipMax" />
      <h2>${member.id}</h2>
      <input type="hidden" name="id" value="${member.id}" />
      <input type="hidden" name="listGrade" value="host" />
      <button type="submit">클럽생성</button>
    </form>
    </sec:authorize>
    <!-- 
    <script>
      $(document).ready(function () {
        // 주 장르에 따른 세부 장르 목록
        const subGenresByMainGenre = {
          액티비티: [
            "자전거",
            "배드민턴",
            "등산",
            "야구",
            "서핑",
            "클라이밍",
            "농구",
          ],
          "푸드,드링크": ["맛집투어", "베이킹", "카페", "위스키"],
          취미: ["공예", "보드게임", "드로잉", "PC게임", "모바일게임"],
          문화예술: [
            "영화",
            "전시",
            "페스티벌",
            "힙합",
            "미술",
            "대중가요",
            "콘서트",
          ],
          스터디: ["코딩", "공무원", "자격증", "영어", "중국어", "일본어"],
        };

        // 주 장르 선택 시 세부 장르 목록 업데이트
        $("#mainGenreSelect").change(function () {
          const selectedMainGenre = $(this).val(); // 선택된 주 장르의 값
          const $subGenreSelect = $("#subGenreSelect"); // 세부 장르 선택 요소

          // 세부 장르 드롭다운 초기화
          $subGenreSelect
            .empty()
            .append('<option value="">-- 선택 --</option>');

          // 선택된 주 장르가 있고, 해당 장르에 세부 장르가 정의되어 있으면
          if (selectedMainGenre && subGenresByMainGenre[selectedMainGenre]) {
            const subGenres = subGenresByMainGenre[selectedMainGenre]; // 선택된 주 장르의 세부 장르 배열

            // 세부 장르 배열을 순회하여 드롭다운에 추가
            $.each(subGenres, function (index, subGenre) {
              $subGenreSelect.append(
                $("<option></option>").val(subGenre).text(subGenre)
              );
            });
          }
        });
      });
    </script>-->
  </body>
</html>
