package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.utils.getSMetadata
import io.github.sunshinewzy.sunstcore.utils.removeClone
import io.github.sunshinewzy.sunstcore.utils.removeSItem
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound

object Squeezer : MachineManual(
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
        mapOf('a' to SBlock(Material.COBBLE_WALL), 'b' to SBlock(Material.STEP)),
        Triple(0, 2, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual) {
        val loc = event.loc.removeClone(2)
        val centerBlock = event.loc.block
        val player = event.player
        val inv = player.inventory
        
        val meta = centerBlock.getSMetadata(SkyDream.getPlugin(), name)
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
        else if(inv.contains(Material.SAPLING, 8)){
            if(loc.block.type == Material.AIR){
                if(inv.removeSItem(Material.SAPLING, 8)){
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