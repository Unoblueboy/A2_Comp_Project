package com.example.natha_000.a2_comp_project.Graphing_Stats;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.natha_000.a2_comp_project.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TabbedStatsActivity extends AppCompatActivity {

    static File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Statistics");
    private static final int READ_REQUEST_CODE = 42;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_stats);
        new StatsClasses(0,10,1);
        new StatsClasses(10,20,2);
        new StatsClasses(20,30,1);
        new StatsClasses(30,40,2);
        new StatsClasses(40,50,1);
        new StatsClasses(50,60,1);
        new StatsClasses(60,70,5);
        new StatsClasses(70,80,4);
        new StatsClasses(80,90,11);
        new StatsClasses(90,100,5);
        new StatsClasses(100,110,4);
        new StatsClasses(110,120,8);
        new StatsClasses(120,130,11);
        new StatsClasses(130,140,13);
        new StatsClasses(140,150,7);
        new StatsClasses(150,160,12);
        new StatsClasses(160,170,10);
        new StatsClasses(170,180,7);
        new StatsClasses(180,190,13);
        new StatsClasses(190,200,25);
        new StatsClasses(200,210,11);
        new StatsClasses(210,220,21);
        new StatsClasses(220,230,22);
        new StatsClasses(230,240,31);
        new StatsClasses(240,250,24);
        new StatsClasses(250,260,35);
        new StatsClasses(260,270,31);
        new StatsClasses(270,280,36);
        new StatsClasses(280,290,37);
        new StatsClasses(290,300,39);
        new StatsClasses(300,310,40);
        new StatsClasses(310,320,57);
        new StatsClasses(320,330,41);
        new StatsClasses(330,340,50);
        new StatsClasses(340,350,52);
        new StatsClasses(350,360,70);
        new StatsClasses(360,370,72);
        new StatsClasses(370,380,67);
        new StatsClasses(380,390,82);
        new StatsClasses(390,400,74);
        new StatsClasses(400,410,85);
        new StatsClasses(410,420,73);
        new StatsClasses(420,430,81);
        new StatsClasses(430,440,72);
        new StatsClasses(440,450,92);
        new StatsClasses(450,460,85);
        new StatsClasses(460,470,108);
        new StatsClasses(470,480,90);
        new StatsClasses(480,490,84);
        new StatsClasses(490,500,85);
        new StatsClasses(500,510,83);
        new StatsClasses(510,520,94);
        new StatsClasses(520,530,100);
        new StatsClasses(530,540,74);
        new StatsClasses(540,550,85);
        new StatsClasses(550,560,105);
        new StatsClasses(560,570,73);
        new StatsClasses(570,580,82);
        new StatsClasses(580,590,86);
        new StatsClasses(590,600,80);
        new StatsClasses(600,610,87);
        new StatsClasses(610,620,72);
        new StatsClasses(620,630,67);
        new StatsClasses(630,640,72);
        new StatsClasses(640,650,67);
        new StatsClasses(650,660,69);
        new StatsClasses(660,670,38);
        new StatsClasses(670,680,58);
        new StatsClasses(680,690,63);
        new StatsClasses(690,700,63);
        new StatsClasses(700,710,58);
        new StatsClasses(710,720,42);
        new StatsClasses(720,730,44);
        new StatsClasses(730,740,41);
        new StatsClasses(740,750,28);
        new StatsClasses(750,760,45);
        new StatsClasses(760,770,35);
        new StatsClasses(770,780,24);
        new StatsClasses(780,790,24);
        new StatsClasses(790,800,13);
        new StatsClasses(800,810,22);
        new StatsClasses(810,820,16);
        new StatsClasses(820,830,16);
        new StatsClasses(830,840,6);
        new StatsClasses(840,850,12);
        new StatsClasses(850,860,9);
        new StatsClasses(860,870,13);
        new StatsClasses(870,880,11);
        new StatsClasses(880,890,11);
        new StatsClasses(890,900,9);
        new StatsClasses(900,910,5);
        new StatsClasses(910,920,5);
        new StatsClasses(920,930,6);
        new StatsClasses(930,940,2);
        new StatsClasses(940,950,4);
        new StatsClasses(950,960,4);
        new StatsClasses(960,970,2);
        new StatsClasses(970,980,4);
        new StatsClasses(980,990,2);
        new StatsClasses(990,1000,1);
        new StatsClasses(1000,1010,0);
        new StatsClasses(1010,1020,5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
//                Log.i("Tabbed","On Page Selected"+Integer.toString(position));
                if (position == 0) {
                    StatsClasses.reset();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                Log.i("Tabbed","On Page Scrolled: " + Arrays.toString(new float[]{arg0,arg1,arg2}));
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
//                Log.i("Tabbed","On Page Scroll State Changed: " + Integer.toString(pos));
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export();
            }
        });
    }

    public void export() {
        String newfolname = (new SimpleDateFormat("dd-MM-yy-HH:mm:ss")).format(new Date());

        File newfolder = new File(folder.toString() + File.separator + newfolname);
        if (!newfolder.exists()) {
            newfolder.mkdirs();
        }
        (new SketchHistogram()).export(600, 1000, newfolder);
        (new SketchCumFreq()).export(600, 1000, newfolder);
        (new StatsDataTable()).export(newfolder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link NewFragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends NewFragmentPagerAdapter {

        int prevFragment = 0;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.i("Project: Switch","");
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new StatsDataTable();
                case 1:
                    return new SketchHistogram();
                case 2:
                    return new SketchCumFreq();
            }
            return null;

//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("Project: Switch Title",Integer.toString(position));
            switch (position) {
                case 0:
                    return "table";
                case 1:
                    return "Histogram";
                case 2:
                    return "Cumulative Frequency";
            }
            return null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        StatsClasses.reset();
    }
}
