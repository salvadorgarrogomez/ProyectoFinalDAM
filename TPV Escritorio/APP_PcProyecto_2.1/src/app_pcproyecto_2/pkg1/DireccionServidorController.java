package app_pcproyecto_2.pkg1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class DireccionServidorController implements Initializable {

    @FXML
    private PasswordField url;
    @FXML
    private TextField nombre;
    @FXML
    private PasswordField contra;
    @FXML
    private Button entrar;
    @FXML
    private Button salir;

    //  Se inicializan los textfield y botones para dar funcionamiento a la escena
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cargar la configuración guardada si existe
        url.setText(APP_PcProyecto_21.URL);
        nombre.setText(APP_PcProyecto_21.USUARIO);
        contra.setText(APP_PcProyecto_21.CONTRASEÑA);
        entrar.setOnAction(event -> handleEntrar());
        salir.setOnAction(event -> handleSalir());
    }

    //  Metodo activado desde la escena, en este caso, en lugar de hacerlo y asociar el FXML desde la interfaz, lo he realizado desde codigo y no asociando en el
    //  scenebuilder, ambas formas son validas y funcionan
    private void handleEntrar() {
        //  Se escribe en los textfield los datos de acceso al servidor, d esta forma se hace mas dinamico y da muchas mas opciones en lugar de que fuera estatico
        //  en el caso de migracion de base de datos o cambio de servidor, es mas efectivo de sta forma que a establecerlo en el codigo de forma estatica
        String servidorUrl = url.getText();
        String usuario = nombre.getText();
        String contraseña = contra.getText();
        // Se guardan los datos de configuración, para que estos aparezcan por defectos una vez se han establecido
        //  De esta forma el usuario no tendra que añadirlos siempre que inicie sesion, solo cuando haya que modificarlos, se hace llamada al metodo guardar
        //  en el main, para que se queden guardados a nivel interno y asi cuando se inicialice la app, estos apareceran por pantalla
        APP_PcProyecto_21.guardarConfiguracion(servidorUrl, usuario, contraseña);
        // Con los datos anteriores, se establece la conexión
        try {
            Connection connection = DriverManager.getConnection(servidorUrl, usuario, contraseña);
            if (connection != null) {
                // Se cierra la ventana actual y abrir la siguiente ventana en caso de conexion exitosa
                Stage stage = (Stage) entrar.getScene().getWindow();
                stage.close();
                // Se carga la ventana principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CambioUsuario.fxml"));
                Parent root = loader.load();
                Stage mainStage = new Stage();
                mainStage.setScene(new Scene(root));
                mainStage.setTitle("Cambio de Usuario");
                mainStage.show();
            }
        } catch (SQLException ex) {
            //  En caso de error en los datos de acceso proporcionados, se muestra un mensaje de error al usuario
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage() + "\nDatos de acceso incorrectos, revisar con un Administrador.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Metodo de activacion del boton para salir de la aplicacion
    private void handleSalir() {
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
