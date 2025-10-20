package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoAutor;
import entidades.Autor;

/**
 * Servlet implementation class ControllerSocio
 */
@WebServlet({ "/ControllerSocio", "/controllersocio" })
public class ControlerSocio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlerSocio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String operacion= request.getParameter("operacion");
		
		
		switch(operacion) {
		case "listarAutores":
			
		    DaoAutor dao=new DaoAutor();
			try {
				ArrayList<Autor> listadoautores= dao.listadoAutores();
				request.setAttribute("listadoautores", listadoautores);
				request.getRequestDispatcher("socios/listadoautores.jsp").forward(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("mensaje", "Operación fallida. Error: " + e.getMessage());
				request.getRequestDispatcher("socios/listadoautores.jsp").forward(request,response);
			}
		    
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
