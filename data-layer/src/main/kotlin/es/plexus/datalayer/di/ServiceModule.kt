package es.plexus.datalayer.di

import es.plexus.datalayer.service.MarvelApiService
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataServicesModule = module {
    single { createMarvelService(get()) }
}

private fun createMarvelService(retrofit: Retrofit) = retrofit.create(MarvelApiService::class.java)
