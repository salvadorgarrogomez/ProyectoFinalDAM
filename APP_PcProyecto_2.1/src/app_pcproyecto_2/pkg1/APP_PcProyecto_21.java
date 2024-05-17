/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package app_pcproyecto_2.pkg1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author salva
 */
public class APP_PcProyecto_21 extends Application {
    
    //  Datos necesarios para el establecimiento de la conexion a la bbdd postgresql.
    private static final String URL = "jdbc:postgresql://192.168.1.138:5432/Bar_ElEscobar_2.0";
    private static final String USUARIO = "postgres";
    private static final String CONTRASEÑA = "12345";

    @Override
    public void start(Stage stage) throws Exception {
        // Obtenemos la conexión a la bbdd postgresql
        Connection connection = getConnection();

        // Comprobacion de la conexion, si es null saca por pantalla el mensaje de error.
        if (connection == null) {
            // En caso de ser null, se muestra mensaje de error y se sale de la aplicación si no se puede establecer la conexión
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Si la establece correctamente la conexion a la bbdd, se ha definido el archivo fxml que debe de mostrarse en primera instancia, para iniciar la app
        Parent cambioUsuario = FXMLLoader.load(getClass().getResource("CambioUsuario.fxml"));
        Scene cambioUsuarioScene = new Scene(cambioUsuario);
        stage.setScene(cambioUsuarioScene);
        stage.setTitle("Cambio de Usuario");
        stage.show();
    }

    //  Metodo de establecimiento de la conexion, en caso de que de error, es decir que el server este apagado no se localice la conexion
    //  se muestra por pantalla el mensaje de error con un pequeño mensaje descriptivo del problema al usuario.
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage() + "\nEquipo servidor, ¿apagado o encendido? \nRevisar.", "Error", JOptionPane.ERROR_MESSAGE);
            //  Con esta implementacion, al darle al boton aceptar de la ventana del error, se cierra la conexion, para que esta no se quede en segundo
            //  intentando establecer la conexion de forma indefinida.
            throw ex; 
        }
    }

    //  Metodo main, de lanzamiendo de la app JavaFX
    public static void main(String[] args) {
        launch(args);
    }
    
}
