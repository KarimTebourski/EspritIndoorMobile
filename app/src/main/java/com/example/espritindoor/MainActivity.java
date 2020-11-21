package com.example.espritindoor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Adapters.NotificationListAdapter;
import com.example.espritindoor.Model.Notification;
import com.example.espritindoor.Model.user;
import com.example.espritindoor.ViewModel.ChatFragement;
import com.example.espritindoor.ViewModel.ColleagueFragement;
import com.example.espritindoor.ViewModel.EventActivity;
import com.example.espritindoor.ViewModel.ExploreFragement;
import com.example.espritindoor.ViewModel.HeatMapFragment;
import com.example.espritindoor.ViewModel.ProfileFragement;
import com.example.espritindoor.ViewModel.VenueSelectorFragment;
import com.example.espritindoor.technique.ApiInterface;
import com.example.espritindoor.technique.retrofit;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.util.Log.d;
import static com.example.espritindoor.App.CHANNEL_1_ID;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Advance3DDrawerLayout drawer;
    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    private static final String TAG = "Esprit";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Notification> notifications;
    private NotificationManagerCompat notificationManager;


    private  int profil , background ;
    private String id_user;
    private  String username;
    private int NBR ;
    MenuItem menuItem;
    // badge text view
    TextView badgeCounter_Chat;
    // change the number to see badge in action
    int pendingNotifications_Chat = 22;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_advance_3d_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    // Profil
        try {
        profil = getIntent().getExtras().getInt("profile");

    }catch (Exception e ) { }


    // background

        try {
        background = getIntent().getExtras().getInt("background");
    }catch (Exception e)
    {
        background = 0 ;
    }


        // Session user he echanger avec shredpref
        //id_user = "5ede5f27afba200bb8f8f269";
       id_user = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");
        d("id_user", "    " +id_user);
        d("username", "    " +username);

                Kommunicate.init(this, "ebb690c2205236269b6526483a309c88");

        new KmConversationBuilder(MainActivity.this)
                .launchConversation(new KmCallback() {
                    @Override
                    public void onSuccess(Object message) {
                        Log.d("Conversation", "Success : " + message);
                    }

                    @Override
                    public void onFailure(Object error) {
                        Log.d("Conversation", "Failure : " + error);
                    }
                });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerNotification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notifications = new ArrayList<>();
        for (int i=0; i<1;i++)
        {
            Notification n = new Notification(
                    "Messages" +i,
                    "on vous avait envoyé un message",1
            );

            notifications.add(n);
        }

        adapter = new NotificationListAdapter(notifications,this);

       recyclerView.setAdapter(adapter);



        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = new VenueSelectorFragment(id_user,username).getClass();
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Some action is here", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(Gravity.START, 0.96f);
        drawer.setRadius(Gravity.START, 20);
        drawer.setViewElevation(Gravity.START, 8);
        drawer.setViewRotation(Gravity.START, 15);

        ensurePermissions();
        //notification
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Retrofit retro = retrofit.getInstance();
        ApiInterface apiInterface = retro.create(ApiInterface.class);
        Call<user> call = apiInterface.GetLesDemande(id_user);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

          //      String aaa = response.body().getDemande().get(1);
                int NBR = response.body().getNbr() ;
                d("8888", "***** " + NBR);
                d("8888", "***** " + response.body().get_id());


           for ( int i = 0 ;  i < NBR ; i ++)
           {
//                    String title = response.body().getDemande().get(i) ;
               String message = response.body().getDemande().get(i) ;
               notificationManager = NotificationManagerCompat.from(getApplication());

               Intent activityIntent = new Intent(getApplication(), MainActivity.class);
               PendingIntent contentIntent = PendingIntent.getActivity(getApplication(),
                       0, activityIntent, 0);

               Intent broadcastIntent = new Intent(getApplication(), NotificationReceiver.class);
               broadcastIntent.putExtra("toastMessage", message);
               broadcastIntent.putExtra("id_user", id_user);
               PendingIntent actionIntent = PendingIntent.getBroadcast(getApplication(),
                       0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

               Intent a = new Intent(getApplication(), NotificationReceiver2.class);
               a.putExtra("toastMessage2", message);
               a.putExtra("id_user", id_user);
               PendingIntent A = PendingIntent.getBroadcast(getApplication(),
                       0, a, PendingIntent.FLAG_UPDATE_CURRENT);


               Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_alert);

               android.app.Notification notification = new NotificationCompat.Builder(getApplication(), CHANNEL_1_ID)
                       .setSmallIcon(R.drawable.ic_add_alert)
                       .setContentTitle(id_user)
                       .setContentText(message)
                       .setLargeIcon(largeIcon)
                       .setStyle(new NotificationCompat.BigPictureStyle()
                               .bigPicture(largeIcon))
                       .setPriority(NotificationCompat.PRIORITY_HIGH)
                       .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                       .setColor(Color.BLUE)
                       .setContentIntent(contentIntent)
                       .setAutoCancel(true)
                       .setOnlyAlertOnce(true)
                       .setGroup("aa")
                       .addAction(R.mipmap.ic_launcher, "accepter", actionIntent)
                       .addAction(R.mipmap.ic_launcher, "refuser", A)
                       .build();
               notificationManager.notify(i, notification);
           }





            }

            @Override
            public void onFailure(Call<user>  call, Throwable t) {
                d("/////", "**************mainActicity***************" +t.getMessage());

            }
        });



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Fragment fragment = null;
        Fragment fragmentClass = new ExploreFragement();
        Bundle args = new Bundle();
        if (id == R.id.nav_mapBox) {
            fragmentClass = new ExploreFragement();
        }
        if (id == R.id.nav_collegues) {
            fragmentClass = new ColleagueFragement(id_user);
        }
        if (id == R.id.nav_profile) {
            fragmentClass = new ProfileFragement(profil , background , id_user);
        }
        if (id == R.id.nav_explore) {
            fragmentClass =new VenueSelectorFragment(id_user,username);
        }
        if (id == R.id.nav_event) {
           /* try {
                fragmentClass = new EventFragment();
            } catch (ParseException e) {
                e.printStackTrace();
            } */

            Intent myIntent = new Intent(MainActivity.this, EventActivity.class);

            MainActivity.this.startActivity(myIntent);
        }
        if (id == R.id.nav_slideshow)
        {
            fragmentClass = new ChatFragement(id_user);
        }
        if (id == R.id.nav_heatmap) {
            fragmentClass = new HeatMapFragment();
        }
        try {
            fragment = (Fragment) fragmentClass ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_right_drawer:
                drawer.openDrawer(Gravity.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Checks that we have access to required information, if not ask for users permission.
     */
    private void ensurePermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // We dont have access to FINE_LOCATION (Required by Google Maps example)
            // IndoorAtlas SDK has minimum requirement of COARSE_LOCATION to enable WiFi scanning
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.location_permission_request_title)
                        .setMessage(R.string.location_permission_request_rationale)
                        .setPositiveButton(R.string.permission_button_accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "request permissions");
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                                Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton(R.string.permission_button_deny, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        R.string.location_permission_denied_message,
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

            } else {

                // ask user for permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ACCESS_COARSE_LOCATION:

                if (grantResults.length == 0
                        || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, R.string.location_permission_denied_message,
                            Toast.LENGTH_LONG).show();
                }
                break;
        }

    }




}
