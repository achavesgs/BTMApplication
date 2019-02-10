package br.com.btm.btmapplication

import retrofit2.Call
import retrofit2.http.*

interface SignaturesApiInterface {
    @POST("/api/signin")
    fun signIn(@Query("username") username: String, @Query("password") password: String): Call<Usuario>

    @POST("/api/signup")
    fun signUp(@Query("username") username: String, @Query("password") password: String): Call<Usuario>

    @GET("/api/my-signatures/{userId}")
    fun mySignatures(@Path("userId") userId: String): Call<MutableList<Signature>>

    @DELETE("/api/my-signatures/{userId}/{itemId}")
    fun deleteSignature(@Path("userId") userId: String, @Path("itemId") itemId: String): Call<String>

    @POST("/api/my-signatures/{userId}")
    fun createSignature(@Path("userId") userId: String, @Body signature: Signature): Call<String>

    @PUT("/api/my-signatures/{userId}/{itemId}")
    fun updateSignature(@Path("userId") userId: String, @Path("itemId") itemId: String, @Body signature: Signature): Call<String>
}