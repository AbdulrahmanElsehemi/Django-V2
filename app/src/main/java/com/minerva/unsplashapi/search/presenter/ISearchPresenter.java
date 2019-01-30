package com.minerva.unsplashapi.search.presenter;

import com.minerva.unsplashapi.base.IBasePresenter;

/**
 * Created by Abdulrahman on 1/30/2019.
 */

public interface ISearchPresenter extends IBasePresenter {

    void searchPhotos(String query,int page);
}
