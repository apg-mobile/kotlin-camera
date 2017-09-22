package com.apg.camera.widget

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.apg.camera.widget.exception.CameraPreviewException

/**
 * Created by alphaadmin on 27/9/2559.
 */

class CameraPreview : SurfaceView, SurfaceHolder.Callback, Camera.AutoFocusCallback {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    private var camera: Camera? = null
        get() {
            if (field == null) field = CameraManager.getCameraInstance()
            return field
        }

    var previewCallback: (ByteArray, Camera) -> Unit = { _, _ -> }

    init {
        holder.addCallback(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent !is FrameLayout) throw CameraPreviewException("Parent should be FrameLayout!")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        camera?.stopPreview()
        val cameraParams = camera?.parameters

        CameraManager.getBestCameraSize(camera?.parameters?.supportedPreviewSizes)?.let {
            cameraParams?.setPreviewSize(it.width, it.height)
            val surfaceSize = CameraManager.getBestSurfaceSize(context, it)
            layoutParams = FrameLayout.LayoutParams(surfaceSize.height, surfaceSize.width)
        }

        val focusMode = cameraParams?.supportedFocusModes ?: mutableListOf()
        when {
            focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) -> {
                cameraParams?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
            }
            focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO) -> {
                cameraParams?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            }
            focusMode.contains(Camera.Parameters.FOCUS_MODE_AUTO) -> {
                cameraParams?.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
            }
        }

        camera?.parameters = cameraParams
        camera?.setDisplayOrientation(CameraManager.getCameraOrientation(context).degree)
        camera?.setPreviewDisplay(holder)
        camera?.startPreview()
        camera?.setPreviewCallback(previewCallback)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera?.setPreviewDisplay(holder)
        camera?.setDisplayOrientation(Orientation.PORTRAIT.degree)
    }

    override fun onAutoFocus(success: Boolean, camera: Camera?) {
    }

    fun onPause() {
        camera?.release()
        camera = null
    }

}
