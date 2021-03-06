package com.minerva.unsplashapi.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.minerva.unsplashapi.base.BaseDrawerActivity;
import com.minerva.unsplashapi.R;
import com.minerva.unsplashapi.search.view.SearchActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class MainActivity extends BaseDrawerActivity {
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private ViewPager viewPager;
    private SmartTabLayout viewPagerTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Django Collection");
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent();
                intent.putExtra("query", query);
                intent.setClass(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.all, MainFragment.class)
                .add(R.string.curated, MainFragment.class)
                .add(R.string.featured, MainFragment.class)
                .create());

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        viewPagerTab = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
