<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>
body {
  padding:1.5em;
  background: #f5f5f5
}

table {
  border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
}

th {
  text-align: left;
}
  
thead {
  font-weight: bold;
  color: #fff;
  background: #73685d;
}
  
 td, th {
  padding: 1em .5em;
  vertical-align: middle;
}
  
 td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
}

a {
  color: #73685d;
}
  
 @media all and (max-width: 768px) {
    
  table, thead, tbody, th, td, tr {
    display: block;
  }
  
  th {
    text-align: right;
  }
  
  table {
    position: relative; 
    padding-bottom: 0;
    border: none;
    box-shadow: 0 0 10px rgba(0,0,0,.2);
  }
  
  thead {
    float: left;
    white-space: nowrap;
  }
  
  tbody {
    overflow-x: auto;
    overflow-y: hidden;
    position: relative;
    white-space: nowrap;
  }
  
  tr {
    display: inline-block;
    vertical-align: top;
  }
  
  th {
    border-bottom: 1px solid #a39485;
  }
  
  td {
    border-bottom: 1px solid #e5e5e5;
  }
  
  
  }



</style>

<body>
 

    
    
    <table>
    <thead>
    <tr>
       <th>아이디</th>
                    <th>이름</th>
                    <th>성별</th>
                    <th>나이</th>
                    <th>폰 번호</th>
                    <th>권한</th>
                    <th>강퇴</th>
                    <th>등급설정</th>

    </tr>
    </thead>
    <tbody>
     <!-- 데이터 행을 여기에 추가합니다 -->
                <c:forEach items="${allMember}" var="list">
                <tr>
                    <td>${list.member.id}</td>
                    <td>${list.member.nickname}</td>
                    <td>${list.member.gender }</td>
                    <td>${list.member.age }</td>
                    <td>${list.member.phone }</td>
                    <td>${list.listGrade}</td>
                    <td><input type="checkbox" disabled checked></td>
                    <td><button class="btn btn-danger btn-sm">관리자</button><button class="btn btn-danger btn-sm">일반</button><button class="btn btn-danger btn-sm">삭제</button></td>
                </tr>   
                </c:forEach>         
   
    </tbody>
</table>

</body>
</html>