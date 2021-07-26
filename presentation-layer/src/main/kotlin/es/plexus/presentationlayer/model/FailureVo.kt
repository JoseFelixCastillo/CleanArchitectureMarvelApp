package es.plexus.presentationlayer.model

import es.plexus.presentation_layer.R

sealed class FailureVo(val msgResource: Int) {
    object NoNetwork : FailureVo(msgResource = R.string.no_connection_error_msg)
    object NoDetails : FailureVo(msgResource = R.string.error_detal)
    object TryAgain : FailureVo(msgResource = R.string.try_again)
}
