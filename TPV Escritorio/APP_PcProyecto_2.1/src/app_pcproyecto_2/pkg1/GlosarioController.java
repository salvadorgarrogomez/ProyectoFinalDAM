package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Categorias;
import constructores.Productos;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class GlosarioController implements Initializable {

    @FXML
    private ComboBox<Categorias> glosarioCategoria;
    @FXML
    private ListView<Productos> glosarioPlatos;
    @FXML
    private TextArea areaDetalle;

    private ObservableList<Productos> productosObservableList = FXCollections.observableArrayList();

    // En el metodo initialize se añade una llamada al metodo cargarCategorias(), para que cuando se entre en la escena, se carguen los datos de forma
    //  rapida y automatica donde se requiera y se ha diseñado
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarCategorias();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método para cargar las categorías desde la base de datos y mostrarlas en el ComboBox
    private void cargarCategorias() throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement consultaCategorias = connection.prepareStatement("SELECT * FROM categorias ORDER BY id"); ResultSet resultadoCategorias = consultaCategorias.executeQuery()) {
            List<Categorias> categorias = new ArrayList<>();
            while (resultadoCategorias.next()) {
                int id = resultadoCategorias.getInt("id");
                String nombre = resultadoCategorias.getString("nombre");
                Categorias categoria = new Categorias(id, nombre);
                categorias.add(categoria);
            }
            glosarioCategoria.getItems().addAll(categorias);
            // Listener para manejar el evento de selección de una categoría
            glosarioCategoria.setOnAction(event -> {
                productosObservableList.clear();
                Categorias categoriaSeleccionada = glosarioCategoria.getValue();
                if (categoriaSeleccionada != null) {
                    try {
                        cargarProductosPorCategoria(categoriaSeleccionada);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // Método para cargar los productos por categoría y mostrarlos en el ListView
    //  En este caso, al seleccionar una categoria o valor en el combox, se cargaran todos productos asociados en el listview
    private void cargarProductosPorCategoria(Categorias categoriaSeleccionada) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM productos WHERE categoria_id = ?")) {
            statement.setInt(1, categoriaSeleccionada.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    //  Se crea un contructor de Productos, llamando a un nuevo producto, y asociando la estructura que debe de tener
                    Productos producto = new Productos();
                    producto.setId(resultSet.getInt("id"));
                    producto.setNombre(resultSet.getString("nombre"));
                    producto.setPrecio(resultSet.getDouble("precio"));
                    producto.setTipo_porcion(resultSet.getString("tipo_porcion"));
                    productosObservableList.add(producto);
                }
                // Se muestran los productos en el ListView
                mostrarProductosEnListView();
            }
        }
    }

    // Método para mostrar el detalle de un producto seleccionado en el ListView
    //   En este metodo, al clickar sobre un producto de los que ha aparecido en el listview, de tal forma que al darle click, se mostraran todos los datos 
    //  se le han dado un formato con StringBuilder, para que tenga un formato amigable y legible para el usuario
    private void mostrarDetalleProducto(Productos producto) {
        StringBuilder detalles = new StringBuilder();
        // Consulta SQL para obtener todos los datos del producto
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            statement.setInt(1, producto.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Construcción del detalle del producto
                    detalles.append("ID: ").append(resultSet.getInt("id")).append("\n");
                    detalles.append("Nombre: ").append(resultSet.getString("nombre")).append("\n");
                    detalles.append("Descripcion: ").append(resultSet.getString("description")).append("\n");
                    detalles.append("Precio: ").append(resultSet.getDouble("precio")).append("\n");
                    detalles.append("Tipo plato: ").append(resultSet.getString("tipo_plato")).append("\n");
                    detalles.append("Tipo porcion: ").append(resultSet.getString("tipo_porcion")).append("\n");
                    //  Como estos datos son booleanos, no hace falta que se muestren todos si son false o true, con la implementacion de estos if/else
                    //  solo se mostraran los valores true por pantalla
                    detalles.append("Información adicional: \n");
                    if (resultSet.getBoolean("es_vegetariano")) {
                        detalles.append("- Es vegetariano\n");
                    }
                    if (resultSet.getBoolean("es_vegano")) {
                        detalles.append("- Es vegano\n");
                    }
                    if (resultSet.getBoolean("es_sin_gluten")) {
                        detalles.append("- Es sin gluten\n");
                    }
                    if (resultSet.getBoolean("es_sin_lactosa")) {
                        detalles.append("- Es sin lactosa\n");
                    }
                    if (resultSet.getBoolean("es_picante")) {
                        detalles.append("- Es picante\n");
                    }
                    // Se formatea la fecha para eliminar los microsegundos
                    String fecha = resultSet.getString("fecha_hora");
                    int indexOfDot = fecha.indexOf('.');
                    if (indexOfDot != -1) {
                        fecha = fecha.substring(0, indexOfDot);
                    }
                    detalles.append("Fecha de la ultima actualización: ").append(fecha).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        areaDetalle.setText(detalles.toString());
    }

    // Método para mostrar los productos en el ListView
    private void mostrarProductosEnListView() {
        glosarioPlatos.setItems(productosObservableList);
        // Listener para manejar el evento de selección de un producto en el ListView
        glosarioPlatos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleProducto(newValue);
            }
        });
    }
}
