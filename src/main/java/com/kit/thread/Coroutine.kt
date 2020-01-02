package com.kit.thread

import com.kit.app.core.task.JustDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Coroutine {
    @JvmStatic
    fun main(justDo: JustDo) {
        GlobalScope.launch(Dispatchers.Main) {
            justDo.doo()
        }
    }

    @JvmStatic
    fun io(justDo: JustDo) {
        GlobalScope.launch(Dispatchers.IO) {
            justDo.doo()
        }
    }

    @JvmStatic
    fun run(justDo: JustDo) {
        GlobalScope.launch(Dispatchers.Default) {
            justDo.doo()
        }
    }

}