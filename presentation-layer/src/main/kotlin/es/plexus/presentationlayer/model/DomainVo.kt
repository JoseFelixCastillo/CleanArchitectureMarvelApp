package es.plexus.presentationlayer.model

data class CharacterWrapperVo(
    val attributionText: String,
    val attributionHtml: String,
    val characterResults: List<CharacterVo>
)

data class CharacterVo(
    val id: Int,
    val name: String,
    val description: String,
    val resourceUri: String,
    val thumbnail: ImageVo,
)

data class ImageVo(
    val extension: String,
    val path: String
)
