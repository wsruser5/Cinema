package com.mrz.worldcinema.api

import com.mrz.worldcinema.data.Cover
import com.mrz.worldcinema.data.LastVideoInfo
import com.mrz.worldcinema.data.MoviesListItem
import com.mrz.worldcinema.data.Token
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiRequests {
    @POST("auth/register")
    @FormUrlEncoded
    suspend fun signUp(@Field("email") email: String,
                       @Field("password") password: String,
                       @Field("firstName") firstName: String,
                       @Field("lastName") lastName: String): Response<String>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun signIn(@Field("email") email: String, @Field("password") password: String):Response<Token>

    @GET("movies/cover")
    fun getCover(): Observable<Cover>

    @GET("movies")
    fun getMovies(
            @Query("filter") filter: String): Observable<List<MoviesListItem>>

    @Headers("Authorization: Bearer 435874")
    @GET("usermovies")
    fun getLastVideo(
            @Query("filter") filter: String,
            @Path("token") token:String): Observable<List<LastVideoInfo>>
}