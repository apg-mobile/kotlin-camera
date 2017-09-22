package com.apg.camera.widget

import android.hardware.Camera

fun CameraPreview.setPreviewCallback(cb: (ByteArray, Camera) -> Unit) {
    previewCallback = cb
}