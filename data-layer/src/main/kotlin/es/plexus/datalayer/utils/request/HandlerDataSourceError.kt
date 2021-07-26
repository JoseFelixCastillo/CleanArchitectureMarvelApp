package es.plexus.datalayer.utils.request

import arrow.core.Either
import es.plexus.domainlayer.model.FailureBo
import retrofit2.Response
import java.lang.Exception

interface HandlerDataSourceError {
    /**
     * This function provides a mechanism to handle HTTP errors coming from a Retrofit
     * [Response]. It returns an [Either] which models the [FailureBo] to be transmitted beyond the
     * domain layer.
     */
    fun <T> handleError(response: Response<T>): Either<FailureBo, Nothing>

    /**
     * This function provides a mechanism to handle Exceptions coming from a Retrofit
     * request or [Response]. It returns an [Either] which models the [FailureBo] to be transmitted beyond the
     * domain layer.
     */
    fun handleException(exception: Exception): Either<FailureBo, Nothing>

    /**
     * This function provides a mechanism to handle empty body from Retrofit
     */
    fun handleEmptyBody(message: String): Either<FailureBo, Nothing>
}
