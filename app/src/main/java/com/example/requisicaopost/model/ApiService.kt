package com.example.requisicaopost.model

import com.example.requisicaopost.service.Postagem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("posts")
    suspend fun salvarPostagem(
        @Body postagem: Postagem
    ): Response<Postagem>

    @FormUrlEncoded
    @POST("posts")
    suspend fun salvarPostagemFormulario(
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String,
    ): Response<Postagem>

}