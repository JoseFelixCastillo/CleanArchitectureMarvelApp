package es.plexus.datalayer.utils.timestamp

interface TimestampDataSource {
    fun getCurrentTimestamp(): Long
}
