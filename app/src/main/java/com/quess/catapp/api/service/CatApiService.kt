package com.quess.catapp.api.service

import com.quess.catapp.model.Breed
import com.quess.catapp.model.BreedImage
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/breeds")
    fun getCatsBreeds(): Observable<List<Breed>>

    @GET("v1/images/search")
    fun getCatImages(
        @Query("breed_id") breed_id: String,
        @Query("limit") limit: Int,
        @Query("size") size: String,
        @Query("mime_types") mime_types: String?
    ): Observable<List<BreedImage>>
}