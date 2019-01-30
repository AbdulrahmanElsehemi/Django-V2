package com.minerva.unsplashapi.collection.model;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.service.PhotoService;

import java.util.List;

import rx.Subscriber;

public class CollectionModel implements ICollectionModel {
    private Subscriber subscriber;
    private CollectionPhotoListener mCollectionPhotoListener;
    private boolean loadMore = false;

    public CollectionModel(CollectionPhotoListener mCollectionPhotoListener) {
        this.mCollectionPhotoListener = mCollectionPhotoListener;
    }

    public void getCollectionPhotos(int id, int page) {
        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionPhotoListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mCollectionPhotoListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mCollectionPhotoListener.onSuccessLoadMore(photos);
                }
            }
        };

        PhotoService.getInstance().getCollectionPhotos(subscriber, id, page, 10);
    }

    public void getCuratedCollectionPhotos(int id, int page) {
        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionPhotoListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mCollectionPhotoListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mCollectionPhotoListener.onSuccessLoadMore(photos);
                }
            }
        };

        PhotoService.getInstance().getCuratedCollectionPhotos(subscriber, id, page, 10);
    }

    public interface CollectionPhotoListener {
        void onSuccess(List<Photo> s);

        void onFailure(Throwable e);

        void onSuccessLoadMore(List<Photo> s);

    }
}
