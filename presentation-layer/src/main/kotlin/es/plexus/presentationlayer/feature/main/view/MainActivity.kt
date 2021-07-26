package es.plexus.presentationlayer.feature.main.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.plexus.domainlayer.feature.main.MainController
import es.plexus.domainlayer.model.DEFAULT_STRING_VALUE
import es.plexus.presentation_layer.databinding.ActivityMainBinding
import es.plexus.presentationlayer.base.BaseMvvmActivity
import es.plexus.presentationlayer.feature.detail.view.DetailActivity
import es.plexus.presentationlayer.feature.main.adapter.CharacterAdapter
import es.plexus.presentationlayer.feature.main.viewmodel.MainViewModel
import es.plexus.presentationlayer.model.CharacterWrapperVo
import es.plexus.presentationlayer.model.FailureVo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseMvvmActivity<MainViewModel, MainController, MainState>() {
    override val viewModel: MainViewModel by viewModel()
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViewModel()
        viewModel.onViewCreated()
    }

    override fun processRenderState(renderState: MainState) {
        when (renderState) {
            is MainState.ShowError -> renderError(renderState.error)
            is MainState.ShowCharacters -> renderCharacters(renderState.charactersWrapperVo)
            is MainState.GoToDetailCharacter -> goToDetailCharacter(renderState.id)
        }
    }

    override fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

    private fun renderCharacters(charactersWrapperVo: CharacterWrapperVo) {
        with(viewBinding.rvItems) {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = CharacterAdapter(
                itemList = charactersWrapperVo.characterResults,
                onItemSelected = { character ->
                    viewModel.onCharacterItemSelected(item = character)
                }
            )
        }
        with(viewBinding) {
            if (charactersWrapperVo.attributionHtml != DEFAULT_STRING_VALUE) {
                attributionHtmlText.text = charactersWrapperVo.attributionHtml.toHtmlSpan()
                attributionHtmlText.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun String.toHtmlSpan(): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(this)
        }
    }

    private fun renderError(error: FailureVo) {
        Toast.makeText(applicationContext, getString(error.msgResource), Toast.LENGTH_SHORT).show()
    }

    private fun goToDetailCharacter(id: Int) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(DetailActivity.INTENT_DATA_KEY, id)
        startActivity(intent)
    }
}
