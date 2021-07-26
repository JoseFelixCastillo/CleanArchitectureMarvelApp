package es.plexus.domainlayer.di

import es.plexus.domainlayer.base.UseCase
import es.plexus.domainlayer.feature.detail.DetailController
import es.plexus.domainlayer.feature.detail.DetailControllerImpl
import es.plexus.domainlayer.feature.main.MainController
import es.plexus.domainlayer.feature.main.MainControllerImpl
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.usecase.FETCH_CHARACTERS_UC_TAG
import es.plexus.domainlayer.usecase.FETCH_DETAIL_CHARACTER_UC_TAG
import es.plexus.domainlayer.usecase.FetchCharactersUC
import es.plexus.domainlayer.usecase.FetchDetailCharacterUC
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModules = module {
    factory<UseCase<UseCase.NoneParams, CharacterDataWrapperBo>>(named(name = FETCH_CHARACTERS_UC_TAG)) {
        FetchCharactersUC(get())
    }
    factory<UseCase<Int, CharacterBo>>(named(name = FETCH_DETAIL_CHARACTER_UC_TAG)) {
        FetchDetailCharacterUC(get())
    }
}

val controllerModules = module {
    factory<MainController> {
        MainControllerImpl(get(named(name = FETCH_CHARACTERS_UC_TAG)))
    }
    factory<DetailController> {
        DetailControllerImpl(get(named(name = FETCH_DETAIL_CHARACTER_UC_TAG)))
    }
}

val domainLayerModules = listOf(useCasesModules, controllerModules)
