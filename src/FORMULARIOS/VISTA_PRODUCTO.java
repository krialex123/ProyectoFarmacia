/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import CLASES.PRODUCTO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AldairDesk
 */
public class VISTA_PRODUCTO extends javax.swing.JFrame {

    private final CONEXION con = new CONEXION();
    private REGISTRO_VENTAS ventasForm;


public VISTA_PRODUCTO() {
    
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarTabla();
        tabla_Bproducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}    



    public VISTA_PRODUCTO(REGISTRO_VENTAS ventasForm) {
        
        initComponents();
        this.ventasForm = ventasForm;
        this.setLocationRelativeTo(null);
        MostrarTabla();
        tabla_Bproducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
// ======================================================
    // ✅ MOSTRAR TODOS LOS PRODUCTOS
    // ======================================================
    public void MostrarTabla() {

    String[] titulos = {"CODIGO", "DESCRIPCIÓN", "LABORATORIO", "STOCK", "PRECIO", "VENCIMIENTO"};
    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, PRECIO, FVENCIMIENTO FROM t_productos";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("CODIGO"),
                rs.getString("DESCRIPCION"),
                rs.getString("LABORATORIO"),
                rs.getInt("STOCK"),
                rs.getBigDecimal("PRECIO"),
                rs.getDate("FVENCIMIENTO")
            });
        }

        tabla_Bproducto.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los productos.");
    }
}

    // ======================================================
    // ✅ BUSCAR POR CÓDIGO O DESCRIPCIÓN
    // ======================================================
    public void BuscarProducto(String texto) {

    String[] titulos = {"CODIGO", "DESCRIPCIÓN", "LABORATORIO", "STOCK", "PRECIO", "VENCIMIENTO"};
    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, PRECIO, FVENCIMIENTO "
               + "FROM t_productos "
               + "WHERE CODIGO LIKE ? OR DESCRIPCION LIKE ?";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {

        String filtro = "%" + texto.trim() + "%";

        ps.setString(1, filtro);
        ps.setString(2, filtro);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("LABORATORIO"),
                    rs.getInt("STOCK"),
                    rs.getBigDecimal("PRECIO"),
                    rs.getDate("FVENCIMIENTO")
                });
            }
        }

        tabla_Bproducto.setModel(modelo);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error al buscar productos.");
    }
}
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_Bproducto = new javax.swing.JTable();
        bntBcliente = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_Bproducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla_Bproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_BproductoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_BproductoMousePressed(evt);
            }
        });
        tabla_Bproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_BproductoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_Bproducto);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 125, 690, 310));

        bntBcliente.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bntBcliente.setText("Buscar");
        bntBcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntBclienteActionPerformed(evt);
            }
        });
        jPanel1.add(bntBcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, 40));

        btn_cancelar.setBackground(new java.awt.Color(255, 0, 0));
        btn_cancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-GO TO_newuser.png"))); // NOI18N
        btn_cancelar.setText("CANCELAR");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, -1, 50));

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 180, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/8275340 (2).jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 730, 460));

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
             this.dispose();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void tabla_BproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_BproductoKeyReleased
        
    }//GEN-LAST:event_tabla_BproductoKeyReleased

    private void tabla_BproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BproductoMouseClicked
        
        int fila = tabla_Bproducto.rowAtPoint(evt.getPoint());

    if (fila < 0) {
        return;
    }

    if (ventasForm == null) {
        JOptionPane.showMessageDialog(this, "Esta ventana solo debe abrirse desde ventas.");
        return;
    }

    String codigo = tabla_Bproducto.getValueAt(fila, 0).toString();
    String descripcion = tabla_Bproducto.getValueAt(fila, 1).toString();
    String laboratorio = tabla_Bproducto.getValueAt(fila, 2).toString();

    int stock = Integer.parseInt(tabla_Bproducto.getValueAt(fila, 3).toString());

    java.math.BigDecimal precio = new java.math.BigDecimal(
            tabla_Bproducto.getValueAt(fila, 4).toString()
    );

    java.sql.Date fechaSQL = (java.sql.Date) tabla_Bproducto.getValueAt(fila, 5);

    if (fechaSQL == null) {
        JOptionPane.showMessageDialog(this, "El producto no tiene fecha de vencimiento.");
        return;
    }

    java.time.LocalDate vencimiento = fechaSQL.toLocalDate();

    PRODUCTO p = new PRODUCTO(
            codigo,
            descripcion,
            laboratorio,
            stock,
            vencimiento,
            precio
    );

    ventasForm.SeleccionarProducto(p);

    this.dispose();
   
    }//GEN-LAST:event_tabla_BproductoMouseClicked

    private void tabla_BproductoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BproductoMousePressed

    }//GEN-LAST:event_tabla_BproductoMousePressed

    private void bntBclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBclienteActionPerformed
        // TODO add your handling code here:
        BuscarProducto(txtBuscarProducto.getText());
        
    }//GEN-LAST:event_bntBclienteActionPerformed

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        // TODO add your handling code here:
    String texto = txtBuscarProducto.getText().trim();

    if (texto.isEmpty()) {
        MostrarTabla(); // 🔄 Si no escribe nada, mostrar todo
        return;
    }

    BuscarProducto(texto); // 🔍 Buscar automáticamente
       
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

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
            java.util.logging.Logger.getLogger(VISTA_PRODUCTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VISTA_PRODUCTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VISTA_PRODUCTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VISTA_PRODUCTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VISTA_PRODUCTO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntBcliente;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_Bproducto;
    private javax.swing.JTextField txtBuscarProducto;
    // End of variables declaration//GEN-END:variables
}
