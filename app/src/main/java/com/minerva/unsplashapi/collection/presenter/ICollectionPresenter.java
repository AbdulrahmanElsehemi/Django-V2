package com.minerva.unsplashapi.collection.presenter;

import com.minerva.unsplashapi.base.IBasePresenter;

public interface ICollectionPresenter extends IBasePresenter {

    void getCollectionPhotos(int id, int page);

    void getCuratedCollectionPhotos(int id, int page);
}
