package com.example.natha_000.a2_comp_project.Graphing_Stats;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * Created by Natha_000 on 18/03/2017.
 */
public class TabsPagerAdapter extends NewFragmentPagerAdapter{

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new StatsDataTable();
            case 1:
                // Games fragment activity
                return new SketchHistogram();
            case 2:
                // Movies fragment activity
                return new SketchCumFreq();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}

