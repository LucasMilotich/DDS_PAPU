package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.ui.Notification;

import Logica.Equipo;
import Logica.Jugador;
import Logica.Partido;

public class BDEquipo extends BDConnection {

	public void crearEquipo (String nombreEquipo){
		
		Statement stmt = null;
		String query = "INSERT INTO EQUIPOS (NOMBREEQUIPO) VALUES ('" + nombreEquipo + "')";
		
		try {
		    stmt = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    System.out.println(rs);
	     
		    }
		    catch(SQLException e){
		    	 System.out.println(e);
		    } finally {
		        if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} }
		    }
	}
	
	public void cargarJugadorEnJugadoresEquipo(Jugador jugador, Partido partido, Equipo equipo){
		
		BDPartido bdPartido = new BDPartido();
		try {
			bdPartido.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int idPartido = bdPartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha());
		
		Statement stmt = null;
	    String query = "INSERT INTO JUGADORESEQUIPO (EQUIPO, JUGADOR, PARTIDO) VALUES (" 
	    		+ this.obtenerIDEquipo(equipo.getNombre()) + ", " + jugador.getDNI() + ", " + idPartido + ")";
	    try {
	    stmt = this.conn.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    System.out.println(rs);
     
	    }
	    catch(SQLException e){
	    	 System.out.println(e);
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
	}

	public Integer obtenerIDEquipo(String nombreEquipo){
		
		Integer id= null;
		Statement stmt = null;
		ResultSet rs;
	    String query = "SELECT * FROM EQUIPOS WHERE NOMBREEQUIPO = '" + nombreEquipo + "'";    	
	    
	    try {
	    stmt = this.conn.createStatement();
	     rs = stmt.executeQuery(query);
	     if(rs.next())
	     id=rs.getInt("EQUIPO");
	     else Notification.show("error");
		    
	    System.out.println(rs);
     
	    }
	    catch(SQLException e){
	    	 System.out.println(e);
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
		
		return id;
	}
	
}
