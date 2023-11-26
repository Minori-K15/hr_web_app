<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除画面</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
</head>
<body>
<header>
 <h1><strong>登録内容</strong></h1>
 <% String message = (String)request.getAttribute("message"); %>
 <p><%= message %></p>
</header>
<main>
  <form action="deleteAdmin" method="get">
    <input type=hidden name="employee_id" value='<%= request.getAttribute("employee_id") %>'>
    <input type=hidden name="employee" value='<%= request.getAttribute("employee") %>'>
    
    <p><strong>社員番号：</strong><%= request.getAttribute("employee_id") %></p>
    <p><strong>従業員：</strong><%= request.getAttribute("employee") %></p>
    <p><strong>所属課：</strong><%= request.getAttribute("affiliation") %></p>
    <p><strong>役職：</strong><%= request.getAttribute("job_title") %></p>
    <button class="btn btn-light" type="submit">削除</a></button>
</form>
  <div class="d-grid gap-2 d-md-block">
    <button class="btn btn-light"><a href="listAdmin">戻る</a></button>
  </div>
</main>
</body>
</html>