package BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Logica.Jugador;
import Logica.Partido;
import Logica.Penalizacion;

public class BDPenalizaciones extends BDConnection {

	public void agregarPenalizacion(String motivo, Jugador jugador,
			Partido partido) {
		Statement stmt = null;
		String query = null;
		BDPartido bd = new BDPartido();
		try {
			bd.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		query = "INSERT INTO penalizaciones (JUGADOR,MOTIVO,PARTIDO) values ("
				+ jugador.getDNI()
				+ ",'"
				+ motivo
				+ "',"
				+ bd.obtenerIDPartido(partido.getNombre(), partido.getLugar(),
						partido.getFecha()) + " )";

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

	public boolean tuvoInfracciones(Jugador jugador) {

		Statement stmt = null;
		String query = null;
		boolean tuvo = false;
		int i = 0;

		query = "select * from penalizaciones where jugador ="
				+ jugador.getDNI();

		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (i = 0; rs.next(); ++i)
				;
			if (i >= 1)
				tuvo= true;

			else
				tuvo= false;
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
		return tuvo;
	}
	
	public List<Penalizacion> obtenerPenalizacionesDeUn(Jugador jugador){
		List<Penalizacion> penalizaciones = new ArrayList<Penalizacion>();
		Statement stmt = null;
		String query = null;
		BDPartido bdPartido = new BDPartido();
		try {
			bdPartido.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		int i = 0;

		query = "select * from penalizaciones where jugador ="
				+ jugador.getDNI();

		try {
			stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs);
			for (i = 0; rs.next(); ++i){
			Penalizacion penalizacion = new Penalizacion();
			penalizacion.setMotivo(rs.getString("MOTIVO"));
			penalizacion.setJugador(jugador);
			penalizacion.setPartido(bdPartido.obtenerPartidoPorId(rs.getInt("PARTIDO")));
			penalizaciones.add(penalizacion);
			
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
		
		return penalizaciones;
		
	}
}
