package com.kit.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class LifecycleKotlinCoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) = installCoroutineScope(savedInstanceState) {
        super.onCreate(savedInstanceState)
    }

}

