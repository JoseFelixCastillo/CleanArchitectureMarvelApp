package es.plexus.domainlayer.repository

import arrow.core.Either
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo

interface MarvelRepository {
    suspend fun fetchCharacters(): Either<FailureBo, CharacterDataWrapperBo>
    suspend fun fetchCharacterDetail(id: Int): Either<FailureBo, CharacterBo>
}
