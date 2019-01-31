package com.minerva.unsplashapi.photo.model;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.service.PhotoService;
import com.minerva.unsplashapi.photo.presenter.PhotoPresenter;

import java.util.List;

import rx.Subscriber;

public class PhotoModel implements IPhotoModel {

    private Subscriber subscriber;
    private PhotoOnListener mPhotoOnListener;
    private boolean loadMore = false;

    public PhotoModel(PhotoOnListener mPhotoOnListener) {
        this.mPhotoOnListener = mPhotoOnListener;
    }

    @Override
    public void getNewPhotos(int page) {
        subscriber = new Subscriber<List<Photo>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getNewPhotos(subscriber, page, 10);
    }

    @Override
    public void getPopularPhotos(int page) {
        subscriber = new Subscriber<List<Photo>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getPopularPhotos(subscriber, page, 10);
    }

    @Override
    public void getOldPhotos(int page) {
        subscriber = new Subscriber<List<Photo>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getOldPhotos(subscriber, page, 10);
    }

    public interface PhotoOnListener {
        void onSuccess(List<Photo> s);

        void onFailure(Throwable e);

        void onSuccessLoadMore(List<Photo> s);
    }
}
