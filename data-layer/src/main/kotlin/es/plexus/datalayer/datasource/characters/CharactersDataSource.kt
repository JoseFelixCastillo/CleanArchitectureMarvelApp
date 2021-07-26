package es.plexus.datalayer.datasource.characters

import arrow.core.Either
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo

interface CharactersDataSource {
    interface Remote {
        suspend fun requestCharacters(): Either<FailureBo, CharacterDataWrapperBo>
        suspend fun requestDetailCharacter(id: Int): Either<FailureBo, CharacterDataWrapperBo>
    }
    interface Cache {
        suspend fun getCharacters(): CharacterDataWrapperBo?
        suspend fun saveCharacters(characterDataWrapper: CharacterDataWrapperBo?)
        suspend fun getDetailCharacter(id: Int): CharacterBo?
        suspend fun saveDetailCharacter(characterBo: CharacterBo?)
    }
}
