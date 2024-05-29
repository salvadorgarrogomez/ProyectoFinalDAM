package app_pcproyecto_2.pkg1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class APP_PcProyecto_21 extends Application {

    //  Datos de acceso al entorno servidor, no se establece de forma estatica, sino en base a los datos ingresados por el usuario
    static String URL;
    static String USUARIO;
    static String CONTRASEÑA;
    //  Creacion de un archvio de configuracion donde se guardaran los datos y credenciales de acceso al servidor, para dar algo mas de seguridad (no estan cifrados)
    //  el archivo se guarda en la raiz del usuario del sistema, no en el directorio de la aplicacion
    private static final String CONFIG_FILE = System.getProperty("user.home") + File.separator + ".config.properties";

    // Al iniciar el programa, se cargara por defecto la escena fxml indicada, de tal forma que el usuario pueda colocar de forma dinamica el servidor de trabajo
    @Override
    public void start(Stage stage) throws Exception {
        // Se cargan los datos del servidor desde un archivo de propiedades
        cargarConfiguracion();
        // Se muestran la ventana de configuración del servidor
        Parent root = FXMLLoader.load(getClass().getResource("DireccionServidor.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Configuración del Servidor");
        stage.show();
    }

    //  Metodo para cargar los datos guardados en local de las credenciales de acceso al servidor, de tal forma que los datos se cargaran en las celdas de la
    //  escena para que usuario no tenga que introducir los datos siempre que habra la aplicacion
    public static void cargarConfiguracion() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            Properties propiedades = new Properties();
            try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
                propiedades.load(input);
                URL = propiedades.getProperty("url");
                USUARIO = propiedades.getProperty("usuario");
                CONTRASEÑA = propiedades.getProperty("contrasena");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //  Metodo para guardar los datos de acceso al servidor la primera vez que el usuario los introduzca
    public static void guardarConfiguracion(String url, String usuario, String contrasena) {
        Properties propiedades = new Properties();
        propiedades.setProperty("url", url);
        propiedades.setProperty("usuario", usuario);
        propiedades.setProperty("contrasena", contrasena);
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            propiedades.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Conexion con el servidor, de aqui tiran y heredan todas las conexiones de la aplicacion, haciendo asi que se reutilice en todo momento y no se sature el sistema
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage() + "\nEquipo servidor, ¿apagado o encendido?. Revisar.\nSi para acceder has ingresado por primera vez los datos de acceso al servidor, reinicia.", "Error", JOptionPane.ERROR_MESSAGE);
            throw ex;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
