package com.minerva.unsplashapi.search.model;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.service.PhotoService;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Abdulrahman on 1/30/2019.
 */

public class SearchModel implements ISearchModel {

    private Subscriber subscriber;
    private SearchOnListener mSearchOnListener;
    private boolean loadMore = false;

    public SearchModel(SearchOnListener mSearchOnListener) {
        this.mSearchOnListener = mSearchOnListener;
    }

    public void searchPhotos(String query, int page) {
        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSearchOnListener.onFailure(e);

            }

            @Override
            public void onNext(List<Photo> photos) {
                if (!loadMore) {
                    mSearchOnListener.onSuccess(photos);
                    loadMore = true;
                } else {
                    mSearchOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().searchPhotos(subscriber, query, page, 10);

    }


    public interface SearchOnListener {
        void onSuccess(List<Photo> s);

        void onFailure(Throwable e);

        void onSuccessLoadMore(List<Photo> s);

    }
}
