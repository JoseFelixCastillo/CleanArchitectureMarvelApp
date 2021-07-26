package es.plexus.domainlayer.feature.main

import arrow.core.Either
import es.plexus.domainlayer.base.UseCase
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo

internal class MainControllerImpl(
    private val fetchCharactersUC: UseCase<UseCase.NoneParams, CharacterDataWrapperBo>
) : MainController {
    override suspend fun fetchCharacters(): Either<FailureBo, CharacterDataWrapperBo> =
        fetchCharactersUC.run(params = UseCase.NoneParams)
}
