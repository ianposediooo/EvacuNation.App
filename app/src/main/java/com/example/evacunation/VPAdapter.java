package com.example.evacunation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class VPAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList();
    private List<String> snippetList = new ArrayList();

    public VPAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragmentList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.snippetList.get(i);
    }

    public void addFragment(Fragment fragment, String str) {
        this.fragmentList.add(fragment);
        this.snippetList.add(str);
    }



}
