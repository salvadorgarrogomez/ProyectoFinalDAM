/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.app_pcproyecto;

import static com.mycompany.app_pcproyecto.Principal.connection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ro
 */
public class Bebidas extends javax.swing.JDialog {

    private void insertarBebida(Connection connection) throws SQLException {
        String nombre = jTextNombre.getText();
        BigDecimal precio = new BigDecimal(jTextPrecio.getText());
        boolean bebidaRefresco = jCheckBoxRefresco.isSelected();
        boolean bebidaAlcohol = jCheckBoxAlcohol.isSelected();
        boolean bebidaCopa = jCheckBoxCopa.isSelected();
        boolean bebidaTanque = jCheckBoxTanque.isSelected();
        boolean bebidaCubata = jCheckBoxCubata.isSelected();

        try {
            // Verificar si el plato ya existe
            boolean bebidaNoExiste = true;
            if (bebidaRefresco) {
                bebidaNoExiste = bebidaNoExiste(connection, nombre, true, false, false, false, false);
            } else if (bebidaAlcohol) {
                bebidaNoExiste = bebidaNoExiste(connection, nombre, false, true, false, false, false);
            } else if (bebidaCopa) {
                bebidaNoExiste = bebidaNoExiste(connection, nombre, false, false, true, false, false);
            } else if (bebidaTanque) {
                bebidaNoExiste = bebidaNoExiste(connection, nombre, false, false, false, true, false);
            } else if (bebidaCubata) {
                bebidaNoExiste = bebidaNoExiste(connection, nombre, false, false, false, false, true);
            }

            if (bebidaNoExiste) {
                String sql = "INSERT INTO bebidas (nombre, precio, tipo_refresco, tipo_alcohol, tipo_copa, tipo_tanque, tipo_cubata)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, nombre);
                    pstmt.setBigDecimal(2, precio);
                    pstmt.setBoolean(3, bebidaRefresco);
                    pstmt.setBoolean(4, bebidaAlcohol);
                    pstmt.setBoolean(5, bebidaCopa);
                    pstmt.setBoolean(6, bebidaTanque);
                    pstmt.setBoolean(7, bebidaCubata);
                    pstmt.executeUpdate();
                }
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "La bebida se ha añadido correctamente");

            } else {
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(this, "La bebida  ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "La bebida no se ha guardado correctamente, volver a intentar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatos(Connection connection) throws SQLException {

        try {
            String sql = "SELECT id, nombre, precio, tipo_refresco, tipo_alcohol, tipo_copa, tipo_tanque, tipo_cubata FROM bebidas ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboBoxActualizar.removeAllItems(); // Eliminar todos los elementos existentes
                    while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        BigDecimal precio = rs.getBigDecimal("precio");
                        boolean bebidaRefresco = rs.getBoolean("tipo_refresco");
                        boolean bebidaAlcohol = rs.getBoolean("tipo_alcohol");
                        boolean bebidaCopa = rs.getBoolean("tipo_copa");
                        boolean bebidaTanque = rs.getBoolean("tipo_tanque");
                        boolean bebidaCubata = rs.getBoolean("tipo_cubata");

                        // Construir el texto del item del JComboBox con el tipo de plato
                        String tipoBebida;
                        if (bebidaRefresco) {
                            tipoBebida = "Refresco";
                        } else if (bebidaAlcohol) {
                            tipoBebida = "Alcochol";
                        } else if (bebidaCopa) {
                            tipoBebida = "Copa";
                        } else if (bebidaTanque) {
                            tipoBebida = "Tanque";
                        } else if (bebidaCubata) {
                            tipoBebida = "Cubata";
                        } else {
                            tipoBebida = "No especificado";
                        }

                        String item = id + ": " + nombre + " - " + tipoBebida + " - " + precio.toString();
                        jComboBoxActualizar.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar las bebidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean bebidaNoExiste(Connection con, String nombre, boolean bebidaRefresco, boolean bebidaAlcohol, boolean bebidaCopa, boolean bebidaTanque, boolean bebidaCubata) throws SQLException {
        String sql = null;
        if (bebidaRefresco) {
            sql = "SELECT COUNT(*) FROM bebidas WHERE nombre = ? AND tipo_refresco = true";
        } else if (bebidaAlcohol) {
            sql = "SELECT COUNT(*) FROM bebidas WHERE nombre = ? AND tipo_alcohol = true";
        } else if (bebidaCopa) {
            sql = "SELECT COUNT(*) FROM bebidas WHERE nombre = ? AND tipo_copa = true";
        } else if (bebidaTanque) {
            sql = "SELECT COUNT(*) FROM bebidas WHERE nombre = ? AND tipo_tanque = true";
        } else if (bebidaCubata) {
            sql = "SELECT COUNT(*) FROM bebidas WHERE nombre = ? AND tipo_cubata = true";
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
        jCheckBoxRefresco.setSelected(false);
        jCheckBoxAlcohol.setSelected(false);
        jCheckBoxCopa.setSelected(false);
        jCheckBoxTanque.setSelected(false);
        jCheckBoxCubata.setSelected(false);
    }

    /**
     * Creates new form Bebidas
     */
    public Bebidas(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();

        cargarPlatos(connection());

        //        this.setSize(anchoPredeterminado, altoPredeterminado);
        this.setSize(500, 550);
        // Agregar el elemento predeterminado al JComboBox al principio
        jComboBoxActualizar.insertItemAt("< Selecciona una bebida >", 0);
        // Establecer el elemento predeterminado como seleccionado
        jComboBoxActualizar.setSelectedIndex(0);

        // Configurar el ActionListener para el JComboBox
        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            @Override
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

        jLabel3 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButtonActualizar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextNombreActualizar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jCheckBoxAlcohol = new javax.swing.JCheckBox();
        jComboBoxActualizar = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButtonGuardar = new javax.swing.JButton();
        jCheckBoxRefrescoActualizar = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jTextPrecioActualizar = new javax.swing.JTextField();
        jCheckBoxRefresco = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jCheckBoxAlcoholActualizar = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        jTextPrecio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCheckBoxTanque = new javax.swing.JCheckBox();
        jCheckBoxCopa = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jCheckBoxCubata = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxTanqueActualizar = new javax.swing.JCheckBox();
        jCheckBoxCopaActualizar = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        jCheckBoxCubataActualizar = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuInicio = new javax.swing.JMenu();
        jMenuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setText("Precio:");

        jLabel7.setText("Actualizar bebida:");

        jButtonActualizar.setText("Actualizar plato");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jLabel11.setText("Alcohol");

        jLabel10.setText("Refresco");

        jLabel4.setText("Refresco");

        jLabel2.setText("Nombre:");

        jLabel5.setText("Alcohol");

        jTextFieldID.setEditable(false);

        jCheckBoxAlcohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAlcoholActionPerformed(evt);
            }
        });

        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingresar nueva bebida:");

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jCheckBoxRefrescoActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRefrescoActualizarActionPerformed(evt);
            }
        });

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Precio:");

