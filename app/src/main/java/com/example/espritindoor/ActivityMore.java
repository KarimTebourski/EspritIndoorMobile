package com.example.espritindoor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espritindoor.Model.user;
import com.example.espritindoor.technique.ApiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityMore extends AppCompatActivity {

    EditText A, B , C , D ;
    Button btn ;
    String id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);


        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);
        btn =  findViewById(R.id.btnUser);

        String bor = A.getText().toString();
        String Live = B.getText().toString();
        String Fro = C.getText().toString();

        Log.d("aaa", Fro);
       // int Phone = D.getText();
        id = getIntent().getExtras().getString("id");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////a etudier
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.20:3000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                HashMap<Object, Object> map = new HashMap<>();
                map.put("born", bor);
                map.put("Lives", Live);
                map.put("From",Fro);
               // map.put("Phone",Phone);

                Call<user> call = apiInterface.profiluser(id ,map);

                call.enqueue(new Callback<user>() {
                    @Override
                    public void onResponse(Call<user> call, Response<user> response) {
            Log.d("********", "er");
                    }

                    @Override
                    public void onFailure(Call<user> call, Throwable t) {
                        Log.d("/////:", " error "+ t.toString());
                    }
                });
            }
        });












    }

}

