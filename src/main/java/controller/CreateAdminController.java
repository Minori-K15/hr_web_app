package controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 登録用
 * Servlet implementation class CreateController
 * */

@WebServlet("/createAdmin")
public class CreateAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 変数定義
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static final String SQL_INSERT = "INSERT INTO manager (employee, affiliation, job_title) VALUE (?, ?, ?)";
//	private static final String SQL_SELECT = "SELECT employee_id,employee,affiliation,job_title FROM manager WHERE employee = ?";
	
	
	// 取得パラメーター変数定義
//	private static String EMPLOYEE_ID;
//	private static String EMPLOYEE;
//	private static String AFFILIATION;
//	private static String JOB_TITLE;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// データを取得
		String employee = request.getParameter("employee");
		String affiliation = request.getParameter("affiliation");
		String job_title = request.getParameter("job_title");
		
		// 変換処理
		// affiliation = affiliation(affiliation);
		// job_Title(job_Title);
		
		// JDBCドライバー接続
		String message_jdbc = jdbc_driver();
		request.setAttribute("message", message_jdbc);
		
		// DB接続 INSERT
		String message_Insert = db_connection_insert(JDBC_URL,DB_USER,DB_PASSWORD,employee,affiliation,job_title);
		request.setAttribute("message", message_Insert);
		
		// DB接続 SELECT
//		String message_Select = db_connection_select(JDBC_URL,DB_USER,DB_PASSWORD,employee);
//		request.setAttribute("employee_id", EMPLOYEE_ID);
//		request.setAttribute("employee", EMPLOYEE);
//		request.setAttribute("affiliation", AFFILIATION);
//		request.setAttribute("job_title", JOB_TITLE);
//		request.setAttribute("message", message_Select);
		
		// 新規作成成功　メッセージ
		request.setAttribute("message", employee + " さんを新規作成しました");
		
		// detail.jspにフォワードして表示
		String view = "/WEB-INF/views/detailAdmin.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// JDBCドライブに接続
	protected String jdbc_driver() {
		String messages_jdbc_Driver = null;
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			messages_jdbc_Driver = "Exception:" + e.getMessage();
		}
		return messages_jdbc_Driver;
	}
	
	// DB接続 INSERT
	protected String db_connection_insert(String jdbc_url, String db_user, String db_password,
			String employee, String affiliation, String job_title) {
		String messages_dbConnection = null;
		
		try(Connection connection = DriverManager.getConnection(jdbc_url,db_user,db_password);
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT)){
			
			// セット
			statement.setString(1,employee);
			statement.setString(2,affiliation);
			statement.setString(3,job_title);
			// 実行
			statement.executeUpdate();
			
		} catch (Exception e) {
			messages_dbConnection = "Exception:" + e.getMessage();
			
		}
		return messages_dbConnection;
	}
	
//	// DB接続 SELECT
//	protected String db_connection_select(String jdbc_url, String db_user, String db_password,
//			String employee) {
//		String messages_dbConnection = null;
//		
//		try(Connection connection = DriverManager.getConnection(jdbc_url,db_user,db_password);
//			PreparedStatement statement = connection.prepareStatement(SQL_SELECT)){
//			
//			// 一番初めの?に対して
//			statement.setString(1, employee);
//			ResultSet results = statement.executeQuery();
//			
//			while (results.next()) {
//				EMPLOYEE_ID = results.getString("employee_id");
//				EMPLOYEE = results.getString("employee");
//				AFFILIATION = results.getString("affiliation");
//				JOB_TITLE = results.getString("job_title");
//				messages_dbConnection = "新規作成完了: " + "<br>" + "" + EMPLOYEE_ID + EMPLOYEE + " さん を新規作成できました";
//			}
//		} catch (Exception e) {
//			messages_dbConnection = "Exception:" + e.getMessage();
//		}
//		return messages_dbConnection;
//	}
	
	// 所属名 変換処理
//	protected String affiliation(String affiliation) {
//		switch (affiliation){
//		case "技術課": 
//			return "1";
//			
//		case "営業課":
//			return "2";
//		
//		case "総務課":
//			return "3";
//			
//		case "人事課":
//			return "4";
//		
//		case "その他":
//			return "5";
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
