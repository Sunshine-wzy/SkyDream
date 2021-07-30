package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SRandomItem
import io.github.sunshinewzy.sunstcore.objects.SRandomItems
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Hook(
    item: ItemStack,
    val destroyBlocks: List<Material> = SMaterial.LEAVES.types,
    val extraDropItems: SRandomItems = SRandomItems(
        SRandomItem(5, SDItem.SILKWORM),
        SRandomItem(25, SDItem.MULBERRY_LEAVES),
        SRandomItem(50, SItem(Material.OAK_SAPLING))
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