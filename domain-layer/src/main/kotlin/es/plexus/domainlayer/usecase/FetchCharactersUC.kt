package es.plexus.domainlayer.usecase

import arrow.core.Either
import es.plexus.domainlayer.base.UseCase
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.repository.MarvelRepository

const val FETCH_CHARACTERS_UC_TAG = "FetchCharactersUCTag"

internal class FetchCharactersUC(
    private val marvelRepository: MarvelRepository
) : UseCase<UseCase.NoneParams, CharacterDataWrapperBo> {
    override suspend fun run(params: UseCase.NoneParams): Either<FailureBo, CharacterDataWrapperBo> =
        marvelRepository.fetchCharacters()
}
