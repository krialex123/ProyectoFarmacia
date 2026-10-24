/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import CLASES.GENERAR_DOCUMENTO_FACTURA;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import CLASES.PRODUCTO;
import CLASES.IMPRIMIR_TICKET_POS58;
import CLASES.SesionUsuario;
import java.awt.Color;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;







/**
 *
 * @author aldair
 */

public class REGISTRO_VENTAS extends javax.swing.JFrame {
    
    private Timer timerBusqueda;

DefaultTableModel dt = new DefaultTableModel();
    CONEXION con = new CONEXION();
    // ✅ Variables globales para almacenar el producto seleccionado
    private String codigoProductoSeleccionado = null;
    private int stockDisponible = 0;

    Connection conex = null;
    Statement st;
    ResultSet rs;
    DefaultTableModel dtm;
    INTERFAZ_MENU_PRINCIPAL men = new INTERFAZ_MENU_PRINCIPAL();
   

    private Object[] ob = new Object[5]; // cantidad, descripción, precio, total, fvencimiento

    public REGISTRO_VENTAS() {
        initComponents();
        generarNumeroFactura();
        this.setLocationRelativeTo(null);
        
        
        timerBusqueda = new Timer(200, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = txtselecproducto.getText().trim();
        if (texto.isEmpty()) {
            MostrarProductos(); // Mostrar todo si está vacío
        } else {
            BuscarProducto(texto); // Buscar con delay
        }
    }
});
timerBusqueda.setRepeats(false);  // MUY IMPORTANTE

        

    // ✅ Cambiar modelo aquí (NetBeans ya terminó de cargar la tabla)
    tabla_registro_ventas.setModel(new DefaultTableModel(
    new Object [][] {},
    new String [] {
        "CANT","CODIGO", "DESCRIPCIÓN", "PRECIO UNIT.", "TOTAL"
    }
));

    
 



    
    dtm = (DefaultTableModel) tabla_registro_ventas.getModel();
    
    
        this.setLocationRelativeTo(null);
        dtm = (DefaultTableModel) tabla_registro_ventas.getModel();

        Date fech = new Date();
        SimpleDateFormat FormFecha = new SimpleDateFormat("dd/MM/YYYY");
        lbl_fecha.setText(FormFecha.format(fech));

        MostrarClientes();
        ;
        
    }
    //===================================================
    //          CALCULAR EL TOTAL DE LA VENTA 
    //===================================================
    private void calcularTotal() {
    double total = 0;

    DefaultTableModel modelo = (DefaultTableModel) tabla_registro_ventas.getModel();

    // La columna 4 SIEMPRE es el TOTAL del producto
    for (int i = 0; i < modelo.getRowCount(); i++) {
        total += Double.parseDouble(modelo.getValueAt(i, 4).toString());
    }

    txt_total.setText(String.valueOf(total));
}
    
    //===================================================
    //                    VUELTO 
    //===================================================
    
    private void vuelto (){
    
   try {
        double total = Double.parseDouble(txt_total.getText());
        double monto = Double.parseDouble(txt_monto.getText());

        double vuelto = monto - total;

        if (vuelto < 0) {
            // Falta dinero
            txt_vuelto.setForeground(Color.RED);
            txt_vuelto.setText("FALTA DINERO");
        } else {
            // Vuelto normal
            txt_vuelto.setForeground(new Color(0, 128, 0)); // verde
            txt_vuelto.setText(String.format("%.2f", vuelto));
        }

    } catch (Exception e) {
        txt_vuelto.setForeground(Color.BLACK);
        txt_vuelto.setText("0.00");
    }
    }

    
    //===================================================
    //          PASAR DATOS DE LA TABLA 
    //===================================================
    
 private void pasarProductoConClick() {

    int fila = tabla_registro_producto.getSelectedRow();
    if (fila == -1) return;

    DefaultTableModel modeloProductos = (DefaultTableModel) tabla_registro_producto.getModel();
    DefaultTableModel modeloVentas = (DefaultTableModel) tabla_registro_ventas.getModel();

    String codigo = modeloProductos.getValueAt(fila, 0).toString();
    String descripcion = modeloProductos.getValueAt(fila, 1).toString();
    int stockActual = Integer.parseInt(modeloProductos.getValueAt(fila, 3).toString());
    double precio = Double.parseDouble(modeloProductos.getValueAt(fila, 4).toString());

    if (stockActual <= 0) {
        JOptionPane.showMessageDialog(this, "Este producto no tiene stock disponible.");
        return;
    }

    String cantidadTxt = JOptionPane.showInputDialog(
            this,
            "Cantidad para " + descripcion + "\nStock disponible: " + stockActual
    );

    if (cantidadTxt == null || cantidadTxt.trim().isEmpty()) return;

    int cantidad;

    try {
        cantidad = Integer.parseInt(cantidadTxt.trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.");
        return;
    }

    if (cantidad <= 0) {
        JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
        return;
    }

    int cantidadYaAgregada = 0;

    for (int i = 0; i < modeloVentas.getRowCount(); i++) {
        String codTabla = modeloVentas.getValueAt(i, 1).toString();

        if (codTabla.equals(codigo)) {
            cantidadYaAgregada += Integer.parseInt(modeloVentas.getValueAt(i, 0).toString());
        }
    }

    if ((cantidadYaAgregada + cantidad) > stockActual) {
        JOptionPane.showMessageDialog(
                this,
                "Stock insuficiente.\nDisponible: " + stockActual +
                "\nYa agregado: " + cantidadYaAgregada +
                "\nIntentas agregar: " + cantidad,
                "Sin stock",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    double subtotal = precio * cantidad;

    modeloVentas.addRow(new Object[]{
        cantidad,
        codigo,
        descripcion,
        precio,
        subtotal
    });

    calcularTotal();
}

    
    //===================================================
    //          GENERAR NUMERO DE FACTURTA
    //===================================================
    
    public void generarNumeroFactura() {
    try (Connection cx = con.ObtenerConexion()) {

        String sql = "SELECT MAX(FACTURA) AS ultimo FROM t_ventas";
        PreparedStatement ps = cx.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int siguiente = 1;

        if (rs.next() && rs.getString("ultimo") != null) {
            siguiente = Integer.parseInt(rs.getString("ultimo")) + 1;
        }

        // ✅ Formato: BOLET00 + número (ej: BOLET001, BOLET010)
        String facturaFormateada = String.format("BOLET%03d", siguiente);

        txt_bol.setText("BOLET00"); // si este es fijo visual
        txt_num_bol.setText(String.valueOf(siguiente)); // solo el número
        // O si quieres mostrar todo junto en un solo campo:
        // txt_num_bol.setText(facturaFormateada);

        rs.close();
        ps.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error generando número de factura: " + e.getMessage());
    }
}

    
    // =====================================================
    // ✅ Buscar los productos
    // =====================================================
    
    public void BuscarProducto(String buscar) {
    conex = con.ObtenerConexion();
    String titulos[] = {"CODIGO", "DESCRIPCIÓN", "LABORATORIO", "STOCK", "PRECIO", "FVENCIMIENTO"};
    DefaultTableModel dt = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, PRECIO, FVENCIMIENTO "
               + "FROM t_productos "
               + "WHERE CODIGO LIKE '%" + buscar + "%' OR DESCRIPCION LIKE '%" + buscar + "%'";

    try {
        st = conex.createStatement();
        rs = st.executeQuery(sql);

        while (rs.next()) {
            Object fila[] = {
                rs.getString("CODIGO"),
                rs.getString("DESCRIPCION"),
                rs.getString("LABORATORIO"),
                rs.getInt("STOCK"),
                rs.getDouble("PRECIO"),
                rs.getString("FVENCIMIENTO")
            };
            dt.addRow(fila);
        }

        tabla_registro_producto.setModel(dt);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "❌ Error al buscar producto: " + e.getMessage());
    }
}

    
    // =====================================================
    // ✅ Buscar los clientes
    // =====================================================
    
    public void BuscarClientes(String buscar) {
    conex = con.ObtenerConexion();
    String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "CELULAR"};
    DefaultTableModel dt = new DefaultTableModel(null, titulos);

    String sql = "SELECT DNI, NOMBRES, APELLIDOS, SEXO, CELULAR "
               + "FROM t_cliente "
               + "WHERE DNI LIKE '%" + buscar + "%' OR NOMBRES LIKE '%" + buscar + "%'";

    try {
        st = conex.createStatement();
        rs = st.executeQuery(sql);

        while (rs.next()) {
            Object fila[] = {
                rs.getString("DNI"),
                rs.getString("NOMBRES"),
                rs.getString("APELLIDOS"),
                rs.getString("SEXO"),
                rs.getString("CELULAR")
            };
            dt.addRow(fila);
        }

      //  tabla_clientes.setModel(dt);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "❌ Error al buscar cliente: " + e.getMessage());
    }
}

    
    
    // =====================================================
    // ✅ Mostrar todos los productos
    // =====================================================

    public void MostrarProductos() {
    conex = con.ObtenerConexion();
    String titulos[] = {"CODIGO", "DESCRIPCIÓN", "LABORATORIO", "STOCK", "PRECIO", "FVENCIMIENTO"};
    DefaultTableModel dt = new DefaultTableModel(null, titulos);

    try {
        st = conex.createStatement();
        rs = st.executeQuery("SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, PRECIO, FVENCIMIENTO FROM t_productos");

        while (rs.next()) {
            Object[] fila = {
                rs.getString("CODIGO"),
                rs.getString("DESCRIPCION"),
                rs.getString("LABORATORIO"),
               
                rs.getInt("STOCK"),
                rs.getDouble("PRECIO"),
                rs.getString("FVENCIMIENTO")
            };
            dt.addRow(fila);
        }

        tabla_registro_producto.setModel(dt);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "❌ Error al mostrar productos: " + e.getMessage());
    }
}

    
    
    // =====================================================
    // ✅ Mostrar todos los clientes
    // =====================================================
    public void MostrarClientes() {
        conex = con.ObtenerConexion();
        String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "CELULAR"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);
        try {
            st = conex.createStatement();
            rs = st.executeQuery("SELECT DNI, NOMBRES, APELLIDOS, SEXO, CELULAR FROM t_cliente");
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("DNI"),
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOS"),
                    rs.getString("SEXO"),
                    rs.getString("CELULAR")
                };
                dt.addRow(fila);
            }
           // tabla_clientes.setModel(dt);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al mostrar clientes: " + e.getMessage());
        }
    }

    // =====================================================
    // ✅ Filtro general de clientes
    // =====================================================
    /* public void FiltrarClientes(String campo, String valor) {
        conex = con.ObtenerConexion();
        String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "CELULAR"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);
        try {
            String sql = "SELECT DNI, NOMBRES, APELLIDOS, SEXO, CELULAR FROM t_cliente WHERE " + campo + " LIKE '%" + valor + "%'";
            st = conex.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("DNI"),
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOS"),
                    rs.getString("SEXO"),
                    rs.getString("CELULAR")
                };
                dt.addRow(fila);
            }
            tabla_clientes.setModel(dt);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al filtrar clientes: " + e.getMessage());
        }
    }
*/
    // =====================================================
    // ✅ Cargar laboratorios al combo
    // =====================================================
    
    
    
