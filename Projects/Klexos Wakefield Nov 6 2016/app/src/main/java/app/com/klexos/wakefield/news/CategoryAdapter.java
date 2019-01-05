package app.com.klexos.wakefield.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of data (twitter feed) to display.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    // Context of the app
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeMainFragment();
        } else if (position == 1) {
            return new HomeSportsFragment();
        } else if (position == 2) {
            return new HomeClubsFragment();
        } else if (position == 3) {
            return new HomeTeachersFragment();
        } else
            return new HomeStaffFragment();
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_general);
        } else if (position == 1) {
            return mContext.getString(R.string.category_sports);
        } else if (position == 2) {
            return mContext.getString(R.string.category_clubs);
        } else if (position == 3) {
            return mContext.getString(R.string.category_teachers);
        } else
            return mContext.getString(R.string.category_staff);
    }
}