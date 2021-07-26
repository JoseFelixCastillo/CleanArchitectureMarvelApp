package es.plexus.presentationlayer.di

import es.plexus.presentationlayer.feature.detail.viewmodel.DetailViewModel
import es.plexus.presentationlayer.feature.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationLayerModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        DetailViewModel(get())
    }
}
