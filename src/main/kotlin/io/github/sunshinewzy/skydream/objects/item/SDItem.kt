package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.sunstcore.SunSTCore
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SShapedRecipe
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe


enum class SDItem(val item: ItemStack) {
    HOOK_STICK(
        SItem(
            Material.STICK,
            "§f钩子",
            "§a用钩子打树叶有几率掉蚕和桑叶",
            "§b还能增加橡树树苗的掉落几率"
        ),
        "HOOK_STICK",
        mapOf('x' to Material.STICK),
        "xx", " x", " x"
    ),
    
    ;
    
    
    constructor(item: SItem, recipe: Recipe) : this(item) {
        item.addRecipe(SkyDream.getPlugin(), recipe)
    }
    
    constructor(
        item: SItem,
        key: String,
        ingredient: Map<Char, Material>,
        line1: String = "",
        line2: String = "",
        line3: String = ""
    ) : this(item) {
        item.addRecipe(
            SkyDream.getPlugin(),
            SShapedRecipe(
            NamespacedKey(SkyDream.getPlugin(), key),
            item,
            ingredient,
            line1, line2, line3
        ))
    }
}