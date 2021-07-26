package es.plexus.datalayer.utils.hash

import es.plexus.datalayer.utils.interceptor.PRIVATE_KEY
import es.plexus.datalayer.utils.interceptor.PUBLIC_KEY
import java.math.BigInteger
import java.security.MessageDigest

private const val RADIX = 16
private const val SIGNUM = 1
private const val MD5_ALGORITHM = "MD5"

class Md5HashDataSource : HashDataSource {
    override fun buildHash(timestamp: String): String {
        val md = MessageDigest.getInstance(MD5_ALGORITHM)
        val data = timestamp.toByteArray() + PRIVATE_KEY.toByteArray() + PUBLIC_KEY.toByteArray()
        return BigInteger(SIGNUM, md.digest(data)).toString(RADIX)
    }
}
