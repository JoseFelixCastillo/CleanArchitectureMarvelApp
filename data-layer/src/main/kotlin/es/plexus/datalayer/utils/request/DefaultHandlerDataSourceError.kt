package es.plexus.datalayer.utils.request

import arrow.core.Either
import arrow.core.left
import es.plexus.datalayer.model.FailureDto
import es.plexus.datalayer.model.toFailureBo
import es.plexus.datalayer.utils.interceptor.NoConnectivityException
import es.plexus.domainlayer.model.FailureBo
import retrofit2.Response

private const val FIRST_HTTP_CODE_CLIENT_ERROR = 400
private const val LAST_HTTP_CODE_CLIENT_ERROR = 499
private const val FIRST_HTTP_CODE_SERVER_ERROR = 500
private const val LAST_HTTP_CODE_SERVER_ERROR = 599

internal open class DefaultHandlerDataSourceError : HandlerDataSourceError {
    override fun <T> handleError(response: Response<T>) =
        when (response.code()) {
            in FIRST_HTTP_CODE_CLIENT_ERROR..LAST_HTTP_CODE_CLIENT_ERROR -> FailureDto.ClientError(
                code = response.code(),
                message = response.message()
            )
            in FIRST_HTTP_CODE_SERVER_ERROR..LAST_HTTP_CODE_SERVER_ERROR -> FailureDto.ServerError(
                code = response.code(),
                message = response.message()
            )
            else -> FailureDto.UnexpectedFailure(response.code(), response.message())
        }.toFailureBo().left()

    override fun handleException(exception: Exception) =
        when (exception) {
            is NoConnectivityException -> FailureDto.NoNetwork
            else -> {
                FailureDto.UnexpectedFailure(
                    ErrorMessage.CODE_UNEXPECTED_ERROR,
                    exception.localizedMessage
                )
            }
        }.toFailureBo().left()

    override fun handleEmptyBody(message: String): Either<FailureBo, Nothing> {
        return FailureDto.EmptyResponse(message).toFailureBo().left()
    }
}
