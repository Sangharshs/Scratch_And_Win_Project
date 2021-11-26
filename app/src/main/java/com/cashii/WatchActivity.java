package com.cashii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cashii.cashii.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cashii.Config.DAILY_VIDEO_ADS_REWARD;


public class WatchActivity extends AppCompatActivity{

   CardView card1,card2,card3,card4,card5,card6,card7,card8,card9,card10;
   LinearLayout signal1,signal2,signal3,signal4;
   // ProgressBar progressBar;
    ProgressDialog progressDialog;
    BroadcastReceiver broadcastReceiver;
    RewardedVideoAd rewardedVideoAd;
   RewardedVideoAdListener rewardedVideoAdListener;
   LinearLayout indicator1,indicator2,indicator3,indicator4,indicator5,indicator6,indicator7,indicator8,indicator9,indicator10;
   String video_reward = "100";
    SharedPreferences put_status10,put_status9, put_status8,put_status7,put_status6,
                       put_status5,put_status4,put_status3, put_status2,put_status1;
    SharedPreferences getClick;
   SharedPreferences.Editor clickEditor1, clickEditor2, clickEditor3,clickEditor4,
           clickEditor5,clickEditor6,clickEditor7,clickEditor8,clickEditor9,clickEditor10;
   AlertDialog alertDialog;
   AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        AudienceNetworkAds.initialize(this);





        card1 = findViewById(R.id.cardView1);
        card2 = findViewById(R.id.cardView2);
        card3 = findViewById(R.id.cardView3);
        card4 = findViewById(R.id.cardView4);
        card5 = findViewById(R.id.cardView5);
        card6 = findViewById(R.id.cardView6);
        card7 = findViewById(R.id.cardView7);
        card8 = findViewById(R.id.cardView8);
        card9 = findViewById(R.id.cardView9);
        card10 = findViewById(R.id.cardView10);

        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        indicator4 = findViewById(R.id.indicator4);
        indicator5 = findViewById(R.id.indicator5);
        indicator6 = findViewById(R.id.indicator6);
        indicator7 = findViewById(R.id.indicator7);
        indicator8 = findViewById(R.id.indicator8);
        indicator9 = findViewById(R.id.indicator9);
        indicator10 = findViewById(R.id.indicator10);


        indicator1.setBackgroundResource(R.drawable.red_round);
        indicator2.setBackgroundResource(R.drawable.red_round);
        indicator3.setBackgroundResource(R.drawable.red_round);
        indicator4.setBackgroundResource(R.drawable.red_round);
        indicator5.setBackgroundResource(R.drawable.red_round);
        indicator6.setBackgroundResource(R.drawable.red_round);
        indicator7.setBackgroundResource(R.drawable.red_round);
        indicator8.setBackgroundResource(R.drawable.red_round);
        indicator9.setBackgroundResource(R.drawable.red_round);
        indicator10.setBackgroundResource(R.drawable.red_round);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Ad....");
        progressDialog.setCancelable(false);

        broadcastReceiver = new NetworkChangeReceiver();
        registerBrodcastReciver();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat")
                String format = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                //  Toast.makeText(getApplicationContext(), format, Toast.LENGTH_SHORT).show();
                SharedPreferences getDate = getSharedPreferences("DATE_WATCH",MODE_PRIVATE);
                SharedPreferences.Editor editor = getDate.edit();
                editor.putString("watch_date",format);
                editor.apply();

