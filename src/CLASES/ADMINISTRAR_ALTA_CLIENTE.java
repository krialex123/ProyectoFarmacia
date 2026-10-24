package CLASES;

import java.sql.*;
import javax.swing.JOptionPane;

public class ADMINISTRAR_ALTA_CLIENTE {

    CONEXION con = new CONEXION();
    Connection conex = null;

    // ✅ Método final corregido: SIN campo DIRECCION
    public void GUARDAR(String dni, String nombres, String apellidos, String sexo, String celular) {
        String sql = "INSERT INTO t_cliente (DNI, NOMBRES, APELLIDOS, SEXO, CELULAR) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conex = con.ObtenerConexion();
             PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, sexo);
            ps.setString(5, celular);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Cliente guardado correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al guardar cliente: " + e.getMessage());
        }
    }
}
