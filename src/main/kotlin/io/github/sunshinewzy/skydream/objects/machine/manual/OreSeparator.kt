package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.sunstcore.SunSTCore
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.MachineStructure
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.objects.SMetadataValue
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.giveItem
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
import kotlin.random.Random

class OreSeparator : MachineManual(
    "矿石分离机",
    SkyDream.wrench,
    MachineStructure.CentralSymmetry(
        """
        a
        
        a
        ba
        
        a
        """.trimIndent(),
        mapOf('a' to SBlock(Material.FENCE), 'b' to SBlock(Material.WOOL), 'c' to SBlock(Material.WOOD)),
        Triple(0, 0, 0)
    )
) {
    override fun manualRun(event: SMachineRunEvent.Manual) {
        val theLoc = event.loc.addClone(0, -1, 0)
        val block = theLoc.block
        val centerBlock = event.loc.block
        val sBlock = theLoc.getSBlock()
        val player = event.player
        
        var meta = SMetadataValue(SunSTCore.getPlugin(), 0)
        if(centerBlock.hasMetadata(name)){
            centerBlock.getMetadata(name).forEach {
                if(it is SMetadataValue)
                    meta = it
            }
        }
        var cnt = meta.asInt()
        

        when {
            sBlock.isSimilar(Material.DIRT) -> {
                if(cnt >= 4){
                    cnt = 0
                    
                    block.type = Material.AIR
                    player.giveItem(SDItem.PEBBLE, Random.nextInt(3) + 2)
                }
                else{
                    cnt++

                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                    player.sendMsg("§e正在分离...")
                }
            }
        
            
        
        }
        
        meta.data = cnt
    }
    
    
}