/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app_pcproyecto_2.pkg0;

import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
import constructores.DetallesComanda;
import constructores.Mesas;
import constructores.Productos;
import constructores.TicketComanda;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author salva
 */
public class DetallesComandaController implements Initializable {

    @FXML
    private DatePicker calendario;
    @FXML
    private ListView<TicketComanda> listTicket;
    @FXML
    private ListView<String> comandasDIA;
    @FXML
    private ListView<String> comandasMES;
    @FXML
    private TextField totalDIAsinIVA;
    @FXML
    private TextField totalDIAconIVA;
    @FXML
    private TextField comensalesDIA;
    @FXML
    private TextField totalMESsinIVA;
    @FXML
    private TextField totalMESconIVA;
    @FXML
    private TextField comensalesMES;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void manejarSeleccionFecha() {
            // Obtener la fecha seleccionada en el DatePicker
            LocalDate fechaSeleccionada = calendario.getValue();

            // Obtener los tickets para el día seleccionado
            List<TicketComanda> ticketsDia = obtenerTicketsPorFecha(fechaSeleccionada);
            List<TicketComanda> ticketsMes = obtenerTicketsPorMes(fechaSeleccionada);
            List<DetallesComanda> detallesComandaDia = obtenerDetallesComandaPorDia(fechaSeleccionada);
            List<DetallesComanda> detallesComandaMes = obtenerDetallesComandaPorMes(fechaSeleccionada);

            // Calcular los totales del mes
            double totalSinIVAMes = 0;
            double totalConIVAMes = 0;
            int totalComensalesMes = 0;
            for (TicketComanda ticket : ticketsMes) {
                totalSinIVAMes += ticket.getImporte_total_sin_IVA();
                totalConIVAMes += ticket.getImporte_total_con_IVA();
                totalComensalesMes += ticket.getNum_comensales();
            }

            double totalSinIVA = 0;
            double totalConIVA = 0;
            int totalComensales = 0;
            for (TicketComanda ticket : ticketsDia) {
                totalSinIVA += ticket.getImporte_total_sin_IVA();
                totalConIVA += ticket.getImporte_total_con_IVA();
                totalComensales += ticket.getNum_comensales();
            }

            // Mostrar los totales en los TextField
            totalDIAsinIVA.setText(String.format("%.2f", totalSinIVA) + "€");
            totalDIAconIVA.setText(String.format("%.2f", totalConIVA) + "€");
            comensalesDIA.setText(String.valueOf(totalComensales));

            // Mostrar los totales del mes en los TextField
            totalMESsinIVA.setText(String.format("%.2f", totalSinIVAMes) + "€");
            totalMESconIVA.setText(String.format("%.2f", totalConIVAMes) + "€");
            comensalesMES.setText(String.valueOf(totalComensalesMes));

            // Mostrar los tickets del día en el ListView
            mostrarTickets(ticketsDia);
            mostrarDetallesComandaDia(detallesComandaDia);
            mostrarDetallesComandaMes(detallesComandaMes);

            // Ocultar la barra de progreso cuando se completan todas las operaciones
    }

