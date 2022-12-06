package io.github.sunshinewzy.skydream.listeners

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.Hook
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.manual.Composter
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.objects.SItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockFertilizeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object SDSubscriber : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if(event.hand == EquipmentSlot.OFF_HAND) {
            if(SItem.isItemSimilar(event.player.inventory.itemInMainHand, SDItem.CRUCIBLE_TONGS.item))
                event.isCancelled = true
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockBreak(event: BlockBreakEvent) {
        if(event.isCancelled) return
        val item = event.player.inventory.itemInMainHand
        val block = event.block

        Hook.hooks.forEach { hook ->
            if(SItem.isItemSimilar(item, hook)){
                if(hook.destroyBlocks.contains(block.type)){

                    val dropItem = hook.extraDropItems.takeOneFirst()
                    if(dropItem.type == Material.OAK_SAPLING) {
                        block.world.dropItem(block.location, SItem(SMaterial.getSapling(block.type)))
                    } else if(dropItem.type != Material.AIR) {
                        block.world.dropItem(block.location, dropItem)
                    }

                }

                return@forEach
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockFertilize(event: BlockFertilizeEvent) {
        val player = event.player ?: return
        val handItem = player.inventory.itemInMainHand

        if(SItem.isItemSimilar(handItem, SDItem.ENRICHED_BONE_MEAL.item)) {
            handItem.amount--
            event.blocks.forEach {
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