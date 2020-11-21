package com.example.espritindoor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.example.espritindoor.ViewModel.DetailActivity;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private final List<Event> mValues;
    Context mContext ;

    public EventListAdapter(Context context, List<Event> items) {
        mValues = items;
        this.mContext = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_content, parent, false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mPhoto.setImageResource(R.drawable.smokey_mountain);
        holder.mEventName.setText(mValues.get(position).getEventName());
        holder.mDescription.setText(mValues.get(position).getDescription());
        holder.mDate.setText(mValues.get(position).getDate().toString());

        // Load contact picture from the saved contact picture URL
        final Context context = holder.mView.getContext();

    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPhoto;
        public final TextView mEventName;
        public final TextView mDescription;
        public final TextView mDate;
        public Event mItem;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mPhoto = (ImageView) view.findViewById(R.id.event_list_photo);
            mEventName = (TextView) view.findViewById(R.id.event_list_name);
            mDescription = (TextView) view.findViewById(R.id.event_list_description);
            mDate = (TextView) view.findViewById(R.id.event_list_date);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Event clickedDataItem = mValues.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("movies", (Parcelable) clickedDataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getEventName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
