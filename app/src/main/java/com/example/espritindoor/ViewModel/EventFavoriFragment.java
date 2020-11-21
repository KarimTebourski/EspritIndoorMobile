package com.example.espritindoor.ViewModel;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Adapters.FavorisEventAdapter;
import com.example.espritindoor.Helper.RecyclerItemTouchHelper;
import com.example.espritindoor.Helper.RecyclerItemTouchHelperListener;
import com.example.espritindoor.Model.Event;
import com.example.espritindoor.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFavoriFragment extends Fragment implements RecyclerItemTouchHelperListener {


    private    RecyclerView mRecyclerView;
    private FavorisEventAdapter mAdapter;
    List<Event> mFlowerList = new ArrayList<>();
    Event mFlowerData;
    View view;
    LinearLayout layout;
    public EventFavoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_event_favori, container, false);
        layout = view.findViewById(R.id.event_favori_layout);
        mRecyclerView = view.findViewById(R.id.recyclerviewFavoris);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView);
/*
        mFlowerList = new ArrayList<>();

        mFlowerData = new Event("Rose","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        mFlowerData = new Event("event1","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        mFlowerData = new Event("castra","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        mFlowerData = new Event("event2","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        mFlowerData = new Event("event3","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        mFlowerData = new Event("event4","@drawable/colorado_mountains.jpg");
        mFlowerList.add(mFlowerData);

        FavorisEventAdapter myAdapter = new FavorisEventAdapter(getContext(), mFlowerList);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(myAdapter);
*/


        getFavoriFromDB();
        return view;

    }



    public void getFavoriFromDB() {

        final AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mFlowerList = new ArrayList<>();
            }
            @Override
            protected Void doInBackground(Integer... UserId) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.1.20:3000/users/userId/5e9594b39b7d942358da83dc")
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray array =  object.getJSONArray ("events");
                    for(int i=0; i<array.length(); i++){
                        // JSONObject object = array.getJSONObject(i);
                        System.out.println( array.getJSONObject(i)+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        String eventName = array.getJSONObject(i).getString("eventName");
                        String id = array.getJSONObject(i).getString("_id");
                        //   String eventName = object.getString("eventName");
                        // String photo  = object.getString("photo");
                        String photo = array.getJSONObject(i).getString("photo");
                        Event event = new Event(id,eventName,photo);
                        mFlowerList.add(event);
                        //    System.out.println(mEvents.get(Integer.parseInt(getId()+"idddddddddddddddddiddddddddddddddddddddddddddd")));
                    }
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
                mAdapter = new FavorisEventAdapter(getContext(),mFlowerList);
                mRecyclerView.setAdapter(mAdapter);
            }
        };

        asyncTask.execute();
    }


    public void RemoveFavorisFromDB(final String id) {

        final AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {

            }
            @Override
            protected Void doInBackground(Integer... UserId) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.1.20:3000/users/delete/5e9594b39b7d942358da83dc/favEvent/"+id)
                        .get()
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
          /*  mAdapter = new FavorisEventAdapter(getContext(),mFlowerList);
            mRecyclerView.setAdapter(mAdapter); */
            }
        };

        asyncTask.execute();
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        String name = mFlowerList.get(viewHolder.getAdapterPosition()).getEventName();
        String id = mFlowerList.get(viewHolder.getAdapterPosition()).getIdEvent();
        final Event deleteItem = mFlowerList.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();
        System.out.println(deletedIndex +"Deleeeeeeeeeeeeeeeeete");
        System.out.println(name+"    sdsfsdfkldsfldskfjdslfkdsjflsdjflksdkjflsdfjsdlkfjsdlfkj");
        RemoveFavorisFromDB(id);

        mAdapter.removeItem(viewHolder.getAdapterPosition());
        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        Snackbar snackbar = Snackbar.make(layout ,name+" Removed ... ",Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);

        snackbar.show();
        mRecyclerView.invalidate();
        layout.invalidate();
    }
}
