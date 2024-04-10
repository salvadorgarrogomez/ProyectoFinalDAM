/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.app_pcproyecto;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 *
 * @author Ro
 */
public class Principal extends javax.swing.JFrame {

    private static final String URL = "jdbc:postgresql://192.168.1.138:5432/Bar_ElEscobar"; //En su defecto localhost, para trabajar en local
    private static final String USUARIO = "bar_Admin";
    private static final String CONTRASEÑA = "Admin123456";

    /**
     *
     * @return @throws SQLException
     */
    public static Connection connection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage() + "\nEquipo servidor, ¿apagado o encendido? \nRevisar.", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Devuelve null si hay un error de conexión
        }
    }

// Método para crear las tablas en la base de datos al iniciar la aplicación
    private static void crearTablas(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Crear tabla de platos del menú
            String crearTablaEntrantes = "CREATE TABLE IF NOT EXISTS entrantes ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN,"
                    + "precio_unidad BOOLEAN)";

            String crearTablaEnsaladas = "CREATE TABLE IF NOT EXISTS ensaladas ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaCombinados = "CREATE TABLE IF NOT EXISTS combinados ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaCaldos = "CREATE TABLE IF NOT EXISTS caldos ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaBebidas = "CREATE TABLE IF NOT EXISTS bebidas ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "tipo_refresco BOOLEAN,"
                    + "tipo_alcohol BOOLEAN,"
                    + "tipo_copa BOOLEAN,"
                    + "tipo_tanque BOOLEAN,"
                    + "tipo_cubata BOOLEAN)";

            String crearTablaPasta = "CREATE TABLE IF NOT EXISTS pasta ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaCarnes = "CREATE TABLE IF NOT EXISTS carnes ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaPescados = "CREATE TABLE IF NOT EXISTS pescados ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaArroces = "CREATE TABLE IF NOT EXISTS arroces ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaFideua = "CREATE TABLE IF NOT EXISTS fideua ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaPostres = "CREATE TABLE IF NOT EXISTS postres ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50),"
                    + "precio DECIMAL(10, 2),"
                    + "racion_completa BOOLEAN,"
                    + "media_racion BOOLEAN)";

            String crearTablaMesas = "CREATE TABLE IF NOT EXISTS mesas ("
                    + "id SERIAL PRIMARY KEY,"
                    + "nombre VARCHAR(50))";

            // Ejecutar los comandos SQL
            stmt.executeUpdate(crearTablaEntrantes);
            stmt.executeUpdate(crearTablaEnsaladas);
            stmt.executeUpdate(crearTablaCombinados);
            stmt.executeUpdate(crearTablaCaldos);
            stmt.executeUpdate(crearTablaBebidas);
            stmt.executeUpdate(crearTablaPasta);
            stmt.executeUpdate(crearTablaCarnes);
            stmt.executeUpdate(crearTablaPescados);
            stmt.executeUpdate(crearTablaArroces);
            stmt.executeUpdate(crearTablaFideua);
            stmt.executeUpdate(crearTablaPostres);
            stmt.executeUpdate(crearTablaMesas);

            // Otras tablas pueden ser creadas de manera similar
            // Crear tabla de órdenes, tabla de asociación entre platos y órdenes, etc.
        }

    }

    private void cargarPlatosArroz(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM arroces ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboArroces.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboArroces.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosCaldos(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM caldos ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboCaldo.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboCaldo.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosCarnes(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM carnes ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboCarnes.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboCarnes.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosCombinados(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM combinados ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboCombinados.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboCombinados.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosEnsaladas(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM ensaladas ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboEnsaladas.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboEnsaladas.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosEntrantes(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion, precio_unidad FROM entrantes ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboEntrantes.removeAllItems(); // Eliminar todos los elementos existentes
                    while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String nombre = rs.getString("nombre");
                        BigDecimal precio = rs.getBigDecimal("precio");
                        boolean racionCompleta = rs.getBoolean("racion_completa");
                        boolean mediaRacion = rs.getBoolean("media_racion");
                        boolean precioUnidad = rs.getBoolean("precio_unidad");

                        // Construir el texto del item del JComboBox con el tipo de plato
                        String tipoPlato;
                        if (racionCompleta) {
                            tipoPlato = "Ración Completa";
                        } else if (mediaRacion) {
                            tipoPlato = "Media Ración";
                        } else if (precioUnidad) {
                            tipoPlato = "Precio por unidad";
                        } else {
                            tipoPlato = "No especificado";
                        }

                        String item = id + ": " + nombre + " - " + tipoPlato + " - " + precio.toString();
                        jComboEntrantes.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosFideua(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM fideua ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboFideua.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboFideua.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosPasta(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM pasta ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboPastas.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboPastas.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosPescados(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM pescados ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboPescados.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboPescados.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPlatosPostres(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, racion_completa, media_racion FROM postres ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboPostres.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboPostres.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar los platos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarBebidas(Connection connection) throws SQLException {

        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT id, nombre, precio, tipo_refresco, tipo_alcohol, tipo_copa, tipo_tanque, tipo_cubata FROM bebidas ORDER BY id";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    jComboBebidas.removeAllItems(); // Eliminar todos los elementos existentes
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
                        jComboBebidas.addItem(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo del error, por ejemplo, mostrar un mensaje al usuario
            JOptionPane.showMessageDialog(this, "Error al cargar las bebidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // Método para mostrar el JDialog "Entrantes"
    public void mostrarDialogoEntrantes() throws SQLException {
        // Mostrar el diálogo Entrantes
        Entrantes dialogo = new Entrantes(new javax.swing.JDialog(), true); // Pasar un nuevo JFrame como propietario del diálogo
        dialogo.setVisible(true);
    }

    private void configurarComboBox(JComboBox comboBox, String textoPredeterminado) {
        // Agregar el elemento predeterminado al JComboBox al principio
        comboBox.insertItemAt(textoPredeterminado, 0);
        // Establecer el elemento predeterminado como seleccionado
        comboBox.setSelectedIndex(0);

        // Centrar el texto en el JComboBox
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                    int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        // Obtener el ancho máximo de los elementos en el JComboBox
        int anchoMaximo = 0;
        FontMetrics fontMetrics = comboBox.getFontMetrics(comboBox.getFont());
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = (String) comboBox.getItemAt(i);
            int anchoItem = fontMetrics.stringWidth(item);
            if (anchoItem > anchoMaximo) {
                anchoMaximo = anchoItem;
            }
        }

        // Establecer el ancho preferido del JComboBox para que coincida con el ancho máximo encontrado
        comboBox.setPreferredSize(new Dimension(anchoMaximo, comboBox.getPreferredSize().height));
    }

    public Principal() throws SQLException {
        // Crear las tablas en la base de datos
        crearTablas(connection());
        initComponents(); // Inicializar los componentes de la interfaz gráfica

        // Configurar la ventana para que se maximice al abrirse
        this.setExtendedState(this.MAXIMIZED_BOTH);

        // Cargar los platos de cada categoría
        cargarPlatosArroz(connection());
        cargarPlatosCaldos(connection());
        cargarPlatosCarnes(connection());
        cargarPlatosCombinados(connection());
        cargarPlatosEnsaladas(connection());
        cargarPlatosEntrantes(connection());
        cargarPlatosFideua(connection());
        cargarPlatosPasta(connection());
        cargarPlatosPescados(connection());
        cargarPlatosPostres(connection());
        cargarBebidas(connection());

        // Configurar los JComboBox
        configurarComboBox(jComboPostres, "< Selecciona un plato >");
        configurarComboBox(jComboPescados, "< Selecciona un plato >");
        configurarComboBox(jComboPastas, "< Selecciona un plato >");
        configurarComboBox(jComboFideua, "< Selecciona un plato >");
        configurarComboBox(jComboEntrantes, "< Selecciona un plato >");
        configurarComboBox(jComboEnsaladas, "< Selecciona un plato >");
        configurarComboBox(jComboCombinados, "< Selecciona un plato >");
        configurarComboBox(jComboCarnes, "< Selecciona un plato >");
        configurarComboBox(jComboCaldo, "< Selecciona un plato >");
        configurarComboBox(jComboArroces, "< Selecciona un plato >");
        configurarComboBox(jComboBebidas, "< Selecciona una bebida >");
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
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuItem6 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboMesa = new javax.swing.JComboBox<>();
        jComboEntrantes = new javax.swing.JComboBox<>();
        jComboCombinados = new javax.swing.JComboBox<>();
        jComboCaldo = new javax.swing.JComboBox<>();
        jComboPastas = new javax.swing.JComboBox<>();
        jComboCarnes = new javax.swing.JComboBox<>();
        jComboPescados = new javax.swing.JComboBox<>();
        jComboArroces = new javax.swing.JComboBox<>();
        jComboFideua = new javax.swing.JComboBox<>();
        jComboPostres = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboEnsaladas = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBebidas = new javax.swing.JComboBox<>();
        jTextFieldMesa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaElementosMenu = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComboDiario = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaMesa2 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaMesa3 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaMesa1 = new javax.swing.JTextArea();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaMesa5 = new javax.swing.JTextArea();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaMesa6 = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextAreaMesa4 = new javax.swing.JTextArea();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextAreaMesa8 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextAreaMesa7 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextAreaMesa9 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuInicial = new javax.swing.JMenu();
        jMenuMesas = new javax.swing.JMenu();
        jMenuPlatos = new javax.swing.JMenu();
        jMenuItemEntrantes = new javax.swing.JMenuItem();
        jMenuItemCombi = new javax.swing.JMenuItem();
        jMenuItemCarnes = new javax.swing.JMenuItem();
        jMenuItemPescados = new javax.swing.JMenuItem();
        jMenuItemArroz = new javax.swing.JMenuItem();
        jMenuItemFideua = new javax.swing.JMenuItem();
        jMenuItemPasta = new javax.swing.JMenuItem();
        jMenuItemBebidas = new javax.swing.JMenuItem();
        jMenuItemPostres = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuMesas = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemBorrar = new javax.swing.JMenuItem();
        jMenuSalir = new javax.swing.JMenuItem();
        jMenuActualizar = new javax.swing.JMenu();
        jMenuItemActualizar = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jMenuItem6.setText("jMenuItem6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Mesa:");

        jLabel2.setText("Entrantes:");

        jLabel3.setText("Combinados:");

        jLabel4.setText("Caldos");

        jLabel5.setText("Pastas:");

        jLabel6.setText("Carnes:");

        jLabel7.setText("Pescados:");

        jLabel8.setText("Arroces: ");

        jLabel9.setText("Fideuá:");

        jLabel10.setText("Postres:");

        jLabel11.setText("Ensaladas");

        jLabel12.setText("Bebidas: ");

        jTextAreaElementosMenu.setColumns(20);
        jTextAreaElementosMenu.setRows(5);
        jScrollPane2.setViewportView(jTextAreaElementosMenu);

        jButton1.setText("Guardar Mesa");

        jLabel13.setText("Menu diario y bocadillos:");

        jComboDiario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Mesa 1");

        jTextAreaMesa2.setColumns(20);
        jTextAreaMesa2.setRows(5);
        jScrollPane3.setViewportView(jTextAreaMesa2);

        jTextField2.setEditable(false);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Mesa 2");

        jTextAreaMesa3.setColumns(20);
        jTextAreaMesa3.setRows(5);
        jScrollPane4.setViewportView(jTextAreaMesa3);

        jTextAreaMesa1.setColumns(20);
        jTextAreaMesa1.setRows(5);
        jScrollPane5.setViewportView(jTextAreaMesa1);

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("Mesa 3");

        jTextField4.setEditable(false);
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setText("Mesa 4");

        jTextAreaMesa5.setColumns(20);
        jTextAreaMesa5.setRows(5);
        jScrollPane6.setViewportView(jTextAreaMesa5);

        jTextField5.setEditable(false);
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("Mesa 5");

        jTextAreaMesa6.setColumns(20);
        jTextAreaMesa6.setRows(5);
        jScrollPane7.setViewportView(jTextAreaMesa6);

        jTextAreaMesa4.setColumns(20);
        jTextAreaMesa4.setRows(5);
        jScrollPane8.setViewportView(jTextAreaMesa4);

        jTextField6.setEditable(false);
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("Mesa 6");

        jTextField7.setEditable(false);
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("Mesa 7");

        jTextField8.setEditable(false);
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setText("Mesa 8");

        jTextField9.setEditable(false);
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setText("Mesa 9");
        jTextField9.setToolTipText("");

        jTextAreaMesa8.setColumns(20);
        jTextAreaMesa8.setRows(5);
        jScrollPane9.setViewportView(jTextAreaMesa8);

        jTextAreaMesa7.setColumns(20);
        jTextAreaMesa7.setRows(5);
        jScrollPane11.setViewportView(jTextAreaMesa7);

        jTextAreaMesa9.setColumns(20);
        jTextAreaMesa9.setRows(5);
        jScrollPane10.setViewportView(jTextAreaMesa9);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboDiario, 0, 125, Short.MAX_VALUE)
                    .addComponent(jComboEntrantes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboEnsaladas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCombinados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboArroces, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboFideua, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCarnes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboPescados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboPastas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboCaldo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboPostres, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBebidas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboMesa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(jTextFieldMesa)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboDiario))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboEntrantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jComboEnsaladas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboCombinados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboArroces))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboFideua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboCarnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboPescados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboPastas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboCaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboPostres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );


        jMenuInicial.setText("Inicio");

        jMenuMesas.setText("Mesas");
        jMenuBar1.add(jMenuMesas);

        jMenuPlatos.setText("Añadir Plato");

        jMenuItemEntrantes.setText("Entrantes y Ensaladas");
        jMenuItemEntrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEntrantesActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemEntrantes);

        jMenuItemCombi.setText("Combinados y Caldos");
        jMenuItemCombi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCombiActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemCombi);

        jMenuItemCarnes.setText("Carnes");
        jMenuItemCarnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCarnesActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemCarnes);

        jMenuItemPescados.setText("Pescados");
        jMenuItemPescados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPescadosActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemPescados);

        jMenuItemArroz.setText("Arroces");
        jMenuItemArroz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemArrozActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemArroz);

        jMenuItemFideua.setText("Fideua");
        jMenuItemFideua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFideuaActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemFideua);

        jMenuItemPasta.setText("Pasta");
        jMenuItemPasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPastaActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemPasta);

        jMenuItemBebidas.setText("Bebidas");
        jMenuItemBebidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBebidasActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemBebidas);

        jMenuItemPostres.setText("Postres");
        jMenuItemPostres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPostresActionPerformed(evt);
            }
        });
        jMenuPlatos.add(jMenuItemPostres);

        jMenuItem5.setText("Menu diario");
        jMenuPlatos.add(jMenuItem5);

        jMenuInicial.add(jMenuPlatos);

        jMenuMesas.setText("Añadir Mesa");

        jMenuItem4.setText("Mesa");
        jMenuMesas.add(jMenuItem4);

        jMenuInicial.add(jMenuMesas);

        jMenu1.setText("Borrar Plato");

        jMenuItemBorrar.setText("Borrar plato");
        jMenuItemBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemBorrar);

        jMenuInicial.add(jMenu1);

        jMenuSalir.setText("Salir");
        jMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSalirActionPerformed(evt);
            }
        });
        jMenuInicial.add(jMenuSalir);

        jMenuBar1.add(jMenuInicial);

        jMenuActualizar.setText("Actualizar Menus");

        jMenuItemActualizar.setText("Actualizar");
        jMenuItemActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarActionPerformed(evt);
            }
        });
        jMenuActualizar.add(jMenuItemActualizar);

        jMenuBar1.add(jMenuActualizar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemCombiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCombiActionPerformed
        // TODO add your handling code here:
        Combinados combinados = null;
        try {
            combinados = new Combinados(new javax.swing.JDialog(), true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        combinados.setVisible(true);
    }//GEN-LAST:event_jMenuItemCombiActionPerformed

    private void jMenuItemPastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPastaActionPerformed
        // TODO add your handling code here:
        Pasta pasta = null;
        try {
            pasta = new Pasta(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        pasta.setVisible(true);
    }//GEN-LAST:event_jMenuItemPastaActionPerformed

    private void jMenuItemEntrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEntrantesActionPerformed
        // TODO add your handling code here:

        Entrantes entrantes = null;
        try {
            entrantes = new Entrantes(new javax.swing.JDialog(), true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        entrantes.setVisible(true);

    }//GEN-LAST:event_jMenuItemEntrantesActionPerformed

    private void jMenuItemCarnesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCarnesActionPerformed
        // TODO add your handling code here:
        Carnes carne = null;
        try {
            carne = new Carnes(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        carne.setVisible(true);
    }//GEN-LAST:event_jMenuItemCarnesActionPerformed

    private void jMenuItemPescadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPescadosActionPerformed
        // TODO add your handling code here:
        Pescados pescado = null;
        try {
            pescado = new Pescados(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        pescado.setVisible(true);
    }//GEN-LAST:event_jMenuItemPescadosActionPerformed

    private void jMenuItemArrozActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemArrozActionPerformed
        // TODO add your handling code here:
        Arroces arroz = null;
        try {
            arroz = new Arroces(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        arroz.setVisible(true);
    }//GEN-LAST:event_jMenuItemArrozActionPerformed

    private void jMenuItemFideuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFideuaActionPerformed
        // TODO add your handling code here:
        Fideua fideua = null;
        try {
            fideua = new Fideua(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        fideua.setVisible(true);
    }//GEN-LAST:event_jMenuItemFideuaActionPerformed

    private void jMenuItemBebidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBebidasActionPerformed
        // TODO add your handling code here:
        Bebidas bebidas = null;
        try {
            bebidas = new Bebidas(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        bebidas.setVisible(true);
    }//GEN-LAST:event_jMenuItemBebidasActionPerformed

    private void jMenuItemPostresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPostresActionPerformed
        // TODO add your handling code here:
        Postres postres = null;
        try {
            postres = new Postres(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        postres.setVisible(true);
    }//GEN-LAST:event_jMenuItemPostresActionPerformed

    private void jMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuSalirActionPerformed

    private void jMenuItemActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarActionPerformed
        try {
            // TODO add your handling code here:
            cargarPlatosArroz(connection());
            cargarPlatosCaldos(connection());
            cargarPlatosCarnes(connection());
            cargarPlatosCombinados(connection());
            cargarPlatosEnsaladas(connection());
            cargarPlatosEntrantes(connection());
            cargarPlatosFideua(connection());
            cargarPlatosPasta(connection());
            cargarPlatosPescados(connection());
            cargarPlatosPostres(connection());
            cargarBebidas(connection());

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Configurar los JComboBox
        configurarComboBox(jComboPostres, "< Selecciona un plato >");
        configurarComboBox(jComboPescados, "< Selecciona un plato >");
        configurarComboBox(jComboPastas, "< Selecciona un plato >");
        configurarComboBox(jComboFideua, "< Selecciona un plato >");
        configurarComboBox(jComboEntrantes, "< Selecciona un plato >");
        configurarComboBox(jComboEnsaladas, "< Selecciona un plato >");
        configurarComboBox(jComboCombinados, "< Selecciona un plato >");
        configurarComboBox(jComboCarnes, "< Selecciona un plato >");
        configurarComboBox(jComboCaldo, "< Selecciona un plato >");
        configurarComboBox(jComboArroces, "< Selecciona un plato >");
        configurarComboBox(jComboBebidas, "< Selecciona una bebida >");
    }//GEN-LAST:event_jMenuItemActualizarActionPerformed

    private void jMenuItemBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBorrarActionPerformed
        // TODO add your handling code here:
        BorrarPlato borrar = null;
        try {
            borrar = new BorrarPlato(this, true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        borrar.setVisible(true);
    }//GEN-LAST:event_jMenuItemBorrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException {
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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);

                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboArroces;
    private javax.swing.JComboBox<String> jComboBebidas;
    private javax.swing.JComboBox<String> jComboCaldo;
    private javax.swing.JComboBox<String> jComboCarnes;
    private javax.swing.JComboBox<String> jComboCombinados;
    private javax.swing.JComboBox<String> jComboDiario;
    private javax.swing.JComboBox<String> jComboEnsaladas;
    private javax.swing.JComboBox<String> jComboEntrantes;
    private javax.swing.JComboBox<String> jComboFideua;
    private javax.swing.JComboBox<String> jComboMesa;
    private javax.swing.JComboBox<String> jComboPastas;
    private javax.swing.JComboBox<String> jComboPescados;
    private javax.swing.JComboBox<String> jComboPostres;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenuActualizar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuInicial;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItemActualizar;
    private javax.swing.JMenuItem jMenuItemArroz;
    private javax.swing.JMenuItem jMenuItemBebidas;
    private javax.swing.JMenuItem jMenuItemBorrar;
    private javax.swing.JMenuItem jMenuItemCarnes;
    private javax.swing.JMenuItem jMenuItemCombi;
    private javax.swing.JMenuItem jMenuItemEntrantes;
    private javax.swing.JMenuItem jMenuItemFideua;
    private javax.swing.JMenuItem jMenuItemPasta;
    private javax.swing.JMenuItem jMenuItemPescados;
    private javax.swing.JMenuItem jMenuItemPostres;
    private javax.swing.JMenu jMenuMesas;
    private javax.swing.JMenu jMenuPlatos;
    private javax.swing.JMenuItem jMenuSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaElementosMenu;
    private javax.swing.JTextArea jTextAreaMesa1;
    private javax.swing.JTextArea jTextAreaMesa2;
    private javax.swing.JTextArea jTextAreaMesa3;
    private javax.swing.JTextArea jTextAreaMesa4;
    private javax.swing.JTextArea jTextAreaMesa5;
    private javax.swing.JTextArea jTextAreaMesa6;
    private javax.swing.JTextArea jTextAreaMesa7;
    private javax.swing.JTextArea jTextAreaMesa8;
    private javax.swing.JTextArea jTextAreaMesa9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextFieldMesa;
    // End of variables declaration//GEN-END:variables
}
