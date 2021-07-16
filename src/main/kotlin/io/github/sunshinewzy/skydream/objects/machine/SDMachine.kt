package io.github.sunshinewzy.skydream.objects.machine

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineWrench
import io.github.sunshinewzy.sunstcore.objects.SItem
import org.bukkit.Material

object SDMachine : Initable {
    val wrench = SMachineWrench(SkyDream.plugin, SItem(Material.BONE, "§b扳手", "§7一个普通的扳手", "§a敲击中心方块以构建多方块机器"), "SkyDream")
    
    
    override fun init() {
        SDManualMachine.init()
        SDTimerMachine.init()
        
        wrench.init()
    }
    
}