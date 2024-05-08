package app_pcproyecto_2.pkg0;

import static app_pcproyecto_2.pkg0.APP_PcProyecto_20.getConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuevaContraseñaController implements Initializable {

    @FXML
    private TextField usuarioRecuperar;

    @FXML
    private PasswordField nuevaContra;

    @FXML
    private PasswordField repiteNuevaContra;

    @FXML
    private Button confirmarContra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cambiarContraseña() {
        String nombreUsuario = usuarioRecuperar.getText();
        String contrasenia = nuevaContra.getText();
        String repiteContrasenia = repiteNuevaContra.getText();

        // Verificar si las contraseñas coinciden
        if (!contrasenia.equals(repiteContrasenia)) {
            mostrarAlerta("Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }

        // Verificar si el nombre de usuario existe en la base de datos
        if (!existeUsuario(nombreUsuario)) {
            mostrarAlerta("El nombre de usuario no existe.", Alert.AlertType.ERROR);
            return;
        }

        // Realizar el cambio de contraseña
        if (actualizarContraseña(nombreUsuario, contrasenia)) {
            // Mostrar un mensaje de éxito si se actualizó correctamente
            mostrarAlerta("Contraseña cambiada correctamente.", Alert.AlertType.INFORMATION);
        } else {
            // Mostrar un mensaje de error si no se pudo actualizar la contraseña
            mostrarAlerta("Error al cambiar la contraseña.", Alert.AlertType.ERROR);
        }

        // Cerrar la ventana
        Stage stage = (Stage) confirmarContra.getScene().getWindow();
        stage.close();
    }

    private boolean actualizarContraseña(String nombreUsuario, String nuevaContraseña) {
        String sqlUpdate = "UPDATE usuarios SET contrasenia = ? WHERE nombre = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
            statement.setString(1, nuevaContraseña);
            statement.setString(2, nombreUsuario);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
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

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
