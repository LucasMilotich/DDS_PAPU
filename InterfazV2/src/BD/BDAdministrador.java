package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.aria.client.State;
import com.vaadin.ui.Notification;

import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;
import Logica.Rechazado;

public class BDAdministrador extends BDConnection {
	 public BDAdministrador() throws SQLException{
		 super.getConnection();
		 
	 }
	 
	
	public void insertarRechazado(Integer Dnijugador,Integer DniProponedor,String motivo){
		 
		 Statement stmt2 = null;
		 String query2 = "INSERT INTO RECHAZOS (DNIPROPUESTO,DNIPROPONEDOR,MOTIVO)"
		 		+ "VALUES ('"+ Dnijugador +"','"+ DniProponedor +"','" + motivo + "')";
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
					} 
			        }
			    }
}
	 
	 public List<Rechazado> getJugadoresRechazados(){
		 BDJugador bdjugador = null;
		 
		 try {
				
			 bdjugador = new BDJugador();
			 bdjugador.getConnection();
			 
		 } catch (SQLException e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 
		 BDPartido bdPartido = null;
		 
		 try {
				
			 bdPartido = new BDPartido();
			 bdPartido.getConnection();
			 
		 } catch (SQLException e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 
		 List<Rechazado> rechazados = new ArrayList<Rechazado>();
		 Statement stmt = null;
		 ResultSet rs;
		 String query = "SELECT * FROM RECHAZOS";
		 try {
			    stmt = this.conn.createStatement();
			    rs = stmt.executeQuery(query);
			    System.out.println(rs);
			    for (int i=1; rs.next(); i++)
			    {
			    	Rechazado rechazado = new Rechazado();
			    	rechazado.setJugadorProponedor(bdjugador.obtenerJugador(rs.getInt("DNIPROPONEDOR")));
			    	rechazado.setJugadorPropuesto(bdjugador.obtenerJugador(rs.getInt("DNIPROPUESTO")));
			    	rechazado.setFecha(rs.getDate("FECHA"));
			    	rechazado.setMotivo(rs.getNString("MOTIVO"));
			    	
			    	rechazados.add(rechazado);
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
		    return rechazados;
	 }
	 
	 public List<Rechazado> getRechazoJugador(Integer dni){
		 BDJugador bdjugador = null;
		 
		 try {
				
			 bdjugador = new BDJugador();
			 bdjugador.getConnection();
			 
		 } catch (SQLException e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 
		 BDPartido bdPartido = null;
		 
		 
		 List<Rechazado> rechazados = new ArrayList<Rechazado>();
		 Statement stmt = null;
		 ResultSet rs;
		 String query = "SELECT * FROM RECHAZOS R,JUGADORES J"
		 		+ "WHERE J.DNI = R.DNIPROPUESTO AND P.ID = R.PARTIDO"
		 		+ " AND R.DNIPROPONEDOR = "+ dni;
		 try {
			    stmt = this.conn.createStatement();
			    rs = stmt.executeQuery(query);
			    System.out.println(rs);
			    for (int i=1; rs.next(); i++)
			    {
			    	Rechazado rechazado = new Rechazado();
			    	rechazado.setJugadorProponedor(bdjugador.obtenerJugador(rs.getInt("DNIPROPONEDOR")));
			    	rechazado.setJugadorPropuesto(bdjugador.obtenerJugador(rs.getInt("DNIPROPUESTO")));
			    	rechazado.setFecha(rs.getDate("FECHA"));
			    	rechazado.setMotivo(rs.getNString("MOTIVO"));
			    	
			    	rechazados.add(rechazado);
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
		    return rechazados;
	 
	 }

	 public void organizarPartido(String nombre,Date fecha, String hora, String lugar){
		 Statement stmt = null;
		 String query = "insert into PARTIDOS (NOMBRE,LUGAR,FECHA)  VALUES ('" 
			+ nombre +"','"+ lugar + "','"+ fecha + "')" ;
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
	 
	 public List<Partido> getListaPartidos(){
		 List<Partido> partidos = new ArrayList<Partido>();
		 Statement stmt = null;
		 String query = "SELECT * FROM PARTIDOS" ;
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
		    return partidos;
	 }
	 
	 public void actualizarInscripcionAceptada(int id){
		 Statement stmt = null;
		 Statement stmt2 = null;
	
		 String query = "UPDATE INSCRIPCIONES SET INSCRIPCIONACEPTADA = 1 WHERE ID = '" 
			+ id +"'" ;
		 String query2 = "UPDATE JUGADORES_PARTIDO SET CONFIRMADO=1 WHERE  PARTIDO = (select partido from inscripciones where id =  "
				 + id + ") and jugadores = (select jugador from inscripciones where id =  "
				 + id + ")";
		
		    try {
		    stmt = this.conn.createStatement();
		    stmt2 = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    ResultSet rs2 = stmt2.executeQuery(query2);
		    System.out.println(rs);
		    
		    }
		    catch(SQLException e){
		    	 System.out.println(e);
		    } finally {
		        if (stmt != null) { try {
					stmt.close();
					stmt2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} }
		    }
	 }

	public List<Jugador> getJugadoresParaAprobar() {
			
			List<Jugador> jugadores = new ArrayList<Jugador>();
			Jugador jugador = null;
			
			Statement stmt = null;
			ResultSet rs;
			
			try {
				this.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		    String query = "select * from jugadoresparaparobar jp,jugadores j where j.dni = jp.jugadores and jp.APROBADO != 1";
		    try {
		    stmt = this.conn.createStatement();
		    rs = stmt.executeQuery(query);
		    
			    for (int i=1; rs.next(); i++)
			    {	
			    	jugador = new Jugador();
			    	jugador.setDNI(rs.getInt("DNI"));
			    	jugador.setEdad(rs.getInt("EDAD"));
			    	jugador.setApellido(rs.getString("APELLIDO"));
			    	jugador.setNombre(rs.getString("NOMBRE"));
			    	//jugador.setNivelDeJuego(rs.getInt("NIVELDEJUEGO"));
			    	//jugador.setListaDeInscripciones(this.obtenerInscriciones(jugador));
			    	//jugador.setAmigos(this.obtenerListaAmigos(jugador));
			    	jugadores.add(jugador);
			    }
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
			
			
			
			return jugadores;
		}


	public void actualizarAprobado(Jugador jugador) {

		Statement stmt = null;
		ResultSet rs;
	    String query = "UPDATE jugadoresparaparobar SET aprobado = 1 where jugadores = " + jugador.getDNI();
	    try {
	    stmt = this.conn.createStatement();
	     rs = stmt.executeQuery(query);
	   
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


	public Partido obtenerPartidoParaAprobar(Integer dni) {
		Partido partido = null;
		Integer idpartido = null;
		Statement stmt = null;
		ResultSet rs;
	    String query = "SELECT PARTIDO FROM jugadoresparaparobar where jugadores = " + dni;
	    try {
	    stmt = this.conn.createStatement();
	    rs = stmt.executeQuery(query);
	    
	    for (int i=1; rs.next(); i++){ 
	    	idpartido = rs.getInt("PARTIDO");
	    }
	    
	    BDPartido bdPartido = null;
		 
		 try {
				
			 bdPartido = new BDPartido();
			 bdPartido.getConnection();
			 
			 partido = bdPartido.obtenerPartidoPorId(idpartido);
		 } catch (SQLException e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 
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
		return partido;
	}


	public Integer obtenerDNIProponedor(Integer dni) {
		//Jugador jugadorProponedor = null;
		Integer dniProponedor = null;
		Statement st = null;
		String query = "SELECT DISTINCT JUGADOR_PROPONEDOR AS PROP FROM jugadoresparaparobar where jugadores = " + dni;		
	    try {
		
	    st = this.conn.createStatement();
	    ResultSet r = st.executeQuery(query);
	    
	    for (int i=1; r.next(); i++)
	    {	
	    	dniProponedor = r.getInt("PROP");
	    }
	    
	    } catch(SQLException e){
	    	 System.out.println(e);
	    } finally {
	        if (st != null) { try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
	    }
		return dniProponedor;
	}


	public void borrarJugadorParaArpobar(Integer dNI) {
		
		Statement stmt = null;
		ResultSet rs;
	    String query = "DELETE FROM jugadoresparaparobar where jugadores = " + dNI;
	    try {
	    stmt = this.conn.createStatement();
	     rs = stmt.executeQuery(query);
	   
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


	public Rechazado getJugadorRechazado(Integer dniPropuesto, Integer dniProponedor) {
		BDJugador bdjugador = null;
		Rechazado rechazado = new Rechazado();
		 try {
				
			 bdjugador = new BDJugador();
			 bdjugador.getConnection();
			 
		 } catch (SQLException e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		 }
		 
		 Statement stmt = null;
		 ResultSet rs;
		 String query = "SELECT * FROM RECHAZOS R,JUGADORES J"
		 		+ "WHERE J.DNI = R.DNIPROPUESTO"
		 		+ " AND R.DNIPROPONEDOR = "+ dniProponedor + ""
		 				+ "AND R.DNIPROPUESTO = " + dniPropuesto;
		 try {
			    stmt = this.conn.createStatement();
			    rs = stmt.executeQuery(query);
			    System.out.println(rs);
			
			    	
			    	rechazado.setJugadorProponedor(bdjugador.obtenerJugador(rs.getInt("DNIPROPONEDOR")));
			    	rechazado.setJugadorPropuesto(bdjugador.obtenerJugador(rs.getInt("DNIPROPUESTO")));
			    	rechazado.setFecha(rs.getDate("FECHA"));
			    	rechazado.setMotivo(rs.getNString("MOTIVO"));
			    	
			    	
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
			return rechazado;
	}
}	 