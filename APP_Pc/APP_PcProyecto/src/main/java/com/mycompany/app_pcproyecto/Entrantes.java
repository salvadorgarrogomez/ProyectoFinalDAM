/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.app_pcproyecto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ro
 */
public class Entrantes extends javax.swing.JDialog {

    private void insertarPlatoEntrante() {
        String nombre = jTextNombre.getText();
        BigDecimal precio = new BigDecimal(jTextPrecio.getText());
        boolean racionCompleta = jCheckBoxCompleta.isSelected();
        boolean mediaRacion = jCheckBoxMedia.isSelected();
        boolean precioUnidad = jCheckBoxUnidad.isSelected();

        String url = "jdbc:postgresql://localhost:5432/Bar_ElEscobar";
        String usuario = "bar_Admin";
        String contraseña = "Admin123456";

        try (Connection con = DriverManager.getConnection(url, usuario, contraseña)) {
            // Verificar si el plato ya existe
            boolean platoNoExiste = true;
            if (mediaRacion) {
                platoNoExiste = platoNoExiste(con, nombre, true);
            } else if (racionCompleta) {
                platoNoExiste = platoNoExiste(con, nombre, false);
            }

            if (platoNoExiste) {
                String sql = "INSERT INTO entrantes (nombre, precio, racion_completa, media_racion, precio_unidad) "
                        + "VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, nombre);
                    pstmt.setBigDecimal(2, precio);
                    pstmt.setBoolean(3, racionCompleta);
                    pstmt.setBoolean(4, mediaRacion);
                    pstmt.setBoolean(5, precioUnidad);
                    pstmt.executeUpdate();
                }
                limpiarCampos();
            } else {
                // Mostrar mensaje de error
                System.out.println("El plato ya existe en la base de datos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            System.out.println("El plato no se ha guardado correctamente, volver a intentar.");
        }
    }

    private void cargarPlatos() {
        String url = "jdbc:postgresql://localhost:5432/Bar_ElEscobar";
        String usuario = "bar_Admin";
        String contraseña = "Admin123456";

        try (Connection con = DriverManager.getConnection(url, usuario, contraseña)) {
            String sql = "SELECT id, nombre, precio FROM entrantes ORDER BY id";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboBoxActualizar.removeAllItems(); // Eliminar todos los elementos existentes
                    while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        BigDecimal precio = rs.getBigDecimal("precio");
                        String item = id + ": " + nombre + " - " + precio.toString();
                        jComboBoxActualizar.addItem(item);
                    }
                }
            }
            System.out.println("Base de datos actualizada correctamente");
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            System.out.println("Error al cargar los platos.");
        }
    }

    private boolean platoNoExiste(Connection con, String nombre, boolean mediaRacion) throws SQLException {
        String sql;
        if (mediaRacion) {
            sql = "SELECT COUNT(*) FROM entrantes WHERE nombre = ? AND media_racion = true";
        } else {
            sql = "SELECT COUNT(*) FROM entrantes WHERE nombre = ? AND racion_completa = true";
        }
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
            }
        }
        return false;
    }

    private void limpiarCampos() {
        jTextNombre.setText("");
        jTextPrecio.setText("");
        jCheckBoxMedia.setSelected(false);
        jCheckBoxCompleta.setSelected(false);
        jCheckBoxUnidad.setSelected(false);
    }

    /**
     * Creates new form Entrantes
     */
    public Entrantes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarPlatos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jButtonGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBoxMedia = new javax.swing.JCheckBox();
        jCheckBoxCompleta = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jCheckBoxUnidad = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxActualizar = new javax.swing.JComboBox<>();
        jButtonActualizar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCheckBoxUnidadActualizar = new javax.swing.JCheckBox();
        jCheckBoxMediaActualizar = new javax.swing.JCheckBox();
        jCheckBoxCompletaActualizar = new javax.swing.JCheckBox();
        jTextPrecioActualizar = new javax.swing.JTextField();
        jTextNombreActualizar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuAtras = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingresar nuevo plato al menu de Entrantes:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Precio:");

        jLabel4.setText("Media racion:");

        jLabel5.setText("Racion completa:");

        jCheckBoxMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMediaActionPerformed(evt);
            }
        });

        jCheckBoxCompleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCompletaActionPerformed(evt);
            }
        });

        jLabel6.setText("Precio Unidad:");

        jCheckBoxUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUnidadActionPerformed(evt);
            }
        });

        jLabel7.setText("Actualizar plato:");

        jComboBoxActualizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecciona un plato >" }));
        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });

        jButtonActualizar.setText("Actualizar plato");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Nombre:");

        jLabel9.setText("Precio:");

        jLabel10.setText("Media racion:");

        jLabel11.setText("Racion completa:");

        jLabel12.setText("Precio Unidad:");

        jCheckBoxUnidadActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUnidadActualizarActionPerformed(evt);
            }
        });

        jCheckBoxMediaActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMediaActualizarActionPerformed(evt);
            }
        });

        jCheckBoxCompletaActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCompletaActualizarActionPerformed(evt);
            }
        });

        jLabel13.setText("ID");

        jTextFieldID.setEditable(false);

        jMenuAtras.setText("Atras");
        jMenuAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAtrasActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuAtras);

        jMenu2.setText("Ensaladas");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonActualizar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jComboBoxActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(41, 41, 41)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jCheckBoxCompleta)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButtonGuardar))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jCheckBoxMedia)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel6)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jCheckBoxUnidad))))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel3))
                                            .addGap(66, 66, 66)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextPrecio)
                                                .addComponent(jTextNombre))))
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(41, 41, 41)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jCheckBoxCompletaActualizar)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jCheckBoxMediaActualizar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBoxUnidadActualizar))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(66, 66, 66)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextPrecioActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextNombreActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMedia)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jCheckBoxCompleta)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jCheckBoxUnidad))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGuardar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNombreActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPrecioActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMediaActualizar)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jCheckBoxCompletaActualizar)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12)
                        .addComponent(jCheckBoxUnidadActualizar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonActualizar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        // TODO add your handling code here:
        insertarPlatoEntrante();
        cargarPlatos();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jCheckBoxMediaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMediaActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMedia.isSelected()) {
            jCheckBoxCompleta.setSelected(false);
            jCheckBoxUnidad.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxMediaActionPerformed

    private void jCheckBoxCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCompletaActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCompleta.isSelected()) {
            jCheckBoxMedia.setSelected(false);
            jCheckBoxUnidad.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCompletaActionPerformed

    private void jCheckBoxUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUnidadActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxUnidad.isSelected()) {
            jCheckBoxMedia.setSelected(false);
            jCheckBoxCompleta.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxUnidadActionPerformed

    private void jComboBoxActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxActualizarActionPerformed
        // TODO add your handling code here:
        jComboBoxActualizar.setSelectedItem("<Selecciona un plato>");
        String seleccion = (String) jComboBoxActualizar.getSelectedItem();
        if (seleccion != null && !seleccion.equals("< Selecciona un plato >")) {
            String[] parts = seleccion.split(": ");
            if (parts.length != 2) {
                System.out.println("Error al obtener el nombre del plato.");
                return;
            }
            int idPlato;
            try {
                idPlato = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException ex) {
                System.out.println("Error al obtener el ID del plato.");
                return;
            }

            String url = "jdbc:postgresql://localhost:5432/Bar_ElEscobar";
            String usuario = "bar_Admin";
            String contraseña = "Admin123456";

            try (Connection con = DriverManager.getConnection(url, usuario, contraseña)) {
                String sql = "SELECT id, nombre, precio, racion_completa, media_racion, precio_unidad FROM entrantes WHERE id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setInt(1, idPlato);// Usar el ID del plato obtenido correctamente
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            jTextFieldID.setText(rs.getInt("id") + ""); // Establece el ID del plato
                            jTextNombreActualizar.setText(rs.getString("nombre")); // Establece el nombre del plato
                            jTextPrecioActualizar.setText(rs.getBigDecimal("precio").toString()); // Establece el precio del plato
                            jCheckBoxCompletaActualizar.setSelected(rs.getBoolean("racion_completa")); // Establece el valor de racion_completa
                            jCheckBoxMediaActualizar.setSelected(rs.getBoolean("media_racion")); // Establece el valor de media_racion
                            jCheckBoxUnidadActualizar.setSelected(rs.getBoolean("precio_unidad")); // Establece el valor de precio_unidad
                        }
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                // Manejo del error, por ejemplo, mostrar un mensaje al usuario
                System.out.println("Error al obtener los datos del plato.");
            }
        } else {
            // Vaciar los campos
            jTextFieldID.setText("");
            jTextNombreActualizar.setText("");
            jTextPrecioActualizar.setText("");
            jCheckBoxCompletaActualizar.setSelected(false);
            jCheckBoxMediaActualizar.setSelected(false);
            jCheckBoxUnidadActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jComboBoxActualizarActionPerformed

    private void jCheckBoxUnidadActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUnidadActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxUnidadActualizar.isSelected()) {
            jCheckBoxCompletaActualizar.setSelected(false);
            jCheckBoxMediaActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxUnidadActualizarActionPerformed

    private void jCheckBoxMediaActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMediaActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMediaActualizar.isSelected()) {
            jCheckBoxCompletaActualizar.setSelected(false);
            jCheckBoxUnidadActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxMediaActualizarActionPerformed

    private void jCheckBoxCompletaActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCompletaActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCompletaActualizar.isSelected()) {
            jCheckBoxUnidadActualizar.setSelected(false);
            jCheckBoxMediaActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCompletaActualizarActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        // TODO add your handling code here:
        String selectedPlato = (String) jComboBoxActualizar.getSelectedItem();

        // Obtener el ID del plato seleccionado
        String[] parts = selectedPlato.split(" - ");
        if (parts.length != 2) {
            System.out.println("Error al obtener el nombre del plato.");
            return;
        }
        int idPlato;
        try {
            idPlato = Integer.parseInt(jTextFieldID.getText().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Error al obtener el ID del plato.");
            return;
        }

        // Obtener los nuevos valores
        String nuevoNombre;
        BigDecimal nuevoPrecio;
        boolean racionCompleta;
        boolean mediaRacion;
        boolean precioUnidad;
        try {
            nuevoNombre = jTextNombreActualizar.getText();
            nuevoPrecio = new BigDecimal(jTextPrecioActualizar.getText());
            racionCompleta = jCheckBoxCompletaActualizar.isSelected();
            mediaRacion = jCheckBoxMediaActualizar.isSelected();
            precioUnidad = jCheckBoxUnidadActualizar.isSelected();
        } catch (NumberFormatException ex) {
            System.out.println("Error al obtener los nuevos valores.");
            return;
        }

        // Establecer la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/Bar_ElEscobar";
        String usuario = "bar_Admin";
        String contraseña = "Admin123456";

        try (Connection con = DriverManager.getConnection(url, usuario, contraseña)) {
            // Actualizar el plato en la base de datos
            String sqlUpdate = "UPDATE entrantes SET nombre = ?, precio = ?, racion_completa = ?, media_racion = ?, precio_unidad = ? WHERE id = ?";
            try (PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate)) {
                pstmtUpdate.setString(1, nuevoNombre);
                pstmtUpdate.setBigDecimal(2, nuevoPrecio);
                pstmtUpdate.setBoolean(3, racionCompleta);
                pstmtUpdate.setBoolean(4, mediaRacion);
                pstmtUpdate.setBoolean(5, precioUnidad);
                pstmtUpdate.setInt(6, idPlato);
                int rowsUpdated = pstmtUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("El plato se ha actualizado correctamente.");
                    cargarPlatos(); // Recargar los datos del JComboBox
                } else {
                    System.out.println("No se encontró el plato a actualizar.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al actualizar el plato.");
        }
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jMenuAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAtrasActionPerformed
        // TODO add your handling code here:
        // Cerrar el JDialog actual
        dispose();
        // Obtener el JFrame principal desde el JDialog
        JFrame ventanaPrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
        // Mostrar la ventana principal
        ventanaPrincipal.setVisible(true);
    }//GEN-LAST:event_jMenuAtrasActionPerformed

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
            java.util.logging.Logger.getLogger(Entrantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Entrantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Entrantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Entrantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Entrantes dialog = new Entrantes(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JCheckBox jCheckBoxCompleta;
    private javax.swing.JCheckBox jCheckBoxCompletaActualizar;
    private javax.swing.JCheckBox jCheckBoxMedia;
    private javax.swing.JCheckBox jCheckBoxMediaActualizar;
    private javax.swing.JCheckBox jCheckBoxUnidad;
    private javax.swing.JCheckBox jCheckBoxUnidadActualizar;
    private javax.swing.JComboBox<String> jComboBoxActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenuAtras;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextNombreActualizar;
    private javax.swing.JTextField jTextPrecio;
    private javax.swing.JTextField jTextPrecioActualizar;
    // End of variables declaration//GEN-END:variables
}
