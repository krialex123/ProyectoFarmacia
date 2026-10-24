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
public class VISTA_LABORATORIO extends javax.swing.JFrame {

     DefaultTableModel dt = new DefaultTableModel();
    CONEXION con = new CONEXION();
    Connection conex = null;
    Statement st;
    ResultSet rs;

    
 private CATALOGO_PRODUCTOS productosForm;  // variable para guardar la referencia

// ✅ Constructor normal
public VISTA_LABORATORIO() {
    initComponents();
    this.setLocationRelativeTo(null);
    MostrarLaboratorios();
    tabla_Blaboratorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

// ✅ Constructor que recibe el formulario principal
public VISTA_LABORATORIO(CATALOGO_PRODUCTOS productosForm) {
    initComponents();
    this.setLocationRelativeTo(null);
    this.productosForm = productosForm;
    MostrarLaboratorios();
    tabla_Blaboratorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}


    // ✅ MOSTRAR TABLA DE LABORATORIOS
    public void MostrarLaboratorios() {
        conex = con.ObtenerConexion();
        String titulos[] = {"CODIGO", "DESCRIPCION"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);

        try {
            st = conex.createStatement();
            rs = st.executeQuery("SELECT * FROM t_laboratorio");

            while (rs.next()) {
                Object filas[] = {
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION")
                };
                dt.addRow(filas);
            }
            tabla_Blaboratorio.setModel(dt);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error cargando laboratorios: " + e.getMessage());
        }
    }

    // ✅ FILTRAR POR DESCRIPCIÓN
    public void FiltrarLaboratorio(String texto) {
        conex = con.ObtenerConexion();
        String titulos[] = {"CODIGO", "DESCRIPCION"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);

        try {
            st = conex.createStatement();
            rs = st.executeQuery("SELECT * FROM t_laboratorio WHERE DESCRIPCION LIKE '%" + texto + "%'");

            while (rs.next()) {
                Object filas[] = {
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION")
                };
                dt.addRow(filas);
            }
            tabla_Blaboratorio.setModel(dt);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al filtrar: " + e.getMessage());
        }
    }    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_Blaboratorio = new javax.swing.JTable();
        bntBlaboratorio = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        txtbuscarlaboratorio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_Blaboratorio.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_Blaboratorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_BlaboratorioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_BlaboratorioMousePressed(evt);
            }
        });
        tabla_Blaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_BlaboratorioKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_Blaboratorio);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 125, 690, 310));

        bntBlaboratorio.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bntBlaboratorio.setText("Buscar");
        bntBlaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntBlaboratorioActionPerformed(evt);
            }
        });
        jPanel1.add(bntBlaboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, 40));

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

        txtbuscarlaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarlaboratorioKeyReleased(evt);
            }
        });
        jPanel1.add(txtbuscarlaboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 180, -1));

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

    private void tabla_BlaboratorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_BlaboratorioKeyReleased
        
    }//GEN-LAST:event_tabla_BlaboratorioKeyReleased

    private void tabla_BlaboratorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BlaboratorioMouseClicked

       int fila = tabla_Blaboratorio.rowAtPoint(evt.getPoint());
    if (fila > -1 && productosForm != null) {
        String codigo = tabla_Blaboratorio.getValueAt(fila, 0).toString();      // CODIGO
        String descripcion = tabla_Blaboratorio.getValueAt(fila, 1).toString(); // DESCRIPCION
        productosForm.setLaboratorioSeleccionado(codigo, descripcion);
        this.dispose();
    }
    }//GEN-LAST:event_tabla_BlaboratorioMouseClicked

    private void tabla_BlaboratorioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_BlaboratorioMousePressed

    }//GEN-LAST:event_tabla_BlaboratorioMousePressed

    private void bntBlaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBlaboratorioActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_bntBlaboratorioActionPerformed

    private void txtbuscarlaboratorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarlaboratorioKeyReleased
        // TODO add your handling code here:
       
        if (txtbuscarlaboratorio.getText().isEmpty()) {
            MostrarLaboratorios();
        } else {
            FiltrarLaboratorio(txtbuscarlaboratorio.getText());
        }
    }//GEN-LAST:event_txtbuscarlaboratorioKeyReleased

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
            java.util.logging.Logger.getLogger(VISTA_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VISTA_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VISTA_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VISTA_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new VISTA_LABORATORIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntBlaboratorio;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_Blaboratorio;
    private javax.swing.JTextField txtbuscarlaboratorio;
    // End of variables declaration//GEN-END:variables
}
