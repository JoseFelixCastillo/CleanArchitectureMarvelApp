package es.plexus.presentationlayer.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.plexus.domainlayer.base.BaseController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseMvvmActivity<T : BaseMvvmViewModel<S, U>, S : BaseController, U : BaseState> :
    AppCompatActivity(), BaseMvvmView<T, S, U> {

    override fun initViewModel() {
        lifecycleScope.launch {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<U> -> {
                        processRenderState(screenState.renderState)
                        hideLoading()
                    }
                }
            }
        }
    }
    abstract fun showLoading()
    abstract fun hideLoading()
}
