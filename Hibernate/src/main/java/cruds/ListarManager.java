package cruds;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import clases.*; // Asegúrate de que coincida con tu paquete real
import java.util.List;

public class ListarManager {

	public static String listar(String tabla, String campo, int comparacion, String valor) {
		StringBuilder resultado = new StringBuilder();
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			
			String operador;
			
			String hql = "FROM " + tabla; 
			
			// Construcción dinámica de la query
			if (campo != null && !campo.isEmpty()) {
				
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
				
				// Usamos :valor como marcador de posición (parámetro seguro)
				hql += " WHERE " + campo + " " + operador + " :valor";
			}

			Query<?> query;
			if (tabla.equals("Player")) query = session.createQuery(hql, Player.class);
			else if (tabla.equals("Games")) query = session.createQuery(hql, Games.class);
			else query = session.createQuery(hql, Compras.class);

			// Asignación de parámetros segura
			if (campo != null && !campo.isEmpty()) {
				setParametero(query, "valor", valor);
			}

			List<?> lista = query.list();
			if (lista.isEmpty()) return "No hay resultados.";
			
			for (Object o : lista) {
				resultado.append(o.toString()).append("\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error al listar (verifica que el nombre del campo sea correcto).";
		}
		return resultado.toString();
	}

	// Método auxiliar para detectar tipo de dato (Int, Double, Date o String)
	private static void setParametero(Query<?> query, String nombre, String valor) {
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