    // Método para obtener los tickets asociados a una fecha específica desde la base de datos
    private List<TicketComanda> obtenerTicketsPorMes(LocalDate fecha) {
        List<TicketComanda> tickets = new ArrayList<>();

        // Obtener el primer día del mes y el último día del mes
        LocalDate primerDiaMes = fecha.withDayOfMonth(1);
        LocalDate ultimoDiaMes = fecha.withDayOfMonth(fecha.lengthOfMonth());

        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM ticket_comanda WHERE fecha_pedido::date >= ? AND fecha_pedido::date <= ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, primerDiaMes);
                pstmt.setObject(2, ultimoDiaMes);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Iterar sobre los resultados y crear objetos TicketComanda
                    while (rs.next()) {
                        TicketComanda ticket = new TicketComanda();
                        ticket.setId(rs.getInt("id"));
                        String nombreMesa = rs.getString("nombre_mesa");
                        // Implementa el método obtenerMesaPorNombre para obtener el objeto Mesas
                        Mesas mesa = obtenerMesaPorNombre(nombreMesa);
                        ticket.setNombre_mesa(mesa);
                        ticket.setNum_ticket(rs.getString("num_ticket"));
                        ticket.setArchivo_ticket(rs.getBytes("archivo_ticket"));
                        ticket.setImporte_total_sin_IVA(rs.getDouble("importe_total_sin_IVA"));
                        ticket.setImporte_total_con_IVA(rs.getDouble("importe_total_con_IVA"));
                        ticket.setNum_comensales(rs.getInt("num_comensales"));
                        ticket.setFecha_pedido(rs.getDate("fecha_pedido"));
                        tickets.add(ticket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    private List<TicketComanda> obtenerTicketsPorFecha(LocalDate fecha) {
        List<TicketComanda> tickets = new ArrayList<>();

        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM ticket_comanda WHERE fecha_pedido::date = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, fecha);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Iterar sobre los resultados y crear objetos TicketComanda
                    while (rs.next()) {
                        TicketComanda ticket = new TicketComanda();
                        ticket.setId(rs.getInt("id"));
                        String nombreMesa = rs.getString("nombre_mesa");
                        Mesas mesa = obtenerMesaPorNombre(nombreMesa);
                        ticket.setNombre_mesa(mesa);
                        ticket.setNum_ticket(rs.getString("num_ticket"));
                        ticket.setArchivo_ticket(rs.getBytes("archivo_ticket"));
                        ticket.setImporte_total_sin_IVA(rs.getDouble("importe_total_sin_IVA"));
                        ticket.setImporte_total_con_IVA(rs.getDouble("importe_total_con_IVA"));
                        ticket.setNum_comensales(rs.getInt("num_comensales"));
                        ticket.setFecha_pedido(rs.getDate("fecha_pedido"));
                        tickets.add(ticket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    // Método para mostrar los tickets en el ListView
    private void mostrarTickets(List<TicketComanda> tickets) {
        listTicket.getItems().setAll(tickets);
    }

    // Método para manejar la selección de un ticket en el ListView
    @FXML
    private void handleTicketSelection(MouseEvent event) {
        if (event.getClickCount() == 1) { // Verifica si se ha hecho un solo clic
            // Obtener el ticket seleccionado en la lista
            TicketComanda ticketSeleccionado = listTicket.getSelectionModel().getSelectedItem();
            if (ticketSeleccionado != null) {
                // Mostrar el contenido del ticket en un popup
                mostrarContenidoPopup(ticketSeleccionado);
            }
        }
    }

    // Método para mostrar el contenido de un ticket seleccionado en un popup
    private void mostrarContenidoPopup(TicketComanda ticket) {
        try {
            // Convertir el arreglo de bytes a un String
            String contenidoTexto = new String(ticket.getArchivo_ticket(), StandardCharsets.UTF_8);

            // Agregar el número de ticket al contenido del ticket
            contenidoTexto += "\n\nNúmero de ticket: " + ticket.getNum_ticket();

            // Mostrar el contenido del ticket en un popup
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contenido del ticket - " + ticket.getNum_ticket());
            alert.setHeaderText("Contenido del ticket");
            alert.setContentText(contenidoTexto);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el objeto Mesas por su nombre
    private Mesas obtenerMesaPorNombre(String nombreMesa) {
        Mesas mesa = null;
        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM mesas WHERE nombre = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombreMesa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Construir el objeto Mesas
                        mesa = new Mesas();
                        mesa.setNombre(rs.getString("nombre"));
                        // Otros campos...
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesa;
    }

    private Productos obtenerProductoPorId(int productoId) {
        Productos producto = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM productos WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, productoId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        producto = new Productos();
                        producto.setNombre(rs.getString("nombre"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    private void mostrarDetallesComandaDia(List<DetallesComanda> detallesComandaDia) {
        ObservableList<String> detallesList = FXCollections.observableArrayList();

        // Iterar sobre los detalles de la comanda y agregarlos a la lista
        for (DetallesComanda detalle : detallesComandaDia) {
            String detalleString = " - " + detalle.getProducto_id().getNombre() + " - " + detalle.getCantidad();
            detallesList.add(detalleString);
        }

        // Establecer la lista de detalles en el ListView
        comandasDIA.setItems(detallesList);
    }

    private void mostrarDetallesComandaMes(List<DetallesComanda> detallesComandaMes) {
        ObservableList<String> detallesList = FXCollections.observableArrayList();

        // Iterar sobre los detalles de la comanda y agregarlos a la lista
        for (DetallesComanda detalle : detallesComandaMes) {
            String detalleString = " - " + detalle.getProducto_id().getNombre() + " - " + detalle.getCantidad();
            detallesList.add(detalleString);
        }

        // Establecer la lista de detalles en el ListView
        comandasMES.setItems(detallesList);
    }

    private List<DetallesComanda> obtenerDetallesComandaPorDia(LocalDate fecha) {
        List<DetallesComanda> detallesComanda = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT producto_id, SUM(cantidad) AS cantidad_total FROM detalles_comanda WHERE fecha_hora::date = ? GROUP BY producto_id ORDER BY cantidad_total DESC";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, fecha);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        DetallesComanda detalle = new DetallesComanda();
                        int productoId = rs.getInt("producto_id");
                        Productos producto = obtenerProductoPorId(productoId); // Método para obtener Producto por su ID
                        detalle.setProducto_id(producto);
                        detalle.setCantidad(rs.getInt("cantidad_total"));
                        detallesComanda.add(detalle);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallesComanda;
    }

    private List<DetallesComanda> obtenerDetallesComandaPorMes(LocalDate fecha) {
        List<DetallesComanda> detallesComanda = new ArrayList<>();

        // Obtener el primer y último día del mes
        LocalDate primerDiaMes = fecha.withDayOfMonth(1);
        LocalDate ultimoDiaMes = fecha.withDayOfMonth(fecha.lengthOfMonth());

        try (Connection connection = getConnection()) {
            String sql = "SELECT producto_id, SUM(cantidad) AS cantidad_total FROM detalles_comanda WHERE fecha_hora::date >= ? AND fecha_hora::date <= ? GROUP BY producto_id ORDER BY cantidad_total DESC";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, primerDiaMes);
                pstmt.setObject(2, ultimoDiaMes);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        DetallesComanda detalle = new DetallesComanda();
                        int productoId = rs.getInt("producto_id");
                        Productos producto = obtenerProductoPorId(productoId); // Método para obtener Producto por su ID
                        detalle.setProducto_id(producto);
                        detalle.setCantidad(rs.getInt("cantidad_total"));
                        detallesComanda.add(detalle);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallesComanda;
    }

}
