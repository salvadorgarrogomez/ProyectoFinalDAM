package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Categorias;
import constructores.Mesas;
import constructores.Productos;
import constructores.TicketComanda;
import constructores.Usuarios;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author salva
 */
public class AuditoriaController implements Initializable {

    @FXML
    private ComboBox<Categorias> selectCategor;
    @FXML
    private ComboBox<Categorias> selectCategoriBorrar;
    @FXML
    private ComboBox<Productos> selectPlatoBorrar;
    @FXML
    private ComboBox<Usuarios> selectUser;
    @FXML
    private ComboBox<Usuarios> selectUserBorrar;
    @FXML
    private ComboBox<String> selectRol;
    @FXML
    private ListView<TicketComanda> ListComandas;
    @FXML
    private DatePicker calendario;
    @FXML
    private TableView<TicketComanda> tableDatos;
    @FXML
    private TableColumn<TicketComanda, String> tableMES;
    @FXML
    private TableColumn<TicketComanda, String> tableDIA;
    @FXML
    private TableColumn<TicketComanda, String> tableComandas;
    @FXML
    private TableColumn<TicketComanda, String> tableSIN;
    @FXML
    private TableColumn<TicketComanda, String> tableCON;
    @FXML
    private TableColumn<TicketComanda, String> tableComensa;
    @FXML
    private DatePicker calendarioTabla;
    @FXML
    private TextField totalSINIVA;
    @FXML
    private TextField totalCONIVA;
    @FXML
    private TextField totalComensales;
    @FXML
    private TextField totalComandas;
    @FXML
    private StackedBarChart<String, Number> graficaDatos;
    @FXML
    private TextField newCategoria;
    @FXML
    private TextField borraCatego;
    @FXML
    private TextField newNombreCatego;
    @FXML
    private TextField newNombreUser;
    @FXML
    private TextField borraUser;
    @FXML
    private TextField borraPlato;
    private ObservableList<Categorias> categoriasList = FXCollections.observableArrayList();
    private ObservableList<Categorias> categoriasListBorrar = FXCollections.observableArrayList();
    private ObservableList<Usuarios> usuariosList = FXCollections.observableArrayList();
    private ObservableList<Productos> productosList = FXCollections.observableArrayList();

    private Usuarios usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //  Dentro de esta clase Controller, se definen las llamadas a metodos definidos, para que se ejecuten y activen nada mas inicializar la escena
        //  En este caso, se realiza el lanzamiento de varios metodos, diseñados para cargar datos de distintas tablas de la bbdd
        //  en varios combox de la escena.
        try {
            cargarCategorias();
            cargarCategoriasBorrar();
            cargarUsuarios();
            cargarROL();
            cargarProductos();
            //  Evento diseñado, para que al selecctionar un dia en el calendario y se cargue la tabla implementada en la escena.
            calendarioTabla.setOnAction(event -> cargarDatosTabla());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //  Lanzador que castea las fechas de los pedidos guardados en bbdd, de tal forma que los divida por mes en la tabla, de tal forma que solo salgan
        //  los ticket del mes seleccionado
        tableMES.setCellValueFactory(cellData -> {
            java.sql.Date fechaPedido = (java.sql.Date) cellData.getValue().getFecha_pedido();
            LocalDate fechaLocal = fechaPedido.toLocalDate(); // Convertir a LocalDate directamente
            return new SimpleStringProperty(String.valueOf(fechaLocal.getMonthValue()));
        });

        //   Igual que el lanzador anterior, pero para que muestre los ticket por diferenciados por dias
        tableDIA.setCellValueFactory(cellData -> {
            java.sql.Date fechaPedido = (java.sql.Date) cellData.getValue().getFecha_pedido();
            LocalDate fechaLocal = fechaPedido.toLocalDate(); // Convertir a LocalDate directamente
            return new SimpleStringProperty(String.valueOf(fechaLocal.getDayOfMonth()));
        });

        //  Lanzador, que permite, mostrar en un textfield, el dato seleccionado en un combox, en este caso se utiliza una lista obersable, para 
        //  tener controlados todos los valores existentes en el combox
        selectCategor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Categorias>() {
            @Override
            public void changed(ObservableValue<? extends Categorias> observable, Categorias oldValue, Categorias newValue) {
                if (newValue != null) {
                    // Se obtiene el nombre de la categoiria dentro del combox, llamando al contructor de Categorias
                    String nombreCategoria = newValue.getNombre();
                    // Se establece el texfield, donde debe de aparece el elemento seleccionado en el combox
                    newNombreCatego.setText(nombreCategoria);
                }
            }
        });

