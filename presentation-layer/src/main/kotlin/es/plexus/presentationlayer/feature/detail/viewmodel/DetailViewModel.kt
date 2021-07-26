package es.plexus.presentationlayer.feature.detail.viewmodel

import androidx.lifecycle.viewModelScope
import es.plexus.domainlayer.feature.detail.DetailController
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.FailureBo
import es.plexus.presentationlayer.base.BaseMvvmViewModel
import es.plexus.presentationlayer.base.ScreenState
import es.plexus.presentationlayer.feature.detail.view.DetailState
import es.plexus.presentationlayer.model.FailureVo
import es.plexus.presentationlayer.model.toCharacterVo
import es.plexus.presentationlayer.model.toFailureVo
import kotlinx.coroutines.launch

class DetailViewModel(controller: DetailController) :
    BaseMvvmViewModel<DetailController, DetailState>(controller) {

    fun onViewCreated(id: Int) {
        if (id != DEFAULT_INTEGER_VALUE) {
            viewModelScope.launch {
                controller.fetchDetailCharacter(id).fold(::handleError, ::handleSuccess)
            }
        } else {
            _screenState.value = ScreenState.Render(DetailState.ShowError(FailureVo.NoDetails))
        }
    }

    private fun handleSuccess(characterBo: CharacterBo) {
        _screenState.value =
            ScreenState.Render(DetailState.ShowCharacterDetail(characterVo = characterBo.toCharacterVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _screenState.value =
            ScreenState.Render(DetailState.ShowError(failure = failureBo.toFailureVo()))
    }
}
