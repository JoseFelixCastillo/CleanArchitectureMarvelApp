package es.plexus.presentationlayer.base

import androidx.lifecycle.ViewModel
import es.plexus.domainlayer.base.BaseController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This parametrized abstract class is intended to be extended by any app presentation-layer view-model which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param S represents the state of the view, and must extend from [BaseState]
 * @property screenState the [StateFlow] which will be updated to notify the view
 *
 */

abstract class BaseMvvmViewModel<T : BaseController, S : BaseState>(protected val controller: T) :
    ViewModel() {

    protected val _screenState: MutableStateFlow<ScreenState<S>> by lazy {
        MutableStateFlow(
            ScreenState.Loading
        )
    }
    val screenState: StateFlow<ScreenState<S>>
        get() {
            return _screenState
        }
}
