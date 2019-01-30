package com.minerva.unsplashapi.collection.model;

public interface ICollectionModel {
    void getCollectionPhotos(int id, int page);

    void getCuratedCollectionPhotos(int id, int page);

}
