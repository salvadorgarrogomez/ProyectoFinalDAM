/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.app_pcproyecto;

import static com.mycompany.app_pcproyecto.Principal.connection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ro
 */
public class Postres extends javax.swing.JDialog {

    private void insertarPlatoPostres(Connection connection) throws SQLException {
        String nombre = jTextNombre.getText();
        BigDecimal precio = new BigDecimal(jTextPrecio.getText());
        boolean racionCompleta = jCheckBoxCompleta.isSelected();
        boolean mediaRacion = jCheckBoxMedia.isSelected();

        try (Statement stmt = connection.createStatement()) {
            // Verificar si el plato ya existe
            boolean platoNoExiste = true;
            if (mediaRacion) {
                platoNoExiste = platoNoExiste(connection, nombre, true, false);
            } else if (racionCompleta) {
                platoNoExiste = platoNoExiste(connection, nombre, false, true);
            }

            if (platoNoExiste) {
                String sql = "INSERT INTO postres (nombre, precio, racion_completa, media_racion)"
                        + "VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, nombre);
                    pstmt.setBigDecimal(2, precio);
                    pstmt.setBoolean(3, racionCompleta);
                    pstmt.setBoolean(4, mediaRacion);
                    pstmt.executeUpdate();
                }
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Plato de postres añadido correctamente");
            } else {
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(this, "El plato ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "El plato no se ha guardado correctamente, volver a intentar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatos(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM postres ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboBoxActualizar.removeAllItems(); // Eliminar todos los elementos existentes
                    while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        BigDecimal precio = rs.getBigDecimal("precio");
                        boolean racionCompleta = rs.getBoolean("racion_completa");
                        boolean mediaRacion = rs.getBoolean("media_racion");

                        // Construir el texto del item del JComboBox con el tipo de plato
                        String tipoPlato;
                        if (racionCompleta) {
                            tipoPlato = "Ración Completa";
                        } else if (mediaRacion) {
                            tipoPlato = "Media Ración";
                        } else {
                            tipoPlato = "No especificado";
                        }

                        String item = id + ": " + nombre + " - " + tipoPlato + " - " + precio.toString();
                        jComboBoxActualizar.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean platoNoExiste(Connection con, String nombre, boolean mediaRacion, boolean racionCompleta) throws SQLException {
        String sql = null;
        if (mediaRacion) {
            sql = "SELECT COUNT(*) FROM postres WHERE nombre = ? AND media_racion = true";
        } else if (racionCompleta) {
            sql = "SELECT COUNT(*) FROM postres WHERE nombre = ? AND racion_completa = true";
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
    }

    /**
     * Creates new form Postres
     */
    public Postres(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();

        cargarPlatos(connection());

        //        this.setSize(anchoPredeterminado, altoPredeterminado);
        this.setSize(500, 550);
        // Agregar el elemento predeterminado al JComboBox al principio
        jComboBoxActualizar.insertItemAt("< Selecciona un plato >", 0);
        // Establecer el elemento predeterminado como seleccionado
        jComboBoxActualizar.setSelectedIndex(0);

        // Configurar el ActionListener para el JComboBox
        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jCheckBoxCompletaActualizar = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jComboBoxActualizar = new javax.swing.JComboBox<>();
        jButtonGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTextPrecioActualizar = new javax.swing.JTextField();
        jCheckBoxMedia = new javax.swing.JCheckBox();
        jTextNombre = new javax.swing.JTextField();
        jButtonActualizar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextNombreActualizar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCheckBoxCompleta = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBoxMediaActualizar = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuInicio = new javax.swing.JMenu();
        jMenuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel8.setText("Nombre:");

        jCheckBoxCompletaActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCompletaActualizarActionPerformed(evt);
            }
        });

        jLabel13.setText("ID");

        jLabel3.setText("Precio:");

        jLabel7.setText("Actualizar plato:");

        jLabel11.setText("Racion completa:");

        jLabel4.setText("Media racion:");

        jLabel2.setText("Nombre:");

        jTextFieldID.setEditable(false);

        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jCheckBoxMedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMediaActionPerformed(evt);
            }
        });

        jButtonActualizar.setText("Actualizar plato");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jLabel10.setText("Media racion:");

        jLabel5.setText("Racion completa:");

        jCheckBoxCompleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCompletaActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingresar nuevo plato al menu de Postres:");

        jCheckBoxMediaActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMediaActualizarActionPerformed(evt);
            }
        });

        jLabel9.setText("Precio:");

        jMenuInicio.setText("Inicio");

        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenuInicio.add(jMenuSalir);

        jMenuBar1.add(jMenuInicio);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(41, 41, 41)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBoxCompleta)
                                .addComponent(jCheckBoxMedia)))
                        .addComponent(jLabel13)
                        .addComponent(jLabel9)
                        .addComponent(jLabel7)
                        .addComponent(jComboBoxActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBoxCompletaActualizar)
                                .addComponent(jCheckBoxMediaActualizar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonActualizar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(186, 186, 186))
                                    .addComponent(jTextNombreActualizar)
                                    .addComponent(jTextPrecioActualizar))))
                        .addComponent(jButtonGuardar, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jTextPrecio)))))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jCheckBoxCompleta)))
                    .addComponent(jCheckBoxMedia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButtonGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNombreActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextPrecioActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonActualizar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jCheckBoxCompletaActualizar))
                    .addComponent(jCheckBoxMediaActualizar))
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxCompletaActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCompletaActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCompletaActualizar.isSelected()) {
            jCheckBoxMediaActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCompletaActualizarActionPerformed

    private void jComboBoxActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxActualizarActionPerformed
        // TODO add your handling code here:
        String seleccion = (String) jComboBoxActualizar.getSelectedItem();
        if (seleccion != null && !seleccion.equals("< Selecciona un plato >")) {
            String[] parts = seleccion.split(": ");
            if (parts.length != 2) {
                JOptionPane.showMessageDialog(this, "Error al obtener el nombre del plato.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idPlato;
            try {
                idPlato = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener el ID del plato.", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {
                Connection connection = Principal.connection();
                String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM postres WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, idPlato);// Usar el ID del plato obtenido correctamente
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            jTextFieldID.setText(rs.getInt("id") + ""); // Establece el ID del plato
                            jTextNombreActualizar.setText(rs.getString("nombre")); // Establece el nombre del plato
                            jTextPrecioActualizar.setText(rs.getBigDecimal("precio").toString()); // Establece el precio del plato
                            jCheckBoxCompletaActualizar.setSelected(rs.getBoolean("racion_completa")); // Establece el valor de racion_completa
                            jCheckBoxMediaActualizar.setSelected(rs.getBoolean("media_racion")); // Establece el valor de media_racion
                        }
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                // Manejo del error, por ejemplo, mostrar un mensaje al usuario
                JOptionPane.showMessageDialog(this, "Error al obtener los datos del plato.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Vaciar los campos
            jTextFieldID.setText("");
            jTextNombreActualizar.setText("");
            jTextPrecioActualizar.setText("");
            jCheckBoxCompletaActualizar.setSelected(false);
            jCheckBoxMediaActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jComboBoxActualizarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        try {
            // TODO add your handling code here:
            insertarPlatoPostres(connection());
            cargarPlatos(connection());
        } catch (SQLException ex) {
            Logger.getLogger(Postres.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Agregar el elemento predeterminado al JComboBox al principio
        jComboBoxActualizar.insertItemAt("< Selecciona un plato >", 0);
        // Establecer el elemento predeterminado como seleccionado
        jComboBoxActualizar.setSelectedIndex(0);

        // Configurar el ActionListener para el JComboBox
        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jCheckBoxMediaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMediaActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMedia.isSelected()) {
            jCheckBoxCompleta.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxMediaActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        // TODO add your handling code here:
        String selectedPlato = (String) jComboBoxActualizar.getSelectedItem();

        // Obtener el ID del plato seleccionado
        String[] parts = selectedPlato.split(" - ");
        if (parts.length != 3) {
            JOptionPane.showMessageDialog(this, "Error al obtener el nombre del plato.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idPlato;
        try {
            idPlato = Integer.parseInt(jTextFieldID.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del plato.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los nuevos valores
        String nuevoNombre;
        BigDecimal nuevoPrecio;
        boolean racionCompleta;
        boolean mediaRacion;
        try {
            nuevoNombre = jTextNombreActualizar.getText();
            nuevoPrecio = new BigDecimal(jTextPrecioActualizar.getText());
            racionCompleta = jCheckBoxCompletaActualizar.isSelected();
            mediaRacion = jCheckBoxMediaActualizar.isSelected();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el plato.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection connection = Principal.connection();
            // Actualizar el plato en la base de datos
            String sqlUpdate = "UPDATE postres SET nombre = ?, precio = ?, racion_completa = ?, media_racion = ? WHERE id = ?";
            try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdate)) {
                pstmtUpdate.setString(1, nuevoNombre);
                pstmtUpdate.setBigDecimal(2, nuevoPrecio);
                pstmtUpdate.setBoolean(3, racionCompleta);
                pstmtUpdate.setBoolean(4, mediaRacion);
                pstmtUpdate.setInt(5, idPlato);
                int rowsUpdated = pstmtUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Plato del menu de postres, actualizado correctamente");
                    cargarPlatos(connection()); // Recargar los datos del JComboBox
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el plato a actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el plato.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jCheckBoxCompletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCompletaActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCompleta.isSelected()) {
            jCheckBoxMedia.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCompletaActionPerformed

    private void jCheckBoxMediaActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMediaActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMediaActualizar.isSelected()) {
            jCheckBoxCompletaActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxMediaActualizarActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuSalirActionPerformed

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
            java.util.logging.Logger.getLogger(Postres.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Postres.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Postres.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Postres.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Postres dialog = null;
                try {
                    dialog = new Postres(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(Postres.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private javax.swing.JComboBox<String> jComboBoxActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuInicio;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextNombre;
    private javax.swing.JTextField jTextNombreActualizar;
    private javax.swing.JTextField jTextPrecio;
    private javax.swing.JTextField jTextPrecioActualizar;
    // End of variables declaration//GEN-END:variables
}
