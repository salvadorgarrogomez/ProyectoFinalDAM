/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.app_pcproyecto;

import static com.mycompany.app_pcproyecto.Principal.connection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Ro
 */
public class BorrarPlato extends javax.swing.JDialog {

    private Connection connection;

    public BorrarPlato(Connection connection) {
        this.connection = connection;
    }

    private void cargarNombreTablas(Connection connection) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                // Verificar si el nombre de la tabla no es "mesa"
                if (!tableName.equalsIgnoreCase("mesas")) {
                    listModel.addElement(tableName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las tablas.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        jListMenus.setModel(listModel);

        // Agregar ListSelectionListener al jListMenus
        jListMenus.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedTableName = jListMenus.getSelectedValue();
                    cargarFilasDeTablaSeleccionada(connection, selectedTableName);
                }
            }
        });
    }

    private void cargarFilasDeTablaSeleccionada(Connection connection, String nombreTabla) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT * FROM " + nombreTabla;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                ResultSetMetaData metaData = rs.getMetaData();
                int maxColumnCount = metaData.getColumnCount();
                // Iterar sobre las filas del ResultSet
                while (rs.next()) {
                    StringBuilder row = new StringBuilder();
                    // Iterar sobre las columnas de cada fila
                    for (int i = 1; i <= maxColumnCount; i++) {
                        // Obtener el nombre de la columna
                        String columnName = metaData.getColumnName(i);
                        // Obtener el valor de la columna
                        Object value = rs.getObject(i);
                        // Verificar si el valor es booleano y true
                        if (value instanceof Boolean && (Boolean) value) {
                            // Agregar solo el nombre de la columna al StringBuilder
                            row.append(columnName).append(", ");
                        } else if (!(value instanceof Boolean)) {
                            row.append(value).append(", ");
                        }
                    }
                    // Agregar la fila al modelo de lista
                    listModel.addElement(row.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las filas de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        jListPlatos.setModel(listModel);
    }

    private boolean tablaExiste(Connection connection, String nombreTabla) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, nombreTabla, null)) {
            return rs.next();
        }
    }

    private void cerrarConexiones() {
        // Cierra la conexión principal
        if (connection != null) {
            try {
                connection.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Creates new form BorrarPlato
     */
    public BorrarPlato(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        cargarNombreTablas(connection());

        //        this.setSize(anchoPredeterminado, altoPredeterminado);
        this.setSize(600, 600);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListMenus = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListPlatos = new javax.swing.JList<>();
        jButtonBorrar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("1. Elige el menu del que quieres borrar el plato:");

        jListMenus.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jListMenusAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jListMenus);

        jLabel2.setText("2. Elige el plato del menu que quieres borrar:");

        jListPlatos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jListPlatosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(jListPlatos);

        jButtonBorrar.setText("Borrar el plato");
        jButtonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarActionPerformed(evt);
            }
        });

        jMenu1.setText("Inicio");

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSalir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2))
                    .addComponent(jButtonBorrar))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBorrar))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jListMenusAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jListMenusAncestorAdded

        String selectedTableName = jListMenus.getSelectedValue();
        if (selectedTableName != null) {
            cargarFilasDeTablaSeleccionada(connection(), selectedTableName);
        }
    }//GEN-LAST:event_jListMenusAncestorAdded

    private void jListPlatosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jListPlatosAncestorAdded
        // TODO add your handling code here:
        jListPlatos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Obtener el índice del elemento seleccionado
                    int selectedIndex = jListPlatos.getSelectedIndex();
                    // Obtener el valor del elemento seleccionado
                    String selectedValue = jListPlatos.getSelectedValue();
                    // Si el índice y el valor son válidos
                    if (selectedIndex != -1 && selectedValue != null) {
                        // Permitir que el botón de borrar esté habilitado
                        jButtonBorrar.setEnabled(true);
                    }
                }
            }
        });
    }//GEN-LAST:event_jListPlatosAncestorAdded

    private void jButtonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarActionPerformed
        // Obtener el nombre de la tabla seleccionada en jListMenus
        String selectedTableName = jListMenus.getSelectedValue();
        if (selectedTableName != null) {
            DefaultListModel<String> listModel = (DefaultListModel<String>) jListPlatos.getModel();
            int selectedIndex = jListPlatos.getSelectedIndex();
            if (selectedIndex != -1) {
                // Obtener el valor del elemento seleccionado
                String selectedItem = listModel.getElementAt(selectedIndex);
                String[] parts = selectedItem.split(", ");
                String idPlato = parts[0];
                Connection connection = null;
                try {
                    connection = Principal.connection();
                    String sql = "DELETE FROM " + selectedTableName + " WHERE id = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setInt(1, Integer.parseInt(idPlato));
                        pstmt.executeUpdate();
                    }

                    // Eliminar el elemento seleccionado del modelo del jListPlatos
                    listModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(this, "Plato borrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al borrar el plato.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Cerrar la conexión al finalizar
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un plato para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBorrarActionPerformed

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        // Cerrar todas las conexiones abiertas
        cerrarConexiones();
        // Salir de la aplicación
        dispose();
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

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
            java.util.logging.Logger.getLogger(BorrarPlato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BorrarPlato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BorrarPlato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BorrarPlato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BorrarPlato dialog = null;
                try {
                    dialog = new BorrarPlato(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(BorrarPlato.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton jButtonBorrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jListMenus;
    private javax.swing.JList<String> jListPlatos;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
