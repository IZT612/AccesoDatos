package cruds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import util.HibernateUtil;

public class EditarManager {

	public static boolean editar(String tabla, String campoFiltro, String valorFiltro, 
								 int comparacion, String campoEditar, String nuevoValor) {
		
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			String operador;
			
			String hql = "";
			
			tx = session.beginTransaction();
			
			// Construcción dinámica de la query
			if (campoEditar != null && !campoEditar.isEmpty()) {
				
				switch (comparacion) {
				
				case 2 : {
					operador = "<";
					break;
				}
				
				case 3 : {
					operador = ">";
					break;
				}
				
				default : {
					operador = "=";
					break;
				}
				
				}
				
				hql = "UPDATE " + tabla + " SET " + campoEditar + " = :nuevoVal WHERE " + campoFiltro + " " + operador + " :filtroVal";
				
			}
			
			// HQL con parámetros seguros

			
			MutationQuery query = session.createMutationQuery(hql);
			
			// Usamos el mismo helper para asignar tipos correctos
			setParametero(query, "nuevoVal", nuevoValor);
			setParametero(query, "filtroVal", valorFiltro);
			
			int result = query.executeUpdate();
			System.out.println("Registros actualizados: " + result);

			tx.commit();
			return false;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return true;
		}
	}
	
	private static void setParametero(MutationQuery query, String nombre, String valor) {
		try {
			query.setParameter(nombre, Integer.parseInt(valor));
		} catch (NumberFormatException e1) {
			try {
				query.setParameter(nombre, Double.parseDouble(valor));
			} catch (NumberFormatException e2) {
				if (valor.matches("\\d{4}-\\d{2}-\\d{2}")) {
					query.setParameter(nombre, java.sql.Date.valueOf(valor));
				} else {
					query.setParameter(nombre, valor);
				}
			}
		}
	}
}