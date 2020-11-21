package com.example.espritindoor.ViewModel;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Adapters.Adapter_List_Friend;
import com.example.espritindoor.FriendViewModel;
import com.example.espritindoor.Model.Friend;
import com.example.espritindoor.Model.user;
import com.example.espritindoor.R;
import com.example.espritindoor.databinding.FragmentColleagueFragementBinding;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.util.Log.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColleagueFragement extends Fragment {

    FriendViewModel friendViewModel ;
    RecyclerView recyclerView ;


    public String Userid ;
    Context context ;

    public ColleagueFragement(String a) {
        // Required empty public constructor
        this.Userid = a ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_colleague_fragement, container, false);
        // Inflate the layout for this fragment
        FragmentColleagueFragementBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_colleague_fragement, container, false);
        View v = binding.getRoot();

        // Inflate the layout for this fragment

        friendViewModel = new ViewModelProvider(getActivity()).get(FriendViewModel.class);
        friendViewModel.getFriend();
        recyclerView = binding.ContactFriend;
        Adapter_List_Friend adapter_list_friend = new Adapter_List_Friend();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_list_friend);


        friendViewModel.FriendMutableLiveData.observe(getActivity(), new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {

                adapter_list_friend.setList((ArrayList<Friend>) friends );



                binding.search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ArrayList<Friend> recherche = new ArrayList<>();


                        for (int i = 0 ; i< friends.size() ; i++)
                        {
                            String a =friends.get(i).getUsernameF();
                            //item.getText1().toLowerCase().contains(text.toLowerCase())
                            if(a != null && a.toLowerCase().contains(s.toString().toLowerCase()))
                            {
                                recherche.add(friends.get(i));
                                d("c bon ", "oui c bon");

                            }

                        }


                        if(s.length()==0)
                            adapter_list_friend.setList((ArrayList<Friend>) friends);
                        else
                            adapter_list_friend.setList(recherche);



                    }
                });


            }
        });



        // ajouter firend

        adapter_list_friend.setonItemClick(new Adapter_List_Friend.ItemClickListene() {
            @Override
            public void ajouterFriend(int position) {
                String a = friendViewModel.FriendMutableLiveData.getValue().get(position).get_id();
               // Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
                Retrofit retrofi = retrofit.getInstance();


                ApiInterface apiInterface = retrofi.create(ApiInterface.class);
                              System.out.println("wxsqqsdqssdddddddddddddddddddddddddqs"+Userid);
                Call<user> call = apiInterface.SetDemade(Userid,a);

                call.enqueue(new Callback<user>() {
                    @Override
                    public void onResponse(Call<user> call, Response<user> response) {

                        System.out.println("envoiyer  ssdsqdqsq");
                        //System.out.println("" + response.body());
                        Toast.makeText(getActivity(),Userid, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<user> call, Throwable t) {
                        d("***", "*********MyFriendVM***************" + t.getMessage());

                    }
                });

            }
        });




        return v;
    }


}
