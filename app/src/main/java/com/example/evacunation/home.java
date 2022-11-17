package com.example.evacunation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes.dex */
public class home extends Fragment {
    View myFragment;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        this.myFragment = inflate;
        this.viewPager = (ViewPager) inflate.findViewById(R.id.viewpager2);
        this.tabLayout = (TabLayout) this.myFragment.findViewById(R.id.tabs);
        return this.myFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setUpViewPager(this.viewPager);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new TabLayout.OnTabSelectedListener() { // from class: com.example.evacunation.home.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        VPAdapter vPAdapter = new VPAdapter(getChildFragmentManager());
        vPAdapter.addFragment(new disasterfragment(), "Tips");
        vPAdapter.addFragment(new news(), "News");
        vPAdapter.addFragment(new hazard(), "NOAH");
        vPAdapter.addFragment(new earthquakesweb(), "USGS");
        vPAdapter.addFragment(new about(), "About");
        viewPager.setAdapter(vPAdapter);
    }
}
