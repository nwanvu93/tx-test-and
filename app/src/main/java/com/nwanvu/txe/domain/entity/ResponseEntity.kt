package com.nwanvu.txe.domain.entity

/**
 * Base Class for handling responses from API calls.
 */
class ResponseEntity<T>(
    val error: String?,
    val data: T?,
) {
    /**
     * check response is success or not
     */
    fun isSuccess() = error == null

    companion object {
        /**
         * create an empty response
         */
        fun <T> default(): ResponseEntity<T> = ResponseEntity(null, null)
    }
}