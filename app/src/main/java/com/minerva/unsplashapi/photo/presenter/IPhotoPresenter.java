package com.minerva.unsplashapi.photo.presenter;

public interface IPhotoPresenter {

    void getNewPhotos(int page);

    void getPopularPhotos(int page);

    void getOldPhotos(int page);
}
