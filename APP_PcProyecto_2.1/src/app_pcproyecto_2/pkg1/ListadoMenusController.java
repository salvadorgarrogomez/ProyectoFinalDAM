package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Categorias;
import constructores.Productos;
import constructores.Usuarios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ListadoMenusController implements Initializable {

    private Usuarios usuario;

    @FXML
    private ComboBox<Categorias> selecionInsert;

    @FXML
    private ComboBox<Categorias> seleccionCategoriaActua;

    @FXML
    private TextField platoNuevo;
    @FXML
    private TextArea descriNuevo;
    @FXML
    private TextField precioNuevo;
    @FXML
    private TextField tipoNuevo;
    @FXML
    private TextField porcionNuevo;
    @FXML
    private CheckBox vegeNuevo;
    @FXML
    private CheckBox vegaNuevo;
    @FXML
    private CheckBox glutenNuevo;
    @FXML
    private CheckBox lactosaNuevo;
    @FXML
    private CheckBox picaNuevo;
    @FXML
    private TextField categoriaActua;
    @FXML
    private TextField nombreActua;
    @FXML
    private TextArea descriActua;
    @FXML
    private TextField precioActua;
    @FXML
    private TextField tipoActua;
    @FXML
    private TextField porcionActua;
    @FXML
    private CheckBox vegeActua;
    @FXML
    private CheckBox vegaActua;
    @FXML
    private CheckBox glutenActua;
    @FXML
    private CheckBox lactosaActua;
    @FXML
    private CheckBox picanteActua;
    @FXML
    private Button limpiar;

    @FXML
    private ComboBox<Productos> selecctionProductoActua;

    private ObservableList<Productos> productosObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try (Connection connection = getConnection()) {
            // Obtener la lista de todas las categorías utilizando la conexión a la base de datos
            List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
            cargarCategorias();
            configurarListeners();
            // Crear una lista de nombres de categorías
            List<String> nombresCategorias = new ArrayList<>();
            for (Categorias categoria : categorias) {
                nombresCategorias.add(categoria.getNombre());
            }

            // Agregar los nombres de categorías al ComboBox
            selecionInsert.getItems().addAll(categorias);
            seleccionCategoriaActua.getItems().addAll(categorias);

            // Configurar el listener para el ComboBox de categorías
            seleccionCategoriaActua.setOnAction(event -> {
                if (seleccionCategoriaActua.getValue() != null) {
                    categoriaActua.setText(seleccionCategoriaActua.getValue().getNombre());
                } else {
                    categoriaActua.clear(); // Limpiar el TextField si no hay ninguna categoría seleccionada
                }

                // Llamar al método para cargar los productos asociados a la categoría seleccionada
                cargarProductosPorCategoria(seleccionCategoriaActua.getValue());
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    @FXML
    private void confirmarNuevoPlato(ActionEvent event) {
        try {
            int usuarioId = usuario.getId();
            // Obtener valores ingresados por el usuario desde la interfaz gráfica
            String nombre = platoNuevo.getText();
            String description = descriNuevo.getText();
            double precio = Double.parseDouble(precioNuevo.getText());
            String tipoPlato = tipoNuevo.getText();
            String tipoPorcion = porcionNuevo.getText();
            boolean esVegetariano = vegeNuevo.isSelected();
            boolean esVegano = vegaNuevo.isSelected();
            boolean esSinGluten = glutenNuevo.isSelected();
            boolean esSinLactosa = lactosaNuevo.isSelected();
            boolean esPicante = picaNuevo.isSelected();
            // Obtener el nombre de la categoría seleccionada

            // Obtener el objeto Categorias correspondiente al nombre seleccionado
            Categorias categoriaSeleccionada = selecionInsert.getValue();

            // Crear una instancia de Productos con los datos ingresados por el usuario
            Productos nuevoProducto = new Productos();
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setDescripcion(description);
            nuevoProducto.setPrecio(precio);
            nuevoProducto.setCategoria_id(categoriaSeleccionada); // Establecer la categoría seleccionada
            nuevoProducto.setTipo_plato(tipoPlato);
            nuevoProducto.setTipo_porcion(tipoPorcion);
            nuevoProducto.setEs_vegetariano(esVegetariano);
            nuevoProducto.setEs_vegano(esVegano);
            nuevoProducto.setEs_sin_gluten(esSinGluten);
            nuevoProducto.setEs_sin_lactosa(esSinLactosa);
            nuevoProducto.setEs_picante(esPicante);

            // Insertar el nuevo producto en la base de datos
            boolean exito = insertarProductoEnBD(nuevoProducto, usuarioId);
            if (exito) {
                // Mostrar mensaje de éxito al usuario
                mostrarAlerta("Éxito", "Producto insertado correctamente", AlertType.INFORMATION);
                // Limpiar los campos después de la inserción
                limpiarCampos();
            } else {
                // Mostrar mensaje de error al usuario
                mostrarAlerta("Error", "No se pudo insertar el producto", AlertType.ERROR);
            }
        } catch (Exception e) {
            // Mostrar mensaje de error al usuario si ocurre una excepción
            mostrarAlerta("Error", "Ha ocurrido un error al procesar la solicitud" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        platoNuevo.clear();
        descriNuevo.clear();
        precioNuevo.clear();
        tipoNuevo.clear();
        porcionNuevo.clear();
        vegeNuevo.setSelected(false);
        vegaNuevo.setSelected(false);
        glutenNuevo.setSelected(false);
        lactosaNuevo.setSelected(false);
        picaNuevo.setSelected(false);
        selecionInsert.getSelectionModel().clearSelection();
        selecionInsert.setPromptText("< Selecciona un elemento >");
    }

    public static boolean insertarProductoEnBD(Productos producto, int usuarioId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = "INSERT INTO productos (nombre, description, precio, categoria_id, tipo_plato, tipo_porcion, es_vegetariano, es_vegano, es_sin_gluten, es_sin_lactosa, es_picante, usuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getCategoria_id().getId());
            statement.setString(5, producto.getTipo_plato());
            statement.setString(6, producto.getTipo_porcion());
            statement.setBoolean(7, producto.isEs_vegetariano());
            statement.setBoolean(8, producto.isEs_vegano());
            statement.setBoolean(9, producto.isEs_sin_gluten());
            statement.setBoolean(10, producto.isEs_sin_lactosa());
            statement.setBoolean(11, producto.isEs_picante());
            statement.setInt(12, usuarioId); // Establecer el usuarioId en la sentencia SQL
            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @FXML
    private void handleTabulation(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            event.consume(); // Evita que el evento se propague más allá del control actual
        }
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    // Método para cargar los productos por categoría
// Método para cargar los productos por categoría
    private void cargarProductosPorCategoria(Categorias categoriaSeleccionada) {
        if (categoriaSeleccionada != null) {
            // Limpiar la lista de productos
            productosObservableList.clear();
            // Obtener los productos asociados a la categoría seleccionada
            List<Productos> productos = obtenerProductosPorCategoria(categoriaSeleccionada);
            // Agregar los productos a la lista observable
            productosObservableList.addAll(productos);
            // Establecer la lista observable como elementos del ComboBox de productos
            selecctionProductoActua.setItems(productosObservableList);
        }
    }

    // Método para obtener los productos asociados a una categoría
    private List<Productos> obtenerProductosPorCategoria(Categorias categoria) {
        List<Productos> productos = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM productos WHERE categoria_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, categoria.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Crear instancia de Productos con los datos obtenidos de la base de datos
                        Productos producto = new Productos();
                        producto.setId(resultSet.getInt("id"));
                        producto.setNombre(resultSet.getString("nombre"));
                        producto.setDescripcion(resultSet.getString("description"));
                        producto.setPrecio(resultSet.getDouble("precio"));
                        producto.setTipo_plato(resultSet.getString("tipo_plato"));
                        producto.setTipo_porcion(resultSet.getString("tipo_porcion"));
                        producto.setEs_vegetariano(resultSet.getBoolean("es_vegetariano"));
                        producto.setEs_vegano(resultSet.getBoolean("es_vegano"));
                        producto.setEs_sin_gluten(resultSet.getBoolean("es_sin_gluten"));
                        producto.setEs_sin_lactosa(resultSet.getBoolean("es_sin_lactosa"));
                        producto.setEs_picante(resultSet.getBoolean("es_picante"));

                        productos.add(producto);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productos;
    }

    @FXML
    private void confirmarActua(ActionEvent event) {
        try {
            int usuarioId = usuario.getId();
            // Obtener el producto seleccionado
            Productos productoSeleccionado = selecctionProductoActua.getValue();

            // Verificar que el producto no sea nulo
            if (productoSeleccionado != null) {
                // Obtener la categoría seleccionada
                Categorias categoriaSeleccionada = seleccionCategoriaActua.getValue();

                // Verificar si la categoría seleccionada no es nula
                if (categoriaSeleccionada != null) {
                    // Obtener el objeto Categorias correspondiente al nombre seleccionado
                    Integer categoria = obtenerIdCategoriaPorNombre(categoriaSeleccionada.getNombre());

                    // Verificar si la categoría se encontró en la base de datos
                    if (categoria != null) {
                        // Obtener los valores ingresados por el usuario desde la interfaz gráfica
                        String nombre = nombreActua.getText();
                        String description = descriActua.getText();
                        double precio = Double.parseDouble(precioActua.getText());
                        String tipoPlato = tipoActua.getText();
                        String tipoPorcion = porcionActua.getText();
                        boolean esVegetariano = vegeActua.isSelected();
                        boolean esVegano = vegaActua.isSelected();
                        boolean esSinGluten = glutenActua.isSelected();
                        boolean esSinLactosa = lactosaActua.isSelected();
                        boolean esPicante = picanteActua.isSelected();

                        // Actualizar los valores del producto seleccionado
                        productoSeleccionado.setNombre(nombre);
                        productoSeleccionado.setDescripcion(description);
                        productoSeleccionado.setPrecio(precio);
                        productoSeleccionado.setTipo_plato(tipoPlato);
                        productoSeleccionado.setTipo_porcion(tipoPorcion);
                        productoSeleccionado.setEs_vegetariano(esVegetariano);
                        productoSeleccionado.setEs_vegano(esVegano);
                        productoSeleccionado.setEs_sin_gluten(esSinGluten);
                        productoSeleccionado.setEs_sin_lactosa(esSinLactosa);
                        productoSeleccionado.setEs_picante(esPicante);

                        // Llamar al método para actualizar el producto en la base de datos
                        boolean exito = actualizarProductoEnBD(productoSeleccionado, usuarioId);
                        limpiarCamposProducto();
                        if (exito) {
                            // Mostrar mensaje de éxito al usuario
                            mostrarAlerta("Éxito", "Producto actualizado correctamente", AlertType.INFORMATION);
                        } else {
                            // Mostrar mensaje de error al usuario
                            mostrarAlerta("Error", "No se pudo actualizar el producto", AlertType.ERROR);
                        }
                    } else {
                        // Mostrar mensaje de error si la categoría no se encontró en la base de datos
                        mostrarAlerta("Error", "La categoría seleccionada no existe", AlertType.ERROR);
                    }
                } else {
                    // Si la categoría seleccionada es nula, mostrar mensaje de error
                    mostrarAlerta("Error", "No se ha seleccionado ninguna categoría", AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            // Mostrar mensaje de error al usuario si ocurre una excepción
            mostrarAlerta("Error", "Ha ocurrido un error al procesar la solicitud" + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private int obtenerIdCategoriaPorNombre(String nombreCategoria) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int categoriaId = -1; // Valor predeterminado en caso de que no se encuentre la categoría
        try {
            connection = getConnection();
            String sql = "SELECT id FROM categorias WHERE nombre = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombreCategoria);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                categoriaId = resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return categoriaId;
    }

    private boolean actualizarProductoEnBD(Productos producto, int usuarioId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();

            // Obtener el nombre de la categoría desde el campo de texto
            String nombreCategoria = categoriaActua.getText();

            // Verificar si el nombre de la categoría existe en la base de datos
            int categoriaId = obtenerIdCategoriaPorNombre(nombreCategoria);
            if (categoriaId != -1) {
                // Obtener la fecha y hora actual
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);

                String sql = "UPDATE productos SET nombre = ?, description = ?, precio = ?, categoria_id = ?, tipo_plato = ?, tipo_porcion = ?, es_vegetariano = ?, es_vegano = ?, es_sin_gluten = ?, es_sin_lactosa = ?, es_picante = ?, usuario_id = ?, fecha_hora = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setDouble(3, producto.getPrecio());
                statement.setInt(4, categoriaId); // Usar el ID de la categoría
                statement.setString(5, producto.getTipo_plato());
                statement.setString(6, producto.getTipo_porcion());
                statement.setBoolean(7, producto.isEs_vegetariano());
                statement.setBoolean(8, producto.isEs_vegano());
                statement.setBoolean(9, producto.isEs_sin_gluten());
                statement.setBoolean(10, producto.isEs_sin_lactosa());
                statement.setBoolean(11, producto.isEs_picante());
                statement.setInt(12, usuarioId); // ID del usuario que realiza la actualización
                statement.setTimestamp(13, (java.sql.Timestamp) timestamp); // Fecha y hora actual
                statement.setInt(14, producto.getId()); // ID del producto a actualizar
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            } else {
                // Si el nombre de la categoría no existe, mostrar un mensaje de error
                mostrarAlerta("Error","El nombre de la categoría no existe en la base de datos.", AlertType.ERROR);
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void cargarCategorias() throws SQLException {
        try (Connection connection = getConnection()) {
            List<Categorias> categorias = obtenerCategoriasDesdeBD(connection);
            selecionInsert.getItems().addAll(categorias);
            seleccionCategoriaActua.getItems().addAll(categorias);
        }
    }

    private void configurarListeners() {
        seleccionCategoriaActua.setOnAction(event -> {
            cargarProductosPorCategoria(seleccionCategoriaActua.getValue());
        });

        selecctionProductoActua.setOnAction(event -> {
            actualizarCamposProducto();
        });
    }

    private void actualizarCamposProducto() {
        Productos productoSeleccionado = selecctionProductoActua.getValue();

        if (productoSeleccionado != null) {
            nombreActua.setText(productoSeleccionado.getNombre());
            descriActua.setText(productoSeleccionado.getDescripcion());
            precioActua.setText(String.valueOf(productoSeleccionado.getPrecio()));
            tipoActua.setText(productoSeleccionado.getTipo_plato());
            porcionActua.setText(productoSeleccionado.getTipo_porcion());
            vegeActua.setSelected(productoSeleccionado.isEs_vegetariano());
            vegaActua.setSelected(productoSeleccionado.isEs_vegano());
            glutenActua.setSelected(productoSeleccionado.isEs_sin_gluten());
            lactosaActua.setSelected(productoSeleccionado.isEs_sin_lactosa());
            picanteActua.setSelected(productoSeleccionado.isEs_picante());
        } else {
            limpiarCamposProducto();
        }
    }

    private void limpiarCamposProducto() {
        nombreActua.clear();
        descriActua.clear();
        precioActua.clear();
        tipoActua.clear();
        porcionActua.clear();
        vegeActua.setSelected(false);
        vegaActua.setSelected(false);
        glutenActua.setSelected(false);
        lactosaActua.setSelected(false);
        picanteActua.setSelected(false);
        selecctionProductoActua.getSelectionModel().clearSelection();
        selecctionProductoActua.setPromptText("< Selecciona un producto >");
        seleccionCategoriaActua.getSelectionModel().clearSelection();
        seleccionCategoriaActua.setPromptText("< Selecciona un elemento >");
    }
    
    @FXML
    private void limpiarCamposProducto(ActionEvent event) {
        nombreActua.clear();
        descriActua.clear();
        precioActua.clear();
        tipoActua.clear();
        porcionActua.clear();
        vegeActua.setSelected(false);
        vegaActua.setSelected(false);
        glutenActua.setSelected(false);
        lactosaActua.setSelected(false);
        picanteActua.setSelected(false);
        selecctionProductoActua.getSelectionModel().clearSelection();
        selecctionProductoActua.setPromptText("< Selecciona un producto >");
        seleccionCategoriaActua.getSelectionModel().clearSelection();
        seleccionCategoriaActua.setPromptText("< Selecciona un elemento >");
    }

}
