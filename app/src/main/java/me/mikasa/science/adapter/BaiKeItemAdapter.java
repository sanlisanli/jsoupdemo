package me.mikasa.science.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseRecyclerViewAdapter;
import me.mikasa.science.bean.BaiKeItem;
import me.mikasa.science.view.ScaleImageView;

/**
 * Created by mikasacos on 2018/9/24.
 */

public class BaiKeItemAdapter extends BaseRecyclerViewAdapter<BaiKeItem> {
    private Context mContext;
    public BaiKeItemAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_bai_ke,parent,false);
        return new BaiKeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaiKeHolder)holder).bindView(mDataList.get(position));
    }

    class BaiKeHolder extends BaseRvViewHolder{
        @BindView(R.id.iv_baike)
        ScaleImageView imageView;
        @BindView(R.id.tv_baike)
        TextView textView;
        public BaiKeHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(BaiKeItem baiKeItem) {
            textView.setText(baiKeItem.getTitle());
            imageView.setImageBitmap(baiKeItem.getImg());
        }
    }
}
