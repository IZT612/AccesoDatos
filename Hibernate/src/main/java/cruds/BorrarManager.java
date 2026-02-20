package cruds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class BorrarManager {

	public static boolean borrar(int tabla, boolean borrarTodo, String filtro) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			String entidad;
			
			tx = session.beginTransaction();

			if (tabla == 0) {
				// Orden inverso para respetar FKs
				session.createMutationQuery("DELETE FROM Compras").executeUpdate();
				session.createMutationQuery("DELETE FROM Player").executeUpdate();
				session.createMutationQuery("DELETE FROM Games").executeUpdate();
				System.out.println("Base de datos vaciada.");
			} else {
				
				switch (tabla) {
				
					case 1 : {
						
						entidad = "Player";
						break;
					}
					
					case 2 : {
						
						entidad = "Compras";
						break;
					}
					
					case 3 : {
						
						entidad = "Games";
						break;
					}
					
					default : {
						
						entidad = "";
						break;
					}
				
				}
				
				String hql = "DELETE FROM " + entidad;
				if (!borrarTodo && !filtro.isEmpty()) {
					// Aquí el usuario debe escribir la condición completa (ej: id > 5)
					// Hibernate no permite parametrizar nombres de columna en el WHERE fácilmente sin Criteria
					hql += " WHERE " + filtro;
				}
				
				session.createMutationQuery(hql).executeUpdate();
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