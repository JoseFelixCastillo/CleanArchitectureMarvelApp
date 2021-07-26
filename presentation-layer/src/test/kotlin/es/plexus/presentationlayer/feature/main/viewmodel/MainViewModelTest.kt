package es.plexus.presentationlayer.feature.main.viewmodel

import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import es.plexus.domainlayer.feature.main.MainController
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.DEFAULT_STRING_VALUE
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.model.ImageBo
import es.plexus.presentationlayer.base.ScreenState
import es.plexus.presentationlayer.di.presentationLayerModule
import es.plexus.presentationlayer.feature.TestCoroutineRule
import es.plexus.presentationlayer.feature.main.view.MainState
import es.plexus.presentationlayer.model.CharacterVo
import es.plexus.presentationlayer.model.FailureVo
import es.plexus.presentationlayer.model.ImageVo
import es.plexus.presentationlayer.model.toCharacterWrapperVo
import es.plexus.presentationlayer.model.toFailureVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class MainViewModelTest : KoinTest {

    private val viewModel: MainViewModel by inject()
    private lateinit var mockController: MainController

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        mockController = mock()
        startKoin {
            modules(
                listOf(
                    presentationLayerModule,
                    module(override = true) {
                        factory() { mockController }
                    }
                ))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check that onViewCreated load characters if data is successful`() = runBlockingTest {
        // given
        val dummyCharacters = getDummyCharacterBoWrapper()
        whenever(mockController.fetchCharacters()).thenReturn(dummyCharacters.right())
        // when
        viewModel.onViewCreated()
        // then
        verify(mockController).fetchCharacters()
        Assert.assertEquals(
            MainState.ShowCharacters(dummyCharacters.toCharacterWrapperVo()),
            getRenderState()
        )
    }

    @Test
    fun `check that onViewCreated load error if data return no network`() = runBlockingTest {
        // given
        val failure = FailureBo.NoNetwork
        whenever(mockController.fetchCharacters()).thenReturn(failure.left())
        // when
        viewModel.onViewCreated()
        // then
        verify(mockController).fetchCharacters()
        Assert.assertEquals(
            MainState.ShowError(failure.toFailureVo()),
            getRenderState()
        )
    }

    @Test
    fun `check that onViewCreated load error if data return error`() = runBlockingTest {
        // given
        val failure = FailureBo.UnexpectedFailure(DEFAULT_INTEGER_VALUE, DEFAULT_STRING_VALUE)
        whenever(mockController.fetchCharacters()).thenReturn(failure.left())
        // when
        viewModel.onViewCreated()
        // then
        verify(mockController).fetchCharacters()
        Assert.assertEquals(
            MainState.ShowError(failure.toFailureVo()),
            getRenderState()
        )
    }

    @Test
    fun `check that when an item was pressed with a valid id, detail page logic is activated`() =
        runBlockingTest {
            // given
            val dummyCharacter = getDummyValidCharacterVo()
            // when
            viewModel.onCharacterItemSelected(dummyCharacter)
            // then
            Assert.assertEquals(
                MainState.GoToDetailCharacter(dummyCharacter.id),
                getRenderState()
            )
        }

    @Test
    fun `check that when an item was pressed with a non valid id, error no details is showed`() =
        runBlockingTest {
            // given
            val dummyCharacter = getDummyCharacterVo()
            // when
            viewModel.onCharacterItemSelected(dummyCharacter)
            // then
            Assert.assertEquals(
                MainState.ShowError(FailureVo.NoDetails),
                getRenderState()
            )
        }

    private fun getRenderState() =
        (viewModel.screenState.value as? ScreenState.Render<MainState>)?.renderState

    private fun getDummyCharacterBoWrapper() = CharacterDataWrapperBo(
        attributionText = DEFAULT_STRING_VALUE,
        attributionHtml = DEFAULT_STRING_VALUE,
        charactersResults = listOf(getDummyCharacterBo())
    )

    private fun getDummyCharacterBo() = CharacterBo(
        id = DEFAULT_INTEGER_VALUE,
        name = DEFAULT_STRING_VALUE,
        description = DEFAULT_STRING_VALUE,
        resourceUri = DEFAULT_STRING_VALUE,
        thumbnail = getDummyThumbnailBo()
    )

    private fun getDummyThumbnailBo() = ImageBo(
        extension = DEFAULT_STRING_VALUE,
        path = DEFAULT_STRING_VALUE
    )

    private fun getDummyCharacterVo() = CharacterVo(
        id = DEFAULT_INTEGER_VALUE,
        name = DEFAULT_STRING_VALUE,
        description = DEFAULT_STRING_VALUE,
        resourceUri = DEFAULT_STRING_VALUE,
        thumbnail = getDummyThumbnailVo()
    )

    private fun getDummyThumbnailVo() = ImageVo(
        extension = DEFAULT_STRING_VALUE,
        path = DEFAULT_STRING_VALUE
    )

    private fun getDummyValidCharacterVo() = CharacterVo(
        id = 1354,
        name = DEFAULT_STRING_VALUE,
        description = DEFAULT_STRING_VALUE,
        resourceUri = DEFAULT_STRING_VALUE,
        thumbnail = getDummyThumbnailVo()
    )
}
