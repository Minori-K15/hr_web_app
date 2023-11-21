package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * ログアウト用
 * Servlet implementation class CreateController
 * */

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログアウト時はセッションを破棄する
		HttpSession session = request.getSession(false);
		// 既にセッションが存在する場合は一度破棄する
		if (session != null) {
			log("セッション破棄 セッションID=[" + session.getId() + "]");
			
			int employee_id = (int) session.getAttribute("employee_id");
			String employee = (String) session.getAttribute("employee");
			
			String message = "ID : [ " + employee_id + "] " + "ユーザー : [ " + employee + "] " + " ログアウトしました" + "<br>" + "ログインしてください";
			request.setAttribute("message",  message);
			session.invalidate();
		}
		
		// セッション破棄後にログイン画面に遷移する
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}
}
