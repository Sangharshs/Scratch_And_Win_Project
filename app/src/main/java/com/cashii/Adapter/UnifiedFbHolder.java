package com.cashii.Adapter;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.cashii.cashii.R;

public class UnifiedFbHolder extends RecyclerView.ViewHolder {
    public NativeAdLayout nativeAdLayout;
    public MediaView mvAdMedia, ivAdIcon;
    public TextView tvAdTitle,tvAdBody,tvAdSocialContext,tvAdSponsoredLabel;
    NativeAd nativeAd;

    public Button btncallToAction;
    public LinearLayout adChoicesContainer;

    public UnifiedFbHolder(NativeAdLayout nativeAdLayout) {
        super(nativeAdLayout);
        this.nativeAdLayout = nativeAdLayout;
//        nativeAd.unregisterView();
//        nativeAdLayout = nativeAdLayout.findViewById(R.id.native_ad_container);
        mvAdMedia = nativeAdLayout.findViewById(R.id.native_ad_media);
        tvAdTitle = nativeAdLayout.findViewById(R.id.native_ad_title);
        tvAdBody  = nativeAdLayout.findViewById(R.id.native_ad_body);
        tvAdSocialContext = nativeAdLayout.findViewById(R.id.native_ad_social_context);
        tvAdSponsoredLabel = nativeAdLayout.findViewById(R.id.native_ad_sponsored_label);
        btncallToAction  =  nativeAdLayout.findViewById(R.id.native_ad_call_to_action);
        ivAdIcon         = nativeAdLayout.findViewById(R.id.native_ad_icon);
        adChoicesContainer= nativeAdLayout.findViewById(R.id.ad_choices_container);



    }
}
