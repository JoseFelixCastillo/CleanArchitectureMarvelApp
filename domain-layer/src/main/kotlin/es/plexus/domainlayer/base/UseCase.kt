package es.plexus.domainlayer.base

import arrow.core.Either
import es.plexus.domainlayer.model.FailureBo

/**
 * Defines baseline use-case using Kotlin Coroutines
 */
interface UseCase<in T, out S> {
    /**
    * Executes the use-cause
    *
    * @param [params] arguments used in the query
    * @return An [S] value if successful or a [FailureBo] otherwise
    */
    suspend fun run(params: T): Either<FailureBo, S>
    object NoneParams
}
