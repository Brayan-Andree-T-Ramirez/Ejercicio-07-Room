package com.upiiz.dm_batrb_07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_batrb_07.data.db.CrudVeterinario;
import com.upiiz.dm_batrb_07.data.entities.Veterinario;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCorreo, etContrasenia, etNombre;
    TextView tvIngresar;
    CheckBox cbTerminos;
    Button btnContinuar;
    CrudVeterinario crudVeterinario;
    Veterinario veterinario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etCorreo = findViewById(R.id.etCorreoRegistro);
        etContrasenia = findViewById(R.id.etContraseniaRegistro);
        etNombre = findViewById(R.id.etNombreaRegistro);
        cbTerminos = findViewById(R.id.cbAceptarTerminos);
        btnContinuar = findViewById(R.id.btnContinuarRegistro);
        tvIngresar = findViewById(R.id.tvIngresar);

        btnContinuar.setOnClickListener(this);
        tvIngresar.setOnClickListener(this);
        crudVeterinario = new CrudVeterinario(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnContinuarRegistro){

            String correo = etCorreo.getText().toString().trim();
            String contrasenia = etContrasenia.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();

            if(correo.isEmpty()){
                etCorreo.setError("El correo es obligatorio");
                etCorreo.requestFocus();
                return;
            }

            if(contrasenia.isEmpty()){
                etContrasenia.setError("La contraseña es obligatoria");
                etContrasenia.requestFocus();
                return;
            }

            if(nombre.isEmpty()){
                etNombre.setError("El nombre es obligatorio");
                etNombre.requestFocus();
                return;
            }

            if (!cbTerminos.isChecked()) {
                cbTerminos.setError("Debes aceptar los términos");
                Toast.makeText(this,
                        "Debes marcar la casilla para continuar",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            veterinario = new Veterinario(null,nombre,correo,contrasenia);
            crudVeterinario.agregar(veterinario);

            Toast.makeText(this,
                    "Usuario registrado exitosamente",
                    Toast.LENGTH_SHORT).show();

            finish();

        }else{
            finish();
        }
    }
}