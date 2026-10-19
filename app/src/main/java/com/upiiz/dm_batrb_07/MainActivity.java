package com.upiiz.dm_batrb_07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_batrb_07.data.db.CrudMascota;
import com.upiiz.dm_batrb_07.data.db.CrudVeterinario;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegistrar, btnIngresar;
    EditText etCorreo, etContrasenia;
    //Una instancia - Crud Mascotas
    CrudVeterinario crudVeterinario;
    CrudMascota crudMascota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        crudVeterinario = new CrudVeterinario(this);
        crudMascota = new CrudMascota(this);


        btnRegistrar.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);

        /*
        Son las notas, las deje porque si me sirvieron
        //Se va a segundo plao y ya no regresa
        Mascota mascota = new Mascota(null, "Polo","Gran perro",58,2L);
        crudMascota.agregar(mascota);
        //Se va a segundo plano, termina el proceso y ya no regresa
        mascota.setId(20L);
        crudMascota.eliminar(mascota);

        //Se va a segundo plano, termina el proceso y ya no regresa
        mascota.setEdad(6);
        crudMascota.actualizar(mascota);

        //Se va a segundo plano, termina el proceso y con la interfaz regresa las mascotas
        //Devuelve las mascotas a primer plano o a la UI - Hilo principal
        crudMascota.obtenerTodas(mascotas -> {
            //Agregar el listado de mascotas a un ListView
            mascotas.forEach((pet)->{
                System.out.println(pet.getNombre());
            });
        });

        crudMascota.obtenerPorId(mascota.getId(), pet->{
            //Si encuentra a la amscota - Firulais
            //Si no encuentra la mascota, regresa null
            if(pet!=null){
                System.out.println();
            }
            System.out.println(pet.getNombre());
        });

        //Se va a segundo plano, termina el proceso y con la interfaz regresa a las mascota a
        //primer plano o a la UI o Hilo principal -Main Looper
        */

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegistrar){
            Intent intentRegistro = new Intent(this, RegistroActivity.class);
            borrarCampos();
            startActivity(intentRegistro);
        }else{
            String correo = etCorreo.getText().toString().trim();
            String contrasenia = etContrasenia.getText().toString().trim();

            if(correo.isEmpty()){
                etCorreo.setError("El correo es obligatorio");
                etContrasenia.requestFocus();
                return;
            }

            if(contrasenia.isEmpty()){
                etContrasenia.setError("La contraseña es obligatoria");
                etContrasenia.requestFocus();
                return;
            }
            crudVeterinario.obtenerPorCorreo(correo,veterinario ->
            {
                if (veterinario==null){
                    Toast.makeText(this,
                            "Veterinario no encontrado",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(correo.equals(veterinario.getCorreo())  && contrasenia.equals(veterinario.getContrasenia())){
                    Intent intentListado = new Intent(this, ListadoMascotasActivity.class);
                    intentListado.putExtra("idVeterinario",veterinario.getId());
                    borrarCampos();
                    startActivity(intentListado);
                }else{
                    Toast.makeText(this,
                            "Correo o contraseña inválidos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

            });
        }
    }

    private void borrarCampos(){
        etCorreo.setText("");
        etContrasenia.setText("");
    }
}