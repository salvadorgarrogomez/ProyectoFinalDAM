package app_pcproyecto_2.pkg0;

import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
import constructores.Usuarios;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.Button;

public class NuevoUsuarioController implements Initializable {

    @FXML
    private TextField newUser;

    @FXML
    private PasswordField newContra;

    @FXML
    private PasswordField newRepiteContra;

    @FXML
    private TextField userAdmin;

    @FXML
    private PasswordField contraAdmin;

    @FXML
    private Button confirmarNewUser;

    @FXML
    private Button confirmarAdmin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aquí puedes inicializar la clase, si es necesario
        confirmarNewUser.setDisable(true);
    }

    @FXML
    private void iniciarSesionAdmin() throws SQLException {
        String nombreUsuario = userAdmin.getText();
        String contrasenia = contraAdmin.getText();
        if (validarCredenciales(nombreUsuario, contrasenia)) {
            // Código para iniciar sesión exitoso
            if (esAdmin(nombreUsuario)) {
                // Habilitar el botón para agregar nuevo usuario
                confirmarNewUser.setDisable(false);
            } else {
                // Deshabilitar el botón para agregar nuevo usuario
                confirmarNewUser.setDisable(true);
            }
            // Limpiar los campos de inicio de sesión si es necesario
            contraAdmin.clear();
            userAdmin.clear();
        } else {
            // Mostrar mensaje de error si las credenciales son incorrectas
            mostrarAlerta("Credenciales incorrectas. Por favor, inténtelo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void confirmarNuevoUsuario() throws SQLException {
        String nombreUsuario = newUser.getText();
        String contrasenia = newContra.getText();
        String repiteContrasenia = newRepiteContra.getText();

        // Crear una instancia de Usuarios con los datos proporcionados por el usuario
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setNombre(nombreUsuario);
        nuevoUsuario.setContrasenia(contrasenia);

        // Verificar si las contraseñas coinciden
        if (!contrasenia.equals(repiteContrasenia)) {
            mostrarAlerta("Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }

        // Intentar agregar el nuevo usuario
        if (agregarNuevoUsuario(nuevoUsuario)) {
            mostrarAlerta("Nuevo usuario creado correctamente.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) newUser.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Error al crear el nuevo usuario.", Alert.AlertType.ERROR);
        }
    }

    // Método para agregar un nuevo usuario
    private boolean agregarNuevoUsuario(Usuarios usuario) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                String sql = "INSERT INTO usuarios (nombre, contrasenia) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getContrasenia());
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            } else {
                mostrarAlerta("Error de conexión. La conexión no está establecida.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al ejecutar la consulta SQL.", Alert.AlertType.ERROR);
            return false;
        }
    }

    private boolean existeUsuario(String nombreUsuario, Connection connection) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
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

    private boolean validarCredenciales(String nombreUsuario, String contrasenia) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                statement.setString(2, contrasenia);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Retorna true si se encontró un usuario con las credenciales proporcionadas
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Retorna false si hubo un error o las credenciales son incorrectas
    }

    private boolean esAdmin(String nombreUsuario) throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            String sql = "SELECT rol FROM usuarios WHERE nombre = ? AND rol = 'admin'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Retorna true si el usuario tiene el rol de admin
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Retorna false si hubo un error o el usuario no tiene el rol de admin
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
