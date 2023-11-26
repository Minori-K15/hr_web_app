<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規作成</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
</head>
<body>
<header>
 <h1><strong>新規作成</strong></h1>
 <% String message = (String)request.getAttribute("message"); %>
 <p><%= message %></p>
</header>
<main>
  <form action="createAdmin" method="get">
  <label for="employee">従業員名</label><br>
  <input type="text" name="employee" value=''><br>

  <label for="affiliation">所属課</label><br>
    <select name ="affiliation" width = "147" height = "21.5">
      <option value="技術課">技術課</option>
      <option value="営業課">営業課</option>
      <option value="総務課">総務課</option>
      <option value="人事課">人事課</option>
    </select><br>

  <label for="job_title">所属課</label><br>
    <select name ="job_title" width = "147" height = "21.5">
      <option value="課長">課長</option>
      <option value="係長">係長</option>
      <option value="主任">主任</option>
      <option value="一般">一般</option>
    </select><br>
  <button class="btn btn-light" type="submit">作成</button>
  <button class="btn btn-light" type="submit"><a href="list">戻る</button>
  </form>
</main>
</body>
</html>