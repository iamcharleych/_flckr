package com.ic.flckr.network.core

class DataRequest {

    val operation: String
    var status: Status = Status.PENDING
    val params = HashMap<String, Any>()

    constructor(operation: String) {
        this.operation = operation
    }

    constructor(request: DataRequest) {
        this.operation = request.operation
        this.status = request.status
        this.params.putAll(request.params)
    }

    fun with(key: String, value: Any): DataRequest {
        params[key] = value

        return this
    }

    fun with(params: Map<String, Any>): DataRequest {
        this.params.putAll(params)
        return this
    }

    override fun toString(): String {
        return "DataRequest{operation='$operation', params='$params, status='$status'}"
    }

    enum class Status {
        PENDING,
        SUCCESS,
        FAILED
    }
}

inline fun <reified T> DataRequest.requireArgument(key: String): T {
    val value = params[key]
    return if (value is T) value else throw IllegalStateException("Argument for key=$key not found")
}
