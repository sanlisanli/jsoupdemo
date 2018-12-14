package me.mikasa.science.adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseRecyclerViewAdapter;
import me.mikasa.science.bean.GuoKe;
import me.mikasa.science.view.ScaleImageView;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GuokeAdapter extends BaseRecyclerViewAdapter<GuoKe> {
    private Context mContext;
    public GuokeAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_guoke,parent,false);
        return new GuokeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GuokeHolder)holder).bindView(mDataList.get(position));
    }

    class GuokeHolder extends BaseRvViewHolder{
        @BindView(R.id.tv_label)
        TextView label;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_comment)
        TextView comment;
        @BindView(R.id.iv_image)
        ScaleImageView img;
        @BindView(R.id.tv_desc)
        TextView desc;
        GuokeHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(GuoKe guoKe) {
            label.setText(guoKe.getLabel());
            title.setText(guoKe.getTitle());
            author.setText(guoKe.getAuthor());
            comment.setText(guoKe.getComment());
            desc.setText(guoKe.getDesc());
            Glide.with(mContext).load(guoKe.getImgUrl())
                    .placeholder(R.drawable.ic_bili)
                    .error(R.drawable.ic_bili)
                    .into(img);
        }
    }
}
