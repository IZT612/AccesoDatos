package cruds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import clases.*; // IMPORTANTE: Tu paquete de entidades
import util.HibernateUtil;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class InsertarManager {

	public static boolean insertar(int tabla, List<String> datos) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();

			switch (tabla) {
				case 1: // Player
					Player p = new Player(datos.get(0), datos.get(1), datos.get(2));
					session.persist(p); // 'persist' es la forma moderna de 'save'
					break;
				case 2: // Compras
					Player playerRef = session.get(Player.class, Integer.parseInt(datos.get(0)));
					Games gameRef = session.get(Games.class, Integer.parseInt(datos.get(1)));
					
					if (playerRef == null || gameRef == null) throw new Exception("ID no existe");

					Compras c = new Compras(playerRef, gameRef, datos.get(2), new BigDecimal(datos.get(3)), Date.valueOf(datos.get(4)));
					session.persist(c);
					break;
				case 3: // Games
					Games g = new Games(datos.get(0), Time.valueOf(datos.get(1)));
					session.persist(g);
					break;
			}
			
			tx.commit(); 
			return false;
		} catch (Exception e) {
			if (tx != null) tx.rollback(); 
			e.printStackTrace();
			return true;
		}
	}
}