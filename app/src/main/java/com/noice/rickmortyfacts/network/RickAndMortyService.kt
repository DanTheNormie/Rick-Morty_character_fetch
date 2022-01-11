package com.noice.rickmortyfacts.network

import com.noice.rickmortyfacts.model.CharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("character/{characterPath}")
    suspend fun getCharacterbyId(@Path("characterPath")characterPath:Int):Response<CharacterModel>
}