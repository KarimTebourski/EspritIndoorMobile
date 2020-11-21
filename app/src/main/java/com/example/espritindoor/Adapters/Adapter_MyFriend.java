package com.example.espritindoor.Adapters;

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

public class Adapter_MyFriend extends RecyclerView.Adapter<Adapter_MyFriend.MyFriendHolder> {

    private ArrayList<Friend> friend = new ArrayList<>();
    String url = Url.MonURL();

    //private AdapterView.OnItemClickListener  onItemClickListener ;


    private ItemClickListener mlistener ;

       public  interface ItemClickListener{
           void ItemChat(int position);
           void ItemTrack(int position);
           void ItemFriend(int position);

       }

       public void setonItemClickListener ( ItemClickListener listener)
       {
            mlistener = listener;
        }




    @NonNull
    @Override
    public MyFriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false ), mlistener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendHolder holder, int position) {

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


    public class MyFriendHolder extends RecyclerView.ViewHolder {
        TextView T ;
        ImageView I ;
        ImageButton btnChat ;
        ImageButton btntrak ;
        public MyFriendHolder(@NonNull View itemView , final ItemClickListener listener) {
            super(itemView);

            T = itemView.findViewById(R.id.nomFriendf);
            I = itemView .findViewById(R.id.imageAmi);
            btnChat = itemView.findViewById(R.id.btnChat);
            btntrak = itemView.findViewById(R.id.btnTrak);

            btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!= null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.ItemChat(position);
                        }

                    }

                }
            });

            btntrak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener!= null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.ItemTrack(position);
                        }

                    }

                }
            });

            I.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.ItemTrack(position);
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