              //  Toast.makeText(WatchActivity.this, "card 1", Toast.LENGTH_SHORT).show();
                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    put_status1  = getSharedPreferences("CARD_STATUS_1",MODE_PRIVATE);
                    clickEditor1 = put_status1.edit();
                    clickEditor1.putString("card_check1","disable1");
                    clickEditor1.apply();
                    put_status1 = getSharedPreferences("CARD_STATUS_1",MODE_PRIVATE);
                    String ss1 = put_status1.getString("card_check1","0");
                    if(ss1.equals("disable1")){
                        indicator1.setBackgroundResource(R.drawable.white_round);
                        card1.setClickable(false);
                    }
                }else {
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(WatchActivity.this, "card 2", Toast.LENGTH_SHORT).show();
                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status2  = getSharedPreferences("CARD_STATUS_2",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor2 = put_status2.edit();
                    status_editor2.putString("card_check2","disable2");
                    status_editor2.apply();
                    //card2
                    put_status2 = getSharedPreferences("CARD_STATUS_2",MODE_PRIVATE);
                    String ss2 = put_status2.getString("card_check2","0");
                    if(ss2.equals("disable2")){
                        indicator2.setBackgroundResource(R.drawable.white_round);
                        card2.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status3  = getSharedPreferences("CARD_STATUS_3",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor3 = put_status3.edit();
                    status_editor3.putString("card_check3","disable3");
                    status_editor3.apply();
                    //card3
                    put_status3 = getSharedPreferences("CARD_STATUS_3",MODE_PRIVATE);
                    String ss3 = put_status3.getString("card_check3","0");
                    if(ss3.equals("disable3")){
                        indicator3.setBackgroundResource(R.drawable.white_round);
                        card3.setClickable(false);
                    }else {
                        indicator3.setBackgroundResource(R.drawable.red_round);
                        card3.setClickable(true);
                    }
                  }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status4  = getSharedPreferences("CARD_STATUS_4",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor4 = put_status4.edit();
                    status_editor4.putString("card_check4","disable4");
                    status_editor4.apply();
                    //card4
                    put_status4 = getSharedPreferences("CARD_STATUS_4",MODE_PRIVATE);
                    String ss4 = put_status4.getString("card_check4","0");
                    if(ss4.equals("disable4")){
                        indicator4.setBackgroundResource(R.drawable.white_round);
                        card4.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status5  = getSharedPreferences("CARD_STATUS_5",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor5 = put_status5.edit();
                    status_editor5.putString("card_check5","disable5");
                    status_editor5.apply();
                    //card5
                    put_status5 = getSharedPreferences("CARD_STATUS_5",MODE_PRIVATE);
                    String ss5 = put_status5.getString("card_check5","0");
                    if(ss5.equals("disable5")){
                        indicator5.setBackgroundResource(R.drawable.white_round);
                        card5.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status6  = getSharedPreferences("CARD_STATUS_6",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor6 = put_status6.edit();
                    status_editor6.putString("card_check6","disable6");
                    status_editor6.apply();
                    //card6
                    put_status6 = getSharedPreferences("CARD_STATUS_6",MODE_PRIVATE);
                    String ss6  = put_status6.getString("card_check6","0");
                    if(ss6.equals("disable6")){
                        indicator6.setBackgroundResource(R.drawable.white_round);
                        card6.setClickable(false);
                    }

                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           //     Log.d("card7", String.valueOf(status_editor7.putString("card_check7","disable7")));
                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    SharedPreferences put_status7  = getSharedPreferences("CARD_STATUS_7",MODE_PRIVATE);
                    SharedPreferences.Editor status_editor7 = put_status7.edit();
                    status_editor7.putString("card_check7","disable7");
                    status_editor7.apply();
                    //card7
                    put_status7 = getSharedPreferences("CARD_STATUS_7",MODE_PRIVATE);
                    String ss7  = put_status7.getString("card_check7","0");
                    if(ss7.equals("disable7")){
                        indicator7.setBackgroundResource(R.drawable.white_round);
                        card7.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    put_status8  = getSharedPreferences("CARD_STATUS_8",MODE_PRIVATE);
                    clickEditor8 = put_status8.edit();
                    clickEditor8.putString("card_check8","disable8");
                    clickEditor8.apply();
                    //card8
                    put_status8 = getSharedPreferences("CARD_STATUS_8",MODE_PRIVATE);
                    String ss8  = put_status8.getString("card_check8","0");
                    if(ss8.equals("disable8")){
                        indicator8.setBackgroundResource(R.drawable.white_round);
                        card8.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    put_status9  = getSharedPreferences("CARD_STATUS_9",MODE_PRIVATE);
                    clickEditor9 = put_status9.edit();
                    clickEditor9.putString("card_check9","disable9");
                    clickEditor9.apply();
                    //card 9
                    put_status9 = getSharedPreferences("CARD_STATUS_9",MODE_PRIVATE);
                    String ss9  = put_status9.getString("card_check9","0");
                    if(ss9.equals("disable9")){
                        indicator9.setBackgroundResource(R.drawable.white_round);
                        card9.setClickable(false);
                    }

                }else {
                    loadVideoAd();

                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedVideoAd.isAdLoaded()){
                    rewardedVideoAd.show();
                    put_status10  = getSharedPreferences("CARD_STATUS_10",MODE_PRIVATE);
                    clickEditor10 = put_status10.edit();
                    clickEditor10.putString("card_check10","disable10");
                    clickEditor10.apply();
                    put_status10 = getSharedPreferences("CARD_STATUS_10",MODE_PRIVATE);
                    String ss10  = put_status10.getString("card_check10","0");
                    if(ss10.equals("disable10")){
                        indicator10.setBackgroundResource(R.drawable.white_round);
                        card10.setClickable(false);
                    }
                }else {
                    loadVideoAd();
                    Toast.makeText(WatchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }





                //card1

            }
        });

        rewardedVideoAd = new RewardedVideoAd(this, getString(R.string.rewarded_video_ad));
        rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {

                // Rewarded video ad failed to load
                Toast.makeText(WatchActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                Log.e("", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Toast.makeText(WatchActivity.this, "Video Ad Loaded", Toast.LENGTH_SHORT).show();

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
                SharedPreferences  coins_s = getSharedPreferences("spin",MODE_PRIVATE);
                int ads_reward = Integer.parseInt(coins_s.getString("Coins", "0"));
                ads_reward = ads_reward + (Integer.parseInt(DAILY_VIDEO_ADS_REWARD));


                SharedPreferences.Editor coinsEdit = coins_s.edit();
                coinsEdit.putString("Coins", String.valueOf(ads_reward));
                coinsEdit.apply();

                Toast.makeText(WatchActivity.this, "Reward Added In Your Wallet", Toast.LENGTH_SHORT).show();

                recreate();
            }

            @Override
            public void onRewardedVideoClosed() {

                recreate();
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d("", "Rewarded video ad closed!");


            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        adView = new AdView(this, getString(R.string.rectangle_ad), AdSize.RECTANGLE_HEIGHT_250);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();


        SharedPreferences getDate = getSharedPreferences("DATE_WATCH",MODE_PRIVATE);
        String saved_date = getDate.getString("watch_date","0");
        //Toast.makeText(this, saved_date, Toast.LENGTH_SHORT).show();
        @SuppressLint("SimpleDateFormat")
        String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
      //  Toast.makeText(this, current_date, Toast.LENGTH_SHORT).show();

        if(!current_date.equals(saved_date)){

            put_status1  = getSharedPreferences("CARD_STATUS_1",MODE_PRIVATE);
            clickEditor1 = put_status1.edit();
            clickEditor1.putString("card_check1","enable");
            clickEditor1.apply();

            put_status2  = getSharedPreferences("CARD_STATUS_2",MODE_PRIVATE);
            clickEditor2 = put_status2.edit();
            clickEditor2.putString("card_check2","enable");
            clickEditor2.apply();

            put_status3  = getSharedPreferences("CARD_STATUS_3",MODE_PRIVATE);
            clickEditor3 = put_status3.edit();
            clickEditor3.putString("card_check3","enable");
            clickEditor3.apply();

            put_status4  = getSharedPreferences("CARD_STATUS_4",MODE_PRIVATE);
            clickEditor4 = put_status4.edit();
            clickEditor4.putString("card_check4","enable");
            clickEditor4.apply();

            put_status5  = getSharedPreferences("CARD_STATUS_5",MODE_PRIVATE);
            clickEditor5 = put_status5.edit();
            clickEditor5.putString("card_check5","enable");
            clickEditor5.apply();

            put_status6  = getSharedPreferences("CARD_STATUS_6",MODE_PRIVATE);
            clickEditor6 = put_status6.edit();
            clickEditor6.putString("card_check6","enable");
            clickEditor6.apply();

            put_status7  = getSharedPreferences("CARD_STATUS_7",MODE_PRIVATE);
            clickEditor7 = put_status7.edit();
            clickEditor7.putString("card_check7","enable");
            clickEditor7.apply();

            put_status8  = getSharedPreferences("CARD_STATUS_8",MODE_PRIVATE);
            clickEditor8 = put_status8.edit();
            clickEditor8.putString("card_check8","enable");
            clickEditor8.apply();

            put_status9  = getSharedPreferences("CARD_STATUS_9",MODE_PRIVATE);
            clickEditor9 = put_status9.edit();
            clickEditor9.putString("card_check9","enable");
            clickEditor9.apply();

            put_status10  = getSharedPreferences("CARD_STATUS_10",MODE_PRIVATE);
            clickEditor10 = put_status10.edit();
            clickEditor10.putString("card_check10","enable");
            clickEditor10.apply();
        }else {
            //    card 10
            put_status10 = getSharedPreferences("CARD_STATUS_10", MODE_PRIVATE);
            String ss10 = put_status10.getString("card_check10", "0");
            if (ss10.equals("disable10")) {
                indicator10.setBackgroundResource(R.drawable.white_round);
                card10.setClickable(false);
            }
            //card 9
            put_status9 = getSharedPreferences("CARD_STATUS_9", MODE_PRIVATE);
            String ss9 = put_status9.getString("card_check9", "0");
            if (ss9.equals("disable9")) {
                indicator9.setBackgroundResource(R.drawable.white_round);
                card9.setClickable(false);
            }
            //card8
            put_status8 = getSharedPreferences("CARD_STATUS_8", MODE_PRIVATE);
            String ss8 = put_status8.getString("card_check8", "0");
            if (ss8.equals("disable8")) {
                indicator8.setBackgroundResource(R.drawable.white_round);
                card8.setClickable(false);
            }
            //card7
            put_status7 = getSharedPreferences("CARD_STATUS_7", MODE_PRIVATE);
            String ss7 = put_status7.getString("card_check7", "0");
            if (ss7.equals("disable7")) {
                indicator7.setBackgroundResource(R.drawable.white_round);
                card7.setClickable(false);
            }
            //card6
            put_status6 = getSharedPreferences("CARD_STATUS_6", MODE_PRIVATE);
            String ss6 = put_status6.getString("card_check6", "0");
            if (ss6.equals("disable6")) {
                indicator6.setBackgroundResource(R.drawable.white_round);
                card6.setClickable(false);
            }
            //card5
            put_status5 = getSharedPreferences("CARD_STATUS_5", MODE_PRIVATE);
            String ss5 = put_status5.getString("card_check5", "0");
            if (ss5.equals("disable5")) {
                indicator5.setBackgroundResource(R.drawable.white_round);
                card5.setClickable(false);
            }
            //card4
            put_status4 = getSharedPreferences("CARD_STATUS_4", MODE_PRIVATE);
            String ss4 = put_status4.getString("card_check4", "0");
            if (ss4.equals("disable4")) {
                indicator4.setBackgroundResource(R.drawable.white_round);
                card4.setClickable(false);
            }
            //card3
            put_status3 = getSharedPreferences("CARD_STATUS_3", MODE_PRIVATE);
            String ss3 = put_status3.getString("card_check3", "0");

            if (ss3.equals("disable3")) {
                indicator3.setBackgroundResource(R.drawable.white_round);
                card3.setClickable(false);
            }
            //card2
            put_status2 = getSharedPreferences("CARD_STATUS_2", MODE_PRIVATE);
            String ss2 = put_status2.getString("card_check2", "0");
            if (ss2.equals("disable2")) {
                indicator2.setBackgroundResource(R.drawable.white_round);
                card2.setClickable(false);
            }
                //card1
                put_status1 = getSharedPreferences("CARD_STATUS_1", MODE_PRIVATE);
                String ss1 = put_status1.getString("card_check1", "0");
                if (ss1.equals("disable1")) {
                    indicator1.setBackgroundResource(R.drawable.white_round);
                    card1.setClickable(false);
                }
        }


          loadVideoAd();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        loadVideoAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadVideoAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideoAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisteredBrodcastReciver();
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
    private void loadVideoAdandshow() {
        loadVideoAd();

    }
    private void loadVideoAd() {
              rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    private void progress_dilouge(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
    }
}