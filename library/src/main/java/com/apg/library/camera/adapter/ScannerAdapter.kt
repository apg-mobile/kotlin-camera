package com.apg.library.camera.adapter

/**
 * Created by siwarats on 25/9/2560.
 */
interface ScannerAdapter<in T> : android.hardware.Camera.PreviewCallback {
    fun onResponse(response: T)
}