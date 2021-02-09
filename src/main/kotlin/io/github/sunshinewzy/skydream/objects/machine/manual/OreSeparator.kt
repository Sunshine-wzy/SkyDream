package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.MachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import org.bukkit.Material

class OreSeparator : MachineManual(MachineStructure.CentralSymmetry(
    """
        a
        
        a
        ba
        
        a
    """.trimIndent(),
    mapOf('a' to SBlock(Material.FENCE), 'b' to SBlock(Material.WOOL))
)) {
    override fun runMachine() {
        
    }
}