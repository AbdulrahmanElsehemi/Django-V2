package com.minerva.unsplashapi.search.view;

import com.minerva.unsplashapi.base.IBaseView;
import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.search.presenter.SearchPresenter;

import java.util.List;

/**
 * Created by Abdulrahman on 1/30/2019.
 */

public interface ISearchView extends IBaseView<SearchPresenter>{

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init();

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);

}
