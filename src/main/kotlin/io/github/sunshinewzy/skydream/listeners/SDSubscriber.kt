package io.github.sunshinewzy.skydream.listeners

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.Hook
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.manual.Composter
import io.github.sunshinewzy.sunstcore.enums.SMaterial.Companion.getSapling
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.utils.subscribeEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockFertilizeEvent

object SDSubscriber : Initable {

    override fun init() {
        subscribeEvent<BlockBreakEvent> { 
            if(isCancelled) return@subscribeEvent
            val item = player.inventory.itemInMainHand
            val block = block
            
            Hook.hooks.forEach { hook ->
                if(item.isItemSimilar(hook)){
                    if(hook.destroyBlocks.contains(block.type)){
                        
                        val dropItem = hook.extraDropItems.takeOneFirst()
                        if(dropItem.type == Material.OAK_SAPLING) {
                            block.world.dropItem(block.location, SItem(block.type.getSapling()))
                        } else if(dropItem.type != Material.AIR) {
                            block.world.dropItem(block.location, dropItem)
                        }
                        
                    }
                    
                    return@forEach
                }
            }
        }
        
        subscribeEvent<BlockFertilizeEvent> { 
            val player = player ?: return@subscribeEvent
            val handItem = player.inventory.itemInMainHand
            
            if(handItem.isItemSimilar(SDItem.ENRICHED_BONE_MEAL)) {
                handItem.amount--
                blocks.forEach {
                    if(it.type == Material.GRASS) {
                        Bukkit.getScheduler().runTask(SkyDream.plugin, Runnable {
                            it.block.type = Composter.flowers.random()
                        })
                    } else if(it.type == Material.POPPY || it.type == Material.DANDELION) {
                        Bukkit.getScheduler().runTask(SkyDream.plugin, Runnable {
                            it.block.type = Composter.tallFlowers.random()
                        })
                    }
                }
            }
        }
    }
    
}