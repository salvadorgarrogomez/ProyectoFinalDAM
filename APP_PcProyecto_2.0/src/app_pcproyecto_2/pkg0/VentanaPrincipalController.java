// VentanaPrincipalController.java
package app_pcproyecto_2.pkg0;

import constructores.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class VentanaPrincipalController implements Initializable {

    private int usuarioId;

    @FXML
    Pane layout;
    @FXML
    private Button buttonUsuario;
    private Mesas_ComandasController mesasController;
    private ObservableList<StringProperty> textAreaData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesasController = new Mesas_ComandasController();
        textAreaData = FXCollections.observableArrayList();
        mesasController.setTextAreaData(textAreaData);
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
            stage.show();

            // Cerrar la ventana principal si se abrió desde el botón en VentanaPrincipal
            if (controller.isPermitirCerrarVentanaPrincipal()) {
                Stage mainWindow = (Stage) buttonUsuario.getScene().getWindow();
                mainWindow.close();
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
    public void pantallaMesas() throws IOException {

        // Obtener el texto de los TextArea antes de cambiar de escena
        // Cargar el archivo FXML de la ventana de Mesas y Comandas
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Mesas_Comandas.fxml"));
        Parent mesas = loader.load();

        // Obtener el controlador del FXML cargado
        Mesas_ComandasController controller = loader.getController();

        // Establecer el usuario en el controlador
        Usuarios usuario = new Usuarios();
        usuario.setId(usuarioId);
        controller.setUsuario(usuario);
        controller.setTextAreaData(textAreaData);

        // Cambiar a la escena de Mesas y Comandas
        layout.getChildren().setAll(mesas);
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
        // Realiza cualquier inicialización adicional necesaria aquí
    }

    @FXML
    public void pantallaDetalle() throws IOException {

        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        Pane inicioImagen = FXMLLoader.load(getClass().getResource("DetallesComanda.fxml"));
        layout.getChildren().setAll(inicioImagen);

    }

    public void setMesasController(Mesas_ComandasController mesasController) {
        this.mesasController = mesasController;
    }

}
