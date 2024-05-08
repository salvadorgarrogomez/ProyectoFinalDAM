package app_pcproyecto_2.pkg0;

import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
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

    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setVentanaPrincipalController(VentanaPrincipalController controller) {
        this.ventanaPrincipalController = controller;
    }

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

    @FXML
    public void mostrarNuevoUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoUsuario.fxml"));
        Parent root = loader.load();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Nuevo Usuario:");
        popupStage.setScene(new Scene(root));
        popupStage.setOnCloseRequest(event -> {
            event.consume();
            popupStage.close();
            cargarVentanaCambioUsuario();
        });
        popupStage.showAndWait();
    }

    private void cargarVentanaCambioUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CambioUsuario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonEntrar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cambio de Usuario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void login() throws SQLException, IOException {
        permitirCerrarVentanaPrincipal = true;
        String nombreUsuario = usuarioNombre.getText();
        String contrasenia = usuarioContrase.getText();
        usuarioId = obtenerIdUsuario(nombreUsuario, contrasenia); // Obtener el id del usuario
        if (usuarioId != 0) { // Si el id del usuario es diferente de 0 (indicando que se encontró en la base de datos)
            Usuarios usuario = obtenerUsuario(usuarioId);
            abrirVentanaPrincipal(usuario);
        } else {
            mostrarAlerta("Nombre de usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    private void abrirVentanaPrincipal(Usuarios usuario) throws IOException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipal.fxml"));
            Parent principalVentana = loader.load();
            Scene principalScene = new Scene(principalVentana);
            VentanaPrincipalController controller = loader.getController();
            controller.inicializar(usuarioId);
            Stage principalStage = new Stage();
            principalStage.setScene(principalScene);
            principalStage.setTitle("Aplicacion TPV para el Bar ElEscobar");
            principalStage.show();
            Stage stage = (Stage) buttonEntrar.getScene().getWindow();
            stage.close();
            if (ventanaPrincipalController != null && permitirCerrarVentanaPrincipal) {
                ventanaPrincipalController.pantallaInicio();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private boolean validarCredenciales(String nombreUsuario, String contrasenia) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                statement.setString(2, contrasenia);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setPermitirCerrarVentanaPrincipal(boolean permitirCerrar) {
        this.permitirCerrarVentanaPrincipal = permitirCerrar;
    }

    public boolean isPermitirCerrarVentanaPrincipal() {
        return permitirCerrarVentanaPrincipal;
    }
}
