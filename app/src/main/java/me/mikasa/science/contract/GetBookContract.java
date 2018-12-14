package me.mikasa.science.contract;

import java.util.List;

import me.mikasa.science.bean.Book;

/**
 * Created by mikasacos on 2018/9/21.
 */

public interface GetBookContract {
    interface View{
        void getBookSuccess(List<Book> list);
        void getError(String msg);
    }
    interface Model{
        void getBook(int page);//没有getError,getSuccess
    }
    interface Presenter{
        void getBook(int page);
        void getBookSuccess(List<Book>list);
        void getError(String msg);
    }
}