public void SeleccionarProducto(PRODUCTO p) {

}


    // =====================================================
    // ✅ Actualizar stock
    // =====================================================

public void ActualizarStock(String codigo, int cantidadVendida) {
       try (Connection conex = con.ObtenerConexion()) {

        // ✅ Verificar stock del producto exacto
        String sqlSelect = "SELECT STOCK FROM t_productos WHERE CODIGO = ?";
        PreparedStatement psSelect = conex.prepareStatement(sqlSelect);
        psSelect.setString(1, codigo);
        ResultSet rsSelect = psSelect.executeQuery();
        
        if (codigoProductoSeleccionado == null) {
    JOptionPane.showMessageDialog(this,
        "️ Primero seleccione un producto.",
        "Sin producto seleccionado",
        JOptionPane.WARNING_MESSAGE);
    return;
       }

        if (rsSelect.next()) {
            int stockActual = rsSelect.getInt("STOCK");
            int nuevoStock = stockActual - cantidadVendida;

            if (nuevoStock < 0) {
                JOptionPane.showMessageDialog(this,
                        "️ Stock insuficiente del producto con código: " + codigo,
                        "Sin Stock", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ✅ Actualizar stock
            String sqlUpdate = "UPDATE t_productos   SET STOCK = ? WHERE CODIGO = ?";
            PreparedStatement psUpdate = conex.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, nuevoStock);
            psUpdate.setString(2, codigo);
            psUpdate.executeUpdate();

            psUpdate.close();
        } else {
            JOptionPane.showMessageDialog(this,
                    "️ No se encontró el producto con código: " + codigo,
                    "No encontrado", JOptionPane.WARNING_MESSAGE);
        }

        rsSelect.close();
        psSelect.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, " Error actualizando stock: " + e.getMessage());
    }
    }
    
    // ✅ Este método recibirá los datos enviados desde VISTA_CLIENTE
