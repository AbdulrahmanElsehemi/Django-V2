package com.minerva.unsplashapi.photo.presenter;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.photo.model.IPhotoModel;
import com.minerva.unsplashapi.photo.model.PhotoModel;
import com.minerva.unsplashapi.photo.view.IPhotoView;

import java.util.List;

public class PhotoPresenter implements IPhotoPresenter, PhotoModel.PhotoOnListener {
    private IPhotoModel mIPhotoModel;
    private IPhotoView mIPhotoView;


    public PhotoPresenter(IPhotoView iPhotoView) {

        mIPhotoView = iPhotoView;
        mIPhotoModel = new PhotoModel(this);
        mIPhotoView.setPresenter(this);
    }

    @Override
    public void start() {
        mIPhotoView.showLoading();
    }

    @Override
    public void getNewPhotos(int page) {
        mIPhotoModel.getNewPhotos(page);
    }

    @Override
    public void getPopularPhotos(int page) {
        mIPhotoModel.getPopularPhotos(page);
    }

    @Override
    public void getOldPhotos(int page) {
        mIPhotoModel.getOldPhotos(page);
    }

    public void onSuccess(List<Photo> photos) {
        mIPhotoView.loadingSuccess(photos);
    }

    public void onSuccessLoadMore(List<Photo> photos) {
        mIPhotoView.loadingMoreSuccess(photos);
    }


    public void onFailure(Throwable e) {
        mIPhotoView.loadingFailed();
    }
}
