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
import me.mikasa.science.bean.TianWen;

/**
 * Created by mikasacos on 2018/9/12.
 */

public class TianWenAdapter extends BaseRecyclerViewAdapter<TianWen> {
    private Context mContext;
    public TianWenAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_astron,parent,false);
        return new TianWenHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TianWenHolder)holder).bindView(mDataList.get(position));//ViewHolder-->TianWenHolder
    }

    class TianWenHolder extends BaseRvViewHolder{
        @BindView(R.id.articleTitle)
        TextView title;
        @BindView(R.id.articleSummary)
        TextView summary;
        @BindView(R.id.articleImage)
        ImageView img;
        @BindView(R.id.astron_date)
        TextView date;
        TianWenHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(TianWen tianWen) {
            title.setText(tianWen.getTitle());
            summary.setText(tianWen.getDes());
            date.setText(tianWen.getDate());
            Glide.with(mContext).load(tianWen.getPicUrl())
                    .error(R.drawable.ic_bili)
                    .placeholder(R.drawable.ic_bili)
                    .into(img);
        }
    }
}
