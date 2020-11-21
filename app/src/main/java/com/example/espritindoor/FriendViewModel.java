package com.example.espritindoor;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.espritindoor.Model.Friend;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendViewModel extends ViewModel {


   public MutableLiveData<List<Friend>> FriendMutableLiveData = new MutableLiveData<>();



    public void getFriend(){

        Retrofit retrofi = retrofit.getInstance();
        ApiInterface apiInterface = retrofi.create(ApiInterface.class);
        Call<List<Friend>> call = apiInterface.GetFriend();
        call.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {

                FriendMutableLiveData.setValue(response.body());
                Log.d("***", "///////////////////////*"+call);




            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d("***", "************************"+t.getMessage());
            }
        });




    }

}