public void SeleccionarCliente(String dni, String nombre, String celular) {
    txt_dni.setText(dni);
    txt_nom_ape.setText(nombre);
    txt_celular.setText(celular);
}


   public void Contador()
   {
   int op=0;
   int acumulador=0;
   for(int i=0;i<=100;i++)
   {
      acumulador=acumulador+1;
   }
}
   private boolean verificarStock(String codigo, int cantidad) {
    try (Connection conex = con.ObtenerConexion()) {
        String sql = "SELECT STOCK FROM t_productos WHERE CODIGO = ?";
        PreparedStatement ps = conex.prepareStatement(sql);
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int stockActual = rs.getInt("STOCK");

            if (cantidad > stockActual) {
                JOptionPane.showMessageDialog(this,
                        "️ Stock insuficiente.\nDisponible: " + stockActual,
                        "Sin stock", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "️ No se encontró el producto con código: " + codigo);
        return false;

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                " Error verificando stock: " + e.getMessage());
    }
    return false;
}

   
   
    
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_dni = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_nuevo_cliente = new javax.swing.JButton();
        txt_nom_ape = new javax.swing.JTextField();
        txt_celular = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btneliminarproducto = new javax.swing.JButton();
        btn_registrar_venta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_registro_ventas = new javax.swing.JTable();
        btn_sali = new javax.swing.JButton();
        txt_total = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txt_bol = new javax.swing.JTextField();
        txt_num_bol = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_registro_producto = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtselecproducto = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_vuelto = new javax.swing.JTextField();
        txt_monto = new javax.swing.JTextField();
        btnRcliente = new javax.swing.JButton();
        btngenerarcomprobante = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbl_userr = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("REGISTRO DE VENTAS");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("REGISTRO DE");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DNI");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        txt_dni.setEditable(false);
        txt_dni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txt_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 127, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRE Y APELLIDOS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CELULAR");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        btn_nuevo_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_nuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/Animation - 1733269663890.gif"))); // NOI18N
        btn_nuevo_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_clienteActionPerformed(evt);
            }
        });
        getContentPane().add(btn_nuevo_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 140, 140));

        txt_nom_ape.setEditable(false);
        txt_nom_ape.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_ape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nom_apeActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nom_ape, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 127, -1));

        txt_celular.setEditable(false);
        txt_celular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txt_celular, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 127, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("PRODUCTO:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, -1, -1));

        btneliminarproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btneliminarproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/ELIMINAR.png"))); // NOI18N
        btneliminarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarproductoActionPerformed(evt);
            }
        });
        getContentPane().add(btneliminarproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, 180, 50));

        btn_registrar_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_registrar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/agregar (1).gif"))); // NOI18N
        btn_registrar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_ventaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_registrar_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 320, 110, 60));

        tabla_registro_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tabla_registro_ventas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 680, 110));

        btn_sali.setBackground(new java.awt.Color(255, 0, 0));
        btn_sali.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_sali.setForeground(new java.awt.Color(255, 255, 255));
        btn_sali.setText("SALIR");
        btn_sali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_sali, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 480, 110, 50));

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        getContentPane().add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 450, 110, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("MONTO: S/.");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 410, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DATOS DEL CLIENTE");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("DETALLES DE LA VENTA");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("VENTAS");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        jPanel2.setBackground(new java.awt.Color(153, 0, 204));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("BOLETA       N°");

        txt_bol.setEditable(false);
        txt_bol.setBackground(new java.awt.Color(204, 0, 204));
        txt_bol.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_bol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bol.setText("BOLET00");
        txt_bol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bolActionPerformed(evt);
            }
        });

        txt_num_bol.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_num_bol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_num_bol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_num_bolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(txt_bol, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_num_bol, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_bol, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txt_num_bol))
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 200, 90));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TOTAL: S/.");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 450, -1, -1));

        lbl_fecha.setForeground(new java.awt.Color(255, 255, 0));
        lbl_fecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/reloj (1).gif"))); // NOI18N
        lbl_fecha.setText("FECHA ACTUAL");
        getContentPane().add(lbl_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 130, 20));

        tabla_registro_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla_registro_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_registro_productoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabla_registro_producto);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 580, 130));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("VUELTO: S/.");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 490, -1, -1));

        txtselecproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtselecproductoActionPerformed(evt);
            }
        });
        txtselecproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtselecproductoKeyReleased(evt);
            }
        });
        getContentPane().add(txtselecproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 42, 310, 30));

        jPanel4.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 10, 340));

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 620, 10));

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 470, 10));

        txt_vuelto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_vuelto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_vueltoKeyReleased(evt);
            }
        });
        getContentPane().add(txt_vuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 490, 110, -1));

        txt_monto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_montoKeyReleased(evt);
            }
        });
        getContentPane().add(txt_monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 410, 110, -1));

        btnRcliente.setText("***");
        btnRcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRclienteActionPerformed(evt);
            }
        });
        getContentPane().add(btnRcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, -1, -1));

        btngenerarcomprobante.setBackground(new java.awt.Color(153, 255, 153));
        btngenerarcomprobante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        btngenerarcomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngenerarcomprobanteActionPerformed(evt);
            }
        });
        getContentPane().add(btngenerarcomprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 400, 110, 60));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("SELECCIONA EL  PRODUCTO");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/image1.jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 570));

        lbl_userr.setForeground(new java.awt.Color(255, 255, 255));
        lbl_userr.setText("---");
        getContentPane().add(lbl_userr, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    private void btn_saliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saliActionPerformed

     // TODO add your handling code here:
     this.dispose();
    }//GEN-LAST:event_btn_saliActionPerformed

    private void btn_nuevo_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_clienteActionPerformed

    GESTION_CLIENTE client = new GESTION_CLIENTE();

  // 🔹 Cuando se cierre la ventana de registro, actualizamos la tabla
