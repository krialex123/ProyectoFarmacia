package FORMULARIOS;

import FORMULARIOS.INTERFAZ_MENU_PRINCIPAL;
import CLASES.CONEXION;
import CLASES.VALIDAR_INICIO;
import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AUTENTICACION_USUARIO extends javax.swing.JFrame {

    Connection conex=null;
    Statement st;
    ResultSet rs;

    public AUTENTICACION_USUARIO() {
        
       initComponents();
       txtpas_contraseña.addActionListener(e -> btn_ingresarActionPerformed(null));
txt_usuario.addActionListener(e -> btn_ingresarActionPerformed(null));

       this.setLocationRelativeTo(null);
 
      CONEXION conect = new CONEXION();
      Connection con = conect.ObtenerConexion();
      INTERFAZ_MENU_PRINCIPAL men = new INTERFAZ_MENU_PRINCIPAL();
       
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtpas_contraseña = new javax.swing.JPasswordField();
        txt_usuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_ingresar = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbo_rol = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtpas_contraseña.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtpas_contraseña.setForeground(new java.awt.Color(204, 204, 204));
        txtpas_contraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpas_contraseña.setBorder(null);
        txtpas_contraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpas_contraseñaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtpas_contraseñaMousePressed(evt);
            }
        });
        txtpas_contraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpas_contraseñaActionPerformed(evt);
            }
        });
        txtpas_contraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpas_contraseñaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpas_contraseñaKeyTyped(evt);
            }
        });
        jPanel1.add(txtpas_contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 190, 30));

        txt_usuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_usuario.setForeground(new java.awt.Color(204, 204, 204));
        txt_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_usuario.setText("Ingrese usuario");
        txt_usuario.setBorder(null);
        txt_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_usuarioMousePressed(evt);
            }
        });
        txt_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usuarioActionPerformed(evt);
            }
        });
        txt_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_usuarioKeyPressed(evt);
            }
        });
        jPanel1.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 190, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-USER3_AUTENTICATION.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, -1, -1));

        btn_ingresar.setBackground(new java.awt.Color(0, 255, 51));
        btn_ingresar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_ingresar.setForeground(new java.awt.Color(255, 255, 255));
        btn_ingresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-SIGN IN_newuser.png"))); // NOI18N
        btn_ingresar.setText("INICIAR SESION");
        btn_ingresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ingresar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_ingresar.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 160, 50));

        btn_salir.setBackground(new java.awt.Color(255, 0, 0));
        btn_salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_salir.setForeground(new java.awt.Color(255, 255, 255));
        btn_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-GO TO_newuser.png"))); // NOI18N
        btn_salir.setText("CERRAR SESION");
        btn_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salir.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_salir.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jPanel1.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, -1, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FARMAVITAL");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("BOTICA");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CARGO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        cbo_rol.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cbo_rol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VENTAS", "ADMINISTRADOR" }));
        jPanel1.add(cbo_rol, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 140, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NEW_FONDO_LOGGIN.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
   
        String usuario = txt_usuario.getText().trim();
    String clave = String.valueOf(txtpas_contraseña.getPassword()).trim();
    String rolSeleccionado = cbo_rol.getSelectedItem().toString();  // ADMINISTRADOR o VENTAS

    if (usuario.isEmpty() || usuario.equals("Ingrese usuario")) {
        JOptionPane.showMessageDialog(this, "⚠ Ingrese su usuario");
        txt_usuario.requestFocus();
        return;
    }

    if (clave.isEmpty()) {
        JOptionPane.showMessageDialog(this, "⚠ Ingrese su contraseña");
        txtpas_contraseña.requestFocus();
        return;
    }

    try {
        CONEXION cn = new CONEXION();
        Connection conex = cn.ObtenerConexion();

        PreparedStatement ps = conex.prepareStatement(
            "SELECT DNI, NOMBRES, APELLIDOS, ROL FROM t_usuario " +
            "WHERE USUARIO = ? AND CONTRASENA = ? AND ROL = ?"
        );

        ps.setString(1, usuario);
        ps.setString(2, clave);
        ps.setString(3, rolSeleccionado);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            // ================================
            // 🔥 GUARDAMOS DATOS DE LA SESIÓN
            // ================================
            CLASES.SesionUsuario.DNI = rs.getString("DNI");
            CLASES.SesionUsuario.NOMBRE =
                rs.getString("NOMBRES") + " " + rs.getString("APELLIDOS");

            CLASES.SesionUsuario.ROL = rs.getString("ROL");   // <<--- OBLIGATORIO

            JOptionPane.showMessageDialog(this,
                "✅ Bienvenido " + CLASES.SesionUsuario.NOMBRE);

            // ABRIR EL MENU PRINCIPAL
            INTERFAZ_MENU_PRINCIPAL men = new INTERFAZ_MENU_PRINCIPAL();
            men.setVisible(true);

            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Usuario/Contraseña/Rol incorrectos",
                "Acceso denegado",
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_ingresarActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
       System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void txt_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usuarioActionPerformed
       
    }//GEN-LAST:event_txt_usuarioActionPerformed

    private void txt_usuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_usuarioMousePressed
        if (txt_usuario.getText().equals("Ingrese usuario")) {
        txt_usuario.setText("");
        txt_usuario.setForeground(Color.BLACK);
    }
    }//GEN-LAST:event_txt_usuarioMousePressed

    private void txt_usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usuarioKeyPressed

    }//GEN-LAST:event_txt_usuarioKeyPressed

    private void txtpas_contraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpas_contraseñaKeyTyped
 if (String.valueOf(txtpas_contraseña.getPassword()).equals("Contraseña")) {
        txtpas_contraseña.setText("");
        txtpas_contraseña.setForeground(Color.BLACK);
        txtpas_contraseña.setEchoChar('•');
    }
    }//GEN-LAST:event_txtpas_contraseñaKeyTyped

    private void txtpas_contraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpas_contraseñaKeyPressed

    }//GEN-LAST:event_txtpas_contraseñaKeyPressed

    private void txtpas_contraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpas_contraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpas_contraseñaActionPerformed

    private void txtpas_contraseñaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpas_contraseñaMousePressed
        this.txtpas_contraseña.setText("");
        this.txtpas_contraseña.setForeground(Color.black);// TODO add your handling code here:
    }//GEN-LAST:event_txtpas_contraseñaMousePressed

    private void txtpas_contraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpas_contraseñaMouseClicked
        // TODO add your handling code here:
     if (String.valueOf(txtpas_contraseña.getPassword()).equals("Ingresar contraseña")) {
        txtpas_contraseña.setText("");
        txtpas_contraseña.setForeground(Color.black);
        txtpas_contraseña.setEchoChar('•'); // Activar puntos
    }
    }//GEN-LAST:event_txtpas_contraseñaMouseClicked

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
            java.util.logging.Logger.getLogger(AUTENTICACION_USUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AUTENTICACION_USUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AUTENTICACION_USUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AUTENTICACION_USUARIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AUTENTICACION_USUARIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JComboBox<String> cbo_rol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_usuario;
    private javax.swing.JPasswordField txtpas_contraseña;
    // End of variables declaration//GEN-END:variables
}
