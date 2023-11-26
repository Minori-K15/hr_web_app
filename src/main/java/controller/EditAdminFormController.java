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
 * 編集画面用
 * Servlet implementation class CreateController
 * */

@WebServlet("/edit_form")
public class EditAdminFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 変数定義
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/human_resources";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "";
	private static final String SQL_SELECT = "SELECT employee_id,employee,affiliation,job_title FROM manager WHERE employee_id = ?";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// メッセージの表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "編集ページです");
		}
		
		// id取得
		int employee_id = Integer.parseInt(request.getParameter("employee_id"));
		
		// JDBCドライブに接続
		jdbc_driver();
		
		//　変数初期化
		String link = null;

		try (Connection connection = DriverManager.getConnection (JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT)){
					
					// 一番初めの?に対して
					statement.setInt(1, employee_id);
					ResultSet results = statement.executeQuery();
					
					while (results.next()) {
						
						String id = results.getString("employee_id");
						request.setAttribute("employee_id", id);
						
						String employee = results.getString("employee");
						request.setAttribute("employee", employee);
						
						String affiliation = results.getString("affiliation");
						request.setAttribute("affiliation", affiliation);
						
						String job_title = results.getString("job_title");
						request.setAttribute("job_title", job_title);
						
						//置換処理
						String job_title_Number = job_title(job_title);
						// リンク取得
						link = editUrl(job_title_Number);
					}
			} catch (Exception e) {
				request.setAttribute("message", "Exception:" + e.getMessage());
			}
		// edit_form.jspを表示
		String view = "/WEB-INF/views/" + link ;
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	
	}
	
	// JDBCドライブに接続
	protected void jdbc_driver() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
			new IllegalArgumentException("Loading of jdbc driver failed" + e);
		}
	}
	
	// 役職名 変換処理
	protected String job_title(String job_title) {
		switch (job_title){
		case "一般": 
			return "0";
		
		case "主任":
			return "1";
		
		case "係長":
			return "2";
		
		case "課長":
			return "3";
		}
		return job_title;
	}
	
	// 役職取得
	protected String editUrl (String job_title_number) {
		
		if(Integer.parseInt(job_title_number) >= 2) {
			return "editAdmin_form.jsp";
		}	else {
			return "edit_form.jsp";
		}
	}
}
