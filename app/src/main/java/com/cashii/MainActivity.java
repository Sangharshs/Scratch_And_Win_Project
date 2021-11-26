package com.cashii;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cashii.cashii.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;
import com.cashii.Adapter.CustomAdapter;
import com.cashii.Adapter.DrawerAdapter;
import com.cashii.Adapter.DrawerItem;
import com.cashii.Adapter.SimpleItem;
import com.cashii.Model.Grid_Item;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.cashii.Config.Main_Url;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    public static final int POS_PRIVACY_POLICY = 0;
    public static final int POS_RATE_US = 1;
    public static final int POS_SHARE_APP = 2;
    public static final int POS_MORE_APPS = 3;

    private String[] screenTitles;
    private Drawable[] screen_Icons;

    private SlidingRootNav slidingRootNav;

    List<Grid_Item> grid_itemList;
    CardView cardView;
    RecyclerView recyclerView_main;
    NavigationView navigationView;
   // DrawerLayout drawer;

    DrawerLayout drawerLayout;
    Class fragmentClass;
    public static Fragment fragment;
    private final String TAG = "NativeAdActivity".getClass().getSimpleName();
    private NativeAd nativeAd;
    BroadcastReceiver broadcastReceiver,myreciver;
    AdView adView;
    TelephonyManager tel;
    TextView imei;

    ImageView imageView;


    public static RewardedVideoAd rewardedVideoAd;
    private static RewardedVideoAdListener rewardedVideoAdListener;

    @SuppressLint({"HardwareIds", "WrongViewCast"})
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.menu_on_toolbar);


        final Toolbar toolbar = findViewById(R.id.toolBarMy);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

         drawerLayout = findViewById(R.id.drawer_M);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(170)
                .withRootViewScale(0.74f)
                .withRootViewElevation(25)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingRootNav.openMenu();
                if(slidingRootNav.isMenuOpened()){
                    slidingRootNav.closeMenu();
                }
            }
        });

        app_installs();

        adView = new AdView(this, getString(R.string.rectangle_ad), AdSize.RECTANGLE_HEIGHT_250);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();

        broadcastReceiver = new NetworkChangeReceiver();
        myreciver  = new MyReceiver();
        registerBrodcastReciver();

        recyclerView_main = findViewById(R.id.recyclerview_main);
        cardView = findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    startActivity(new Intent(MainActivity.this, WithdrawActivity.class));

                }else {
                    startActivity(new Intent(MainActivity.this, WithdrawActivity.class));
                }
            }
        });

//        ADS
        rewardedVideoAd = new RewardedVideoAd(this, getString(R.string.rewarded_video_ad));
        rewardedVideoAdListener = new RewardedVideoAdListener() {
                @Override
                public void onError(Ad ad, AdError error) {
                    // Rewarded video ad failed to load
                   // Toast.makeText(MainActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("", "Rewarded video ad failed to load: " + error.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                   // Toast.makeText(MainActivity.this, "Video Ad Loaded", Toast.LENGTH_SHORT).show();
                    // Rewarded video ad is loaded and ready to be displayed
                    Log.d("", "Rewarded video ad is loaded and ready to be displayed!");
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Rewarded video ad clicked
                    Log.d("", "Rewarded video ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Rewarded Video ad impression - the event will fire when the
                    // video starts playing
                    Log.d("", "Rewarded video ad impression logged!");
                }

                @Override
                public void onRewardedVideoCompleted() {
                }

                @Override
                public void onRewardedVideoClosed() {

                    // The Rewarded Video ad was closed - this can occur during the video
                    // by closing the app, or closing the end card.
                    Log.d("", "Rewarded video ad closed!");


                }
            };


        loadNativeAd();

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView_main.setLayoutManager(layoutManager);

        grid_itemList = new ArrayList<>();
        grid_itemList.add(new Grid_Item("Spin"));
        grid_itemList.add(new Grid_Item("Watch"));
        grid_itemList.add(new Grid_Item("Memes"));
        grid_itemList.add(new Grid_Item("Scratch"));

        CustomAdapter customAdapter = new CustomAdapter(grid_itemList);
        recyclerView_main.setAdapter(customAdapter);

        screen_Icons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter_d = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_PRIVACY_POLICY),
                createItemFor(POS_RATE_US).setChecked(true),
                createItemFor(POS_SHARE_APP),
                createItemFor(POS_MORE_APPS)
                ));

        adapter_d.setListener(this);

        RecyclerView list =  findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter_d);




    }

    private String[] loadScreenTitles(){
        return getResources().getStringArray(R.array.id_screenTitlesArray);
    }
    private Drawable[] loadScreenIcons() {
        TypedArray t = getResources().obtainTypedArray(R.array.id_screenIconsArray);
        Drawable[] icons = new Drawable[t.length()];
        for (int i = 0; i < t.length(); i++) {
            int id = t.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
            t.recycle();
            return icons;
            }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screen_Icons[position],screenTitles[position])
                .withIconTint(color(R.color.colorPrimary))
                .withIextTint(color(R.color.colorAccent))
                .withSelectedIconTint(color(R.color.colorPrimary))
                .withSelectedTextTint(color(R.color.colorPrimary));
    }
    private int color (@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }

    private void registerBrodcastReciver() {
        registerReceiver(myreciver,new IntentFilter(Intent.ACTION_DATE_CHANGED));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
          registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
          registerReceiver(myreciver,new IntentFilter(Intent.ACTION_DATE_CHANGED));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            registerReceiver(myreciver,new IntentFilter(Intent.ACTION_DATE_CHANGED));
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisteredBrodcastReciver(){
        try {
            unregisterReceiver(myreciver);
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }

        unregisteredBrodcastReciver();
    }


    private void loadNativeAd() {
              nativeAd = new NativeAd(this,getString(R.string.native_ad_fb));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };
        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }


    public void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        NativeAdLayout adView = (NativeAdLayout) inflater.inflate(R.layout.native_ad_fb_ayout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public void app_installs(){
        if(!ApplicationUtils.getBooleanPreferenceValue(this,"isFirstTimeExecution")){
            Log.d(TAG, "First time Execution");
            ApplicationUtils.setBooleanPreferenceValue(this,"isFirstTimeExecution",true);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Main_Url+"app_installs.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Success")){
                                Toast.makeText(MainActivity.this, "Wel-come", Toast.LENGTH_LONG).show();

                            }
                            else{
                                // progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    @SuppressLint("HardwareIds")
                    String ID = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    //  Toast.makeText(WithdrawActivity.this, payment, Toast.LENGTH_SHORT).show();
                    params.put("app_installs",ID);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }
    public static void loadVideoAd() {
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadVideoAd();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadVideoAd();
    }


    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(position == POS_PRIVACY_POLICY){
            startActivity(new Intent(MainActivity.this,PrivacyActivity.class));
        }else if(position == POS_RATE_US) {
            String urlll = getString(R.string.play_store_link)+getApplicationContext().getPackageName();
            Uri uri = Uri.parse(urlll);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
        else if(position == POS_SHARE_APP){
            String urlll = getString(R.string.play_store_link)+getApplicationContext().getPackageName();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                            getString(R.string.app_name) + "\n\n" + urlll);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

        }else if(position == POS_MORE_APPS){

        }


        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}