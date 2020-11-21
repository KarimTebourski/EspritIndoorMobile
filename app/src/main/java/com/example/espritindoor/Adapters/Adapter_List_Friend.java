package com.example.espritindoor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Model.Friend;
import com.example.espritindoor.R;
import com.example.espritindoor.URL.Url;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_List_Friend extends RecyclerView.Adapter<Adapter_List_Friend.ListFriendHolder> {

    private ArrayList<Friend> friend = new ArrayList<>();
    String url = Url.MonURL();
    Context context ;



    private ItemClickListene mlistene ;

    public  interface ItemClickListene{
        void ajouterFriend(int position);
    }

    public void setonItemClick ( ItemClickListene listener)
    {
        mlistene = listener;
    }


    @NonNull
    @Override
    public ListFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return  new ListFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_friend,parent,false),mlistene) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ListFriendHolder holder, int position) {

        Friend F = friend.get(position);
        holder.T.setText(F.getUsernameF());

        Friend image = friend.get(position);

        Picasso.get()
                .load(url+"/images/"+(image.getImageProfil()))
                .into(holder.I);







    }

    @Override
    public int getItemCount() {
        return friend.size();
    }




    public class ListFriendHolder extends RecyclerView.ViewHolder {
        TextView T ;
        ImageView I ;
        ImageButton B ;
        public ListFriendHolder(@NonNull View itemView, final ItemClickListene listener) {
            super(itemView);
            T = itemView.findViewById(R.id.nomFriend);
            I = itemView.findViewById(R.id.imagelist);
            B = itemView.findViewById(R.id.ajouterBtn);

            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!= null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.ajouterFriend(position);
                        }

                    }

                }
            });

        }
    }


     public void setList(ArrayList<Friend> f) {
        this.friend = f;
        notifyDataSetChanged();

    }


}
