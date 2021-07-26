package es.plexus.datalayer.repository

import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import es.plexus.datalayer.datasource.characters.CharactersDataSource
import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.DEFAULT_STRING_VALUE
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.model.ImageBo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MarvelRepositoryImplTest : KoinTest {

    @InjectMocks
    private lateinit var marvelRepository: MarvelRepositoryImpl

    @Mock
    private lateinit var mockCharactersCacheDataSource: CharactersDataSource.Cache

    @Mock
    private lateinit var mockCharactersRemoteDataSource: CharactersDataSource.Remote

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `check that if characters cache data source has data, return this data`() =
        runBlockingTest {
            // given
            val mockCharacters = getDummyCharacterBoWrapper()
            whenever(mockCharactersCacheDataSource.getCharacters()).thenReturn(
                mockCharacters
            )
            // when
            marvelRepository.fetchCharacters()
            // then
            verify(mockCharactersCacheDataSource).getCharacters()
            Assert.assertEquals(marvelRepository.fetchCharacters(), mockCharacters.right())
        }

    @Test
    fun `check that if characters cache data source do not have data, remote data source request data and return`() =
        runBlockingTest {
            // given
            val mockCharacters = getDummyCharacterBoWrapper()
            whenever(mockCharactersCacheDataSource.getCharacters()).thenReturn(null)
            whenever(mockCharactersRemoteDataSource.requestCharacters()).thenReturn(mockCharacters.right())
            // when
            marvelRepository.fetchCharacters()
            // then
            verify(mockCharactersCacheDataSource).getCharacters()
            verify(mockCharactersRemoteDataSource).requestCharacters()
            verifyNoMoreInteractions(mockCharactersRemoteDataSource)
            Assert.assertEquals(marvelRepository.fetchCharacters(), mockCharacters.right())
        }

    @Test
    fun `check that if characters cache data source do not have data, and remote data source is success, then save characters in cache`() =
        runBlockingTest {
            // given
            val mockCharacters = getDummyCharacterBoWrapper()
            whenever(mockCharactersCacheDataSource.getCharacters()).thenReturn(null)
            whenever(mockCharactersRemoteDataSource.requestCharacters()).thenReturn(mockCharacters.right())
            // when
            marvelRepository.fetchCharacters()
            // then
            verify(mockCharactersCacheDataSource).saveCharacters(mockCharacters)
        }

    @Test
    fun `check that if characters cache data source do not have data, and remote data source is failure, then do not save in cache`() =
        runBlockingTest {
            // given
            val failure = FailureBo.NoNetwork
            whenever(mockCharactersCacheDataSource.getCharacters()).thenReturn(null)
            whenever(mockCharactersRemoteDataSource.requestCharacters()).thenReturn(failure.left())
            // when
            marvelRepository.fetchCharacters()
            // then
            verify(mockCharactersCacheDataSource).getCharacters()
            verifyNoMoreInteractions(mockCharactersCacheDataSource)
            verify(mockCharactersRemoteDataSource).requestCharacters()
            verifyNoMoreInteractions(mockCharactersRemoteDataSource)

        }

    @Test
    fun `check that if characters cache data source do not have data, and remote data source is failure, then return failure`() =
        runBlockingTest {
            // given
            val failure = FailureBo.NoNetwork
            whenever(mockCharactersCacheDataSource.getCharacters()).thenReturn(null)
            whenever(mockCharactersRemoteDataSource.requestCharacters()).thenReturn(failure.left())
            // when
            marvelRepository.fetchCharacters()
            // then
            Assert.assertEquals(marvelRepository.fetchCharacters(), failure.left())
        }

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
}
