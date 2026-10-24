
package CLASES;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CONEXION {

    public String db = "apuqhawa_farmavital";
    public String url = "jdbc:mysql://apuqhawarina.com:3306/" + db + "?useSSL=false&serverTimezone=UTC";
    public String user = "paco paco";
    public String pass = "boni boni";


    public Connection ObtenerConexion() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "❌ Error: Driver MySQL no encontrado\n" + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error conectando a MySQL\n" + e.getMessage());
        }
        return cn;
    }
}
