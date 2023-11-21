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
 * 登録画面用
 * Servlet implementation class CreateController
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
		
		// create_form.jspを表示
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
				
				// 
				statement.setString(1, username);
				statement.setString(2, hashedPassword);
				ResultSet result = statement.executeQuery();
				
				if (result.next()) {
					// 
					int employee_id = result.getInt("employee_id");
					String employee = result.getString("employee");
					
					// サーバーの保持するセッションを取得する
					HttpSession session = request.getSession();
					
					// キーと値のペアでセッションに登録する
					session.setAttribute("employee_id", employee_id);
					session.setAttribute("username", username);
					session.setAttribute("employee", employee);
					
					// String employeeName = request.setAttribute("employee", employee);
					response.sendRedirect("list");
					
					// list.jspにフォワードして表示
//					String view = "/WEB-INF/views/list.jsp";
//					RequestDispatcher dispatcher = request.getRequestDispatcher(view);
//					dispatcher.forward(request, response);
					
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
}
