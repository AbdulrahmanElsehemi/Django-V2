package com.minerva.unsplashapi.photo.view;

import android.view.View;

import com.minerva.unsplashapi.base.IBaseView;
import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.photo.presenter.PhotoPresenter;

import java.util.List;



/**
 * Created by Abdulrahman
 */
public interface IPhotoView extends IBaseView<PhotoPresenter> {

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init(View v);

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);

}
