package crud;

import java.sql.*;

public class InsertarManager {

    // ---------- PLAYER ----------

    public static void insertarPlayer(
            Connection conn,
            String email,
            String password
    ) throws SQLException {

        String sql = "INSERT INTO Player (email, password) VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);

        ps.executeUpdate();
    }

    // ---------- GAMES ----------

    public static void insertarGames(
            Connection conn,
            String nombre,
            String tiempoJugado
    ) throws SQLException {

        String sql = "INSERT INTO Games (Nombre, tiempoJugado) VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, tiempoJugado);

        ps.executeUpdate();
    }

    // ---------- COMPRAS ----------

    public static void insertarCompra(
            Connection conn,
            String emailPlayer,
            String nombreGame,
            String cosa,
            float precio,
            String fecha
    ) throws SQLException {

        int idPlayer = obtenerIdPlayer(conn, emailPlayer);
        int idGames = obtenerIdGame(conn, nombreGame);

        String sql = "INSERT INTO Compras(idPlayer, idGames, Cosa, Precio, FechaCompra) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idPlayer);
        ps.setInt(2, idGames);
        ps.setString(3, cosa);
        ps.setFloat(4, precio);
        ps.setString(5, fecha);

        ps.executeUpdate();
    }

    // ---------- BUSCAR PLAYER ----------

    private static int obtenerIdPlayer(Connection conn, String email)
            throws SQLException {

        String sql = "SELECT idPlayer FROM Player WHERE email = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            throw new SQLException("No existe el Player con ese email");
        }

        return rs.getInt("idPlayer");
    }

    // ---------- BUSCAR GAME ----------

    private static int obtenerIdGame(Connection conn, String nombre)
            throws SQLException {

        String sql = "SELECT idGames FROM Games WHERE Nombre = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            throw new SQLException("No existe el Game con ese nombre");
        }

        return rs.getInt("idGames");
    }
}
