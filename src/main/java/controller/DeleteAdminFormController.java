package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 削除用
 * Servlet implementation class CreateController
 * */

@WebServlet("/delete_admin_form")
public class DeleteAdminFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static final String SQL_SELECT = "SELECT * FROM manager WHERE employee_id = ?";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
		IOException {
		
		// メッセージの表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "削除してもよろしいですか？");
		}
		
		// id取得
		int employee_id = Integer.parseInt(request.getParameter("employee_id"));
		
		// JDBCドライバー接続
		jdbc_driver(JDBC_DRIVER);
		
		// DB接続 SELECT
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL_SELECT)){
			
			statement.setInt(1, employee_id);
			ResultSet results = statement.executeQuery();
				
				while (results.next()) {
					
					String number_id = results.getString("employee_id");
					request.setAttribute("employee_id", number_id);
					
					String employee = results.getString("employee");
					request.setAttribute("employee", employee);
					
					String affiliation = results.getString("affiliation");
					request.setAttribute("affiliation", affiliation);
					
					String job_title = results.getString("job_title");
					request.setAttribute("job_title", job_title);
				}
			} catch (Exception e) {
			request.setAttribute("message", "Exception:" + e.getMessage());
		}
		
		// delete_form.jspを表示
		String view = "/WEB-INF/views/deleteAdmin_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// jdbcドライバーに接続
	protected void jdbc_driver(String jdbc_driver) {
		try {
			Class.forName(jdbc_driver);
		} catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Loading of jdbc driver failed" + e);
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
//		
//		case "5":
//			return "その他";
//			
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + affiliation);
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
//			
//		case "5":
//			return "その他";
//			
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + job_Title);
//		}
//	}
}
