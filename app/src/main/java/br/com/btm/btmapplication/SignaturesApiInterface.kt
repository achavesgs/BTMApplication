package br.com.btm.btmapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SignaturesApiInterface {
    @POST("/api/signin")
    fun signIn(@Query("username") username: String, @Query("password") password: String): Call<Usuario>

    @POST("/api/signup")
    fun signUp(@Query("username") username: String, @Query("password") password: String): Call<Usuario>

    @GET("/api/my-signatures/{userId}")
    fun mySignatures(@Path("userId") userId: String): Call<List<Signature>>
}