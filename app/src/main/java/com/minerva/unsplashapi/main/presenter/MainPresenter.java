package com.minerva.unsplashapi.main.presenter;

import com.minerva.unsplashapi.common.data.Collection;
import com.minerva.unsplashapi.main.model.IMainModel;
import com.minerva.unsplashapi.main.model.MainModel;
import com.minerva.unsplashapi.main.view.IMainView;

import java.util.List;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class MainPresenter implements IMainPresenter, MainModel.CollectionOnListener {

    private IMainModel mIMainModel;
    private IMainView mIMainView;

    public MainPresenter(IMainView iMainView) {

        mIMainView = iMainView;
        mIMainModel = new MainModel(this);
        mIMainView.setPresenter(this);
    }
    @Override
    public void onSuccess(List<Collection> s) {
        mIMainView.loadingSuccess(s);

    }

    @Override
    public void onFailure(Throwable e) {
        mIMainView.loadingFailed();

    }

    @Override
    public void onSuccessLoadMore(List<Collection> s) {
        mIMainView.loadingMoreSuccess(s);

    }

    @Override
    public void getCollections(int page) {
        mIMainModel.getAllCollections(page);

    }

    @Override
    public void getCuratedCollections(int page) {
        mIMainModel.getCuratedCollections(page);

    }

    @Override
    public void getFeaturedCollections(int page) {
        mIMainModel.getFeaturedCollections(page);

    }


    @Override
    public void start() {
        mIMainView.showLoading();

    }
}
