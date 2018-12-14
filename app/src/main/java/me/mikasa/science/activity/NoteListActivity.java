package me.mikasa.science.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.adapter.NoteAdapter;
import me.mikasa.science.base.BaseActivity;
import me.mikasa.science.bean.Note;
import me.mikasa.science.db.NoteManager;
import me.mikasa.science.listener.OnRecyclerViewItemClickListener;
import me.mikasa.science.utils.SpUtil;

public class NoteListActivity extends BaseActivity implements OnRecyclerViewItemClickListener{
    @BindView(R.id.rv_note_list)
    RecyclerView recyclerView;
    @BindView(R.id.note_add)
    FloatingActionButton addNoteBtn;
    @BindView(R.id.toolbar_include)
    Toolbar toolbar;

    private NoteAdapter adapter;
    private NoteManager noteManager;
    private Context mContext;
    private List<Note> noteList;
    private boolean isEditModel=false;

    @Override
    protected int setLayoutResId() {
        mContext=this;
        return R.layout.activity_note_list;
    }

    @Override
    protected void initData() {
        SpUtil.init(this);
        noteManager=new NoteManager(mContext);
        if (noteManager.isEmpty()){
            noteManager.initTable();
            SpUtil.getInstance().setInt("noteNum",2);
        }
        noteList=new ArrayList<>();
        adapter=new NoteAdapter(mContext);
        initFromDB();
    }
    private void initFromDB(){
        noteList=noteManager.selectAll();
        Collections.sort(noteList);//排序
        adapter.appendData(noteList);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnRecyclerViewItemClickListener(this);//!!!
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoteListActivity.this,NoteAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        //Toast.makeText(mContext,"你点击了"+pos,Toast.LENGTH_SHORT).show();
        if (isEditModel){
            //如果是删除模式，单击子项变色
            if (noteList.get(pos).isFlag()){//onBindViewHolder
                noteList.get(pos).setFlag(false);
            }else {
                noteList.get(pos).setFlag(true);
            }
            adapter.notifyDataSetChanged();//notice
        }else {
            //非删除模式，单击进入编辑模式
            Intent intent=new Intent(NoteListActivity.this,NoteEditActivity.class);
            intent.putExtra("id",noteList.get(pos).getId());
            intent.putExtra("content",noteList.get(pos).getContent());
            startActivity(intent);
        }
    }
    private void deleteNote(){
        for (int i=noteList.size()-1;i>-1;i--){
            Note note=noteList.get(i);
            if (note.isFlag()){
                noteManager.delete(note.getId());
                noteList.remove(i);
                adapter.notifyItemRemoved(i);
            }
        }
        reload();
    }
    private void reload(){
        if (noteList!=null){
            noteList.clear();
        }
        adapter.updateData(noteList);
        initFromDB();
    }

    @Override
    public boolean onItemLongClick(int pos) {
        if (!isEditModel){
            //进入删除模式
            noteList.get(pos).setFlag(true);
            //pos添加到deleteItem中
            adapter.notifyDataSetChanged();
            isEditModel=true;
            supportInvalidateOptionsMenu();//通知系统更新菜单
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isEditModel){
            //如果是删除模式
            isEditModel=false;
            for (int i=0;i<noteList.size();i++){
                //颜色恢复白色
                noteList.get(i).setFlag(false);
            }
            adapter.notifyDataSetChanged();
            supportInvalidateOptionsMenu();//通知系统更新menu
        }else {
            finish();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (isEditModel){
            getMenuInflater().inflate(R.menu.menu_note_delete,menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_note_list,menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.delete_mode:
                isEditModel=true;
                supportInvalidateOptionsMenu();//通知系统更新菜单
                return true;
            case R.id.note_delete:
                deleteNote();
                isEditModel=false;
                supportInvalidateOptionsMenu();
                return true;
        }
        return true;
    }

}
