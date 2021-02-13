package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.skydream.SkyDream.Companion.getPlugin
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.interfaces.Itemable
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.addRecipe
import io.github.sunshinewzy.sunstcore.objects.SShapedRecipe
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapelessRecipe


enum class SDItem(val item: ItemStack) : Itemable {
    WRENCH(
        SDMachine.wrench,
        "WRENCH",
        mapOf('x' to WOOD, 'y' to LOG, 'z' to BONE, 'm' to STICK),
        "x x","yzy"," m "
    ),
    HOOK_STICK(
        SItem(STICK, "§f钩子", "§a用钩子打树叶有几率掉蚕和桑叶", "§b还能增加橡树树苗的掉落几率"),
        "HOOK_STICK",
        mapOf('x' to STICK),
        "xx", " x", " x"
    ),
    HOOK_WOOD(
        SItem(WOOD_SWORD, "§f木钩", "§a比钩子更快", "§b但易损坏").apply {
            itemMeta = itemMeta.apply {
                itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
            }
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
        },
        "HOOK_WOOD",
        mapOf('x' to WOOD, 'y' to STICK),
        "yx"," x"," y"
    ),
    HOOK_STONE(
        SItem(STONE_SWORD, "§f石钩", "§a木钩的升级版", "§b增加了耐久").apply {
            itemMeta = itemMeta.apply {
                itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
            }
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
            addEnchantment(Enchantment.DURABILITY, 1)
        },
        "HOOK_STONE",
        mapOf('x' to COBBLESTONE, 'y' to STICK),
        "yx"," x"," y"
    ),
    PEBBLE(
        SItem(STONE_BUTTON, "§7石子", "§f一颗普通的石子", "§a4个石子可以合成1个圆石哦").apply { 
            addRecipe(
                getPlugin(),
                ShapelessRecipe(NamespacedKey(getPlugin(), "PEBBLE"), SItem(COBBLESTONE)).addIngredient(4, type)
            )
        }
    ),
	SILKWORM(SItem(INK_SACK, 7, 1, "§8蚕", "§a将我拿在副手，用主手喂桑叶给我吃哦~")),
	MULBERRY_LEAVES(SItem(LONG_GRASS, 2, 1, "§a桑叶", "§f拿在主手时可喂食副手的蚕")),
	TIN_POWDER(SItem(INK_SACK, 8, 1, "§7锡粉")),
	COPPER_POWDER(SItem(INK_SACK, 11, 1, "§e铜粉")),
	BRONZE_POWDER(SItem(INK_SACK, 14, 1, "§6青铜粉")),
    
    
    ;
    
    
    constructor(item: ItemStack, recipe: Recipe) : this(item) {
        item.addRecipe(getPlugin(), recipe)
    }
    
    constructor(item: ItemStack, vararg recipes: Recipe) : this(item) {
        recipes.forEach { 
            item.addRecipe(getPlugin(), it)
        }
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
            getPlugin(),
            SShapedRecipe(
                NamespacedKey(getPlugin(), key),
                item,
                ingredient,
                line1,
                line2,
                line3
            )
        )
    }


    override fun getSItem(): ItemStack = item
    
    
    companion object : Initable {
        override fun init() {}
    }
    
}