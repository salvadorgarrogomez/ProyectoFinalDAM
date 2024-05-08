package app_pcproyecto_2.pkg0;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class APP_PcProyecto_20 extends Application {

    private static final String URL = "jdbc:postgresql://192.168.1.138:5432/Bar_ElEscobar_2.0";
    private static final String USUARIO = "postgres";
    private static final String CONTRASEÑA = "12345";

    @Override
    public void start(Stage stage) throws Exception {
        // Obtener la conexión a la base de datos
        Connection connection = getConnection();

        // Verificar si la conexión es nula antes de continuar
        if (connection == null) {
            // Mostrar un mensaje de error y salir de la aplicación si no se puede establecer la conexión
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        // Cargar el archivo FXML de la ventana de Cambio de Usuario
        Parent cambioUsuario = FXMLLoader.load(getClass().getResource("CambioUsuario.fxml"));
        Scene cambioUsuarioScene = new Scene(cambioUsuario);
        stage.setScene(cambioUsuarioScene);
        stage.setTitle("Cambio de Usuario");
        stage.show();
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage() + "\nEquipo servidor, ¿apagado o encendido? \nRevisar.", "Error", JOptionPane.ERROR_MESSAGE);
            throw ex; 
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
