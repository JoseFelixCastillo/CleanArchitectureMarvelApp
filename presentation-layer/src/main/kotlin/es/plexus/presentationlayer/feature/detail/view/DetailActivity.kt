package es.plexus.presentationlayer.feature.detail.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import es.plexus.domainlayer.feature.detail.DetailController
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.presentation_layer.R
import es.plexus.presentation_layer.databinding.ActivityDetailBinding
import es.plexus.presentationlayer.base.BaseMvvmActivity
import es.plexus.presentationlayer.feature.detail.viewmodel.DetailViewModel
import es.plexus.presentationlayer.model.CharacterVo
import es.plexus.presentationlayer.model.FailureVo
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseMvvmActivity<DetailViewModel, DetailController, DetailState>() {

    companion object {
        const val INTENT_DATA_KEY = "characterItem"
    }

    override val viewModel: DetailViewModel by viewModel()
    private lateinit var viewBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViewModel()
        val id = intent.getIntExtra(INTENT_DATA_KEY, DEFAULT_INTEGER_VALUE)
        viewModel.onViewCreated(id = id)
    }

    override fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

    override fun processRenderState(renderState: DetailState) {
        when (renderState) {
            is DetailState.ShowCharacterDetail -> renderCharacter(renderState.characterVo)
            is DetailState.ShowError -> renderError(renderState.failure)
        }
    }

    private fun renderCharacter(characterVo: CharacterVo) {
        with(viewBinding) {
            title = characterVo.name
            tvDescription.text = characterVo.description
            Glide.with(this@DetailActivity)
                .load("${characterVo.thumbnail.path}/detail.${characterVo.thumbnail.extension}")
                .placeholder(R.drawable.img_marvel_placeholder)
                .fitCenter()
                .into(image)
        }
    }

    private fun renderError(error: FailureVo) {
        Toast.makeText(applicationContext, getString(error.msgResource), Toast.LENGTH_SHORT).show()
    }
}
