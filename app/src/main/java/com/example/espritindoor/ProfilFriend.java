package com.example.espritindoor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espritindoor.Model.user;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.util.Log.d;

public class ProfilFriend extends AppCompatActivity {
    ImageButton delete ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_friend);

       String id = getIntent().getExtras().getString("id_friend");
        String id_user = getIntent().getExtras().getString("id_user");


        delete = findViewById(R.id.delete);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retro = retrofit.getInstance();
                ApiInterface apiInterface = retro.create(ApiInterface.class);
                Call<user> call = apiInterface.DeleteFriend(id_user ,id);
                call.enqueue(new Callback<user>() {
                    @Override
                    public void onResponse(Call<user> call, Response<user> response) {


                        d("test*********", "Friend est suprimer");



                    }

                    @Override
                    public void onFailure(Call<user>  call, Throwable t) {
                        d("/////", "**************reponce*************** error" +t.getMessage());

                    }
                });
            }
        });
    }
}
