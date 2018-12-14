package me.mikasa.science.presenter;

import java.util.List;

import me.mikasa.science.bean.Book;
import me.mikasa.science.contract.GetBookContract;
import me.mikasa.science.model.GetBookModelImpl;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class GetBookPresneterImpl implements GetBookContract.Presenter {
    private GetBookContract.Model mModel;
    private GetBookContract.View mView;
    public GetBookPresneterImpl(GetBookContract.View view){
        this.mView=view;
        mModel=new GetBookModelImpl(this);
    }

    @Override
    public void getBook(int page) {
        mModel.getBook(page);
    }

    @Override
    public void getBookSuccess(List<Book> list) {
        mView.getBookSuccess(list);
    }

    @Override
    public void getError(String msg) {
        mView.getError(msg);
    }
}
