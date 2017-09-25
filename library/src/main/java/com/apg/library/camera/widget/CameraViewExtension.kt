package com.apg.library.camera.widget

import android.hardware.Camera

fun CameraView.setPreviewCallback(cb: (ByteArray, Camera) -> Unit) {
    previewCallback = cb
}