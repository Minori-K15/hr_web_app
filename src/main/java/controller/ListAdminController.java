package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * 一覧表示用
 * Servlet implementation class CreateController
 * */

@WebServlet("/listAdmin")
public class ListAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static final String SQL_SELECT = "SELECT * FROM manager";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション
		HttpSession session = request.getSession(false);
		// セッション情報がないもしくは期待した状態でなければログイン画面に遷移する
		if (session == null) {
			request.getRequestDispatcher("login").forward(request, response);
		}
		
		String employee_Name = (String) session.getAttribute("employee");
		request.setAttribute("employee",employee_Name);
		
		// メッセージの表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "[管理者専用] " + "人事情報を管理しましょう" );
		}
		
		// JDBCドライバー接続
		jdbc_driver();
		
		// DB接続 SELECT
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
			ResultSet results = statement.executeQuery()){
				// 取得したデータをリスト化
				ArrayList<HashMap<String, String>> rows = new
				ArrayList<HashMap<String, String>> ();
				
				while (results.next()) {
					HashMap<String, String> conlums = new
					HashMap<String, String> ();
					
					String employee_id = results.getString("employee_id");
					conlums.put("employee_id", employee_id);
					
					String employee = results.getString("employee");
					conlums.put("employee", employee);
					
					String affiliation = results.getString("affiliation");
					conlums.put("affiliation", affiliation);
					
					String job_title = results.getString("job_title");
					conlums.put("job_title", job_title);
					
					rows.add(conlums);
				}
				request.setAttribute("rows", rows);
		} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		
		// list.jspにフォワードして表示
		String view = "/WEB-INF/views/listAdmin.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// JDBCドライバー接続
	protected void jdbc_driver() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Loading of JDBC driver failed" + e);
		}
	}
	
	// 所属名 変換処理
//	protected String affiliation(String affiliation) {
//		switch (affiliation){
//		case "1": 
//			return "技術課";
//			
//		case "2":
//			return "営業課";
//		
//		case "3":
//			return "総務課";
//			
//		case "4":
//			return "人事課";
//		}
//	}
	
	// 役職名 変換処理
//	protected String job_Title(String job_Title) {
//		switch (job_Title){
//		case "1":
//			return "課長";
//			
//		case "2":
//			return "係長";
//		
//		case "3":
//			return "主任";
//			
//		case "4":
//			return "一般";
//		}
//	}
}
