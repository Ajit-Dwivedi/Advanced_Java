package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloWorldXml extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("in do-get xml "+Thread.currentThread());
		//1. set resp cont type : meant for clnt browser
		resp.setContentType("text/html");
		//2. get writer : for sending char , buf resp from servlet ---> clnt
		try(PrintWriter pw=resp.getWriter())
		{
			pw.print("Resp from the xml servlet @ "+LocalDateTime.now());
		}//JVM : pw.close() --> resp is sent / committed/ rendered.
		
	}

	@Override
	public void destroy() {
		System.out.println("in destory xml "+Thread.currentThread());
	}

	@Override
	public void init() throws ServletException {
		System.out.println("in init xml "+Thread.currentThread());
	}

}
