package app_pcproyecto_2.pkg0;

import constructores.Mesas;
import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
import constructores.Categorias;
import constructores.DetallesComanda;
import constructores.Pedidos;
import constructores.Productos;
import constructores.TicketComanda;
import constructores.Usuarios;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Mesas_ComandasController implements Initializable {

    @FXML
    private ComboBox<Mesas> seleccionMesa;
    @FXML
    private ComboBox<Categorias> seleccionCategoria;
    @FXML
    private Pane mesas;
    @FXML
    private Spinner cantidad;
    @FXML
    private TabPane tabPaneMesas;
    @FXML
    private ListView<Productos> listProductos;
    @FXML
    private TextArea ListMesa_1;
    @FXML
    private TextArea ListMesa_2;
    @FXML
    private TextArea ListMesa_3;
    @FXML
    private TextArea ListMesa_4;
    @FXML
    private TextArea ListMesa_5;
    @FXML
    private TextArea ListMesa_6;
    @FXML
    private TextArea ListMesa_7;
    @FXML
    private TextArea ListMesa_8;
    @FXML
    private TextArea ListMesa_9;
    @FXML
    private TextArea ListMesa_10;
    @FXML
    private TextArea ListBarra_1;
    @FXML
    private TextArea ListBarra_2;
    @FXML
    private TextArea ListBarra_3;
    @FXML
    private TextArea ListBarra_4;
    @FXML
    private Spinner comensalesMesa1;
    @FXML
    private Spinner comensalesMesa2;
    @FXML
    private Spinner comensalesMesa3;
    @FXML
    private Spinner comensalesMesa4;
    @FXML
    private Spinner comensalesMesa5;
    @FXML
    private Spinner comensalesMesa6;
    @FXML
    private Spinner comensalesMesa7;
    @FXML
    private Spinner comensalesMesa8;
    @FXML
    private Spinner comensalesMesa9;
    @FXML
    private Spinner comensalesMesa10;
    @FXML
    private Spinner comensalesBarra1;
    @FXML
    private Spinner comensalesBarra2;
    @FXML
    private Spinner comensalesBarra3;
    @FXML
    private Spinner comensalesBarra4;
    @FXML
    private TextField acumuladoMesa1;
    @FXML
    private TextField acumuladoMesa2;
    @FXML
    private TextField acumuladoMesa3;
    @FXML
    private TextField acumuladoMesa4;
    @FXML
    private TextField acumuladoMesa5;
    @FXML
    private TextField acumuladoMesa6;
    @FXML
    private TextField acumuladoMesa7;
    @FXML
    private TextField acumuladoMesa8;
    @FXML
    private TextField acumuladoMesa9;
    @FXML
    private TextField acumuladoMesa10;
    @FXML
    private TextField acumuladoBarra1;
    @FXML
    private TextField acumuladoBarra2;
    @FXML
    private TextField acumuladoBarra3;
    @FXML
    private TextField acumuladoBarra4;
    @FXML
    private TextField acumuladoMesa1conIVA;
    @FXML
    private TextField acumuladoMesa2conIVA;
    @FXML
    private TextField acumuladoMesa3conIVA;
    @FXML
    private TextField acumuladoMesa4conIVA;
    @FXML
    private TextField acumuladoMesa5conIVA;
    @FXML
    private TextField acumuladoMesa6conIVA;
    @FXML
    private TextField acumuladoMesa7conIVA;
    @FXML
    private TextField acumuladoMesa8conIVA;
    @FXML
    private TextField acumuladoMesa9conIVA;
    @FXML
    private TextField acumuladoMesa10conIVA;
    @FXML
    private TextField acumuladoBarra1conIVA;
    @FXML
    private TextField acumuladoBarra2conIVA;
    @FXML
    private TextField acumuladoBarra3conIVA;
    @FXML
    private TextField acumuladoBarra4conIVA;
    @FXML
    private Button confirmarMesa1;
    @FXML
    private Button confirmarMesa2;
    @FXML
    private Button confirmarMesa3;
    @FXML
    private Button confirmarMesa4;
    @FXML
    private Button confirmarMesa5;
    @FXML
    private Button confirmarMesa6;
    @FXML
    private Button confirmarMesa7;
    @FXML
    private Button confirmarMesa8;
    @FXML
    private Button confirmarMesa9;
    @FXML
    private Button confirmarMesa10;
    @FXML
    private Button confirmarBarra1;
    @FXML
    private Button confirmarBarra2;
    @FXML
    private Button confirmarBarra3;
    @FXML
    private Button confirmarBarra4;

    private List<Mesas> listaMesas;
    private Usuarios usuario;
    private Connection connection;
    private Map<Integer, Productos> productosMap;
    private ActualizacionMesasThread actualizacionMesasThread;
    private Map<Mesas, List<DetallesComanda>> detallesPorMesa = new HashMap<>();
    private Map<String, TextArea> listViewMap;
    private Map<Mesas, String> estadoAnterior = new HashMap<>();
    private Map<String, TextField> textFieldTotalMap = new HashMap<>();
    private Map<String, TextField> textFieldTotalIVAMap = new HashMap<>();
    private Map<String, Spinner<Integer>> comensalesMap;
    private Map<Button, String> nombreMesaMap = new HashMap<>();

    @Override
    @SuppressWarnings({"unchecked"})
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Mesas_ComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Inicializa la lista de mesas
        listViewMap = new HashMap<>();
        listaMesas = new ArrayList<>();
        cargarMesasEnComboBox();
        List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
        try {
            cargarCategorias();
        } catch (SQLException ex) {
            Logger.getLogger(Mesas_ComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> nombresCategorias = new ArrayList<>();
        for (Categorias categoria : categorias) {
            nombresCategorias.add(categoria.getNombre());
        }

        actualizacionMesasThread = new ActualizacionMesasThread();
        actualizacionMesasThread.start();
        actualizarEstadosDesdeBD();
        iniciarActualizacionContinua();
        productosMap = new HashMap<>();

        listViewMap = new HashMap<>();
        listViewMap.put("Mesa_1", ListMesa_1);
        listViewMap.put("Mesa_2", ListMesa_2);
        listViewMap.put("Mesa_3", ListMesa_3);
        listViewMap.put("Mesa_4", ListMesa_4);
        listViewMap.put("Mesa_5", ListMesa_5);
        listViewMap.put("Mesa_6", ListMesa_6);
        listViewMap.put("Mesa_7", ListMesa_7);
        listViewMap.put("Mesa_8", ListMesa_8);
        listViewMap.put("Mesa_9", ListMesa_9);
        listViewMap.put("Mesa_10", ListMesa_10);
        listViewMap.put("Barra_1", ListBarra_1);
        listViewMap.put("Barra_2", ListBarra_2);
        listViewMap.put("Barra_3", ListBarra_3);
        listViewMap.put("Barra_4", ListBarra_4);

        SpinnerValueFactory<Integer> gradesValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.cantidad.setValueFactory(gradesValueFactory);

        SpinnerValueFactory<Integer> gradesValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa1.setValueFactory(gradesValueFactory1);
        SpinnerValueFactory<Integer> gradesValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa2.setValueFactory(gradesValueFactory2);
        SpinnerValueFactory<Integer> gradesValueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa3.setValueFactory(gradesValueFactory3);
        SpinnerValueFactory<Integer> gradesValueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa4.setValueFactory(gradesValueFactory4);
        SpinnerValueFactory<Integer> gradesValueFactory5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa5.setValueFactory(gradesValueFactory5);
        SpinnerValueFactory<Integer> gradesValueFactory6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa6.setValueFactory(gradesValueFactory6);
        SpinnerValueFactory<Integer> gradesValueFactory7 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa7.setValueFactory(gradesValueFactory7);
        SpinnerValueFactory<Integer> gradesValueFactory8 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa8.setValueFactory(gradesValueFactory8);
        SpinnerValueFactory<Integer> gradesValueFactory9 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa9.setValueFactory(gradesValueFactory9);
        SpinnerValueFactory<Integer> gradesValueFactory10 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesMesa10.setValueFactory(gradesValueFactory10);
        SpinnerValueFactory<Integer> gradesValueFactory11 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesBarra1.setValueFactory(gradesValueFactory11);
        SpinnerValueFactory<Integer> gradesValueFactory12 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesBarra2.setValueFactory(gradesValueFactory12);
        SpinnerValueFactory<Integer> gradesValueFactory13 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesBarra3.setValueFactory(gradesValueFactory13);
        SpinnerValueFactory<Integer> gradesValueFactory14 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        this.comensalesBarra4.setValueFactory(gradesValueFactory14);

        comensalesMap = new HashMap<>();
        comensalesMap.put("Mesa_1", comensalesMesa1);
        comensalesMap.put("Mesa_2", comensalesMesa2);
        comensalesMap.put("Mesa_3", comensalesMesa3);
        comensalesMap.put("Mesa_4", comensalesMesa4);
        comensalesMap.put("Mesa_5", comensalesMesa5);
        comensalesMap.put("Mesa_6", comensalesMesa6);
        comensalesMap.put("Mesa_7", comensalesMesa7);
        comensalesMap.put("Mesa_8", comensalesMesa8);
        comensalesMap.put("Mesa_9", comensalesMesa9);
        comensalesMap.put("Mesa_10", comensalesMesa10);
        comensalesMap.put("Barra_1", comensalesBarra1);
        comensalesMap.put("Barra_2", comensalesBarra2);
        comensalesMap.put("Barra_3", comensalesBarra3);
        comensalesMap.put("Barra_4", comensalesBarra4);

        textFieldTotalMap = new HashMap<>();
        textFieldTotalMap.put("Mesa_1", acumuladoMesa1);
        textFieldTotalMap.put("Mesa_2", acumuladoMesa2);
        textFieldTotalMap.put("Mesa_3", acumuladoMesa3);
        textFieldTotalMap.put("Mesa_4", acumuladoMesa4);
        textFieldTotalMap.put("Mesa_5", acumuladoMesa5);
        textFieldTotalMap.put("Mesa_6", acumuladoMesa6);
        textFieldTotalMap.put("Mesa_7", acumuladoMesa7);
        textFieldTotalMap.put("Mesa_8", acumuladoMesa8);
        textFieldTotalMap.put("Mesa_9", acumuladoMesa9);
        textFieldTotalMap.put("Mesa_10", acumuladoMesa10);
        textFieldTotalMap.put("Barra_1", acumuladoBarra1);
        textFieldTotalMap.put("Barra_2", acumuladoBarra2);
        textFieldTotalMap.put("Barra_3", acumuladoBarra3);
        textFieldTotalMap.put("Barra_4", acumuladoBarra4);

        textFieldTotalIVAMap = new HashMap<>();
        textFieldTotalIVAMap.put("Mesa_1", acumuladoMesa1conIVA);
        textFieldTotalIVAMap.put("Mesa_2", acumuladoMesa2conIVA);
        textFieldTotalIVAMap.put("Mesa_3", acumuladoMesa3conIVA);
        textFieldTotalIVAMap.put("Mesa_4", acumuladoMesa4conIVA);
        textFieldTotalIVAMap.put("Mesa_5", acumuladoMesa5conIVA);
        textFieldTotalIVAMap.put("Mesa_6", acumuladoMesa6conIVA);
        textFieldTotalIVAMap.put("Mesa_7", acumuladoMesa7conIVA);
        textFieldTotalIVAMap.put("Mesa_8", acumuladoMesa8conIVA);
        textFieldTotalIVAMap.put("Mesa_9", acumuladoMesa9conIVA);
        textFieldTotalIVAMap.put("Mesa_10", acumuladoMesa10conIVA);
        textFieldTotalIVAMap.put("Barra_1", acumuladoBarra1conIVA);
        textFieldTotalIVAMap.put("Barra_2", acumuladoBarra2conIVA);
        textFieldTotalIVAMap.put("Barra_3", acumuladoBarra3conIVA);
        textFieldTotalIVAMap.put("Barra_4", acumuladoBarra4conIVA);

        nombreMesaMap = new HashMap<>();
        nombreMesaMap.put(confirmarMesa1, "Mesa_1");
        nombreMesaMap.put(confirmarMesa2, "Mesa_2");
        nombreMesaMap.put(confirmarMesa3, "Mesa_3");
        nombreMesaMap.put(confirmarMesa4, "Mesa_4");
        nombreMesaMap.put(confirmarMesa5, "Mesa_5");
        nombreMesaMap.put(confirmarMesa6, "Mesa_6");
        nombreMesaMap.put(confirmarMesa7, "Mesa_7");
        nombreMesaMap.put(confirmarMesa8, "Mesa_8");
        nombreMesaMap.put(confirmarMesa9, "Mesa_9");
        nombreMesaMap.put(confirmarMesa10, "Mesa_10");
        nombreMesaMap.put(confirmarBarra1, "Barra_1");
        nombreMesaMap.put(confirmarBarra2, "Barra_2");
        nombreMesaMap.put(confirmarBarra3, "Barra_3");
        nombreMesaMap.put(confirmarBarra4, "Barra_4");

        seleccionCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obtener los productos asociados a la categoría seleccionada
                List<Productos> productos = obtenerProductosPorCategoria(newValue.getId());
                // Mostrar los productos en el ListView
                mostrarProductosEnListView(productos);
            }
        });

        listProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Aquí puedes realizar cualquier acción cuando se selecciona un producto
            }
        });

        seleccionMesa.setOnAction(event -> {
            Mesas mesaSeleccionada = seleccionMesa.getValue();
            if (mesaSeleccionada != null) {
                // Obtener el nombre de la mesa seleccionada
                String nombreMesa = mesaSeleccionada.getNombre();
                // Llama al método para activar el tab correspondiente
                activarTab(nombreMesa);
            }
        });

    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void confirmarComanda(ActionEvent event) {
        try {
            Mesas mesaSeleccionada = seleccionMesa.getValue();
            Pedidos pedido = new Pedidos();
            Productos productoSeleccionado = listProductos.getSelectionModel().getSelectedItem();
            int cantidadSeleccionada = (int) cantidad.getValue();

            Spinner<Integer> spinnerComensales = comensalesMap.get(mesaSeleccionada.getNombre());
            if (spinnerComensales != null) {
                int numComensales = spinnerComensales.getValue();

                if (mesaSeleccionada != null && productoSeleccionado != null && cantidadSeleccionada > 0 && numComensales > 0) {
                    // Verificar si el producto seleccionado existe en el mapa de productos
                    if (productosMap.containsKey(productoSeleccionado.getId())) {
                        // Obtener el producto completo del mapa utilizando su ID
                        Productos producto = productosMap.get(productoSeleccionado.getId());

                        // Calcular el precio total del producto por la cantidad seleccionada
                        double precioTotal = producto.getPrecio() * cantidadSeleccionada;

                        // Obtener el TextArea correspondiente a la mesa seleccionada
                        TextArea textAreaMesa = listViewMap.get(mesaSeleccionada.getNombre());
                        if (textAreaMesa != null) {
                            // Obtener el texto actual del TextArea
                            String textoActual = textAreaMesa.getText();
                            String comandaTexto = "";

                            // Verificar si el producto ya está presente en el texto actual del TextArea
                            boolean productoExistente = false;
                            if (textoActual.contains(producto.getNombre())) {
                                String[] lineas = textoActual.split("\n");
                                for (String linea : lineas) {
                                    if (linea.contains(producto.getNombre())) {
                                        // Obtener la cantidad actual y calcular la nueva cantidad y precio total
                                        String[] partes = linea.split(" - ");
                                        int cantidadActual = Integer.parseInt(partes[1].split(": ")[1]);
                                        int nuevaCantidad = cantidadActual + cantidadSeleccionada;
                                        double nuevoPrecioTotal = producto.getPrecio() * nuevaCantidad;

                                        // Construir la nueva línea con la cantidad y el precio total actualizados
                                        comandaTexto = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€",
                                                producto.getNombre(), nuevaCantidad, producto.getPrecio(), nuevoPrecioTotal);

                                        // Reemplazar la línea actual con la nueva línea en el texto del TextArea
                                        textoActual = textoActual.replace(linea, comandaTexto);
                                        productoExistente = true;
                                        break;
                                    }
                                }
                            }

                            // Si el producto no está presente, agregar una nueva línea al texto del TextArea
                            if (!productoExistente) {
                                comandaTexto = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€\n",
                                        producto.getNombre(), cantidadSeleccionada, producto.getPrecio(), precioTotal);
                                textAreaMesa.appendText(comandaTexto);

                                // Incrementar el número de comanda y asociarlo a la mesa
                            } else {
                                // Si el producto ya está presente, reemplazar el texto completo del TextArea con el texto actualizado
                                textAreaMesa.setText(textoActual);
                            }

                            // Recalcular los totales después de agregar el nuevo producto
                            double totalSinIVA = calcularTotalComanda(textAreaMesa.getText());
                            double totalConIVA = calcularTotalConIVA(totalSinIVA);
                            TextField textFieldTotal = textFieldTotalMap.get(mesaSeleccionada.getNombre());
                            TextField textFieldTotalIVA = textFieldTotalIVAMap.get(mesaSeleccionada.getNombre());
                            textFieldTotal.setText(String.format("%.2f", totalSinIVA));
                            textFieldTotalIVA.setText(String.format("%.2f", totalConIVA));

                            // Insertar el pedido en la base de datos
                            insertarPedidoEnBD(pedido, mesaSeleccionada, producto, cantidadSeleccionada, producto.getPrecio(), numComensales);

                            // Obtener el número de mesa o barra seleccionado en el ComboBox
                            if (mesaSeleccionada != null) {
                                // Obtener el número de mesa o barra
                                String nombreMesa = mesaSeleccionada.getNombre();
                                int mesaNumber;
                                if (nombreMesa.startsWith("Mesa_")) {
                                    mesaNumber = Integer.parseInt(nombreMesa.substring("Mesa_".length()));
                                } else if (nombreMesa.startsWith("Barra_")) {
                                    mesaNumber = Integer.parseInt(nombreMesa.substring("Barra_".length()));
                                } else {
                                    // Manejar el caso donde el nombre de la mesa no sigue el formato esperado
                                    // Por ejemplo, puedes mostrar un mensaje de error o lanzar una excepción
                                    return; // O cualquier otra acción que necesites hacer en este caso
                                }

                                // Actualizar el estado de la mesa en la base de datos
                                actualizarEstadoMesaEnBD(mesaSeleccionada, "ocupado", numComensales);
                            }

                            // Mostrar mensaje de éxito al usuario
                            mostrarAlerta("Producto añadido a la comanda.", Alert.AlertType.INFORMATION);

                            // Limpiar la selección del producto y la cantidad después de enviar la comanda
                            listProductos.getSelectionModel().clearSelection();
                            cantidad.getValueFactory().setValue(0);
                        } else {
                            // Mensaje de error si no se encuentra el TextArea
                            mostrarAlerta("No se pudo encontrar el TextArea de la mesa seleccionada.", Alert.AlertType.ERROR);
                        }
                    } else {
                        // Producto no válido, mostrar mensaje de error
                        mostrarAlerta("El producto seleccionado no existe.", Alert.AlertType.INFORMATION);
                    }
                } else {
                    // Validación fallida
                    mostrarAlerta("Debe seleccionar una mesa, un producto y especificar una cantidad válida.\n Además, debes establecer el número de comensales.", Alert.AlertType.ERROR);
                }
            } else {
                // Mensaje de error si no se encuentra el Spinner de comensales
                mostrarAlerta("No se pudo encontrar el Spinner de comensales de la mesa seleccionada.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error al usuario si ocurre una excepción
            e.printStackTrace();
            mostrarAlerta("Se produjo un error al confirmar la comanda.", Alert.AlertType.ERROR);
        }
    }

    // Método para iniciar la actualización continua de las mesas
    private void iniciarActualizacionContinua() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // Actualiza los estados de las mesas desde la base de datos
            actualizarEstadosDesdeBD();
            // Actualiza los TextField en la interfaz de usuario
            actualizarTextFieldsWithMesas();
        }, 0, 5, TimeUnit.SECONDS); // Actualiza cada 5 segundos
    }

    private void actualizarEstadosDesdeBD() {
        listaMesas.clear(); // Limpiar la lista antes de actualizarla
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String sql = "SELECT id, nombre, estado FROM mesas"; // Seleccionar también el número de mesa
                try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre"); // Obtener el número de mesa
                        String estado = resultSet.getString("estado");
                        // Crear una nueva instancia de Mesas
                        Mesas mesa = new Mesas();
                        mesa.setId(id);
                        mesa.setNombre(nombre);
                        mesa.setEstado(estado);
                        // Agrega la mesa a la lista
                        listaMesas.add(mesa);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarTextFieldsWithMesas() {
        Platform.runLater(() -> {
            // Iterar sobre cada mesa en la lista
            for (Mesas mesa : listaMesas) {
                String estadoActual = mesa.getEstado();
                String estadoAntiguo = estadoAnterior.getOrDefault(mesa, "");

                // Verificar si el estado ha cambiado
                if (!estadoActual.equals(estadoAntiguo)) {
                    String nombreMesa = mesa.getNombre();
                    String fxId = nombreMesa.replaceAll("[^a-zA-Z0-9]", "");
                    TextField textField = (TextField) mesas.lookup("#" + fxId);

                    if (textField != null) {
                        // Actualizar el TextField con el estado de la mesa
                        textField.setText(estadoActual);

                        // Limpiar clases de estilo previas y aplicar nuevo estilo según el estado de la mesa
                        textField.getStyleClass().clear();
                        if (estadoActual.equals("libre")) {
                            textField.getStyleClass().add("text-field-libre"); // Agregar clase de estilo para libre
                        } else if (estadoActual.equals("ocupado")) {
                            textField.getStyleClass().add("text-field-ocupado"); // Agregar clase de estilo para ocupado
                        }

                        // Actualizar el estado anterior de la mesa
                        estadoAnterior.put(mesa, estadoActual);
                    }
                }
            }
        });
    }

    private void cargarMesasEnComboBox() {
        // Obtener la conexión a la base de datos
        try (Connection connection = getConnection()) {
            if (connection != null) {
                // Limpiar ComboBox
                seleccionMesa.getItems().clear();

                // Consulta SQL para obtener las mesas desde la base de datos
                String sql = "SELECT nombre FROM mesas";
                try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Obtener el nombre de la mesa de la fila actual
                        String nombre = resultSet.getString("nombre");
                        // Agregar el nombre de la mesa al ComboBox
                        seleccionMesa.getItems().add(new Mesas(nombre));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Categorias> obtenerCategoriasDesdeBD(Connection connection) {
        List<Categorias> categorias = new ArrayList<>();
        try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM categorias"); ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                // Crear instancia de Categorias con los valores recuperados de la base de datos
                Categorias categoria = new Categorias(id, nombre);
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categorias;
    }

    private void cargarCategorias() throws SQLException {
        try (Connection connection = getConnection()) {
            List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
            seleccionCategoria.getItems().addAll(categorias);
        }
    }

    private List<Productos> obtenerProductosPorCategoria(int categoriaId) {
        List<Productos> productos = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String sql = "SELECT * FROM productos WHERE categoria_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, categoriaId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String nombre = resultSet.getString("nombre");
                            double precio = resultSet.getDouble("precio");
                            String tipo_porcion = resultSet.getString("tipo_porcion");
                            // Otras columnas que desees obtener del resultado
                            // Crear una instancia de Producto y agregarla a la lista
                            Productos producto = new Productos(id, nombre, precio, tipo_porcion);
                            productos.add(producto);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private void mostrarProductosEnListView(List<Productos> productos) {
        ObservableList<Productos> observableList = FXCollections.observableArrayList(productos);
        listProductos.setItems(observableList);
        listProductos.setCellFactory(param -> new ListCell<Productos>() {
            @Override
            protected void updateItem(Productos producto, boolean empty) {
                super.updateItem(producto, empty);
                if (empty || producto == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s - Precio: %.2f€ - %s", producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getTipo_porcion()));
                    // Agregar el producto al mapa
                    productosMap.put(producto.getId(), producto);
                }
            }
        });
    }

    private void activarTab(String nombreMesa) {
        // Itera sobre los tabs en el TabPane
        for (Tab tab : tabPaneMesas.getTabs()) {
            // Verifica si el texto del tab coincide exactamente con el nombre de la mesa seleccionada
            if (tab.getId().equalsIgnoreCase(nombreMesa)) {
                // Selecciona el tab correspondiente
                tabPaneMesas.getSelectionModel().select(tab);
                return; // Importante: salir del bucle una vez encontrado el tab correspondiente
            }
        }
        for (Tab tab : tabPaneMesas.getTabs()) {
            if (tab.getText().startsWith("Barra")) {
                // Selecciona el tab correspondiente
                tabPaneMesas.getSelectionModel().select(tab);
                return; // Importante: salir del bucle una vez encontrado el tab correspondiente
            }
        }
        // Si no se encontró el tab correspondiente, imprime un mensaje de advertencia
    }

    private void insertarPedidoEnBD(Pedidos pedido, Mesas mesaSeleccionada, Productos producto, int cantidad, double precioUnitario, int numComensales) {
        try (Connection connection = getConnection()) {

            // Insertar el pedido en la base de datos
            String sql = "INSERT INTO pedidos (nombre_mesa, nombre_producto, cantidad_producto, precio_unitario, precio_total, num_comensales, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtInsert = connection.prepareStatement(sql)) {
                pstmtInsert.setString(1, mesaSeleccionada.getNombre());
                pstmtInsert.setString(2, producto.getNombre());
                pstmtInsert.setInt(3, cantidad);
                pstmtInsert.setDouble(4, precioUnitario);
                pstmtInsert.setDouble(5, precioUnitario * cantidad); // Calcula el precio total multiplicando el precio unitario por la cantidad
                pstmtInsert.setInt(6, numComensales);
                pstmtInsert.setInt(7, usuario.getId());
                pstmtInsert.executeUpdate();
                mostrarAlerta("Pedido insertado correctamente en la base de datos.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private class ActualizacionMesasThread extends Thread {

        private boolean running;

        public ActualizacionMesasThread() {
            this.running = true;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Platform.runLater(() -> {
                        actualizarTextFieldsWithMesas();
                    });
                    Thread.sleep(5000); // 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void detener() {
            running = false;
        }
    }

    private void actualizarEstadoMesaEnBD(Mesas mesa, String estado, int numComensales) {
        try (Connection connection = getConnection()) {
            // Preparar la consulta SQL para actualizar el estado de la mesa y el número de comensales
            String sql = "UPDATE mesas SET estado = ?, comensales = ? WHERE nombre = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, estado);
                statement.setInt(2, numComensales);
                statement.setString(3, mesa.getNombre());
                // Ejecutar la consulta
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    // Éxito, el estado de la mesa y el número de comensales se han actualizado
                } else {
                    // Falló, no se actualizó el estado de la mesa y el número de comensales
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calcularTotalComanda(String textoComanda) {
        double total = 0.0;
        if (!textoComanda.isEmpty()) {
            String[] lineas = textoComanda.split("\n");
            for (String linea : lineas) {
                String[] partes = linea.split(" - ");
                String precioTotalString = partes[3].split(": ")[1].replace(",", ".");
                double precioTotal = Double.parseDouble(precioTotalString.replace("€", ""));
                total += precioTotal;
            }
        }
        return total;
    }

    private double calcularTotalConIVA(double totalSinIVA) {
        // Calcular el total con IVA
        double totalConIVA = totalSinIVA * 1.10; // Asumiendo un IVA del 10%
        return totalConIVA;
    }

    @FXML
    private void confirmarPago(ActionEvent event) {
        try {
            Mesas mesaSeleccionada = seleccionMesa.getValue();
            Button botonMesa = null;
            Spinner<Integer> spinnerComensales = comensalesMap.get(mesaSeleccionada.getNombre());
            if (mesaSeleccionada != null) {
                // Obtener el botón correspondiente al nombre de la mesa seleccionada
                botonMesa = obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
                // Obtener el TextArea correspondiente a la mesa seleccionada
                TextArea textAreaMesa = listViewMap.get(mesaSeleccionada.getNombre());
                if (textAreaMesa != null) {
                    // Obtener el texto actual del TextArea
                    String textoComanda = textAreaMesa.getText();
                    // Obtener los detalles de la comanda desde el texto del TextArea
                    List<DetallesComanda> detallesComanda = obtenerDetallesComandaDesdeTexto(textoComanda, mesaSeleccionada);

                    double totalSinIVA = calcularTotalComanda(textoComanda);
                    double totalConIVA = calcularTotalConIVA(totalSinIVA);
                    // Obtener el número de comensales
                    int numComensales = spinnerComensales.getValue();
                    TicketComanda ticket = generarTicket(mesaSeleccionada, detallesComanda, totalSinIVA, totalConIVA, numComensales);
                    guardarTicketEnBD(ticket);
                    botonMesa = obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
                    // Insertar los detalles de la comanda en la base de datos
                    for (DetallesComanda detalle : detallesComanda) {
                        // Aquí utilizamos el botón obtenido para obtener el ID de la mesa
                        insertarDetallesComandaEnBD(mesaSeleccionada, detalle.getProducto_id(), detalle.getCantidad());
                    }
                    // Cambiar el estado de la mesa a "libre" y el número de comensales a 0
                    actualizarEstadoMesaEnBD(mesaSeleccionada, "libre", 0);
                    // Limpiar el TextArea de la mesa seleccionada
                    textAreaMesa.clear();
                    mostrarAlerta("Pago confirmado. Comanda cerrada.", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("No se pudo encontrar el TextArea de la mesa seleccionada.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Debe seleccionar una mesa para confirmar el pago.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error al usuario si ocurre una excepción
            e.printStackTrace();
            mostrarAlerta("Se produjo un error al confirmar el pago.", Alert.AlertType.ERROR);
        }
    }

    private Button obtenerBotonPorNombreMesa(String nombreMesa) {
        // Iterar sobre el HashMap para encontrar el botón correspondiente al nombre de la mesa
        for (Map.Entry<Button, String> entry : nombreMesaMap.entrySet()) {
            if (entry.getValue().equals(nombreMesa)) {
                return entry.getKey(); // Devolver el botón correspondiente al nombre de la mesa
            }
        }
        return null; // Retornar null si no se encuentra el botón
    }

    private List<DetallesComanda> obtenerDetallesComandaDesdeTexto(String textoComanda, Mesas mesaSeleccionada) {
        List<DetallesComanda> detallesComanda = new ArrayList<>();
        String[] lineas = textoComanda.split("\n");

        for (String linea : lineas) {
            String[] campos = linea.split(" - ");
            String nombreProducto = campos[0];
            int cantidad = Integer.parseInt(campos[1].split(": ")[1]);
            Productos producto = obtenerProductoPorNombre(nombreProducto);
            if (producto != null) {
                DetallesComanda detalle = new DetallesComanda(mesaSeleccionada, producto, cantidad);
                detallesComanda.add(detalle);
            }
        }

        return detallesComanda;
    }

    private void insertarDetallesComandaEnBD(Mesas mesaSeleccionada, Productos producto, int cantidad) {
        try (Connection connection = getConnection()) {
            // Obtener el nombre de la mesa desde el objeto mesa seleccionada
            String nombreMesa = mesaSeleccionada.getNombre();
            Button botonMesa = nombreMesaMap.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(nombreMesa))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (botonMesa != null) {
                // Insertar el pedido en la base de datos
                String sql = "INSERT INTO detalles_comanda (nombre_mesa, producto_id, cantidad, usuario_id) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmtInsert = connection.prepareStatement(sql)) {
                    pstmtInsert.setString(1, mesaSeleccionada.getNombre()); // Aquí se utiliza el ID de la mesa
                    pstmtInsert.setInt(2, producto.getId()); // Aquí se utiliza el ID del producto
                    pstmtInsert.setInt(3, cantidad);
                    pstmtInsert.setInt(4, usuario.getId());
                    pstmtInsert.executeUpdate();
                }
            } else {
                // Mostrar mensaje de error si no se encontró el botón
                mostrarAlerta("No se encontró el botón asociado al nombre de la mesa.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Productos obtenerProductoPorNombre(String nombreProducto) {
        // Suponiendo que tienes una conexión a tu base de datos
        try (Connection connection = getConnection()) {
            // Preparar la consulta SQL para buscar el producto por su nombre
            String sql = "SELECT * FROM productos WHERE nombre = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombreProducto);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        Productos producto = new Productos(id, nombreProducto);
                        return producto;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TicketComanda generarTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales) {
        // Generar el contenido del ticket
        String contenidoTicket = construirContenidoTicket(mesa, detallesComanda, totalSinIVA, totalConIVA, numComensales);

        // Crear una instancia de TicketComanda
        TicketComanda ticket = new TicketComanda();
        ticket.setNombre_mesa(mesa);
        ticket.setNum_ticket(generarNumeroTicket());
        ticket.setArchivo_ticket(contenidoTicket.getBytes());
        ticket.setImporte_total_sin_IVA((int) totalSinIVA);
        ticket.setImporte_total_con_IVA((int) totalConIVA);
        ticket.setNum_comensales(numComensales);
//        ticket.setUsuario_id(usuarioId); // Utiliza setUsuario_id para establecer el usuario
        ticket.setFecha_hora(new Date());

        return ticket;
    }

    private void guardarTicketEnBD(TicketComanda ticket) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO ticket_comanda (nombre_mesa, num_ticket, archivo_ticket, importe_total_con_iva, importe_total_sin_iva, num_comensales, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, ticket.getNombre_mesa().getNombre());
                pstmt.setString(2, ticket.getNum_ticket());
                pstmt.setBytes(3, ticket.getArchivo_ticket());
                pstmt.setBigDecimal(4, BigDecimal.valueOf(ticket.getImporte_total_con_IVA()));
                pstmt.setBigDecimal(5, BigDecimal.valueOf(ticket.getImporte_total_sin_IVA()));
                pstmt.setInt(6, ticket.getNum_comensales());
                pstmt.setInt(7, usuario.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar el ticket en la base de datos.", Alert.AlertType.ERROR);
        }
    }

    private String generarNumeroTicket() {
        // Generar un número de ticket único basado en la fecha y hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fechaHora = sdf.format(new Date());
        return "TICKET-" + fechaHora;
    }

    private String construirContenidoTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales) {
        StringBuilder contenidoTicket = new StringBuilder();

        // Encabezado del ticket
        contenidoTicket.append(mesa.getNombre()).append("\n");
        contenidoTicket.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n\n");

        // Detalles de la comanda
        contenidoTicket.append("Comandas:\n");
        contenidoTicket.append("Cant.Producto").append("\t\tPrecio").append("\t\tTotal").append("\n");
        contenidoTicket.append("-----------------------------------------").append("\n");
        for (DetallesComanda detalle : detallesComanda) {
//            Productos producto = obtenerProductoPorNombre(nombreProducto); // Suponiendo que tienes un método para obtener el objeto Producto por su ID
//            contenidoTicket.append("Cantidad ").append(detalle.getCantidad()).append(": ").append(producto.getNombre()).append("\t");
//            contenidoTicket.append(producto.getPrecio()).append("\t");
//            contenidoTicket.append(detalle.getCantidad() * producto.getPrecio()).append("\n");
        }
        contenidoTicket.append("\n");

        // Total sin impuestos
        contenidoTicket.append("Pago (sin impuestos): ").append(String.format("%.2f", totalSinIVA)).append("€").append("\n");

        // Total con impuestos
        contenidoTicket.append("Total (impuestos incluidos): ").append(String.format("%.2f", totalConIVA)).append("€").append("\n\n");

        // Agradecimiento
        contenidoTicket.append("Gracias por su visita");

        return contenidoTicket.toString();
    }

}
