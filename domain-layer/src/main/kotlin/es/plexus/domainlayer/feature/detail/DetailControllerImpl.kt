package es.plexus.domainlayer.feature.detail

import arrow.core.Either
import es.plexus.domainlayer.base.UseCase
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.FailureBo

internal class DetailControllerImpl(private val fetchDetailCharacterUC: UseCase<Int, CharacterBo>) :
    DetailController {
    override suspend fun fetchDetailCharacter(id: Int): Either<FailureBo, CharacterBo> =
        fetchDetailCharacterUC.run(id)
}
