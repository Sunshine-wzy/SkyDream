package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.addRecipe
import io.github.sunshinewzy.sunstcore.objects.SShapedRecipe
import org.bukkit.Material as M
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe


enum class SDItem(val item: ItemStack) {
    HOOK_STICK(
        SItem(M.STICK, "§f钩子", "§a用钩子打树叶有几率掉蚕和桑叶", "§b还能增加橡树树苗的掉落几率"),
        "HOOK_STICK",
        mapOf('x' to M.STICK),
        "xx", " x", " x"
    ),
    HOOK_WOOD(
        SItem(M.WOOD_SWORD, "§f木钩", "§a比钩子更快", "§b但易损坏").apply {
            itemMeta = itemMeta.apply {
                itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
            }
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
        },
        "HOOK_WOOD",
        mapOf('x' to M.WOOD, 'y' to M.STICK),
        "yx"," x"," y"
    ),
    HOOK_STONE(
        SItem(M.STONE_SWORD, "§f石钩", "§a木钩的升级版", "§b增加了耐久").apply {
            itemMeta = itemMeta.apply {
                itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
            }
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
            addEnchantment(Enchantment.DURABILITY, 1)
        },
        "HOOK_STONE",
        mapOf('x' to M.COBBLESTONE, 'y' to M.STICK),
        "yx"," x"," y"
    ),
    WRENCH(
        SItem(M.BONE, "§b扳手", "§7一个普通的扳手", "§a敲击中心方块以构建多方块机器"),
        "WRENCH",
        mapOf('x' to M.WOOD, 'y' to M.LOG, 'z' to M.BONE, 'm' to M.STICK),
        "x x","yzy"," m "
    )
    
    ;
    
    
    constructor(item: ItemStack, recipe: Recipe) : this(item) {
        item.addRecipe(SkyDream.getPlugin(), recipe)
    }
    
    constructor(item: ItemStack, vararg recipes: Recipe) : this(item) {
        recipes.forEach { 
            item.addRecipe(SkyDream.getPlugin(), it)
        }
    }
    
    
    constructor(
        item: SItem,
        key: String,
        ingredient: Map<Char, M>,
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
            )
        )
    }
    
}