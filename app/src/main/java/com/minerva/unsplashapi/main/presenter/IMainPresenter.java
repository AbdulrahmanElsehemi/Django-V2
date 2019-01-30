package com.minerva.unsplashapi.main.presenter;

import com.minerva.unsplashapi.base.IBasePresenter;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public interface IMainPresenter extends IBasePresenter {

    void getCollections(int page);

    void getCuratedCollections(int page);

    void getFeaturedCollections(int page);

}
