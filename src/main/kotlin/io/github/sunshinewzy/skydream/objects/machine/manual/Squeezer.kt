package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.utils.*
import org.bukkit.Material
import org.bukkit.Sound

object Squeezer : SMachineManual(
    "Squeezer",
    "压榨机",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
             
            a
            
            a
            a
            
            a
            b
        """.trimIndent(),
        mapOf('a' to SBlock(Material.COBBLESTONE_WALL), 'b' to SBlock(Material.SMOOTH_STONE_SLAB)),
        SCoordinate(0, 2, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc.subtractClone(2)
        val centerBlock = event.loc.block
        val player = event.player
        val inv = player.inventory
        
        val meta = centerBlock.getSMetadata(SkyDream.plugin, name)
        var cnt = meta.asInt()
        
        if(cnt >= 1){
            if(cnt >= 8){
                cnt = 0
                loc.block.type = Material.WATER
                player.playSound(loc, Sound.BLOCK_WATER_AMBIENT, 1f, 2f)
            }
            else{
                cnt++
                player.playSound(loc, Sound.BLOCK_GRASS_BREAK, 1f, 2f)
            }
        }
        else if(inv.containsItem(SMaterial.SAPLING, 8)){
            if(loc.block.type == Material.AIR){
                if(inv.removeSItem(SMaterial.SAPLING, 8)){
                    cnt = 1
                    player.playSound(loc, Sound.BLOCK_GRASS_PLACE, 1f, 2f)
                }
            }
            else{
                player.sendMsg(name, "§4机器底部中间被方块阻塞了！")
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
            }
        }
        else{
            player.sendMsg(name, "§4你的背包中没有8棵树苗！")
            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
        }

        meta.data = cnt
        centerBlock.setMetadata(name, meta)
    }
    
}