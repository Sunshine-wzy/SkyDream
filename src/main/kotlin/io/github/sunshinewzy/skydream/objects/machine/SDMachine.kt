package io.github.sunshinewzy.skydream.objects.machine

import io.github.sunshinewzy.skydream.objects.machine.manual.SDManualMachine
import io.github.sunshinewzy.skydream.objects.machine.timer.SDTimerMachine
import io.github.sunshinewzy.sunstcore.interfaces.Initable

object SDMachine : Initable {

    override fun init() {
        SDManualMachine.init()
        SDTimerMachine.init()
    }
    
}