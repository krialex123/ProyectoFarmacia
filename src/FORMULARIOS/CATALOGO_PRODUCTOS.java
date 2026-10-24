/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIOS;

import CLASES.CONEXION;
import CLASES.PRODUCTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;




public class CATALOGO_PRODUCTOS extends javax.swing.JFrame {

    DefaultTableModel dt = new DefaultTableModel();
    private final CONEXION con = new CONEXION();

    public CATALOGO_PRODUCTOS() {
        initComponents();
        this.setLocationRelativeTo(null);
        MostrarTabla();
        mostrarAlertaProductosPorVencer();
        btn_actualizar.setEnabled(true);
        btn_eliminar.setEnabled(false);
        btn_nuevo.setEnabled(true);
    }
    
    private String labCodigoSeleccionado = null;

    private void mostrarAlertaProductosPorVencer() {

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, FVENCIMIENTO "
               + "FROM t_productos "
               + "WHERE FVENCIMIENTO BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 MONTH)";

    StringBuilder mensaje = new StringBuilder();

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            mensaje.append("• ")
                   .append(rs.getString("DESCRIPCION"))
                   .append(" | Lab: ")
                   .append(rs.getString("LABORATORIO"))
                   .append(" | Vence: ")
                   .append(rs.getDate("FVENCIMIENTO"))
                   .append("\n");
        }

        if (mensaje.length() > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "⚠ Productos que vencen dentro de 6 meses:\n\n" + mensaje,
                    "Alerta de vencimiento",
                    JOptionPane.WARNING_MESSAGE
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "No se pudo verificar productos por vencer.");
    }
}
    
    
public void setLaboratorioSeleccionado(String codigo, String descripcion) {
    this.labCodigoSeleccionado = codigo;   // <- se guarda el CÓDIGO (para la FK)
    txt_laboratorio.setText(descripcion);  // <- solo muestras la DESCRIPCIÓN
}

    // ===============================
    // 🔹 MOSTRAR TABLA + ALERTA
    // ===============================
    public void MostrarTabla() {

    String[] titulos = {
        "CODIGO", 
        "DESCRIPCION", 
        "LABORATORIO", 
        "STOCK", 
        "FECHA_VENCIMIENTO", 
        "PRECIO"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, FVENCIMIENTO, PRECIO FROM t_productos";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            Object[] fila = {
                rs.getString("CODIGO"),
                rs.getString("DESCRIPCION"),
                rs.getString("LABORATORIO"),
                rs.getInt("STOCK"),
                rs.getDate("FVENCIMIENTO"),
                rs.getDouble("PRECIO")
            };

            modelo.addRow(fila);
        }

        tabla_productos.setModel(modelo);

        tabla_productos.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                if (isSelected) {
                    c.setBackground(new java.awt.Color(153, 204, 255));
                    return c;
                }

                try {
                    Object fechaObj = table.getValueAt(row, 4);

                    if (fechaObj instanceof java.sql.Date) {
                        java.sql.Date fechaSQL = (java.sql.Date) fechaObj;
                        LocalDate venc = fechaSQL.toLocalDate();

                        long meses = ChronoUnit.MONTHS.between(LocalDate.now(), venc);

                        if (meses < 0) {
                            c.setBackground(new java.awt.Color(255, 102, 102));
                        } else if (meses <= 6) {
                            c.setBackground(new java.awt.Color(255, 204, 102));
                        } else {
                            c.setBackground(java.awt.Color.WHITE);
                        }

                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                    }

                } catch (Exception e) {
                    c.setBackground(java.awt.Color.WHITE);
                }

                return c;
            }
        });

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al cargar los productos."
        );
    }
}

    
    
    // =============================================
// 🔍 FILTRAR POR CÓDIGO
// =============================================
private void FiltrarCodigo(String texto) {

    String[] titulos = {
        "CODIGO",
        "DESCRIPCION",
        "LABORATORIO",
        "STOCK",
        "FECHA_VENCIMIENTO",
        "PRECIO"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, FVENCIMIENTO, PRECIO "
               + "FROM t_productos WHERE CODIGO LIKE ?";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {

        ps.setString(1, "%" + texto.trim() + "%");

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("LABORATORIO"),
                    rs.getInt("STOCK"),
                    rs.getDate("FVENCIMIENTO"),
                    rs.getDouble("PRECIO")
                });

            }

        }

        tabla_productos.setModel(modelo);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al buscar el producto."
        );

    }

}

