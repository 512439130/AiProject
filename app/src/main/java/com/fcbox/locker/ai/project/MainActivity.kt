package com.fcbox.locker.ai.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 沉浸式状态栏配置
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)
        
        // 注意：原有的登录状态检查逻辑已移至 SplashFragment 处理，
        // 这里只需负责加载容器和基础 Activity 配置。
    }
}
