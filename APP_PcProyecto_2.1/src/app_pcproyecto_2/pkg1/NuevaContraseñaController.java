package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
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

    //  Metodo incializado desde un boton en la escena, para cambiar la contraseña si asi lo desea un usuario que haya olvidado la contraseña de su usuario
    @FXML
    private void cambiarContraseña() {
        String nombreUsuario = usuarioRecuperar.getText();
        String contrasenia = nuevaContra.getText();
        String repiteContrasenia = repiteNuevaContra.getText();
        // Se verifica las contraseñas coinciden
        if (!contrasenia.equals(repiteContrasenia)) {
            mostrarAlerta("Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }
        // Se verifica si el nombre de usuario existe en la base de datos
        if (!existeUsuario(nombreUsuario)) {
            mostrarAlerta("El nombre de usuario no existe.", Alert.AlertType.ERROR);
            return;
        }
        // Se realiza el cambio de contraseña
        if (actualizarContraseña(nombreUsuario, contrasenia)) {
            // Se muestra un mensaje de éxito si se actualizó correctamente
            mostrarAlerta("Contraseña cambiada correctamente.", Alert.AlertType.INFORMATION);
        } else {
            // Se muestra un mensaje de error si no se pudo actualizar la contraseña
            mostrarAlerta("Error al cambiar la contraseña.", Alert.AlertType.ERROR);
        }
        // Cerrado de la ventana al terminar la operacion
        Stage stage = (Stage) confirmarContra.getScene().getWindow();
        stage.close();
    }

    //  Metodo auxiliar donde se encuentra el UPDATE para actualizar el valor en bbdd
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

    //  Metodo auxiliar para comprobar  si existe un usuario con el nombre añadido, si existe el usuario se podra cambiar la contraseña
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

    //  Metodo necesario para dar formato a las aletar poup que van saliendole al usuario cada vez que realiza una operacion
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
