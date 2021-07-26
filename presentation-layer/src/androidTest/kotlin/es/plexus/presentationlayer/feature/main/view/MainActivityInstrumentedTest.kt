package es.plexus.presentationlayer.feature.main.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.DEFAULT_STRING_VALUE
import es.plexus.domainlayer.model.ImageBo
import es.plexus.presentation_layer.R
import es.plexus.presentationlayer.base.ScreenState
import es.plexus.presentationlayer.feature.detail.view.DetailState
import es.plexus.presentationlayer.feature.detail.viewmodel.DetailViewModel
import es.plexus.presentationlayer.feature.main.viewholder.CharacterViewHolder
import es.plexus.presentationlayer.feature.main.viewmodel.MainViewModel
import es.plexus.presentationlayer.model.CharacterVo
import es.plexus.presentationlayer.model.CharacterWrapperVo
import es.plexus.presentationlayer.model.ImageVo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityInstrumentedTest : KoinTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private var state = MutableStateFlow<ScreenState<MainState>>(ScreenState.Loading)
    private lateinit var viewModelMock: MainViewModel

    @Before
    fun setUp() {
        viewModelMock = mockk(relaxed = true)
        every { viewModelMock.screenState } returns state
        startKoin {
            modules(listOf(module {
                viewModel { viewModelMock }
            }))
        }
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        stopKoin()
        scenario.close()
    }

    @Test
    fun whenActivityStartsProgressBarIsDisplayed() {
        onView(withId(R.id.pbLoading))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkThatWhenPressOnItemCharacterCallViewModelWithTheItem() {
        // given
        val dummyCharacters = getDummyCharacterVoWrapper()
        state.value = ScreenState.Render(MainState.ShowCharacters(dummyCharacters))
        // when
        onView(withId(R.id.rvItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterViewHolder>(
                0,
                ViewActions.click()
            )
        )
        // then
        verify(exactly = 1) { viewModelMock.onViewCreated() }
        verify(exactly = 1) { viewModelMock.onCharacterItemSelected(dummyCharacters.characterResults[0]) }
    }

    @Test
    fun checkThatItemViewIsVisibleThenLoad() {
        // given
        val dummyCharacters = getDummyCharacterVoWrapper()
        state.value = ScreenState.Render(MainState.ShowCharacters(dummyCharacters))
        // then
        onView(withId(R.id.rvItems)).check(
            ViewAssertions.matches(
                hasDescendant(
                    withText(
                        dummyCharacters.characterResults[0].name
                    )
                )
            )
        )
    }

    private fun getDummyCharacterVoWrapper() = CharacterWrapperVo(
        attributionText = DEFAULT_STRING_VALUE,
        attributionHtml = DEFAULT_STRING_VALUE,
        characterResults = listOf(getDummyCharacterVo(), getDummyValidCharacterVo())
    )

    private fun getDummyCharacterVo() = CharacterVo(
        id = DEFAULT_INTEGER_VALUE,
        name = "Spiderman",
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
