package crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Falta evitar crear tablas si las tablas de las que depende no están creadas
public class CrearManager {
	
	/* "tablas" puede valer:
	 * 0 -> Todas las tablas
	 * 1 -> Player
	 * 2 -> Compras
	 * 3 -> Games
	 */
	public static boolean crearTablas(int tablas, Connection conn) {
		
		boolean error = false;
		
		String player =
		        "CREATE TABLE IF NOT EXISTS Player (" +
		        " idPlayer INT PRIMARY KEY," +
		        " Nick VARCHAR(45)," +
		        " password VARCHAR(128)," +
		        " email VARCHAR(100)" +
		        ")";

		String games =
		        "CREATE TABLE IF NOT EXISTS Games (" +
		        " idGames INT PRIMARY KEY," +
		        " Nombre VARCHAR(45)," +
		        " tiempoJugado TIME" +
		        ")";

		String compras =
		        "CREATE TABLE IF NOT EXISTS Compras (" +
		        " idCompra INT PRIMARY KEY," +
		        " idPlayer INT," +
		        " idGames INT," +
		        " Cosa VARCHAR(25)," +
		        " Precio DECIMAL(6, 2)," +
		        " FechaCompra DATE," +
		        " CONSTRAINT FK_idPlayer_Player FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)," +
		        " CONSTRAINT FK_idGames_Games FOREIGN KEY (idGames) REFERENCES Games(idGames)" +
		        ")";

		
		try {
			
			Statement stmt = conn.createStatement();
			
			switch (tablas) {
			
			case 0: {
				
				stmt.executeUpdate(player);
				stmt.executeUpdate(games);
				stmt.executeUpdate(compras);
				break;
				
			}
			
			case 1: {
				
				stmt.executeUpdate(player);
				break;
				
			} case 2: {
				
				Statement consulta = conn.createStatement();
				ResultSet rs = consulta.executeQuery(
					    "SELECT table_name FROM information_schema.tables " +
					    	    "WHERE table_schema = DATABASE() " +
					    	    "AND table_name IN ('Player', 'Games')"
				);

				while (rs.next()) {
				    String tabla = rs.getString("table_name");
				    if ("Player".equals(tabla)) error = true;
				    if ("Games".equals(tabla)) error = true;
				}

				
				stmt.executeUpdate(compras);
				break;
				
			} case 3: {
				
				stmt.executeUpdate(games);
				break;
				
			} default: {
				
				error = true;
				break;
				
			}
			
			}
			
		} catch (SQLException e) {
			
			System.out.println(e);
			error = true;
			
		}
		
		return error;
		

		
	}

}
