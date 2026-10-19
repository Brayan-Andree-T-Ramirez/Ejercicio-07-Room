package com.upiiz.dm_batrb_07.data.db;

import android.content.Context;
import android.os.Looper;
import android.os.Handler;

import androidx.room.Room;

import com.upiiz.dm_batrb_07.data.entities.Mascota;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrudMascota {
    //Manejar el CRUD
    //Instanciar la db
    //Usar hilos para realizar CRUD
    AppDataBase db;

    //Son operaciones de lectura y escritura
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CrudMascota(Context context){
        db= Room.databaseBuilder(context, AppDataBase.class,"veterinaria").build();
    }

    public void agregar(Mascota mascota){
        executorService.execute(()->{
            //Mandamos a segundo plano la operacion de IO (Input - Output)
            db.mascotaDao().crear(mascota);
        });

    }

    public void actualizar(Mascota mascota){
        executorService.execute(()->{
            db.mascotaDao().actualizar(mascota);
        });

    }

    public void eliminar(Mascota mascota){
        executorService.execute(()->{
            db.mascotaDao().eliminar(mascota);
        });
    }

    //Pendiente
    public void obtenerTodas(OnMascotasLoadedListener onCategoriasLoadadListener){

        //Segundo plano - Hilo secundario - Lo que no sea UI
        executorService.execute(()->{

            List<Mascota> mascotas = db.mascotaDao().obtenerTodas();

            //Si vamos aqui, ya tengo las mascotas
            //Requiero regresarlas a primer plano - Hilo principal - UI
            new Handler(Looper.getMainLooper()).post(()->{
                //Regresa las mascotas
                onCategoriasLoadadListener.onComplete(mascotas);
            });
        });
    }

    //Pendiente
    public void obtenerPorId(Long id, OnMascotaLoadedListener onCategoriaLoadedListener){
        executorService.execute(()->{
            Mascota mascota= db.mascotaDao().obtenerPorId(id);

            new Handler(Looper.getMainLooper()).post(()->{
               onCategoriaLoadedListener.onComplete(mascota);
            });
        });
    }

    public void obtenerMascotasPorVeterinario(Long idVeterinario,OnMascotasLoadedListener listener){
        executorService.execute(()->{
            List<Mascota> mascotas = db.mascotaDao().obtenerMascotasVeterinario(idVeterinario);
            new Handler(Looper.getMainLooper()).post(()->{
                listener.onComplete(mascotas);
            });
        });
    }

    //Interfaz para obtenerTodos
    public interface OnMascotaLoadedListener{
        void onComplete(Mascota mascota);
    }

    public interface OnMascotasLoadedListener{
        void onComplete(List<Mascota> mascota);
    }

    //Interfaz para obtenerUna
}