// =============================================
// 🔍 FILTRAR POR DESCRIPCIÓN
// =============================================
private void FiltrarDescripcion(String texto) {

    String[] titulos = {
        "CODIGO",
        "DESCRIPCION",
        "LABORATORIO",
        "STOCK",
        "FECHA_VENCIMIENTO",
        "PRECIO"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "SELECT CODIGO, DESCRIPCION, LABORATORIO, STOCK, FVENCIMIENTO, PRECIO "
               + "FROM t_productos WHERE DESCRIPCION LIKE ?";

    try (Connection conex = con.ObtenerConexion();
         PreparedStatement ps = conex.prepareStatement(sql)) {

        ps.setString(1, "%" + texto.trim() + "%");

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                modelo.addRow(new Object[]{
                    rs.getString("CODIGO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("LABORATORIO"),
                    rs.getInt("STOCK"),
                    rs.getDate("FVENCIMIENTO"),
                    rs.getDouble("PRECIO")
                });

            }

        }

        tabla_productos.setModel(modelo);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al buscar el producto."
        );

    }
}
public void setLaboratorioSeleccionado(String descripcion) {
    txt_laboratorio.setText(descripcion);
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_precio = new javax.swing.JTextField();
        txt_descripcion = new javax.swing.JTextField();
        txt_codigo = new javax.swing.JTextField();
        txt_laboratorio = new javax.swing.JTextField();
        txt_stock = new javax.swing.JTextField();
        btn_eliminar = new javax.swing.JButton();
        btn_registrar = new javax.swing.JButton();
        btn_nuevo = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_productos = new javax.swing.JTable();
        btn_cancelar = new javax.swing.JButton();
        btnRproducto = new javax.swing.JButton();
        btnSlab = new javax.swing.JButton();
        rdo_codigo = new javax.swing.JRadioButton();
        date_vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        rdo_descripcion = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTO DE PRODUCTOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CODIGO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DESCRIPCION");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("LABORATORIO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("STOCK");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("F. VENCIMIENTO");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("PRECIO");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        txt_precio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_precio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 150, -1));

        txt_descripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_descripcion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 150, -1));

        txt_codigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 150, -1));

        txt_laboratorio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txt_laboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 130, -1));

        txt_stock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_stock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stockActionPerformed(evt);
            }
        });
        txt_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_stockKeyTyped(evt);
            }
        });
        jPanel1.add(txt_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 150, -1));

        btn_eliminar.setBackground(new java.awt.Color(204, 51, 0));
        btn_eliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/ELIMINAR.png"))); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, -1, 50));

        btn_registrar.setBackground(new java.awt.Color(0, 255, 0));
        btn_registrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_registrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/icon_usuario_reg_32px.png"))); // NOI18N
        btn_registrar.setText("REGISTRAR");
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, 50));

        btn_nuevo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, 110, 50));

        btn_actualizar.setBackground(new java.awt.Color(0, 255, 204));
        btn_actualizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/actualizar (1).gif"))); // NOI18N
        btn_actualizar.setText("ACTUALIZAR");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 150, 50));

        txt_buscar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_buscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel1.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 200, -1));

        tabla_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 460, 340));

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
        jPanel1.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, -1, 50));

        btnRproducto.setBackground(new java.awt.Color(153, 255, 153));
        btnRproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/NUEVO.png"))); // NOI18N
        btnRproducto.setText("LAB");
        btnRproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRproductoActionPerformed(evt);
            }
        });
        jPanel1.add(btnRproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 150, 70));

        btnSlab.setText("...");
        btnSlab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSlabActionPerformed(evt);
            }
        });
        jPanel1.add(btnSlab, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 40, -1));

        rdo_codigo.setBackground(new java.awt.Color(0, 0, 0));
        rdo_codigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_codigo.setForeground(new java.awt.Color(255, 255, 255));
        rdo_codigo.setText("CODIGO");
        rdo_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_codigoActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));
        jPanel1.add(date_vencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 150, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/—Pngtree—web banner 3d rendered .jpg"))); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 0, -1, -1));

        rdo_descripcion.setBackground(new java.awt.Color(0, 0, 0));
        rdo_descripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdo_descripcion.setForeground(new java.awt.Color(255, 255, 255));
        rdo_descripcion.setText("DESCRIPCION");
        rdo_descripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_descripcionActionPerformed(evt);
            }
        });
        jPanel1.add(rdo_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
       try (Connection conex = con.ObtenerConexion()) {

        PreparedStatement ps = conex.prepareStatement(
            "DELETE FROM t_productos WHERE CODIGO=?"
        );
        ps.setString(1, txt_codigo.getText());
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "🗑️ Producto eliminado correctamente.");
        MostrarTabla();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void rdo_descripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_descripcionActionPerformed
        if(rdo_descripcion.isSelected()==true)
        {
            rdo_codigo.setSelected(false);
        }
    }//GEN-LAST:event_rdo_descripcionActionPerformed

    private void rdo_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_codigoActionPerformed
        if(rdo_codigo.isSelected()==true)
        {
            rdo_descripcion.setSelected(false);
        }
    }//GEN-LAST:event_rdo_codigoActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void tabla_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_productosMouseClicked
       
        int fila = tabla_productos.rowAtPoint(evt.getPoint());
        txt_codigo.setText(String.valueOf(tabla_productos.getValueAt(fila, 0)));
        txt_descripcion.setText(String.valueOf(tabla_productos.getValueAt(fila, 1)));
        txt_laboratorio.setText(String.valueOf(tabla_productos.getValueAt(fila, 2)));
        txt_stock.setText(String.valueOf(tabla_productos.getValueAt(fila, 3)));
        java.util.Date fecha = (java.util.Date) tabla_productos.getValueAt(fila, 4);
        date_vencimiento.setDate(fecha);
        txt_precio.setText(String.valueOf(tabla_productos.getValueAt(fila, 5)));
    }//GEN-LAST:event_tabla_productosMouseClicked

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        String texto = txt_buscar.getText().trim();
        if (rdo_codigo.isSelected()) FiltrarCodigo(texto);
        else if (rdo_descripcion.isSelected()) FiltrarDescripcion(texto);
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        txt_codigo.setText("");
        txt_descripcion.setText("");
        txt_stock.setText("");
        txt_precio.setText("");
        txt_laboratorio.setText("");
        txt_codigo.requestFocus();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        try (Connection conex = con.ObtenerConexion()) {

        if (labCodigoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "⚠ Debe seleccionar un laboratorio");
            return;
        }

        PreparedStatement ps = conex.prepareStatement(
            "INSERT INTO t_productos (CODIGO, DESCRIPCION, LABORATORIO, STOCK, FVENCIMIENTO, PRECIO) "
            + "VALUES (?, ?, ?, ?, ?, ?)"
        );

        ps.setString(1, txt_codigo.getText());
        ps.setString(2, txt_descripcion.getText());
        ps.setString(3, labCodigoSeleccionado);
        ps.setInt(4, Integer.parseInt(txt_stock.getText()));

        java.util.Date fecha = date_vencimiento.getDate();
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        ps.setDate(5, sqlDate);

        ps.setDouble(6, Double.parseDouble(txt_precio.getText()));

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "✅ Producto registrado correctamente.");
        MostrarTabla();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "❌ Error al registrar: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void txt_stockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stockKeyTyped
        char c=evt.getKeyChar();
        if(c<'0' || c>'9')evt.consume();
    }//GEN-LAST:event_txt_stockKeyTyped

    private void btnRproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRproductoActionPerformed
        // TODO add your handling code here:
        
        REGISTRAR_LABORATORIO buscador = new REGISTRAR_LABORATORIO();
        buscador.setVisible(true);
        buscador.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnRproductoActionPerformed

    private void txt_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stockActionPerformed

    private void btnSlabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSlabActionPerformed
        // TODO add your handling code here:
    VISTA_LABORATORIO vista = new VISTA_LABORATORIO(this);
    vista.setVisible(true);
    }//GEN-LAST:event_btnSlabActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        
        try (Connection conex = con.ObtenerConexion()) {

        if (labCodigoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "⚠ Debe seleccionar un laboratorio");
            return;
        }

        PreparedStatement ps = conex.prepareStatement(
            "UPDATE t_productos SET DESCRIPCION=?, LABORATORIO=?, STOCK=?, FVENCIMIENTO=?, PRECIO=? "
            + "WHERE CODIGO=?"
        );

        ps.setString(1, txt_descripcion.getText());
        ps.setString(2, labCodigoSeleccionado);
        ps.setInt(3, Integer.parseInt(txt_stock.getText()));

        java.util.Date fecha = date_vencimiento.getDate();
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        ps.setDate(4, sqlDate);

        ps.setDouble(5, Double.parseDouble(txt_precio.getText()));
        ps.setString(6, txt_codigo.getText());

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "✅ Producto actualizado correctamente.");
        MostrarTabla();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "❌ Error al actualizar: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_actualizarActionPerformed
      
        
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
            java.util.logging.Logger.getLogger(CATALOGO_PRODUCTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CATALOGO_PRODUCTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CATALOGO_PRODUCTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CATALOGO_PRODUCTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CATALOGO_PRODUCTOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRproducto;
    private javax.swing.JButton btnSlab;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_registrar;
    private com.toedter.calendar.JDateChooser date_vencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_codigo;
    private javax.swing.JRadioButton rdo_descripcion;
    private javax.swing.JTable tabla_productos;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_laboratorio;
    private javax.swing.JTextField txt_precio;
    private javax.swing.JTextField txt_stock;
    // End of variables declaration//GEN-END:variables
}
