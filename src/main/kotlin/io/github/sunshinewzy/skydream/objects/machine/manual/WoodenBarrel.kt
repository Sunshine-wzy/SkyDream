package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.utils.*
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Sound

object WoodenBarrel : SMachineManual(
    "WoodenBarrel",
    "木桶",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            ab
            
            b
            cb
            
             
            cb
        """.trimIndent(),
        mapOf(
            'a' to SBlock(SMaterial.SLAB_WOOD).setItem(SItem(Material.OAK_SLAB, listOf("§a我是上半砖~"))),
            'b' to SBlock(SMaterial.FENCE_WOOD),
            'c' to SBlock(SMaterial.PLANKS)
        ),
        SCoordinate(0, 1, 0)
    )
) {
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc.addClone(0, 1, 0)
        val upLoc = loc.addClone(0, 1, 0)
        val block = loc.block
        val sBlock = loc.getSBlock()
        val player = event.player
        
        val meta = event.loc.block.getSMetadata(SkyDream.plugin, name)
        var pair = meta.asPair("" to 0)
        
        if(block.type == Material.AIR){
            var cnt = pair.second
            
            if(cnt > 0){
                when(pair.first) {
                    "SAPLING" -> {
                        if(cnt > 1){
                            cnt--
                            player.playSound(loc, Sound.BLOCK_GRASS_BREAK, 1f, 2f)
                        }
                        else{
                            cnt = 0
                            loc.block.type = Material.DIRT
                            player.world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
                            player.world.playEffect(upLoc, Effect.VILLAGER_PLANT_GROW, 3)
                        }
                    }
                    
                    "BONE_MEAL" -> {
                        if(cnt > 1){
                            cnt--
                            player.playSound(loc, Sound.BLOCK_GRASS_BREAK, 1f, 2f)
                        }
                        else{
                            cnt = 0
                            player.giveItem(SItem(Material.BONE_MEAL))
                            player.world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
                            player.world.playEffect(upLoc, Effect.VILLAGER_PLANT_GROW, 3)
                        }
                    }
                    
                    else -> cnt = 0
                }

                meta.data = pair.first to cnt
                event.loc.block.setMetadata(name, meta)
                return
            }
            
            val inv = player.inventory
            val offHandItem = inv.itemInOffHand
            
            if(offHandItem.type != Material.AIR){
                // 8树苗 -> 1泥土
                if(offHandItem.type in SMaterial.SAPLING){
                    if(offHandItem.amount >= 8){
                        offHandItem.amount -= 8
                        pair = "SAPLING" to 8
                        player.playSound(loc, Sound.BLOCK_GRASS_PLACE, 1f, 2f)
                    }
                    else{
                        player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                        player.sendMsg(name, "§4您副手上的树苗不足8棵！")
                    }
                }
                
                // 8桑叶 -> 1骨粉
                else if(offHandItem.isItemSimilar(SDItem.MULBERRY_LEAVES)){
                    if(offHandItem.amount >= 8){
                        offHandItem.amount -= 8
                        pair = "BONE_MEAL" to 8
                        player.playSound(loc, Sound.BLOCK_GRASS_PLACE, 1f, 2f)
                    }
                    else{
                        player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                        player.sendMsg(name, "§4您副手上的桑叶不足8片！")
                    }
                }
                
                else{
                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                    player.sendMsg(name, "§4您副手上的物品无法被塞进木桶！")
                }
            }
            else{
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
                player.sendMsg(name , "§4您的副手上没有东西！")
            }
        }
        else{
            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
            player.sendMsg(name, "§4机器顶部中间被方块阻塞了！")
        }
        
        meta.data = pair
        event.loc.block.setMetadata(name, meta)
    }
}