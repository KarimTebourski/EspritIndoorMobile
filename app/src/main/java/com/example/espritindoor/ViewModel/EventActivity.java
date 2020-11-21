package com.example.espritindoor.ViewModel;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Contoller.EventsLab;
import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private RecyclerView mEventsRecycler;
    //  private EventActivity.EventListAdapter mAdapter;
    private LinearLayoutManager linearLayout;
    Context context ;
    EventsLab eventsLab;
    List<Event> events;
    List<Event> mEvents = new ArrayList<>();
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //  mEventsRecycler = (RecyclerView) findViewById(R.id.event_list);
        //  linearLayout = new LinearLayoutManager(context);
        //  mEventsRecycler.setLayoutManager(linearLayout);
        // getEventsFromDB();


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //keep selected fragment when rotating the device
        if (savedInstanceState == null) {
            try {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventFragment()).commit();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    try {
                        selectedFragment = new EventFragment() ;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.navigation_reservation:
                    selectedFragment = new EventPublicationFragment();
                    break;
                case R.id.navigation_favorites:
                    selectedFragment = new EventFavoriFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            androidx.fragment.app.Fragment fragment = null;
            Class fragmentClass = EventFragment.class;
            Bundle args = new Bundle();
            if (id == R.id.navigation_home) {
                fragmentClass = EventFragment.class;
            }
            if (id == R.id.navigation_reservation) {
                fragmentClass = EventPublicationFragment.class;
            }
            if (id == R.id.navigation_favorites) {
                fragmentClass = EventFavoriFragment.class;
            }
            try {
                fragment = (androidx.fragment.app.Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
           // fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };

*/
/*

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
                        .url("http://192.168.43.63:3000/events/")
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
                        String sDate1="31/12/1998";
                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
                        Date date1 =formatter1.parse(sDate1);
                        Event event = new Event(id,eventName,description,"@drawable/foggy_iceland.jpg",date1);
                        mEvents.add(event);
                    }
                    System.out.println(mEvents.get(0).getIdEvent()+"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                }catch (IOException e){
                    e.printStackTrace();
                }

                catch (
                        JSONException e) {
                    e.printStackTrace();

                } catch (ParseException e) {
                    e.printStackTrace();

                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter = new EventActivity.EventListAdapter(mEvents);
                mEventsRecycler.setAdapter(mAdapter);
            }
        };

        asyncTask.execute();
    }
*/
/*

    private class EventHolder extends RecyclerView.ViewHolder {
        public TextView mEventName;
        public TextView mDescription;
        public TextView mDate;
        public ImageView mPhoto;
        public Event mEvent;

        public EventHolder(View itemView) {
            super(itemView);

            mEventName = (TextView) itemView.findViewById(R.id.event_list_name);
            mDescription = (TextView) itemView.findViewById(R.id.event_list_description);
            mDate = (TextView) itemView.findViewById(R.id.event_list_date);
            mPhoto = (ImageView) itemView.findViewById(R.id.event_list_photo);
        }
    }

    private  class EventListAdapter extends RecyclerView.Adapter<EventActivity.EventHolder> {
        private List<Event> mEvents;
        public EventListAdapter(List<Event> events) {
            mEvents = events;
        }
        @NonNull
        @Override
        public EventActivity.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_content, parent, false);
            return new EventActivity.EventHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder holder, int position) {
            Event event = mEvents.get(position);
            holder.mEvent = event;
            holder.mEventName.setText(event.getEventName());
            holder.mDescription.setText(event.getDescription());
            holder.mDate.setText(event.getDate().toString());
            holder.mPhoto.setImageResource(R.drawable.colorado_mountains);
        }


        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }*/
}
