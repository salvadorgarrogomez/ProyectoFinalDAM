package app_pcproyecto_2.pkg0;

import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarCategorias();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cargarCategorias() throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement consultaCategorias = connection.prepareStatement("SELECT * FROM categorias"); ResultSet resultadoCategorias = consultaCategorias.executeQuery()) {

            List<Categorias> categorias = new ArrayList<>();
            while (resultadoCategorias.next()) {
                int id = resultadoCategorias.getInt("id");
                String nombre = resultadoCategorias.getString("nombre");
                Categorias categoria = new Categorias(id, nombre);
                categorias.add(categoria);
            }
            glosarioCategoria.getItems().addAll(categorias);

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

    private void cargarProductosPorCategoria(Categorias categoriaSeleccionada) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM productos WHERE categoria_id = ?")) {
            statement.setInt(1, categoriaSeleccionada.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Productos producto = new Productos();
                    producto.setId(resultSet.getInt("id"));
                    producto.setNombre(resultSet.getString("nombre"));
                    producto.setPrecio(resultSet.getDouble("precio"));
                    producto.setTipo_porcion(resultSet.getString("tipo_porcion"));
                    productosObservableList.add(producto);
                }
                mostrarProductosEnListView();
            }
        }
    }

    private void mostrarDetalleProducto(Productos producto) {
        StringBuilder detalles = new StringBuilder();

        // Realizar una consulta SQL para obtener todos los datos del producto
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM productos WHERE id = ?")) {
            statement.setInt(1, producto.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    detalles.append("ID: ").append(resultSet.getInt("id")).append("\n");
                    detalles.append("Nombre: ").append(resultSet.getString("nombre")).append("\n");
                    detalles.append("Descripcion: ").append(resultSet.getString("description")).append("\n");
                    detalles.append("Precio: ").append(resultSet.getDouble("precio")).append("\n");
                    detalles.append("Tipo plato: ").append(resultSet.getString("tipo_plato")).append("\n");
                    detalles.append("Tipo porcion: ").append(resultSet.getString("tipo_porcion")).append("\n");
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
                    // Formatear la fecha para eliminar los microsegundos
                    String fecha = resultSet.getString("fecha_hora");
                    fecha = fecha.substring(0, fecha.indexOf('.'));
                    detalles.append("Fecha de la ultima actualización: ").append(fecha).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        areaDetalle.setText(detalles.toString());
    }

    private void mostrarProductosEnListView() {
        glosarioPlatos.setItems(productosObservableList);
        glosarioPlatos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleProducto(newValue);
            }
        });
    }
}
