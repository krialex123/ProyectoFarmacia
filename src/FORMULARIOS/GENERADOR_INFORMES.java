/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


/**
 *
 * @author AldairDesk
 */
public class GENERADOR_INFORMES extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    CONEXION con=new CONEXION();
    Connection conex=null;
    Statement st;
    ResultSet rs;
    public GENERADOR_INFORMES() {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarTabla_Ventas();
        
        btn_ventas.setEnabled(true);
        
    }
  

 public void MostrarTabla_Ventas() {
    conex = con.ObtenerConexion();

String titulos[] = {"N° FACTURA", "FECHA", "CLIENTE", "PRODUCTO", "CANTIDAD", "PRECIO", "MONTO", "USUARIO"};
DefaultTableModel dt = new DefaultTableModel(null, titulos);

try {
    st = conex.createStatement();

    rs = st.executeQuery(
    "SELECT " +
    "v.FACTURA AS ID_FACTURA, " +
    "f.FECHA AS FECHA, " +
    "COALESCE(CONCAT(c.NOMBRES, ' ', c.APELLIDOS), 'PUBLICO GENERAL') AS CLIENTE, " +
    "p.DESCRIPCION AS PRODUCTO, " +
    "(v.MONTO_VENTA / p.PRECIO) AS CANTIDAD, " +
    "p.PRECIO AS PRECIO, " +
    "v.MONTO_VENTA AS MONTO, " +
    "v.USUARIO AS USUARIO " +
    "FROM t_ventas v " +
    "LEFT JOIN t_factura f ON f.ID_FACTURA = v.FACTURA " +
    "LEFT JOIN t_cliente c ON c.DNI = v.CLIENTE " +
    "LEFT JOIN t_productos p ON p.CODIGO = v.CODIGO"
);



    while (rs.next()) {
        Object fila[] = {
            rs.getString("ID_FACTURA"),
            rs.getString("FECHA"),
            rs.getString("CLIENTE"),
            rs.getString("PRODUCTO"),
            rs.getString("CANTIDAD"),
            rs.getString("PRECIO"),
            rs.getString("MONTO"),
            rs.getString("USUARIO")
        };
        dt.addRow(fila);
    }

    tabla_reporte.setModel(dt);

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "❌ Error mostrando ventas: " + e.getMessage());
}

}

 public void MostrarTabla_Productos()
    {
        conex = con.ObtenerConexion();
    String titulos[] = {"CODIGO","DESCRIPCION","LABORATORIO","TIPO","STOCK","PRECIO","FVENCIMIENTO"};
    DefaultTableModel dt = new DefaultTableModel(null, titulos);

    try {
        st = conex.createStatement();
        rs = st.executeQuery("SELECT * FROM t_productos");
        while (rs.next()) {
            Object filas[] = {
                rs.getString("CODIGO"),
                rs.getString("DESCRIPCION"),
                rs.getString("LABORATORIO"),
                rs.getString("TIPO"),
                rs.getString("STOCK"),
                rs.getString("PRECIO"),
                rs.getString("FVENCIMIENTO")
            };
            dt.addRow(filas);
        }
        tabla_reporte.setModel(dt);
    } catch(SQLException e) {
        e.printStackTrace();
    }
    }
 public void MostrarTabla_Clientes()
    {
        conex = con.ObtenerConexion();
String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "CELULAR"};
DefaultTableModel dt = new DefaultTableModel(null, titulos);

