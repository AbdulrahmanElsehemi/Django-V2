package com.minerva.unsplashapi.main.view;

import android.view.View;

import com.minerva.unsplashapi.base.IBaseView;
import com.minerva.unsplashapi.common.data.Collection;
import com.minerva.unsplashapi.main.presenter.MainPresenter;

import java.util.List;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public interface IMainView extends IBaseView<MainPresenter> {

    void showLoading();

    void loadingSuccess(List<Collection> list);

    void loadingFailed();

    void init(View v);

    void initAdapter();

    void loadingMoreSuccess(List<Collection> list);
}
