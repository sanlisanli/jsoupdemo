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
import me.mikasa.science.bean.Book;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class BookAdapter extends BaseRecyclerViewAdapter<Book> {
    private Context mContext;
    public BookAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_dangdang_book,parent,false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BookHolder)holder).bindView(mDataList.get(position));
    }

    class BookHolder extends BaseRvViewHolder{
        @BindView(R.id.book_title)
        TextView title;
        @BindView(R.id.bookImage)
        ImageView img;
        @BindView(R.id.book_desc)
        TextView desc;
        @BindView(R.id.book_author)
        TextView author;
        @BindView(R.id.book_price)
        TextView price;
        BookHolder(View itemView){
            super(itemView);
        }

        @Override
        protected void bindView(Book book) {
            title.setText(book.getTitle());
            desc.setText(book.getDesc());
            author.setText(book.getAuthor());
            price.setText(book.getPrice());
            Glide.with(mContext).load(book.getImgUrl())
                    .placeholder(R.drawable.ic_bili)
                    .error(R.drawable.ic_bili)
                    .into(img);
        }
    }
}
