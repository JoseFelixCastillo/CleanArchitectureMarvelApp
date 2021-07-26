package es.plexus.datalayer.service

import es.plexus.datalayer.model.CharacterDataWrapperDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun requestCharacters(): Response<CharacterDataWrapperDto>

    @GET("v1/public/characters/{characterId}")
    suspend fun requestDetailCharacter(@Path("characterId") id: Int): Response<CharacterDataWrapperDto>
}
