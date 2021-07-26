package es.plexus.datalayer.model

import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.DEFAULT_INTEGER_VALUE
import es.plexus.domainlayer.model.DEFAULT_STRING_VALUE
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.model.ImageBo
import es.plexus.domainlayer.model.MarvelFailureBo

fun FailureDto.toFailureBo(): FailureBo = when (this) {
    is FailureDto.ClientError -> FailureBo.ClientError(code, message)
    is FailureDto.EmptyResponse -> FailureBo.EmptyResponse(message)
    FailureDto.NoNetwork -> FailureBo.NoNetwork
    is FailureDto.ServerError -> FailureBo.ServerError(code, message)
    is FailureDto.UnexpectedFailure -> FailureBo.UnexpectedFailure(code, localizedMessage)
    is FailureDto.FeatureFailure -> handleFailure()
}

private fun FailureDto.FeatureFailure.handleFailure(): FailureBo.FeatureFailure {
    return when (this) {
        is MarvelFailure.LoginError -> MarvelFailureBo.LoginError
        else -> MarvelFailureBo.LoginError
    }
}

fun CharacterDataWrapperDto.toCharacterDataWrapperBo(): CharacterDataWrapperBo =
    CharacterDataWrapperBo(
        attributionText = attributionText ?: DEFAULT_STRING_VALUE,
        attributionHtml = attributionHtml ?: DEFAULT_STRING_VALUE,
        charactersResults = data?.toCharactersBoList() ?: listOf()
    )

fun CharacterDataContainerDto.toCharactersBoList(): List<CharacterBo> = charactersResults?.map {
    CharacterBo(
        id = it.id ?: DEFAULT_INTEGER_VALUE,
        name = it.name ?: DEFAULT_STRING_VALUE,
        description = it.description ?: DEFAULT_STRING_VALUE,
        resourceUri = it.resourceUri ?: DEFAULT_STRING_VALUE,
        thumbnail = it.thumbnail?.toImageBo() ?: getDefaultThumbnailBo()
    )
} ?: listOf()

fun ImageDto.toImageBo(): ImageBo = ImageBo(
    extension = extension ?: DEFAULT_STRING_VALUE,
    path = path ?: DEFAULT_STRING_VALUE
)

private fun getDefaultThumbnailBo() = ImageBo(
    extension = DEFAULT_STRING_VALUE,
    path = DEFAULT_STRING_VALUE
)
