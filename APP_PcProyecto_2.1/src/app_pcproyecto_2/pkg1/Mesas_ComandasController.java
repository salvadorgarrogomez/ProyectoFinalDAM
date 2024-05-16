package app_pcproyecto_2.pkg1;

import constructores.Mesas;
import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Categorias;
import constructores.DetallesComanda;
import constructores.Productos;
import constructores.TicketComanda;
import constructores.Usuarios;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
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
    @FXML
    private ProgressIndicator progreso;

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
    private String numeroTicketGenerado;
    private ObservableList<StringProperty> textAreaData = FXCollections.observableArrayList();
    private boolean comandaConfirmada = false;

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

        textAreaData.add(ListMesa_1.textProperty());
        textAreaData.add(ListMesa_2.textProperty());
        textAreaData.add(ListMesa_3.textProperty());
        textAreaData.add(ListMesa_4.textProperty());
        textAreaData.add(ListMesa_5.textProperty());
        textAreaData.add(ListMesa_6.textProperty());
        textAreaData.add(ListMesa_7.textProperty());
        textAreaData.add(ListMesa_8.textProperty());
        textAreaData.add(ListMesa_9.textProperty());
        textAreaData.add(ListMesa_10.textProperty());
        textAreaData.add(ListBarra_1.textProperty());
        textAreaData.add(ListBarra_2.textProperty());
        textAreaData.add(ListBarra_3.textProperty());
        textAreaData.add(ListBarra_4.textProperty());

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

        for (TextArea textArea : listViewMap.values()) {
            textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // Verificar si el TextArea ha perdido el foco
                    actualizarComanda(); // Actualizar la comanda cuando se pierde el foco del TextArea
                }
            });
        }
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void confirmarComanda(ActionEvent event) {
        try {
            Mesas mesaSeleccionada = seleccionMesa.getValue();
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

                            if (!productoExistente) {
                                comandaTexto = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€\n",
                                        producto.getNombre(), cantidadSeleccionada, producto.getPrecio(), precioTotal);
                                textAreaMesa.appendText(comandaTexto);
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
                            comandaConfirmada = true;
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

    private void actualizarComanda() {
        // Recorrer cada mesa y obtener su TextArea correspondiente
        for (Map.Entry<String, TextArea> entry : listViewMap.entrySet()) {
            String nombreMesa = entry.getKey();
            TextArea textAreaMesa = entry.getValue();

            // Verificar si el TextArea no es nulo
            if (textAreaMesa != null) {
                String textoActual = textAreaMesa.getText();
                double totalMesaSinIVA = 0.0;
                StringBuilder nuevoTexto = new StringBuilder();

                // Procesar cada línea del texto de la comanda
                String[] lineas = textoActual.split("\n");

                for (String linea : lineas) {
                    String[] partes = linea.split(" - ");
                    if (partes.length == 4) { // Asegúrate de que la línea tenga el formato esperado
                        String nombreProducto = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].split(": ")[1].trim());
                        double precioUnitario = Double.parseDouble(partes[2].split(": ")[1].replace("€", "").replace(",", ".").trim());
                        double precioTotal = cantidad * precioUnitario;
                        totalMesaSinIVA += precioTotal;

                        // Actualizar la línea con la nueva cantidad y precio total
                        String nuevaLinea = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€",
                                nombreProducto, cantidad, precioUnitario, precioTotal);

                        // Agregar la línea al nuevo texto
                        nuevoTexto.append(nuevaLinea).append("\n");
                    } else {
                        // Si no es una línea válida, agregarla sin cambios
                        nuevoTexto.append(linea).append("\n");
                    }
                }

                // Establecer el nuevo texto en el TextArea
                textAreaMesa.setText(nuevoTexto.toString().trim()); // Eliminar el último salto de línea

                // Calcular el total con IVA para la mesa y actualizar los campos correspondientes
                double totalMesaConIVA = calcularTotalConIVA(totalMesaSinIVA);
                TextField textFieldTotal = textFieldTotalMap.get(nombreMesa);
                TextField textFieldTotalIVA = textFieldTotalIVAMap.get(nombreMesa);
                textFieldTotal.setText(String.format("%.2f", totalMesaSinIVA));
                textFieldTotalIVA.setText(String.format("%.2f", totalMesaConIVA));
            }
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

                // Consulta SQL para obtener las mesas desde la base de datos, ordenadas alfabéticamente
                String sql = "SELECT nombre FROM mesas ORDER BY nombre";
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
        try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM categorias ORDER BY id"); ResultSet resultado = consulta.executeQuery()) {

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
            // Mostrar la barra de progreso
            progreso.setVisible(true);

            Button botonConfirmacion = (Button) event.getSource();

            // Obtener el nombre de la mesa asociada al botón desde el mapa
            String nombreMesa = nombreMesaMap.get(botonConfirmacion);

            // Obtener el objeto Mesas correspondiente al nombre de la mesa
            Mesas mesaSeleccionada = obtenerMesaPorNombre(nombreMesa);
            Spinner<Integer> spinnerComensales = comensalesMap.get(mesaSeleccionada.getNombre());
            if (mesaSeleccionada != null) {
                // Obtener el botón correspondiente al nombre de la mesa seleccionada
                obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
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
                    final TicketComanda ticket = generarTicket(mesaSeleccionada, detallesComanda, totalSinIVA, totalConIVA, numComensales, textoComanda);
                    guardarTicketEnBD(ticket);
                    obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
                    // Insertar los detalles de la comanda en la base de datos
                    for (DetallesComanda detalle : detallesComanda) {
                        // Aquí utilizamos el botón obtenido para obtener el ID de la mesa
                        insertarDetallesComandaEnBD(mesaSeleccionada, detalle.getProducto_id(), detalle.getCantidad());
                    }
                    // Cambiar el estado de la mesa a "libre" y el número de comensales a 0
                    actualizarEstadoMesaEnBD(mesaSeleccionada, "libre", 0);
                    textAreaMesa.clear();
                    TextField textFieldTotal = textFieldTotalMap.get(mesaSeleccionada.getNombre());
                    TextField textFieldTotalIVA = textFieldTotalIVAMap.get(mesaSeleccionada.getNombre());
                    textFieldTotal.clear();
                    textFieldTotalIVA.clear();
                    spinnerComensales.getValueFactory().setValue(0);
                    // Mostrar el ticket en un popup
                    mostrarAlerta("Pago confirmado. Comanda cerrada.\nCargando ticket...", Alert.AlertType.INFORMATION);

                    // Iniciar el proceso de búsqueda del número de ticket
                    buscarNumeroTicket(event, ticket);
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

    @FXML
    private void buscarNumeroTicket(ActionEvent event, TicketComanda ticket) {
        // Mostrar la barra de progreso
        progreso.setVisible(true);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int segundosEspera = 3;
                int totalIteraciones = segundosEspera * 10; // 10 actualizaciones por segundo

                for (int i = 0; i < totalIteraciones; i++) {
                    // Calcular el progreso actual
                    double progresoActual = (double) i / totalIteraciones;

                    // Actualizar la barra de progreso en la interfaz de usuario
                    Platform.runLater(() -> progreso.setProgress(progresoActual));

                    // Esperar 100 milisegundos (para simular una décima de segundo)
                    Thread.sleep(100);
                }

                // Una vez completado el progreso, buscar el número de ticket
                String numeroTicketGenerado = obtenerNumeroTicketGenerado();
                if (numeroTicketGenerado != null) {
                    Platform.runLater(() -> {
                        ticket.setNum_ticket(numeroTicketGenerado);
                        mostrarContenidoPopup(ticket);
                    });
                }

                return null;
            }
        };

        task.setOnSucceeded(e -> {
            // Ocultar la barra de progreso cuando la tarea ha terminado
            progreso.setVisible(false);
        });

        new Thread(task).start();
    }

    private void mostrarContenidoPopup(TicketComanda ticket) {
        try {
            // Convertir el arreglo de bytes a un String
            String contenidoTexto = new String(ticket.getArchivo_ticket(), StandardCharsets.UTF_8);

            // Mostrar el contenido del ticket en un popup
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

    private String obtenerNumeroTicketGenerado() {
        String numeroTicket = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT num_ticket FROM ticket_comanda ORDER BY id DESC LIMIT 1";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        numeroTicket = rs.getString("num_ticket");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeroTicket;
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

    private Mesas obtenerMesaPorNombre(String nombreMesa) {
        // Iterar sobre la lista de mesas
        for (Mesas mesa : listaMesas) {
            String nombreBoton = mesa.getNombre(); // Obtener el nombre de la mesa
            if (nombreMesa.equals(nombreBoton)) {
                return mesa; // Retorna la mesa correspondiente al nombre
            }
        }
        // Si no se encuentra la mesa, retorna null
        return null;
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
                        double precio = rs.getDouble("precio");
                        Productos producto = new Productos(id, nombreProducto, precio);
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

    public String obtenerNombreUsuarioPorId(int usuarioId) {
        String nombreUsuario = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT nombre FROM usuarios WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, usuarioId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        nombreUsuario = rs.getString("nombre");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreUsuario;
    }

    private TicketComanda generarTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales, String textoComanda) {

        // Generar el contenido del ticket
        String contenidoTicket = construirContenidoTicket(mesa, detallesComanda, totalSinIVA, totalConIVA, numComensales, textoComanda);

        // Crear una instancia de TicketComanda
        TicketComanda ticket = new TicketComanda();
        ticket.setNombre_mesa(mesa);
        ticket.setNum_ticket(numeroTicketGenerado); // Usar el número de ticket generado previamente
        ticket.setArchivo_ticket(contenidoTicket.getBytes());
        ticket.setImporte_total_sin_IVA((double) totalSinIVA);
        ticket.setImporte_total_con_IVA((double) totalConIVA);
        ticket.setNum_comensales(numComensales);
        ticket.setUsuario_id(usuario); // Utiliza setUsuario_id para establecer el usuario
        ticket.setFecha_pedido(new Date());

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

    private String centrarTexto(String texto, int longitudTotal) {
        int longitudTexto = texto.length();
        if (longitudTexto >= longitudTotal) {
            return texto; // No es necesario centrar, devuelve el texto original
        } else {
            int espaciosPorAgregar = longitudTotal - longitudTexto;
            int espaciosAntes = espaciosPorAgregar / 2;
            int espaciosDespues = espaciosPorAgregar - espaciosAntes;
            StringBuilder textoCentrado = new StringBuilder();
            for (int i = 0; i < espaciosAntes; i++) {
                textoCentrado.append(" ");
            }
            textoCentrado.append(texto);
            for (int i = 0; i < espaciosDespues; i++) {
                textoCentrado.append(" ");
            }
            return textoCentrado.toString();
        }
    }

    private String construirContenidoTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales, String textoComanda) {
        StringBuilder contenidoTicket = new StringBuilder();
        int longitudTotal = 80;
        int longitudTotalFactura = 50;
        int longitudTotalNIF = 75;
        int longitudTotalTele = 70;
        String nombreUsuario = obtenerNombreUsuarioPorId(usuario.getId());

        contenidoTicket.append(centrarTexto("NIF: 12345678X", longitudTotalNIF)).append("\n");
        contenidoTicket.append(centrarTexto("Bar ElEscobar", longitudTotal)).append("\n");
        contenidoTicket.append(centrarTexto("623191754 | 683572682", longitudTotalTele)).append("\n\n\n");

        contenidoTicket.append(mesa.getNombre()).append("\n");
        contenidoTicket.append(new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date())).append("\n\n");
        contenidoTicket.append(centrarTexto("***NO VALIDO COMO FACTURA***", longitudTotalFactura)).append("\n");

        // Detalles de la comanda
        contenidoTicket.append("Comandas:\n");
        contenidoTicket.append(String.format("%-5s%-35s%-15s%-10s\n", "Cant.", "Producto", "Precio", "Total"));
        contenidoTicket.append("-----------------------------------------\n");

        // Procesar cada línea del texto de la comanda
        String[] lineas = textoComanda.split("\n");
        for (String linea : lineas) {
            String[] campos = linea.split(" - ");
            String nombreProducto = campos[0];
            int cantidad = Integer.parseInt(campos[1].split(": ")[1]);
            Productos producto = obtenerProductoPorNombre(nombreProducto);
            if (producto != null) {
                double precio = producto.getPrecio();
                double totalLinea = cantidad * precio;
                if (nombreProducto.length() > 15) {
                    nombreProducto = nombreProducto.substring(0, 15) + "...";
                    nombreProducto = String.format("%1$-20s", nombreProducto);
                }
                // Añadir tabuladores para separar las columnas
                contenidoTicket.append(String.format("%-5s\t%-30s\t%-15s%-10s\n", cantidad, nombreProducto, String.format("%.2f€", precio), String.format("%.2f€", totalLinea)));
            }
        }

        contenidoTicket.append("-----------------------------------------\n");
        contenidoTicket.append("Número de comensales: ").append(numComensales).append("\n\n");
        contenidoTicket.append("IVA: 10% \t\t\t");
        double importeIVA = totalConIVA - totalSinIVA;
        contenidoTicket.append("Cuota: ").append(String.format("%.2f", importeIVA)).append("€\n");
        contenidoTicket.append("Base: ").append(String.format("%.2f", totalSinIVA)).append("€ \t\t");
        contenidoTicket.append("Base + Cuota: ").append(String.format("%.2f", totalConIVA)).append("€\n\n");
        contenidoTicket.append("Total (Impuestos Incl.): ").append(String.format("%.2f", totalConIVA)).append("€\n");
        contenidoTicket.append("-----------------------------------------\n");

        contenidoTicket.append("Le atendió ").append(nombreUsuario).append("\n");
        contenidoTicket.append("Gracias por su visita/Thanks for your visit").append("\n");

        return contenidoTicket.toString();
    }

    public ObservableList<StringProperty> getTextAreaData() {
        return textAreaData;
    }

    void setTextAreaData(ObservableList<StringProperty> textAreaData) {
        this.textAreaData = textAreaData;
    }

}
