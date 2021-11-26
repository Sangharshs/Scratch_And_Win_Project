//package com.cashii.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cashii.Model.ImageModel;
//import com.cashii.VideoPlayerViewHolder;
//import com.cashii.cashii.R;
//import com.facebook.ads.AdOptionsView;
//import com.facebook.ads.NativeAd;
//import com.facebook.ads.NativeAdLayout;
//import com.facebook.ads.NativeAdsManager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Viewholder> {
//    public static final int AD_DISPLAY_FREQUENCY_LIMIT = 4;
//    public static final int POST_TYPE = 0;
//    public static final int AD_TYPE = 1;
//    List<NativeAd> nativeAdList;
//    Context context;
//    List<ImageModel> imageModels;
//
//    private NativeAd nativeAd;
//    NativeAdsManager nativeAdsManager;
//    LinearLayout nativeAdContainer;
//
//    public ImageAdapter(List<NativeAd> nativeAdList, NativeAd nativeAd, NativeAdsManager nativeAdsManager, LinearLayout nativeAdContainer) {
//        this.nativeAdList = nativeAdList;
//        this.nativeAd = nativeAd;
//        this.nativeAdsManager = nativeAdsManager;
//        this.nativeAdContainer = nativeAdContainer;
//        nativeAdList = new ArrayList<>();
//        imageModels = new ArrayList<>();
//    }
//
//    @NonNull
//    @Override
//    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        if (viewType == AD_TYPE) {
//////         2
////            NativeAdLayout nativeAdLayout  = (NativeAdLayout) LayoutInflater.from(parent.getContext())
////                    .inflate(R.layout.native_ad_fb_ayout, parent,false);
////            if(nativeAdLayout == null){
////                nativeAdLayout.setVisibility(View.GONE);
////            }
////            return new UnifiedFbHolder(nativeAdLayout);
////
////        }else
////
////        {
////            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_list_item,viewGroup,false);
////            return new VideoPlayerViewHolder(view);
////        }
////        return null;
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
////        int viewType = getItemViewType(i);
////        switch (viewType)
////        {
////            case AD_TYPE:
////                NativeAd ad ;
////                if(nativeAdList.size() > position/AD_DISPLAY_FREQUENCY_LIMIT){
////                    ad = nativeAdList.get(position/AD_DISPLAY_FREQUENCY_LIMIT);
////                }else {
////                    ad = nativeAdsManager.nextNativeAd();
////                    if (ad != null) {
////                        if (!ad.isAdInvalidated()) {
////                            nativeAdList.add(ad);
////                        }
////                    }
////                }
////                UnifiedFbHolder adHolder = (UnifiedFbHolder)holder;
////                adHolder.adChoicesContainer.removeAllViews();
////
////                if(ad != null)
////                {
////                    //MainActivity.inflateAd(nativeAd);
////                    //Toast.makeText(context, ""+adHolder.tvAdTitle, Toast.LENGTH_SHORT).show();
////                    adHolder.tvAdTitle.setText(ad.getAdvertiserName());
////                    adHolder.tvAdBody.setText(ad.getAdBodyText());
////                    adHolder.tvAdSocialContext.setText(ad.getAdSocialContext());
////                    adHolder.tvAdSponsoredLabel.setText("Sponsored");
////                    adHolder.btncallToAction.setText(ad.getAdCallToAction());
////                    adHolder.btncallToAction.setVisibility(ad.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
////
////                    AdOptionsView adOptionsView = new AdOptionsView(,ad,adHolder.nativeAdLayout);
////                    adHolder.adChoicesContainer.addView(adOptionsView,0);
////
////                    List<View> clickableView = new ArrayList<>();
////                    clickableView.add(adHolder.ivAdIcon);
////                    clickableView.add(adHolder.mvAdMedia);
////                    clickableView.add(adHolder.btncallToAction);
////
////
////                    ad.registerViewForInteraction(
////                            adHolder.nativeAdLayout,adHolder.mvAdMedia,adHolder.ivAdIcon,
////                            clickableView);
////                }
////
////                break;
////            case POST_TYPE:
////                ((VideoPlayerViewHolder)viewHolder).onBind(mediaObjects.get(i), requestManager);
////
////
////                break;
////        }
////    }
////
////    @Override
////    public int getItemCount() {
////        return 0;
////    }
////
////    public class Viewholder extends RecyclerView.ViewHolder {
////        public Viewholder(@NonNull View itemView) {
////            super(itemView);
////        }
////    }
//    }
