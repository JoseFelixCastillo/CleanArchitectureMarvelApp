package es.plexus.presentationlayer.feature.main.view

import es.plexus.presentationlayer.base.BaseState
import es.plexus.presentationlayer.model.CharacterWrapperVo
import es.plexus.presentationlayer.model.FailureVo

sealed class MainState : BaseState() {
    data class ShowError(val error: FailureVo) : MainState()
    data class ShowCharacters(val charactersWrapperVo: CharacterWrapperVo) : MainState()
    data class GoToDetailCharacter(val id: Int) : MainState()
}
