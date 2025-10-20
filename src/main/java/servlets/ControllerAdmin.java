 package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.DaoAutor;
import dao.DaoEjemplar;
import dao.DaoPrestamo;
import dao.DaoSocio;
import entidades.Autor;
import entidades.Ejemplar;
import entidades.Prestamo;
import entidades.Socio;

/**
 * Servlet implementation class ControllerAdmin
 */
@WebServlet({ "/ControllerAdmin", "/controlleradmin" })
public class ControllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void procesarError(HttpServletRequest request,
            HttpServletResponse response,
            Exception e, String url)
           throws ServletException, IOException {
       String mensajeError = e.getMessage();
       request.setAttribute("error", mensajeError);
       if (url == null) {
           url = "admin/error.jsp";
       }
       request.getRequestDispatcher(url).forward(request, response);
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String operacion= request.getParameter("operacion");
		//
		
	
		switch(operacion) {
		//----------------------------------------------------
		case "altaAutor":
			 String nombre = request.getParameter("nombre");
             String fechaNacimiento = request.getParameter("fechaNacimiento");

             
             DateTimeFormatter formatoDeFecha=DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDate fechanacimiento=LocalDate.parse(fechaNacimiento,formatoDeFecha);
             
             java.sql.Date sqlDatefechanacimiento=java.sql.Date.valueOf(fechanacimiento);
             
             Autor autor=new Autor();
             autor.setNombre(nombre);
             autor.setFechanacimiento(sqlDatefechanacimiento);
             request.setAttribute("nombre", nombre);
             request.setAttribute("fechanacimiento", sqlDatefechanacimiento);
             
            try {
            	DaoAutor dao=new DaoAutor();
				dao.insertaAutor(autor);
				request.setAttribute("mensajeconfirmacion", "Operación realizada con éxito");
				request.getRequestDispatcher("admin/altaAutor.jsp").forward(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("mensajeerror", "Operación fallida. Error: " + e.getMessage());
				request.getRequestDispatcher("admin/altaAutor.jsp").forward(request,response);
			} catch (DateTimeParseException pe) {
				// TODO Auto-generated catch block
				request.setAttribute("mensajeerror", "Formato de fecha incorrecto: yyyy-MM-dd");
				request.setAttribute("nuevoautor", autor);
				request.setAttribute("fechaErronea", sqlDatefechanacimiento);
				procesarError(request, response, pe, "mensajeerror");
				request.getRequestDispatcher("admin/altaAutor.jsp").forward(request,response);
			}
             
             break;
             //-----------------------------------------
             
		case "socioslibrosfueraplazo":
			DaoSocio dao=new DaoSocio();
			try {
				ArrayList<Socio> listadomorosos= dao.listadoMorosos();
				request.setAttribute("listadoMorosos", listadomorosos);
				request.getRequestDispatcher("admin/listadoMorosos.jsp").forward(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("mensaje", "Operación fallida. Error: " + e.getMessage());
				request.getRequestDispatcher("admin/listadoMorosos.jsp").forward(request,response);
			}
		    
			break;
			//-------------------------------------------------
		case "listarLibrosMorosos":
			String stridsocio=request.getParameter("idsocio");
            int idsocio=Integer.parseInt(stridsocio);
            try {
                dao = new DaoSocio();
                ArrayList<Socio> listadoMorosos = dao.listadoMorosos();
                request.setAttribute("listadoMorosos", listadoMorosos);
                
                DaoPrestamo daop = new DaoPrestamo();
                ArrayList<Prestamo> listadoLibrosMorosos = daop.listadoLibrosMoroso(idsocio);
                request.setAttribute("listadoLibrosMorosos", listadoLibrosMorosos);
                Socio socioSeleccionado = dao.findSocioById(idsocio);
                request.setAttribute("socioSeleccionado", socioSeleccionado);
                request.getRequestDispatcher("admin/listadoMorosos.jsp").forward(request,response);
                
            } catch(Exception e){
                System.out.println("Error");
                request.setAttribute("mensaje", "Operación fallida. Error: " + e.getMessage());
				request.getRequestDispatcher("admin/listadoMorosos.jsp").forward(request,response);
            }
            break;
            //--------------------------------------------------------
            
		case "altaSocio":
			String nombresocio = request.getParameter("nombre");
            String email = request.getParameter("email");
            String direccion = request.getParameter("direccion");
            
            Socio socio=new Socio();
            socio.setNombre(nombresocio);
            socio.setEmail(email);
            socio.setDireccion(direccion);
            request.setAttribute("nombre", nombresocio);
            request.setAttribute("email", email);
            request.setAttribute("direccion", direccion);
            
           try {
           	DaoSocio dao1=new DaoSocio();
				dao1.insertSocio(socio);
				request.setAttribute("mensajeconfirmacion", "Operación realizada con éxito");
				request.getRequestDispatcher("admin/altaSocio.jsp").forward(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("mensajeerror", "Operación fallida. Error: " + e.getMessage());
				request.getRequestDispatcher("admin/altaSocio.jsp").forward(request,response);
			} catch (DateTimeParseException pe) {
				// TODO Auto-generated catch block
				request.setAttribute("mensajeerror", "Formato de fecha incorrecto: yyyy-MM-dd");
				procesarError(request, response, pe, "mensajeerror");
				request.getRequestDispatcher("admin/altaSocio.jsp").forward(request,response);
			}
            
            break;
            //----------------------------------------------
		case "prestamo":
			String idejemplarprestamo = request.getParameter("idejemplarprestamo");
			String idsocioprestamo = request.getParameter("idsocioprestamo");
			try {
			    int idejemplar = Integer.parseInt(idejemplarprestamo);
			    int idsociopres = Integer.parseInt(idsocioprestamo);
			    // Fecha de préstamo: hoy
			java.time.LocalDate hoy = java.time.LocalDate.now();
			java.sql.Date sqlFechaPrestamo = java.sql.Date.valueOf(hoy);
			// Fecha límite: 5 días después
			java.time.LocalDate fechaDev = hoy.plusDays(5);
			// Si cae en sábado o domingo, mover al próximo lunes
			java.time.DayOfWeek dow = fechaDev.getDayOfWeek();
			if (dow == java.time.DayOfWeek.SATURDAY) {
			    fechaDev = fechaDev.plusDays(2);
			} else if (dow == java.time.DayOfWeek.SUNDAY) {
			    fechaDev = fechaDev.plusDays(1);
			}
			java.sql.Date sqlFechaDevolucion = java.sql.Date.valueOf(fechaDev);
			try {
				DaoPrestamo dao1=new DaoPrestamo();
				dao1.insertarPrestamo(idejemplar, idsociopres, sqlFechaPrestamo, sqlFechaDevolucion);
				request.setAttribute("mensajeconfirmacion", "Operación realizada con éxito");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("mensajeerror", "Operación fallida. Error: " + e.getMessage());
				}
			    
			    
			} catch(NumberFormatException nfe) {
			    request.setAttribute("mensajeerror", "Datos inválidos para id ejemplar o id socio.");
			} catch(Exception e) {
			    e.printStackTrace();
			    request.setAttribute("mensajeerror", "Error al crear préstamo: "+e.getMessage());
			}
			request.getRequestDispatcher("admin/prestamo.jsp").forward(request,response);
		    break;
		    //--------------------------------------
		case "devolucion": 
			String idejemplarStr = request.getParameter("idejemplar");
			String idsocioStr = request.getParameter("idsocio");
			
			if (idejemplarStr == null || idsocioStr == null || idejemplarStr.isEmpty() || idsocioStr.isEmpty()) {
			    request.setAttribute("mensaje", "Debe indicar el ID del socio y del ejemplar para registrar la devolución.");
			    request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response);
			    break;
			}
			
			try (java.sql.Connection conn = new conexiones.Conexion().getConexion()) {
			    conn.setAutoCommit(false);
			
			    int idejemplar = Integer.parseInt(idejemplarStr);
			    int idsociodevolucion = Integer.parseInt(idsocioStr);
			
			    // Verificar existencia del préstamo activo
			boolean existe = false;
			try (java.sql.PreparedStatement ps = conn.prepareStatement(
			    "SELECT COUNT(*) FROM PRESTAMO WHERE IDEJEMPLAR=? AND IDSOCIO=? AND (ESTADO IS NULL OR ESTADO <> 'Devuelto')")) {
			    ps.setInt(1, idejemplar);
			    ps.setInt(2, idsociodevolucion);
			    try (java.sql.ResultSet rs = ps.executeQuery()) {
			        if (rs.next() && rs.getInt(1) > 0) existe = true;
			    }
			}
			
			if (!existe) {
			    request.setAttribute("mensaje", "No existe un préstamo activo con esos datos.");
			    request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response);
			    break;
			}
			
			java.time.LocalDate hoy = java.time.LocalDate.now();
			
			// Actualizar préstamo como devuelto
			try (java.sql.PreparedStatement ps = conn.prepareStatement(
			    "UPDATE PRESTAMO SET FECHADEVOLUCIONREAL=?, ESTADO='Devuelto' WHERE IDEJEMPLAR=? AND IDSOCIO=?")) {
			    ps.setDate(1, java.sql.Date.valueOf(hoy));
			    ps.setInt(2, idejemplar);
			    ps.setInt(3, idsociodevolucion);
			    ps.executeUpdate();
			}
			
			// Actualizar estado del ejemplar
			try (java.sql.PreparedStatement ps = conn.prepareStatement(
			    "UPDATE EJEMPLAR SET ESTADO='Disponible' WHERE IDEJEMPLAR=?")) {
			    ps.setInt(1, idejemplar);
			    ps.executeUpdate();
			}
			
			conn.commit();
			
			java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
			request.setAttribute("mensaje", "Devolución registrada correctamente el " + hoy.format(fmt));
			request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response);
			} catch (Exception e) {
			    e.printStackTrace();
			    request.setAttribute("mensaje", "Error al registrar la devolución: " + e.getMessage());
			request.getRequestDispatcher("admin/devolucion.jsp").forward(request, response);
			}
			break;
			
         default:
        	 break;
        	/*
			  * case "listarEjemplares": {                  // lista general
			    DaoEjemplar dao = new DaoEjemplar();
			    request.setAttribute("ejemplares", dao.listado());
			    request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
			    break;
			}
			
			case "listarEjemplaresPorIsbn": {           // lista filtrada por ISBN
			    String isbn = request.getParameter("isbn");
			    DaoEjemplar dao = new DaoEjemplar();
			    request.setAttribute("isbn", isbn);
			    request.setAttribute("ejemplares", dao.listadoPorIsbn(isbn));
			    request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
			    break;
			}
			
			case "nuevoEjemplar": {                     // muestra la vista con el form de alta
			    request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
			    break;
			}
			
			        	 */
					}
				}
			
				/**
				 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	//--------------------------------------Ramas nuevas -------------------------------

		/*String operacion= request.getParameter("operacion");
		
		
	
		switch(operacion) {
		case "guardarEjemplar": {
		    try {
		        String isbn = request.getParameter("isbn");
		        Ejemplar e = new Ejemplar();
		        e.setIsbn(isbn);
		        e.setBaja("N");

		        DaoEjemplar dao = new DaoEjemplar();
		        dao.insertEjemplar(e);  // usa S_EJEMPLAR.NEXTVAL (alineada)

		        request.setAttribute("msg", "Ejemplar insertado. ID: " + e.getIdejemplar());
		        // refrescamos lista (si venías con filtro ISBN, lo respetamos)
		        request.setAttribute("isbn", isbn);
		        request.setAttribute("ejemplares", dao.listadoPorIsbn(isbn));
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);

		    } catch (Exception ex) {
		        request.setAttribute("error", "No se pudo insertar: " + ex.getMessage());
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
		    }
		    break;
		}

		case "bajaEjemplar": {
		    try {
		        int id = Integer.parseInt(request.getParameter("idejemplar"));
		        DaoEjemplar dao = new DaoEjemplar();
		        int filas = dao.marcarBaja(id);
		        request.setAttribute("msg", filas == 1 ? "Ejemplar dado de baja." : "No se pudo dar de baja.");
		        request.setAttribute("ejemplares", dao.listadoEjemplares());
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
		    } catch (Exception ex) {
		        request.setAttribute("error", ex.getMessage());
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
		    }
		    break;
		}

		case "altaEjemplar": {
		    try {
		        int id = Integer.parseInt(request.getParameter("idejemplar"));
		        DaoEjemplar dao = new DaoEjemplar();
		        int filas = dao.quitarBaja(id);
		        request.setAttribute("msg", filas == 1 ? "Ejemplar reactivado." : "No se pudo reactivar.");
		        request.setAttribute("ejemplares", dao.listadoEjemplares());
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
		    } catch (Exception ex) {
		        request.setAttribute("error", ex.getMessage());
		        request.getRequestDispatcher("/admin_ejemplares.jsp").forward(request, response);
		    }
		    break;
		}

		
		}*/
	}

}
