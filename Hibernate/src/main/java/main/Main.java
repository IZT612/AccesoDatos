package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import cruds.*;
import util.HibernateUtil;

public class Main {
	
	private static String menu = "Escoja una opción:\n1. Crear tablas.\n2. Insertar.\n3. Listar.\n4. Modificar.\n5. Borrar.\n6. Eliminar tablas.\n0. Salir.";
	
	// private static Connection conn;  <-- ELIMINADO (Hibernate lo gestiona internamente)
	
	private static Scanner sc = new Scanner(System.in);
	
	private static int opcion;

		public static void main(String[] args) {

			
			System.out.println("--- GESTIÓN CON HIBERNATE ---");
			System.out.println(menu);
			
			opcion = sc.nextInt();
			sc.nextLine(); 
			
			while(opcion != 0) {
				
				switch (opcion) {
				
				// --- CASO 1: CREAR TABLAS ---
				case 1:
					System.out.println("Escoja una opción:\n1. Crear todas.\n2. Crear Player\n3. Crear Compras\n4. Crear Games.\n");
					int opCrear = sc.nextInt();	
					sc.nextLine();
					
					if (CrearManager.crearTablas(opCrear)) {
						System.out.println("Error al crear tablas (o ya existen).");
					} else {
						System.out.println("Operación finalizada.");
					}
					break;
				
				// --- CASO 2: INSERTAR ---
				case 2:
					int tablaIns;
					int iteraciones = 0;
					List<String> datos = new ArrayList<>();
					boolean tablaCorrecta = true;
					
					System.out.println("Introduzca en qué tabla quiere insertar:\n1. Player\n2. Compras.\n3. Games\n");
					tablaIns = sc.nextInt();
					sc.nextLine();

					switch (tablaIns) {
						case 1: // Player
							System.out.println("Introduce en orden: Nick, password, email:\n");
							iteraciones = 3; 
							break;
						case 2: // Compras
							System.out.println("Introduce en orden: idPlayer (existente), idGames (existente), Cosa, Precio, FechaCompra (YYYY-MM-DD):\n");
							iteraciones = 5; 
							break;
						case 3: // Games
							System.out.println("Introduce en orden: Nombre, tiempoJugado (HH:MM:SS):\n");
							iteraciones = 2; 
							break;
						default:
							System.out.println("Opción incorrecta.");
							tablaCorrecta = false;
							break;
					}
					
					if (tablaCorrecta) {
						for (int i = 1; i <= iteraciones; i++) {
							System.out.print("Dato " + i + ": ");
							datos.add(sc.nextLine());
						}
						
						if(InsertarManager.insertar(tablaIns, datos)) {
							System.out.println("Ha ocurrido algún error al insertar.");
						} else {
							System.out.println("Insertado correctamente.");
						}
					}
					break;
					
				// --- CASO 3: LISTAR ---
				case 3:
					String tablaList = "";
					String campoList = "";
					int comparacionList = 0;
					String valorList = "";
					
					System.out.println("¿Qué tabla listar?:\n1. Player\n2. Compras\n3. Games");
					int opList = sc.nextInt();
					sc.nextLine();
					
					switch(opList) {
						case 1: tablaList = "Player"; break;
						case 2: tablaList = "Compras"; break;
						case 3: tablaList = "Games"; break;
						default: System.out.println("Tabla no válida"); break;
					}
					
					if (!tablaList.isEmpty()) {
						System.out.println("¿Filtrar por campo? (Escribe el nombre o 0 para listar todo):");
						System.out.println("(Ejemplos: idPlayer, Nick, Precio, Nombre...)");
						
						String inputCampo = sc.nextLine();
						
						if (!inputCampo.equals("0") && !inputCampo.isEmpty()) {
							campoList = inputCampo;
							
							System.out.println("Comparación:\n1. Igual (=)\n2. Menor (<)\n3. Mayor (>)");
							comparacionList = sc.nextInt();
							sc.nextLine();
							
							System.out.println("Valor a comparar:");
							valorList = sc.nextLine();
						}
						
						System.out.println(ListarManager.listar(tablaList, campoList, comparacionList, valorList));
					}
					break;
				
				// --- CASO 4: MODIFICAR ---
				case 4:
					String tablaEdit = "";
					String campoFiltro = "";
					String valorFiltro = "";
					int comparacionEdit = 0;
					String campoEditar = "";
					String nuevoValor = "";
					
					System.out.println("Tabla para actualizar:\n1. Player\n2. Compras\n3. Games");
					int opEdit = sc.nextInt();
					sc.nextLine();
					
					switch(opEdit) {
						case 1: tablaEdit = "Player"; break;
						case 2: tablaEdit = "Compras"; break;
						case 3: tablaEdit = "Games"; break;
					}

					System.out.println("Introduce el campo por el que quieres FILTRAR (WHERE): (Ej: idPlayer, Nick...)");
					campoFiltro = sc.nextLine();
					
					if (!campoFiltro.isEmpty()) {
						System.out.println("Comparación:\n1. Igual (=)\n2. Menor (<)\n3. Mayor (>)");
						comparacionEdit = sc.nextInt();
						sc.nextLine();
						
						System.out.println("Valor del filtro:");
						valorFiltro = sc.nextLine();
						
						System.out.println("Introduce el campo que quieres EDITAR (SET): (Ej: email, Precio...)");
						campoEditar = sc.nextLine();
						
						System.out.println("Nuevo valor:");
						nuevoValor = sc.nextLine();
						
						boolean error = EditarManager.editar(tablaEdit, campoFiltro, valorFiltro, comparacionEdit, campoEditar, nuevoValor);
						if (error) System.out.println("Error al editar.");
						else System.out.println("Editado correctamente.");
					}
					break;
				
				// --- CASO 5: BORRAR ---
				case 5:
					int tablaDel = 0; 
					boolean borrarTodo = true;
					String filtroDel = "";

					System.out.println("¿Desea vaciar tablas?");
					System.out.println("1. Borrar TODO");
					System.out.println("2. Borrar registros específicos");
					int opDel = sc.nextInt();
					sc.nextLine();

					if (opDel == 1) {
						tablaDel = 0; 
						borrarTodo = true;
					} else {
						System.out.println("Seleccione tabla:\n1. Player\n2. Compras\n3. Games");
						tablaDel = sc.nextInt();
						sc.nextLine();

						System.out.println("¿Borrar toda la tabla (1) o filtrar (2)?");
						int subOp = sc.nextInt();
						sc.nextLine();
						
						if (subOp == 1) {
							borrarTodo = true;
						} else {
							borrarTodo = false;
							System.out.println("Escriba la condición (Ej: idPlayer = 5, Precio > 20):");
							filtroDel = sc.nextLine();
						}
					}

					if (BorrarManager.borrar(tablaDel, borrarTodo, filtroDel)) {
						System.out.println("Error al borrar.");
					} else {
						System.out.println("Borrado realizado.");
					}
					break;
				
				// --- CASO 6: ELIMINAR TABLAS (DROP) ---
				case 6:
					int tablaDrop = 0;
					System.out.println("¿Eliminar todas las tablas? 1. Sí / 2. No");
					int opDrop = sc.nextInt();
					sc.nextLine();

					if (opDrop != 1) {
						System.out.println("¿Qué tabla eliminar?\n1. Player\n2. Compras\n3. Games");
						tablaDrop = sc.nextInt();
						sc.nextLine();
					}
					
					if (EliminarManager.eliminar(tablaDrop)) {
						System.out.println("Error al eliminar tabla(s).");
					} else {
						System.out.println("Tablas eliminadas.");
					}
					break;
				} 
				
				System.out.println("\n" + menu);
				opcion = sc.nextInt();
				sc.nextLine();
			}
			
			System.out.println("Saliendo del programa.");
			sc.close();
			HibernateUtil.getSessionFactory().close();
		}

}
