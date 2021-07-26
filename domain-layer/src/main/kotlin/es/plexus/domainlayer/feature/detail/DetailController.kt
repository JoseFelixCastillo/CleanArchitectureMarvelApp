package es.plexus.domainlayer.feature.detail

import arrow.core.Either
import es.plexus.domainlayer.base.BaseController
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.FailureBo

interface DetailController : BaseController {
    suspend fun fetchDetailCharacter(id: Int): Either<FailureBo, CharacterBo>
}
