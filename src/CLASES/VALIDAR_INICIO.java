package CLASES;

import java.sql.*;
import javax.swing.JOptionPane;

public class VALIDAR_INICIO {

    CONEXION conex = new CONEXION();

    public String verificar(String user, String pass, String rol) {
        String dniEncontrado = null;

        String sql = "SELECT DNI FROM t_usuario WHERE USUARIO = ? AND CONTRASENA = ? AND ROL = ?";

        try (Connection con = conex.ObtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, rol);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dniEncontrado = rs.getString("DNI"); // ✅ OBTENEMOS EL DNI
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ ERROR al ingresar: " + e.getMessage());
        }

        return dniEncontrado; // ✅ Null si no existe
    }
}
