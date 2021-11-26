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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.cashii.cashii.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.cashii.Config.DAILY_SCRATCH;
import static com.cashii.Config.DAILY_SPIN;

public class ScratchActivity extends AppCompatActivity {

    public static String up_s = null;
    TextView textView,daily_scratch_limit_textView;
    int scratchCount;
    RewardedVideoAd rewardedVideoAd;
    BroadcastReceiver broadcastReceiver;
    RewardedVideoAdListener rewardedVideoAdListener;
    String r;
    int daily_scratch_limit = 0;
    SimpleDateFormat currentDate;
    ScratchView scratchView;
    CardView cardView;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    AdView adView;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);



        broadcastReceiver = new NetworkChangeReceiver();
        registerBrodcastReciver();


        daily_scratch_limit_textView = findViewById(R.id.daily_scratch_limit);
        textView   = findViewById(R.id.point_Text);
        scratchView = findViewById(R.id.scratch_view);
        cardView = findViewById(R.id.c);

        if(daily_scratch_limit_textView.getText().toString().equals("0")){
            scratchView.setVisibility(View.INVISIBLE);
        }
        Random random = new Random();
        r = String.valueOf(random.nextInt(120));
        textView.setText(r);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Ad....");
        progressDialog.setCancelable(false);

        daily_scratch_limit_textView.setText(String.valueOf(daily_scratch_limit));
        if(scratchCount!=0) {
        SharedPreferences coins_s = getSharedPreferences("spin",MODE_PRIVATE);
        scratchCount = Integer.parseInt(coins_s.getString("Coins", "0"));
        scratchCount = scratchCount + (Integer.parseInt(r));


            SharedPreferences.Editor coinsEdit = coins_s.edit();
            coinsEdit.putString("Coins", String.valueOf(scratchCount));
            coinsEdit.apply();
        }

                        scratchView.setRevealListener(new ScratchView.IRevealListener() {
                            @Override
                            public void onRevealed(ScratchView scratchView) {

                               if(rewardedVideoAd.isAdLoaded()) {
                                   int dsl = Integer.parseInt(daily_scratch_limit_textView.getText().toString());
                                   daily_scratch_limit = dsl;
                                   dsl--;

                                   if (dsl < 0) {
                                       dsl = 0;
                                   }
                                   daily_scratch_limit_textView.setText(String.valueOf(dsl));
                                   String updated_scratch_limit = daily_scratch_limit_textView.getText().toString();
                                   if (updated_scratch_limit.equals("1")) {
                                       @SuppressLint("SimpleDateFormat")
                                       String format = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                                       //  Toast.makeText(getApplicationContext(), format, Toast.LENGTH_SHORT).show();
                                       SharedPreferences getDate = getSharedPreferences("DATE", MODE_PRIVATE);
                                       SharedPreferences.Editor editor = getDate.edit();
                                       editor.putString("s_date", format);
                                       editor.apply();
                                   }


                                   SharedPreferences getScratchLimit = getSharedPreferences("scratch", MODE_PRIVATE);
                                   SharedPreferences.Editor editor = getScratchLimit.edit();
                                   editor.putString("scratch_limit", String.valueOf(dsl));
                                   editor.apply();
                               }
                             new Handler().

                            postDelayed(new Runnable() {
                                @Override
                                public void run () {
                                    show_winning_box();
                                    // Add the line which you want to run after 5 sec.
                                }
                            },4000);
                        }
                    @Override
                    public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {
                        if (percent>=0.9) {
                        }
                    }
                });

            //no previous datetime was saved (allow button click)
            //(don't forget to persist datestring when button is clicked)
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Ad....");
        progressDialog.setCancelable(false);
        rewardedVideoAd = new RewardedVideoAd(this, getString(R.string.rewarded_video_ad));
        rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                Toast.makeText(ScratchActivity.this,"" +error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                // Rewarded video ad failed to load
                             Log.e("", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

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
                Random random = new Random();
                String r = String.valueOf(random.nextInt(120));
                textView.setText(r);

                SharedPreferences coins_s = getSharedPreferences("spin",MODE_PRIVATE);
                scratchCount = Integer.parseInt(coins_s.getString("Coins", "0"));
                scratchCount = scratchCount + (Integer.parseInt(r));

                SharedPreferences.Editor coinsEdit = coins_s.edit();
                coinsEdit.putString("Coins", String.valueOf(scratchCount));
                coinsEdit.apply();

                Toast.makeText(ScratchActivity.this, "Task Complete", Toast.LENGTH_SHORT).show();
                loadVideoAd();
                   recreate();
                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                 loadVideoAd();
            }
        };
    }

    private int getRandomNum(){
        Random random = new Random();
        return random.nextInt(10)+15;
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
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onStart() {
        super.onStart();
        loadVideoAd();

        adView = new AdView(this, getString(R.string.rectangle_ad),  AdSize.RECTANGLE_HEIGHT_250);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();

        SharedPreferences getScratchLimit = getSharedPreferences("scratch",MODE_PRIVATE);
        String updated_scratch_limit = getScratchLimit.getString("scratch_limit","0");

     //   Toast.makeText(this, updated_scratch_limit, Toast.LENGTH_SHORT).show();
        daily_scratch_limit_textView.setText(updated_scratch_limit);
        String s_l_t = daily_scratch_limit_textView.getText().toString();

        int p_d_l = Integer.parseInt(s_l_t);
        int D_P = Integer.parseInt(DAILY_SCRATCH);

        if(updated_scratch_limit.equals("0")) {
            SharedPreferences getDate = getSharedPreferences("DATE", MODE_PRIVATE);
            String saved_date = getDate.getString("s_date", "0");

            @SuppressLint("SimpleDateFormat")
            String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            Toast.makeText(this, current_date, Toast.LENGTH_SHORT).show();

            if (!saved_date.equals(current_date)) {
                SharedPreferences editValue = getSharedPreferences("scratch", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = editValue.edit();
                editor1.putString("scratch_limit", DAILY_SCRATCH);
                editor1.apply();
                recreate();
             //   Toast.makeText(this, "Scratch limit is over", Toast.LENGTH_SHORT).show();
                //finish();
            }
            //finish();
        }

    }

    private void loadVideoAd() {
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    public void show_winning_box(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.win_coin_diologe,null);

        Button collect_now_btn = v.findViewById(R.id.get_now_btn);
        Button not_now_btn     = v.findViewById(R.id.not_btn);
        TextView text_coins= v.findViewById(R.id.text_coins_alet);

        //856369125

        alertDialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false)
                .create();
        alertDialog.show();
        text_coins.setText(r);

        collect_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(rewardedVideoAd.isAdLoaded()){
                     rewardedVideoAd.show();
                 }else{
                     loadVideoAd();
                     Toast.makeText(ScratchActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                     recreate();
                 }
                alertDialog.cancel();
            }
        });
        not_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(ScratchActivity.this, "Coins Are Not Collected", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });
       // startAlertAtParticularTime();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisteredBrodcastReciver();
        // alertDialog.dismiss();
    }
}