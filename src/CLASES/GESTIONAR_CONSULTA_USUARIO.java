
package CLASES;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class GESTIONAR_CONSULTA_USUARIO {
    
    public List<USUARIO> LisUsuario(Integer dni){
        
    List<USUARIO> lista = new ArrayList();
    String consultaSQL="SELECT * FROM t_usuario WHERE DNI LIKE '%"+dni+"%'";
    CONEXION conect = new CONEXION();
    
    try
    {
    PreparedStatement st= conect.ObtenerConexion().prepareStatement(consultaSQL);
    ResultSet rs = st.executeQuery();
    
    while (rs.next())
    {
    USUARIO user = new USUARIO();
    
    user.setFECHA_NACIMIENTO(rs.getString(7));
    user.setDNI(rs.getString(1));
    user.setNOMBRES(rs.getString(2));
    user.setAPELLIDOS(rs.getString(3));
    user.setSEXO(rs.getString(4));
    user.setDIRECCION(rs.getString(5));
    user.setCELULAR(rs.getString(6));
    
    user.setROLL(rs.getString(8));
    user.setCONTRASENA(rs.getString(10));
    user.setUSUARIO(rs.getString(9));

    lista.add(user);
    }
    
    }
    catch(SQLException e)
    {
    e.printStackTrace();
    }
    return lista;
    }
    
}
