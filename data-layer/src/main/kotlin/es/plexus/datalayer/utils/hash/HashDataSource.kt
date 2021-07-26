package es.plexus.datalayer.utils.hash

interface HashDataSource {
    fun buildHash(timestamp: String): String
}
