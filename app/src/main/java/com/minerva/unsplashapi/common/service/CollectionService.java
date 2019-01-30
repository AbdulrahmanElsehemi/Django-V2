package com.minerva.unsplashapi.common.service;

import com.minerva.unsplashapi.common.Constants;
import com.minerva.unsplashapi.common.api.CollectionApi;
import com.minerva.unsplashapi.common.data.Collection;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class CollectionService {
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private CollectionApi collectionApi;

    private CollectionService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.UNSPLASH_API_BASE_URL)
                .build();
        collectionApi=retrofit.create(CollectionApi.class);
    }

    private static class SingletonHolder {
        private static final CollectionService _instance = new CollectionService();
    }

    public static CollectionService getInstance() {
        return SingletonHolder._instance;
    }

    public void getAllCollections(Subscriber<List<Collection>> subscriber, int page, int per_page) {
        collectionApi.getAllCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void getCuratedCollections(Subscriber<List<Collection>> subscriber, int page, int per_page) {
        collectionApi.getCuratedCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getFeaturedCollections(Subscriber<List<Collection>> subscriber, int page, int per_page) {
        collectionApi.getFeaturedCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
