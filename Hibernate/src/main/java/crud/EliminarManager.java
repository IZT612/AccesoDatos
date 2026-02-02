package crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EliminarManager {

    /* "tablas" puede valer:
     * 0 -> Todas las tablas
     * 1 -> Player
     * 2 -> Compras
     * 3 -> Games
     */
    public static boolean borrarTablas(int tablas, Connection conn) {

        boolean error = false;

        String borrarCompras = "DROP TABLE IF EXISTS Compras";
        String borrarPlayer  = "DROP TABLE IF EXISTS Player";
        String borrarGames   = "DROP TABLE IF EXISTS Games";

        try {

            Statement stmt = conn.createStatement();

            switch (tablas) {

            case 0: {
                // Orden importante por claves foráneas
                stmt.executeUpdate(borrarCompras);
                stmt.executeUpdate(borrarPlayer);
                stmt.executeUpdate(borrarGames);
                break;
            }

            case 1: {
                // Player depende de Compras
                stmt.executeUpdate(borrarCompras);
                stmt.executeUpdate(borrarPlayer);
                break;
            }

            case 2: {
                stmt.executeUpdate(borrarCompras);
                break;
            }

            case 3: {
                // Games depende de Compras
                stmt.executeUpdate(borrarCompras);
                stmt.executeUpdate(borrarGames);
                break;
            }

            default: {
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
