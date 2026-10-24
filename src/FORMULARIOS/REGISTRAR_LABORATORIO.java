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
 * @author Krialex
 */
public class REGISTRAR_LABORATORIO extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    CONEXION con = new CONEXION();
    Connection conex = null;
    Statement st;
    ResultSet rs;
    



public REGISTRAR_LABORATORIO() {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarLaboratorios();
        tabla_Laboratorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}


    public REGISTRAR_LABORATORIO(REGISTRO_VENTAS ventasForm) {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarLaboratorios();
        tabla_Laboratorio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
    }

// ✅ MOSTRAR LABORATORIOS
    public void MostrarLaboratorios() {
        conex = con.ObtenerConexion();
        String titulos[] = {"CÓDIGO", "DESCRIPCIÓN"};
        DefaultTableModel dt = new DefaultTableModel(null, titulos);

        try {
            st = conex.createStatement();
            rs = st.executeQuery("SELECT CODIGO, DESCRIPCION FROM t_laboratorio");

            while (rs.next()) {
                Object fila[] = {
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION")
                };
                dt.addRow(fila);
            }

            tabla_Laboratorio.setModel(dt);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error cargando laboratorios: " + e.getMessage());
        }
    }
    
    

    // ✅ GUARDAR LABORATORIO
    public void GuardarLaboratorio() {
        String codigo = txtCodigoLab.getText();
    String descripcion = txtNombreLab.getText();

    if (codigo.isEmpty() || descripcion.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "⚠️ Complete todos los campos.",
                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String sql = "INSERT INTO t_laboratorio (CODIGO, DESCRIPCION) VALUES (?, ?)";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {

        ps.setString(1, codigo);
        ps.setString(2, descripcion);
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "✅ Laboratorio registrado correctamente");
        MostrarLaboratorios();
        txtCodigoLab.setText("");
        txtNombreLab.setText("");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "❌ Error al registrar: " + e.getMessage());
    }
    }

    // ✅ ELIMINAR LABORATORIO
    public void EliminarLaboratorio() {
        String codigo = txtCodigoLab.getText();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione un laboratorio de la tabla.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "DELETE FROM t_laboratorio WHERE CODIGO = ?";

        try (Connection conex = con.ObtenerConexion();
             PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "🗑 Laboratorio eliminado");
            MostrarLaboratorios();
            txtCodigoLab.setText("");
            txtNombreLab.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al eliminar: " + e.getMessage());
        }

    
}
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_Laboratorio = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        bntEliminarLab = new javax.swing.JButton();
        bntGuardarLab = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        txtCodigoLab = new javax.swing.JTextField();
        txtNombreLab = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_Laboratorio.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_Laboratorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_LaboratorioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_LaboratorioMousePressed(evt);
            }
        });
        tabla_Laboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_LaboratorioKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_Laboratorio);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 750, 220));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Codigo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        bntEliminarLab.setBackground(new java.awt.Color(51, 255, 204));
        bntEliminarLab.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bntEliminarLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        bntEliminarLab.setText("Eliminar");
        bntEliminarLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEliminarLabActionPerformed(evt);
            }
        });
        jPanel1.add(bntEliminarLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 180, 50));

        bntGuardarLab.setBackground(new java.awt.Color(51, 255, 204));
        bntGuardarLab.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        bntGuardarLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        bntGuardarLab.setText("Guardar");
        bntGuardarLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGuardarLabActionPerformed(evt);
            }
        });
        jPanel1.add(bntGuardarLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 180, 50));

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
        jPanel1.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, -1, 50));

        txtCodigoLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoLabKeyReleased(evt);
            }
        });
        jPanel1.add(txtCodigoLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 140, -1));
        jPanel1.add(txtNombreLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 210, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/8275340 (2).jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 800, 460));

        jLabel3.setText("Codigo");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

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

    private void tabla_LaboratorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_LaboratorioKeyReleased
        
    }//GEN-LAST:event_tabla_LaboratorioKeyReleased

    private void tabla_LaboratorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_LaboratorioMouseClicked

       int fila = tabla_Laboratorio.rowAtPoint(evt.getPoint());

        txtCodigoLab.setText(tabla_Laboratorio.getValueAt(fila, 0).toString());
        txtNombreLab.setText(tabla_Laboratorio.getValueAt(fila, 1).toString());
    
    }//GEN-LAST:event_tabla_LaboratorioMouseClicked

    private void tabla_LaboratorioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_LaboratorioMousePressed

    }//GEN-LAST:event_tabla_LaboratorioMousePressed

    private void bntEliminarLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEliminarLabActionPerformed
        // TODO add your handling code here:
        
        EliminarLaboratorio();
        
        
    }//GEN-LAST:event_bntEliminarLabActionPerformed

    private void txtCodigoLabKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoLabKeyReleased
        // TODO add your handling code here:
         
    }//GEN-LAST:event_txtCodigoLabKeyReleased

    private void bntGuardarLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGuardarLabActionPerformed
        // TODO add your handling code here:
        GuardarLaboratorio();
    }//GEN-LAST:event_bntGuardarLabActionPerformed

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
            java.util.logging.Logger.getLogger(REGISTRAR_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(REGISTRAR_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(REGISTRAR_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(REGISTRAR_LABORATORIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new REGISTRAR_LABORATORIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntEliminarLab;
    private javax.swing.JButton bntGuardarLab;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla_Laboratorio;
    private javax.swing.JTextField txtCodigoLab;
    private javax.swing.JTextField txtNombreLab;
    // End of variables declaration//GEN-END:variables
}
