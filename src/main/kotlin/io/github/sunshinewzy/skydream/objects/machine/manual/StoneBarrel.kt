package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.*
import org.bukkit.Material
import org.bukkit.Sound

object StoneBarrel : SMachineManual(
    "StoneBarrel", "石桶",
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
            'a' to SBlock(Material.SMOOTH_STONE_SLAB).setItem(SItem(Material.SMOOTH_STONE_SLAB, listOf("§a我是上半砖~"))),
            'b' to SBlock(Material.COBBLESTONE_WALL),
            'c' to SBlock(Material.STONE_BRICKS)
        ),
        SCoordinate(0, 1, 0)
    )
) {
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = SExtensionKt.addClone(event.loc, 0, 1, 0)
        val block = loc.block
        val player = event.player
        val inv = player.inventory
        val offHandItem = inv.itemInOffHand

        val meta = SExtensionKt.getSMetadata(event.loc.block, SkyDream.plugin, name)
        val value = meta.value()
        var pair = if(value is Pair<*, *>) {
            val (first, second) = value
            if(first is String && second is Int){
                first to second
            } else "" to 0
        } else "" to 0
        var cnt = pair.second
        
        when(block.type) {
            // 岩浆
            Material.LAVA -> {
                if(cnt > 0) {
                    when(pair.first) {
                        "NETHERRACK" -> {
                            if(cnt > 1){
                                cnt--
                                player.playSound(loc, Sound.BLOCK_LAVA_POP, 1f, 2f)
                            }
                            else{
                                cnt = 0
                                block.type = Material.NETHERRACK
                                player.world.playSound(loc, Sound.ITEM_BUCKET_EMPTY_LAVA, 1f, 2f)
                            }
                        }

                        "END_STONE" -> {
                            if(cnt > 1){
                                cnt--
                                player.playSound(loc, Sound.BLOCK_LAVA_POP, 1f, 2f)
                            }
                            else{
                                cnt = 0
                                block.type = Material.END_STONE
                                player.world.playSound(loc, Sound.ITEM_BUCKET_EMPTY_LAVA, 1f, 2f)
                            }
                        }

                        "CRIMSON_NYLIUM" -> {
                            if(cnt > 1){
                                cnt--
                                player.playSound(loc, Sound.BLOCK_LAVA_POP, 1f, 2f)
                            }
                            else{
                                cnt = 0
                                try {
                                    block.type = Material.CRIMSON_NYLIUM
                                } catch (_: Exception) {}
                                player.world.playSound(loc, Sound.ITEM_BUCKET_EMPTY_LAVA, 1f, 2f)
                            }
                        }

                        "WARPED_NYLIUM" -> {
                            if(cnt > 1){
                                cnt--
                                player.playSound(loc, Sound.BLOCK_LAVA_POP, 1f, 2f)
                            }
                            else{
                                cnt = 0
                                try {
                                    block.type = Material.WARPED_NYLIUM
                                } catch (_: Exception) {}
                                player.world.playSound(loc, Sound.ITEM_BUCKET_EMPTY_LAVA, 1f, 2f)
                            }
                        }

                        else -> cnt = 0
                    }

                    meta.data = pair.first to cnt
                    event.loc.block.setMetadata(name, meta)
                    return
                }

                when (offHandItem.type) {
                    // + 1红石 -> 1地狱岩
                    Material.REDSTONE -> {
                        offHandItem.amount -= 1
                        pair = "NETHERRACK" to 8
                        player.playSound(loc, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 2f)
                    }

                    // + 1荧石粉 -> 1末地石
                    Material.GLOWSTONE_DUST -> {
                        offHandItem.amount -= 1
                        pair = "END_STONE" to 8
                        player.playSound(loc, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 2f)
                    }

                    else -> {
                        try {
                            when(offHandItem.type) {
                                // + 1绯红菌 -> 1绯红菌岩
                                Material.CRIMSON_FUNGUS -> {
                                    offHandItem.amount -= 1
                                    pair = "CRIMSON_NYLIUM" to 8
                                    player.playSound(loc, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 2f)
                                }

                                // + 1诡异菌 -> 1诡异菌岩
                                Material.WARPED_FUNGUS -> {
                                    offHandItem.amount -= 1
                                    pair = "WARPED_NYLIUM" to 8
                                    player.playSound(loc, Sound.ITEM_BUCKET_FILL_LAVA, 1f, 2f)
                                }
                                
                                else -> {
                                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                                    SExtensionKt.sendMsg(player, name, "§4您副手上的物品无法被塞进装有岩浆的石桶！")
                                }
                            }
                        } catch (_: Exception) {
                            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                            SExtensionKt.sendMsg(player, name, "§4您副手上的物品无法被塞进装有岩浆的石桶！")
                        }
                    }
                }

            }
            
            Material.WATER -> {
                SExtensionKt.getChest(SExtensionKt.subtractClone(event.loc, 2).block)?.let { chest ->
                    val chestInv = chest.inventory
                    if(SExtensionKt.removeSItem(chestInv, Material.LAVA_BUCKET, 1)) {
                        player.playSound(loc, Sound.BLOCK_LAVA_EXTINGUISH, 1f, 1f)
                        loc.world?.apply {
                            dropItemNaturally(loc, SItem(Material.OBSIDIAN))
                            dropItemNaturally(loc, SItem(Material.BUCKET))
                        }
                        return
                    }
                }
                
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
            }

            Material.AIR -> {
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                SExtensionKt.sendMsg(player, name, "§4您副手上的物品无法被塞进空的石桶！")
            }

            else -> {
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 0.5f)
                SExtensionKt.sendMsg(player, name, "§4石桶顶部中间被方块阻塞了！")
            }
        }
        
        
        meta.data = pair
        event.loc.block.setMetadata(name, meta)
    }
    
}