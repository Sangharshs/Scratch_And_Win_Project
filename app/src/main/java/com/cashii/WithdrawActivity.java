package com.cashii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cashii.cashii.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cashii.Config.Main_Url;

public class WithdrawActivity extends AppCompatActivity {

    public static int MINIMUM_WITHDRAWAL_AMOUNT = 0;
    TextView textView,showwininInr,showwininDollar;
    Button button;
    int minimum_amount = MINIMUM_WITHDRAWAL_AMOUNT;
    int available_amount;
    EditText name_editText,email_edittext, country_edittext,number_edittext,amount_Edittext;
    RadioGroup radioGroup;
    RadioButton radioButton_paytm,radioButton_paypal;
    String country,name,email,phone_number,amount,payment;
    String witdrawlAmount;
    int remainamount,enteredAmount;
    int selectedID;
    TextView minimum_c;
    private NativeAd nativeAd;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        broadcastReceiver = new NetworkChangeReceiver();
        registerBrodcastReciver();

        textView = findViewById(R.id.total_coins_textView);
        button   = findViewById(R.id.submit);
        radioGroup = findViewById(R.id.radio_group);
        showwininInr = findViewById(R.id.winamountinRupees);
        showwininDollar = findViewById(R.id.winamountinDollar);
        minimum_c = findViewById(R.id.minimum_withdrawal_c);
        radioButton_paytm = findViewById(R.id.radio_paytm);
        radioButton_paypal = findViewById(R.id.radio_paypal);

        //Edit Text
        amount_Edittext = findViewById(R.id.amount_editText);
        country_edittext = findViewById(R.id.country_editText);
        number_edittext = findViewById(R.id.number_edit_text);
        email_edittext = findViewById(R.id.email_editText);
        name_editText = findViewById(R.id.name_edit_text);
        //Edit Text

        selectedID = radioGroup.getCheckedRadioButtonId();
         minimum_c.setText("1. Minimum Withdrawal Coins  "+String.valueOf(MINIMUM_WITHDRAWAL_AMOUNT));

         if(MINIMUM_WITHDRAWAL_AMOUNT == 0){
            minimum_c.setVisibility(View.INVISIBLE);
         }

         if(MINIMUM_WITHDRAWAL_AMOUNT != 0) {
             button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     available_amount = Integer.parseInt(textView.getText().toString());
                   //  amount_Edittext.setText(String.valueOf(available_amount));

                     if (available_amount > minimum_amount || available_amount == minimum_amount) {
                         witdrawlAmount = amount_Edittext.getText().toString();
                         enteredAmount = Integer.parseInt(amount_Edittext.getText().toString());
                        int w_a = Integer.parseInt(witdrawlAmount);
                        int a_a = Integer.parseInt(String.valueOf(available_amount));
                           if(available_amount > enteredAmount){

                               send_payment_request();
                           }else {
                               Toast.makeText(WithdrawActivity.this, "Your entered amount is greater than available amount", Toast.LENGTH_SHORT).show();
                           }

                     } else {
                         Toast.makeText(WithdrawActivity.this, "Minimum amount is " + minimum_amount, Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }
        //        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(radioGroup == R.id.radio_paypal){
//
//                }
//            }
//        });
        //Toast.makeText(this, payment, Toast.LENGTH_SHORT).show();

    }

     public void send_payment_request(){
         StringRequest stringRequest = new StringRequest(Request.Method.POST, Main_Url+"/payment_request.php",
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         if(response.equals("Success")){
                             Toast.makeText(WithdrawActivity.this, "Done", Toast.LENGTH_LONG).show();
                             SharedPreferences getshared = getSharedPreferences("spin",MODE_PRIVATE);
                             SharedPreferences.Editor edit_balance = getshared.edit();
                             enteredAmount = Integer.parseInt(amount_Edittext.getText().toString());
                             remainamount = available_amount - enteredAmount;
                             edit_balance.putString("Coins",String.valueOf(remainamount));
                             edit_balance.apply();
                             Toast.makeText(WithdrawActivity.this, response, Toast.LENGTH_SHORT).show();
                             finish();
                         }
                         else{
                            // progressDialog.dismiss();
                             Toast.makeText(WithdrawActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(WithdrawActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
             }
         }
         ) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 final Map<String, String> params = new HashMap<String, String>();

                             country = country_edittext.getText().toString();
                             email = email_edittext.getText().toString();
                             amount = amount_Edittext.getText().toString();
                             name   = name_editText.getText().toString();
                             phone_number = number_edittext.getText().toString();
                             final String value = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()))
                             .getText().toString();


                             params.put("username", name);
                             params.put("amount", amount);
                             params.put("payment",value);
                             params.put("email", email);
                             params.put("city", country);

                 return params;             }

         };
         Toast.makeText(this, email+amount+name+country+phone_number, Toast.LENGTH_SHORT).show();

         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);
    }
    private void loadNativeAd() {
             nativeAd = new NativeAd(this,getString(R.string.native_ad_fb));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
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
                           }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
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
        LayoutInflater inflater = LayoutInflater.from(WithdrawActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        NativeAdLayout adView = (NativeAdLayout) inflater.inflate(R.layout.native_ad_fb_ayout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(WithdrawActivity.this, nativeAd, nativeAdLayout);
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


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences getshared = getSharedPreferences("spin",MODE_PRIVATE);
        String v1 = getshared.getString("Coins","0");
        textView.setText(getshared.getString("Coins", "0"));
     //   amount_Edittext.setText(v1);

        double inrValue = 20;
        double dollarVlue = 75000;
              loadSetting();
        loadNativeAd();
        double inr = Integer.parseInt(v1)/20d;
       // double dollar = Integer.parseInt(v1)/75000d;

        showwininInr.setText(String.valueOf(inr));
       // showwininDollar.setText(String.valueOf(dollar));
    }

    public void loadSetting(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,Main_Url+"/setting.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        MINIMUM_WITHDRAWAL_AMOUNT = object.getInt("minimum");
                        String show = String.valueOf(MINIMUM_WITHDRAWAL_AMOUNT);
                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                 recreate();
                // progressBar.setVisibility(View.GONE);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisteredBrodcastReciver();
    }

}