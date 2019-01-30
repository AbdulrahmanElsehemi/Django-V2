package com.minerva.unsplashapi.collection.view;

import com.minerva.unsplashapi.base.IBaseView;
import com.minerva.unsplashapi.collection.presenter.CollectionPresenter;
import com.minerva.unsplashapi.common.data.Photo;

import java.util.List;

public interface ICollectionView extends IBaseView<CollectionPresenter> {

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init();

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);

}
