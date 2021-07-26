package es.plexus.datalayer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.plexus.datalayer.datasource.characters.CharactersCacheDataSource
import es.plexus.datalayer.datasource.characters.CharactersDataSource
import es.plexus.datalayer.datasource.characters.CharactersRemoteDataSource
import es.plexus.datalayer.repository.MarvelRepositoryImpl
import es.plexus.datalayer.utils.connectivity.ConnectivityDataSource
import es.plexus.datalayer.utils.connectivity.ConnectivityDataSource.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import es.plexus.datalayer.utils.connectivity.ConnectivityDataSourceImpl
import es.plexus.datalayer.utils.hash.HashDataSource
import es.plexus.datalayer.utils.hash.Md5HashDataSource
import es.plexus.datalayer.utils.interceptor.ConnectivityInterceptor
import es.plexus.datalayer.utils.interceptor.ConnectivityInterceptor.Companion.CONNECTIVITY_INTERCEPTOR_TAG
import es.plexus.datalayer.utils.interceptor.REQUEST_QUERY_INTERCEPTOR_TAG
import es.plexus.datalayer.utils.interceptor.RequestKeyInterceptor
import es.plexus.datalayer.utils.timestamp.TimestampDataSource
import es.plexus.datalayer.utils.timestamp.TimestampDataSourceImpl
import es.plexus.domainlayer.repository.MarvelRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 20L
private const val READ_TIMEOUT = 30L
private const val MARVEL_BASE_URL = "https://gateway.marvel.com/"

internal val dataSourcesModule = module {
    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get(named(name = CONNECTIVITY_INTERCEPTOR_TAG)))
            .addInterceptor(get(named(name = REQUEST_QUERY_INTERCEPTOR_TAG)))
            .build()
    }

    single<Retrofit> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .baseUrl(MARVEL_BASE_URL)
            .build()
    }

    factory<Interceptor>(named(name = CONNECTIVITY_INTERCEPTOR_TAG)) {
        ConnectivityInterceptor(get(named(name = CONNECTIVITY_DATA_SOURCE_TAG)))
    }

    factory<Interceptor>(named(name = REQUEST_QUERY_INTERCEPTOR_TAG)) {
        RequestKeyInterceptor(get(), get())
    }

    factory<TimestampDataSource> {
        TimestampDataSourceImpl()
    }

    factory<HashDataSource> {
        Md5HashDataSource()
    }

    factory<ConnectivityDataSource>(named(name = CONNECTIVITY_DATA_SOURCE_TAG)) {
        ConnectivityDataSourceImpl(androidContext())
    }

    factory<CharactersDataSource.Remote> {
        CharactersRemoteDataSource(get())
    }
    factory<CharactersDataSource.Cache> {
        CharactersCacheDataSource()
    }
}

internal val dataRepositoriesModule = module {
    single<MarvelRepository> {
        MarvelRepositoryImpl(
            charactersCacheDataSource = get(),
            charactersRemoteDataSource = get()
        )
    }
}

val dataLayerModule = listOf(dataSourcesModule, dataRepositoriesModule, dataServicesModule)
