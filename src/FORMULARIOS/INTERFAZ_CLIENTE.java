/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AldairDesk
 */
public class INTERFAZ_CLIENTE extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    CONEXION con=new CONEXION();
    Connection conex=null;
    Statement st;
    ResultSet rs;
    
    public INTERFAZ_CLIENTE() {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarTabla();
    }

    public void MostrarTabla()
    {
        conex=con.ObtenerConexion();
        String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR"}; 
        DefaultTableModel dt = new DefaultTableModel(null,titulos);
        try
        {
        st=conex.createStatement();
        rs=st.executeQuery("SELECT * FROM T_CLIENTE");
        while(rs.next())
        {
            Object filas[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
            dt.addRow(filas);
        }
        tabla_clientes.setModel(dt);
        }
        catch(SQLException e)
        {
           e.printStackTrace();
        }
    }
    public void FiltrarDNI(String dni){
        try {
            st=conex.createStatement();
            rs=st.executeQuery("SELECT * FROM T_CLIENTE WHERE DNI LIKE '%"+txt_buscar.getText()+"%'");
            
             String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR"};
             DefaultTableModel dt = new DefaultTableModel(null,titulos);
        
            while(rs.next())
        {
            Object filas[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
            dt.addRow(filas);
        }
        tabla_clientes.setModel(dt);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void FiltrarNombre(String nom){
        try {
            st=conex.createStatement();
            rs=st.executeQuery("SELECT * FROM T_CLIENTE WHERE NOMBRES LIKE '%"+txt_buscar.getText()+"%'");
            
             String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR"};
             DefaultTableModel dt = new DefaultTableModel(null,titulos);
        
            while(rs.next())
        {
            Object filas[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
            dt.addRow(filas);
        }
        tabla_clientes.setModel(dt);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_clientes = new javax.swing.JTable();
        txt_buscar = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();
        rdo_dni = new javax.swing.JRadioButton();
        rdo_nombre = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla_clientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_clientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_clientesMousePressed(evt);
            }
        });
        tabla_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_clientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_clientes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 95, 690, 340));

        txt_buscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel1.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 200, 30));

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
        jPanel1.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, 50));

        rdo_dni.setBackground(new java.awt.Color(51, 51, 51));
        rdo_dni.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_dni.setForeground(new java.awt.Color(255, 255, 255));
        rdo_dni.setText("DNI");
        rdo_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_dniActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        rdo_nombre.setBackground(new java.awt.Color(51, 51, 51));
        rdo_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_nombre.setForeground(new java.awt.Color(255, 255, 255));
        rdo_nombre.setText("NOMBRE");
        rdo_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nombreActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/8275340 (2).jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
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

    private void rdo_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_dniActionPerformed
       if(rdo_dni.isSelected()==true)
       {
       rdo_nombre.setSelected(false);
       }
    }//GEN-LAST:event_rdo_dniActionPerformed

    private void rdo_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_nombreActionPerformed
       if(rdo_nombre.isSelected()==true)
       {
       rdo_dni.setSelected(false);
       }
    }//GEN-LAST:event_rdo_nombreActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
       if(rdo_dni.isSelected()==true)
        {
            FiltrarDNI(txt_buscar.getText().trim());
        }           
        else
        {
            if(rdo_nombre.isSelected()==true)
            {
                FiltrarNombre(txt_buscar.getText().trim());
            }  
        }
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void tabla_clientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_clientesKeyReleased
        
    }//GEN-LAST:event_tabla_clientesKeyReleased

    private void tabla_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_clientesMouseClicked

    }//GEN-LAST:event_tabla_clientesMouseClicked

    private void tabla_clientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_clientesMousePressed

    }//GEN-LAST:event_tabla_clientesMousePressed

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
            java.util.logging.Logger.getLogger(INTERFAZ_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new INTERFAZ_CLIENTE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_dni;
    private javax.swing.JRadioButton rdo_nombre;
    private javax.swing.JTable tabla_clientes;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
