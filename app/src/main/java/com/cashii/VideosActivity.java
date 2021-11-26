package com.cashii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cashii.cashii.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdsManager;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.cashii.Adapter.VideoPlayerRecyclerAdapter;
import com.cashii.Model.MediaObject;
import com.cashii.Resource.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cashii.Adapter.VideoPlayerRecyclerAdapter.AD_DISPLAY_FREQUENCY_LIMIT;
import static com.cashii.Config.Main_Url;

public class VideosActivity extends AppCompatActivity implements NativeAdsManager.Listener {

    private static final String TAG = "MainActivity";

    VideoPlayerRecyclerAdapter adapter;
    private VideoPlayerRecyclerView mRecyclerView;
    RewardedVideoAd rewardedVideoAd;
    RewardedVideoAdListener rewardedVideoAdListener;
    NativeAd nativeAd;
    NativeAdListener nativeAdListener;
    NativeAdsManager nativeAdsManager;
    BroadcastReceiver broadcastReceiver;
    MediaObject media_objects;
    ArrayList<MediaObject> mediaObjectArrayList;
    List<MediaObject> mediaObjectList = new ArrayList<>();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        broadcastReceiver = new NetworkChangeReceiver();
        registerBrodcastReciver();

        mRecyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        nativeAdsManager = new NativeAdsManager(VideosActivity.this,getString(R.string.native_ad),AD_DISPLAY_FREQUENCY_LIMIT);
        nativeAdsManager.loadAds();
        nativeAdsManager.setListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);


        load_videos();

        rewardedVideoAd = new RewardedVideoAd(this, getString(R.string.rewarded_video_ad));
        rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {

                // Rewarded video ad failed to load
                Toast.makeText(VideosActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.e("", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Toast.makeText(VideosActivity.this, "Video Ad Loaded", Toast.LENGTH_SHORT).show();
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
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d("", "Rewarded video completed!");
                loadVideoAd();
                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("", "Rewarded video ad closed!");

                loadVideoAd();

            }
        };
        //initRecyclerView();
        showAdWithDelay();
    }


    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(getApplicationContext())
                .setDefaultRequestOptions(options);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if(mRecyclerView!=null) {
//            mRecyclerView.releasePlayer();
//        }
        unregisteredBrodcastReciver();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //loadNativeAd();
        loadVideoAd();
    }
    private void loadVideoAd() {
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }
    @Override
    public void onBackPressed() {

        if(rewardedVideoAd.isAdLoaded()){
            rewardedVideoAd.show();
        }else {
            finish();
            super.onBackPressed();
        }
    }


    @Override
    public void onAdsLoaded() {
     //   Toast.makeText(this, "Native Load", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAdError(AdError adError) {
        Toast.makeText(this, adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if rewardedVideoAd has been loaded successfully
                if (rewardedVideoAd == null || !rewardedVideoAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if (rewardedVideoAd.isAdInvalidated()) {
                    return;
                }
                rewardedVideoAd.show();
            }
        }, 1000 * 30 * 10); // Show the ad after 15 minutes
    }
    private void registerBrodcastReciver() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisteredBrodcastReciver(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void load_videos(){
        mediaObjectArrayList = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,Main_Url+"/getvideos.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            progressBar.setVisibility(View.VISIBLE);
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        media_objects = new MediaObject();
                        media_objects.setThumbnail(Main_Url+object.getString("image"));
                        media_objects.setMedia_url(Main_Url+object.getString("url"));
                        mediaObjectArrayList.add(media_objects);
                        // Toast.makeText(getContext(),"Success"+response,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setMediaObjects(mediaObjectArrayList);
                adapter = new VideoPlayerRecyclerAdapter(mediaObjectArrayList, initGlide(),getApplicationContext(),nativeAdsManager);
                mRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressBar.setVisibility(View.GONE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}
