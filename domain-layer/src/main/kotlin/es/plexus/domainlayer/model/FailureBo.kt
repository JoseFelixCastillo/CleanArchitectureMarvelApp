package es.plexus.domainlayer.model

sealed class FailureBo {
    object NoNetwork : FailureBo()
    class EmptyResponse(val message: String?) : FailureBo()
    class ServerError(val code: Int, val message: String?) : FailureBo()
    class ClientError(val code: Int, val message: String?) : FailureBo()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureBo()
    abstract class FeatureFailure : FailureBo()
}

// Example of feature error
sealed class MarvelFailureBo : FailureBo.FeatureFailure() {
    object LoginError : MarvelFailureBo()
}
