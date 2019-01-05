package app.com.klexos.wakefield.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of data (twitter feed) to display.
 */
public class MyNewsAdapter extends FragmentPagerAdapter {

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public MyNewsAdapter(Context context, FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MyNewsActivityFragment1();
        } else if (position == 1) {
            return new MyNewsActivityFragment2();
        } else if (position == 2) {
            return new MyNewsActivityFragment3();
        } else
            return new MyNewsActivityFragment4();
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return MyNewsActivity.getUsername1();
        } else if (position == 1) {
            return MyNewsActivity.getUsername2();
        } else if (position == 2) {
            return MyNewsActivity.getUsername3();
        } else
            return MyNewsActivity.getUsername4();
    }
}