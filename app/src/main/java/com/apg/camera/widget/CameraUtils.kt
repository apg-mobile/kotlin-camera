package com.apg.camera.widget

import android.content.Context
import android.hardware.Camera
import android.view.Surface
import android.view.WindowManager

/**
 * Created by Sattha Puangput on 8/21/2015.
 */
class CameraUtils {

    class Size(val w: Int, val h: Int)

    fun printCameraParameter(p: Camera.Parameters, context: Context) {
        val picSize = p.pictureSize
        val preSize = p.previewSize
    }

    enum class CameraOrientation private constructor(val degree: Int) {
        LANDSCAPE(0), PORTRAIT(90), REVERSE_LANDSCAPE(180), REVERSE_PORTRAIT(270)
    }

    companion object {


        private val TAG = "CameraUtils"

        /** A safe way to get an instance of the Camera object.  */
        // attempt to get a Camera instance
        // Camera is not available (in use or does not exist)
        // returns null if camera is unavailable
        val cameraInstance: Camera?
            get() {
                var c: Camera? = null
                try {
                    c = Camera.open()
                } catch (e: Exception) {
                }

                return c
            }

        /**
         * Get current orientation of device
         *
         * @param context a context of caller
         * @return true if portrait, false landscape
         */
        fun isDisplayPortrait(context: Context): Boolean {
            val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
            when (rotation) {
                Surface.ROTATION_0 // display orientation
                -> return true
                Surface.ROTATION_90 -> return false
                Surface.ROTATION_180 -> return true
                else -> return false
            }
        }

        /**
         * Get current orientation of device
         *
         * @param context a context of caller
         * @return an enum of CameraOrientation which provide rotation fix value
         */
        fun getCameraOrientation(context: Context): CameraOrientation {
            val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
            when (rotation) {
                Surface.ROTATION_0 // display orientation
                -> return CameraOrientation.PORTRAIT // camera orientation
                Surface.ROTATION_90 -> return CameraOrientation.LANDSCAPE
                Surface.ROTATION_180 -> return CameraOrientation.REVERSE_PORTRAIT
                else -> return CameraOrientation.REVERSE_LANDSCAPE
            }
        }

        /**
         * Get best support resolution of camera preview
         *
         * @param sizes list of support camera preview sizes
         * @return the best support size of camera preview
         */
        fun getBestCameraSize(sizes: List<Camera.Size>, context: Context): Camera.Size? {
            var bestSize: Camera.Size? = null
            for (size in sizes) {
                if (bestSize == null) {
                    bestSize = size
                } else {
                    if (bestSize.width < size.width) {
                        bestSize = size
                    }
                }
            }
            return bestSize
        }

        /**
         * Get best size for surface view which compatible with selected preview size
         *
         * @param previewSize the preview size support by device
         * @param isPortrait the orientation of device
         * @param reqWidth the display width by display orientation (pixels)
         * @param reqHeight the display height by display orientation (pixels)
         * @return optimal size of surface view
         */
        fun getOptimalSurfaceSize(previewSize: Camera.Size, isPortrait: Boolean, reqWidth: Int, reqHeight: Int): Size {
            var viewWidth: Int
            var viewHeight: Int
            val newCamWidth: Int
            val newCamHeight: Int
            val tmpCamWidth: Int
            val tmpCamHeight: Int
            if (isPortrait) {
                tmpCamWidth = reqHeight
                tmpCamHeight = reqWidth
            } else {
                tmpCamWidth = reqWidth
                tmpCamHeight = reqHeight
            }

            newCamWidth = getNewWidthByCameraHardware(previewSize, tmpCamHeight)
            newCamHeight = getNewHeightByCameraHardware(previewSize, tmpCamWidth)

            // camera default portrait and device portrait is different side.
            if (isPortrait) {
                // use new height which, keep aspect ratio from reqWidth
                viewWidth = reqWidth
                viewHeight = newCamWidth
                if (viewHeight < reqHeight) {
                    // however, if height not fit to screen, use reqHeight and use new width with keep aspect ratio.
                    viewWidth = newCamHeight
                    viewHeight = reqHeight
                }
            } else {
                // use new width which, keep aspect ratio from reqHeight
                viewWidth = newCamWidth
                viewHeight = reqHeight
                if (viewWidth < reqWidth) {
                    // however, if width not fit to screen, use reqWidth and use new height with keep aspect ratio.
                    viewWidth = reqWidth
                    viewHeight = newCamHeight
                }
            }

            return Size(viewWidth, viewHeight)
        }

        /**
         * Get a new relative width from height by keep aspect ratio with selected camera preview size
         *
         * @param previewSize the preview size support by device
         * @param newHeight a new height that use to calculate for its relative width
         * @return new width
         */
        fun getNewWidthByCameraHardware(previewSize: Camera.Size, newHeight: Int): Int {
            return Math.round(previewSize.width.toDouble() / previewSize.height * newHeight).toInt()
        }

        /**
         * Get a new relative height from width by keep aspect ratio with selected camera preview size
         *
         * @param previewSize the preview size support by device
         * @param newWidth a new width that use to calculate for its relative height
         * @return new height
         */
        fun getNewHeightByCameraHardware(previewSize: Camera.Size, newWidth: Int): Int {
            return Math.round(previewSize.height.toDouble() / previewSize.width * newWidth).toInt()
        }
    }

}
