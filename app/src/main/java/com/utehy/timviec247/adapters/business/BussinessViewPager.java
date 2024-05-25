package com.utehy.timviec247.adapters.business;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.utehy.timviec247.fragments.business.BussAccFragment;
import com.utehy.timviec247.fragments.business.NotiBussFragment;
import com.utehy.timviec247.fragments.business.PostFragment;

public class BussinessViewPager extends FragmentStatePagerAdapter {

    public BussinessViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PostFragment();
            case 1:
                return new NotiBussFragment();
            case 2:
                return new BussAccFragment();
            default:
                return new PostFragment();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
