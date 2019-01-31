package com.minerva.unsplashapi.photo.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.ui.GoToast;
import com.minerva.unsplashapi.photo.adapter.PhotoAdapter;
import com.minerva.unsplashapi.photo.presenter.PhotoPresenter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements IPhotoView {

    private PhotoPresenter mPhotoPresenter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mEmptyLayout;
    private AVLoadingIndicatorView loadingView;
    private List<Photo> photos = new ArrayList<>();

    private int position;
    private int page = 0;


    private PhotoAdapter mPhotosAdapter;

    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onStart(){
        super.onStart();

        if(mPhotoPresenter == null) {
            mPhotoPresenter = new PhotoPresenter(this);
            mPhotoPresenter.start();
            position = FragmentPagerItem.getPosition(getArguments());
            loadData(position);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mPhotoPresenter == null) {
            mPhotoPresenter = new PhotoPresenter(this);
        }

        if(photos.size() != 0){
            loadingView.hide();
            swipeToLoadLayout.setVisibility(View.VISIBLE);
        }
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
        mPhotosAdapter = new PhotoAdapter(getContext(),photos);
        mRecyclerView.setAdapter(mPhotosAdapter);

    }

    @Override
    public void loadingMoreSuccess(List<Photo> list) {
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
                photos.clear();
                photos.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 100);
                GoToast.makeText(getContext(), GoToast.ToastType.REFRESH_SUCCESS, Toast.LENGTH_SHORT).show();
                mPhotosAdapter.notifyDataSetChanged();
            } else {
                photos.addAll(list);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 100);
                GoToast.makeText(getContext(), GoToast.ToastType.LOADMORE_SUCCESS, Toast.LENGTH_SHORT).show();
                mPhotosAdapter.notifyItemRangeChanged(photos.size() - list.size(), photos.size());
            }
        }
    }


    @Override
    public void setPresenter(PhotoPresenter presenter) {
        mPhotoPresenter = presenter;

    }

    private void loadData(int position){
        switch (position){
            case 0:
                mPhotoPresenter.getNewPhotos(page + 1);
                break;
            case 1:
                mPhotoPresenter.getPopularPhotos(page + 1);
                break;
            case 2:
                mPhotoPresenter.getOldPhotos(page + 1);
                break;
            default:
                break;
        }
    }
}
