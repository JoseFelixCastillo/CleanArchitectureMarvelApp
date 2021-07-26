package es.plexus.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import es.plexus.domainlayer.feature.main.MainController
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.FailureBo
import es.plexus.presentationlayer.base.BaseMvvmViewModel
import es.plexus.presentationlayer.base.ScreenState
import es.plexus.presentationlayer.feature.main.view.MainState
import es.plexus.presentationlayer.model.CharacterVo
import es.plexus.presentationlayer.model.FailureVo
import es.plexus.presentationlayer.model.toCharacterWrapperVo
import es.plexus.presentationlayer.model.toFailureVo
import kotlinx.coroutines.launch

class MainViewModel(controller: MainController) :
    BaseMvvmViewModel<MainController, MainState>(controller) {

    fun onViewCreated() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch {
            controller.fetchCharacters().fold(::handleError, ::handleSuccess)
        }
    }
    fun onCharacterItemSelected(item: CharacterVo) {
        if (item.id != DEFAULT_INTEGER_VALUE) {
            _screenState.value = ScreenState.Render(MainState.GoToDetailCharacter(item.id))
        } else {
            _screenState.value = ScreenState.Render(MainState.ShowError(FailureVo.NoDetails))
        }
    }

    private fun handleError(failure: FailureBo) {
        _screenState.value = ScreenState.Render(MainState.ShowError(failure.toFailureVo()))
    }

    private fun handleSuccess(charactersWrapper: CharacterDataWrapperBo) {
        _screenState.value =
            ScreenState.Render(MainState.ShowCharacters(charactersWrapper.toCharacterWrapperVo()))
    }
}
