package es.plexus.datalayer.datasource.characters

import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo

internal class CharactersCacheDataSource : CharactersDataSource.Cache {
    private var characterDataWrapper: CharacterDataWrapperBo? = null
    private var characterDetailMap: MutableMap<Int, CharacterBo> = mutableMapOf()

    override suspend fun getCharacters(): CharacterDataWrapperBo? = characterDataWrapper

    override suspend fun saveCharacters(characterDataWrapper: CharacterDataWrapperBo?) {
        this.characterDataWrapper = characterDataWrapper
    }

    override suspend fun getDetailCharacter(id: Int): CharacterBo? = characterDetailMap[id]

    override suspend fun saveDetailCharacter(characterBo: CharacterBo?) {
        if (characterBo != null) {
            characterDetailMap[characterBo.id] = characterBo
        }
    }
}
