package me.mikasa.science.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wingsofts.byeburgernavigationview.ByeBurgerBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import me.mikasa.science.R;
import me.mikasa.science.base.BaseActivity;
import me.mikasa.science.fragment.DiscoveryFragment;
import me.mikasa.science.fragment.GalleryFragment;
import me.mikasa.science.fragment.HomeFragment;
import me.mikasa.science.fragment.MineFragment;
import me.mikasa.science.listener.PermissionListener;
import me.mikasa.science.utils.transformer.ScaleInOutTransformer;
import me.mikasa.science.view.NoScrollViewPager;
import me.mikasa.science.view.bottombar.BottomBarItem;
import me.mikasa.science.view.bottombar.BottomBarLayout;

import static me.mikasa.science.constants.Constant.SearchTextHint;

public class HomeActivity extends BaseActivity implements PermissionListener{
    @BindView(R.id.main_view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_hint)
    TextView searchHint;
    @BindView(R.id.search_rootview)
    LinearLayout search_root;
    private List<Fragment> fragmentList=new ArrayList<>();
    private ByeBurgerBehavior mBehavior;
    private HomeFragment homeFragment;
    private int i=randomHint();
    private static boolean toolbarIsVisible;
    private BottomBarLayout bottomView;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        toolbarIsVisible=true;
        setSupportActionBar(toolbar);
        bottomView=(BottomBarLayout)findViewById(R.id.main_bottom);
        //mBehavior=ByeBurgerBehavior.from(fab);
        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        requestRuntimePermission(permissions,this);//申请权限
        searchHint.setText(SearchTextHint[i]);
    }

    @Override
    protected void initListener() {
        search_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("hint",SearchTextHint[i]);
                startActivity(intent);
            }
        });
        bottomView.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem item, int prePos, int curPos) {
                switch (curPos){
                    case 0:
                        //mBehavior.show();
                        if (!toolbarIsVisible){
                            toolbar.setVisibility(View.VISIBLE);
                            toolbarIsVisible=true;
                        }
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        if (toolbarIsVisible){
                            toolbar.setVisibility(View.INVISIBLE);
                            toolbarIsVisible=false;
                        }
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        if (toolbarIsVisible){
                            toolbar.setVisibility(View.INVISIBLE);
                            toolbarIsVisible=false;
                        }
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        if (toolbarIsVisible){
                            toolbar.setVisibility(View.INVISIBLE);
                            toolbarIsVisible=false;
                        }
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    private void initFragment(){
        fragmentList=new ArrayList<>();
        homeFragment=HomeFragment.newInstance();
        fragmentList.add(homeFragment);
        fragmentList.add(GalleryFragment.newInstance());
        fragmentList.add(DiscoveryFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageTransformer(true,new ScaleInOutTransformer());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    @Override
    public void onGranted() {
        initFragment();
    }

    @Override
    public void onDenied(List<String> deniedPermission) {
        for (String s:deniedPermission){
            showToast("你拒绝了权限 "+s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_search:
                Intent intent=new Intent(this,SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.home_skin:
                showToast("待开发");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int randomHint(){
        Random random=new Random(System.currentTimeMillis());
        int i=random.nextInt(SearchTextHint.length);//使用种子为66的Random对象生成[0,length)内伪随机int
        return i;
    }

}
