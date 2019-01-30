package com.minerva.unsplashapi.main.model;

import com.minerva.unsplashapi.common.data.Collection;
import com.minerva.unsplashapi.common.service.CollectionService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class MainModel implements IMainModel {

    private Subscriber subscriber;
    private CollectionOnListener mCollectionOnListener;
    private boolean loadMore = false;
    private List<Collection> filterCollections;

    public MainModel(CollectionOnListener mCollectionOnListener) {
        this.mCollectionOnListener = mCollectionOnListener;
    }

    @Override
    public void getAllCollections(int page) {
        subscriber = new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for (int i = 0; i < collections.size(); i++) {
                    if (collections.get(i).cover_photo != null) {
                        filterCollections.add(collections.get(i));
                    }
                }
                if (!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;

                } else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getAllCollections(subscriber, page, 10);

    }

    @Override
    public void getCuratedCollections(int page) {
        subscriber=new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for (int i = 0; i < collections.size(); i++) {
                    if (collections.get(i).cover_photo != null) {
                        filterCollections.add(collections.get(i));
                    }
                }

                if (!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;
                } else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getCuratedCollections(subscriber, page, 10);

    }

    @Override
    public void getFeaturedCollections(int page) {
        subscriber = new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for (int i = 0; i < collections.size(); i++) {
                    if (collections.get(i).cover_photo != null) {
                        filterCollections.add(collections.get(i));
                    }
                }

                if (!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;
                } else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getFeaturedCollections(subscriber, page, 10);

    }

    public interface CollectionOnListener {
        void onSuccess(List<Collection> s);

        void onFailure(Throwable e);

        void onSuccessLoadMore(List<Collection> s);
    }

}
