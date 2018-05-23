package com.fpliu.newton.ui.dialog.sample

import android.os.Bundle
import com.fpliu.newton.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 自定义弹出框使用示例
 * @author 792793182@qq.com 2018-03-28.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "自定义弹出框使用示例"
        setContentView(R.layout.activity_main)
        click(xx).subscribe {

        }
    }
}