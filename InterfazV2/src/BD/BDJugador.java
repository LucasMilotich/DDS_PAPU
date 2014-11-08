package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import Logica.Amigo;
import Logica.Inscripcion;
import Logica.InscripcionCondicional;
import Logica.InscripcionEstandar;
import Logica.InscripcionSolidaria;
import Logica.Jugador;
import Logica.Partido;
import Logica.Penalizacion;

public class BDJugador extends BDConnection
{
	
	/* public BDJugador() throws SQLException{
		 super.getConnection();
		 
	 }*/
	
	public void crearJugador(Integer DNI, String nombre, String apellido, int edad, Date fechaNacimiento){
		Statement stmt1 = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int dia = fechaNacimiento.getDate();
		int mes = fechaNacimiento.getMonth() + 1;
		int anio = fechaNacimiento.getYear() + 1900;
		
		String query1 = "SELECT COUNT(*) AS count FROM JUGADORES WHERE DNI = "+ DNI;
		try{
			stmt1 = this.conn.createStatement();
			rs = stmt1.executeQuery(query1);
			rs.next();
			if(rs.getInt("count") != 0){
				Notification.show("Un jugador con el mismo DNI ya existe en la Base de Datos",Type.ERROR_MESSAGE);
			}else{
				String query = "insert into JUGADORES (DNI,NOMBRE,APELLIDO,EDAD,FECHANACIMIENTO,TUVOINFRACCIONES)  VALUES ('" 
						+ DNI + "','"+ nombre +"','"+ apellido   + "','" + edad + "', "
				+ "to_date('"
				+ dia
				+ "/"
				+ mes
				+ "/"
				+ anio
				+ "','dd/mm/yyyy'), '" + "NO" + "')" ;
			    try {
				   // stmt = this.conn.createStatement();
				    rs = stmt1.executeQuery(query);
				    System.out.println(rs);
			    }catch(SQLException e){
			    	System.out.println(e);
			    }finally {
			        if (stmt != null) {
			        	try {
			        		stmt.close();
			        	}catch (SQLException e) {
			        		e.printStackTrace();
			        	} 
			        }
			    }
			    Notification.show("Se ha creado el jugador "+nombre+" con DNI "+DNI);
		    }
		}catch(SQLException e){
			System.out.println(e);
		}
		
	}

