package com.example.espritindoor.ViewModel;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.espritindoor.ActivityMore;
import com.example.espritindoor.Adapters.Adapter_Friend_profil;
import com.example.espritindoor.Adapters.ImageAdapter;
import com.example.espritindoor.Full_View;
import com.example.espritindoor.Model.user;
import com.example.espritindoor.MovieViewModel;
import com.example.espritindoor.R;
import com.example.espritindoor.URL.Url;
import com.example.espritindoor.databinding.FragmentProfilFragementBinding;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.util.Log.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragement extends Fragment {
    ArrayList<Integer> image ;
    MovieViewModel movieViewModel ;
    String id ;
    int profile  , background;
    String url = Url.MonURL();

    public ProfileFragement(int a , int b ,String c ) {
        this.profile = a;
        this.background = b ;
        this.id = c ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         //View v = inflater.inflate(R.layout.fragment_profil_fragement, container, false);

        FragmentProfilFragementBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil_fragement, container, false);
        View view = binding.getRoot();

            binding.goMap.setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View v) {
                     Intent a = new Intent(getActivity(), TrackFriend.class);

                     startActivity(a);
             }
                    });

            binding.goChats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.flContent , new ColleagueFragement(id));
                    fr.commit();

                }
            });




        Retrofit retro = retrofit.getInstance();
        ApiInterface apiInterface = retro.create(ApiInterface.class);
        Call<user> call = apiInterface.GetLesDemande(id);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

                binding.nomUser.setText(response.body().getUserName());
                binding.mailUser.setText(response.body().getEmail());
                //binding.imgProfil
                Picasso.get()
                        .load(url+"/images/"+(response.body().getImageProfil()))
                        .into(binding.imgProfil);

                //background_img
                Picasso.get()
                        .load(url+"/images/"+(response.body().getBackground()))
                        .into(binding.backgroundImg);
            }

            @Override
            public void onFailure(Call<user>  call, Throwable t) {
                d("/////", "**************mainActicity***************" +t.getMessage());

            }
        });








        binding.fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), ActivityMore.class);
                a.putExtra("id",id);
                startActivity(a);

            }
        });

        binding.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.flContent , new ColleagueFragement(id));
                fr.commit();


            }
        });

        if(profile == 0 )
            binding.imgProfil.setImageResource(R.drawable.walid);
        else
            binding.imgProfil.setImageResource(profile);


        if(background == 0 )
            binding.backgroundImg.setImageResource(R.drawable.a);
        else
            binding.backgroundImg.setImageResource(background);









        movieViewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);


        binding.setModelView(movieViewModel);
        movieViewModel.getlist_Friend();
        final Adapter_Friend_profil adapter = new Adapter_Friend_profil();
        binding.Recycle1.setAdapter(adapter);
        movieViewModel.mutableLiveData.observe(getActivity(), new Observer<ArrayList<user>>() {
            @Override
            public void onChanged(ArrayList<user> users) {
                adapter.setList(users);
            }
        });
        binding.Recycle1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        binding.setLifecycleOwner(this);



        image = new ArrayList<>(Arrays.asList(
                R.drawable.walid,R.drawable.cc,R.drawable.bb,R.drawable.ic_user,R.drawable.unnamed,R.drawable.a,R.drawable.bb,R.drawable.walid,R.drawable.ic_user
        ));

        binding.Grid.setAdapter(new ImageAdapter(image, getActivity()));

        binding.Grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int item_post = image.get(position);
                ShoxDialogBox(item_post);
            }
        });

        return view;
    }
    public  void ShoxDialogBox (int item_pos)
    {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialogg);
        TextView name_image = dialog.findViewById(R.id.txt_Image_name);
        ImageView Image  = dialog.findViewById(R.id.img);
        ImageButton btn_full = dialog.findViewById(R.id.btn_full);
        ImageButton  btn_Closs = dialog.findViewById(R.id.btn_close);



        String title = getResources().getResourceName(item_pos);

        // extracting name
        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        name_image.setText(name);

        Image.setImageResource(item_pos);

        btn_Closs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Full_View.class);
                intent.putExtra("img",item_pos);
                startActivity(intent);

            }
        });

        dialog.show();

    }

}
