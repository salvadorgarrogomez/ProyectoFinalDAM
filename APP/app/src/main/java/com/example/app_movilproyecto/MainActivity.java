package com.example.app_movilproyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner mesa, entrantes, ensaladas, combinados, caldos, bebidas, pasta, carnes, pescados, arroces, fideua, postres;
    Connection connection;
    Toolbar toolbar;
    TextView comanda;
    Button actualizar;
    Conexion_Postgres conexionBBDD;
    List<String> datosGuardados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesa = findViewById(R.id.Mesa);
        entrantes = findViewById(R.id.entrantes);
        ensaladas = findViewById(R.id.ensaladas);
        combinados = findViewById(R.id.combinados);
        caldos = findViewById(R.id.caldos);
        bebidas = findViewById(R.id.bebidas);
        pasta = findViewById(R.id.pasta);
        carnes = findViewById(R.id.carnes);
        pescados = findViewById(R.id.pescados);
        arroces = findViewById(R.id.arroces);
        fideua = findViewById(R.id.fideua);
        postres = findViewById(R.id.postres);

        comanda = findViewById(R.id.textViewComanda);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actualizar = findViewById(R.id.btnActualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        conexionBBDD = new Conexion_Postgres();
        try {
            Connection connection = conexionBBDD.conexionBD();
            // Aquí puedes realizar operaciones con la conexión a la base de datos

            mesa.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT numero FROM mesas WHERE id = " + id);
                            if (rs.next()) {
                                String numeroMesa = rs.getString("numero");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Mesa: " + numeroMesa);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            entrantes.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                if (connection != null) {
                    try (Statement stmt = connection.createStatement()) {
                        ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM entrantes WHERE id = " + id);
                        if (rs.next()) {
                            String nombre = rs.getString("nombre");
                            String precio = rs.getString("precio");
                            TextView textViewComanda = findViewById(R.id.textViewComanda);
                            textViewComanda.setText("Entrante: " + nombre + " ," + precio);
                        }
                    } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            ensaladas.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM ensaladas WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Ensalada: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            combinados.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM combinados WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Combinados: " + nombre + " ," + precio); // Corregir "Ensalada" por "Combinado"
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Acción a realizar cuando no se selecciona ningún elemento
                    }
                });

            caldos.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM caldos WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Caldos: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            bebidas.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM bebidas WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Bebida: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            pasta.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM pastas WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Pasta: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            carnes.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM carnes WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Carne: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            pescados.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM pescados WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Ensalada: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            arroces.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM arroces WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Arroz: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            fideua.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM fideua WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Fideua: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            postres.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                    if (connection != null) {
                        try (Statement stmt = connection.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT nombre, precio FROM postres WHERE id = " + id);
                            if (rs.next()) {
                                String nombre = rs.getString("nombre");
                                String precio = rs.getString("precio");
                                TextView textViewComanda = findViewById(R.id.textViewComanda);
                                textViewComanda.setText("Postres: " + nombre + " ," + precio);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona ningún elemento
                }
            });

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error al conectar a la base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void guardarDatos() {
        String textoComanda = comanda.getText().toString();
        if (!textoComanda.isEmpty()) {
            datosGuardados.add(textoComanda);
            Toast.makeText(MainActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No hay datos para guardar", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cerrarConexion();
    }

    private void cerrarConexion() {
        if (conexionBBDD != null) {
            conexionBBDD.cerrar_conexion(connection);
        }
    }

    //  Metoddo para dar utilidad y funcionamiento al boton de menu, creado en res/menu(main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //  Con este ultimo metodo, lo que conseguimos, es que al darle a los 3 puntos de la barra de herramientas
    // nos aparezca el nombre de la pantalla secundaria a la que queremos ir.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            Intent intent = new Intent(MainActivity.this, Conjunto_mesas.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}