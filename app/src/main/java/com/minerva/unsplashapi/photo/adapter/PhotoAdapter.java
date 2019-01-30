package com.minerva.unsplashapi.photo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minerva.unsplashapi.R;
import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.ui.AdaptiveImageView;
import com.minerva.unsplashapi.common.ui.activity.PhotoDetailActivity;
import com.minerva.unsplashapi.common.utils.ColorUtils;
import com.minerva.unsplashapi.common.utils.TypefaceUtils;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context mContext;
    private List<Photo> mPhotos;

    public PhotoAdapter(Context context, List<Photo> list) {
        mContext = context;
        mPhotos = list;
        setHasStableIds(true);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Glide
                .with(mContext)
                .load(mPhotos.get(position).urls.small)
                .into(holder.imageView);
        holder.background.setBackgroundColor(
                ColorUtils.calcCardBackgroundColor(
                        mContext,
                        mPhotos.get(position).color));
        holder.title.setText("By " + mPhotos.get(position).user.name + ", On " +
                mPhotos.get(position).created_at.split("T")[0]);


    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public long getItemId(int position) {
        return mPhotos.get(position).urls.regular.hashCode();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout background;
        public AdaptiveImageView imageView;
        public TextView title;

        public PhotoViewHolder(View itemView, int position) {
            super(itemView);
            this.background = itemView.findViewById(R.id.item_photo_background);
            background.setOnClickListener(this);

            this.imageView = itemView.findViewById(R.id.item_photo_image);
            this.title = itemView.findViewById(R.id.item_photo_title);
            TypefaceUtils.setTypeface(itemView.getContext(), title);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_photo_background:
                    Intent intent = new Intent();
                    intent.putExtra("id", mPhotos.get(getAdapterPosition()).id);
                    intent.putExtra("avatar", mPhotos.get(getAdapterPosition()).user.profile_image.large);
                    intent.setClass(mContext, PhotoDetailActivity.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}
