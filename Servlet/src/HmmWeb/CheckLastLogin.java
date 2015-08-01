package HmmWeb;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckLastLogin
 */
@WebServlet("/LoginNewRecordsCheck")
public class CheckLastLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INVALID_PARAM = "Invalid Parameter!";
	private static final String CONN_ERROR = "Failed to estabalish database connection!";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckLastLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Writer
		Writer writer = response.getWriter();
		
		//Get UID
		String uidStr = request.getParameter("UID");
		if(uidStr == null) {
			writer.write(INVALID_PARAM+"\n");
			writer.flush();
		}
		int uid = -1;
		try {
			uid = Integer.parseInt(uidStr);
		}catch(Exception e) {
			writer.write(INVALID_PARAM+"\n");
			writer.flush();
		}
		
		//Fetch new records
		UserUtil userUtil = new UserUtil(uid);
		if(userUtil.open() == true) {
			writer.write(userUtil.lastUpdateFromLastLogin());
			writer.flush();
		} else {
			writer.write(CONN_ERROR + "\n");
			writer.flush();
		}
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
