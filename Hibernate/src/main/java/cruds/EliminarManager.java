package cruds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class EliminarManager {

	public static boolean eliminar(int tabla) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			
			if (tabla == 0) {
				session.createNativeQuery("DROP TABLE IF EXISTS Compras").executeUpdate();
				session.createNativeQuery("DROP TABLE IF EXISTS Player").executeUpdate();
				session.createNativeQuery("DROP TABLE IF EXISTS Games").executeUpdate();
			} else {
				 String nombreTabla = "";
				 switch (tabla) {
					case 1: nombreTabla = "Player"; break;
					case 2: nombreTabla = "Compras"; break;
					case 3: nombreTabla = "Games"; break;
				 }
				 if (!nombreTabla.isEmpty()) {
					 session.createNativeQuery("DROP TABLE IF EXISTS " + nombreTabla).executeUpdate();
				 }
			}
			
			tx.commit();
			return false;
		} catch (Exception e) { 
			if (tx != null) tx.rollback();
			return true; 
		}
	}
}