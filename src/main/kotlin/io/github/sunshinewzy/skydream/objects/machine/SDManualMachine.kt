package io.github.sunshinewzy.skydream.objects.machine

import io.github.sunshinewzy.skydream.objects.machine.manual.*
import io.github.sunshinewzy.sunstcore.interfaces.Initable

object SDManualMachine : Initable {
    override fun init() {
        Sieve.init()
        WoodenBarrel.init()
        Squeezer.init()
        Millstone.init()
        ClayMaker.init()
        Crucible.init()
        
    }
}