package com.minerva.unsplashapi.collection.presenter;

import com.minerva.unsplashapi.collection.model.CollectionModel;
import com.minerva.unsplashapi.collection.model.ICollectionModel;
import com.minerva.unsplashapi.collection.view.ICollectionView;
import com.minerva.unsplashapi.common.data.Photo;

import java.util.List;

public class CollectionPresenter implements ICollectionPresenter, CollectionModel.CollectionPhotoListener {

    private ICollectionModel mICollectionModel;
    private ICollectionView mICollectionView;

    public CollectionPresenter(ICollectionView mICollectionView) {
        this.mICollectionView = mICollectionView;
        mICollectionModel = new CollectionModel(this);
        mICollectionView.setPresenter(this);
    }

    @Override
    public void onSuccess(List<Photo> s) {
        mICollectionView.loadingSuccess(s);
    }

    @Override
    public void onFailure(Throwable e) {
        mICollectionView.loadingFailed();
    }

    @Override
    public void onSuccessLoadMore(List<Photo> s) {
        mICollectionView.loadingMoreSuccess(s);
    }

    @Override
    public void getCollectionPhotos(int id, int page) {
        mICollectionModel.getCollectionPhotos(id,page);
    }

    @Override
    public void getCuratedCollectionPhotos(int id, int page) {
        mICollectionModel.getCuratedCollectionPhotos(id,page);
    }

    @Override
    public void start() {
        mICollectionView.showLoading();
    }
}
