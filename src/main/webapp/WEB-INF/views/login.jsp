<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
</head>
<body>
<header>
 <h1>ログイン</h1>
 <% String message = (String)request.getAttribute("message"); %>
 <p><%= message %></p>
</header>
<main>
  <form action="login" method="post">
  <label for="username">ユーザー名:</label><br>
  <input type="text" name="username" value=''><br>
  <label for="password">パスワード:</label><br>
  <input type="text" name="password" value=''><br>
  <button class="btn btn-light" type="submit">ログイン</a></button>
</form>
  <div class="d-grid gap-2 d-md-block">
    <button class="btn btn-light"><a href="account">新規作成</a></button>
  </div>
</main>
</body>
</html>