	public List<Amigo> obtenerListaAmigos(Jugador jugador){
		List<Amigo> amigos = new ArrayList<Amigo>();
		 ResultSet rs;
		Statement stmt = null;
	    String query = "select * from amigos where jugador=" + jugador.getDNI()   ;
	    try {
	    stmt = this.conn.createStatement();
	    rs = stmt.executeQuery(query);
	    System.out.println(rs);
	    for (int i=1; rs.next(); i++)
	    {
	    	Amigo amigo = new Amigo();
	    	amigo.setJugador(jugador);
	    	amigo.setAmigo(this.obtenerJugador(rs.getInt("AMIGO")));	
	    	
	    	amigos.add(amigo);
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
		
	  
	    
		return amigos;
	}
	
	public Jugador obtenerJugador(Integer DNI){
		Jugador jugador = null;
		Statement stmt = null;
		ResultSet rs;
	    String query = "select * from jugadores where DNI=" + DNI   ;
	    try {
	    stmt = this.conn.createStatement();
	     rs = stmt.executeQuery(query);
	     
		    for (int i=1; rs.next(); i++)
		    {	jugador = new Jugador();
		    	jugador.setDNI(DNI);
		    	jugador.setEdad(rs.getInt("EDAD"));
		    	jugador.setApellido(rs.getString("APELLIDO"));
		    	jugador.setNombre(rs.getString("NOMBRE"));
		    	jugador.setNivelDeJuego(rs.getInt("NIVELDEJUEGO"));
		    	jugador.setListaDeInscripciones(this.obtenerInscriciones(jugador));
		    	jugador.setAmigos(this.obtenerListaAmigos(jugador));
		    	jugador.setPenalizaciones(this.obtenerPenalizaciones(jugador));
		    	jugador.setPromedioUltimoPartido(rs.getDouble("PROMEDIOULTIMOPARTIDO"));
		    	jugador.setTuvoInfracciones(rs.getString("TUVOINFRACCIONES"));
		    	jugador.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
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
		
		
		
		return jugador;
		
	}
	
	public List<Jugador> obtenerJugadoresSeleccionadosSinListasDeUnPartido(Partido partido){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		
		
		Statement stmt = null;
		ResultSet rs;
		BDPartido bdPartido = new BDPartido();
		try {
			bdPartido.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    String query = "select * from jugadores where DNI in (select JUGADORES from jugadores_partido where partido=" +  
	    bdPartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha())+ " and confirmado= 1)";  
	    try {
	    stmt = this.conn.createStatement();
	     rs = stmt.executeQuery(query);
	   
		    for (int i=1; rs.next(); i++)
		    {	
		    	Jugador jugador = new Jugador();
		    	jugador.setDNI(rs.getInt("DNI"));
		    	jugador.setEdad(rs.getInt("EDAD"));
		    	jugador.setApellido(rs.getString("APELLIDO"));
		    	jugador.setNombre(rs.getString("NOMBRE"));
		    	jugador.setNivelDeJuego(rs.getInt("NIVELDEJUEGO"));
		    	jugador.setPromedioUltimoPartido(rs.getDouble("PROMEDIOULTIMOPARTIDO"));
		    	jugador.setTuvoInfracciones(rs.getString("TUVOINFRACCIONES"));
		    	jugador.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
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

	public List<Inscripcion> obtenerInscriciones(Jugador jugador){
	
		BDInscripciones bd;
		try {
			bd = new BDInscripciones();
			bd.getConnection();
			return bd.obtenerInscripcionPorJugador(jugador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	
	
	}

	public void crearJugadorAmigo(Integer DNI, Integer DNIAmigo, Partido partido){
		Statement stmt = null;
	    String query = "insert into AMIGOS (JUGADOR, AMIGO)  VALUES ('" 
		+ DNI + "','"+ DNIAmigo + "')" ;
	    this.crearJugadorParaAprobar(DNIAmigo, partido);
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
	
	public void crearJugadorParaAprobar(Integer DNI, Partido partido){
		BDPartido bdpartido;
		Integer idPartido = null;
		
		try {
			
			bdpartido = new BDPartido();
			bdpartido.getConnection();
		idPartido=	bdpartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Statement stmt = null;
	    String query = "insert into JUGADORESPARAPAROBAR (JUGADORES, APROBADO, PARTIDO)  VALUES ('" 
		+ DNI + "','"+ 0 + "','"+ idPartido +"')";
	    
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
	
	public void crearInscripcion(Integer DNI, Partido partido, Inscripcion inscripcion){
		BDPartido bdpartido;
		Integer idPartido = null;
		try {
			
			bdpartido = new BDPartido();
			bdpartido.getConnection();
		idPartido=	bdpartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Statement stmt = null;
		int tipo = 9;
		if(inscripcion instanceof InscripcionEstandar){
			tipo = 1;
		}
		if(inscripcion instanceof InscripcionSolidaria){
			tipo = 2;
		}
		if(inscripcion instanceof InscripcionCondicional){
			tipo = 3;
		}
		Integer bool = 0;
		if(inscripcion.isInscripto()==true){
			bool=1;
		}
	    String query = "insert into INSCRIPCIONES (JUGADOR, INSCRIPCIONACEPTADA, PARTIDO, TIPOINSCRIPCION)  VALUES ('" 
		+ DNI + "','"+ bool +"','"+ idPartido +"','"+ tipo + "')" ;
	    this.crearInscripcionJugPar(DNI, idPartido);
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
	
	public void crearInscripcionJugPar(Integer DNI, Integer partido){
		Statement stmt = null;
	    String query = "insert into JUGADORES_PARTIDO (JUGADORES, PARTIDO,CONFIRMADO)  VALUES ('" 
		+ DNI +"','"+ partido + "',0)" ;
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
	
	public void borrarJugador(Jugador jugador){
		Statement stmt = null;
	    String query = "delete from JUGADORES where DNI=" + jugador.getDNI();
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
	
	public void darDeBaja(Jugador jugador){
		Statement stmt = null;
	    String query = "delete from JUGADORES_PARTIDO where DNI=" + jugador.getDNI() ;
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

	public void crearCalificacion(Integer DNI, Integer DNICalificado, Partido partido, int puntaje){
		Statement stmt = null;
	    String query = "insert into CALIFICACIONES (JUGADORCALIFICADOR, JUGADORCALIFICADO, PUNTAJE, PARTIDO)  VALUES ('" 
		+ DNI + "','"+ DNICalificado +"','"+ puntaje   + "','" + partido + "')" ;
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

	public void setearNivelJuego(Jugador jugador, int nivel){
	 	Statement stmt = null;
		String query = "update JUGADORES set NIVELDEJUEGO="+ nivel + "where DNI=" + jugador.getDNI();
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
	
	public void crearReemplazo(Jugador jugador, Jugador reemplazo){
		Statement stmt = null;
		String query = "insert into REEMPLAZOS (JUGADOR, REEMPLAZO) VALUES ('"+ jugador.getDNI() + "','"+ reemplazo.getDNI() + "')";
		this.setearReemplazoEnJugadores(jugador, reemplazo);
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

	public void setearReemplazoEnJugadores(Jugador jugador, Jugador reemplazo){
		Statement stmt = null;
		String query = "update JUGADORES set REEMPLAZO="+ reemplazo.getDNI() + "where DNI=" + jugador.getDNI();
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
	
	public List<Jugador> obtenerJugadores(){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		Jugador jugador = null;
		
		Statement stmt = null;
		ResultSet rs;
	    String query = "select * from jugadores"   ;
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
		    	jugador.setNivelDeJuego(rs.getInt("NIVELDEJUEGO"));
		    	jugador.setListaDeInscripciones(this.obtenerInscriciones(jugador));
		    	jugador.setAmigos(this.obtenerListaAmigos(jugador));
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
	
	public void cambiarNivelDeJuego(Jugador jugador, int nota){
		Statement stmt = null;
	    String query = "update jugadores set niveldejuego = " + nota + " where DNI = "  + jugador.getDNI();
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
	public List<Penalizacion> obtenerPenalizaciones(Jugador jugador){
		List<Penalizacion> penalizaciones = new ArrayList<Penalizacion>();
		Statement stmt = null;
	    String query = "select * from penalizaciones where jugador= " + jugador.getDNI();
	    try {
		    stmt = this.conn.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    BDPartido bd = new BDPartido();
		    bd.getConnection();
		    
		    System.out.println(rs);
		    for (int i=1; rs.next(); i++){
		    	Penalizacion penalizacion = new Penalizacion();
		    	penalizacion.setJugador(jugador);
		    	penalizacion.setMotivo(rs.getString("MOTIVO"));
		    	penalizacion.setPartido(bd.obtenerPartidoPorId(rs.getInt("PARTIDO")));
		    	penalizaciones.add(penalizacion);
		    	
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
		
		return penalizaciones;
		
	}

	public void crearJugadorParaAprobar(Jugador jugador, Partido partido, Integer DNIProponedor){
		BDPartido bdpartido;
		Integer idPartido = null;
		
		int dia = jugador.getFechaNacimiento().getDate();
		int mes = jugador.getFechaNacimiento().getMonth() + 1;
		int anio = jugador.getFechaNacimiento().getYear() + 1900;
		
		try {
			
			bdpartido = new BDPartido();
			bdpartido.getConnection();
		idPartido=	bdpartido.obtenerIDPartido(partido.getNombre(), partido.getLugar(), partido.getFecha());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Statement stmt = null;
	    String query = "insert into JUGADORESPARAPAROBAR (JUGADORES, APROBADO, PARTIDO, JUGADOR_PROPONEDOR, NOMBRE, APELLIDO, EDAD, FECHANACIMIENTO)  VALUES (" 
		+ jugador.getDNI() + ",'"+ 0 + "',"+ idPartido +","+ DNIProponedor +" ,'"+ jugador.getNombre() + "', '"+ jugador.getApellido() + "', " + jugador.getEdad() + ", "
				+ "to_date('"
				+ dia
				+ "/"
				+ mes
				+ "/"
				+ anio
				+ "','dd/mm/yyyy'))";
	    
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
	
	public void actualizarTuvoInfracciones(Integer dni){
	 	Statement stmt = null;
		String query = "update JUGADORES set TUVOINFRACCIONES = '"+ "SI" + "' where DNI=" + dni;
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
}
