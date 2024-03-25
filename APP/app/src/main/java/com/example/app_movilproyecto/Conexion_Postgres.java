package com.example.app_movilproyecto;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_Postgres {

    Connection conexion = null;

    // Creamos nuestra funcion para Conectarnos a Postgresql
    public Connection conexionBD() throws SQLException, ClassNotFoundException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://192.168.56.1:5432/Bar_ElEscobar";
        String user = "bar_Admin";
        String password = "Admin123456";
        conexion = DriverManager.getConnection(url, user, password);
        return conexion;
    }

    // Creamos la funcion para Cerrar la Conexion
    protected void cerrar_conexion(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

