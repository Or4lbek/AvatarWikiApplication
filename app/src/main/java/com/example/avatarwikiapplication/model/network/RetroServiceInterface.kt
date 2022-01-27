package com.example.avatarwikiapplication.model.network

import com.example.avatarwikiapplication.model.CharactersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("characters/?perPage=20")
    suspend fun getCharacters(@Query("page") page:Int): ArrayList<CharactersItem>

    @GET("characters?affiliation=Fire+Nation")
    suspend fun getFireNationCharacters(): ArrayList<CharactersItem>
}