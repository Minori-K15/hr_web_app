<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>人事情報一覧</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>
<body>
<header>
  <h1>人事情報一覧</h1>
  <% String employee = (String)request.getAttribute("employee"); %>
  <% String message = (String)request.getAttribute("message"); %>
  <p><%= message %> <br> ログイン: <%= employee %> さん</p>
</header>
  <table class="table table-striped table-hover table-sm">
  <thead  align="center" class="table-primary">
  <th width="70"><strong>社員番号</strong></th>
  <th width="100"><strong>名前</strong></th>
  <th width="100"><strong>所属課</strong></th>
  <th width="100"><strong>役職</strong></th>
  </thead>
  <tbody>
  <% ArrayList<HashMap<String, String>> rows =
  (ArrayList<HashMap<String, String>>)request.getAttribute("rows");%>
  
  <% for (HashMap<String, String> columns : rows) { %>
  <tr>
  <td align="center" class="table-light"><%= columns.get("employee_id") %></td>
  <td align="center" class="table-light"><button class="btn btn-light"><a href='detail?employee_id=<%= columns.get("employee_id") %>'><%= columns.get("employee") %></a></button></td>
  <td align="center" class="table-light"><%= columns.get("affiliation") %></td>
  <td align="center" class="table-light"><%= columns.get("job_title") %></td>
  </tr>
  <% } %>
  </tbody>
  </table>
  <div class="d-grid gap-2 d-md-block">
  <button class="btn btn-light" type="button"><a href="create_form">新規作成</a></button>
  <button class="btn btn-light" type="button"><a href="logout">ログアウト</a></button>
  </div>
</body>
</html>