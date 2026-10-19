package com.upiiz.dm_batrb_07;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_batrb_07.data.db.CrudMascota;
import com.upiiz.dm_batrb_07.data.db.CrudVeterinario;
import com.upiiz.dm_batrb_07.data.entities.Mascota;

import java.util.ArrayList;
import java.util.List;

public class ListadoMascotasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    //Recuperar el id del veterinario
    Long idVeterinario;

    ArrayAdapter<String> adapter;

    Button btnSalir;
    ImageButton btnAgregarMascota;

    EditText etBuscarMascota;

    TextView tvNombreListado;

    ListView lvMascotas;

    CrudVeterinario crudVeterinario;
    CrudMascota crudMascota;

    private List<Mascota> listaMascotas;
    private List<String> listaNombresMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        idVeterinario = getIntent().getLongExtra("idVeterinario",-1L);

        if (idVeterinario == -1L) {
            Toast.makeText(this,
                    "No se recibió el ID del veterinario",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //Mapeo de variables

        lvMascotas = findViewById(R.id.lvMascotas);

        etBuscarMascota = findViewById(R.id.etBuscarMascota);

        tvNombreListado = findViewById(R.id.tvNombreListado);

        btnAgregarMascota = findViewById(R.id.btnAgregarMascota);
        btnSalir = findViewById(R.id.btnSalir);

        crudMascota = new CrudMascota(this);
        crudVeterinario = new CrudVeterinario(this);

        lvMascotas.setOnItemClickListener(this);
        lvMascotas.setOnItemLongClickListener(this);
        btnAgregarMascota.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        etBuscarMascota.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s); // Filtra el ListView en tiempo real
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosVeterinario();
    }

    private void cargarDatosVeterinario() {
        //Recuperar el veterinario
        crudVeterinario.obtenerPorId(idVeterinario, veterinario -> {
            if (veterinario == null) {
                Toast.makeText(this,
                        "Veterinario no encontrado",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            tvNombreListado.setText(veterinario.getNombre());
        });

        crudMascota.obtenerMascotasPorVeterinario(idVeterinario,mascotas -> {
            listaMascotas = new ArrayList<>(mascotas);
            listaNombresMascotas = new ArrayList<>();
            mascotas.forEach((m)->{
                listaNombresMascotas.add(m.getNombre());
            });

            adapter = new ArrayAdapter<>(this, R.layout.item_mascota,R.id.tvNombreMascota ,listaNombresMascotas);
            lvMascotas.setAdapter(adapter);
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Mascota mascota = listaMascotas.get(position);

        Intent intentActualizar = new Intent(this, ActualizarMascotaActivity.class);
        intentActualizar.putExtra("idVeterinario",idVeterinario);
        intentActualizar.putExtra("idMascota",mascota.getId());
        startActivity(intentActualizar);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Mascota mascota = listaMascotas.get(position);

        new AlertDialog.Builder(this).setTitle("Eliminar: "+mascota.getNombre())
                .setMessage("¿Estás seguro que deseas eliminar esta mascota?")
                .setPositiveButton("Aceptar",(dialog,which)->{
                    crudMascota.eliminar(mascota);
                    Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
                    cargarDatosVeterinario();

                }).setNegativeButton("Cancelar", null).show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSalir){
            finish();
        }else{
            Intent intentAgregarMascota = new Intent(this, AgregarMascotaActivity.class);
            intentAgregarMascota.putExtra("idVeterinario", idVeterinario);
            startActivity(intentAgregarMascota);
        }
    }
}