client.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        MostrarClientes(); // 🔄 Recargar clientes
    }
});

DefaultTableModel model = (DefaultTableModel) tabla_registro_ventas.getModel();
model.setRowCount(0);          // ✅ limpia productos
txt_total.setText("");         // ✅ limpia total
txt_dni.setText("");
txt_nom_ape.setText("");
txt_celular.setText("");
generarNumeroFactura();        // ✅ genera nueva boleta

    client.setVisible(true);
    }//GEN-LAST:event_btn_nuevo_clienteActionPerformed

    private void btneliminarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarproductoActionPerformed
                                                 
     
    }//GEN-LAST:event_btneliminarproductoActionPerformed
    private void btn_registrar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_ventaActionPerformed
       
     if (tabla_registro_ventas.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No hay productos en la venta.");
        return;
    }

    String cliente = txt_dni.getText().trim();
    if (cliente.isEmpty()) {
        cliente = null;
    }

    String usuario = SesionUsuario.DNI;
    if (usuario == null || usuario.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Error: usuario no válido.");
        return;
    }

    String numeroFactura = txt_num_bol.getText().trim();
    if (numeroFactura.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Número de boleta vacío.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tabla_registro_ventas.getModel();

    try (Connection cx = con.ObtenerConexion()) {

        cx.setAutoCommit(false);

        try {

            for (int i = 0; i < model.getRowCount(); i++) {

                String codigoProducto = model.getValueAt(i, 1).toString();
                int cantidadVendida = Integer.parseInt(model.getValueAt(i, 0).toString());
                double montoVenta = Double.parseDouble(model.getValueAt(i, 4).toString());

                String sqlStock = "SELECT STOCK FROM t_productos WHERE CODIGO = ? FOR UPDATE";
                try (PreparedStatement psStock = cx.prepareStatement(sqlStock)) {
                    psStock.setString(1, codigoProducto);

                    try (ResultSet rsStock = psStock.executeQuery()) {

                        if (!rsStock.next()) {
                            throw new SQLException("Producto no encontrado: " + codigoProducto);
                        }

                        int stockActual = rsStock.getInt("STOCK");

                        if (cantidadVendida > stockActual) {
                            cx.rollback();
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Stock insuficiente para el producto: " + codigoProducto +
                                    "\nDisponible: " + stockActual +
                                    "\nCantidad solicitada: " + cantidadVendida,
                                    "Sin stock",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;
                        }
                    }
                }

                String sqlVenta = "INSERT INTO t_ventas (CODIGO, CLIENTE, MONTO_VENTA, USUARIO, FACTURA) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement psVenta = cx.prepareStatement(sqlVenta)) {
                    psVenta.setString(1, codigoProducto);
                    psVenta.setString(2, cliente);
                    psVenta.setDouble(3, montoVenta);
                    psVenta.setString(4, usuario);
                    psVenta.setString(5, numeroFactura);
                    psVenta.executeUpdate();
                }

                String sqlUpdate = "UPDATE t_productos SET STOCK = STOCK - ? WHERE CODIGO = ?";
                try (PreparedStatement psUpdate = cx.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, cantidadVendida);
                    psUpdate.setString(2, codigoProducto);
                    psUpdate.executeUpdate();
                }
            }

            cx.commit();

            JOptionPane.showMessageDialog(this, "🟢 Venta registrada correctamente.");

            model.setRowCount(0);
            txt_total.setText("");
            txt_monto.setText("");
            txt_vuelto.setText("");
            txt_dni.setText("");
            txt_nom_ape.setText("");
            txt_celular.setText("");

            MostrarProductos();
            generarNumeroFactura();

        } catch (Exception e) {
            cx.rollback();
            JOptionPane.showMessageDialog(this, "No se pudo registrar la venta.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error de conexión al registrar venta.");
    }



        
    }//GEN-LAST:event_btn_registrar_ventaActionPerformed

    private void txt_nom_apeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nom_apeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nom_apeActionPerformed

    private void txtselecproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtselecproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtselecproductoActionPerformed

    private void txt_num_bolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_num_bolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_num_bolActionPerformed

    private void btnRclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRclienteActionPerformed
        // TODO add your handling code here:
