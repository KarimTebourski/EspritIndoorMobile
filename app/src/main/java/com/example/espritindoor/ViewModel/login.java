package com.example.espritindoor.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.espritindoor.MainActivity;
import com.example.espritindoor.Model.user;
import com.example.espritindoor.R;
import com.example.espritindoor.databinding.ActivityLoginBinding;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.util.Log.d;

public class login extends AppCompatActivity {

    TextView T, T2;
    String EMAIL, PASS;
    Context context;
    ImageView cccc;
    View mview;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String id ;
    String username;

    //SharedPreferences.Editor editor = getSharedPreferences("userdata",MODE_PRIVATE).edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        T = findViewById(R.id.Email);
        T2 = findViewById(R.id.password2);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        /*Intent intent = new Intent(login.this , MainActivity.class);
        startActivity(intent)*/
        ;


    }

    public void login(View view) {
        EMAIL = T.getText().toString();
        PASS = T2.getText().toString();
        Log.d("***", "*******" + PASS);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.20:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<user> call = apiInterface.GetUser(EMAIL, PASS);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

                if (response.body() != null) {
                    if (EMAIL.equals(response.body().getEmail()) && response.body().getEtat() == true) {
                       // createSassion(response.body().getEmail());
                        id = response.body().get_id() ;
                        username = response.body().getUserName();
                        System.out.println("mil login :"+id);
                        System.out.println("mil login :"+username);
                        editor.putString("id", response.body().get_id());
                        editor.putString("username",response.body().getUserName());
                        editor.apply();
                       // Log.d("sssss", "onResponse: ");
                        Intent A = new Intent(login.this, MainActivity.class);
                        A.putExtra("id",id);
                        A.putExtra("username",username);
                        startActivity(A);

                    }
                }

            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {

                Log.d("***", "************************" + t.getMessage());
            }
        });
    }

    public void registre(View view) {
        Intent intent = new Intent(login.this, registre.class);
        startActivity(intent);
    }

    public void createSassion(String email) {

        Retrofit retrofi = retrofit.getInstance();
        ApiInterface apiInterface = retrofi.create(ApiInterface.class);
        Call<user> call = apiInterface.saisson(email);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

                System.out.println("mabroukkkk  ssdsqdqsq");
                System.out.println("" + response.body().get_id());
                    id = response.body().get_id();
//                System.out.println("*******"+response.body().toString());


            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                d("***", "*********MyFriendVM***************" + t.getMessage());

            }
        });
    }
}

