package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

@WebServlet("/delete")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static final String SQL_DELETE = "DELETE FROM manager WHERE employee_id = ?";

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
		String employee = request.getParameter("employee");
		
		// JDBCドライバー接続
		jdbc_driver(JDBC_DRIVER);
		
		// DB接続 DELETE
		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE)){
			
				statement.setInt(1, employee_id);
				statement.executeUpdate();
				request.setAttribute("message","社員番号: " + employee_id + " 名前: " + employee + " さんの" + "<br>" + "削除が完了しました");
				
		} catch (SQLException e) {
			request.setAttribute("message", "SQLException:" + e.getMessage());
		}
		
		// list.jspにフォワードして表示
		String view = "/list";
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
