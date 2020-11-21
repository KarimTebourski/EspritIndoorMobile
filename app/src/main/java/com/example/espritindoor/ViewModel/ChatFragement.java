package com.example.espritindoor.ViewModel;


import android.content.Intent;
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

import com.example.espritindoor.Adapters.Adapter_MyFriend;
import com.example.espritindoor.Model.Friend;
import com.example.espritindoor.MyFriendVM;
import com.example.espritindoor.ProfilFriend;
import com.example.espritindoor.R;
import com.example.espritindoor.databinding.FragmentChatFragementBinding;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragement extends Fragment {

    String id ;
    MyFriendVM myFriendVM ;

    public ChatFragement(String a) {
        // Required empty public constructor
        this.id = a ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChatFragementBinding binding=  DataBindingUtil.inflate(inflater,R.layout.fragment_chat_fragement, container, false);
        View v = binding.getRoot();

        myFriendVM = new ViewModelProvider(getActivity()).get(MyFriendVM.class);
        myFriendVM.getMyFriend(id);
        Adapter_MyFriend adapter_myFriend = new Adapter_MyFriend();
        binding.MyFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.MyFriend.setAdapter(adapter_myFriend);
        myFriendVM.MyFriendMutableLiveData.observe(getActivity(), new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {
                adapter_myFriend.setList((ArrayList<Friend>) friends);

                binding.searchF.addTextChangedListener(new TextWatcher() {
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
                            String a =friends.get(i).getNameF();
                            //item.getText1().toLowerCase().contains(text.toLowerCase())
                            if(a != null && a.toLowerCase().contains(s.toString().toLowerCase()))
                            {
                                recherche.add(friends.get(i));
                                d("c bon ", "oui c bon");

                            }

                        }


                        if(s.length()==0)
                            adapter_myFriend.setList((ArrayList<Friend>) friends);
                        else
                            adapter_myFriend.setList(recherche);



                    }
                });
            }
        });

        adapter_myFriend.setonItemClickListener(new Adapter_MyFriend.ItemClickListener() {
            @Override
            public void ItemChat(int position) {
                Intent intent = new Intent(getActivity(),Chat.class);
                startActivity(intent);
            }

            @Override
            public void ItemTrack(int position) {
                String id_Friend = myFriendVM.MyFriendMutableLiveData.getValue().get(position).get_id();

                Toast.makeText(getActivity(),id +"    "+ id_Friend ,Toast.LENGTH_LONG).show();

                Intent a = new Intent(getActivity(), TrackFriend.class);

                a.putExtra("id_friend",id_Friend );
                a.putExtra("id_user",id );


                startActivity(a);

            }

            @Override
            public void ItemFriend(int position) {

                String id_Friend = myFriendVM.MyFriendMutableLiveData.getValue().get(position).get_id();

                Toast.makeText(getActivity(),id +"    "+ id_Friend ,Toast.LENGTH_LONG).show();

                Intent a = new Intent(getActivity(), ProfilFriend.class);

                a.putExtra("id_friend",id_Friend );
                a.putExtra("id_user",id );


                startActivity(a);

            }
        });



        return v ;

    }

}
