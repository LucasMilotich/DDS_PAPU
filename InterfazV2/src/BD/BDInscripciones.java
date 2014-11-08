package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Logica.Inscripcion;
import Logica.InscripcionCondicional;
import Logica.InscripcionEstandar;
import Logica.InscripcionSolidaria;
import Logica.Jugador;

public class BDInscripciones extends BDConnection {
	/* public BDInscripciones() throws SQLException{
		 super.getConnection();
		 
	 }*/

	public int getID(Integer dni, String nombrePartido, String lugar, Date fecha){
		 Statement stmt = null;
		 String query = null;
		 BDPartido bdPartido = new BDPartido();
		 try {
			bdPartido.getConnection();
			  query = "select id from inscripciones where jugador = "+ dni +
					 " and partido = "+ bdPartido.obtenerIDPartido(nombrePartido, lugar, fecha) ;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		
		 
		 try {
			    stmt = this.conn.createStatement();
			    ResultSet rs = stmt.executeQuery(query);
			    System.out.println(rs);
			    rs.next();
			    return rs.getInt("ID");
			    }
			    catch(SQLException e){
			    	 System.out.println(e);
			    	 return -1;
			    } finally {
			        if (stmt != null) { 
			        	try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        	}
			    }

		
	 }
	 
	 public List<Inscripcion> obtenerInscripcionPorJugador(Jugador jugador){
		 	Integer DNI = jugador.getDNI();
		 	Inscripcion inscripcion = null;
		 	List<Inscripcion> inscripciones= new ArrayList<Inscripcion>();
			Statement stmt = null;
			ResultSet rs;
		    String query = "select * from inscripciones where jugador=" + DNI   ;
		    try {
		    stmt = this.conn.createStatement();
		     rs = stmt.executeQuery(query);
		     	//tipoINS = 1 -->  std ; 2 --> solidaria ; 3 --> condicional;
		     for (int i=1; rs.next(); i++){
			    
		     if(rs.getInt("TIPOINSCRIPCION")==1){
		    	 inscripcion = new InscripcionEstandar();
		     }else if(rs.getInt("TIPOINSCRIPCION")==2){
		    	 inscripcion = new InscripcionSolidaria();
		     }else if(rs.getInt("TIPOINSCRIPCION")==3){
		    	 inscripcion = new InscripcionCondicional();
		     }
		     BDJugador bdJugador = new BDJugador();
		     bdJugador.getConnection();
		     BDPartido bdPartido = new BDPartido();
		     bdPartido.getConnection();
		     
		     inscripcion.setJugador(jugador);
		     inscripcion.setInscripto(rs.getBoolean("INSCRIPCIONACEPTADA"));
		     inscripcion.setPartido(bdPartido.obtenerPartidoPorId(rs.getInt("PARTIDO")));
		     inscripciones.add(inscripcion);
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
		 
		 
		return inscripciones;
		 
	 }
	 
	 public Inscripcion obtenerInscripcionPorPartido(String nombre, String lugar, Date fecha){
		 Inscripcion inscripcion=null;;
		 
		 
		 return inscripcion;
	 }
	 

	 public Inscripcion obtenerInscripcionPorID(Integer ID){
		Inscripcion inscripcion=null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from inscripciones where ID=" + ID ;
		
		try {
		    stmt = this.conn.createStatement();
		     rs = stmt.executeQuery(query);
		     if (rs.next()){
		    	 
			    BDJugador bdJugador = new BDJugador();
			    bdJugador.getConnection();
			    BDPartido bdPartido = new BDPartido();
			    bdPartido.getConnection(); 
			    if(rs.getInt("TIPOINSCRIPCION")==1){
			    	 inscripcion = new InscripcionEstandar();
			     }else if(rs.getInt("TIPOINSCRIPCION")==2){
			    	 inscripcion = new InscripcionSolidaria();
			     }else if(rs.getInt("TIPOINSCRIPCION")==3){
			    	 inscripcion = new InscripcionCondicional();
			     }
		    	
		    	inscripcion.setId(rs.getInt("ID"));
		    	inscripcion.setInscripto(rs.getBoolean("INSCRIPCIONACEPTADA"));
		    	inscripcion.setJugador(bdJugador.obtenerJugador(rs.getInt("JUGADOR")));
		    	inscripcion.setPartido(bdPartido.obtenerPartidoPorId(rs.getInt("PARTIDO")));
		    	
			    
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
		 
		 return inscripcion;
	 }

	 
	 public void BorrarInscripcion(Inscripcion inscripcion){
		 BDPartido bdPartido;
		 int id = 0 ;
		try {
			bdPartido = new BDPartido();
			bdPartido.getConnection();
			  id = bdPartido.obtenerIDPartido(inscripcion.getPartido().getNombre(), inscripcion.getPartido().getLugar(), inscripcion.getPartido().getFecha());
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 Statement stmt = null;
			ResultSet rs;
		    String query = "delete  from inscripciones where jugador=" + inscripcion.getJugador().getDNI() 
		    		+ " and partido = " + id ;
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

	 public Integer obtenerInscripcionIdPorPartidoYDni(Integer dni){
		 
		 Integer idInscripcion = null;
		// Inscripcion inscripcion=null;
		 
			
				Statement stmt = null;
				ResultSet rs;
			    String query = "SELECT DISTINCT i.ID FROM jugadoresparaparobar jp,inscripciones i where jp.jugador_proponedor = " + dni + " AND i.jugador = " + dni + " AND "
			    		+ " jp.partido = i.partido AND jp.jugador_proponedor = i.jugador";
			    try {
			    this.getConnection();
			    stmt = this.conn.createStatement();
			    rs = stmt.executeQuery(query);
			    
			    for (int i=1; rs.next(); i++){ 
			    	idInscripcion = rs.getInt("ID");
			    }
			   
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
		        if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} }
		    } 
		return idInscripcion;
	 }
}
