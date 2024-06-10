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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
    private Map<String, TextArea> listViewMap;
    private Map<Mesas, String> estadoAnterior = new HashMap<>();
    private Map<String, TextField> textFieldTotalMap = new HashMap<>();
    private Map<String, TextField> textFieldTotalIVAMap = new HashMap<>();
    private Map<String, Spinner<Integer>> comensalesMap;
    private Map<Button, String> nombreMesaMap = new HashMap<>();
    private String numeroTicketGenerado;

    @Override
    @SuppressWarnings({"unchecked"})
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Mesas_ComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Se inicializa la lista de mesas mediante un hasmap para poder trabajar con estas mesas en cualquier momento desde el Controller
        listViewMap = new HashMap<>();
        //  Array con las mesas existentes en la bbdd
        listaMesas = new ArrayList<>();
        //  Se cargan las mesas en el combox requerido, llamando al metodo con el funcionamiento
        cargarMesasEnComboBox();
        //  Creacion de un List para obtener todas las categorias presentes en la bbdd llamando al metodo con la consulta Select para ello
        List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
        try {
            //  Llamada al metodo que permite cargar los datos en el comnbox requerido, para poder trabajar con la categorias
            cargarCategorias();
        } catch (SQLException ex) {
            Logger.getLogger(Mesas_ComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> nombresCategorias = new ArrayList<>();
        for (Categorias categoria : categorias) {
            nombresCategorias.add(categoria.getNombre());
        }
        //  Inicializacion de los metodos, que iniciacializan de forma automatica un hilo paralelo
        actualizacionMesasThread = new ActualizacionMesasThread();
        actualizacionMesasThread.start();
        actualizarEstadosDesdeBD();
        productosMap = new HashMap<>();
        //  Creacion de una serie de hasmap, para tener localizados y poder trabajar con varios elementos a la misma vez
        //  Hasmap de las mesas, en este caso de los TextArea donde se colocarian los datos de las comandas
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
        //  Inicializacion de los spinner, para trabajar con ellos, para que muestren y permitan asignar un valor numerico
        //  De serie los spinner no estan inicializados, y no permite asignar valores en la interfaz
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
        //  Hashmap, que contienen todos los spinner presentan en la escena
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
        //  Hashmap que contiene en su conjunto unos textfield
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
        //  Hashmap que contiene en su conjunto unos textfield
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
        //  Hashmap que contiene en su conjunto todos los botones presentes en la escena para confirmar los pagos
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
        // Listener para el ComboBox de selección de categoría
        seleccionCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Se obtienen los productos asociados a la categoría seleccionada
                List<Productos> productos = obtenerProductosPorCategoria(newValue.getId());
                // Se muestran  los productos en el ListView
                mostrarProductosEnListView(productos);
            }
        });
        // Listener para el ListView de productos
        listProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            }
        });
        // Acción al seleccionar una mesa en el ComboBox
        seleccionMesa.setOnAction(event -> {
            Mesas mesaSeleccionada = seleccionMesa.getValue();
            if (mesaSeleccionada != null) {
                // Se obtiene el nombre de la mesa seleccionada
                String nombreMesa = mesaSeleccionada.getNombre();
                // Se llama al método para activar el tab correspondiente
                activarTab(nombreMesa);
            }
        });
        // Se itera sobre el mapa de TextAreas asociados a los productos en la comanda
        for (TextArea textArea : listViewMap.values()) {
            // Listener para detectar si un TextArea pierde el foco
            textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // Verificar si el TextArea ha perdido el foco
                    actualizarComanda(); // Actualizar la comanda cuando se pierde el foco del TextArea
                }
            });
        }
    }

    // Método que se activa como un evento al confirmar una comanda desde la escena, en este caso, a través de un botón.
    @FXML
    @SuppressWarnings("unchecked")
    private void confirmarComanda(ActionEvent event) {
        try {
            // Se obtiene la mesa seleccionada y el producto seleccionado
            Mesas mesaSeleccionada = seleccionMesa.getValue();
            Productos productoSeleccionado = listProductos.getSelectionModel().getSelectedItem();
            // Se obtiene la cantidad seleccionada y el Spinner de comensales asociado a la mesa
            int cantidadSeleccionada = (int) cantidad.getValue();
            Spinner<Integer> spinnerComensales = comensalesMap.get(mesaSeleccionada.getNombre());
            // Se verifica si se encontró el Spinner de comensales
            if (spinnerComensales != null) {
                int numComensales = spinnerComensales.getValue();
                // Se valida la selección de mesa, producto, cantidad y número de comensales
                if (mesaSeleccionada != null && productoSeleccionado != null && cantidadSeleccionada > 0 && numComensales > 0) {
                    // Se verifica si el producto seleccionado existe en el mapa de productos
                    if (productosMap.containsKey(productoSeleccionado.getId())) {
                        // Se obtiene  el producto completo del mapa utilizando su ID
                        Productos producto = productosMap.get(productoSeleccionado.getId());
                        // Se calcula el precio total del producto por la cantidad seleccionada
                        double precioTotal = producto.getPrecio() * cantidadSeleccionada;
                        // Se obtiene el TextArea correspondiente a la mesa seleccionada
                        TextArea textAreaMesa = listViewMap.get(mesaSeleccionada.getNombre());
                        // Se verifica si se encontró el TextArea de la mesa
                        if (textAreaMesa != null) {
                            // Se obtiene el texto actual del TextArea
                            String textoActual = textAreaMesa.getText();
                            String comandaTexto = "";
                            // Se verifica si el producto ya está presente en el texto actual del TextArea
                            boolean productoExistente = false;
                            if (textoActual.contains(producto.getNombre())) {
                                // Actualizacion de la cantidad y el precio total si el producto ya está en la comanda
                                String[] lineas = textoActual.split("\n");
                                for (String linea : lineas) {
                                    if (linea.contains(producto.getNombre())) {
                                        // Se obtiene la cantidad actual y se calcula la nueva cantidad y precio total
                                        String[] partes = linea.split(" - ");
                                        int cantidadActual = Integer.parseInt(partes[1].split(": ")[1]);
                                        int nuevaCantidad = cantidadActual + cantidadSeleccionada;
                                        double nuevoPrecioTotal = producto.getPrecio() * nuevaCantidad;
                                        // Construccion de la nueva línea con la cantidad y el precio total actualizados
                                        comandaTexto = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€",
                                                producto.getNombre(), nuevaCantidad, producto.getPrecio(), nuevoPrecioTotal);
                                        // Se reemplaza la línea actual con la nueva línea en el texto del TextArea
                                        textoActual = textoActual.replace(linea, comandaTexto);
                                        productoExistente = true;
                                        break;
                                    }
                                }
                            }
                            // Se agregar el producto a la comanda si no está presente
                            if (!productoExistente) {
                                comandaTexto = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€\n",
                                        producto.getNombre(), cantidadSeleccionada, producto.getPrecio(), precioTotal);
                                textAreaMesa.appendText(comandaTexto);
                            } else {
                                // Si el producto ya está presente, se reemplaza el texto completo del TextArea con el texto actualizado
                                textAreaMesa.setText(textoActual);
                            }
                            // Reecalculado de los totales después de agregar el nuevo producto
                            double totalSinIVA = calcularTotalComanda(textAreaMesa.getText());
                            double totalConIVA = calcularTotalConIVA(totalSinIVA);
                            TextField textFieldTotal = textFieldTotalMap.get(mesaSeleccionada.getNombre());
                            TextField textFieldTotalIVA = textFieldTotalIVAMap.get(mesaSeleccionada.getNombre());
                            textFieldTotal.setText(String.format("%.2f", totalSinIVA));
                            textFieldTotalIVA.setText(String.format("%.2f", totalConIVA));
                            // Actualizacion del estado de la mesa en la base de datos
                            if (mesaSeleccionada != null) {
                                String nombreMesa = mesaSeleccionada.getNombre();
                                int mesaNumber;
                                if (nombreMesa.startsWith("Mesa_")) {
                                    mesaNumber = Integer.parseInt(nombreMesa.substring("Mesa_".length()));
                                } else if (nombreMesa.startsWith("Barra_")) {
                                    mesaNumber = Integer.parseInt(nombreMesa.substring("Barra_".length()));
                                } else {
                                    return;
                                }
                                //  Llamando al metodo actualizarEstado, mediante un update en el metodo, se cambia el estado de la mesa en la tabla
                                actualizarEstadoMesaEnBD(mesaSeleccionada, "ocupado", numComensales);
                            }
                            // Se muestra el mensaje de éxito al usuario
                            mostrarAlerta("Producto añadido a la comanda.", Alert.AlertType.INFORMATION);
                            // Limpieza de la selección del producto y la cantidad después de enviar la comanda
                            listProductos.getSelectionModel().clearSelection();
                            cantidad.getValueFactory().setValue(0);
                        } else {
                            // Mensaje de error si no se encuentra el TextArea de la mesa seleccionada
                            mostrarAlerta("No se pudo encontrar el TextArea de la mesa seleccionada.", Alert.AlertType.ERROR);
                        }
                    } else {
                        // Mensaje de error si el producto seleccionado no existe
                        mostrarAlerta("El producto seleccionado no existe.", Alert.AlertType.INFORMATION);
                    }
                } else {
                    // Mensaje de validación fallida si falta algún dato requerido
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

    //  Meotodo auxiliar llamado desde el initialice, que se activa solo cuando se activa o pierde el foco en el textarea seleccionado por el usuario
    //  Esto permite actualizar a mano las cantidades o precios en el textarea o borrar datos del textarea si se han añadido por error al tomar la comanda al cliente
    private void actualizarComanda() {
        // Se recorre cada mesa y obtiene su TextArea correspondiente
        for (Map.Entry<String, TextArea> entry : listViewMap.entrySet()) {
            String nombreMesa = entry.getKey();
            TextArea textAreaMesa = entry.getValue();
            // Verificacion si el TextArea no es nulo
            if (textAreaMesa != null) {
                String textoActual = textAreaMesa.getText();
                double totalMesaSinIVA = 0.0;
                StringBuilder nuevoTexto = new StringBuilder();
                // Se procesa cada línea del texto de la comanda
                //  Para ello, cada linea a de tener un formato exacto y concreto, para poder calcular las cantidads y los precios correctamente
                String[] lineas = textoActual.split("\n");
                for (String linea : lineas) {
                    String[] partes = linea.split(" - ");
                    if (partes.length == 4) {
                        String nombreProducto = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].split(": ")[1].trim());
                        double precioUnitario = Double.parseDouble(partes[2].split(": ")[1].replace("€", "").replace(",", ".").trim());
                        double precioTotal = cantidad * precioUnitario;
                        totalMesaSinIVA += precioTotal;
                        // Se actualiza la línea con la nueva cantidad y precio total
                        String nuevaLinea = String.format("%s - Cantidad: %d - Precio unitario: %.2f€ - Precio total: %.2f€",
                                nombreProducto, cantidad, precioUnitario, precioTotal);
                        // Se agrega la línea al nuevo texto
                        nuevoTexto.append(nuevaLinea).append("\n");
                    } else {
                        // Si no es una línea válida, se agrega sin cambios
                        nuevoTexto.append(linea).append("\n");
                    }
                }
                // Se establece el nuevo texto en el TextArea
                textAreaMesa.setText(nuevoTexto.toString().trim()); // Elimina el último salto de línea
                // Calculo del total con IVA para la mesa y actualizar los campos correspondientes
                double totalMesaConIVA = calcularTotalConIVA(totalMesaSinIVA);
                TextField textFieldTotal = textFieldTotalMap.get(nombreMesa);
                TextField textFieldTotalIVA = textFieldTotalIVAMap.get(nombreMesa);
                textFieldTotal.setText(String.format("%.2f", totalMesaSinIVA));
                textFieldTotalIVA.setText(String.format("%.2f", totalMesaConIVA));
            }
        }
    }

    // Con este metodo estableciendo una conexion  a la bbdd y mediante el select, se obtiene el estado actual del campo en la columna de la tabla,
    // por un lado se inicializa al cargar la escena, para darle un funcionamiento inicial y mostrar el estado de las mesas
    // por otro lado, lo que se ha realizado ha sido llamarlo desde el Thread o hilo para que de forma peridica vaya actualizando en la interfaz el estado de las mesas
    private void actualizarEstadosDesdeBD() {
        listaMesas.clear(); // Limpieza de la lista antes de actualizarla
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String sql = "SELECT id, nombre, estado FROM mesas"; // Selecciona también el número de mesa
                try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre"); // Obtener el número de mesa
                        String estado = resultSet.getString("estado");
                        // Creacion de una nueva instancia de Mesas
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

    // Dentro del hilo, y haciendo llamada a los estilos css indicados en la escena del SecenBuilder, se ajustan los datos que en una serie de textfield,
    //  a modo de muestra por interfaz y mas intuitivo, para saber que mesas estan ocupadas o libre
    private void actualizarTextFieldsWithMesas() {
        // Aseguramos que los cambios en la interfaz de usuario se realicen en el hilo de la aplicación JavaFX.
        Platform.runLater(() -> {
            // Se itera sobre cada mesa en la lista
            for (Mesas mesa : listaMesas) {
                String estadoActual = mesa.getEstado();
                String estadoAntiguo = estadoAnterior.getOrDefault(mesa, "");
                // Verificacion de si el estado ha cambiado
                if (!estadoActual.equals(estadoAntiguo)) {
                    String nombreMesa = mesa.getNombre();
                    String fxId = nombreMesa.replaceAll("[^a-zA-Z0-9]", "");
                    TextField textField = (TextField) mesas.lookup("#" + fxId);
                    if (textField != null) {
                        // Se actualiza el TextField con el estado de la mesa
                        textField.setText(estadoActual);
                        // Limpieza de clases de estilo previas y aplicar nuevo estilo según el estado de la mesa
                        textField.getStyleClass().clear();
                        if (estadoActual.equals("libre")) {
                            textField.getStyleClass().add("text-field-libre"); // Se agrega la clase de estilo para libre
                        } else if (estadoActual.equals("ocupado")) {
                            textField.getStyleClass().add("text-field-ocupado"); // Se agrega la clase de estilo para ocupado
                        }
                        // Actualizacion del estado anterior de la mesa
                        estadoAnterior.put(mesa, estadoActual);
                    }
                }
            }
        });
    }

    //  Metodo para la creacion de un hilo paralelo, que permitira actualizar en tiempo real el estado de las mesas y verificar si estan libres u ocupadas
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
                        actualizarEstadosDesdeBD();
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

    //  Metodo para cargar los nombres de las mesas en el combox indicado
    private void cargarMesasEnComboBox() {
        // Obtener la conexión a la base de datos
        try (Connection connection = getConnection()) {
            if (connection != null) {
                // Se limpia el ComboBox
                seleccionMesa.getItems().clear();
                // Consulta SQL para obtener las mesas desde la base de datos, ordenadas alfabéticamente
                String sql = "SELECT nombre FROM mesas ORDER BY nombre";
                try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Se obtiene el nombre de la mesa de la fila actual
                        String nombre = resultSet.getString("nombre");
                        // Se agrega el nombre de la mesa al ComboBox
                        seleccionMesa.getItems().add(new Mesas(nombre));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Obtencion de todas las categorias en base al id, para obtener su nombre y ordenandolo por id
    private List<Categorias> obtenerCategoriasDesdeBD(Connection connection) {
        List<Categorias> categorias = new ArrayList<>();
        try (PreparedStatement consulta = connection.prepareStatement("SELECT * FROM categorias ORDER BY id"); ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                // Se crea la instancia de Categorias con los valores recuperados de la base de datos
                Categorias categoria = new Categorias(id, nombre);
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return categorias;
    }

    //  Metodo ultimo, que permitira cargar los datos de las categorias en el combox, este es iniciaalizado desde el initialice del Controller
    private void cargarCategorias() throws SQLException {
        try (Connection connection = getConnection()) {
            List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
            seleccionCategoria.getItems().addAll(categorias);
        }
    }

    //  Metodo auxiliar, que permite obtener todos los productos relacionados por el id de la categoria asociadas, de tal forma que al obtener estos datos,
    // y utilizando otro metodo, se cargaran los datos en un listview
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
                            // Creacion de una instancia de Producto y agregarla a la lista
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

    //  Este metodo en conjunto con el anterior mostrado, se podran mostrar los datos de los productos en el listview con misma categoria asociada
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
                    //  Formato en el que saldran los datos por pantalla en el listview
                    setText(String.format("%s - %s - Precio: %.2f€ - %s", producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getTipo_porcion()));
                    // Se agrega el producto al mapa
                    productosMap.put(producto.getId(), producto);
                }
            }
        });
    }

    //  Metodo para activar el tab de mesas, este en base a la seleccion de una mesa en el combox donde se muestra, permite abrir de forma automatica el tab
    //  correspondiente a la mesa con la que se quiere trabajar
    private void activarTab(String nombreMesa) {
        // Itera sobre los tabs en el TabPane
        for (Tab tab : tabPaneMesas.getTabs()) {
            // Se verifica si el texto del tab coincide exactamente con el nombre de la mesa seleccionada
            if (tab.getId().equalsIgnoreCase(nombreMesa)) {
                // Selecciona el tab correspondiente
                tabPaneMesas.getSelectionModel().select(tab);
                return; // Se sale del bucle una vez encontrado el tab correspondiente
            }
        }
        for (Tab tab : tabPaneMesas.getTabs()) {
            if (tab.getText().startsWith("Barra")) {
                // Selecciona el tab correspondiente
                tabPaneMesas.getSelectionModel().select(tab);
                return; // Se sale del bucle una vez encontrado el tab correspondiente
            }
        }
    }

    //  Metodo auxiliar llamado desde otros metodos, que sirve para actualizar el estado de las mesas con el UPDATE, este sirve para actualizar 
    //  el estado a libre u ocupado y añadirle los comensales que se han unido a la mesa.
    private void actualizarEstadoMesaEnBD(Mesas mesa, String estado, int numComensales) {
        try (Connection connection = getConnection()) {
            // Se prepara la consulta SQL para actualizar el estado de la mesa y el número de comensales
            String sql = "UPDATE mesas SET estado = ?, comensales = ? WHERE nombre = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, estado);
                statement.setInt(2, numComensales);
                statement.setString(3, mesa.getNombre());
                // Ejecucion de la consulta
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Dos metodos para realizar el calculo del total del precio acumulada, con IVA y sin IVA, iterando sobre las lineas de texto en los textarea,
    //  siguiendo el formato de estos, se localizan los totales o acumulado
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

    //  Metodo activado desde la escena, que sirve para confirmar los pagos de las comandas, en este caso, se revisa todo el texto de los textarea, los textfield
    //  con los IVAs, los comensales, la mesa, etc... Y se registra en la tabla de la bbdd de TicketComanda, para registrar en cada una de sus columnas los datos de interes
    @FXML
    private void confirmarPago(ActionEvent event) {
        try {
            // Se muestra una barra de progreso, que simularia el proceso de carga del Ticket, por defecto esta aparece como false, para que no se vea, hasta que se le de a este boton
            progreso.setVisible(true);
            //  Inicializacion del boton, para confirmar los pagos
            Button botonConfirmacion = (Button) event.getSource();
            // Se obtiene el nombre de la mesa asociada al botón desde el hashmap creado con todos los botones en el initialice del Controller
            String nombreMesa = nombreMesaMap.get(botonConfirmacion);
            // Se obtiene  el objeto Mesas correspondiente al nombre de la mesa
            Mesas mesaSeleccionada = obtenerMesaPorNombre(nombreMesa);
            //  Inicializacion del spinner para asociar el numero de comensales, haciendo llamada al hashmap que almacena todos los spinner
            Spinner<Integer> spinnerComensales = comensalesMap.get(mesaSeleccionada.getNombre());
            if (mesaSeleccionada != null) {
                // Se obtiene el botón correspondiente al nombre de la mesa seleccionada
                obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
                // Se obtiene el TextArea correspondiente a la mesa seleccionada
                TextArea textAreaMesa = listViewMap.get(mesaSeleccionada.getNombre());
                if (textAreaMesa != null) {
                    // Se obtiene el texto actual del TextArea
                    String textoComanda = textAreaMesa.getText();
                    // Se obtienen los detalles de la comanda desde el texto del TextArea
                    List<DetallesComanda> detallesComanda = obtenerDetallesComandaDesdeTexto(textoComanda, mesaSeleccionada);
                    //  Se llaman a ambos metodos para calcular los totales, de tal forma que se puedan guardar en bbdd y asi mostrarlo en el Ticket
                    double totalSinIVA = calcularTotalComanda(textoComanda);
                    double totalConIVA = calcularTotalConIVA(totalSinIVA);
                    // Se obtiene el número de comensales
                    int numComensales = spinnerComensales.getValue();
                    //  Mediante la creacion de una instancia de TicketComanda, y con todos los valores de las columnas de la tabla inicializadas anteriormente
                    //  se introducen los datos en la tabla de TicketComanda (detallesComanda) hace referencia a una columna bytea que donde se almacenaria 
                    //  el conjunto de datos del textarea
                    final TicketComanda ticket = generarTicket(mesaSeleccionada, detallesComanda, totalSinIVA, totalConIVA, numComensales, textoComanda);
                    guardarTicketEnBD(ticket);
                    obtenerBotonPorNombreMesa(mesaSeleccionada.getNombre());
                    // Se insertan los detalles de la comanda en la base de datos
                    for (DetallesComanda detalle : detallesComanda) {
                        // Aquí se utiliza el botón obtenido para obtener el ID de la mesa e insertar los datos en la tabla DetallesComanda, para saber
                        //  todas las comandas que se han registrado y se han servido, producto a producto con sus cantidades, haciendo llamada al metodo con el INSERT
                        insertarDetallesComandaEnBD(mesaSeleccionada, detalle.getProducto_id(), detalle.getCantidad());
                    }
                    // Se cambia el estado de la mesa a "libre" y el número de comensales a 0
                    actualizarEstadoMesaEnBD(mesaSeleccionada, "libre", 0);
                    //  Se limpia el textarea, para poder añadir mas datos a continuacion
                    textAreaMesa.clear();
                    //  Llamada a ambos hasmap, para localizar ambos textfield con los totales, y acontinuacion poder limpiarlos
                    TextField textFieldTotal = textFieldTotalMap.get(mesaSeleccionada.getNombre());
                    TextField textFieldTotalIVA = textFieldTotalIVAMap.get(mesaSeleccionada.getNombre());
                    //  Se limpian los textfield de los totales y se establece el spinner a 0
                    textFieldTotal.clear();
                    textFieldTotalIVA.clear();
                    spinnerComensales.getValueFactory().setValue(0);
                    // Se muestra el ticket en un popup
                    mostrarAlerta("Pago confirmado. Comanda cerrada.\nCargando ticket...", Alert.AlertType.INFORMATION);
                    // Inicializacion del proceso de búsqueda del número de ticket
                    buscarNumeroTicket(event, ticket);
                } else {
                    mostrarAlerta("No se pudo encontrar el TextArea de la mesa seleccionada.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Debe seleccionar una mesa para confirmar el pago.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // Se muestra el mensaje de error al usuario si ocurre una excepción
            e.printStackTrace();
            mostrarAlerta("Se produjo un error al confirmar el pago.", Alert.AlertType.ERROR);
        }
    }

    //  El numTicket se genera de forma automatica en bbdd, esto se realiza con una funcion inicializada con trigger y se genera antes de la creacion del propio ticket,
    //  de esta forma el ticket se crea de forma automatica, para tenerle un seguimiento, como el ticket se genera antes, con este metodo se localiza en la bbdd, y se le asocia
    // al ticket, para que se pueda mostrar por escrito en el ticket, para disponer de el a la hora de la impresion
    // Al mismo tiempo, este metodo da funcionamiento a la barra de progreso, para que este se muetre y actualice
    private void buscarNumeroTicket(ActionEvent event, TicketComanda ticket) {
        // Se muestra la barra de progreso, igual que en el metodo anterior
        progreso.setVisible(true);
        // Tarea en segundo plano para simular el progreso y obtener el número de ticket
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int segundosEspera = 2;
                int totalIteraciones = segundosEspera * 10; // 10 actualizaciones por segundo
                for (int i = 0; i < totalIteraciones; i++) {
                    // Se calcula el progreso actual
                    double progresoActual = (double) i / totalIteraciones;
                    // Se actualiza la barra de progreso en la interfaz de usuario
                    Platform.runLater(() -> progreso.setProgress(progresoActual));
                    // 100 milisegundos de espera (para simular una décima de segundo)
                    Thread.sleep(100);
                }
                // Una vez completado el progreso, se busca el número de ticket
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
        // Ocultar la barra de progreso cuando la tarea ha terminado
        task.setOnSucceeded(e -> {
            progreso.setVisible(false);
        });
        // Se inicia la tarea en un nuevo hilo
        new Thread(task).start();
    }

    // Método que muestra el contenido del ticket en un popup
    private void mostrarContenidoPopup(TicketComanda ticket) {
        try {
            // Se convierte el arreglo de bytes a un String
            String contenidoTexto = new String(ticket.getArchivo_ticket(), StandardCharsets.UTF_8);
            // Se muestra el contenido del ticket en un popup
            contenidoTexto += "\n\nNúmero de ticket: " + ticket.getNum_ticket();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contenido del ticket - " + ticket.getNum_ticket());
            alert.setHeaderText("Contenido del ticket");
            alert.setContentText(contenidoTexto);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método que obtiene el número de ticket generado más reciente de la base de datos, y de esta forma poder asociarlo al ticket para mostrarlo por pantalla
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

    //  Metodo para asociar los botones existentes y presentes en el hashmap, a las Mesas existentes
    private Button obtenerBotonPorNombreMesa(String nombreMesa) {
        // Se itera sobre el HashMap para encontrar el botón correspondiente al nombre de la mesa
        for (Map.Entry<Button, String> entry : nombreMesaMap.entrySet()) {
            if (entry.getValue().equals(nombreMesa)) {
                return entry.getKey(); // Se devuelve el botón correspondiente al nombre de la mesa
            }
        }
        return null; // Retornar null si no se encuentra el botón
    }

    //  Metodo para localizar la mesa en base al nombre
    private Mesas obtenerMesaPorNombre(String nombreMesa) {
        // Se itera sobre la lista de mesas
        for (Mesas mesa : listaMesas) {
            String nombreBoton = mesa.getNombre(); // Se obtiene el nombre de la mesa
            if (nombreMesa.equals(nombreBoton)) {
                return mesa; // Retorna la mesa correspondiente al nombre
            }
        }
        // Si no se encuentra la mesa, retorna null
        return null;
    }

    //  Metodo List, para localizar el detalle de comanda, es decir lo escrito dentro del textarea diviendo el contenido en un formato concreto,
    //  para localizar todo el contenido
    private List<DetallesComanda> obtenerDetallesComandaDesdeTexto(String textoComanda, Mesas mesaSeleccionada) {
        List<DetallesComanda> detallesComanda = new ArrayList<>();
        String[] lineas = textoComanda.split("\n");
        //  Division linea a linea especificando el formato deseado
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

    // Metodo que contiene el metodo INSERT para introducir linea a linea los prodtuctos del textarea, haciendo asi seguimiento a todos los productos que se venden 
    private void insertarDetallesComandaEnBD(Mesas mesaSeleccionada, Productos producto, int cantidad) {
        try (Connection connection = getConnection()) {
            // Se obtiene el nombre de la mesa desde el objeto mesa seleccionada
            String nombreMesa = mesaSeleccionada.getNombre();
            Button botonMesa = nombreMesaMap.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(nombreMesa))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            if (botonMesa != null) {
                // Se inserta el producto en la base de datos, en la tabla de detalles_comanda
                String sql = "INSERT INTO detalles_comanda (nombre_mesa, producto_id, cantidad, usuario_id) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmtInsert = connection.prepareStatement(sql)) {
                    pstmtInsert.setString(1, mesaSeleccionada.getNombre()); // Aquí se utiliza el ID de la mesa
                    pstmtInsert.setInt(2, producto.getId()); // Aquí se utiliza el ID del producto
                    pstmtInsert.setInt(3, cantidad);
                    pstmtInsert.setInt(4, usuario.getId()); // Aquí se utiliza el ID del usuario
                    pstmtInsert.executeUpdate();
                }
            } else {
                // Se muestra el mensaje de error si no se encontró el botón
                mostrarAlerta("No se encontró el botón asociado al nombre de la mesa.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Obtencion del id de producto en base al nombre guardado en el textarea
    private Productos obtenerProductoPorNombre(String nombreProducto) {
        try (Connection connection = getConnection()) {
            // Se prepara la consulta SQL para buscar el producto por su nombre
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

    // Metodo auxliar para obtener el usuario logeado, con este metodo podremos asocair el usuario logeado al ticket impreso, para que se vea el 
    //  camarero que realizo el trabajo, que sirvio la mesa, que confirmo el pago
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

    //  Generacion del ticket obteniendo los datos desde el metodo de boton de confirmarPago
    private TicketComanda generarTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales, String textoComanda) {
        // Generacion del contenido del ticket
        String contenidoTicket = construirContenidoTicket(mesa, detallesComanda, totalSinIVA, totalConIVA, numComensales, textoComanda);
        // Se crea una instancia de TicketComanda
        TicketComanda ticket = new TicketComanda();
        ticket.setNombre_mesa(mesa);
        ticket.setNum_ticket(numeroTicketGenerado); // Se usa el número de ticket generado previamente
        ticket.setArchivo_ticket(contenidoTicket.getBytes());
        ticket.setImporte_total_sin_IVA((double) totalSinIVA);
        ticket.setImporte_total_con_IVA((double) totalConIVA);
        ticket.setNum_comensales(numComensales);
        ticket.setUsuario_id(usuario); // Se utiliza setUsuario_id para establecer el usuario
        ticket.setFecha_pedido(new Date());

        return ticket;
    }

    //  Metodo donde se establece el INSERT para guardar todo en la tabla bbdd de ticket_comanda
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

    // Metodo auxiliar para centrar el texto, estableciendo espaciados por defecto y asi darle formato al ticket impreso
    private String centrarTexto(String texto, int longitudTotal) {
        int longitudTexto = texto.length(); // Longitud del texto original
        if (longitudTexto >= longitudTotal) {
            return texto; // Si el texto es mayor o igual a la longitud total, se devuelve el texto original
        } else {
            int espaciosPorAgregar = longitudTotal - longitudTexto; // Se calcula el número total de espacios a agregar
            int espaciosAntes = espaciosPorAgregar / 2; // Se calculan los espacios a agregar antes del texto
            int espaciosDespues = espaciosPorAgregar - espaciosAntes; // Se calculan los espacios a agregar después del texto
            StringBuilder textoCentrado = new StringBuilder();
            for (int i = 0; i < espaciosAntes; i++) {
                textoCentrado.append(" "); // Se agregan los espacios antes del texto
            }
            textoCentrado.append(texto); // Se agregan el texto original
            for (int i = 0; i < espaciosDespues; i++) {
                textoCentrado.append(" "); // Se agregan los espacios después del texto
            }
            return textoCentrado.toString(); // Se devuelve el texto centrado
        }
    }

    //  Metodo para dar formato al ticket que se mostrara por pantalla o bien sacar impreso si asi se desease por parte del usuario,
    //  este seria el formato que saldria por una impresora termica, actualmente unicamente se guarda en bbdd como un archivo bytea
    private String construirContenidoTicket(Mesas mesa, List<DetallesComanda> detallesComanda, double totalSinIVA, double totalConIVA, int numComensales, String textoComanda) {
        //  Con StringBuilder se inicializa y crea el formato del ticket, para concatenar contenido y que sea legible de forma ordenada para su lectura
        StringBuilder contenidoTicket = new StringBuilder();
        //  Inicializacion de variables, para despues llamarlas desde el metodo centrarTexto y asi dar un espaciado determinado
        int longitudTotal = 80;
        int longitudTotalFactura = 50;
        int longitudTotalNIF = 75;
        int longitudTotalTele = 70;
        //  Inicializacion de la variable nombreUsuario, llamando al metodo para obtener el usuario que se ha loegado en la app y asi mostrarlo en el ticket
        String nombreUsuario = obtenerNombreUsuarioPorId(usuario.getId());
        // Cabecera del ticket
        contenidoTicket.append(centrarTexto("Bar ElEscobar", longitudTotal)).append("\n\n");
        contenidoTicket.append(centrarTexto("NIF: 12345678X", longitudTotalNIF)).append("\n");
        contenidoTicket.append(centrarTexto("623191754 | 683572682", longitudTotalTele)).append("\n\n\n");
        // Detalles de la mesa y fecha
        contenidoTicket.append(mesa.getNombre()).append("\n");
        contenidoTicket.append(new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date())).append("\n\n");
        // Aviso de factura no válida
        contenidoTicket.append(centrarTexto("***NO VALIDO COMO FACTURA***", longitudTotalFactura)).append("\n");
        contenidoTicket.append("Comandas:\n");
        // Encabezado de las columnas
        contenidoTicket.append(String.format("%-5s%-35s%-15s%-10s\n", "Cant.", "Producto", "Precio", "Total"));
        contenidoTicket.append("-----------------------------------------\n");
        // Detalles de la comanda
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
                }
                // Se añaden tabuladores para separar las columnas en un formato especifico
                contenidoTicket.append(String.format("%-5s\t%-30s\t%-15s\t%-10s\n", cantidad, nombreProducto, String.format("%.2f€", precio), String.format("%.2f€", totalLinea)));
            }
        }
        contenidoTicket.append("-----------------------------------------\n");
        // Información adicional
        contenidoTicket.append("Número de comensales: ").append(numComensales).append("\n\n");
        contenidoTicket.append("IVA: 10% \t\t\t");
        double importeIVA = totalConIVA - totalSinIVA;
        contenidoTicket.append("Cuota: ").append(String.format("%.2f", importeIVA)).append("€\n");
        contenidoTicket.append("Base: ").append(String.format("%.2f", totalSinIVA)).append("€ \t\t");
        contenidoTicket.append("Base + Cuota: ").append(String.format("%.2f", totalConIVA)).append("€\n\n");
        // Total a pagar y agradecimiento
        contenidoTicket.append("Total (Impuestos Incl.): ").append(String.format("%.2f", totalConIVA)).append("€\n");
        contenidoTicket.append("-----------------------------------------\n");
        contenidoTicket.append("Le atendió ").append(nombreUsuario).append("\n");
        contenidoTicket.append("Gracias por su visita/Thanks for your visit").append("\n");
        //  Se recupera todo el contenido con el formato establecido
        return contenidoTicket.toString();
    }

    //  Metodo necesario para dar formato a las aletar poup que van saliendole al usuario cada vez que realiza una operacion
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    //  Metodo para obtener el usuario pasado por el controlador, para obtener el usuario logeado y asi agregarlo a las insercciones de datos.
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
