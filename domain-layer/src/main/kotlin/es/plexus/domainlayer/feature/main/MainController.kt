package es.plexus.domainlayer.feature.main

import arrow.core.Either
import es.plexus.domainlayer.base.BaseController
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo

interface MainController : BaseController {
    suspend fun fetchCharacters(): Either<FailureBo, CharacterDataWrapperBo>
}
