package es.plexus.datalayer.utils.timestamp

class TimestampDataSourceImpl : TimestampDataSource {
    override fun getCurrentTimestamp() = System.currentTimeMillis()
}
