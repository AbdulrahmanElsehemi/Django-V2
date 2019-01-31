package com.minerva.unsplashapi.photo.presenter;

import com.minerva.unsplashapi.base.IBasePresenter;

public interface IPhotoPresenter extends IBasePresenter {

    void getNewPhotos(int page);

    void getPopularPhotos(int page);

    void getOldPhotos(int page);
}
