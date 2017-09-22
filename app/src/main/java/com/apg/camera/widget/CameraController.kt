package com.apg.camera.widget

import android.content.Context
import android.hardware.Camera
import android.view.Surface
import android.view.WindowManager

/**
 * Created by siwarats on 22/9/2560.
 */
object CameraController {

    fun getCameraInstance(): Camera? {
        var camera: Camera? = null
        try {
            camera = Camera.open()
        } catch (e: Exception) {

        }
        return camera
    }

    fun getBestCameraSize(sizes: MutableList<Camera.Size>?): Camera.Size? {
        if (sizes == null || sizes.isEmpty()) return null

        return sizes.maxBy { it.width }
    }

    fun getBestSurfaceSize(context: Context, previewSize: Camera.Size): Camera.Size {
        return if (isDevicePortrait(context)) {
            previewSize
        } else {
            val tempH = previewSize.height
            val tempW = previewSize.width
            previewSize.width = tempH
            previewSize.height = tempW
            previewSize
        }
    }

    fun isDevicePortrait(context: Context): Boolean {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        val orientation = wm?.defaultDisplay?.orientation
        return orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180
    }

    fun getCameraOrientation(context : Context) : Orientation {
        val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        return when (rotation) {
            Surface.ROTATION_0 -> Orientation.PORTRAIT
            Surface.ROTATION_90 -> Orientation.LANDSCAPE
            Surface.ROTATION_180 -> Orientation.REVERSE_PORTRAIT
            else -> Orientation.REVERSE_LANDSCAPE
        }
    }
}