package com.example.espritindoor.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Model.user;
import com.example.espritindoor.R;

import java.util.ArrayList;

public class Adapter_Friend_profil extends RecyclerView.Adapter<Adapter_Friend_profil.FriendHolder> {

private ArrayList<user> users = new ArrayList<>();


    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return  new FriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_friend,parent,false)) ;

    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
    user p = users.get(position);
    holder.T.setText(p.getUserName());

    user v = users.get(position);
        holder.I.setImageResource(v.getImage());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    public class FriendHolder extends RecyclerView.ViewHolder {

        TextView T ;
        ImageView I ;
        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            T = itemView.findViewById(R.id.text1);
            I = itemView.findViewById(R.id.image1);

        }
    }


    public void setList(ArrayList<user> posts) {
        this.users = posts;
        notifyDataSetChanged();

    }


}
