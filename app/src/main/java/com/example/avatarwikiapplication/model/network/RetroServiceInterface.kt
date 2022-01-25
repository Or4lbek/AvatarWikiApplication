package com.example.avatarwikiapplication.model.network

import com.example.avatarwikiapplication.model.CharactersItem
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("characters")
    suspend fun getCharacters(): ArrayList<CharactersItem>

    @GET("characters?affiliation=Fire+Nation")
    suspend fun getFireNationCharacters(): ArrayList<CharactersItem>
}