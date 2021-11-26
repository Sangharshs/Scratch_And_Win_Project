package com.cashii.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdsManager;
import com.cashii.Model.MediaObject;
import com.cashii.cashii.R;
import com.cashii.VideoPlayerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int AD_DISPLAY_FREQUENCY_LIMIT = 4;
    public static final int POST_TYPE = 0;
    public static final int AD_TYPE = 1;
    List<NativeAd> nativeAdList;

    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();
    private RequestManager requestManager;
    private final String TAG = "NativeAdActivity".getClass().getSimpleName();
    private NativeAd nativeAd;
    LinearLayout nativeAdContainer;
    Context context;
    NativeAdsManager nativeAdsManager;
    //AdHolder holder;
    public VideoPlayerRecyclerAdapter(ArrayList<MediaObject> mediaObjects, RequestManager requestManager, Context context, NativeAdsManager nativeAdsManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
        this.context = context;
        this.nativeAdsManager = nativeAdsManager;
        nativeAdList = new ArrayList<>();
        mediaObjects = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == AD_TYPE) {
//         2
           NativeAdLayout nativeAdLayout  = (NativeAdLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.native_ad_fb_ayout, viewGroup,false);
           if(nativeAdLayout == null){
            nativeAdLayout.setVisibility(View.GONE);
           }
            return new UnifiedFbHolder(nativeAdLayout);

        }else

         {
             View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_video_list_item,viewGroup,false);
             return new VideoPlayerViewHolder(view);
        }

  }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
      int viewType = getItemViewType(i);
      switch (viewType)
      {
          case AD_TYPE:
              NativeAd ad ;
              if(nativeAdList.size() > i/AD_DISPLAY_FREQUENCY_LIMIT){
                  ad = nativeAdList.get(i/AD_DISPLAY_FREQUENCY_LIMIT);
              }else {
                  ad = nativeAdsManager.nextNativeAd();
                  if (ad != null) {
                      if (!ad.isAdInvalidated()) {
                          nativeAdList.add(ad);
                      }
                  }
              }
              UnifiedFbHolder adHolder = (UnifiedFbHolder)viewHolder;
              adHolder.adChoicesContainer.removeAllViews();

              if(ad != null)
              {
                  //MainActivity.inflateAd(nativeAd);
                  //Toast.makeText(context, ""+adHolder.tvAdTitle, Toast.LENGTH_SHORT).show();
                  adHolder.tvAdTitle.setText(ad.getAdvertiserName());
                  adHolder.tvAdBody.setText(ad.getAdBodyText());
                  adHolder.tvAdSocialContext.setText(ad.getAdSocialContext());
                  adHolder.tvAdSponsoredLabel.setText("Sponsored");
                  adHolder.btncallToAction.setText(ad.getAdCallToAction());
                  adHolder.btncallToAction.setVisibility(ad.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);

                  AdOptionsView adOptionsView = new AdOptionsView(context,ad,adHolder.nativeAdLayout);
                  adHolder.adChoicesContainer.addView(adOptionsView,0);

                  List<View> clickableView = new ArrayList<>();
                  clickableView.add(adHolder.ivAdIcon);
                  clickableView.add(adHolder.mvAdMedia);
                  clickableView.add(adHolder.btncallToAction);


                  ad.registerViewForInteraction(
                          adHolder.nativeAdLayout,adHolder.mvAdMedia,adHolder.ivAdIcon,
                          clickableView);
              }

              break;
          case POST_TYPE:
              ((VideoPlayerViewHolder)viewHolder).onBind(mediaObjects.get(i), requestManager);


              break;
      }
    }

    @Override
    public int getItemViewType(int position) {
        return position % AD_DISPLAY_FREQUENCY_LIMIT == 0 && position != 0 ? AD_TYPE:POST_TYPE;
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size()+ nativeAdList.lastIndexOf(10000);
    }



}


