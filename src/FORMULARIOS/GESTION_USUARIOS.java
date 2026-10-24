/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import CLASES.ADMINISTRAR_ALTA_USUARIO;
import CLASES.USUARIO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GESTION_USUARIOS extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    CLASES.ADMINISTRAR_ALTA_USUARIO ob = new ADMINISTRAR_ALTA_USUARIO();
    int op;
    
    CONEXION con=new CONEXION();
    Connection conex=null;
    Statement st;
    ResultSet rs;
    
    public GESTION_USUARIOS() {
        initComponents();
        this.setLocationRelativeTo(null);
       MostrarTabla();
       btn_nuevo.setEnabled(false);
       txt_dni.requestFocus();;
      
    }

    public void MostrarTabla()
    {
        conex=con.ObtenerConexion();
        String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR","FECHA DE NACIMIENTO","ROL","USUARIO","CONTRASENA"}; 
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
        tabla_usuarios.setModel(dt);
        }
        catch(SQLException e)
        {
           e.printStackTrace();
        }
    }
    public void FiltrarDNI(String dni){
        try {
            st=conex.createStatement();
            rs=st.executeQuery("SELECT * FROM t_usuario WHERE DNI LIKE '%"+txt_buscar.getText()+"%'");
            
             String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR","FECHA DE NACIMIENTO","ROL","USUARIO","CONTRASENA"};
             DefaultTableModel dt = new DefaultTableModel(null,titulos);
        
            while(rs.next())
        {
            Object filas[]={rs.getString(10),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
            dt.addRow(filas);
        }
        tabla_usuarios.setModel(dt);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void FiltrarNombre(String nom){
        try {
            st=conex.createStatement();
            rs=st.executeQuery("SELECT * FROM t_usuario WHERE NOMBRES LIKE '%"+txt_buscar.getText()+"%'");
            
             String titulos[]={"DNI","NOMBRES","APELLIDOS","SEXO","DIRECCION","CELULAR","FECHA DE NACIMIENTO","ROL","USUARIO","CONTRASENA"};
             DefaultTableModel dt = new DefaultTableModel(null,titulos);
        
            while(rs.next())
        {
            Object filas[]={rs.getString(10),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
            dt.addRow(filas);
        }
        tabla_usuarios.setModel(dt);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btn_registrar = new javax.swing.JButton();
        txt_dni = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        rdo_masculino = new javax.swing.JRadioButton();
        rdo_femenino = new javax.swing.JRadioButton();
        txt_direccion = new javax.swing.JTextField();
        txt_celular = new javax.swing.JTextField();
        txt_contraseña = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        cbo_roll = new javax.swing.JComboBox<>();
        btn_salir = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btn_nuevo = new javax.swing.JButton();
        rdo_dni = new javax.swing.JRadioButton();
        rdo_nombre = new javax.swing.JRadioButton();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_usuarios = new javax.swing.JTable();
        txt_fecha_nacimiento = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION Y REGISTRO DE NUEVO USUARIO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DNI");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 95, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NOMBRES");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 133, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("APELLIDOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 171, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SEXO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 211, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("DIRECCION");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 253, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CELULAR");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 288, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("FECHA DE NACIMIENTO");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 326, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("CARGO");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 364, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("USUARIO");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 395, -1, -1));

        btn_registrar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/solicitud-de-propuestas (1).gif"))); // NOI18N
        btn_registrar.setText("GUARDAR");
        btn_registrar.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 430, 120, 40));

        txt_dni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniKeyTyped(evt);
            }
        });
        jPanel1.add(txt_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 93, 210, -1));

        txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 131, 213, -1));

        txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_apellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 169, 213, -1));

        rdo_masculino.setBackground(new java.awt.Color(51, 51, 51));
        rdo_masculino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_masculino.setForeground(new java.awt.Color(255, 255, 255));
        rdo_masculino.setText("MASCULINO");
        rdo_masculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_masculinoActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_masculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 207, -1, -1));

        rdo_femenino.setBackground(new java.awt.Color(51, 51, 51));
        rdo_femenino.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_femenino.setForeground(new java.awt.Color(255, 255, 255));
        rdo_femenino.setText("FEMENINO");
        rdo_femenino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_femeninoActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_femenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 207, -1, -1));

        txt_direccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 248, 213, -1));

        txt_celular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_celularKeyTyped(evt);
            }
        });
        jPanel1.add(txt_celular, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 286, 213, -1));

        txt_contraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 431, 218, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("CONTRASEÑA");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 433, -1, -1));

        txt_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 393, 218, -1));

        cbo_roll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbo_roll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VENTAS", "ADMINISTRADOR" }));
        jPanel1.add(cbo_roll, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 362, 130, -1));

        btn_salir.setBackground(new java.awt.Color(255, 0, 0));
        btn_salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_salir.setForeground(new java.awt.Color(255, 255, 255));
        btn_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icons8-GO TO_newuser.png"))); // NOI18N
        btn_salir.setText("SALIR");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jPanel1.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 420, 110, 60));

        btn_actualizar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/actualizar (1)_1.gif"))); // NOI18N
        btn_actualizar.setText("ACTUALIZAR");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 430, -1, 40));

        btneliminar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btneliminar.setText("ELIMINAR");
        btneliminar.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 430, 80, 40));

        btn_nuevo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, 100, 40));

        rdo_dni.setBackground(new java.awt.Color(51, 51, 51));
        rdo_dni.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_dni.setForeground(new java.awt.Color(255, 255, 255));
        rdo_dni.setText("DNI");
        rdo_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_dniActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, -1, -1));

        rdo_nombre.setBackground(new java.awt.Color(51, 51, 51));
        rdo_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_nombre.setForeground(new java.awt.Color(255, 255, 255));
        rdo_nombre.setText("NOMBRE");
        rdo_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nombreActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, -1, -1));

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel1.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 180, -1));

        tabla_usuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_usuariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_usuarios);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 690, 330));

        try {
            txt_fecha_nacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(txt_fecha_nacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 80, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/image1.jpg"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 1090, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdo_femeninoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_femeninoActionPerformed
        if(rdo_femenino.isSelected()==true)
        {
        rdo_masculino.setSelected(false);
        }
    }//GEN-LAST:event_rdo_femeninoActionPerformed

    private void rdo_masculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_masculinoActionPerformed
        if(rdo_masculino.isSelected()==true)
        {
        rdo_femenino.setSelected(false);
        }
    }//GEN-LAST:event_rdo_masculinoActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed

        
        String dni,nom,ape,sexo="",dir,cel,fec,rol="",usu,cont;

        dni=this.txt_dni.getText();
        nom=this.txt_nombres.getText();
        ape=this.txt_apellidos.getText();
        
        if(rdo_masculino.isSelected()==true)
        {
            sexo = "MASCULINO";
        }
        if(rdo_femenino.isSelected()==true)
        {
            sexo = "FEMENINO";
        }

        dir=this.txt_direccion.getText();
        cel=this.txt_celular.getText();
        fec=this.txt_fecha_nacimiento.getText();
        
        op=cbo_roll.getSelectedIndex();
       
       switch(op)
       {
           case 0: rol=(String)this.cbo_roll.getSelectedItem();
           case 1: rol=(String)this.cbo_roll.getSelectedItem();
       }
        
        usu=this.txt_usuario.getText();
        cont=this.txt_contraseña.getText();
        ADMINISTRAR_ALTA_USUARIO reg=new ADMINISTRAR_ALTA_USUARIO();
        reg.GUARDAR(dni, nom, ape, sexo, dir, cel, fec, rol, usu, cont);
        MostrarTabla();
        btn_nuevo.setEnabled(false);
        // Convertir DD/MM/YYYY a YYYY-MM-DD
        String[] partes = fec.split("/");
        String fechaMysql = partes[2] + "-" + partes[1] + "-" + partes[0];

        
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
    this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
     try {
    // 1) Determinar sexo
    String sexo = "";
    if (rdo_masculino.isSelected()) {
        sexo = rdo_masculino.getText();
    } else if (rdo_femenino.isSelected()) {
        sexo = rdo_femenino.getText();
    }

    // ======================================================
    // 2) Convertir fecha DD/MM/YYYY → YYYY-MM-DD
    // ======================================================

    String fechaTexto = txt_fecha_nacimiento.getText().trim();

    if (fechaTexto.equals("")) {
        JOptionPane.showMessageDialog(null, "Ingrese la fecha de nacimiento");
        return;
    }

    // Acepta 1 o 2 dígitos en día y mes
    if (!fechaTexto.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
        JOptionPane.showMessageDialog(null, "Formato incorrecto. Use DD/MM/YYYY");
        return;
    }

    String[] partes = fechaTexto.split("/");

    // Asegurar 2 dígitos
    String dia = partes[0].length() == 1 ? "0" + partes[0] : partes[0];
    String mes = partes[1].length() == 1 ? "0" + partes[1] : partes[1];
    String año = partes[2];

    // Formato MySQL
    String fechaMysql = año + "-" + mes + "-" + dia;

    // ======================================================
    // 3) SQL UPDATE con PreparedStatement
    // ======================================================

    String sql = "UPDATE t_usuario SET "
            + "NOMBRES=?, APELLIDOS=?, SEXO=?, DIRECCION=?, CELULAR=?, "
            + "FECHA_NACIMIENTO=?, ROL=?, USUARIO=?, CONTRASENA=? "
            + "WHERE DNI=?";

    conex = con.ObtenerConexion();
    PreparedStatement pst = conex.prepareStatement(sql);

    pst.setString(1, txt_nombres.getText());
    pst.setString(2, txt_apellidos.getText());
    pst.setString(3, sexo);
    pst.setString(4, txt_direccion.getText());
    pst.setString(5, txt_celular.getText());  // celular en varchar funciona
    pst.setString(6, fechaMysql);             // FECHA CORREGIDA
    pst.setString(7, cbo_roll.getSelectedItem().toString());
    pst.setString(8, txt_usuario.getText());
    pst.setString(9, txt_contraseña.getText());
    pst.setString(10, txt_dni.getText());

    int filas = pst.executeUpdate();

    if (filas == 0) {
        JOptionPane.showMessageDialog(null, "⚠ No se actualizó ningún registro. Verifique el DNI.");
        return;
    }

    JOptionPane.showMessageDialog(null, "REGISTROS ACTUALIZADOS CORRECTAMENTE");

    // ======================================================
    // 4) Limpiar campos
    // ======================================================
    MostrarTabla();
    txt_dni.setEditable(true);
    txt_dni.setText("");
    txt_nombres.setText("");
    txt_apellidos.setText("");
    txt_direccion.setText("");
    txt_celular.setText("");
    txt_fecha_nacimiento.setText("");
    cbo_roll.setSelectedIndex(0);
    txt_usuario.setText("");
    txt_contraseña.setText("");
    txt_dni.requestFocus();

    btn_nuevo.setEnabled(false);
    btn_actualizar.setEnabled(false);
    btn_registrar.setEnabled(true);

} catch (SQLException sqlEx) {
    JOptionPane.showMessageDialog(null,
            "ERROR AL ACTUALIZAR LOS DATOS INGRESADOS:\n" + sqlEx.getMessage());
}
                       
    }//GEN-LAST:event_btn_actualizarActionPerformed

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

    private void tabla_usuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_usuariosMouseClicked
        
        int seleccion = tabla_usuarios.rowAtPoint(evt.getPoint());
        
        txt_dni.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 0)));
        txt_nombres.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 1)));
        txt_apellidos.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 2)));
        txt_direccion.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 4)));
        txt_celular.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 5)));
        txt_fecha_nacimiento.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 6)));
        cbo_roll.setSelectedItem(String.valueOf(tabla_usuarios.getValueAt(seleccion, 7)));
        txt_usuario.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 8)));
        txt_contraseña.setText(String.valueOf(tabla_usuarios.getValueAt(seleccion, 9)));
        txt_dni.setEditable(false);
        btn_registrar.setEnabled(false);
        btn_nuevo.setEnabled(true);
       btn_actualizar.setEnabled(true);
    }//GEN-LAST:event_tabla_usuariosMouseClicked

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        txt_dni.setText("");
        txt_nombres.setText("");
        txt_apellidos.setText("");
        txt_direccion.setText("");
        txt_celular.setText("");
        txt_fecha_nacimiento.setText("");
        cbo_roll.setSelectedItem(0);
        txt_usuario.setText("");
        txt_contraseña.setText("");
        txt_dni.setEditable(true);
        txt_dni.requestFocus();
        btn_registrar.setEnabled(true);
        btn_actualizar.setEnabled(false);
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void txt_dniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniKeyTyped
        char c=evt.getKeyChar();
        if(c<'0' || c>'9')evt.consume();
    }//GEN-LAST:event_txt_dniKeyTyped

    private void txt_celularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_celularKeyTyped
        char c=evt.getKeyChar();
        if(c<'0' || c>'9')evt.consume();
    }//GEN-LAST:event_txt_celularKeyTyped

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        
        // Verificar que haya un DNI seleccionado
    String dni = txt_dni.getText().trim();
    if (dni.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Seleccione o ingrese el DNI del usuario a eliminar.");
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro que desea eliminar el usuario con DNI " + dni + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
    );

    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            conex = con.ObtenerConexion();
            st = conex.createStatement();
            
            String sql = "DELETE FROM t_usuario WHERE DNI = '" + dni + "'";
            int filasAfectadas = st.executeUpdate(sql);

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "✅ Usuario eliminado correctamente.");
                MostrarTabla(); // Refrescar la tabla
                // Limpiar campos
                txt_dni.setText("");
                txt_nombres.setText("");
                txt_apellidos.setText("");
                txt_direccion.setText("");
                txt_celular.setText("");
                txt_fecha_nacimiento.setText("");
                cbo_roll.setSelectedIndex(0);
                txt_usuario.setText("");
                txt_contraseña.setText("");
                txt_dni.setEditable(true);
                btn_registrar.setEnabled(true);
                btn_actualizar.setEnabled(false);
                btn_nuevo.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ No se encontró ningún usuario con ese DNI.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al eliminar el usuario: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btneliminarActionPerformed

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
            java.util.logging.Logger.getLogger(GESTION_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GESTION_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GESTION_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GESTION_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GESTION_USUARIOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JButton btneliminar;
    private javax.swing.JComboBox<String> cbo_roll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdo_dni;
    private javax.swing.JRadioButton rdo_femenino;
    private javax.swing.JRadioButton rdo_masculino;
    private javax.swing.JRadioButton rdo_nombre;
    public static javax.swing.JTable tabla_usuarios;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_celular;
    private javax.swing.JTextField txt_contraseña;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_dni;
    private javax.swing.JFormattedTextField txt_fecha_nacimiento;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
