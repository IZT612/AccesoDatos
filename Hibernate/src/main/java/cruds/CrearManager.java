package cruds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


/*
 * NOTA DE ARQUITECTURA:
 * Se utiliza SQL NATIVO para cumplir el requisito de creación manual vía menú.
 * Normalmente Hibernate gestionaría esto automáticamente con 'hbm2ddl.auto'.
 */
public class CrearManager {
	
	public static boolean crearTablas(int tablas) {
		
		// Definición SQL Nativa para las tablas
		String player = "CREATE TABLE IF NOT EXISTS Player (idPlayer INT AUTO_INCREMENT PRIMARY KEY, Nick VARCHAR(45), password VARCHAR(128), email VARCHAR(100))";
		String games = "CREATE TABLE IF NOT EXISTS Games (idGames INT AUTO_INCREMENT PRIMARY KEY, Nombre VARCHAR(45), tiempoJugado TIME)";
		String compras = "CREATE TABLE IF NOT EXISTS Compras (idCompra INT AUTO_INCREMENT PRIMARY KEY, idPlayer INT, idGames INT, Cosa VARCHAR(25), Precio DECIMAL(6, 2), FechaCompra DATE, FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer), FOREIGN KEY (idGames) REFERENCES Games(idGames))";
		
		boolean error = false;
		Transaction tx = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
				
			switch (tablas) {
				case 1:
					session.createNativeQuery(player).executeUpdate();
					session.createNativeQuery(games).executeUpdate();
					session.createNativeQuery(compras).executeUpdate();
					break;
				case 2:
					session.createNativeQuery(player).executeUpdate();
					break;
				case 3:
					session.createNativeQuery(compras).executeUpdate();
					break;
				case 4:
					session.createNativeQuery(games).executeUpdate();
					break;
				default:
					error = true;
					break;
			}
			
			tx.commit();
		
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			error = true;
		}
		return error;
	}
}