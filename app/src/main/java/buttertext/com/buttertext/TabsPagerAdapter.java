package buttertext.com.buttertext;

/**
 * Created by Gabriel on 12/28/2015.
 */

import buttertext.com.buttertext.InboxFragment;
import buttertext.com.buttertext.ContactsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Phone fragment activity
                return new PhoneFragment();
            case 1:
                // Inbox fragment activity
                return new InboxFragment();
            case 2:
                // Contacts fragment activity
                return new ContactsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}