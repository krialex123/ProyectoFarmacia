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
import javax.swing.ListSelectionModel;

/**
 *
 * @author AldairDesk
 */
public class VISTA_CLIENTE extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    CONEXION con = new CONEXION();
    Connection conex = null;
    Statement st;
    ResultSet rs;
    
private REGISTRO_VENTAS ventasForm;  // variable para guardar la referencia


public VISTA_CLIENTE() {
    initComponents();
    this.setLocationRelativeTo(null);
    MostrarTabla();
    tabla_Bclientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}


    public VISTA_CLIENTE(REGISTRO_VENTAS ventasForm) {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarTabla();
        tabla_Bclientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
    }

    // ===============================
    // ✅ MOSTRAR TODOS LOS CLIENTES
    // ===============================
    public void MostrarTabla() {
         conex = con.ObtenerConexion();
    String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "CELULAR"};
    DefaultTableModel dt = new DefaultTableModel(null, titulos);
    try {
        st = conex.createStatement();
        rs = st.executeQuery("SELECT DNI, NOMBRES, APELLIDOS, SEXO, CELULAR FROM t_cliente");

        while (rs.next()) {
            Object filas[] = {
                rs.getString("DNI"),
                rs.getString("NOMBRES"),
                rs.getString("APELLIDOS"),
                rs.getString("SEXO"),
                rs.getString("CELULAR")
            };
            dt.addRow(filas);
        }
        tabla_Bclientes.setModel(dt);
        System.out.println("✅ Clientes cargados");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error cargando clientes: " + e.getMessage());
    }
    }

    // ===============================
    // ✅ FILTRAR POR DNI
    // ===============================
    public void FiltrarDNI(String dni) {
        conex = con.ObtenerConexion();
        String titulos[] = {"DNI", "NOMBRES", "APELLIDOS", "SEXO", "DIRECCION", "CELULAR"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);

        try {
            st = conex.createStatement();
            rs = st.executeQuery("SELECT * FROM t_cliente WHERE DNI LIKE '%" + dni + "%'");

            while (rs.next()) {
                Object filas[] = {
                    rs.getString("DNI"),
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOS"),
                    rs.getString("SEXO"),
                    rs.getString("DIRECCION"),
                    rs.getString("CELULAR")
                };
                dt.addRow(filas);
            }
            tabla_Bclientes.setModel(dt);

        } catch (SQLException e) {
            e.printStackTrace();
        }}
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_Bclientes = new javax.swing.JTable();
        bntBcliente = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        txtbuscarcliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_Bclientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_Bclientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_BclientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_BclientesMousePressed(evt);
            }
        });
        tabla_Bclientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_BclientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_Bclientes);

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

        txtbuscarcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarclienteKeyReleased(evt);
            }
        });
        jPanel1.add(txtbuscarcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 180, -1));

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
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void tabla_BclientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_BclientesKeyReleased
        
    }//GEN-LAST:event_tabla_BclientesKeyReleased

    private void tabla_BclientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BclientesMouseClicked

       int fila = tabla_Bclientes.rowAtPoint(evt.getPoint());

    if (fila > -1) {
        String dni = tabla_Bclientes.getValueAt(fila, 0).toString();
        String nombre = tabla_Bclientes.getValueAt(fila, 1).toString() + " " +
                        tabla_Bclientes.getValueAt(fila, 2).toString();
        String celular = tabla_Bclientes.getValueAt(fila, 4).toString();

        REGISTRO_VENTAS rv = new REGISTRO_VENTAS();
        rv.SeleccionarCliente(dni, nombre, celular);
        rv.setVisible(true);

        this.dispose();
    }
    }//GEN-LAST:event_tabla_BclientesMouseClicked

    private void tabla_BclientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BclientesMousePressed

    }//GEN-LAST:event_tabla_BclientesMousePressed

    private void bntBclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBclienteActionPerformed
        // TODO add your handling code here:
        
        FiltrarDNI(txtbuscarcliente.getText());
    }//GEN-LAST:event_bntBclienteActionPerformed

    private void txtbuscarclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarclienteKeyReleased
        // TODO add your handling code here:
         if (txtbuscarcliente.getText().isEmpty()) {
            MostrarTabla();
        } else {
            FiltrarDNI(txtbuscarcliente.getText());
        }
    }//GEN-LAST:event_txtbuscarclienteKeyReleased

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
            java.util.logging.Logger.getLogger(VISTA_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VISTA_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VISTA_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VISTA_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VISTA_CLIENTE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntBcliente;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_Bclientes;
    private javax.swing.JTextField txtbuscarcliente;
    // End of variables declaration//GEN-END:variables
}
