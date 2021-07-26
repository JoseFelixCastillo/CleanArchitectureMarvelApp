package es.plexus.domainlayer.model

const val DEFAULT_INTEGER_VALUE = -1
const val DEFAULT_STRING_VALUE = ""

data class CharacterDataWrapperBo(
    val attributionText: String,
    val attributionHtml: String,
    val charactersResults: List<CharacterBo>
)

data class CharacterBo(
    val id: Int,
    val name: String,
    val description: String,
    val resourceUri: String,
    val thumbnail: ImageBo,
)

data class ImageBo(
    val extension: String,
    val path: String
)
