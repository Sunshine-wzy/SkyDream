package io.github.sunshinewzy.skydream.listeners

import io.github.sunshinewzy.skydream.objects.item.Hook
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.utils.getSapling
import io.github.sunshinewzy.sunstcore.utils.subscribeEvent
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.material.Leaves
import kotlin.random.Random

object SDSubscriber : Initable {

    override fun init() {
        subscribeEvent<BlockBreakEvent> { 
            if(isCancelled) return@subscribeEvent
            val item = player.inventory.itemInMainHand
            val block = block
            
            Hook.hooks.forEach { hook ->
                if(item.isItemSimilar(hook)){
                    if(hook.destroyBlocks.contains(block.type)){
                        hook.extraDropItems.forEach { 
                            val randInt = Random.nextInt(100) + 1
                            
                            if(randInt in 1..it.first){
                                if(it.second.type == Material.SAPLING){
                                    val meta = block.state.data
                                    if(meta is Leaves){
                                        block.world.dropItem(block.location, meta.getSapling())
                                    }
                                }
                                else block.world.dropItem(block.location, it.second)
                            }
                        }
                        
                    }
                    
                    return@forEach
                }
            }
        }
    }
    
}