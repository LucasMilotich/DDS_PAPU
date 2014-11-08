package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Logica.Calificacion;
import Logica.Jugador;
import Logica.Partido;

public class BDCalificacion extends BDConnection {

	public void crearCalificacion(Partido partido, Jugador jugadorCalificado, Jugador jugadorCalificador, Integer nota){
		 Statement stmt = null;
		 String query=null;
		 BDPartido bdPartido = new BDPartido();
		 try {
			bdPartido.getConnection();
			  query =	"INSERT INTO CALIFICACIONES (JUGADORCALIFICADO,JUGADORCALIFICADOR,PUNTAJE,PARTIDO) VALUES"
				 		+ "(" + jugadorCalificado.getDNI() + "," + jugadorCalificador.getDNI() + "," + nota + ","
				 		+ bdPartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha()) + ")";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		
				 
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
	
	public List<Calificacion> obtenerCalificacionesPorPartido(Partido partido){
		List<Calificacion> calificaciones = new ArrayList<Calificacion>();
		Statement stmt = null;
		 String query=null;
		 BDPartido bdPartido = new BDPartido();
		 BDJugador bdJugador = new BDJugador();
		 try {
			bdPartido.getConnection();
			bdJugador.getConnection();
			  query =	"select * from calificaciones where PARTIDO = " + 
			bdPartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha());
			  
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		
				 
		    try {
		    stmt = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    System.out.println(rs);
		    for(int i = 1;rs.next();++i){
		    	Calificacion calif = new Calificacion();
		    	calif.setPartido(partido);
		    	calif.setJugadorCalificado(bdJugador.obtenerJugador(rs.getInt("JUGADORCALIFICADO")));
		    	calif.setJugadorCalificador(bdJugador.obtenerJugador(rs.getInt("JUGADORCALIFICADOR")));
		    	calif.setPuntaje(rs.getInt("PUNTAJE"));
		    	calificaciones.add(calif);
		    	
		    }
	     
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
		return calificaciones;
	}

}
