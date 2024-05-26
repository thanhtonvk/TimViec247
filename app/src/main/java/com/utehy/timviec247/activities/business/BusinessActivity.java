package com.utehy.timviec247.activities.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utehy.timviec247.R;
import com.utehy.timviec247.adapters.ViewPagerAdapter;
import com.utehy.timviec247.adapters.business.BussinessViewPager;

public class BusinessActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        init();
        BussinessViewPager adapter = new BussinessViewPager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.dangtuyen).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.thongbao).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.canhan).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItem = item.getItemId();
                if (idItem == R.id.dangtuyen) {
                    viewPager.setCurrentItem(0);
                } else if (idItem == R.id.thongbao) {
                    viewPager.setCurrentItem(1);
                } else if (idItem == R.id.canhan) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
        viewPager.setOffscreenPageLimit(2);
    }

    private void init() {
        viewPager = findViewById(R.id.viewpager_buss);
        bottomNavigationView = findViewById(R.id.bottom_nav_bus);
    }
}