package me.mikasa.science.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseRecyclerViewAdapter;
import me.mikasa.science.bean.GalleryItem;
import me.mikasa.science.view.ScaleImageView;

/**
 * Created by mikasacos on 2018/9/13.
 */

public class StarGalleryAdapter extends BaseRecyclerViewAdapter<GalleryItem> {
    private Context mContext;
    public StarGalleryAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_gallery_star,parent,false);
        return new StarGalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StarGalleryViewHolder)holder).bindView(mDataList.get(position));
    }

    class StarGalleryViewHolder extends BaseRvViewHolder{
        @BindView(R.id.astron_image)
        ScaleImageView galleryIv;
        @BindView(R.id.astron_image_desc)
        TextView galleryTv;
        StarGalleryViewHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(GalleryItem item) {
            Glide.with(mContext).load(item.getPicUrl())
                    .placeholder(R.drawable.ic_bili)
                    .error(R.drawable.ic_bili)
                    .into(galleryIv);
            galleryTv.setText(item.getTitle());
        }
    }
}
