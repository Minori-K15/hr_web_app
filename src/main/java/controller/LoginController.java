package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.HashGenerator;

/*
 * ログイン画面用
 * Servlet class CreateController
 * */

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String SQL_SELECT = "SELECT * FROM manager WHERE username = ? AND password = ?";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// メッセージの表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "ログインしてください");
		}
		
		// login.jspを表示
		String view = "/WEB-INF/views/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// データ取得
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// JDBCドライバー接続
		String message_jdbc = jdbc_driver();
		request.setAttribute("message", message_jdbc);
		
		// DB接続 INSERT
			try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD)){
				String hashedPassword = HashGenerator.generateHash(password);
			try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT)){
				
				statement.setString(1, username);
				statement.setString(2, hashedPassword);
				ResultSet result = statement.executeQuery();
				
				if (result.next()) {
					int employee_id = result.getInt("employee_id");
					String job_title = result.getString("job_title");
					
					// サーバーの保持するセッションを取得する
					HttpSession session = request.getSession();
					
					// キーと値のペアでセッションに登録する
					session.setAttribute("employee_id", employee_id);
					session.setAttribute("username", username);
					session.setAttribute("job_title", job_title);
					
					// 権限別listにフォワードして表示
					String view = "/WEB-INF/views/";
					String listAdmin = "listAdmin.jsp";
					String list = "list.jsp";
					
					// 役職名置換
					int job_Title_Num = Integer.parseInt(job_Title(job_title));
					if (job_Title_Num <= 2) {
						// ２以下の場合
						RequestDispatcher dispatcher = request.getRequestDispatcher(view + listAdmin);
						dispatcher.forward(request, response);
					}	else if(job_Title_Num > 2) {
						// 2より大きい場合
						RequestDispatcher dispatcher = request.getRequestDispatcher(view + list);
						dispatcher.forward(request, response);
					}
					} else {
						// 失敗時のメッセージ
						request.setAttribute("message", "ログイン失敗しました");
					
						// ログイン画面に戻る
						String view = "/WEB-INF/views/login.jsp";
						request.getRequestDispatcher(view).forward(request, response);
					}
				}
			} catch (SQLException e) {
				throw new ServletException("Database Connection Failed", e);
			} catch (Exception e) {
				throw new ServletException("Generate hash Failed", e);
		}
	}
	
	// JDBCドライバ接続
	protected String jdbc_driver() {
		String messages_jdbc_Driver = null;
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			messages_jdbc_Driver = "Exception:" + e.getMessage();
		}
		return messages_jdbc_Driver;
	}
	
	// 役職名置換
	protected String job_Title(String job_Title) {
		switch (job_Title){
		case "課長":
			return "1";
			
		case "係長":
			return "2";
		
		case "主任":
			return "3";
			
		case "一般":
			return "4";
			
		}
		return job_Title;
	}
}
