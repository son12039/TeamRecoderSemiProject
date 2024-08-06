<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>클럽 생성</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainCreate.css" />
    <link rel="stylesheet" href="/${pageContext.request.contextPath}/css/reset.css" />
  </head>
  <body>
    <div class="container">
      <h1>클럽 생성</h1>
      <form action="/mainCreate" method="post" enctype="multipart/form-data">
        <div class="create_box">
          <label>클럽 이름:
          <input type="text" name="mainName" value="${vo.mainName}"required />
          </label>
        </div>
        <div class="create_box">
          <label>클럽 설명:
          <textarea
            name="mainText"
            value="${vo.mainText}"
            required
          ></textarea>
          </label>
        </div>
        <div class="create_box">
          <label for="club-image">클럽 이미지 업로드:</label>
          <input
            type="file"
            id="club-image"
            name="${vo.image.imgUrl}"
            accept="image/*"
          />
        </div>
        <button type="submit">생성</button>
      </form>
    </div>
  </body>
</html>
