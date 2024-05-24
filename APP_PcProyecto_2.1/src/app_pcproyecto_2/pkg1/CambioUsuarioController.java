package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CambioUsuarioController implements Initializable {

    @FXML
    private TextField usuarioNombre;
    @FXML
    private PasswordField usuarioContrase;
    @FXML
    private Button buttonEntrar;

    private boolean permitirCerrarVentanaPrincipal;
    private int usuarioId;
    private Usuarios usuario;
    private VentanaPrincipalController ventanaPrincipalController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de cualquier dato necesario al cargar la ventana
    }

    // Método para mostrar la ventana de cambio de contraseña
    @FXML
    public void mostrarNuevaContra() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaContraseña.fxml"));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Nueva Contraseña:");
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
    }

    // Método para mostrar la ventana de nuevo usuario
    @FXML
    public void mostrarNuevoUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoUsuario.fxml"));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Nuevo Usuario:");
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
    }

    // Método para manejar el evento de login
    @FXML
    private void login() throws SQLException, IOException {
        permitirCerrarVentanaPrincipal = true; // Permitir cerrar la ventana principal
        String nombreUsuario = usuarioNombre.getText();
        String contrasenia = usuarioContrase.getText();
        usuarioId = obtenerIdUsuario(nombreUsuario, contrasenia); // Obtener el id del usuario
        if (usuarioId != 0) { // Si se encontró un usuario con las credenciales proporcionadas
            Usuarios usuario = obtenerUsuario(usuarioId); // Obtener los detalles del usuario
            abrirVentanaPrincipal(usuario); // Abrir la ventana principal
        } else {
            mostrarAlerta("Nombre de usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    // Método para abrir la ventana principal de la aplicación
    private void abrirVentanaPrincipal(Usuarios usuario) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipal.fxml"));
            Parent principalVentana = loader.load();
            Scene principalScene = new Scene(principalVentana);
            VentanaPrincipalController controller = loader.getController();
            controller.inicializar(usuarioId); // Inicializar la ventana principal con el id del usuario
            Stage principalStage = new Stage();
            principalStage.setScene(principalScene);
            principalStage.setTitle("Aplicacion TPV para el Bar ElEscobar");
            principalStage.show();
            Stage stage = (Stage) buttonEntrar.getScene().getWindow();
            stage.close(); // Cerrar la ventana de login
            if (ventanaPrincipalController != null && permitirCerrarVentanaPrincipal) {
                ventanaPrincipalController.pantallaInicio(); // Mostrar la pantalla de inicio en la ventana principal
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener los detalles de un usuario por su id
    private Usuarios obtenerUsuario(int usuarioId) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, usuarioId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Usuarios usuario = new Usuarios();
                        usuario.setId(resultSet.getInt("id"));
                        usuario.setNombre(resultSet.getString("nombre"));
                        usuario.setRol(resultSet.getString("rol"));
                        return usuario;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Método para obtener el id de un usuario por su nombre y contraseña
    private int obtenerIdUsuario(String nombreUsuario, String contrasenia) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT id FROM usuarios WHERE nombre = ? AND contrasenia = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                statement.setString(2, contrasenia);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        }
        return 0; // Devolver 0 si no se encontró ningún usuario con las credenciales proporcionadas
    }

    // Método para salir de la aplicación
    @FXML
    public void botonSalir() throws IOException {
        System.exit(0); // Se cierra la aplicación
    }

    // Método para mostrar una alerta
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para establecer el ID del usuario actual
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Método para establecer el controlador de la ventana principal
    public void setVentanaPrincipalController(VentanaPrincipalController controller) {
        this.ventanaPrincipalController = controller;
    }

    // Método para permitir o no cerrar la ventana principal
    public void setPermitirCerrarVentanaPrincipal(boolean permitirCerrar) {
        this.permitirCerrarVentanaPrincipal = permitirCerrar;
    }

    // Método para verificar si se permite cerrar la ventana principal
    public boolean isPermitirCerrarVentanaPrincipal() {
        return permitirCerrarVentanaPrincipal;
    }
}
