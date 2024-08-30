<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
    crossorigin="anonymous" />
</head>
<body>



<div class="container">

   <h1>게시물 수정 ${membershipCode}</h1>
   
   <form action="/write" method="post" enctype="multipart/form-data">
    <div class="form-group">
    <label>Title</label>
    시작 날짜 :  <input type="date"  name="meetDateStart" >
    종료 날짜 :  <input type="date"  name="meetDateEnd">
    색상 선택 : <input type="color" name="color">
    <input class="form-control" name="meetTitle">
    </div>
     <div class="form-group">
    <label>Content</label>
    <textarea class="form-control" name="meetInfo" rows="10"></textarea>
    </div>
     <div class="form-group">
    <label>Add File</label>
    <input class="form-control" name="files"  type="file" multiple accept="image/*">
    </div>
    <input type="hidden" name="membershipCode" value="${membershipCode}">
   <button type="submit" class="btn btn-outline-warning"> 등록 </button>
   </form>

</div>







</body>
</html>