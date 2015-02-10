package lt.jsj.qust.jjlin;

import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Random;

import lt.jsj.qust.jjlin.adapter.ViewPagerAdapter;
import lt.jsj.qust.jjlin.fragment.ZiLiaoFragment;
import lt.jsj.qust.jjlin.views.SlidingTabLayout;


public class RealMainActivity2 extends ActionBarActivity  implements ZiLiaoFragment.OnFragmentInteractionListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    static int indicatorcolor;   //
    static int[] colors={R.color.red,R.color.blue,
            R.color.accent_material_dark,Color.GREEN,Color.YELLOW};
    private ListView mDrawerList;
    ViewPager pager;
    Random random;
    int r;
    private String titles[] = new String[]{"资料", "专辑", "图片", "more图"
            , "MV", "电视剧", "微博", "新闻"};
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random=new Random();
        r=random.nextInt(colors.length);
        indicatorcolor=this.getResources().getColor(R.color.blue);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            setTranslucentStatus(true);
        }
        setContentView(R.layout.activity_real_main_activity2);
        // create our manager instance after the content view is set
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);
//        tintManager.setTintColor(Color.parseColor("#bf360c"));
//        tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        tintManager.setStatusBarTintColor(getResources().getColor(R.color.red));
//        tintManager.setNavigationBarTintColor(getResources().getColor(R.color.red));
//        tintManager.setStatusBarAlpha(0.8f);

//        tintManager.setTintColor(getResources().getColor(R.color.blue));
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }
        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));

        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
//                return Color.RED;


                return  colors[r];

            }

            @Override
            public int getDividerColor(int position) {
                return indicatorcolor;
            }

        });
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "默认", "红色", "蓝色", "灰色"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 1:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.red));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 2:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                    case 3:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        mDrawerLayout.closeDrawer(Gravity.START);

                        break;
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_real_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.CENTER);
                return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
}
