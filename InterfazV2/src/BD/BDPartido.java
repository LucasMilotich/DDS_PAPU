package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Notification;

import Logica.Calificacion;
import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;

public class BDPartido extends BDConnection {
	/*
	 * public BDPartido() throws SQLException{ super.getConnection();
	 * 
	 * }
	 */
	public Partido obtenerPartidoPorId(Integer ID) {
		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where ID=" + ID;

		try {
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
			//	partido.setListaDeJugadores(this
				//		.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				/*partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.getListaCalificaciones();*/
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partido;
	}

	public Partido obtenerPartido(String nombre, String lugar, Date fecha) {
		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
	/*	String query = "select * from partidos where nombre='" + nombre
				+ "' and lugar = '" + lugar + "' and fecha ='" + fecha + "'";*/
		int dia = 0;
		int mes = 0;
		int anio = 0;
		if (fecha != null) {
			dia = fecha.getDate();
			mes = fecha.getMonth() + 1;
			anio = fecha.getYear() + 1900;

		}
		
		String query = "select * from partidos where nombre = '" + nombre
				+ "' and lugar = '" + lugar + "' and fecha = to_date('" + dia
				+ "/" + mes + "/" + anio + "','dd/mm/yyyy')";
		
		try {
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			rs.next();
			partido = new Partido();
			partido.setFecha(rs.getDate("FECHA"));
			partido.setLugar(rs.getString("LUGAR"));
			partido.setNombre(rs.getString("NOMBRE"));
			partido.setHora(rs.getString("HORA"));
			partido.actualizarCerrado(rs.getBoolean("CERRADO"));
			partido.setTerminado(rs.getBoolean("TERMINADO"));
			partido.setEmpezado(rs.getBoolean("EMPEZADO"));
			partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
			partido.setListaDeJugadores(this.obtenerJugadoresDePartido(partido));
			
			// ver donde mierda guardar los jug seleccionados, si en una tabla o
			// en jugadores_partido pero, con los q no juegan, deleteados
			// FALTA HACER
			partido.setJugadoresSeleccionados(this
					.obtenerJugadoresSeleccionadosDe(partido));

			// ya esta hecho
			partido.setListaDeInscripciones(this
					.obtenerInscripcionesDePartido(partido));

			// FALTAN HACER
			partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partido;
	}

	public List<Inscripcion> obtenerListaInscripciones(Integer id) {
		List<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
		Statement stmt = null;
		ResultSet rs;
		String query = "";

		try {
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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
		return inscripciones;
	}

	public Integer obtenerIDPartido(String nombre, String lugar, Date fecha) {
		int dia = 0;
		int mes = 0;
		int anio = 0;
		if (fecha != null) {
			dia = fecha.getDate();
			mes = fecha.getMonth() + 1;
			anio = fecha.getYear() + 1900;

		}
		Integer id = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where nombre = '" + nombre
				+ "' and lugar = '" + lugar + "' and fecha = to_date('" + dia
				+ "/" + mes + "/" + anio + "','dd/mm/yyyy')";

		try {
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
				id = rs.getInt("ID");
			

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return id;
	}

	public void crearPartido(String nombre, Date fecha, String hora,
			String lugar) {

		int dia = fecha.getDate();
		int mes = fecha.getMonth() + 1;
		int anio = fecha.getYear() + 1900;

		Statement stmt = null;
		@SuppressWarnings("deprecation")
		String query = "insert into PARTIDOS (CONFIRMADO, NOMBRE, LUGAR, CERRADO, EMPEZADO, TERMINADO, FECHA,HORA)  VALUES ('"
				+ 0
				+ "','"
				+ nombre
				+ "','"
				+ lugar
				+ "','"
				+ 0
				+ "','"
				+ 0
				+ "','"
				+ 0
				+ "',"
				+ "to_date('"
				+ dia
				+ "/"
				+ mes
				+ "/"
				+ anio
				+ "','dd/mm/yyyy'),'"
				+ hora + "')";
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

	public List<Partido> obtenerPartidosAbiertos() {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where cerrado=0 and confirmado=0 and empezado=0 and terminado=0";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partido.setHora(rs.getString("HORA"));
				partidos.add(partido);
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
		//		partido.setJugadoresSeleccionados(this
			//			.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}

	public List<Partido> obtenerPartidosCerrados() {

		List<Partido> partidos = new ArrayList<Partido>();
	
		
		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where cerrado= 1 and confirmado = 0 and empezado = 0 and terminado = 0";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partidos.add(partido);
				
				
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}

	public List<Partido> obtenerPartidosConfirmados() {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where cerrado=1  and confirmado= 1 and empezado = 0 and terminado = 0";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partidos.add(partido);
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}

	public List<Partido> obtenerPartidosEmpezados() {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where cerrado=1  and confirmado= 1 and empezado= 1 and empezado = 1 and terminado = 0";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partidos.add(partido);
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}

	public List<Partido> obtenerPartidosTerminado() {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where cerrado=1  and confirmado= 1 and empezado= 1 and terminado= 1";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partidos.add(partido);
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}

	public void cambiarEstCerrado(Partido partido) {

		int id = this.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
				partido.getFecha());

		Statement stmt = null;
		String query = "update PARTIDOS set CERRADO=1 where ID=" + id;
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

	public void cambiarEstEmpezado(Partido partido) {

		int id = this.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
				partido.getFecha());

		Statement stmt = null;
		String query = "update PARTIDOS set EMPEZADO=1 where ID=" + id;
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

	public void cambiarEstTerminado(Partido partido) {

		int id = this.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
				partido.getFecha());

		Statement stmt = null;
		String query = "update PARTIDOS set TERMINADO=1 where ID=" + id;
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

	public void darDeBajaJugador(Partido partido, Jugador jugador) {

		int id = this.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
				partido.getFecha());

		Statement stmt = null;
		String query = "delete from jugadores_partido where partido =" + id
				+ "and jugadores = " + jugador.getDNI();
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

	public List<Inscripcion> obtenerInscripcionesDePartido(Partido partido) {
		List<Inscripcion> listaInscripciones = new ArrayList<Inscripcion>();

		BDInscripciones dbInscripcion = null;

		Statement stmt = null;
		String query = "select * from inscripciones where partido = "
				+ this.obtenerIDPartido(partido.getNombre(),
						partido.getLugar(), partido.getFecha());
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (int i = 1; rs.next(); ++i) {
				dbInscripcion = new BDInscripciones();
				dbInscripcion.getConnection();
				Inscripcion inscrip = dbInscripcion.obtenerInscripcionPorID(rs
						.getInt("ID"));
				listaInscripciones.add(inscrip);

			}
		} catch (SQLException e) {
			System.out.println(e);
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

		return listaInscripciones;
	}

	public List<Jugador> obtenerJugadoresDePartido(Partido partido) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		BDJugador dbJugador = null;

		Statement stmt = null;
		String query = "select * from jugadores_partido where partido = "
				+ this.obtenerIDPartido(partido.getNombre(),
						partido.getLugar(), partido.getFecha());
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (int i = 1; rs.next(); ++i) {
				dbJugador = new BDJugador();
				dbJugador.getConnection();
				Jugador jugador = dbJugador.obtenerJugador(rs
						.getInt("JUGADORES"));
				jugadores.add(jugador);

			}
		} catch (SQLException e) {
			System.out.println(e);
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
		return jugadores;
	}

	public List<Jugador> obtenerJugadoresSeleccionadosDe(Partido partido) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		BDJugador dbJugador = null;

		Statement stmt = null;
		String query = "select * from jugadores_partido where partido = "
				+ this.obtenerIDPartido(partido.getNombre(),
						partido.getLugar(), partido.getFecha())
				+ " and confirmado= 1";
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (int i = 1; rs.next(); ++i) {
				dbJugador = new BDJugador();
				dbJugador.getConnection();
				Jugador jugador = dbJugador.obtenerJugador(rs
						.getInt("JUGADORES"));
				jugadores.add(jugador);

			}
		} catch (SQLException e) {
			System.out.println(e);
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
		return jugadores;
	}

	public void cambiarEstConfirmado(Partido partido) {
		int id = this.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
				partido.getFecha());

		Statement stmt = null;
		String query = "update PARTIDOS set CONFIRMADO=1 where ID=" + id;
		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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
	public List<Calificacion> obtenerCalificacionesPorPartidoID(Partido partido){
		
		List<Calificacion> calificaciones = new ArrayList<Calificacion>();
		BDJugador bd = new BDJugador();
		Statement stmt = null;
		String query = "select * from calificaciones where partido = " + this.obtenerIDPartido(partido.getNombre()
				, partido.getLugar(), partido.getFecha());
		
		try {
			bd.getConnection();
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (int i = 1; rs.next(); ++i) {
				Calificacion calificacion = new Calificacion();
				calificacion.setJugadorCalificado(bd.obtenerJugador(rs.getInt("JUGADORCALIFICADO")));
				calificacion.setJugadorCalificador(bd.obtenerJugador(rs.getInt("JUGADORCALIFICADOR")));
				calificacion.setPartido(partido);
				calificacion.setPuntaje(rs.getInt("PUNTAJE"));
				calificaciones.add(calificacion);
			}
		} catch (SQLException e) {
			System.out.println(e);
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
		
		return calificaciones;
	}
	
	
	public List<Partido> obtenerTodosLosPartidos(){
		
		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partido.setHora(rs.getString("HORA"));
				partidos.add(partido);
				partido.setListaDeJugadores(this.obtenerJugadoresDePartido(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		
		return partidos;
	}
	
	public List<Partido> obtenerPartidosParaGenerar() {

		List<Partido> partidos = new ArrayList<Partido>();

		Partido partido = null;
		Statement stmt = null;
		ResultSet rs;
		String query = "select * from partidos where confirmado=0 and empezado = 0 and terminado = 0";

		try {
			BDCalificacion bdCalifi= new BDCalificacion();
			bdCalifi.getConnection();
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(query);
			for (int i = 1; rs.next(); i++) {
				partido = new Partido();
				partido.setFecha(rs.getDate("FECHA"));
				partido.setLugar(rs.getString("LUGAR"));
				partido.setNombre(rs.getString("NOMBRE"));
				partido.actualizarCerrado(rs.getBoolean("CERRADO"));
				partido.setTerminado(rs.getBoolean("TERMINADO"));
				partido.setEmpezado(rs.getBoolean("EMPEZADO"));
				partido.setHora(rs.getString("HORA"));
				partido.actualizarConfirmado(rs.getBoolean("CONFIRMADO"));
				partidos.add(partido);
				partido.setListaDeJugadores(this
						.obtenerJugadoresDePartido(partido));

				// ver donde mierda guardar los jug seleccionados, si en una
				// tabla o en jugadores_partido pero, con los q no juegan,
				// deleteados
				// FALTA HACER
				partido.setJugadoresSeleccionados(this
						.obtenerJugadoresSeleccionadosDe(partido));

				// ya esta hecho
				partido.setListaDeInscripciones(this
						.obtenerInscripcionesDePartido(partido));

				// FALTAN HACER
				partido.setListaCalificaciones(bdCalifi.obtenerCalificacionesPorPartido(partido));
			}

			System.out.println(rs);

		} catch (SQLException e) {
			System.out.println(e);
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

		return partidos;

	}
	
	
	
}
