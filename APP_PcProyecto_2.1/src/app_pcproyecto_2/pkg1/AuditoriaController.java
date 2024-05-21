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
import java.time.LocalDate;
import java.util.ArrayList;
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

    //  Metodo auxiliar, que llamamos desde actualizarNombreCategoria de tal forma que si el usuario quiere actualizar un nombre, y el nuevo nombre ya existe
    //  No se pueda actualizar, al existir previamente
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

    //  Meotodo para dar funcionamiente a un boton de borrar da datos, en este caso la finalidad es borrar la categoria establecida con un combox que pasa info
    //  a un texfield
    @FXML
    private void borrarCategoria() {
        // Se obtiene el nombre de la categoría seleccionada para borrar del TextField
        String nombreCategoria = borraCatego.getText();
        // Se consulta concordandia por nombre con el id de la tabla Categorias
        int categoriaId = obtenerIdCategoriaPorNombre(nombreCategoria);
        //  con el != en caso de el id sea diferente de -1 significa que no se ha encontrado concordancia de id, por tanto no se ejecutara el borrado
        //  Si es igual a 1 si se que ejecutara la operacion, en este caso, como el id_categoria es una foreign key de Productos, y no puede ser not null, 
        //  lo que se ha realizado es en primera opcion un update de todos los productos con id categoria asociado, para asignarles otra categoria
        //  por defecto sera a la categoria 0, y despues de ello se eliminara la categoria seleccionada con un delete
        if (categoriaId != -1) {
            try (Connection connection = getConnection()) {
                // Se actualizan los productos asociados a la categoría borrada
                String sqlUpdate = "UPDATE productos SET categoria_id = 0 WHERE categoria_id = ? AND usuario_id = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdate)) {
                    pstmtUpdate.setInt(1, categoriaId);
                    pstmtUpdate.setInt(2, usuario.getId());
                    pstmtUpdate.executeUpdate();
                }
                // Se borrar la categoría seleccionada
                String sqlDelete = "DELETE FROM categorias WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, categoriaId);
                    pstmtDelete.executeUpdate();
                }
                // Se le muestra al usuario un mensaje de confirmacion del cambio
                mostrarAlerta("Éxito", "Categoría eliminada correctamente.", Alert.AlertType.INFORMATION);
                // Se limpian el TextField y el ComboBox
                borraCatego.clear();
                selectCategoriBorrar.getSelectionModel().clearSelection();
                // Se actualiza la lista de categorías y el ComboBox
                cargarCategorias();
            } catch (SQLException e) {
                e.printStackTrace();
                //  En caso de -1, es decir de no concordancia de id_categoria con una existente, se muestran mensajes de error al usuario
                mostrarAlerta("Error", "Error al eliminar la categoría.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "La categoría especificada no existe.", Alert.AlertType.ERROR);
        }
    }

    //  Como en los metodos anteriores de cargaCategorias, se realiza consulta en este caso a la tabla Usuarios, para que se cargue en su combox de referencia
    //  De forma adicional, se implementa que se carguen todos los id menos el 3 que corresponde al admin, por seguridad este usuario no quiero sea modifcado o borrado
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

    //  Implementacion del metodo de borrado de usuarios por inicializacion mediante boton en la escena
    @FXML
    private void borrarUsuarios() {
        // Obtencion del nombre del usuario seleccionado para borrar del TextField
        String nombreUser = borraUser.getText();
        // Se obtiene el nombre del usuario mediante conconcordancia por id
        int usuarioId = obtenerIdUsuarioPorNombre(nombreUser);
        //  Como en el metodo para borrar Categorias, se establece una relacion en base al id, si el id no es igual a 1, salta error no permitiendo borrar 
        //  el usuario, si el id coincide con uno ya existente, significa que ya existe en la base de datos, en tal caso si que dejara borrar el usuario seleccionado
        if (usuarioId != -1) {
            try (Connection connection = getConnection()) {
                String sqlDelete = "DELETE FROM usuarios WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, usuarioId);
                    pstmtDelete.executeUpdate();
                }
                // Se le muestra al usuario un mensaje de confirmacion del cambio
                mostrarAlerta("Éxito", "Usuario eliminado correctamente.", Alert.AlertType.INFORMATION);
                // Se limpian el TextField y el ComboBox
                borraUser.clear();
                selectUserBorrar.getSelectionModel().clearSelection();
                // Actualización la lista de categorías y el ComboBox
                cargarUsuarios();
            } catch (SQLException e) {
                e.printStackTrace();
                //  En caso de -1, es decir de no concordancia de id_categoria con una existente, se muestran mensajes de error al usuario
                mostrarAlerta("Error", "Error al eliminar el usuario.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El usuario especificado no existe.", Alert.AlertType.ERROR);
        }
    }

    //  Metodo auxliar para consulta de concordancia de nombre con un id en la tabla, con este metodo que se llama desde el metodo de borrado,
    private int obtenerIdUsuarioPorNombre(String nombreUsuario) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        // Valor predeterminado en caso de que no se encuentre la categoría
        int usuarioId = -1;
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

    //  Metodo de activacion por boton para actualizar el nombre de usuario en base a los datos obtenidos desde un combox
    @FXML
    private void actualizarNombreUsuario() {
        // Obtencion el nuevo nombre del usuario desde el TextField
        String nuevoNombre = newNombreUser.getText();
        // Obtencion el nuevo rol del usuario desde el ComboBox
        String nuevoRol = selectRol.getValue();
        // Validacion de que el nuevo nombre y el nuevo rol no estén vacíos en sus respectivos apartados, texfield y combox
        if (!nuevoNombre.isEmpty() && nuevoRol != null) {
            // Obtencion el usuario actualmente seleccionado
            Usuarios usuarioSeleccionado = selectUser.getValue();
            if (usuarioSeleccionado != null) {
                int idUsuario = usuarioSeleccionado.getId(); 
                // Se realiza consulta para verificar si el nuevo nombre ya existe
                int idExistente = obtenerIdUsuarioPorNombre(nuevoNombre);
                if (idExistente == -1 || idExistente == idUsuario) {
                    // Si el nuevo nombre no existe o pertenece al mismo usuario, se procede con la actualización
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
                            // Se actualiza la lista de usuarios y se vuelve a establecer el ComboBox
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

    //  Igual que con otros casos, en este caso para cargar los roles disponibles de usuarios
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

    //  Todos estos metodos son muy similares, ene ste caso se realiza consulta a los productos para cargar el combox con los productos ordenados por ID
    private void cargarProductos() throws SQLException {
        productosList.clear();
        try (Connection connection = getConnection(); PreparedStatement consultaProductos = connection.prepareStatement("SELECT * FROM productos ORDER BY id"); ResultSet resultadoProductos = consultaProductos.executeQuery()) {
            List<Productos> productos = new ArrayList<>();
            while (resultadoProductos.next()) {
                //  Se ha creado un contructor a proposito en la clase Productos para mostrar los datos en el formato requerido, que seria para mostrar los datos siguiente.
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

    //  Metodo auxiliar para obtener el id de Producto en base al nombre seleccionado. Este metodo se llama desde el metodo de FXML para borrar productos de la tabla
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

    //  Metodo FXML para poder inicializarlo desde la escena, en este caso desde un boton
    @FXML
    private void borrarProducto() {
        // Obtencion del nombre del producto seleccionada para borrar del TextField
        String nombreProducto = borraPlato.getText();
        // Obtencion del ID del producto seleccionada
        int usuarioId = obtenerIdProductoPorNombre(nombreProducto);
        // Como en casos anteriores, se utiliza el id para concordar con el producto correspondiente
        if (usuarioId != -1) {
            try (Connection connection = getConnection()) {
                String sqlDelete = "DELETE FROM productos WHERE id = ?";
                try (PreparedStatement pstmtDelete = connection.prepareStatement(sqlDelete)) {
                    pstmtDelete.setInt(1, usuarioId);
                    pstmtDelete.executeUpdate();
                }
                // Se muestra mensaje de éxito al usuario
                mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
                // Se limpian el TextField y el ComboBox
                borraPlato.clear();
                selectPlatoBorrar.getSelectionModel().clearSelection();
                // Se actualiza la lista de categorías y el ComboBox
                cargarProductos();
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al eliminar el producto.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "El producto especificado no existe.", Alert.AlertType.ERROR);
        }
    }

    //  Metodo que se inicializa desde la escena, en este caso este metodo se utiliza para dar funcionalidad al calendario, de tal forma que cuando se le da a un
    //  en el calendario, se muestra por pantalla informacion relevante, en este caso se carga un listview con los Tickets
    @FXML
    private void manejarSeleccionFecha() {
        // Se obtiene la fecha seleccionada en el DatePicker
        LocalDate fechaSeleccionada = calendario.getValue();
        // Se obtienen las comandas correspondientes a la fecha seleccionada
        List<TicketComanda> comandas = obtenerTicketsPorFecha(fechaSeleccionada);
        // Se muestran las comandas en el ListView
        mostrarTickets(comandas);
        //  Con las tres funciones, se esta llamando a varios metodos, donde se encapsulan los funcionamientos necesarios par mostrar datos
    }

    //  Meotodo auxiliar llamado desde el metodo anterior para manejar fechas, con este metodo y mediante consulta a bbdd, se obtienen los ticket asociados
    //  al dia en concreto de creacion del ticket
    private List<TicketComanda> obtenerTicketsPorFecha(LocalDate fecha) {
        //  Array instanciado en la clase de TicketComanda que hace referencia a su tabla en bbdd, de esta forma se tiene acceso a todos los datos guardados
        List<TicketComanda> tickets = new ArrayList<>();
        try (Connection connection = getConnection()) {
            //  Consulta para obtencion de los datos en la tabla, la fecha_pedido::date se basa en la seleccion del dia en el calendario
            String sql = "SELECT * FROM ticket_comanda WHERE fecha_pedido::date = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, fecha);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Se itera sobre los resultados y crean objetos TicketComanda
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
        if (event.getClickCount() == 1) { // Se verifica si se ha hecho un solo clic
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
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM mesas WHERE nombre = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombreMesa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Construccion del objeto Mesas
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

    //  En este metodo, mediante la creacion de objetos de TicketComanda, para mostrar los datos y añadirlos a una tabla en base a la fecha seleccionada en un DatePicker
    private void cargarDatosTabla() {
        //  Se obtiene la fecha seleccionada en un DatePicker
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
                    // Se procesan los resultados de la consulta y almacenan los datos por día
                    while (rs.next()) {
                        int dia = rs.getInt("dia");
                        int id = rs.getInt("id");
                        double totalSinIVA = rs.getDouble("total_sin_iva");
                        double totalConIVA = rs.getDouble("total_con_iva");
                        int totalComensales = rs.getInt("total_comensales");
                        // Creacion de un objeto TicketComanda y agregarlo al mapa
                        TicketComanda ticketComanda = new TicketComanda();
                        // Se setea la fecha con el año, mes y día obtenidos de la base de datos
                        ticketComanda.setFecha_pedido(Date.valueOf(LocalDate.of(anio, fechaSeleccionada.getMonthValue(), dia))); 
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
            // Se limpia la tabla antes de agregar nuevos datos
            tableDatos.getItems().clear();
            // Se agregan los datos organizados por día a la tabla
            tableDatos.getItems().addAll(datosPorDia.values());
            tableDatos.refresh();
            // Configuracion de las celdas de la tabla
            tableComandas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
            tableSIN.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getImporte_total_sin_IVA())));
            tableCON.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getImporte_total_con_IVA())));
            tableComensa.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNum_comensales())));
            calcularTotalesMensuales();
            actualizarGrafica();
        }
    }

    //  Metodo para el calculo de los valores, en este caso, se calculan los totales de los iva y sin iva, asi como de los comensales que han entrado al loca, como del total de comandas
    //  Este metodo, se ha asociado al metodo anterior de carga de datos a la tabla, para que los coja de referencia y realice los calculos
    private void calcularTotalesMensuales() {
        double totalSinIVA = 0.0;
        double totalConIVA = 0.0;
        int totalNumComensales = 0;
        int totalNumComandas = 0;
        // Se recorren los datos de la tabla
        for (TicketComanda ticket : tableDatos.getItems()) {
            totalSinIVA += ticket.getImporte_total_sin_IVA();
            totalConIVA += ticket.getImporte_total_con_IVA();
            totalNumComensales += ticket.getNum_comensales();
            totalNumComandas += ticket.getId(); // Suma de la cantidad de comandas realizadas día a día
        }
        // Se muestran los totales en los TextField correspondientes con un stringformat, para asignar un valor mentario y de decimales
        totalSINIVA.setText(String.format("%.2f€", totalSinIVA));
        totalCONIVA.setText(String.format("%.2f€", totalConIVA));
        totalComensales.setText(String.valueOf(totalNumComensales));
        totalComandas.setText(String.valueOf(totalNumComandas));
    }

    //  Metodo auxiliar, asociado al metodo de carga de datos de la tabla, en este caso, para cargar los datos en una grafica de estadisticas.
    @SuppressWarnings("unchecked")
    private void actualizarGrafica() {
        // Obtencion de los datos de la tabla
        ObservableList<TicketComanda> datosTabla = tableDatos.getItems();
        // Se crean las series de datos para cada métrica
        XYChart.Series<String, Number> serieSinIVA = new XYChart.Series<>();
        XYChart.Series<String, Number> serieConIVA = new XYChart.Series<>();
        XYChart.Series<String, Number> serieComensales = new XYChart.Series<>();
        XYChart.Series<String, Number> serieComandas = new XYChart.Series<>();
        //  Designacion de lo que representa cada valor en la tabla (cada area de datos)
        serieSinIVA.setName("Sin IVA");
        serieConIVA.setName("Con IVA");
        serieComensales.setName("Comensales");
        serieComandas.setName("Comandas");
        List<String> etiquetasEjeX = new ArrayList<>();
        // Se recorren los datos de la tabla y se agregan a las series de datos correspondientes
        for (TicketComanda ticket : datosTabla) {
            Date fechaPedido = (Date) ticket.getFecha_pedido();
            LocalDate localDate = fechaPedido.toLocalDate();
            String dia = String.valueOf(localDate.getDayOfMonth());
            double totalSinIVA = ticket.getImporte_total_sin_IVA();
            double totalConIVA = ticket.getImporte_total_con_IVA();
            int totalComensales = ticket.getNum_comensales();
            int totalComandas = ticket.getId(); // Comandas
            // Se agregan los datos a las series de datos
            serieSinIVA.getData().add(new XYChart.Data<>(dia, totalSinIVA));
            serieConIVA.getData().add(new XYChart.Data<>(dia, totalConIVA));
            serieComensales.getData().add(new XYChart.Data<>(dia, totalComensales));
            serieComandas.getData().add(new XYChart.Data<>(dia, totalComandas));
            etiquetasEjeX.add(dia); // Agregar el día como etiqueta del eje X
        }
        // Configuracion de las etiquetas personalizadas en el eje X
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(etiquetasEjeX));
        ((CategoryAxis) graficaDatos.getXAxis()).setCategories(FXCollections.observableArrayList(etiquetasEjeX));
        // Se limpian los datos existentes en la gráfica y se agregan las nuevas series de datos
        graficaDatos.getData().clear();
        graficaDatos.getData().addAll(serieSinIVA, serieConIVA, serieComensales, serieComandas);
    }

    //  Metodo inicializado mediante un boton en la escena, cone ste metodo se busca que mediante la seleccion de un valor en el listview, se pueda borrar
    //  el TicketComanda en base al id del ticket seleccionado
    @FXML
    private void borrarElementoSeleccionado() {
        // Se obtiene el elemento seleccionado del ListView
        TicketComanda ticketSeleccionado = ListComandas.getSelectionModel().getSelectedItem();
        // Verificacion de si se ha seleccionado un elemento
        if (ticketSeleccionado != null) {
            try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement("DELETE FROM ticket_comanda WHERE id = ?")) {
                // Obtenicion del ID del ticket seleccionado
                int idTicket = ticketSeleccionado.getId();
                // Se establece el ID en la consulta preparada
                pstmt.setInt(1, idTicket);
                // Finalmente se ejectua la consulta DELETE
                int filasBorradas = pstmt.executeUpdate();
                // Por ultimo se verifica si se ha borrado correctamente el elemento
                if (filasBorradas > 0) {
                    // Eliminacion del elemento del ListView
                    ListComandas.getItems().remove(ticketSeleccionado);
                    // Se notifica al usuario que se ha borrado correctamente con un mensaje de alerta
                    mostrarAlerta("Éxito", "El ticket ha sido borrado correctamente.", Alert.AlertType.INFORMATION);
                } else {
                    // Se notifica al usuario si no se ha borrado el elemento
                    mostrarAlerta("Error", "No se pudo borrar el ticket.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Hubo un error al borrar el ticket.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Por favor, seleccione un ticket para borrar.", Alert.AlertType.ERROR);
        }
    }

    //  Metodo base, para la implementacion de mensaje de alerta estilo poup emergente, para que sean mostrados al usuario, mostrando en todo momento que es lo que esta realizando
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    //  Con la utilizacion del constructor de la clase Usuarios y pasando este metodo, se pasa al controlador el usuaria logeado en la app,
    //  de tal forma que siempre quedara registrado todo lo que vayan realizando los usuario en la app y en la bbdd
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

}
