package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.sunstcore.objects.SItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Hook(
    item: ItemStack,
    val destroyBlocks: List<Material> = listOf(
        Material.LEAVES, Material.LEAVES_2
    ),
    val extraDropItems: ArrayList<Pair<Int, ItemStack>> = arrayListOf(
        5 to SDItem.SILKWORM.item,
        10 to SDItem.MULBERRY_LEAVES.item,
        20 to SItem(Material.SAPLING)
    )
) : SItem(item) {
    
    init {
        hooks.add(this)
        
    }
    
    
    
    companion object {
        val hooks = ArrayList<Hook>()
        
        val HOOK_STICK = Hook(SDItem.HOOK_STICK.item)
        val HOOK_WOOD = Hook(SDItem.HOOK_WOOD.item)
        val HOOK_STONE = Hook(SDItem.HOOK_STONE.item)
        
        
    }
    
}