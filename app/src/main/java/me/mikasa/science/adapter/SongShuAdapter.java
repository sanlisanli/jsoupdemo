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
import me.mikasa.science.bean.SongShu;
import me.mikasa.science.view.ScaleImageView;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class SongShuAdapter extends BaseRecyclerViewAdapter<SongShu> {
    private Context mContext;
    public SongShuAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_songshu,parent,false);
        return new SongShuHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SongShuHolder)holder).bindView(mDataList.get(position));
    }

    class SongShuHolder extends BaseRvViewHolder{
        @BindView(R.id.tv_category)
        TextView category;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_comment)
        TextView comment;
        @BindView(R.id.iv_image)
        ImageView img;
        @BindView(R.id.tv_desc)
        TextView desc;
        SongShuHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(SongShu songShu) {
            category.setText(songShu.getCategory());
            title.setText(songShu.getTitle());
            author.setText(songShu.getAuthor());
            comment.setText(songShu.getComment());
            desc.setText(songShu.getDesc());
            Glide.with(mContext).load(songShu.getImgUrl())
                    .placeholder(R.drawable.ic_bili)
                    .error(R.drawable.ic_bili)
                    .into(img);
        }
    }
}
