package com.lst.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lst.news.fragment.HomeListFragment;
import com.lst.news.fragment.ListModel;

import java.util.List;

/**
 * Created by lisongtao on 2018/3/1.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<ListModel> mDataList;
    public HomePagerAdapter(FragmentManager fm, List list) {
        super(fm);
        mDataList = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new HomeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cate_id",mDataList.get(position).getCate_id());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }
}
