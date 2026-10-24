package CLASES;

import java.sql.*;
import javax.swing.JOptionPane;

public class ADMINISTRAR_ALTA_USUARIO {

    CONEXION con = new CONEXION();
    Connection conex = null;
    PreparedStatement pst = null;

    public void GUARDAR(String dni, String nombres, String apellidos, String sexo, 
                        String direccion, String celular, String fecha_nacimiento, 
                        String rol, String usuario, String contrasena) 
    {
        try {
            // ✅ Convertir fecha DD/MM/YYYY a YYYY-MM-DD
            String[] partes = fecha_nacimiento.split("/");
            String fechaMYSQL = partes[2] + "-" + partes[1] + "-" + partes[0];

            String sql = "INSERT INTO t_usuario "
                       + "(DNI, NOMBRES, APELLIDOS, SEXO, DIRECCION, CELULAR, FECHA_NACIMIENTO, ROL, USUARIO, CONTRASENA) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conex = con.ObtenerConexion();
            pst = conex.prepareStatement(sql);

            pst.setString(1, dni);
            pst.setString(2, nombres);
            pst.setString(3, apellidos);
            pst.setString(4, sexo);
            pst.setString(5, direccion);
            pst.setString(6, celular);
            pst.setString(7, fechaMYSQL);
            pst.setString(8, rol);
            pst.setString(9, usuario);
            pst.setString(10, contrasena);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "✅ Usuario registrado correctamente");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ ERROR AL GUARDAR: " + e.getMessage());
        }
    }
}
