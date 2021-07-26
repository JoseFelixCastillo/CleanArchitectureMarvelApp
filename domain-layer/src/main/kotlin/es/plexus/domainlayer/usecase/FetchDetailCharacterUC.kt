package es.plexus.domainlayer.usecase

import arrow.core.Either
import es.plexus.domainlayer.base.UseCase
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.repository.MarvelRepository

const val FETCH_DETAIL_CHARACTER_UC_TAG = "FetchDetailCharacterUCTag"

internal class FetchDetailCharacterUC(private val repository: MarvelRepository) :
    UseCase<Int, CharacterBo> {
    override suspend fun run(params: Int): Either<FailureBo, CharacterBo> =
        repository.fetchCharacterDetail(params)
}
