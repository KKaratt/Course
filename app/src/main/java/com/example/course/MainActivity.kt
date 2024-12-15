package com.example.course

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle


class MainActivity : Activity() {
    private var gLView: GLSurfaceView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = GLSurfaceView(this)
        gLView!!.setRenderer(MyGLRenderer(this))
        gLView!!.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        setContentView(gLView)
    }

    override fun onPause() {
        super.onPause()
        gLView!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        gLView!!.onResume()
    }
}
