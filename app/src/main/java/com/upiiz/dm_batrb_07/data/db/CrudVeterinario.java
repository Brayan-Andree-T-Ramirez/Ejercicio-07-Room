package com.upiiz.dm_batrb_07.data.db;

import android.content.Context;
import android.os.Looper;
import android.os.Handler;

import androidx.room.Room;

import com.upiiz.dm_batrb_07.data.entities.Veterinario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CrudVeterinario {

    AppDataBase db;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CrudVeterinario(Context context){
        db= Room.databaseBuilder(context, AppDataBase.class,"veterinaria").build();
    }

    public  void agregar(Veterinario veterinario){
        executorService.execute(()->{
            db.veterinarioDao().crear(veterinario);
        });

    }

    public void obtenerPorId(Long id, OnVeterinarioLoadedListener listener){
        executorService.execute(()->{
            Veterinario veterinario = db.veterinarioDao().obtenerPorId(id);

            new Handler(Looper.getMainLooper()).post(()->{
                listener.onComplete(veterinario);
            });
        });
    }

    public void obtenerPorCorreo(
            String correo,
            OnVeterinarioLoadedListener listener) {

        executorService.execute(() -> {

            Veterinario veterinario =
                    db.veterinarioDao().obtenerPorCorreo(correo);

            new Handler(Looper.getMainLooper()).post(() -> {
                listener.onComplete(veterinario);
            });

        });
    }

    public interface OnVeterinarioLoadedListener{
        void onComplete(Veterinario veterinario);
    }
}