        jCheckBoxRefresco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRefrescoActionPerformed(evt);
            }
        });

        jLabel8.setText("Nombre:");

        jCheckBoxAlcoholActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAlcoholActualizarActionPerformed(evt);
            }
        });

        jLabel13.setText("ID");

        jLabel6.setText("Copa");

        jLabel12.setText("Tanque");

        jCheckBoxTanque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTanqueActionPerformed(evt);
            }
        });

        jCheckBoxCopa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCopaActionPerformed(evt);
            }
        });

        jLabel14.setText("Cubata");

        jCheckBoxCubata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCubataActionPerformed(evt);
            }
        });

        jLabel15.setText("Copa");

        jLabel16.setText("Tanque");

        jCheckBoxTanqueActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTanqueActualizarActionPerformed(evt);
            }
        });

        jCheckBoxCopaActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCopaActualizarActionPerformed(evt);
            }
        });

        jLabel17.setText("Cubata");

        jCheckBoxCubataActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCubataActualizarActionPerformed(evt);
            }
        });

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
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
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
                                .addComponent(jCheckBoxAlcoholActualizar)
                                .addComponent(jCheckBoxRefrescoActualizar))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBoxTanqueActualizar)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jCheckBoxCopaActualizar)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel17)
                                    .addGap(18, 18, 18)
                                    .addComponent(jCheckBoxCubataActualizar))))
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
                        .addComponent(jButtonGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(41, 41, 41)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jCheckBoxAlcohol)
                                        .addComponent(jCheckBoxRefresco))))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(41, 41, 41)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jCheckBoxTanque)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jCheckBoxCopa)
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel14)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(69, 69, 69)
                                                    .addComponent(jCheckBoxCubata)))))))))
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
                            .addComponent(jCheckBoxAlcohol)))
                    .addComponent(jCheckBoxRefresco)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jCheckBoxCubata)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jCheckBoxTanque)))
                    .addComponent(jCheckBoxCopa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextPrecioActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jCheckBoxCubataActualizar))
                                        .addGap(3, 3, 3)
                                        .addComponent(jCheckBoxTanqueActualizar))
                                    .addComponent(jCheckBoxCopaActualizar))
                                .addGap(9, 9, 9)))
                        .addComponent(jButtonActualizar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jCheckBoxAlcoholActualizar))
                    .addComponent(jCheckBoxRefrescoActualizar))
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        // TODO add your handling code here:
        String selectedPlato = (String) jComboBoxActualizar.getSelectedItem();

        // Obtener el ID del plato seleccionado
        String[] parts = selectedPlato.split(" - ");
        if (parts.length != 3) {
            JOptionPane.showMessageDialog(this, "Error al obtener el nombre de la bebida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idBebida;
        try {
            idBebida = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID de la bebida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los nuevos valores
        String nuevoNombre;
        BigDecimal nuevoPrecio;
        boolean bebidaRefresco;
        boolean bebidaAlcohol;
        boolean bebidaCopa;
        boolean bebidaTanque;
        boolean bebidaCubata;

        try {
            nuevoNombre = jTextNombreActualizar.getText();
            nuevoPrecio = new BigDecimal(jTextPrecioActualizar.getText());
            bebidaRefresco = jCheckBoxRefrescoActualizar.isSelected();
            bebidaAlcohol = jCheckBoxAlcoholActualizar.isSelected();
            bebidaCopa = jCheckBoxCopaActualizar.isSelected();
            bebidaTanque = jCheckBoxTanqueActualizar.isSelected();
            bebidaCubata = jCheckBoxCubataActualizar.isSelected();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la bebida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Establecer la conexión a la base de datos
        try {
            Connection connection = Principal.connection();
            // Actualizar el plato en la base de datos
            String sqlUpdate = "UPDATE bebidas SET nombre = ?, precio = ?, tipo_refresco = ?, tipo_alcohol = ?, tipo_copa = ?, tipo_tanque = ?, tipo_cubata = ? WHERE id = ?";
            try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdate)) {
                pstmtUpdate.setString(1, nuevoNombre);
                pstmtUpdate.setBigDecimal(2, nuevoPrecio);
                pstmtUpdate.setBoolean(3, bebidaRefresco);
                pstmtUpdate.setBoolean(4, bebidaAlcohol);
                pstmtUpdate.setBoolean(5, bebidaCopa);
                pstmtUpdate.setBoolean(6, bebidaTanque);
                pstmtUpdate.setInt(7, idBebida);
                int rowsUpdated = pstmtUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "La bebida se ha actualizado correctamente");
                    cargarPlatos(connection()); // Recargar los datos del JComboBox
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró la bebida a actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el plato.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jCheckBoxAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAlcoholActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxAlcohol.isSelected()) {
            jCheckBoxRefresco.setSelected(false);
            jCheckBoxCopa.setSelected(false);
            jCheckBoxTanque.setSelected(false);
            jCheckBoxCubata.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxAlcoholActionPerformed

    private void jComboBoxActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxActualizarActionPerformed
        // TODO add your handling code here:
        String seleccion = (String) jComboBoxActualizar.getSelectedItem();
        if (seleccion != null && !seleccion.equals("< Selecciona una bebida >")) {
            String[] parts = seleccion.split(": ");
            if (parts.length != 2) {
                JOptionPane.showMessageDialog(this, "Error al obtener el nombre de la bebida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idPlato;
            try {
                idPlato = Integer.parseInt(jTextFieldID.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener el ID de la bebida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Connection connection = Principal.connection();
                String sql = "SELECT id, nombre, precio, tipo_refresco, tipo_alcohol, tipo_copa, tipo_tanque, tipo_cubata FROM bebidas WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, idPlato);// Usar el ID del plato obtenido correctamente
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            jTextFieldID.setText(rs.getInt("id") + ""); // Establece el ID del plato
                            jTextNombreActualizar.setText(rs.getString("nombre")); // Establece el nombre del plato
                            jTextPrecioActualizar.setText(rs.getBigDecimal("precio").toString()); // Establece el precio del plato
                            jCheckBoxRefrescoActualizar.setSelected(rs.getBoolean("tipo_refresco"));
                            jCheckBoxAlcoholActualizar.setSelected(rs.getBoolean("tipo_alcohol"));
                            jCheckBoxCopaActualizar.setSelected(rs.getBoolean("tipo_copa"));
                            jCheckBoxTanqueActualizar.setSelected(rs.getBoolean("tipo_tanque"));
                            jCheckBoxCubataActualizar.setSelected(rs.getBoolean("tipo_cubata"));

                        }
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                // Manejo del error, por ejemplo, mostrar un mensaje al usuario
                JOptionPane.showMessageDialog(this, "Error al obtener los nuevos valores.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Vaciar los campos
            jTextFieldID.setText("");
            jTextNombreActualizar.setText("");
            jTextPrecioActualizar.setText("");
            jCheckBoxRefrescoActualizar.setSelected(false);
            jCheckBoxAlcoholActualizar.setSelected(false);
            jCheckBoxCopaActualizar.setSelected(false);
            jCheckBoxTanqueActualizar.setSelected(false);
            jCheckBoxCubataActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jComboBoxActualizarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        try {
            // TODO add your handling code here:
            insertarBebida(connection());
            cargarPlatos(connection());
        } catch (SQLException ex) {
            Logger.getLogger(Postres.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Agregar el elemento predeterminado al JComboBox al principio
        jComboBoxActualizar.insertItemAt("< Selecciona una bebida >", 0);
        // Establecer el elemento predeterminado como seleccionado
        jComboBoxActualizar.setSelectedIndex(0);

        // Configurar el ActionListener para el JComboBox
        jComboBoxActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxActualizarActionPerformed(evt);
            }
        });
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jCheckBoxRefrescoActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRefrescoActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxRefrescoActualizar.isSelected()) {
            jCheckBoxAlcoholActualizar.setSelected(false);
            jCheckBoxCopaActualizar.setSelected(false);
            jCheckBoxTanqueActualizar.setSelected(false);
            jCheckBoxCubataActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxRefrescoActualizarActionPerformed

    private void jCheckBoxRefrescoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRefrescoActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxRefresco.isSelected()) {
            jCheckBoxAlcohol.setSelected(false);
            jCheckBoxCopa.setSelected(false);
            jCheckBoxTanque.setSelected(false);
            jCheckBoxCubata.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxRefrescoActionPerformed

    private void jCheckBoxAlcoholActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAlcoholActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxAlcoholActualizar.isSelected()) {
            jCheckBoxRefrescoActualizar.setSelected(false);
            jCheckBoxCopaActualizar.setSelected(false);
            jCheckBoxTanqueActualizar.setSelected(false);
            jCheckBoxCubataActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxAlcoholActualizarActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void jCheckBoxTanqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTanqueActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxTanque.isSelected()) {
            jCheckBoxAlcohol.setSelected(false);
            jCheckBoxCopa.setSelected(false);
            jCheckBoxRefresco.setSelected(false);
            jCheckBoxCubata.setSelected(false);
        }

    }//GEN-LAST:event_jCheckBoxTanqueActionPerformed

    private void jCheckBoxCopaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCopaActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCopa.isSelected()) {
            jCheckBoxAlcohol.setSelected(false);
            jCheckBoxRefresco.setSelected(false);
            jCheckBoxTanque.setSelected(false);
            jCheckBoxCubata.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCopaActionPerformed

    private void jCheckBoxCubataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCubataActionPerformed
        // TODO add your handling code here:        
        if (jCheckBoxCubata.isSelected()) {
            jCheckBoxAlcohol.setSelected(false);
            jCheckBoxCopa.setSelected(false);
            jCheckBoxTanque.setSelected(false);
            jCheckBoxRefresco.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCubataActionPerformed

    private void jCheckBoxTanqueActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTanqueActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxTanqueActualizar.isSelected()) {
            jCheckBoxAlcoholActualizar.setSelected(false);
            jCheckBoxCopaActualizar.setSelected(false);
            jCheckBoxRefrescoActualizar.setSelected(false);
            jCheckBoxCubataActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxTanqueActualizarActionPerformed

    private void jCheckBoxCopaActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCopaActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCopaActualizar.isSelected()) {
            jCheckBoxAlcoholActualizar.setSelected(false);
            jCheckBoxRefrescoActualizar.setSelected(false);
            jCheckBoxTanqueActualizar.setSelected(false);
            jCheckBoxCubataActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCopaActualizarActionPerformed

    private void jCheckBoxCubataActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCubataActualizarActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxCubataActualizar.isSelected()) {
            jCheckBoxAlcoholActualizar.setSelected(false);
            jCheckBoxCopaActualizar.setSelected(false);
            jCheckBoxTanqueActualizar.setSelected(false);
            jCheckBoxRefrescoActualizar.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBoxCubataActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(Bebidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bebidas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Bebidas dialog = null;
                try {
                    dialog = new Bebidas(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(Bebidas.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JCheckBox jCheckBoxAlcohol;
    private javax.swing.JCheckBox jCheckBoxAlcoholActualizar;
    private javax.swing.JCheckBox jCheckBoxCopa;
    private javax.swing.JCheckBox jCheckBoxCopaActualizar;
    private javax.swing.JCheckBox jCheckBoxCubata;
    private javax.swing.JCheckBox jCheckBoxCubataActualizar;
    private javax.swing.JCheckBox jCheckBoxRefresco;
    private javax.swing.JCheckBox jCheckBoxRefrescoActualizar;
    private javax.swing.JCheckBox jCheckBoxTanque;
    private javax.swing.JCheckBox jCheckBoxTanqueActualizar;
    private javax.swing.JComboBox<String> jComboBoxActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
