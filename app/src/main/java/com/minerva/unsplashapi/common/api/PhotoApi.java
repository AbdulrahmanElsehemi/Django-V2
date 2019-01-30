package com.minerva.unsplashapi.common.api;

import com.minerva.unsplashapi.common.data.Photo;
import com.minerva.unsplashapi.common.data.PhotoDetails;
import com.minerva.unsplashapi.common.data.PhotoStats;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public interface PhotoApi {
    //data
    String ORDER_BY_LATEST = "latest";
    String ORDER_BY_OLDEST = "oldest";
    String ORDER_BY_POPULAR = "popular";

    String LANDSCAPE_ORIENTATION = "landscape";
    String PORTRAIT_ORIENTATION = "portrait";
    String SQUARE_ORIENTATION = "square";

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("photos")
    Observable<List<Photo>> getPhotos(@Query("page")int page,
                                      @Query("per_page")int per_page,
                                      @Query("order_by")String order_by);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("photos/curated")
    Observable<List<Photo>> getCuratedPhotos(@Query("page") int page,
                                             @Query("per_page") int per_page,
                                             @Query("order_by") String order_by);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("photos/search")
    Observable<List<Photo>> searchPhotos(@Query("query") String query,
                                         @Query("orientation") String orientation,
                                         @Query("page") int page,
                                         @Query("per_page") int per_page);

    @GET("photos/{id}/stats")
    Observable<PhotoStats> getPhotoStats(@Path("id") String id);

    @GET("categories/{id}/photos")
    Observable<List<Photo>> getPhotosInAGivenCategory(@Path("id") int id,
                                                      @Query("page") int page,
                                                      @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("photos/{id}")
    Observable<PhotoDetails> getAPhoto(@Path("id") String id);

    @GET("users/{username}/photos")
    Observable<List<Photo>> getUserPhotos(@Path("username") String username,
                                          @Query("page") int page,
                                          @Query("per_page") int per_page,
                                          @Query("order_by") String order_by);

    @GET("users/{username}/likes")
    Observable<List<Photo>> getUserLikes(@Path("username") String username,
                                         @Query("page") int page,
                                         @Query("per_page") int per_page,
                                         @Query("order_by") String order_by);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("collections/{id}/photos")
    Observable<List<Photo>> getCollectionPhotos(@Path("id") int id,
                                                @Query("page") int page,
                                                @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID 487ac049fbdfb95915dd3b0b38a373a82044f5ec42eaefc183c47d4e526b4a89")
    @GET("collections/curated/{id}/photos")
    Observable<List<Photo>> getCuratedCollectionPhotos(@Path("id") int id,
                                                       @Query("page") int page,
                                                       @Query("per_page") int per_page);
}
