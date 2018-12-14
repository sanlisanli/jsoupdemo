package me.mikasa.science.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseActivity;
import me.mikasa.science.bean.Note;
import me.mikasa.science.db.NoteManager;

public class NoteEditActivity extends BaseActivity {
    @BindView(R.id.note_edit)
    EditText noteEditEt;
    @BindView(R.id.toolbar_include)
    Toolbar toolbar;

    private String id;
    private NoteManager noteManager;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_note_edit;
    }

    @Override
    protected void initData() {
        noteManager=new NoteManager(this);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        String content=intent.getStringExtra("content");
        noteEditEt.setText(content);
        noteEditEt.setSelection(content.length());
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
    }
    private void goBack(){
        if (TextUtils.isEmpty(noteEditEt.getText())){
            noteManager.delete(id);
            finish();
        }else {
            String s=noteEditEt.getText().toString();
            Note note=new Note();
            note.setId(id);
            note.setContent(s);
            Date date=new Date();
            note.setLastTime(Long.toString(date.getTime()));
            noteManager.update(note);
            finish();
        }
    }
    @Override
    protected void initListener() {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                goBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
