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
		
		//para validar si ya no existe un equipo con ese nombre
		Statement stmt = null;
		String query = "SELECT * FROM EQUIPOS WHERE NOMBREEQUIPO = '" + nombreEquipo + "'";
		try {
		    stmt = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    System.out.println(rs);
			if (! rs.next()) {
				
				//para cargar equipo en bd
				Statement stmt2 = null;
				String query2 = "INSERT INTO EQUIPOS (NOMBREEQUIPO) VALUES ('" + nombreEquipo + "')";
			
				try {
				    stmt2 = this.conn.createStatement();
				    ResultSet rs2 = stmt2.executeQuery(query2);
			     
				    }
				    catch(SQLException e){
				    	 System.out.println(e);
				    } finally {
				        if (stmt2 != null) { try {
							stmt2.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} }
				    }
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
		String query = "SELECT * FROM JUGADORESEQUIPO WHERE PARTIDO = " 
				+ idPartido + "AND JUGADOR = " + jugador.getDNI();
		try {
		    stmt = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    System.out.println(rs);
			if (rs.next()) {
				//SI YA EXISTE ESE JUGADOR EN ESE EQUIPO Y PARTIDO, LO DELETEO DE LA TABLA Y LUEGO LO INSERTO
				Statement stmt2 = null;
			    String query2 = "DELETE FROM JUGADORESEQUIPO WHERE JUGADOR = " + jugador.getDNI()
			    		+ " AND PARTIDO = " + idPartido;
			    try {
			    stmt2 = this.conn.createStatement();
			    ResultSet rs2 = stmt2.executeQuery(query2);
			    System.out.println(rs2);
		     
			    }
			    catch(SQLException e){
			    	 System.out.println(e);
			    } finally {
			        if (stmt2 != null) { try {
						stmt2.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} }
			    }
			    
			    //INSERTO AL JUGADOR
				Statement stmt3 = null;
			    String query3 = "INSERT INTO JUGADORESEQUIPO (EQUIPO, JUGADOR, PARTIDO) VALUES (" 
			    		+ this.obtenerIDEquipo(equipo.getNombre()) + ", " + jugador.getDNI() + ", " + idPartido + ")";
			    try {
			    stmt3 = this.conn.createStatement();
			    ResultSet rs3 = stmt3.executeQuery(query3);
			    System.out.println(rs3);
		     
			    }
			    catch(SQLException e){
			    	 System.out.println(e);
			    } finally {
			        if (stmt3 != null) { try {
						stmt3.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} }
			    }
			    
			} else {
				//SI NO EXISTE ESE JUGADOR EN ESE EQUIPO Y PARTIDO
				Statement stmt4 = null;
			    String query4 = "INSERT INTO JUGADORESEQUIPO (EQUIPO, JUGADOR, PARTIDO) VALUES (" 
			    		+ this.obtenerIDEquipo(equipo.getNombre()) + ", " + jugador.getDNI() + ", " + idPartido + ")";
			    try {
			    stmt4 = this.conn.createStatement();
			    ResultSet rs4 = stmt4.executeQuery(query4);
			    System.out.println(rs4);
		     
			    }
			    catch(SQLException e){
			    	 System.out.println(e);
			    } finally {
			        if (stmt4 != null) { try {
						stmt4.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} }
			    }
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

