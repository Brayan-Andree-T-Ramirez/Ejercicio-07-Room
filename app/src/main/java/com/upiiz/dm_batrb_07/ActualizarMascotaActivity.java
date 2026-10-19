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

public class ActualizarMascotaActivity extends AppCompatActivity implements View.OnClickListener {

    Long idVeterinario;
    Long idMascota;

    Mascota mascotaActual;
    TextView tvNombreVeterinario;
    Button btnActualizar, btnRegresarListado;
    EditText etNombre,etRaza, etEdad;
    CrudMascota crudMascota;
    CrudVeterinario crudVeterinario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_mascota);


        idVeterinario = getIntent().getLongExtra("idVeterinario",-1L);
        idMascota = getIntent().getLongExtra("idMascota", -1L);

        //Valida que se hayan recibido correctamente los datos
        if (idVeterinario == -1L) {
            Toast.makeText(this,
                    "No se recibió el ID del veterinario",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(idMascota == -1L){
            Toast.makeText(this, "No se recibió el ID de la mascota",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etNombre = findViewById(R.id.etNombreActualizar);
        etRaza = findViewById(R.id.etRazaActualizar);
        etEdad = findViewById(R.id.etEdadActualizar);

        tvNombreVeterinario = findViewById(R.id.tvNombreVeterinarioActualizarMascota);

        btnActualizar = findViewById(R.id.btActualizar);
        btnRegresarListado = findViewById(R.id.btnRegresarListado);

        crudVeterinario = new CrudVeterinario(this);
        crudMascota = new CrudMascota(this);

        cargarNombreVeterinario();
        cargarValoresMascota();

        btnActualizar.setOnClickListener(this);
        btnRegresarListado.setOnClickListener(this);
    }

    private void cargarValoresMascota() {
        crudMascota.obtenerPorId(idMascota, mascota -> {
            if (mascota != null) {
                mascotaActual = mascota;
                etNombre.setText(mascota.getNombre());
                etEdad.setText(String.valueOf(mascota.getEdad()));
                etRaza.setText(mascota.getRaza());
            }
        });
    }

    private void cargarNombreVeterinario(){
        crudVeterinario.obtenerPorId(idVeterinario,veterinario -> {
            String nombreVeterinario = veterinario.getNombre();
            tvNombreVeterinario.setText(nombreVeterinario);
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btActualizar){
            String nombreMascota = etNombre.getText().toString();
            String razaMascota = etRaza.getText().toString();
            String edadMascota = etEdad.getText().toString();

            if(nombreMascota.isEmpty()){
                etNombre.setError("El nombre es obligatorio");
                etNombre.requestFocus();
                return;
            }

            if (razaMascota.isEmpty()){
                etRaza.setError("La raza es obligatoria");
                etRaza.requestFocus();
                return;
            }

            if (edadMascota.isEmpty()) {
                etEdad.setError("La edad es obligatoria");
                etEdad.requestFocus();
                return; // Detiene la ejecución para que no truene
            }

            int edad = Integer.parseInt(edadMascota);

            mascotaActual.setNombre(nombreMascota);
            mascotaActual.setRaza(razaMascota);
            mascotaActual.setEdad(edad);

            crudMascota.actualizar(mascotaActual);
            Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            finish();
        }
    }
}