        //  Lanzador con la misma funcion que en el anterior, pero enfozado para un combox distinto
        selectCategoriBorrar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Categorias>() {
            @Override
            public void changed(ObservableValue<? extends Categorias> observable, Categorias oldValue, Categorias newValue) {
                if (newValue != null) {
                    // Se obtiene el nombre del usuario dentro del combox, llamando al contructor de Categorias                    
                    String nombreCategoria = newValue.getNombre();
                    // Se establece el texfield, donde debe de aparece el elemento seleccionado en el combox
                    borraCatego.setText(nombreCategoria);
                }
            }
        });

        selectUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuarios>() {
            @Override
            public void changed(ObservableValue<? extends Usuarios> observable, Usuarios oldValue, Usuarios newValue) {
                if (newValue != null) {
                    // Se obtiene el nombre del usuario dentro del combox, llamando al contructor de Usuarios
                    String nombreUsuario = newValue.getNombre();
                    // Se establece el texfield, donde debe de aparece el elemento seleccionado en el combox
                    newNombreUser.setText(nombreUsuario);
                }
            }
        });

        selectUserBorrar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuarios>() {
            @Override
            public void changed(ObservableValue<? extends Usuarios> observable, Usuarios oldValue, Usuarios newValue) {
                if (newValue != null) {
                    // Se obtiene el nombre del usuario dentro del combox, llamando al contructor de Usuarios
                    String nombreUsuario = newValue.getNombre();
                    // Se establece el texfield, donde debe de aparece el elemento seleccionado en el combox
                    borraUser.setText(nombreUsuario);
                }
            }
        });

        selectPlatoBorrar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Productos>() {
            @Override
            public void changed(ObservableValue<? extends Productos> observable, Productos oldValue, Productos newValue) {
                if (newValue != null) {
                    // Se obtiene el nombre del producto dentro del combox, llamando al contructor de Productos
                    String nombreProducto = newValue.getNombre();
                    // Establecer el nombre de la categoría en el TextField
                    borraPlato.setText(nombreProducto);
                }
            }
        });

    }

    //  Metodo con el que se realiza una consulta a la tabla en la bbdd estableciendo la conexion a base de datos,
    //  En este caso, la consulta se hace a la tabla categorias, ordenando por id, para que se vean los datos ordenados en todo momento.
    private void cargarCategorias() throws SQLException {
        //  Inicializacion de la lista observable, que sera la que cargara el combox
        categoriasList.clear(); // Se limpia la lista antes de volver a cargar las categorías
        try (Connection connection = getConnection(); PreparedStatement consultaCategorias = connection.prepareStatement("SELECT * FROM categorias ORDER BY id"); ResultSet resultadoCategorias = consultaCategorias.executeQuery()) {
            //  Despues de establecer la consulta, llamanado al constructor de categorias, se deja especificado lo que se requiere que salga por pantalla, en el combox
            while (resultadoCategorias.next()) {
                int id = resultadoCategorias.getInt("id");
                String nombre = resultadoCategorias.getString("nombre");
                Categorias categoria = new Categorias(id, nombre);
                //  Acto seguido se añade a la lista observable la consulta realizada
                categoriasList.add(categoria);
            }
            //  Se establece, donde vamos a mostrar los datos de la lista observable, en este caso en un combox
            selectCategor.setItems(categoriasList);
        }
    }

    //  Este metodo realiza exactamente lo mismo que el metodo anterior, pero exluyendo un valor concreto que no es necesario que el usuario vea por pantalla
    private void cargarCategoriasBorrar() throws SQLException {
        categoriasListBorrar.clear(); // Limpiar la lista antes de volver a cargar las categorías
        try (Connection connection = getConnection(); PreparedStatement consultaCategorias = connection.prepareStatement("SELECT * FROM categorias WHERE id != 0 ORDER BY id"); ResultSet resultadoCategorias = consultaCategorias.executeQuery()) {

            while (resultadoCategorias.next()) {
                int id = resultadoCategorias.getInt("id");
                String nombre = resultadoCategorias.getString("nombre");
                Categorias categoria = new Categorias(id, nombre);
                categoriasListBorrar.add(categoria);
            }
            selectCategoriBorrar.setItems(categoriasListBorrar);
        }
    }

    //  Metodo FXML, lo que significa que sera inicializado o llamado desde un boton establecido en la escena
    //  Con este metodo lo que se consigue, es la inserccion de datos en una tabla concreta, en este caso en la tabla categorias
    @FXML
    private void insertarNuevaCategoria() {
        // Se obtiene el nombre de la nueva categoría desde un TextField
        String nombreCategoria = newCategoria.getText();
        //  Se verifica si el campo tiene datos, en caso de estar vacio el texfield, al estar configurada la tabla en bbdd como un campo null,
        //  no se pueden añadir valores en blanco, por tanto se le muestra un mensaje al usuario de que debe de colocar un nombre de forma obligada
        if (nombreCategoria.isEmpty()) {
            mostrarAlerta("Error", "El campo de la nueva categoría está vacío.", Alert.AlertType.ERROR);
            return;
        }
        // Conexion a la bbdd para ejecutar la consulta e insertar datos a la categoría
        //  De forma adicional a la inserccion del nombre de la categoria, tambien se añade de forma "automatica" el id de usuario que ha añadido el dato,
        //  este dato del usuario, se ha establecido, pasado entre escenas y con su constructor, el usuario que se ha logeado en la app
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO categorias (nombre, usuario_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombreCategoria);
                pstmt.setInt(2, usuario.getId());
                pstmt.executeUpdate();
                // En caso de inserccion correcta de datos, al usuario se le muestra un mensaje de exito
                mostrarAlerta("Éxito", "La nueva categoría se ha insertado correctamente.", Alert.AlertType.INFORMATION);
                // Se limpia el TextField después de la inserción
                newCategoria.clear();
                // Se actualiza nuevamente la lista de categorías en el combox, para que la nueva inserccion ya sea utilizable por el usuario
                cargarCategorias();
            }
        } catch (SQLException e) {
            // En caso de error, se muestra notificacion de fallo
            mostrarAlerta("Error", "Error al insertar la nueva categoría en la base de datos.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    //  En este caso, este boton se ha establecido para que el usuario pueda actualizar el nombre de la categoria seleccionada en un combox
    @FXML
    private void actualizarNombreCategoria() {
        // Texfield donde se establecera el nuevo nombre para la categoria
        String nuevoNombre = newNombreCatego.getText();
        // Se valida que el nuevo nombre establecido por el usuario no este en blanco o sea null
        if (!nuevoNombre.isEmpty()) {
            //  Con esta instanciacion d la clase Categorias, y en base al combox, se establece la categoria a la que se requiere cambiar el nombre desde el textfield
            Categorias categoriaSeleccionada = selectCategor.getValue();
            if (categoriaSeleccionada != null) {
                int idCategoria = categoriaSeleccionada.getId();
                // Se realiza una consulta para verificar si el nuevo nombre ya existe
                boolean nombreExistente = nombreCategoriaExistente(nuevoNombre);
                if (!nombreExistente) {
                    // Si el nuevo nombre no existe, se procede con la actualización
                    String sql = "UPDATE categorias SET nombre = ?, usuario_id = ? WHERE id = ?";
                    try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setString(1, nuevoNombre);
                        pstmt.setInt(2, usuario.getId()); // Aquí se establece el usuario que realiza la modificación
                        pstmt.setInt(3, idCategoria);
                        int rowsUpdated = pstmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            mostrarAlerta("Éxito", "Nombre de la categoría actualizado correctamente.", Alert.AlertType.INFORMATION);
                            newNombreCatego.clear();
                            // Se actualiza la lista de categorías en el combox
                            cargarCategorias();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarAlerta("Error", "Hubo un error al actualizar el nombre de la categoría.", Alert.AlertType.ERROR);
                    }
                } else {
                    mostrarAlerta("Error", "El nuevo nombre de la categoría ya existe.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Por favor, seleccione una categoría para actualizar.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El nuevo nombre de la categoría no puede estar vacío.", Alert.AlertType.ERROR);
        }
    }

    //  Metodo auxiliar que sera utilizado desde otros metodos, para verificar en base al nombre, la concordancia del nombre con el id relacionado en la tabla
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

    private boolean nombreCategoriaExistente(String nuevoNombre) {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevoNombre);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void borrarCategoria() {
        // Obtener el nombre de la categoría seleccionada para borrar del TextField
        String nombreCategoria = borraCatego.getText();
        // Obtener el ID de la categoría seleccionada
        int categoriaId = obtenerIdCategoriaPorNombre(nombreCategoria);

        if (categoriaId != -1) {
            try (Connection connection = getConnection()) {
                // Actualizar los productos asociados a la categoría borrada
                String sqlUpdate = "UPDATE productos SET categoria_id = 0 WHERE categoria_id = ? AND usuario_id = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdate)) {
                    pstmtUpdate.setInt(1, categoriaId);
                    pstmtUpdate.setInt(2, usuario.getId());
                    pstmtUpdate.executeUpdate();
                }

                // Borrar la categoría seleccionada
                String sqlDelete = "DELETE FROM categorias WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, categoriaId);
                    pstmtDelete.executeUpdate();
                }

                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", "Categoría eliminada correctamente.", Alert.AlertType.INFORMATION);
                // Limpiar el TextField y el ComboBox
                borraCatego.clear();
                selectCategoriBorrar.getSelectionModel().clearSelection();
                // Actualizar la lista de categorías y el ComboBox
                cargarCategorias();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al eliminar la categoría.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "La categoría especificada no existe.", Alert.AlertType.ERROR);
        }
    }

    private void cargarUsuarios() throws SQLException {
        usuariosList.clear();
        try (Connection connection = getConnection(); PreparedStatement consultaUsuarios = connection.prepareStatement("SELECT * FROM usuarios WHERE id != 3"); ResultSet resultadoUsuarios = consultaUsuarios.executeQuery()) {

            while (resultadoUsuarios.next()) {
                int id = resultadoUsuarios.getInt("id");
                String nombre = resultadoUsuarios.getString("nombre");
                String rol = resultadoUsuarios.getString("rol");
                Usuarios usuario = new Usuarios(id, nombre, rol);
                usuariosList.add(usuario); // Agregar el usuario a la lista
            }

            selectUser.setItems(usuariosList); // Utilizar setItems() para asignar la lista al ComboBox
            selectUserBorrar.setItems(usuariosList); // Utilizar setItems() para asignar la lista al otro ComboBox
        }
    }

    @FXML
    private void borrarUsuarios() {
        // Obtener el nombre de la categoría seleccionada para borrar del TextField
        String nombreUser = borraUser.getText();
        // Obtener el ID de la categoría seleccionada
        int usuarioId = obtenerIdUsuarioPorNombre(nombreUser);

        if (usuarioId != -1) {
            try (Connection connection = getConnection()) {
                String sqlDelete = "DELETE FROM usuarios WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, usuarioId);
                    pstmtDelete.executeUpdate();
                }

                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", "Usuario eliminado correctamente.", Alert.AlertType.INFORMATION);
                // Limpiar el TextField y el ComboBox
                borraUser.clear();
                selectUserBorrar.getSelectionModel().clearSelection();
                // Actualizar la lista de categorías y el ComboBox
                cargarUsuarios();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al eliminar el usuario.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El usuario especificado no existe.", Alert.AlertType.ERROR);
        }
    }

    private int obtenerIdUsuarioPorNombre(String nombreUsuario) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int usuarioId = -1; // Valor predeterminado en caso de que no se encuentre la categoría
        try {
            connection = getConnection();
            String sql = "SELECT id FROM usuarios WHERE nombre = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioId = resultSet.getInt("id");
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
        return usuarioId;
    }

    @FXML
    private void actualizarNombreUsuario() {
        // Obtener el nuevo nombre del usuario desde el TextField
        String nuevoNombre = newNombreUser.getText();

        // Obtener el nuevo rol del usuario desde el ComboBox
        String nuevoRol = selectRol.getValue();

        // Validar que el nuevo nombre y el nuevo rol no estén vacíos
        if (!nuevoNombre.isEmpty() && nuevoRol != null) {
            // Obtener el usuario actualmente seleccionado
            Usuarios usuarioSeleccionado = selectUser.getValue();
            if (usuarioSeleccionado != null) {
                int idUsuario = usuarioSeleccionado.getId(); // Asegúrate de usar el método correcto para obtener el ID

                // Realizar una consulta para verificar si el nuevo nombre ya existe
                int idExistente = obtenerIdUsuarioPorNombre(nuevoNombre);

                if (idExistente == -1 || idExistente == idUsuario) {
                    // Si el nuevo nombre no existe o pertenece al mismo usuario, proceder con la actualización
                    String sql = "UPDATE usuarios SET nombre = ?, rol = ? WHERE id = ?";
                    try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setString(1, nuevoNombre);
                        pstmt.setString(2, nuevoRol); // Establecer el nuevo rol del usuario
                        pstmt.setInt(3, idUsuario); // Establecer el ID del usuario en la consulta SQL
                        int rowsUpdated = pstmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            mostrarAlerta("Éxito", "Nombre y rol del usuario actualizados correctamente.", Alert.AlertType.INFORMATION);
                            newNombreUser.clear();
                            selectRol.setValue(null);
                            // Actualizar la lista de usuarios y volver a establecerla en el ComboBox
                            cargarUsuarios();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        mostrarAlerta("Error", "Hubo un error al actualizar el nombre y rol del usuario.", Alert.AlertType.ERROR);
                    }
                } else {
                    mostrarAlerta("Error", "El nuevo nombre del usuario ya existe.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Por favor, seleccione un usuario para actualizar.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El nuevo nombre del usuario y su rol no pueden estar vacíos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarROL() throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement consultaRoles = connection.prepareStatement("SELECT DISTINCT rol FROM usuarios"); ResultSet resultadoRoles = consultaRoles.executeQuery()) {

            List<String> roles = new ArrayList<>();
            while (resultadoRoles.next()) {
                String rol = resultadoRoles.getString("rol");
                roles.add(rol);
            }
            selectRol.getItems().addAll(roles);
        }
    }

    private void cargarProductos() throws SQLException {
        productosList.clear();
        try (Connection connection = getConnection(); PreparedStatement consultaProductos = connection.prepareStatement("SELECT * FROM productos ORDER BY id"); ResultSet resultadoProductos = consultaProductos.executeQuery()) {

            List<Productos> productos = new ArrayList<>();
            while (resultadoProductos.next()) {
                int id = resultadoProductos.getInt("id");
                String nombre = resultadoProductos.getString("nombre");
                double precio = resultadoProductos.getDouble("precio");
                String tipo_porcion = resultadoProductos.getString("tipo_porcion");
                Productos producto = new Productos(id, nombre, precio, tipo_porcion);
                productosList.add(producto);
            }
            selectPlatoBorrar.getItems().addAll(productosList);
        }
    }

    private int obtenerIdProductoPorNombre(String nombreProducto) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int productoId = -1; // Valor predeterminado en caso de que no se encuentre la categoría
        try {
            connection = getConnection();
            String sql = "SELECT id FROM productos WHERE nombre = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombreProducto);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                productoId = resultSet.getInt("id");
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
        return productoId;
    }

    @FXML
    private void borrarProducto() {
        // Obtener el nombre de la categoría seleccionada para borrar del TextField
        String nombreProducto = borraPlato.getText();
        // Obtener el ID de la categoría seleccionada
        int usuarioId = obtenerIdProductoPorNombre(nombreProducto);

        if (usuarioId != -1) {
            try (Connection connection = getConnection()) {
                String sqlDelete = "DELETE FROM productos WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, usuarioId);
                    pstmtDelete.executeUpdate();
                }

                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                // Limpiar el TextField y el ComboBox
                borraPlato.clear();
                selectPlatoBorrar.getSelectionModel().clearSelection();
                // Actualizar la lista de categorías y el ComboBox
                cargarProductos();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al eliminar el producto.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El producto especificado no existe.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void manejarSeleccionFecha() {
        // Obtener la fecha seleccionada en el DatePicker
        LocalDate fechaSeleccionada = calendario.getValue();

        // Obtener las comandas correspondientes a la fecha seleccionada
        List<TicketComanda> comandas = obtenerTicketsPorFecha(fechaSeleccionada);

        // Mostrar las comandas en el ListView
        mostrarTickets(comandas);
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
        ListComandas.getItems().setAll(tickets);
    }

    // Método para manejar la selección de un ticket en el ListView
    @FXML
    private void handleTicketSelection(MouseEvent event) {
        if (event.getClickCount() == 1) { // Verifica si se ha hecho un solo clic
            // Obtener el ticket seleccionado en la lista
            TicketComanda ticketSeleccionado = ListComandas.getSelectionModel().getSelectedItem();
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

    private void cargarDatosTabla() {
        LocalDate fechaSeleccionada = calendarioTabla.getValue();

        if (fechaSeleccionada != null) {
            // Mapa para almacenar los datos por día
            Map<Integer, TicketComanda> datosPorDia = new HashMap<>();

            // Obtener el mes y el año de la fecha seleccionada
            int anio = fechaSeleccionada.getYear();

            // Consulta SQL para obtener los datos de las comandas del mes seleccionado
            String sql = "SELECT EXTRACT(DAY FROM fecha_pedido) as dia, COUNT(*) as id, "
                    + "SUM(importe_total_sin_IVA) as total_sin_iva, SUM(importe_total_con_IVA) as total_con_iva, "
                    + "SUM(num_comensales) as total_comensales "
                    + "FROM ticket_comanda "
                    + "WHERE EXTRACT(MONTH FROM fecha_pedido) = ? AND EXTRACT(YEAR FROM fecha_pedido) = ? "
                    + "GROUP BY EXTRACT(DAY FROM fecha_pedido)";

            try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, fechaSeleccionada.getMonthValue());
                pstmt.setInt(2, anio);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Procesar los resultados de la consulta y almacenar los datos por día
                    while (rs.next()) {
                        int dia = rs.getInt("dia");
                        int id = rs.getInt("id");
                        double totalSinIVA = rs.getDouble("total_sin_iva");
                        double totalConIVA = rs.getDouble("total_con_iva");
                        int totalComensales = rs.getInt("total_comensales");

                        // Crear un objeto TicketComanda y agregarlo al mapa
                        TicketComanda ticketComanda = new TicketComanda();
                        ticketComanda.setFecha_pedido(Date.valueOf(LocalDate.of(anio, fechaSeleccionada.getMonthValue(), dia))); // Setear la fecha con el año, mes y día obtenidos de la base de datos
                        ticketComanda.setId(id);
                        ticketComanda.setImporte_total_con_IVA(totalConIVA);
                        ticketComanda.setImporte_total_sin_IVA(totalSinIVA);
                        ticketComanda.setNum_comensales(totalComensales);
                        datosPorDia.put(dia, ticketComanda);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Hubo un error al cargar los datos de la tabla." + e.getMessage(), Alert.AlertType.ERROR);
            }

            // Limpiar la tabla antes de agregar nuevos datos
            tableDatos.getItems().clear();

            // Agregar los datos organizados por día a la tabla
            tableDatos.getItems().addAll(datosPorDia.values());
            tableDatos.refresh();

            // Configurar las celdas de la tabl
            tableComandas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
            tableSIN.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getImporte_total_sin_IVA())));
            tableCON.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getImporte_total_con_IVA())));
            tableComensa.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNum_comensales())));

            calcularTotalesMensuales();
            actualizarGrafica();
        }
    }

    private void calcularTotalesMensuales() {
        double totalSinIVA = 0.0;
        double totalConIVA = 0.0;
        int totalNumComensales = 0;
        int totalNumComandas = 0;

        // Recorrer los datos de la tabla
        for (TicketComanda ticket : tableDatos.getItems()) {
            totalSinIVA += ticket.getImporte_total_sin_IVA();
            totalConIVA += ticket.getImporte_total_con_IVA();
            totalNumComensales += ticket.getNum_comensales();
            totalNumComandas += ticket.getId(); // Sumar la cantidad de comandas realizadas día a día
        }

        // Mostrar los totales en los TextField correspondientes
        totalSINIVA.setText(String.format("%.2f€", totalSinIVA));
        totalCONIVA.setText(String.format("%.2f€", totalConIVA));
        totalComensales.setText(String.valueOf(totalNumComensales));
        totalComandas.setText(String.valueOf(totalNumComandas));
    }

    @SuppressWarnings("unchecked")
    private void actualizarGrafica() {
        // Obtener los datos de la tabla
        ObservableList<TicketComanda> datosTabla = tableDatos.getItems();

        // Crear series de datos para cada métrica
        XYChart.Series<String, Number> serieSinIVA = new XYChart.Series<>();
        XYChart.Series<String, Number> serieConIVA = new XYChart.Series<>();
        XYChart.Series<String, Number> serieComensales = new XYChart.Series<>();
        XYChart.Series<String, Number> serieComandas = new XYChart.Series<>();

        serieSinIVA.setName("Sin IVA");
        serieConIVA.setName("Con IVA");
        serieComensales.setName("Comensales");
        serieComandas.setName("Comandas");
        List<String> etiquetasEjeX = new ArrayList<>();

        // Recorrer los datos de la tabla y agregarlos a las series de datos correspondientes
        for (TicketComanda ticket : datosTabla) {
            Date fechaPedido = (Date) ticket.getFecha_pedido();
            LocalDate localDate = fechaPedido.toLocalDate();
            String dia = String.valueOf(localDate.getDayOfMonth());
            double totalSinIVA = ticket.getImporte_total_sin_IVA();
            double totalConIVA = ticket.getImporte_total_con_IVA();
            int totalComensales = ticket.getNum_comensales();
            int totalComandas = ticket.getId(); // Comandas

            // Agregar los datos a las series de datos
            serieSinIVA.getData().add(new XYChart.Data<>(dia, totalSinIVA));
            serieConIVA.getData().add(new XYChart.Data<>(dia, totalConIVA));
            serieComensales.getData().add(new XYChart.Data<>(dia, totalComensales));
            serieComandas.getData().add(new XYChart.Data<>(dia, totalComandas));
            etiquetasEjeX.add(dia); // Agregar el día como etiqueta del eje X
        }

        // Configurar etiquetas personalizadas en el eje X
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(etiquetasEjeX));
        ((CategoryAxis) graficaDatos.getXAxis()).setCategories(FXCollections.observableArrayList(etiquetasEjeX));

        // Configurar etiquetas emergentes al pasar el ratón sobre las columnas
        final DecimalFormat format = new DecimalFormat("#.00"); // Formato para el importe
        for (XYChart.Series<String, Number> series : Arrays.asList(serieSinIVA, serieConIVA, serieComensales, serieComandas)) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Tooltip.install(data.getNode(), new Tooltip(format.format(data.getYValue()) + "€"));
            }
        }

        // Limpiar los datos existentes en la gráfica y agregar las nuevas series de datos
        graficaDatos.getData().clear();
        graficaDatos.getData().addAll(serieSinIVA, serieConIVA, serieComensales, serieComandas);
    }

