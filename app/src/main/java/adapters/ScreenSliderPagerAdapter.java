package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.TutorialOneFragment;
import fragments.TutorialTwoFragment;

/**
 * Created by prasadsawant on 11/10/16.
 */

public class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;

    public ScreenSliderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TutorialOneFragment();

            case 1:
                return new TutorialTwoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
