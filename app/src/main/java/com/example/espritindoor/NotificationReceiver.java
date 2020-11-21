package com.example.espritindoor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.espritindoor.Model.user;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.util.Log.d;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");
        d("//////////////", "/////////"+message);
        String id = intent.getStringExtra("id_user");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


        Retrofit retro = retrofit.getInstance();
        ApiInterface apiInterface = retro.create(ApiInterface.class);
        Call<user> call = apiInterface.Demandeaccepter(id ,message);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {


                d("test*********", "c bon ://///////////// ");



            }

            @Override
            public void onFailure(Call<user>  call, Throwable t) {
                d("/////", "**************reponce***************" +t.getMessage());

            }
        });
    }
}
