package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.addClone
import org.bukkit.Location
import org.bukkit.Material

object ClayMaker : MachineManual(
    "粘土制造机",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            b
            
            e
            c
            
            d
            c
        """.trimIndent(),
        mapOf(
            'a' to SBlock(Material.COBBLE_WALL),
            'b' to SBlock(Material.STEP),
            'c' to SBlock(Material.THIN_GLASS),
            'd' to SBlock(Material.AIR).setDisplayItem(SItem(Material.WATER_BUCKET, "§f水", "§a在这里放一桶水","§d我知道你没水桶","§b把压榨机建在粘土制造机的上面","§b然后直接压榨出水...")),
            'e' to SBlock(Material.AIR).setDisplayItem(SItem(Material.COBBLE_WALL))
        ),
        Triple(0, 0, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual) {
        
    }

    override fun specialJudge(loc: Location, isFirst: Boolean): Boolean {
        val topBlock = loc.addClone(2).block
        if(topBlock.type != Material.WATER && topBlock.type != Material.STATIONARY_WATER)
            return false
        
        if(isFirst){
            val upLoc = loc.addClone(1)
            if(upLoc.block.type == Material.COBBLE_WALL){
                upLoc.block.type = Material.AIR
                return true
            }
            return false
        }

        return true
    }
    
}