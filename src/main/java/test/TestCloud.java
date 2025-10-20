package test;

import java.sql.Connection;
import java.sql.SQLException;

import conexiones.ConexionCloud;

public class TestCloud{
	
	public static void main(String[] args) {
		ConexionCloud conexion= new ConexionCloud();
		Connection con = null;
		try {
			con=conexion.getConexion();
			con.close();
		} catch (SQLException e) {
			System.out.println("Tratamiento del error");
			System.out.println(e.toString());
		}
		
	}
}