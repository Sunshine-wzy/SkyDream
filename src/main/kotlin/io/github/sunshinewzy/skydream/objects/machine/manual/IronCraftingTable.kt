package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.inventoryholder.SInventoryHolder
import org.bukkit.Bukkit
import org.bukkit.Material

object IronCraftingTable : MachineManual(
    "铁制工作台",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            ab
            
             
            ca
            
            d
            ab
        """.trimIndent(),
        mapOf(
            'a' to SBlock(Material.IRON_BLOCK),
            'b' to SBlock(Material.IRON_FENCE),
            'c' to SBlock(Material.GOLD_BLOCK),
            'd' to SBlock(Material.WORKBENCH)
        ),
        Triple(0, 2, 0)
    )
) {
    private val holder = SInventoryHolder(name)

    
    override fun manualRun(event: SMachineRunEvent.Manual) {
        val inv = Bukkit.createInventory(holder, 5 * 9, "铁制工作台")
        
        
    }
    
}