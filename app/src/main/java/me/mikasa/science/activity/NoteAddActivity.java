package me.mikasa.science.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseActivity;
import me.mikasa.science.bean.Note;
import me.mikasa.science.db.NoteManager;
import me.mikasa.science.utils.SpUtil;

public class NoteAddActivity extends BaseActivity{
    @BindView(R.id.note_add)
    EditText noteAddEt;
    @BindView(R.id.toolbar_include)
    Toolbar toolbar;

    private NoteManager noteManager;
    private Context mContext;
    @Override
    protected int setLayoutResId() {
        mContext=this;
        SpUtil.init(mContext);
        return R.layout.activity_note_add;
    }

    @Override
    protected void initData() {
        noteManager=new NoteManager(mContext);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
    }

    @Override
    protected void initListener() {
    }
    private void goBack(){
        if (!TextUtils.isEmpty(noteAddEt.getText())){
            String s=noteAddEt.getText().toString();
            Note note=new Note();
            int i=SpUtil.getInstance().getInt("noteNum",-1);
            note.setId(String.valueOf(i+1));
            note.setContent(s);
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
            String firstTime=sdf.format(date);
            note.setFirstTime(firstTime);
            note.setLastTime(Long.toString(date.getTime()));
            noteManager.insert(note);
            SpUtil.getInstance().setInt("noteNum",i+1);
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goBack();
                break;
        }
        return true;
    }
}
