package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.*
import io.github.sunshinewzy.sunstcore.objects.inventoryholder.SPartProtectInventoryHolder
import org.bukkit.Material

object ForgingTable : SMachineManual(
    "ForgingTable",
    "锻造台",
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
            }, id
    )
    
    private val menu = SMenu("ForgingTable", "锻造台", 6).apply {
        holder = this@ForgingTable.holder
        createEdge(SItem(Material.WHITE_STAINED_GLASS_PANE))
    }

    
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        
    }
    
}