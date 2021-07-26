package es.plexus.datalayer.datasource.characters

import arrow.core.Either
import es.plexus.datalayer.model.toCharacterDataWrapperBo
import es.plexus.datalayer.service.MarvelApiService
import es.plexus.datalayer.utils.request.retrofitSafeCall
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo

internal class CharactersRemoteDataSource(private val marvelApiService: MarvelApiService) :
    CharactersDataSource.Remote {
    override suspend fun requestCharacters(): Either<FailureBo, CharacterDataWrapperBo> {
        return retrofitSafeCall(
            retrofitRequest = marvelApiService::requestCharacters,
            transform = { it.toCharacterDataWrapperBo() }
        )
    }

    override suspend fun requestDetailCharacter(id: Int): Either<FailureBo, CharacterDataWrapperBo> {
        return retrofitSafeCall(
            parameter = id,
            retrofitRequest = marvelApiService::requestDetailCharacter,
            transform = { it.toCharacterDataWrapperBo() }
        )
    }
}
