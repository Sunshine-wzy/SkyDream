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
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.SExtensionKt
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
        val loc = SExtensionKt.addClone(event.loc, 0, 1, 0)
        val upLoc = SExtensionKt.addClone(loc, 0, 1, 0)
        val block = loc.block
        val sBlock = SBlock.getSBlock(loc)
        val player = event.player
        
        val meta = SExtensionKt.getSMetadata(event.loc.block, SkyDream.plugin, name)
        val value = meta.value()
        var pair = if(value is Pair<*, *>) {
            val (first, second) = value
            if(first is String && second is Int){
                first to second
            } else "" to 0
        } else "" to 0
        
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
                            SExtensionKt.giveItem(player, SItem(Material.BONE_MEAL))
                            player.world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
                            player.world.playEffect(upLoc, Effect.VILLAGER_PLANT_GROW, 3)
                        }
                    }
                    
                    "FEATHER" -> {
                        if(cnt > 1) {
                            cnt--
                            player.playSound(loc, Sound.BLOCK_GRASS_BREAK, 1f, 2f)
                        } else {
                            cnt = 0
                            SExtensionKt.giveItem(player, SItem(Material.FEATHER))
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
                        SExtensionKt.sendMsg(player, name, "§4您副手上的树苗不足8棵！")
                    }
                }
                
                // 8桑叶 -> 1骨粉
                else if(SItem.isItemSimilar(offHandItem, SDItem.MULBERRY_LEAVES.item)){
                    if(offHandItem.amount >= 8){
                        offHandItem.amount -= 8
                        pair = "BONE_MEAL" to 8
                        player.playSound(loc, Sound.BLOCK_GRASS_PLACE, 1f, 2f)
                    }
                    else{
                        player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                        SExtensionKt.sendMsg(player, name, "§4您副手上的桑叶不足8片！")
                    }
                }
                
                // 4线 -> 1羽毛
                else if(offHandItem.type == Material.STRING) {
                    if(offHandItem.amount >= 4) {
                        offHandItem.amount -= 4
                        pair = "FEATHER" to 4
                        player.playSound(loc, Sound.BLOCK_GRASS_PLACE, 1f, 2f)
                    } else {
                        player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                        SExtensionKt.sendMsg(player, name, "§4您副手上的线不足4根！")
                    }
                }
                
                else{
                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                    SExtensionKt.sendMsg(player, name, "§4您副手上的物品无法被塞进木桶！")
                }
            }
            
            else {
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
                SExtensionKt.sendMsg(player, name , "§4您的副手上没有东西！")
            }
        }
        
        else {
            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
            SExtensionKt.sendMsg(player, name, "§4机器顶部中间被方块阻塞了！")
        }
        
        meta.data = pair
        event.loc.block.setMetadata(name, meta)
    }
}