package es.plexus.datalayer.utils.interceptor

import es.plexus.datalayer.utils.hash.HashDataSource
import es.plexus.datalayer.utils.timestamp.TimestampDataSource
import okhttp3.Interceptor
import okhttp3.Response

private const val TIMESTAMP_QUERY_PARAM = "ts"
private const val PUBLIC_KEY_QUERY_PARAM = "apikey"
private const val HASH_QUERY_PARAM = "hash"

const val REQUEST_QUERY_INTERCEPTOR_TAG = "requestQueryInterceptor"

const val PUBLIC_KEY = "1e9d04f7b30a3527190c4e5342773d72"
const val PRIVATE_KEY = "5cf16872e54a51aa9b07ecfdabd74365c34182ac"

class RequestKeyInterceptor(
    private val timestampDataSource: TimestampDataSource,
    private val hashDataSource: HashDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        var newUrl = originalUrl

        buildRequestKeys(timestampDataSource.getCurrentTimestamp().toString()).map { map ->
            newUrl = newUrl.newBuilder()
                .addQueryParameter(map.key, map.value)
                .build()
        }

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    private fun buildRequestKeys(timestamp: String) = mapOf(
        TIMESTAMP_QUERY_PARAM to timestamp,
        PUBLIC_KEY_QUERY_PARAM to PUBLIC_KEY,
        HASH_QUERY_PARAM to hashDataSource.buildHash(timestamp)
    )
}
