package com.minerva.unsplashapi.main.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.minerva.unsplashapi.R;
import com.minerva.unsplashapi.common.data.Collection;
import com.minerva.unsplashapi.common.ui.GoToast;
import com.minerva.unsplashapi.main.adapter.CollectionsAdapter;
import com.minerva.unsplashapi.main.model.IMainModel;
import com.minerva.unsplashapi.main.presenter.IMainPresenter;
import com.minerva.unsplashapi.main.presenter.MainPresenter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class MainFragment extends Fragment implements IMainView {

    private MainPresenter mMainPresenter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;

    private AVLoadingIndicatorView loadingView;
    private List<Collection> collections = new ArrayList<>();

    private int position;
    private int page = 0;
    private CollectionsAdapter mCollectionsAdapter;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(this);
            mMainPresenter.start();
            position = FragmentPagerItem.getPosition(getArguments());
            loadData(position);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMainPresenter == null) {
            mMainPresenter = new MainPresenter(this);
        }

        if(collections.size() != 0){
            loadingView.hide();
            swipeToLoadLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        mMainPresenter=presenter;
    }

    @Override
    public void showLoading() {
        loadingView.show();

    }

    @Override
    public void loadingSuccess(List<Collection> list) {
        collections.addAll(list);
        page++;
        mCollectionsAdapter.notifyDataSetChanged();
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
        if(collections.size() == 0) {
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
                GoToast.makeText(getContext(), GoToast.ToastType.REFRESH_FAILED, Toast.LENGTH_SHORT).show();
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                },100);
                GoToast.makeText(getContext(), GoToast.ToastType.LOADMORE_FAILED, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void init(View v) {
        swipeToLoadLayout =  v.findViewById(R.id.swipeToLoadLayout);
        mRecyclerView =  v.findViewById(R.id.swipe_target);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        mEmptyLayout = v.findViewById(R.id.empty_layout);
        mEmptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmptyLayout.setVisibility(View.INVISIBLE);
                loadingView.show();
                loadData(position);
            }
        });



        loadingView = v.findViewById(R.id.avi);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData(position);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData(position);
            }
        });

        initAdapter();
    }

    @Override
    public void initAdapter() {
        mCollectionsAdapter=new CollectionsAdapter(getContext(),collections);
        mRecyclerView.setAdapter(mCollectionsAdapter);
    }

    @Override
    public void loadingMoreSuccess(List<Collection> list) {
        loadingView.hide();
        swipeToLoadLayout.setVisibility(View.VISIBLE);
        if (list.size() == 0) {
            GoToast.makeText(getContext(), GoToast.ToastType.NOMORE, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }, 100);
        } else {
            page++;
            if (page == 1) {
                collections.clear();
                collections.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 100);
                GoToast.makeText(getContext(), GoToast.ToastType.REFRESH_SUCCESS, Toast.LENGTH_SHORT).show();
                mCollectionsAdapter.notifyDataSetChanged();
            } else {
                collections.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 100);
                GoToast.makeText(getContext(), GoToast.ToastType.LOADMORE_SUCCESS, Toast.LENGTH_SHORT).show();
                mCollectionsAdapter.notifyItemRangeChanged(collections.size() - list.size(), collections.size());
            }
        }
    }

    private void loadData(int position) {
        switch (position) {
            case 0:
                mMainPresenter.getCollections(page + 1);
                break;
            case 1:
                mMainPresenter.getCuratedCollections(page + 1);
                break;
            case 2:
                mMainPresenter.getFeaturedCollections(page + 1);
                break;
            default:
                break;

        }
    }
}
