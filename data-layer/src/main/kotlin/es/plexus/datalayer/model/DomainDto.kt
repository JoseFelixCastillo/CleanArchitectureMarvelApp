package es.plexus.datalayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDataWrapperDto(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "attributionText")
    val attributionText: String?,
    @Json(name = "attributionHTML")
    val attributionHtml: String?,
    @Json(name = "data")
    val data: CharacterDataContainerDto?,
    @Json(name = "etag")
    val digestValue: String?
)

@JsonClass(generateAdapter = true)
data class CharacterDataContainerDto(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val totalAvailable: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val charactersResults: List<CharacterDto>?
)

@JsonClass(generateAdapter = true)
data class CharacterDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "modified")
    val lastModified: String?,
    @Json(name = "resourceURI")
    val resourceUri: String?,
    @Json(name = "urls")
    val urls: List<UrlDto>?,
    @Json(name = "thumbnail")
    val thumbnail: ImageDto?,
    @Json(name = "comics")
    val comics: ComicIssuesContainerDto?,
    @Json(name = "stories")
    val stories: StoryContainerDto?,
    @Json(name = "events")
    val events: EventsContainerDto?,
    @Json(name = "series")
    val series: SeriesContainerDto?
)

@JsonClass(generateAdapter = true)
data class UrlDto(
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

@JsonClass(generateAdapter = true)
data class ComicIssuesContainerDto(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val availablePath: String?,
    @Json(name = "items")
    val items: List<ComicSummaryDto>?
)

@JsonClass(generateAdapter = true)
data class ComicSummaryDto(
    @Json(name = "resourceURI")
    val comicResourcePath: String?,
    @Json(name = "name")
    val name: String?
)

@JsonClass(generateAdapter = true)
data class StoryContainerDto(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val availablePath: String?,
    @Json(name = "items")
    val items: List<StorySummaryDto>?
)

@JsonClass(generateAdapter = true)
data class StorySummaryDto(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?
)

@JsonClass(generateAdapter = true)
data class EventsContainerDto(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val availablePath: String?,
    @Json(name = "items")
    val items: List<EventSummaryDto>?
)

@JsonClass(generateAdapter = true)
data class EventSummaryDto(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
)

@JsonClass(generateAdapter = true)
data class SeriesContainerDto(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "returned")
    val returned: Int?,
    @Json(name = "collectionURI")
    val availablePath: String?,
    @Json(name = "items")
    val items: List<SeriesSummaryDto>?
)

@JsonClass(generateAdapter = true)
data class SeriesSummaryDto(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
)
