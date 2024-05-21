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

    //  Metodo initialize, donde he especificado la escena que debe de salir por defecto al iniciar al entrar en ella, en este caso se incializa el metodo
    //  fxml del pantallaInicio, de tal forma que se mostrar el contenido de la escena indicada
    //  por otro lado, se deshabilita el boton de auditoria, este solo ha de estar activado en determinadas condiciones
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

    // Metodo activado por boton, que se activa en la escena, con este boton, se mostrara la escena del FXML referenciada
    @FXML
    public void mostrarLogin() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CambioUsuario.fxml"));
            Parent root = loader.load();
            CambioUsuarioController controller = loader.getController();
            // Se establece el indicador como verdadero, en este caso, se cierra la ventanaprincipal para que no este disponible mientras la escena de CambioUsuario
            //  este abierta a modo de seguridad
            controller.setPermitirCerrarVentanaPrincipal(true);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cambio de Usuario");
            // Se crea una alerta de confirmación y se avisa al usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro?");
            alert.setContentText("Se perderán los datos de las comandas que no se haya confirmado el pago. ¿Quieres continuar?");
            // Se obtiene la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si el usuario confirma, se cierra la ventana principal
                Stage mainWindow = (Stage) buttonUsuario.getScene().getWindow();
                mainWindow.close();
                // Se muestra la ventana de cambio de usuario
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Metodo activado desde la escena, este metodo es el que por defecto se activa en el initialice, para se que muestre en primera instancia al entrar
    @FXML
    public void pantallaInicio() throws IOException {
        // Se carga el archivo FXML de la ventana de PantallaInicio
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("PantallaInicio.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    // Metodo activado desde la escena
    @FXML
    public void pantallaGlosario() throws IOException {
        // Se carga el archivo FXML de la ventana de Glosario
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("Glosario.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    // Metodo activado desde la escena mediante un boton
    @FXML
    public void pantallaAuditoria() throws IOException {
        // Se carga el archivo FXML de la ventana de Auditoria
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Auditoria.fxml"));
        Parent auditoria = loader.load();
        AuditoriaController controller = loader.getController();
        //  Se pasa mediante el contoladaror el usuario logeado a la escena destino, para poder mostrarlo y darlo uso en las distintas operaciones que se realicen
        Usuarios usuario = new Usuarios();
        usuario.setId(usuarioId);
        controller.setUsuario(usuario);
        // Se cambia a la escena de Auditoria
        layout.getChildren().setAll(auditoria);
    }

    // Metodo activado a desde la escena mediante un boton
    //  Este metodo es distinto a los demas, en este caso, para que las comandas sean funcionales y no se borre el contenido al cambiar de escena,
    // donde se mata y reactiva la escena y el Controller, no se destruye la escena, sino que se deja en segundo plano, para que los datos en la escena no se pierdan
    @FXML
    public void pantallaMesas() throws IOException {
        // Se verifica si la escena de Mesas y Comandas aún no se ha cargado
        if (mesasScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mesas_Comandas.fxml"));
            mesasScene = loader.load();
            mesasController = loader.getController();
            Usuarios usuario = new Usuarios();
            usuario.setId(usuarioId);
            mesasController.setUsuario(usuario);
        }
        // Se alterna la visibilidad de las escenas
        layout.getChildren().setAll(mesasScene);
        mesasScene.setVisible(true); // Mostrar la escena de Mesas y Comandas
    }

    // Metodo activado a desde la escena mediante un boton
    @FXML
    public void pantallaPlatos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListadoMenus.fxml"));
        Parent root = loader.load();
        ListadoMenusController controller = loader.getController();
        // Se crea un objeto Usuarios con el usuarioId y se pasa al controlador ListadoMenusController
        Usuarios usuario = new Usuarios();
        usuario.setId(usuarioId);
        controller.setUsuario(usuario);
        layout.getChildren().setAll(root);
    }

    // Metodo activado a desde la escena mediante un boton
    //  Con este metodo, se cierra la aplicacion
    @FXML
    public void botonSalir() throws IOException {
        // Se crea una alerta de confirmación para que el usuario este al tanto
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("Se perderán los datos de las comandas que no se hayan confirmado. ¿Quieres continuar?");
        // Se obtiene la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, cerrar la aplicación
            System.exit(0);
        } else {
            // Si el usuario cancela, simplemente se cierra la alerta
            alert.close();
        }
    }

    // Metodo activado a desde la escena mediante un boton
    @FXML
    public void pantallaDetalle() throws IOException {
        // Se carga el archivo FXML de la ventana de DetallesComanda
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("DetallesComanda.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    // Método para inicializar el usuarioId, si con la consulta de esAdmin se verifica que es  rol "admin" se habilita para su uso el boton "auditoria"
    public void inicializar(int usuarioId) {
        this.usuarioId = usuarioId;
        if (esAdmin(usuarioId)) {
            auditoria.setDisable(false);
        }
    }

    //  Verificacion sobre el rol del usuario logeado en la aplicacion, si user loegado tiene el rol "admin" se habilita el requerido en el metodo inicializar
    private boolean esAdmin(int usuarioId) {
        try {
            Connection connection = getConnection(); // Se obtiene la conexión a la base de datos
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
