package com.example.espritindoor.ViewModel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.example.espritindoor.BuildConfig;
import com.example.espritindoor.MainActivity;
import com.example.espritindoor.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = SplashScreen.class.getSimpleName();

    private static final long MINIMUM_SCREEN_DELAY = 250;

    private long mStartTime;
    private long mOnCreateTime, mAppCreateTDiff;

    ViewGroup mSplashMainLayout;
    ViewGroup mSplashMainView;
    ImageView mIconStatic, mIconDynamic;
    boolean mAnimIconDone, mAnimMainViewDone;
    ProgressBar prog ;
    View mNoAvailableNetworkLayout;
    String id  ;
    String username;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        if( BuildConfig.DEBUG )
        {
            mOnCreateTime = System.nanoTime();
            //mAppCreateTDiff = mOnCreateTime - Application.getInstance().mAppCreateTime;
        }
        mAnimIconDone = mAnimMainViewDone = false;

        setContentView( R.layout.activity_splash_screen );
        mSplashMainLayout = findViewById( R.id.splash_layout );
        mSplashMainView = findViewById( R.id.splash_main );
        prog = findViewById(R.id.splash_progressbar);

        prog.setVisibility(View.VISIBLE);


        mIconStatic = mSplashMainLayout.findViewById( R.id.splash_icon);
        mIconDynamic = mSplashMainLayout.findViewById( R.id.splash_icon_2);
        mNoAvailableNetworkLayout = findViewById(R.id.no_available_network_fragment_layout);



        mSplashMainLayout.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                //Remove the listener before proceeding
                mSplashMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener( this );

                int dstTop = mIconStatic.getTop();
                int srcTop = mIconDynamic.getTop();

                animateIcon( dstTop - srcTop );
            }
        });
    }


    void animateIcon(int toTopPos)
    {
        ViewCompat.animate( mIconDynamic )
                .translationY( toTopPos )
                .setDuration( 2000 )
                //.setInterpolator( new DecelerateInterpolator() )
                .setListener( new ViewPropertyAnimatorListener()
                {
                    @Override
                    public void onAnimationStart( View view ) {
                        mAnimIconDone = false;

                        animateMainView( 0 );
                    }

                    @Override
                    public void onAnimationEnd( View view ) {
                        mAnimIconDone = true;
                        view.setAlpha( 1f );
                        continueToGooglePlayServicesCheck();
                    }

                    @Override
                    public void onAnimationCancel( View view ) {}
                } )
                .setStartDelay( 125 );
    }

    void animateMainView(int initDelay )
    {
        ViewCompat.animate( mSplashMainView )
                .alpha( 1f )
                .setDuration( 250 )
                .setListener( new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart( View view ) {
                        mAnimMainViewDone = false;
                    }

                    @Override
                    public void onAnimationEnd( View view ) {
                        mAnimMainViewDone = true;
                        view.setAlpha( 1f );
                        continueToGooglePlayServicesCheck();
                    }

                    @Override
                    public void onAnimationCancel( View view ) {}
                })
                .setStartDelay( initDelay );
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if( BuildConfig.DEBUG )
        {
            long tDiff = (System.nanoTime() - mOnCreateTime);
            float tDiffF = tDiff * (1 / 1000000f);
            float tAppDiffF = mAppCreateTDiff * (1 / 1000000f);
            //dbglog.Log( TAG, String.format( Locale.US, "App to Splash: %.2f, Splash create to resume: %.2f", tAppDiffF, tDiffF ) );
        }
    }

    void continueToGooglePlayServicesCheck()
    {
        if( mAnimIconDone && mAnimMainViewDone ) {
            if( CheckGooglePlayServices( this ) ) {
                if( BuildConfig.DEBUG ) {
                    mStartTime = System.currentTimeMillis();
                }
                if(!isNetworkReachable(this)){
                    Snackbar.make(getWindow().getDecorView().getRootView()
                            , getResources().getString(R.string.no_internet_snackbar_message),
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mSplashMainView.setVisibility(View.GONE);
                    mIconDynamic.setVisibility(View.GONE);
                    mNoAvailableNetworkLayout.setVisibility(View.VISIBLE);
                }else{
                    prepareNextActivity();
                }

            }
        }
    }

    private void prepareNextActivity()
    {
        final long timeToWait = getSplashScreenDelay();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        id = pref.getString("id","a");
        username = pref.getString("username","a");
        final Class clazz;
        System.out.println("ssssssssssssssssssssssssssss"+id);
        System.out.println("ssssssssssssssssssssssssssss"+username);
        if(id.equals("a")){
            clazz = login.class;
        }else{
           // createSassion(email);
           // id = email ;
            clazz = MainActivity.class;
        }

        Handler handler = new Handler( Looper.getMainLooper() );
        handler.postDelayed( () -> startNextActivity( clazz ), timeToWait );
    }

    void startNextActivity( Class clazz ) {
        Intent intent = new Intent( SplashScreen.this, clazz );
       // System.out.println("sqdsdqsdqqqqqqqqqqqqqsdddddddddddddddddddddddq"+id);
               intent.putExtra("id",id);
               intent.putExtra("username",username);
        startActivity( intent );
        overridePendingTransition( 0, 0 );
        finish();
    }

    private long getSplashScreenDelay() {
        long timeToWait = 0;
        long difference = System.currentTimeMillis() - mStartTime;

        if( difference < MINIMUM_SCREEN_DELAY ) {
            timeToWait = MINIMUM_SCREEN_DELAY - difference;
        }

        return timeToWait;
    }

    public static boolean isNetworkReachable( @Nullable Context context )
    {
        boolean online = false;

        if( context != null )
        {
            Context appCtx = context.getApplicationContext();
            if( appCtx != null )
            {
                ConnectivityManager cm = (ConnectivityManager) appCtx.getSystemService( Context.CONNECTIVITY_SERVICE );

                if( cm != null )
                {
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();
                    online = netInfo != null && netInfo.isConnectedOrConnecting();
                }
            }
        }
        return online;
    }

    public static boolean CheckGooglePlayServices(@Nullable final Activity activity) {

        if( activity == null )
        {
            return false;
        }

        GoogleApiAvailability gap = GoogleApiAvailability.getInstance();

        final int googlePlayServicesCheck = gap.isGooglePlayServicesAvailable(activity);

        switch (googlePlayServicesCheck) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_INVALID:
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: {
                // getErrorDialog(Activity activity, int errorCode, int requestCode)
                Dialog dialog = gap.getErrorDialog(activity, googlePlayServicesCheck, 0);
                dialog.setOnCancelListener( dialogInterface -> activity.finish() );
                dialog.show();
            }
        }
        return false;
    }
/*
    public void createSassion(String email) {

        Retrofit retrofi = retrofit.getInstance();
        ApiInterface apiInterface = retrofi.create(ApiInterface.class);
        Call<user> call = apiInterface.saisson(email);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {

                System.out.println("mabroukkkk  ssdsqdqsq");
                System.out.println("" + response.body().get_id());
                id = response.body().get_id();
//                System.out.println("*******"+response.body().toString());


            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                d("***", "*********MyFriendVM***************" + t.getMessage());

            }
        });
    }
    */


}