package com.upiiz.dm_batrb_07;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_batrb_07.data.db.CrudMascota;
import com.upiiz.dm_batrb_07.data.db.CrudVeterinario;
import com.upiiz.dm_batrb_07.data.entities.Mascota;

public class AgregarMascotaActivity extends AppCompatActivity implements View.OnClickListener {

    Long idVeterinario;
    TextView tvNombreVeterinario;
    EditText etNombreMascota, etRaza, etEdad;
    Button btnGuardar, btnRegresarListado;
    CrudMascota crudMascota;
    CrudVeterinario crudVeterinario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        idVeterinario = getIntent().getLongExtra("idVeterinario",-1L);

        if (idVeterinario == -1L) {
            Toast.makeText(this,
                    "No se recibió el ID del veterinario",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        crudMascota = new CrudMascota(this);
        crudVeterinario = new CrudVeterinario(this);

        etNombreMascota = findViewById(R.id.etNombreMascota);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);

        tvNombreVeterinario = findViewById(R.id.tvNombreVeterinarioGuardarMascota);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresarListado = findViewById(R.id.btnRegresarListado);

        cargarNombreVeterinario();

        btnGuardar.setOnClickListener(this);
        btnRegresarListado.setOnClickListener(this);
    }

    private void cargarNombreVeterinario(){
        crudVeterinario.obtenerPorId(idVeterinario,veterinario -> {
            String nombreVeterinario = veterinario.getNombre();
            tvNombreVeterinario.setText(nombreVeterinario);
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnGuardar){
            String nombreMascota = etNombreMascota.getText().toString().trim();
            String razaMascota = etRaza.getText().toString().trim();
            String edadMascota = etEdad.getText().toString().trim();;

            if(nombreMascota.isEmpty()){
                etNombreMascota.setError("El nombre de la mascota es obligatorio");
                etNombreMascota.requestFocus();
                return;
            }

            if (edadMascota.isEmpty()) {
                etEdad.setError("La edad es obligatoria");
                etEdad.requestFocus();
                return; // Detiene la ejecución para que no truene
            }


            if (razaMascota.isEmpty()){
                etRaza.setError("La raza de la mascota es obligatoria");
                return;
            }

            int edad = Integer.parseInt(edadMascota);
            Mascota mascota = new Mascota(null, nombreMascota,razaMascota,edad,idVeterinario);

            crudMascota.agregar(mascota);
            finish();

        }else{
            finish();
        }

    }
}