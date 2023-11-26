package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 登録画面用
 * Servlet implementation class CreateController
 * */

@WebServlet("/createAdmin_form")
public class CreateAdminFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// メッセージの表示
		if (request.getAttribute("message") == null) {
			// nullの場合のメッセージ
			request.setAttribute("message", "新規作成ページです");
		}
		
		// create_form.jspを表示
		String view = "/WEB-INF/views/createAdmin_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	
	}
}
