package com.minerva.unsplashapi.search.presenter;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.search.model.ISearchModel;
import com.minerva.unsplashapi.search.model.SearchModel;
import com.minerva.unsplashapi.search.view.ISearchView;

import java.util.List;

/**
 * Created by Abdulrahman on 1/30/2019.
 */

public class SearchPresenter implements ISearchPresenter, SearchModel.SearchOnListener {

    private ISearchModel mISearchModel;
    private ISearchView mISearchView;

    public SearchPresenter(ISearchView iSearchView) {

        mISearchView = iSearchView;
        mISearchModel = new SearchModel(this);
        mISearchView.setPresenter(this);
    }

    @Override
    public void start() {
        mISearchView.showLoading();

    }

    @Override
    public void onSuccess(List<Photo> s) {
        mISearchView.loadingSuccess(s);

    }

    @Override
    public void onFailure(Throwable e) {
        mISearchView.loadingFailed();

    }

    @Override
    public void onSuccessLoadMore(List<Photo> s) {
        mISearchView.loadingMoreSuccess(s);

    }

    @Override
    public void searchPhotos(String query, int page) {
        mISearchModel.searchPhotos(query, page);

    }
}
