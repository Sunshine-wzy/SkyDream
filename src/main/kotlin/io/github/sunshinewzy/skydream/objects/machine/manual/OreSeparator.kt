package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.getSMetadata
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
import kotlin.random.Random

object OreSeparator : MachineManual(
    "矿石分离机",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
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
        val world = theLoc.world
        
        val meta = centerBlock.getSMetadata(SkyDream.getPlugin(), name)
        var cnt = meta.asInt()
        
        when {
            sBlock.isSimilar(Material.DIRT) -> {
                if(cnt >= 4){
                    cnt = 0
                    
                    block.type = Material.AIR
                    val pebble = SDItem.PEBBLE.item.clone()
                    pebble.amount = Random.nextInt(3) + 2
                    world.dropItem(theLoc, pebble)
                    
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                    player.sendMsg(name, "§a分离成功！")
                }
                else{
                    cnt++

                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                    player.sendMsg(name, "§e正在分离...")
                }
            }
            
            else -> {
                player.playSound(theLoc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                player.sendMsg(name, "§4待分离的方块不正确！")
                return
            }
        }
        
        meta.data = cnt
        centerBlock.setMetadata(name, meta)
    }
    
    
}