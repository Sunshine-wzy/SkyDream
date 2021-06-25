package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.inventoryholder.SPartProtectInventoryHolder
import io.github.sunshinewzy.sunstcore.objects.orderWith
import io.github.sunshinewzy.sunstcore.utils.createEdge
import org.bukkit.Bukkit
import org.bukkit.Material

object IronCraftingTable : SMachineManual(
    "IronCraftingTable",
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
            'b' to SBlock(Material.IRON_BARS),
            'c' to SBlock(Material.GOLD_BLOCK),
            'd' to SBlock(Material.CRAFTING_TABLE)
        ),
        SCoordinate(0, 2, 0)
    )
) {
    private val holder = SPartProtectInventoryHolder(
            ArrayList<Int>().apply {
                for (i in 2..4) {
                    for (j in 2..4) {
                        add(i orderWith j)
                    }
                }
                add(7 orderWith 3)
            },
            name
    )

    
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val inv = Bukkit.createInventory(holder, 5 * 9, "铁制工作台")
        inv.createEdge(5, SItem(Material.WHITE_STAINED_GLASS_PANE))
        
        
    }
    
}