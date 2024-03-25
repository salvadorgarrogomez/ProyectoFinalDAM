package com.example.app_movilproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Conjunto_mesas extends AppCompatActivity {

    private RecyclerView recyclerViewMesas;
    private MesaAdaptador mesaAdapter;
    private List<Mesa> mesas;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesas);

        recyclerViewMesas = findViewById(R.id.recyclerViewMesas);
        recyclerViewMesas.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mesas = new ArrayList<>();
        // Aquí debes llenar la lista de mesas con tus datos

        mesaAdapter = new MesaAdaptador(mesas);
        recyclerViewMesas.setAdapter(mesaAdapter);
    }

    //  Metoddo para dar utilidad y funcionamiento al boton de menu, creado en res/menu(main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_atras, menu);
        return true;
    }

    //  Con este ultimo metodo, lo que conseguimos, es que al darle a los 3 puntos de la barra de herramientas
    // nos aparezca el nombre de la pantalla secundaria a la que queremos ir.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.atras) {
            Intent intent = new Intent(Conjunto_mesas.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

