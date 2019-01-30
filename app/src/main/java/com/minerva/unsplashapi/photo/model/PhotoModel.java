package com.minerva.unsplashapi.photo.model;

import com.minerva.unsplashapi.common.data.Photo;

import java.util.List;

public class PhotoModel implements IPhotoModel {
    @Override
    public void getNewPhotos(int page) {

    }

    @Override
    public void getPopularPhotos(int page) {

    }

    @Override
    public void getOldPhotos(int page) {

    }

    public interface PhotoOnListener{
        void onSuccess(List<Photo> s);
        void onFailure(Throwable e);
        void onSuccessLoadMore(List<Photo> s);
    }
}
