package com.example.espritindoor.ViewModel;


import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Contoller.EventsLab;
import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private RecyclerView mEventsRecycler;
    private EventListAdapter mAdapter;
    private LinearLayoutManager linearLayout;
    private Button follow;
    Context context ;
    EventsLab eventsLab;
    List<Event> events;
    List<Event> mEvents = new ArrayList<>();
    View view;
    URL url;
    EventFavoriFragment eff = new EventFavoriFragment();
    private String Userid ;
    private static final int MAX_DIMENSION = 500;

    public EventFragment(String id) throws ParseException {
        // Required empty public constructor
        this.Userid = id;
    }
    public EventFragment() throws ParseException {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);


        follow = (Button) view.findViewById(R.id.event_favoris);
        mEventsRecycler = (RecyclerView) view.findViewById(R.id.event_list);
        linearLayout = new LinearLayoutManager(getContext());
        mEventsRecycler.setLayoutManager(linearLayout);
        getEventsFromDB();




        // Inflate the layout for this fragment
        //      RecyclerView recyclerView =  v.findViewById(R.id.event_list);
        //      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //   mEventsRecycler.setAdapter(new EventListAdapter(getActivity() , eventsLab.getEventsFromDB()));

        return view;
    }
/*
    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL("http://xxx.xxx.xxx/image.jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imageView = (ImageView) view.findViewById(R.id.event_list_photo);
            imageView.setImageBitmap(result);
        }
    }
*/


    private void addFollowToDB(final String id) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message = "correct";
                //       System.out.println(input[0]+"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                // String eventId = id;
                System.out.println(id+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.1.20:3000/users/favoris/5e9594b39b7d942358da83dc/event/"+id)
                        .post(formBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String resp = response.body().string();
                    System.out.println(resp+"JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("correct")) {






                } else {
                    Toast.makeText(getContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        asyncTask.execute();
    }

    public void getEventsFromDB() {

        final AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mEvents = new ArrayList<>();
            }
            @Override
            protected Void doInBackground(Integer... UserId) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.1.20:3000/events/")
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        System.out.println( array.getJSONObject(i)+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        String id = object.getString("_id");
                        String eventName = object.getString("eventName");
                        String description = object.getString("description");
                        String date1 = object.getString("date");
                        String photo  = object.getString("photo");
                        Event event = new Event(id,eventName,description,photo,date1);
                        mEvents.add(event);
                        //    System.out.println(mEvents.get(Integer.parseInt(getId()+"idddddddddddddddddiddddddddddddddddddddddddddd")));
                    }
//                    System.out.println(mEvents.get(0).getIdEvent()+"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                }catch (IOException e){
                    e.printStackTrace();
                }
                catch (
                        JSONException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter = new EventFragment.EventListAdapter(mEvents);
                mEventsRecycler.setAdapter(mAdapter);
            }
        };

        asyncTask.execute();
    }

    private class EventHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_event;
        public TextView mEventName;
        public TextView mDescription;
        public TextView mDate;
        public ImageView mPhoto;
        public Event mEvent;
        public Button mFavori;

        public EventHolder(View itemView) {
            super(itemView);
            item_event = (LinearLayout) itemView.findViewById(R.id.item_event);
            mEventName = (TextView) itemView.findViewById(R.id.event_list_name);
            mDescription = (TextView) itemView.findViewById(R.id.event_list_description);
            mDate = (TextView) itemView.findViewById(R.id.event_list_date);
            mPhoto = itemView.findViewById(R.id.event_list_photo);
            mFavori = itemView.findViewById(R.id.event_favoris);
        }
    }

    private  class EventListAdapter extends RecyclerView.Adapter<EventHolder> {
        private List<Event> mEvents;
        Dialog myDialog;
        public EventListAdapter(List<Event> events) {
            mEvents = events;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.event_list_content, parent, false);

            return new EventHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull final EventHolder holder, final int position) {

            final Event event = mEvents.get(position);
            holder.mEvent = event;
            //  String urlString = "http://localhost:3000/events/uploads/"+event.getPhoto();
            //  System.out.println(urlString);
            holder.mEventName.setText(event.getEventName());
            holder.mDescription.setText(event.getDescription());
            holder.mDate.setText(event.getDate());
            myDialog = new Dialog(getContext());
            myDialog.setContentView(R.layout.dialog_events);
            TextView dialog_event_name = (TextView) myDialog.findViewById(R.id.dialog_event_name);
            TextView dialog_event_date = (TextView) myDialog.findViewById(R.id.dialog_date);
            TextView dialog_event_description = (TextView) myDialog.findViewById(R.id.dialog_event_description);
            TextView dialog_event_place = (TextView) myDialog.findViewById(R.id.dialog_event_place);
            ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialog_image);
            dialog_event_name.setText(mEvents.get(holder.getAdapterPosition()).getEventName());
            dialog_event_date.setText(mEvents.get(holder.getAdapterPosition()).getDate());
            dialog_event_description.setText(mEvents.get(holder.getAdapterPosition()).getDescription());
            dialog_event_place.setText(mEvents.get(holder.getAdapterPosition()).getDescription());
            RequestCreator request = Picasso
                    .get()
                    .load("http://192.168.1.20:3000/events/uploads/"+event.getPhoto());
            request.resize(660,400 );
            request.centerCrop();
            request.into(dialog_image);

            holder.item_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"number "+String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    myDialog.show();
                }
            });

            holder.mFavori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFollowToDB(event.getIdEvent());
                    ((AppCompatActivity)getContext())
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, eff)
                            .addToBackStack(null)
                            .commit();

                }
            });



            ImageView img =holder.mPhoto;
            //   Picasso.get().load(urlString).into(img);

            RequestCreator request1 = Picasso
                    .get()
                    .load("http://192.168.1.20:3000/events/uploads/"+event.getPhoto());
            request1.resize(660,400 );
            request1.centerCrop();
            request1.into(img);

            //   Picasso.get().load("http://localhost:3000/events/uploads/"+event.getPhoto()).into(img);
           /* Picasso.get()
                    .load(urlString)
                    .into(img);

            */

         /*   holder.mDate.setText(event.getDate());
            ImageView pic =holder.mPhoto;
            Picasso.get()
                    .load(urlString)
                    .into(img);


          */
            // holder.mPhoto.
            // holder.mPhoto.setImageResource(myURL);
            // Picasso.get().load("C:/nodejs/pim/uploads/"+event.getPhoto()).into(holder.mPhoto);
            //  RequestCreator request = Picasso.with(getContext()).load(urlString);
            //   request.resize(MAX_DIMENSION, 0);
            // request.into(holder.mPhoto);

        }
        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }







/*
    String sDate1="31/12/1998";
    SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
    Date date1 =formatter1.parse(sDate1);


    public List<Event> getmEvents ()

    {

        Event e = new Event("Gala", "La description (du latin descriptio) est la présentation de lieux, de personnages ou d'événements dans un récit. Sommaire", "@drawable/foggy_iceland.jpg", date1);
        Event e1 = new Event("Wiiw", "La description (du latin descriptio) est la présentation de lieux, de personnages ou d'événements dans un récit. Sommaire", "@drawable/foggy_iceland.jpg", date1);
        Event e2 = new Event("Chicho", "La description (du latin descriptio) est la présentation de lieux, de personnages ou d'événements dans un récit. Sommaire", "@drawable/foggy_iceland.jpg", date1);
        Event e3 = new Event("IEEE", "La description (du latin descriptio) est la présentation de lieux, de personnages ou d'événements dans un récit. Sommaire", "@drawable/foggy_iceland.jpg", date1);

        mEvents.add(e);
        mEvents.add(e1);
        mEvents.add(e2);
        mEvents.add(e3);


        return mEvents;

    }
*/
}
