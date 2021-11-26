package com.cashii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;

import static com.cashii.Config.DAILY_SPIN;

public class SpinActivity extends AppCompatActivity {
    CardView card_spin;
    LuckyWheelView luckyWheelView;
    int coins;
    int coinCount;
    RewardedVideoAd rewardedVideoAd;
    RewardedVideoAdListener rewardedVideoAdListener;
    TextView total_spin_textview, total_score_text;
    int spin_limit = 0;
    AdView adView;
    BroadcastReceiver broadcastReceiver, myreciver;
    SpinActivity spinActivity;
    //362161925035368_382411896343704   Rewarded Video
    Intent intent = new Intent();
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

        SharedPreferences show_spin_limit = getSharedPreferences("SpinApp",MODE_PRIVATE);
        String updated_spin_limit = show_spin_limit.getString("spin_limit","0");
        total_spin_textview.setText(show_spin_limit.getString("spin_limit","0"));


        if(total_spin_textview.getText().toString().equals("0")) {
            card_spin.setClickable(false);
            SharedPreferences getDate = getSharedPreferences("DATE",MODE_PRIVATE);
            String saved_date = getDate.getString("s_date","0");
            Toast.makeText(this, saved_date, Toast.LENGTH_SHORT).show();
            @SuppressLint("SimpleDateFormat")
            String current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            Toast.makeText(this, current_date, Toast.LENGTH_SHORT).show();
            SharedPreferences getValue = getSharedPreferences("SpinApp", MODE_PRIVATE);

            String prev_day_spins = total_spin_textview.getText().toString();
            int p_d_l = Integer.parseInt(prev_day_spins);
            int D_P = Integer.parseInt(DAILY_SPIN);

            if(!saved_date.equals(current_date)) {
                SharedPreferences editValue = getSharedPreferences("SpinApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = editValue.edit();
                editor.putString("spin_limit", DAILY_SPIN);
                editor.apply();
                recreate();
            }
            card_spin.setVisibility(View.INVISIBLE);
            Toast.makeText(SpinActivity.this, "Try Tomorrow", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences getshared = getSharedPreferences("spin",MODE_PRIVATE);
        String v1 = getshared.getString("Coins","0");
        total_score_text.setText(getshared.getString("Coins", "0"));

        AudienceNetworkAds.initialize(this);

        broadcastReceiver = new NetworkChangeReceiver();
        myreciver   = new MyReceiver();
        registerBrodcastReciver();
        loadVideoAd();
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
    protected void onPause() {
        super.onPause();
        loadVideoAd();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadVideoAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideoAd();
    }

    private void loadVideoAd() {
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);
        card_spin      = findViewById(R.id.card_spin);
        total_spin_textview = findViewById(R.id.total_spin_limit);
        total_score_text = findViewById(R.id.total_points);


        total_spin_textview.setText("5");


        List<LuckyItem> data = new ArrayList<>();


        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "10";
        //luckyItem1.icon = R.drawable.roundedlogo;
        luckyItem1.color = Color.parseColor("#CDDC39");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "20";
        // luckyItem2.icon = R.drawable.roundedlogo;
        luckyItem2.color = Color.parseColor("#FF5722");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "30";
        // luckyItem3.icon = R.drawable.roundedlogo;
        luckyItem3.color = Color.parseColor("#CDDC39");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "40";
        //   luckyItem4.icon = R.drawable.roundedlogo;
        luckyItem4.color = Color.parseColor("#FF5722");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "50";
        // luckyItem5.icon = R.drawable.roundedlogo;
        luckyItem5.color = Color.parseColor("#CDDC39");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "60";
        // luckyItem6.icon = R.drawable.roundedlogo;
        luckyItem6.color = Color.parseColor("#FF5722");
        data.add(luckyItem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "70";
        //luckyItem7.icon = R.drawable.roundedlogo;
        luckyItem7.color = Color.parseColor("#CDDC39");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "80";
        //luckyItem8.icon = R.drawable.roundedlogo;
        luckyItem8.color = Color.parseColor("#FF5722");
        data.add(luckyItem8);
        LuckyItem luckyItem9 = new LuckyItem();
        luckyItem9.topText = "90";
        // luckyItem9.icon = R.drawable.roundedlogo;
        luckyItem9.color = Color.parseColor("#CDDC39");
        data.add(luckyItem9);

        LuckyItem luckyItem10 = new LuckyItem();
        luckyItem10.topText = "100";
        //   luckyItem10.icon = R.drawable.roundedlogo;
        luckyItem10.color = Color.parseColor("#FF5722");
        data.add(luckyItem10);


        LuckyItem luckyItem11 = new LuckyItem();
        luckyItem11.topText = "110";
        // luckyItem9.icon = R.drawable.roundedlogo;
        luckyItem11.color = Color.parseColor("#CDDC39");
        data.add(luckyItem11);

        LuckyItem luckyItem120 = new LuckyItem();
        luckyItem120.topText = "120";

        //   luckyItem10.icon = R.drawable.roundedlogo;
        luckyItem120.color = Color.parseColor("#FF5722");
        data.add(luckyItem120);

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                if (index == 0) {
                    Toast.makeText(SpinActivity.this, "10 Selected ", Toast.LENGTH_SHORT).show();
                    coins = 10;
                } else if (index == 1) {
                    coins = 20;
                    Toast.makeText(SpinActivity.this, "20 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 2) {
                    coins = 30;
                    Toast.makeText(SpinActivity.this, "30 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 3) {
                    coins = 40;
                    Toast.makeText(SpinActivity.this, "40 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 4) {
                    coins = 50;
                    Toast.makeText(SpinActivity.this, "50 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 5) {
                    Toast.makeText(SpinActivity.this, "60 Selected ", Toast.LENGTH_SHORT).show();
                    coins = 60;
                } else if (index == 6) {
                    coins = 70;
                    Toast.makeText(SpinActivity.this, "70 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 7) {
                    coins = 80;
                    Toast.makeText(SpinActivity.this, "80 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 8) {
                    coins = 90;
                    Toast.makeText(SpinActivity.this, "90 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 9) {
                    coins = 100;
                    Toast.makeText(SpinActivity.this, "100 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 10) {
                    coins = 110;
                    Toast.makeText(SpinActivity.this, "110 Selected ", Toast.LENGTH_SHORT).show();
                } else if (index == 11) {
                    coins = 120;
                    Toast.makeText(SpinActivity.this, "120 Selected ", Toast.LENGTH_SHORT).show();
                }
                card_spin.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                card_spin.setClickable(true);

                show_winning_box();
            }
        });
        card_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_spin.setCardBackgroundColor(getResources().getColor(R.color.colorDisable));
                card_spin.setClickable(false);


                String ch = total_spin_textview.getText().toString();
                int index = getRandomIndex();
                luckyWheelView.startLuckyWheelWithTargetIndex(index);
            }
        });

        rewardedVideoAd = new RewardedVideoAd(this, getString(R.string.rewarded_video_ad));
        rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {

                // Rewarded video ad failed to load
                Toast.makeText(SpinActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                Log.e("", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Toast.makeText(SpinActivity.this, "Video Ad Loaded", Toast.LENGTH_SHORT).show();
                int sl = Integer.parseInt(total_spin_textview.getText().toString());
                spin_limit = sl;
                spin_limit--;
                if(spin_limit<0){
                    spin_limit = 0;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("SpinApp",MODE_PRIVATE);
                SharedPreferences.Editor spin_c = sharedPreferences.edit();
                spin_c.putString("spin_limit",String.valueOf(spin_limit));
                spin_c.apply();

                SharedPreferences show_spin_limit = getSharedPreferences("SpinApp",MODE_PRIVATE);
                String updated_spin_limit = show_spin_limit.getString("spin_limit","0");
                total_spin_textview.setText(updated_spin_limit);

                if(updated_spin_limit.equals("1")){
                    @SuppressLint("SimpleDateFormat")
                    String format = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    //  Toast.makeText(getApplicationContext(), format, Toast.LENGTH_SHORT).show();
                    SharedPreferences getDate = getSharedPreferences("DATE",MODE_PRIVATE);
                    SharedPreferences.Editor editor = getDate.edit();
                    editor.putString("s_date",format);
                    editor.apply();
                }
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
                coinCount = Integer.parseInt(coins_s.getString("Coins", "0"));
                coinCount = coinCount + (coins);


                SharedPreferences.Editor coinsEdit = coins_s.edit();
                coinsEdit.putString("Coins", String.valueOf(coinCount));
                coinsEdit.apply();

                SharedPreferences getshared = getSharedPreferences("spin",MODE_PRIVATE);
                total_score_text.setText(getshared.getString("Coins", "0"));


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

    }
    public int getRandomRound(){
        Random random = new Random();
        return random.nextInt(10)+15;
    }
    public void PlayGame(View v){
        int index = getRandomIndex();
        luckyWheelView.startLuckyWheelWithTargetIndex(index);
    }
    private int getRandomIndex(){
        int[] index = new int[]{0,1,2,3,4,5,6,7,8,9,10,11};
        int rand = new Random().nextInt(index.length);
        return index[rand];

    }


    public class YourApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            // Initialize the Audience Network SDK
            AudienceNetworkAds.initialize(this);
        }

    }





    @Override
    protected void onDestroy() {
        unregisteredBrodcastReciver();
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        super.onDestroy();
    }

    public void show_winning_box(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.win_coin_diologe,null);

        Button collect_now_btn = v.findViewById(R.id.get_now_btn);
        Button not_now_btn     = v.findViewById(R.id.not_btn);
        TextView text_coins= v.findViewById(R.id.text_coins_alet);

        text_coins.setText(String.valueOf(coins));

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false)
                .create();
        alertDialog.show();
        //text_coins.setText(coins);

        collect_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpinActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                if(rewardedVideoAd.isAdLoaded()) {
                    rewardedVideoAd.show();
                    alertDialog.cancel();
                }else{
                    loadVideoAd();
                    if(rewardedVideoAd.isAdLoaded()){
                        rewardedVideoAd.show();
                    }
                }
                alertDialog.cancel();
            }
        });
        not_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpinActivity.this, "Coins Are Not Collected", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });
    }
}