package lt.jsj.qust.jjlin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lt.jsj.qust.jjlin.fragment.AlbumFragment;
import lt.jsj.qust.jjlin.fragment.Pic_CategoryFragment;
import lt.jsj.qust.jjlin.fragment.SampleFragment;
import lt.jsj.qust.jjlin.fragment.YmtImgFragment;
import lt.jsj.qust.jjlin.fragment.ZiLiaoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =8;
    private String titles[] ;

    public ViewPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return ZiLiaoFragment.newInstance(position);
            case 1:
                return AlbumFragment.newInstance(position);

            case 2:
                return Pic_CategoryFragment.newInstance(position);
            case 3:
                return YmtImgFragment.newInstance(position);

            case 4:
                return SampleFragment.newInstance(position);

            case 5:
                return SampleFragment.newInstance(position);
            case 6:
                return SampleFragment.newInstance(position);
            case 7:
                return SampleFragment.newInstance(position);

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}