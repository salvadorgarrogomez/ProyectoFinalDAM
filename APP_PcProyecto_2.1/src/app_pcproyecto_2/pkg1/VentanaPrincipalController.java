// VentanaPrincipalController.java
package app_pcproyecto_2.pkg1;

import static app_pcproyecto_2.pkg1.APP_PcProyecto_21.getConnection;
import constructores.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class VentanaPrincipalController implements Initializable {

    private int usuarioId;

    @FXML
    Pane layout;
    @FXML
    private Button buttonUsuario;
    @FXML
    private Button auditoria;
    private Mesas_ComandasController mesasController;
    private Parent mesasScene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXCollections.observableArrayList();
        auditoria.setDisable(true);
        try {
            pantallaInicio();
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void mostrarLogin() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CambioUsuario.fxml"));
            Parent root = loader.load();
            CambioUsuarioController controller = loader.getController();
            controller.setPermitirCerrarVentanaPrincipal(true); // Establecer el indicador como verdadero
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cambio de Usuario");

            // Crear una alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro?");
            alert.setContentText("Se perderán los datos de las comandas que no se haya confirmado el pago. ¿Quieres continuar?");

            // Obtener la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si el usuario confirma, cerrar la ventana principal
                Stage mainWindow = (Stage) buttonUsuario.getScene().getWindow();
                mainWindow.close();
                // Mostrar la ventana de cambio de usuario
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pantallaInicio() throws IOException {

        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("PantallaInicio.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    @FXML
    public void pantallaGlosario() throws IOException {

        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("Glosario.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    @FXML
    public void pantallaAuditoria() throws IOException {
        // Cargar el archivo FXML de la ventana de Auditoria
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Auditoria.fxml"));
        Parent auditoria = loader.load();
        AuditoriaController controller = loader.getController();

        Usuarios usuario = new Usuarios();
        usuario.setId(usuarioId);

        controller.setUsuario(usuario);
        // Cambiar a la escena de Auditoria
        layout.getChildren().setAll(auditoria);
    }

    @FXML
    public void pantallaMesas() throws IOException {
        // Verificar si la escena de Mesas y Comandas aún no se ha cargado
        if (mesasScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mesas_Comandas.fxml"));
            mesasScene = loader.load();
            mesasController = loader.getController();
            Usuarios usuario = new Usuarios();
            usuario.setId(usuarioId);
            mesasController.setUsuario(usuario);
        }

        // Alternar la visibilidad de las escenas
        layout.getChildren().setAll(mesasScene);
        mesasScene.setVisible(true); // Mostrar la escena de Mesas y Comandas
        // Aquí puedes ocultar otras escenas si es necesario
    }

    @FXML
    public void pantallaPlatos() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListadoMenus.fxml"));
        Parent root = loader.load();
        ListadoMenusController controller = loader.getController();

        // Crear un objeto Usuarios con el usuarioId y pasarlo al controlador ListadoMenusController
        Usuarios usuario = new Usuarios();
        usuario.setId(usuarioId);

        controller.setUsuario(usuario);
        layout.getChildren().setAll(root);
    }

    @FXML
    public void botonSalir() throws IOException {
        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        System.exit(0);

    }

    // Método para inicializar el usuarioId
    public void inicializar(int usuarioId) {
        this.usuarioId = usuarioId;
        if (esAdmin(usuarioId)) {
            auditoria.setDisable(false);
        }
        // Realiza cualquier inicialización adicional necesaria aquí
    }

    @FXML
    public void pantallaDetalle() throws IOException {

        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("DetallesComanda.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    public void setMesasController(Mesas_ComandasController mesasController) {
    }

    private boolean esAdmin(int usuarioId) {
        try {
            Connection connection = getConnection(); // Obtener la conexión a la base de datos (debes implementar este método)
            if (connection != null) {
                String sql = "SELECT rol FROM usuarios WHERE id = ? AND rol = 'admin'";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, usuarioId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        return resultSet.next(); // Retorna true si el usuario tiene el rol de admin
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Retorna false si hubo un error o el usuario no tiene el rol de admin
    }

}
