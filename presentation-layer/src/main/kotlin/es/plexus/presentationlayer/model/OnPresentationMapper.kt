package es.plexus.presentationlayer.model

import es.plexus.domainlayer.model.CharacterBo
import es.plexus.domainlayer.model.CharacterDataWrapperBo
import es.plexus.domainlayer.model.FailureBo
import es.plexus.domainlayer.model.ImageBo

fun FailureBo.toFailureVo(): FailureVo =
    when (this) {
        is FailureBo.NoNetwork -> FailureVo.NoNetwork
        is FailureBo.ClientError -> FailureVo.TryAgain
        is FailureBo.EmptyResponse -> FailureVo.TryAgain
        is FailureBo.FeatureFailure -> FailureVo.TryAgain
        is FailureBo.ServerError -> FailureVo.TryAgain
        is FailureBo.UnexpectedFailure -> FailureVo.TryAgain
    }

fun CharacterDataWrapperBo.toCharacterWrapperVo(): CharacterWrapperVo = CharacterWrapperVo(
    attributionText = attributionText,
    attributionHtml = attributionHtml,
    characterResults = charactersResults.map { characterBo ->
        characterBo.toCharacterVo()
    }
)

fun CharacterBo.toCharacterVo() = CharacterVo(
    id = id,
    name = name,
    description = description,
    resourceUri = resourceUri,
    thumbnail = thumbnail.toImageVo()
)

fun ImageBo.toImageVo() = ImageVo(
    extension = extension,
    path = path
)
