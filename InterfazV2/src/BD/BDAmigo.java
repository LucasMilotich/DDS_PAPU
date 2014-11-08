package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Logica.Jugador;

public class BDAmigo extends BDConnection {

	public void agregarAmigo(Jugador jugador, Jugador amigo){
		
		 Statement stmt = null;
		 String query=null;
		
			  query ="INSERT INTO AMIGOS (JUGADOR,AMIGO) values (" + jugador.getDNI() + "," + amigo.getDNI() + ")" ;	
		
		 
		
				 
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
	public void borrarAmigo(Jugador jugador, Jugador amigo){
		
		 Statement stmt = null;
		 String query=null;
		
			  query ="DELETE FROM AMIGOS where jugador = " + jugador.getDNI() + "and amigo = " + amigo.getDNI() ;	
		
		 
		
				 
		    try {
			    stmt = this.conn.createStatement();
			    ResultSet rs = stmt.executeQuery(query);
			    System.out.println(rs);
		    }catch(SQLException e){
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
	}
	

