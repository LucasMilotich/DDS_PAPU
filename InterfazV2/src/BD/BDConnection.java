package BD;
import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.vaadin.ui.Notification;

import Logica.Jugador;
import Logica.Partido;

public class BDConnection {
	
	String user = "system";
	String password = "system";
	Connection conn;
	
	public Connection getConnection() throws SQLException {
		

/*		try {
			Class.forName("oracle.jdbc.driver.­OracleDriver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 try {
			Class.forName ("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.user);
	    connectionProps.put("password", this.password);
	    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",connectionProps);
	    //Notification.show("Connected to database");
	    this.conn = conn;
	    return conn;
	}
	
	public void insertarJugador(int DNI,String nombre, String apellido ) throws SQLException{
		  /*Connection conn = null;
		    Properties connectionProps = new Properties();
		    connectionProps.put("user", this.user);
		    connectionProps.put("password", this.password);
		    connectionProps.put("driverClassName", "oracle.jdbc.OracleDriver");
		    conn = DriverManager.getConnection("jdbc:oracle:thin://localhost:1521/xe",connectionProps);
		    Notification.show("Connected to database");
		    this.conn = conn;*/
		    
		Statement stmt = null;
	    String query = "insert into JUGADORES (DNI,NOMBRE,APELLIDO)  VALUES ('" 
		+ DNI + "','"+ nombre +"','"+ apellido   + "')" ;
	    try {
	    stmt = this.conn.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    System.out.println(rs);
     
	    }
	    catch(SQLException e){
	    	 System.out.println(e);
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
      
	}
}
