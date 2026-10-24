package CLASES;

import java.sql.*;
import javax.swing.JOptionPane;

public class GENERAR_DOCUMENTO_FACTURA {
    
    CONEXION con = new CONEXION();
    Connection conex = null;

    // ✅ 1. GUARDAR FACTURA Y RETORNAR ID_FACTURAA
    public int GUARDAR_FACTURA(String fecha, String cliente) {
        int idGenerado = -1;
        try {
            conex = con.ObtenerConexion();
            PreparedStatement ps = conex.prepareStatement(
                "INSERT INTO t_factura (FECHA, CLIENTE) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, fecha);
            ps.setString(2, cliente);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            // JOptionPane.showMessageDialog(null, "Factura registrada con éxito");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error guardando factura: " + e.getMessage());
        }

        return idGenerado;
    }

    // ✅ 2. GUARDAR VENTA VINCULADA A LA FACTURA
    public void GUARDAR_VENTA(String cod, String cliente, String monto, String user, int idFactura) {
        try {
            conex = con.ObtenerConexion();
            PreparedStatement ps = conex.prepareStatement(
                "INSERT INTO t_ventas (CODIGO, CLIENTE, MONTO_VENTA, USUARIO, FACTURA) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setString(1, cod);
            ps.setString(2, cliente);
            ps.setDouble(3, Double.parseDouble(monto));
            ps.setString(4, user);
            ps.setInt(5, idFactura);

            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error guardando venta: " + e.getMessage());
        }
    }
}
