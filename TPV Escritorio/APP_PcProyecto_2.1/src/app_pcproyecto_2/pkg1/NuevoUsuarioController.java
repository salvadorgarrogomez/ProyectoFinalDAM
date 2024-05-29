package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
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

    //  Se deshabilita al entrar en la escena el boton, indicado, ya que no debe de ser utilizable de incio, a modo validacion por usuario con rol "admin"
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aquí puedes inicializar la clase, si es necesario
        confirmarNewUser.setDisable(true);
    }

    // Metodo activado por boton en la escena, para verificar user y contraseña , de tal forma que se pueda comprobar el rol del usuario introducido, si 
    //  el usuario se comprueba que es admin, se habilitara el boton para añadir un nuevo usuario al sistema
    @FXML
    private void iniciarSesionAdmin() throws SQLException {
        String nombreUsuario = userAdmin.getText();
        String contrasenia = contraAdmin.getText();
        if (validarCredenciales(nombreUsuario, contrasenia)) {
            // Código para iniciar sesión exitoso
            if (esAdmin(nombreUsuario)) {
                // Se habilita el botón para agregar nuevo usuario
                confirmarNewUser.setDisable(false);
            } else {
                // Se deshabilita el botón para agregar nuevo usuario
                confirmarNewUser.setDisable(true);
            }
            // Se limpian los campos de inicio de sesión
            contraAdmin.clear();
            userAdmin.clear();
        } else {
            // Se muestra un mensaje de error si las credenciales son incorrectas
            mostrarAlerta("Credenciales incorrectas. Por favor, inténtelo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    //  Metodo inicialializado desde la escena, para confirmar la introduccion al sistema de un nuevo usuario, como se ve en el metodo anterior,
    //  este boton en la escena solo se habilita si el usuario validado es admin
    @FXML
    private void confirmarNuevoUsuario() throws SQLException {
        String nombreUsuario = newUser.getText();
        String contrasenia = newContra.getText();
        String repiteContrasenia = newRepiteContra.getText();
        // Se crea una instancia de Usuarios con los datos proporcionados por el usuario
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setNombre(nombreUsuario);
        nuevoUsuario.setContrasenia(contrasenia);
        // Se verifica si las contraseñas coinciden
        if (!contrasenia.equals(repiteContrasenia)) {
            mostrarAlerta("Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }
        // Se verifica si ya existe un usuario con el mismo nombre
        if (existeUsuario(nombreUsuario)) {
            mostrarAlerta("Ya existe un usuario con el mismo nombre.", Alert.AlertType.ERROR);
            return;
        }
        // Si los datos son correctos y las contraseñas coinciden y no existe un usuario con el mismo nombre,
        // se agrega el nuevo usuario
        if (agregarNuevoUsuario(nuevoUsuario)) {
            mostrarAlerta("Nuevo usuario creado correctamente.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) newUser.getScene().getWindow();
            stage.close();
        } else {
            //En caso contrario, se muestra un mensaje de error al usuario
            mostrarAlerta("Error al crear el nuevo usuario.", Alert.AlertType.ERROR);
        }
    }

    // Método para agregar un nuevo usuario, donde se encuentra el INSERT para añadir datos a la tabla en la bbdd
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

    //  Metodo de consulta, para verificar con un COUNT los nombres de usuario ya existentes en la tabla de la bbdd, para verificar si ya existe un usuario o no
    private boolean existeUsuario(String nombreUsuario) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, nombreUsuario);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            return count > 0;
                        }
                    }
                }
            } else {
                //  Distintos mensajes de error en caso de que la conexion no se haya podido establecer a la bbdd
                mostrarAlerta("Error de conexión. La conexión no está establecida.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al ejecutar la consulta SQL.", Alert.AlertType.ERROR);
            return false;
        }
        return false;
    }

    //  Metodo para validar credenciales en el boton para comprobar si el nombre y contraseña son correctos
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

    //  Comprobacion en bbdd para comprobar el rol del usuario, si es coincidente o no con el rol "admin" en el metodo de boton, permitira o no algunas acctiones
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

    //  Metodo necesario para mostrar las alertas en tipo popup, para que el usuario sea consciente de las operaciones que va realizando en la aplicacion
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
