package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import credenciales.Credenciales;
import crud.InsertarManager;

public class Main {
	
	private static Connection conn;

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int opcion;
		
		System.out.println("Introduzca qué quiere hacer: 1. Crear tablas.\n2. Insertar.\n3. Listar\n4. Modificar.\n5. Borrar.\n6. Eliminar.");
		
		opcion = sc.nextInt();
		sc.nextLine();
		
		while(opcion >= 1 && opcion <= 6) {
			
			switch(opcion) {
			
				case 2: {
					
					System.out.println("¿En qué tabla quieres insertar?\n1 -> Player\n2 -> Games\n3 -> Compras");

			        opcion = sc.nextInt();
			        sc.nextLine();

			        try {
			            switch (opcion) {

			            case 1: {
			                System.out.print("Email: ");
			                String email = sc.nextLine();

			                System.out.print("Password: ");
			                String password = sc.nextLine();

			                InsertarManager.insertarPlayer(conn, email, password);
			            }

			            case 2: {
			                System.out.print("Nombre del juego: ");
			                String nombre = sc.nextLine();

			                System.out.print("Tiempo jugado (HH:MM:SS): ");
			                String tiempo = sc.nextLine();

			                InsertarManager.insertarGames(conn, nombre, tiempo);
			            }

			            case 3: {
			                System.out.print("Email del Player: ");
			                String emailPlayer = sc.nextLine();

			                System.out.print("Nombre del Game: ");
			                String nombreGame = sc.nextLine();

			                System.out.print("Cosa comprada: ");
			                String cosa = sc.nextLine();

			                System.out.print("Precio: ");
			                float precio = sc.nextFloat();
			                sc.nextLine();

			                System.out.print("Fecha (YYYY-MM-DD): ");
			                String fecha = sc.nextLine();

			                InsertarManager.insertarCompra(
			                        conn,
			                        emailPlayer,
			                        nombreGame,
			                        cosa,
			                        precio,
			                        fecha
			                );
			            }
			            }
			        } catch (SQLException e) {
			            System.out.println(e.getMessage());
			        }
					
				}
			
			}
			
			
		}

	}
	
	public static void conectar() {
		
		try {
			
			conn = DriverManager.getConnection(Credenciales.DIRECCION, Credenciales.USUARIO, Credenciales.PW);
			
		} catch (SQLException e) {
			
			System.out.println("No se ha podido conectar con la base de datos.");
			System.out.println(e);
			
		}

		
	}

}
