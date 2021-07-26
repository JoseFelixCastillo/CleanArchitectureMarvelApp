package es.plexus.datalayer.model

/**
 * A class which models any failure coming from the 'data-layer'
 */
sealed class FailureDto {
    object NoNetwork : FailureDto()
    class EmptyResponse(val message: String?) : FailureDto()
    class ServerError(val code: Int, val message: String?) : FailureDto()
    class ClientError(val code: Int, val message: String?) : FailureDto()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureDto()
    abstract class FeatureFailure : FailureDto()
}

sealed class MarvelFailure : FailureDto.FeatureFailure() {
    object LoginError : MarvelFailure()
}
