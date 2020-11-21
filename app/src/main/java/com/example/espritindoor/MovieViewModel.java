package com.example.espritindoor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.espritindoor.Model.user;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {

    public MutableLiveData<ArrayList<user>> mutableLiveData = new MutableLiveData<>();



    public void getlist_Friend()
    {
        mutableLiveData.setValue(getFriend());
    }




    public ArrayList<user> getFriend()
    {
        ArrayList<user> posts = new ArrayList<>();
        posts.add(new user("Tunis", R.drawable.ic_user));
        posts.add(new user("russia", R.drawable.walid));
        posts.add(new user("france", R.drawable.unnamed));
        posts.add(new user("spain", R.drawable.a));
        posts.add(new user("spain", R.drawable.bb));
        posts.add(new user("united king dom", R.drawable.cc));


        return posts;
    }
}