// Método para obtener los días del mes como etiquetas para el eje X
    private ObservableList<String> getDiasDelMes() {
        ObservableList<String> dias = FXCollections.observableArrayList();
        for (int i = 1; i <= 31; i++) {
            dias.add(String.valueOf(i));
        }
        return dias;
    }

    @FXML
    private void borrarElementoSeleccionado() {
        // Obtener el elemento seleccionado del ListView
        TicketComanda ticketSeleccionado = ListComandas.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un elemento
        if (ticketSeleccionado != null) {
            try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement("DELETE FROM ticket_comanda WHERE id = ?")) {
                // Obtener el ID del ticket seleccionado
                int idTicket = ticketSeleccionado.getId();

                // Establecer el ID en la consulta preparada
                pstmt.setInt(1, idTicket);

                // Ejecutar la consulta DELETE
                int filasBorradas = pstmt.executeUpdate();

                // Verificar si se ha borrado correctamente el elemento
                if (filasBorradas > 0) {
                    // Eliminar el elemento del ListView
                    ListComandas.getItems().remove(ticketSeleccionado);
                    // Notificar al usuario que se ha borrado correctamente
                    mostrarAlerta("Éxito", "El ticket ha sido borrado correctamente.", Alert.AlertType.INFORMATION);
                } else {
                    // Notificar al usuario si no se ha borrado el elemento
                    mostrarAlerta("Error", "No se pudo borrar el ticket.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar cualquier error de la base de datos
                mostrarAlerta("Error", "Hubo un error al borrar el ticket.", Alert.AlertType.ERROR);
            }
        } else {
            // Notificar al usuario si no se ha seleccionado ningún elemento
            mostrarAlerta("Error", "Por favor, seleccione un ticket para borrar.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

}
