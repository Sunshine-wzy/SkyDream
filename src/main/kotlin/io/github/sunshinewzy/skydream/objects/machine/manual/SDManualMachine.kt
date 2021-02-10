package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.sunstcore.interfaces.Initable

object SDManualMachine : Initable {
    override fun init() {
        OreSeparator()
    }
}