VISTA_CLIENTE buscador = new VISTA_CLIENTE(this);
buscador.setVisible(true);
buscador.setLocationRelativeTo(null);

        
    }//GEN-LAST:event_btnRclienteActionPerformed

    private void btngenerarcomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngenerarcomprobanteActionPerformed
        // TODO add your handling code here:
        
        if (tabla_registro_ventas.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No hay productos en la venta.");
        return;
    }

    String factura = txt_num_bol.getText();
    String fecha = lbl_fecha.getText();
    String usuario = lbl_userr.getText();
    String total = txt_total.getText();

    // Cliente opcional
    String cliente;
    if (txt_dni.getText().trim().isEmpty()) {
        cliente = "Consumidor Final";
    } else {
        cliente = txt_nom_ape.getText();
    }

    IMPRIMIR_TICKET_POS58 ticket = new IMPRIMIR_TICKET_POS58(
            factura,
            fecha,
            cliente,
            usuario,
            total,
            tabla_registro_ventas
    );

    ticket.imprimirDirecto();

    JOptionPane.showMessageDialog(this, "🟢 Ticket impreso correctamente.");
        
      
    }//GEN-LAST:event_btngenerarcomprobanteActionPerformed

    private void txt_bolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bolActionPerformed

    private void txtselecproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtselecproductoKeyReleased
        // TODO add your handling code here:
        timerBusqueda.restart();
    }//GEN-LAST:event_txtselecproductoKeyReleased

    private void tabla_registro_productoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_registro_productoMouseClicked
        // TODO add your handling code here:
        
        pasarProductoConClick();
    
    }//GEN-LAST:event_tabla_registro_productoMouseClicked

    private void txt_vueltoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_vueltoKeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txt_vueltoKeyReleased

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_montoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montoKeyReleased
        // TODO add your handling code here:
        vuelto();
    }//GEN-LAST:event_txt_montoKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(REGISTRO_VENTAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(REGISTRO_VENTAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(REGISTRO_VENTAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(REGISTRO_VENTAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            new REGISTRO_VENTAS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRcliente;
    private javax.swing.JButton btn_nuevo_cliente;
    private javax.swing.JButton btn_registrar_venta;
    private javax.swing.JButton btn_sali;
    private javax.swing.JButton btneliminarproducto;
    private javax.swing.JButton btngenerarcomprobante;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_fecha;
    public static javax.swing.JLabel lbl_userr;
    public javax.swing.JTable tabla_registro_producto;
    public javax.swing.JTable tabla_registro_ventas;
    private javax.swing.JTextField txt_bol;
    public static javax.swing.JTextField txt_celular;
    public static javax.swing.JTextField txt_dni;
    private javax.swing.JTextField txt_monto;
    public static javax.swing.JTextField txt_nom_ape;
    private javax.swing.JTextField txt_num_bol;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_vuelto;
    private javax.swing.JTextField txtselecproducto;
    // End of variables declaration//GEN-END:variables
}
