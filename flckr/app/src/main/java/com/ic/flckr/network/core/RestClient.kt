package com.ic.flckr.network.core

import com.ic.data.network.core.DataRequest

abstract class RestClient<RESPONSE> {
    abstract suspend fun execute(request: DataRequest): RESPONSE
}
