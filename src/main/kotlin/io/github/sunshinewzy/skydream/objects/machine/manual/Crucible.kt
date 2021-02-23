package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.countPlaneAround
import io.github.sunshinewzy.sunstcore.utils.judgePlaneAround
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound

object Crucible : MachineManual(
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
            'a' to SBlock(Material.BRICK),
            'b' to SBlock(Material.STEP, 8).setDisplayItem(SItem(Material.STEP, listOf("§a我是上半砖~"))),
            'c' to SBlock(Material.COBBLE_WALL),
            'd' to SBlock(Material.STAINED_CLAY),
            'e' to SBlock(Material.AIR).setDisplayItem(SItem(Material.FURNACE, listOf("§a让我燃烧起来~")))
        ),
        Triple(0, 1, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual) {
        val loc = event.loc
        val topLoc = loc.addClone(1)
        val player = event.player
        val inv = player.inventory
        
        if(topLoc.block.type == Material.AIR){
            if(inv.itemInMainHand.isItemSimilar(SDItem.CRUCIBLE_TONGS)){
                val level = loc.countPlaneAround(Material.BURNING_FURNACE)
                player.sendMsg(name, "当前§b坩埚§f运行速率: §a${level}x")
                
                val offHandItem = inv.itemInOffHand
                if(offHandItem.type != Material.COBBLESTONE) return
                
                if(level == 0){
                    player.sendMsg(name, "§4坩埚没有热源 §e请让熔炉燃烧起来！")
                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.4f)
                }
                else if(level in 1..4){
                    if(addMetaCnt(event, 16, level).flag){
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

    override fun specialJudge(loc: Location, isFirst: Boolean): Boolean {
        val centerLoc = loc.addClone(1)
        
        if(centerLoc.judgePlaneAround(listOf(Material.FURNACE, Material.BURNING_FURNACE))){
            return true
        }
        
        return false
    }
}