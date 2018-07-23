package com.fpliu.newton.ui.dialog.sample

import android.app.Activity
import android.os.Bundle
import com.fpliu.newton.log.Logger
import com.fpliu.newton.ui.dialog.CustomDialog

class MyDialog(activity: Activity) : CustomDialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDismissed() {
        super.onDismissed()
        Logger.i("XX", "onDismissed()")
    }
}