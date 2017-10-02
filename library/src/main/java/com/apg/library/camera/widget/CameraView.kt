package com.apg.library.camera.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * Created by siwarats on 2/10/2560.
 */
class CameraView : FrameLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        addView(CameraPreview(context))
    }

    fun onPause() {
        val child = getChildAt(0)
        if (child is CameraPreview) {
            child.onPause()
        }
    }

    fun onGrantedPermission() {
        val child = getChildAt(0)
        if(child is View) {
            removeViewAt(0)
            addView(child)
        }
    }
}