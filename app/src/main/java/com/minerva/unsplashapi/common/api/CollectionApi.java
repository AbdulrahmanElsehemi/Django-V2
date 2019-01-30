package com.minerva.unsplashapi.common.api;

import com.minerva.unsplashapi.common.data.Collection;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public interface CollectionApi {
    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("collections")
    Observable<List<Collection>> getAllCollections(@Query("page") int page,
                                                   @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("collections/curated")
    Observable<List<Collection>> getCuratedCollections(@Query("page") int page,
                                                       @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("collections/featured")
    Observable<List<Collection>> getFeaturedCollections(@Query("page") int page,
                                                        @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("users/{username}/collections")
    Observable<List<Collection>> getUserCollections(@Path("username") String username,
                                                    @Query("page") int page,
                                                    @Query("per_page") int per_page);

}
