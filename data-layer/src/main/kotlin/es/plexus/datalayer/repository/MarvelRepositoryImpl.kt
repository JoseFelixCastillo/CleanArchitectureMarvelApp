package es.plexus.datalayer.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import es.plexus.datalayer.datasource.characters.CharactersDataSource
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.repository.MarvelRepository

internal class MarvelRepositoryImpl(
    private val charactersRemoteDataSource: CharactersDataSource.Remote,
    private val charactersCacheDataSource: CharactersDataSource.Cache
) : MarvelRepository {
    override suspend fun fetchCharacters(): Either<FailureBo, CharacterDataWrapperBo> {
        val charactersCache = charactersCacheDataSource.getCharacters()
        return charactersCache?.right() ?: charactersRemoteDataSource.requestCharacters()
            .also { either ->
                if (either.isRight()) {
                    charactersCacheDataSource.saveCharacters(either.orNull())
                }
            }
    }

    override suspend fun fetchCharacterDetail(id: Int): Either<FailureBo, CharacterBo> {
        val characterDetailCache = charactersCacheDataSource.getDetailCharacter(id)
        return characterDetailCache?.right()
            ?: charactersRemoteDataSource.requestDetailCharacter(id).fold({
                it.left()
            }, { character ->
                if (character.charactersResults.isEmpty()) {
                    FailureBo.EmptyResponse("No data").left()
                } else {
                    charactersCacheDataSource.saveDetailCharacter(character.charactersResults[0])
                    character.charactersResults[0].right()
                }
            })
    }
}
