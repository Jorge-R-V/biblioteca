 package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Servlet implementation class Controller
 */
@WebServlet({ "/Controller", "/controller" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String operacion= request.getParameter("operacion");
	    // Obtener día de la semana
		HashMap<String, String> diasSemana = new HashMap<String, String>();
		diasSemana.put("MONDAY", "LUNES");
		diasSemana.put("TUESDAY", "MARTES");
		diasSemana.put("WEDNESDAY", "MIERCOLES");
		diasSemana.put("THURSDAY", "JUEVES");
		diasSemana.put("FRIDAY", "VIERNES");
		diasSemana.put("SATURDAY", "SABADO");
		diasSemana.put("SUNDAY", "DOMINGO");
	
		switch (operacion) {
		
			case"matricular":
				String nombre = request.getParameter("nombre");
			    String apellido1 = request.getParameter("apellido1");
			    String apellido2 = request.getParameter("apellido2");
			    String email = request.getParameter("email");
			    String fechaNacimiento = request.getParameter("fechaNacimiento");
			    String provincia = request.getParameter("provincia");
			    String emancipado = request.getParameter("emancipado");
			    String[] modulos = request.getParameterValues("modulos");
			    
			    
			    
				DateTimeFormatter formatoDeFecha=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    	LocalDate fechanacimiento=LocalDate.parse(fechaNacimiento,formatoDeFecha);
		    
			    String diasemanaingles=fechanacimiento.getDayOfWeek().name();
				String diasemana=diasSemana.get(diasemanaingles);
			    
			    java.sql.Date sqlDatefechanacimiento=java.sql.Date.valueOf(fechanacimiento);
			   
			    request.setAttribute("nombre", nombre);
			    request.setAttribute("apellido1", apellido1);
			    request.setAttribute("apellido2", apellido2);
			    request.setAttribute("email", email);
			    request.setAttribute("fechaNacimiento", sqlDatefechanacimiento);
			    request.setAttribute("provincia", provincia);
			    request.setAttribute("emancipado", emancipado);
			    request.setAttribute("modulos", modulos);
			    
			    request.getRequestDispatcher("confirmacionMatricula.jsp").forward(request,response);
			    break;
		    default:
		    	break;
		}
	   
    	
		// TODO Auto-generated method stub
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}
	
	
	
	

}