try {
    st = conex.createStatement();
    rs = st.executeQuery("SELECT * FROM t_cliente");

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

    tabla_reporte.setModel(dt);

} catch (SQLException e) {
    e.printStackTrace();
}

    }
 public void MostrarTabla_Usuarios()
    {
        conex=con.ObtenerConexion();
        String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR","FECHA DE NACIMIENTO","ROL","USUARIO","CONTRASEÑA"}; 
        DefaultTableModel dt = new DefaultTableModel(null,titulos);
        try
        {
        st=conex.createStatement();
        rs=st.executeQuery("SELECT * FROM t_usuario");
        while(rs.next())
        {
            Object filas[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)};
            dt.addRow(filas);
        }
        tabla_reporte.setModel(dt);
        }
        catch(SQLException e)
        {
           e.printStackTrace();
        }
    }
 
 private void exportarReportePDF(String tituloReporte) {

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar reporte PDF");
    chooser.setFileFilter(new FileNameExtensionFilter("Archivo PDF", "pdf"));

    int seleccion = chooser.showSaveDialog(this);

    if (seleccion != JFileChooser.APPROVE_OPTION) {
        return;
    }

    String ruta = chooser.getSelectedFile().getAbsolutePath();

    if (!ruta.toLowerCase().endsWith(".pdf")) {
        ruta += ".pdf";
    }

    try {
        Document documento = new Document(PageSize.A4.rotate(), 25, 25, 35, 25);
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));

        documento.open();

        BaseColor colorPrincipal = new BaseColor(18, 74, 92);
        BaseColor colorSecundario = new BaseColor(0, 150, 136);
        BaseColor colorCabecera = new BaseColor(35, 55, 75);

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.WHITE);
        Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.WHITE);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, BaseColor.WHITE);
        Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);

        PdfPTable encabezado = new PdfPTable(1);
        encabezado.setWidthPercentage(100);

        PdfPCell celdaTitulo = new PdfPCell();
        celdaTitulo.setBackgroundColor(colorPrincipal);
        celdaTitulo.setPadding(12);
        celdaTitulo.setBorder(Rectangle.NO_BORDER);

        Paragraph titulo = new Paragraph(tituloReporte, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);

        Paragraph subtitulo = new Paragraph(
                "Sistema HOSPY - Reporte generado el " +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                fontSubtitulo
        );
        subtitulo.setAlignment(Element.ALIGN_CENTER);

        celdaTitulo.addElement(titulo);
        celdaTitulo.addElement(subtitulo);
        encabezado.addCell(celdaTitulo);

        documento.add(encabezado);
        documento.add(new Paragraph(" "));

        PdfPTable tablaPDF = new PdfPTable(tabla_reporte.getColumnCount());
        tablaPDF.setWidthPercentage(100);

        for (int i = 0; i < tabla_reporte.getColumnCount(); i++) {
            PdfPCell celda = new PdfPCell(new Phrase(tabla_reporte.getColumnName(i), fontHeader));
            celda.setBackgroundColor(colorCabecera);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(6);
            tablaPDF.addCell(celda);
        }

        for (int fila = 0; fila < tabla_reporte.getRowCount(); fila++) {
            for (int col = 0; col < tabla_reporte.getColumnCount(); col++) {

                Object valor = tabla_reporte.getValueAt(fila, col);

                PdfPCell celda = new PdfPCell(
                        new Phrase(valor == null ? "" : valor.toString(), fontCelda)
                );

                celda.setPadding(5);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);

                tablaPDF.addCell(celda);
            }
        }

        documento.add(tablaPDF);

        documento.add(new Paragraph(" "));

        Paragraph totalRegistros = new Paragraph(
                "Total de registros: " + tabla_reporte.getRowCount(),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, colorSecundario)
        );
        totalRegistros.setAlignment(Element.ALIGN_RIGHT);

        documento.add(totalRegistros);

        documento.close();

        JOptionPane.showMessageDialog(this, "PDF generado correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al generar PDF.");
    }
}
 
 public void MostrarVentasPorFecha(java.sql.Date desde, java.sql.Date hasta) {

    String titulos[] = {
        "N° FACTURA", "FECHA", "CLIENTE", "PRODUCTO",
        "CANTIDAD", "PRECIO", "MONTO", "USUARIO"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql =
        "SELECT " +
        "v.FACTURA AS ID_FACTURA, " +
        "f.FECHA AS FECHA, " +
        "COALESCE(CONCAT(c.NOMBRES, ' ', c.APELLIDOS), 'PUBLICO GENERAL') AS CLIENTE, " +
        "p.DESCRIPCION AS PRODUCTO, " +
        "(v.MONTO_VENTA / p.PRECIO) AS CANTIDAD, " +
        "p.PRECIO AS PRECIO, " +
        "v.MONTO_VENTA AS MONTO, " +
        "v.USUARIO AS USUARIO " +
        "FROM t_ventas v " +
        "LEFT JOIN t_factura f ON f.ID_FACTURA = v.FACTURA " +
        "LEFT JOIN t_cliente c ON c.DNI = v.CLIENTE " +
        "LEFT JOIN t_productos p ON p.CODIGO = v.CODIGO " +
        "WHERE DATE(f.FECHA) BETWEEN ? AND ?";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {

        ps.setDate(1, desde);
        ps.setDate(2, hasta);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("ID_FACTURA"),
                    rs.getString("FECHA"),
                    rs.getString("CLIENTE"),
                    rs.getString("PRODUCTO"),
                    rs.getString("CANTIDAD"),
                    rs.getString("PRECIO"),
                    rs.getString("MONTO"),
                    rs.getString("USUARIO")
                });
            }
        }

        tabla_reporte.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error cargando reporte de ventas.");
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_reporte = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btn_cancelar = new javax.swing.JButton();
        btn_exportar = new javax.swing.JButton();
        btn_ventas = new javax.swing.JButton();
        btn_productos = new javax.swing.JButton();
        btn_clientes = new javax.swing.JButton();
        btn_usuarios = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_reporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla_reporte);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 700, 290));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("REPORTE GENERAL");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        btn_cancelar.setBackground(new java.awt.Color(255, 0, 51));
        btn_cancelar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-botón-de-apagado.gif"))); // NOI18N
        btn_cancelar.setText("CANCELAR");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 410, 140, 50));

        btn_exportar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_exportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/excel-icons-animation (1).gif"))); // NOI18N
        btn_exportar.setText("EXPORTAR");
        btn_exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exportarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_exportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 160, 60));

        btn_ventas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/carrito-de-compras (1).gif"))); // NOI18N
        btn_ventas.setText("VENTAS");
        btn_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ventasActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 330, 140, 60));

        btn_productos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/solicitud-de-propuestas (1).gif"))); // NOI18N
        btn_productos.setText("PRODUCTOS");
        btn_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 140, 60));

        btn_clientes.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/usuario (1).gif"))); // NOI18N
        btn_clientes.setText("CLIENTES");
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });
        jPanel1.add(btn_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 140, 60));

        btn_usuarios.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-circundado-usuario-mujer-tipo-de-piel-6.gif"))); // NOI18N
        btn_usuarios.setText("USUARIOS");
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_usuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 120, -1, 60));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-add-background-50.jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exportarActionPerformed
         if (tabla_reporte.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
        return;
    }

    exportarReportePDF("REPORTE GENERAL");
                               
    }//GEN-LAST:event_btn_exportarActionPerformed

    private void btn_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ventasActionPerformed
        MostrarTabla_Ventas();
       
        btn_productos.setEnabled(true);
        btn_clientes.setEnabled(true);
        btn_usuarios.setEnabled(true);
        btn_ventas.setEnabled(false);
    }//GEN-LAST:event_btn_ventasActionPerformed

    private void btn_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productosActionPerformed
        MostrarTabla_Productos();
        btn_ventas.setEnabled(true);
        btn_productos.setEnabled(false);
        btn_clientes.setEnabled(true);
         btn_usuarios.setEnabled(true);
    }//GEN-LAST:event_btn_productosActionPerformed

    private void btn_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientesActionPerformed
        MostrarTabla_Clientes();
        btn_ventas.setEnabled(true);
        btn_productos.setEnabled(true);
        btn_clientes.setEnabled(false);
         btn_usuarios.setEnabled(true);
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        MostrarTabla_Usuarios();
        btn_ventas.setEnabled(true);
        btn_productos.setEnabled(true);
        btn_clientes.setEnabled(true);
        btn_usuarios.setEnabled(false);
    }//GEN-LAST:event_btn_usuariosActionPerformed
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
            java.util.logging.Logger.getLogger(GENERADOR_INFORMES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GENERADOR_INFORMES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GENERADOR_INFORMES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GENERADOR_INFORMES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GENERADOR_INFORMES().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_clientes;
    private javax.swing.JButton btn_exportar;
    private javax.swing.JButton btn_productos;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JButton btn_ventas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_reporte;
    // End of variables declaration//GEN-END:variables
}
