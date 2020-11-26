package com.ic.flckr.feature.gallery.data.networkclient

enum class ApiResponseCode(val code: Int) {
    /**
     * Ok
     */
    SUCCESS(200),
    /**
     * The session is still being created (wait and try again).
     */
    NO_CONTENT(204),
    /**
     * The results have not been modified since the last search.
     */
    BAD_REQUEST(400),
    /**
     * The API Key was not supplied, or it was invalid, or it is not authorized to access the service.
     */
    FORBIDDEN(403),
    /**
     * Resource not found
     */
    NOT_FOUND(404),
    /**
     * There have been too many requests in the last minute.
     */
    TOO_MANY_REQUESTS(429),
    /**
     * An internal server error has occurred which has been logged.
     */
    SERVER_ERROR(500),

    UNKNOWN(-1);

    companion object {
        private val map = values().associateBy { apiCode -> apiCode.code }

        fun Int.toResponseCode(): ApiResponseCode = map[this] ?: UNKNOWN
    }
}
