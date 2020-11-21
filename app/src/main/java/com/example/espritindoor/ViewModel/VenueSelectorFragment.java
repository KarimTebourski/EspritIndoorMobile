package com.example.espritindoor.ViewModel;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Adapters.VenueSelectorAdapter;
import com.example.espritindoor.MainActivity;
import com.example.espritindoor.Model.VenueSelectorItem;
import com.example.espritindoor.R;
import com.example.espritindoor.listeners.IVenueClickedListener;
import com.example.espritindoor.listeners.MenuListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VenueSelectorFragment extends Fragment implements
        IVenueClickedListener {
    public   String userid;
    public  String username;
    private static final String TAG = VenueSelectorFragment.class.getSimpleName();

    private static final int FLIPPER_LIST_ITEMS = 0;
    private static final int FLIPPER_LIST_PROGRESS = 1;


    private Context mContext;
    private MainActivity mActivity;
    private MenuListener mMenuListener;

    private RecyclerView mVenueSelectorList;

    private ViewFlipper mViewFlipper;
    private VenueSelectorAdapter mRecyclerViewAdapter;

    View venueSelectorBackBtn;
    View venueSelectorBtn;
    View mMainView;
    IndoorFragment fragementExplore = new IndoorFragment();
    List<VenueSelectorItem> venues = new ArrayList<>();

    public VenueSelectorFragment() {

    }
    public VenueSelectorFragment(String userid , String username) {
        this.userid = userid ;
        this.username = username;
        System.out.println("Iddddddddd "+ this.userid);
        System.out.println("Iddddddddd "+ this.username);
    }


//region Fragment lifecycle events

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // list
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.bloce);
        Bitmap icon2 = BitmapFactory.decodeResource(getResources(),R.drawable.bloca);

        Bitmap icon3 = BitmapFactory.decodeResource(getResources(),R.drawable.blocb);


        VenueSelectorItem v1 = new VenueSelectorItem("1" , "Bloc E ",icon);
        VenueSelectorItem v2 = new VenueSelectorItem("2" , "Bloc B ",icon2);
        VenueSelectorItem v3 = new VenueSelectorItem("3" , "Bloc A ",icon3);
        venues.add(v1);
        venues.add(v2);
        venues.add(v3);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_selector, container , false);




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mMainView = view;
        //init(getContext() , mMenuListener);
    }
    //endregion


    public void init(Context context, MenuListener menuListener)
    {
        mContext = (mContext != null) ? mContext : context;
        mActivity = (mActivity != null) ? mActivity : (MainActivity) context;
        mMenuListener = menuListener;

        venueSelectorBtn = mMainView.findViewById(R.id.venueselector_venue_ic);
        venueSelectorBackBtn = mMainView.findViewById(R.id.venueselector_back_button);



        setupViewFlipper(mMainView);
        setupListView(mMainView);
    }





    private void setupViewFlipper(View view) {
        mViewFlipper = view.findViewById(R.id.venue_selector_viewflipper);
        mViewFlipper.setDisplayedChild(FLIPPER_LIST_PROGRESS);
    }

    public void setupListView(View view) {
        mContext = (mContext != null) ? mContext : getContext();
        mActivity = (mActivity != null) ? mActivity : (MainActivity) mContext;

        mVenueSelectorList = view.findViewById(R.id.venue_selector_list);
        mVenueSelectorList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(false);
        layoutManager.setAutoMeasureEnabled(true);
        mVenueSelectorList.setLayoutManager(layoutManager);
        System.out.println("9abel il function imta3 il adepteur "+userid);
        System.out.println("9abel il function imta3 il adepteur "+username);
        mRecyclerViewAdapter = new VenueSelectorAdapter( this,getContext(),"ssdsds","sdqsdqsdqd");
        mVenueSelectorList.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setonItemClick(new VenueSelectorAdapter.ItemClickListene() {
            @Override
            public void launchIndoor(int position) {

                if(position== 0){

                    Log.d("aaaaaa", "launchIndoor: ");
                    Bundle bundle = new Bundle();
                    bundle.putString("key","Bloc E ");
                    bundle.putString("key1",userid);// Put anything what you want
                    bundle.putString("key2",username);
                    fragementExplore.setArguments(bundle);

                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContent, fragementExplore).addToBackStack(null).commit();
                }

                if(position== 1)
                    System.out.println("block2");
                if(position== 2)
                    System.out.println("block3");

            }
        });
    }

    public void onDataReady(List<VenueSelectorItem> venues) {

        mRecyclerViewAdapter.setItems(venues);


        updateViewFlipper();
    }

    private void updateViewFlipper() {
        if (mViewFlipper.getDisplayedChild() != FLIPPER_LIST_ITEMS) {
            mViewFlipper.setDisplayedChild(FLIPPER_LIST_ITEMS);
        }
    }

    @Override
    public void OnVenueClicked(String venueId) {

    }
    //endregion


    @Override
    public void onResume() {
        super.onResume();
        init(getContext() , mMenuListener);
       onDataReady(venues);

    }
}

