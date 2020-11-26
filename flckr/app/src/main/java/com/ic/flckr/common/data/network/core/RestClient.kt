package com.ic.flckr.common.data.network.core

abstract class RestClient<RESPONSE> {
    abstract suspend fun execute(request: DataRequest): RESPONSE
}
