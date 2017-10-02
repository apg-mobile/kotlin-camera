package com.apg.library.camera.widget

import android.hardware.Camera

fun CameraView.setPreviewCallback(cb: (ByteArray, Camera) -> Unit) {
    val child = getChildAt(0)
    if(child is CameraPreview) child.previewCallback = cb
}