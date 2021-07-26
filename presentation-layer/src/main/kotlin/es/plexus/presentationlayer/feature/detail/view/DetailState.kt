package es.plexus.presentationlayer.feature.detail.view

import es.plexus.presentationlayer.base.BaseState
import es.plexus.presentationlayer.model.CharacterVo
import es.plexus.presentationlayer.model.FailureVo

sealed class DetailState : BaseState() {
    data class ShowCharacterDetail(val characterVo: CharacterVo) : DetailState()
    data class ShowError(val failure: FailureVo) : DetailState()
}
