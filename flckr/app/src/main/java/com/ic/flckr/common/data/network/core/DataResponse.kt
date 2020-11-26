package com.ic.flckr.common.data.network.core

data class DataResponse<T>(
    @Transient
    var request: DataRequest? = null,
    var code: Int = 0,
    var responseObject: T? = null
)
