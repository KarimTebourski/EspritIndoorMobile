package com.example.espritindoor;

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

import static android.util.Log.d;

public class MyFriendVM extends ViewModel {

    public MutableLiveData<List<Friend>> MyFriendMutableLiveData = new MutableLiveData<>();



    public void getMyFriend(String id){

        Retrofit retrofi = retrofit.getInstance();
        ApiInterface apiInterface = retrofi.create(ApiInterface.class);
        Call<List<Friend>> call = apiInterface.GetMyFriend(id);
        call.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {

                MyFriendMutableLiveData.setValue(response.body());

//                System.out.println("*******"+response.body().toString());


            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                d("***", "*********MyFriendVM***************" +t.getMessage());

            }
        });




    }

}
