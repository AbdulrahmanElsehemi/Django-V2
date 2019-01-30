package com.minerva.unsplashapi.main.model;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public interface IMainModel {

    void getAllCollections(int page);

    void getCuratedCollections(int page);

    void getFeaturedCollections(int page);

}
