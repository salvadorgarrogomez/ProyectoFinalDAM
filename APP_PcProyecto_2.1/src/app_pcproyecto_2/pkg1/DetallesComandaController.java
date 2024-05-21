package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private boolean datosMesCalculados = false;
    private LocalDate fechaMesCalculado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void manejarSeleccionFecha() {
        // Se obtiene la fecha seleccionada en el DatePicker
        LocalDate fechaSeleccionada = calendario.getValue();
        if (!datosMesCalculados || fechaSeleccionada.getMonthValue() != fechaMesCalculado.getMonthValue()) {
            // Se calculan los datos del mes solo si no han sido calculados previamente o si la fecha seleccionada está en un mes diferente
            obtenerTicketsPorMes(fechaSeleccionada);
            // Se actualiza la fecha del mes para la cual se calcularon los datos
            fechaMesCalculado = fechaSeleccionada;
            datosMesCalculados = true;
        }
        // Se obtienen los tickets para el día y mes seleccionado, esto se realiza, creando un List con objetos TicketComanda, llamando a los metodos
        // recurrentes, donde se verifican los ticket que se deben de mostrar al seleccionar un dia en el datepicker
        List<TicketComanda> ticketsDia = obtenerTicketsPorFecha(fechaSeleccionada);
        List<TicketComanda> ticketsMes = obtenerTicketsPorMes(fechaSeleccionada);
        List<DetallesComanda> detallesComandaDia = obtenerDetallesComandaPorDia(fechaSeleccionada);
        List<DetallesComanda> detallesComandaMes = obtenerDetallesComandaPorMes(fechaSeleccionada);
        // Calculo de los totales por mes seleccionado
        double totalSinIVAMes = 0;
        double totalConIVAMes = 0;
        int totalComensalesMes = 0;
        for (TicketComanda ticket : ticketsMes) {
            totalSinIVAMes += ticket.getImporte_total_sin_IVA();
            totalConIVAMes += ticket.getImporte_total_con_IVA();
            totalComensalesMes += ticket.getNum_comensales();
        }
        // Calculo de los totales por dia seleccionado
        double totalSinIVA = 0;
        double totalConIVA = 0;
        int totalComensales = 0;
        for (TicketComanda ticket : ticketsDia) {
            totalSinIVA += ticket.getImporte_total_sin_IVA();
            totalConIVA += ticket.getImporte_total_con_IVA();
            totalComensales += ticket.getNum_comensales();
        }
        // Se muestran los totales por dia en los TextField
        totalDIAsinIVA.setText(String.format("%.2f", totalSinIVA) + "€");
        totalDIAconIVA.setText(String.format("%.2f", totalConIVA) + "€");
        comensalesDIA.setText(String.valueOf(totalComensales));
        // Se muestra los totales por mes seleccionado en los TextField
        totalMESsinIVA.setText(String.format("%.2f", totalSinIVAMes) + "€");
        totalMESconIVA.setText(String.format("%.2f", totalConIVAMes) + "€");
        comensalesMES.setText(String.valueOf(totalComensalesMes));
        // Se muestran los ticket asociados al dia seleccionado en el datepicker, llamando a determinados metodos para mostrar determinados datos donde sea 
        //  requerido en la escena
        mostrarTickets(ticketsDia);
        mostrarDetallesComandaDia(detallesComandaDia);
        mostrarDetallesComandaMes(detallesComandaMes);
    }

    // Método para obtener los tickets asociados a una fecha específica en este caso por mes, desde la base de datos
    private List<TicketComanda> obtenerTicketsPorMes(LocalDate fecha) {
        List<TicketComanda> tickets = new ArrayList<>();
        // Con LocalDate, se obtiene el primer día del mes y el último día del mes
        LocalDate primerDiaMes = fecha.withDayOfMonth(1);
        LocalDate ultimoDiaMes = fecha.withDayOfMonth(fecha.lengthOfMonth());
        // Conexion a la base de datos y se ejecuta la consulta
        try (Connection connection = getConnection()) {
            // La consulta sql se basa en la realizacion de un select, determinado en base a la fecha_pedido, valor generado de forma automatica en bbdd,
            // para determinar la fecha de creacion de la inserccion dentro de un mes determinado
            String sql = "SELECT * FROM ticket_comanda WHERE fecha_pedido::date >= ? AND fecha_pedido::date <= ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, primerDiaMes);
                pstmt.setObject(2, ultimoDiaMes);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Se iteran sobre los resultados y crean objetos TicketComanda
                    while (rs.next()) {
                        TicketComanda ticket = new TicketComanda();
                        ticket.setId(rs.getInt("id"));
                        String nombreMesa = rs.getString("nombre_mesa");
                        // Se implementa el método obtenerMesaPorNombre para obtener el objeto Mesas
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

    //  Igual que el metodo anterior, para para obtener en base al dia, en el anterior metodo, se realizaba en base al mes, en este caso, en base al dia
    //  especifico seleccionado en el datepicker
    private List<TicketComanda> obtenerTicketsPorFecha(LocalDate fecha) {
        List<TicketComanda> tickets = new ArrayList<>();
        // Conectar a la base de datos y ejecutar la consulta
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM ticket_comanda WHERE fecha_pedido::date = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, fecha);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Se iteran sobre los resultados y crear objetos TicketComanda
                    while (rs.next()) {
                        TicketComanda ticket = new TicketComanda();
                        ticket.setId(rs.getInt("id"));
                        String nombreMesa = rs.getString("nombre_mesa");
                        // Se implementa el método obtenerMesaPorNombre para obtener el objeto Mesas
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

    // Método para manejar la selección de un ticket en el ListView, en este caso, con este metodo se llama al metodo mostrarContenidoPopup, que nos permitira 
    //  mostrar el contenido de los ticket guardados en bbdd
    @FXML
    private void handleTicketSelection(MouseEvent event) {
        if (event.getClickCount() == 1) { // Verifica si se ha hecho un solo clic
            // Se obtiene el ticket seleccionado en la lista
            TicketComanda ticketSeleccionado = listTicket.getSelectionModel().getSelectedItem();
            if (ticketSeleccionado != null) {
                // Se muestra el contenido del ticket en un popup
                mostrarContenidoPopup(ticketSeleccionado);
            }
        }
    }

    // Método para mostrar el contenido de un ticket seleccionado en un popup
    private void mostrarContenidoPopup(TicketComanda ticket) {
        try {
            // Se convierte el arreglo de bytes a un String
            String contenidoTexto = new String(ticket.getArchivo_ticket(), StandardCharsets.UTF_8);
            // Se agrega el número de ticket al contenido del ticket
            contenidoTexto += "\n\nNúmero de ticket: " + ticket.getNum_ticket();
            // Se muestra el contenido del ticket en un popup
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
        // Conexión a la base de datos y ejecución de la consulta
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM mesas WHERE nombre = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombreMesa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Construcción del objeto Mesas
                        mesa = new Mesas();
                        mesa.setNombre(rs.getString("nombre"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesa;
    }

    // Método para obtener un objeto Producto por su ID, necesario para mostrar el nombre del producto en los detalles de la comanda,
    //  de tal forma, que con id, asociado se obtiene el nombre asociado para poder mostrarlo
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

    // Método para mostrar los detalles de la comanda del día en el ListView
    private void mostrarDetallesComandaDia(List<DetallesComanda> detallesComandaDia) {
        ObservableList<String> detallesList = FXCollections.observableArrayList();
        // Iteración sobre los detalles de la comanda y agregación a la lista
        for (DetallesComanda detalle : detallesComandaDia) {
            String detalleString = " - " + detalle.getProducto_id().getNombre() + " - " + detalle.getCantidad();
            detallesList.add(detalleString);
        }
        // Establecimiento de la lista de detalles en el ListView
        comandasDIA.setItems(detallesList);
    }

    // Método para mostrar los detalles de la comanda del mes en el ListView
    private void mostrarDetallesComandaMes(List<DetallesComanda> detallesComandaMes) {
        ObservableList<String> detallesList = FXCollections.observableArrayList();
        // Iteración sobre los detalles de la comanda y agregación a la lista
        for (DetallesComanda detalle : detallesComandaMes) {
            String detalleString = " - " + detalle.getProducto_id().getNombre() + " - " + detalle.getCantidad();
            detallesList.add(detalleString);
        }
        // Establecimiento de la lista de detalles en el ListView
        comandasMES.setItems(detallesList);
    }

    //  Metodos que me permiten obetener en base al dia y mes seleccionados en el datepicker, y en base a ellos obtener el total de cantidades acumuladas
    //  por producto, es decir, permite realizar un sumatorio de todas las cantidades a modo de estadistica, sobre cuales serian las comandas mas demandadas
    // Método para obtener los detalles de la comanda de un día específico
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

    // Método para obtener los detalles de la comanda de un mes específico
    private List<DetallesComanda> obtenerDetallesComandaPorMes(LocalDate fecha) {
        List<DetallesComanda> detallesComanda = new ArrayList<>();
        // Obtención del primer y último día del mes
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
