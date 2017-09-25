package com.apg.library.camera.widget.exception

/**
 * Created by siwarats on 22/9/2560.
 */
class CameraViewException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean,
                writableStackTrace: Boolean) : super(message, cause, enableSuppression,
            writableStackTrace)
}