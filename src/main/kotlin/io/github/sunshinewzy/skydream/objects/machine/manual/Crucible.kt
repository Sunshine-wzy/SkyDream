package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.countPlaneAround
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Furnace

object Crucible : SMachineManual(
    "Crucible",
    "坩埚",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            bc
            
            c
            ec
            
             
            dc
        """.trimIndent(),
        mapOf(
            'a' to SBlock(Material.BRICKS),
            'b' to SBlock(Material.SMOOTH_STONE_SLAB).setItem(SItem(Material.SMOOTH_STONE_SLAB, listOf("§a我是上半砖~"))),
            'c' to SBlock(Material.COBBLESTONE_WALL),
            'd' to SBlock(Material.WHITE_TERRACOTTA),
            'e' to SBlock(Material.FURNACE).setItem(SItem(Material.FURNACE, listOf("§a让我燃烧起来~")))
        ),
        SCoordinate(0, 1, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc
        val topLoc = loc.addClone(1)
        val player = event.player
        val inv = player.inventory
        
        if(topLoc.block.type == Material.AIR){
            if(inv.itemInMainHand.isItemSimilar(SDItem.CRUCIBLE_TONGS)){
                loc.block.blockData
                val rate = loc.countPlaneAround(Material.FURNACE) {
                    state.apply {
                        if(this is Furnace) {
                            return@countPlaneAround burnTime > 0
                        }
                    }
                    
                    false
                }
                player.sendMsg(name, "当前§b坩埚§f运行速率: §a${rate}x")
                
                val offHandItem = inv.itemInOffHand
                if(offHandItem.type != Material.COBBLESTONE) return
                
                if(rate == 0){
                    player.sendMsg(name, "§4坩埚没有热源 §e请让熔炉燃烧起来！")
                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.4f)
                }
                else if(rate in 1..4){
                    if(addMetaCnt(event, 16, rate).flag){
                        offHandItem.amount--
                        player.playSound(loc, Sound.BLOCK_LAVA_POP, 1f, 1.4f)
                    }
                    else{
                        topLoc.block.type = Material.LAVA
                        player.world.playSound(loc, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 1.4f)
                    }
                }
            }
            else{
                player.sendMsg(name, "&c危险！操作坩埚必须使用坩埚钳！")
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
            }
        }
        else{
            player.sendMsg(name, "&4坩埚顶部中间被方块阻塞了！")
            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
        }
    }
    
}