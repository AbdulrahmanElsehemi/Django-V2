package com.minerva.unsplashapi.photo.model;

public interface IPhotoModel {

    void getNewPhotos(int page);

    void getPopularPhotos(int page);

    void getOldPhotos(int page);
}
