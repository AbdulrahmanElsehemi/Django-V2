package com.minerva.unsplashapi.search.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.minerva.unsplashapi.R;
import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.ui.GoToast;
import com.minerva.unsplashapi.common.utils.TypefaceUtils;
import com.minerva.unsplashapi.photo.adapter.PhotoAdapter;
import com.minerva.unsplashapi.search.presenter.SearchPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ISearchView, AppBarLayout.OnOffsetChangedListener {
    private Toolbar mToolbar;
    private MaterialSearchView searchView;
    private TextView searchQuery;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Button backBtn;

    private SearchPresenter mSearchPresenter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private AVLoadingIndicatorView loadingView;
    private List<Photo> photos = new ArrayList<>();

    private int page = 0;
    private String query;
    private PhotoAdapter mPhotosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSearchPresenter == null) {
            mSearchPresenter = new SearchPresenter(this);
            mSearchPresenter.start();
            loadData(query);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        appBarLayout.addOnOffsetChangedListener(this);

        if (mSearchPresenter == null) {
            mSearchPresenter = new SearchPresenter(this);
        }

        if (photos.size() != 0) {
            loadingView.hide();
            swipeToLoadLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        swipeToLoadLayout.setRefreshEnabled(verticalOffset == 0);

    }

    @Override
    public void setPresenter(SearchPresenter presenter) {
        mSearchPresenter = presenter;

    }

    @Override
    public void showLoading() {
        loadingView.show();

    }

    @Override
    public void loadingSuccess(List<Photo> list) {
        photos.addAll(list);
        page++;
        mPhotosAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.hide();
                swipeToLoadLayout.setVisibility(View.VISIBLE);
            }
        },1000);
    }

    @Override
    public void loadingFailed() {
        loadingView.hide();
        if(photos.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        }else{
            ++page;
            if(page == 1){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                },100);
                GoToast.makeText(this, GoToast.ToastType.REFRESH_FAILED, Toast.LENGTH_SHORT).show();
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                },100);
                GoToast.makeText(this, GoToast.ToastType.LOADMORE_FAILED, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void init() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 0;
                photos.clear();
                mSearchPresenter.start();
                searchQuery.setText(query.toUpperCase());
                loadData(query);

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
                backBtn.setVisibility(View.INVISIBLE);
                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
            }

            @Override
            public void onSearchViewClosed() {
                backBtn.setVisibility(View.VISIBLE);
            }
        });

        mCollapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = findViewById(R.id.appbar);
        backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchQuery = findViewById(R.id.search_query);
        TypefaceUtils.setTypeface(this, searchQuery);
        searchQuery.setText(query.toUpperCase());
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        mRecyclerView = findViewById(R.id.swipe_target);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mEmptyLayout = findViewById(R.id.empty_layout);
        mEmptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmptyLayout.setVisibility(View.INVISIBLE);
                loadingView.show();
                loadData(query);
            }
        });

        loadingView = findViewById(R.id.avi);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                photos.clear();
                loadData(query);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData(query);
            }
        });

        initAdapter();

    }

    @Override
    public void initAdapter() {
        mPhotosAdapter = new PhotoAdapter(this,photos);
        mRecyclerView.setAdapter(mPhotosAdapter);

    }

    @Override
    public void loadingMoreSuccess(List<Photo> list) {

    }

    private void loadData(String query) {
        if(!this.query.equals(query)){
            this.query = query;
        }
        mSearchPresenter.searchPhotos(query, page + 1);
    }
}
