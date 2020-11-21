package com.example.espritindoor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

public class FavorisEventAdapter extends RecyclerView.Adapter<FavorisEventAdapter.ViewHolder> {

    private final List<Event> mValues;
    Context mContext ;

    public FavorisEventAdapter(Context context, List<Event> items) {
        mValues = items;
        this.mContext = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoris, parent, false);
        return new ViewHolder (view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // ImageView img =holder.mPhoto;
        //   Picasso.get().load(urlString).into(img);

        RequestCreator request = Picasso
                .get()
                .load("http://192.168.1.20:3000/events/uploads/"+holder.mItem.getPhoto());
        request.resize(300,300 );
        request.centerCrop();
        request.into(holder.mPhoto);
       // holder.mPhoto.setImageResource();
        holder.mEventName.setText(mValues.get(position).getEventName());
        final Context context = holder.mView.getContext();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPhoto;
        public final TextView mEventName;
        public Event mItem;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mPhoto =  view.findViewById(R.id.ivImage);
            mEventName = (TextView) view.findViewById(R.id.tvTitle);
        }
    }

    public void removeItem(int position) {
        mValues.remove(position);


    }



    public void restoreItem(Event item, int position) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }


}
