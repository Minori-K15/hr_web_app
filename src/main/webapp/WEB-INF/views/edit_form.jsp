<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>編集画面</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=M+PLUS+Rounded+1c:wght@300&display=swap" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet">
</head>
<body>
<header>
 <h1><strong>編集画面</strong></h1>
 <% String message = (String)request.getAttribute("message"); %>
 <p><%= message %></p>
</header>
<main>
  <form action="update" method="get">
  <label for="employee_id"><strong>社員番号</strong></label><br>
  <input type="text" name="employee_id" value='<%= request.getAttribute("employee_id") %>' readonly><br>

  <label for="employee"><strong>従業員名</strong></label><br>
  <input type="text" name="employee" value='<%= request.getAttribute("employee") %>'><br>
  
    <% 
    String affiliation = (String)request.getAttribute ("affiliation"); 
    String affiliation_1 = (affiliation != null && affiliation.equals("技術課") ? "selected" : ""); 
    String affiliation_2 = (affiliation != null && affiliation.equals("営業課") ? "selected" : "");
    String affiliation_3 = (affiliation != null && affiliation.equals("総務課") ? "selected" : "");
    String affiliation_4 = (affiliation != null && affiliation.equals("人事課") ? "selected" : "");
    %>
    <label for="affiliation"><strong>所属課</strong></label><br>
    <select name ="affiliation" width = "147" height = "21.5">
      <option value="技術課" <%= affiliation_1 %>>技術課</option>
      <option value="営業課" <%= affiliation_2 %>>営業課</option>
      <option value="総務課" <%= affiliation_3 %>>総務課</option>
      <option value="人事課" <%= affiliation_4 %>>人事課</option>
    </select><br>
    
   <% 
    String job_title = (String)request.getAttribute ("job_title"); 
    String job_title_1 = (job_title != null && job_title.equals("課長") ? "selected" : ""); 
    String job_title_2 = (job_title != null && job_title.equals("係長") ? "selected" : "");
    String job_title_3 = (job_title != null && job_title.equals("主任") ? "selected" : "");
    String job_title_4 = (job_title != null && job_title.equals("一般") ? "selected" : "");
    %>
  <label for="job_title"><strong>役職</strong></label><br>
        <select name ="job_title" width = "147" height = "21.5">
      <option value="課長" <%= job_title_1 %>>課長</option>
      <option value="係長" <%= job_title_2 %>>係長</option>
      <option value="主任" <%= job_title_3 %>>主任</option>
      <option value="一般" <%= job_title_4 %>>一般</option>
    </select><br>
  <button class="btn btn-light" type="submit">保存</button>
  </form>
  <div class="d-grid gap-2 d-md-block">
    <button class="btn btn-light"><a href="list">戻る</a></button>
  </div>
</main>
</body>
</html>