package es.plexus.datalayer.utils.request

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import arrow.core.Either
import arrow.core.right
import es.plexus.domainlayer.model.FailureBo
import retrofit2.Response

/**
 * This extension function allows any entity hosting a [Context] to easily check whether there is a
 * valid data connection. It also takes into account the Android version available.
 *
 */
internal fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )
        when {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
            // for other devices which are able to connect with Ethernet
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
            // for VPN connections
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected == true
    }
}

/**
 * This function provides a proceeding to handle with 'Retrofit' request and [Response] objects, so that
 * a parametrized 'Either' type is returned.
 *
 * @param retrofitRequest the retrofit request from a service
 * @param transform function to map response object into a domain object
 * @param handlerDataSourceError interface with functions for handler errors
 *
 */
internal suspend fun <S : Response<T>, T, R> retrofitSafeCall(
    retrofitRequest: suspend () -> S,
    transform: (T) -> R,
    handlerDataSourceError: HandlerDataSourceError = DefaultHandlerDataSourceError()
): Either<FailureBo, R> =
    try {
        retrofitRequest().handle(transform, handlerDataSourceError)
    } catch (exception: Exception) {
        handlerDataSourceError.handleException(exception)
    }

/**
 * This function provides a proceeding to handle with 'Retrofit' request and [Response] objects, so that
 * a parametrized 'Either' type is returned.
 *
 * @param parameter parameter for retrofit request
 * @param retrofitRequest the retrofit request from a service
 * @param transform function to map response object into a domain object
 * @param handlerDataSourceError interface with functions for handler errors
 *
 */
internal suspend fun <U, S : Response<T>, T, R> retrofitSafeCall(
    parameter: U,
    retrofitRequest: suspend (U) -> S,
    transform: (T) -> R,
    handlerDataSourceError: HandlerDataSourceError = DefaultHandlerDataSourceError()
): Either<FailureBo, R> =
    try {
        retrofitRequest(parameter).handle(transform, handlerDataSourceError)
    } catch (exception: Exception) {
        handlerDataSourceError.handleException(exception)
    }

private fun <T, R> Response<T>.handle(
    transform: (T) -> R,
    handlerDataSourceError: HandlerDataSourceError
): Either<FailureBo, R> =
    if (isSuccessful) {
        this.raw().request().url()
        val body = body()
        if (body != null) {
            transform(body).right()
        } else {
            handlerDataSourceError.handleEmptyBody(message())
        }
    } else {
        handlerDataSourceError.handleError(this)
    }

object ErrorMessage {
    const val CODE_UNEXPECTED_ERROR = -1
}
