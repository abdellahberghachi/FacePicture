package com.example.abdellahberghachi.facephoto.utils

import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {


    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        initWi()
        initUi()
        initEvents()
    }

    protected abstract fun initEvents()

    protected abstract fun initUi()

    protected abstract fun initWi()


}
