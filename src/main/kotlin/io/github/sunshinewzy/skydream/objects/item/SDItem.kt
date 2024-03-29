package io.github.sunshinewzy.skydream.objects.item

import io.github.sunshinewzy.skydream.SkyDream.Companion.plugin
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.interfaces.Itemable
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SRecipeChoice
import io.github.sunshinewzy.sunstcore.objects.SShapedRecipe
import io.github.sunshinewzy.sunstcore.utils.SExtensionKt
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.*
import kotlin.random.Random


enum class SDItem(item: ItemStack) : Itemable {
    WRENCH(
        SDMachine.wrench.also { 
            SItem.addShapedRecipeByChoice(
                it, plugin,
                "WRENCH",
                mapOf('x' to SRecipeChoice(SMaterial.PLANKS), 'y' to SRecipeChoice(SMaterial.LOG), 'z' to SRecipeChoice(BONE), 'm' to SRecipeChoice(STICK)),
                "x x","yzy"," m "
            )
        }
    ),
    HOOK_STICK(
        SItem(STICK, "§f钩子", "§a用钩子打树叶有几率掉蚕和桑叶", "§b还能增加橡树树苗的掉落几率")
            .also { 
                SItem.addShapedRecipe(
                    it, plugin,
                    "HOOK_STICK_1",
                    mapOf('x' to org.bukkit.Material.STICK),
                    "xx", " x", " x"
                )
                
                SItem.addShapedRecipe(
                    it, plugin,
                    "HOOK_STICK_2",
                    mapOf('x' to STICK),
                    "xx", "x ", "x "
                )
            }
    ),
    HOOK_WOOD(
        SItem(WOODEN_SWORD, "§f木钩", "§a比钩子更快", "§b但易损坏").apply {
            itemMeta = itemMeta?.apply {
                itemFlags.add(ItemFlag.HIDE_ATTRIBUTES)
            }
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
        }.also {
            SItem.addShapedRecipeByChoice(
                it, plugin,
                "HOOK_WOOD",
                mapOf('x' to SRecipeChoice(SMaterial.PLANKS), 'y' to SRecipeChoice(STICK)),
                "yx"," x"," y"
            )
        }
    ),
    HOOK_STONE(
        SItem(STONE_SWORD, "§f石钩", "§a木钩的升级版", "§b增加了耐久").apply {
            itemMeta = itemMeta?.apply {
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
            SItem.addRecipe(this, plugin, ShapelessRecipe(NamespacedKey(plugin, "PEBBLE"), SItem(COBBLESTONE)).addIngredient(4, type))
        }
    ),
	SILKWORM(SItem(LIGHT_GRAY_DYE, "§8蚕", "§a将我拿在副手，用主手喂桑叶给我吃哦~")),
	MULBERRY_LEAVES(SItem(FERN, 2, 1, "§a桑叶", "§f拿在主手时可喂食副手的蚕").also { sItem -> 
        sItem.addAction { event ->
            event.apply {
                val offHandItem = player.inventory.itemInOffHand
                if(offHandItem.type != AIR) {
                    when(offHandItem.type) {
                        LIGHT_GRAY_DYE -> {
                            if(SItem.isItemSimilar(offHandItem, SILKWORM.item, true, true, false, true)) {
                                if(SItem.addUseCount(offHandItem, player, 4)){
                                    SExtensionKt.giveItem(player, SItem(STRING, Random.nextInt(3) + 1))
                                    player.playSound(player.location, Sound.ENTITY_PLAYER_BURP, 1f, 1f)
                                } else {
                                    SExtensionKt.giveItem(player, SItem(STRING))
                                    player.playSound(player.location, Sound.ENTITY_GENERIC_EAT, 1f, 1f)
                                    item?.let { SItem.removeOne(it) }
                                }
                            }
                        }

                        DIRT -> {
                            if(SItem.addUseCount(offHandItem, player, 16)) {
                                offHandItem.amount--
                                SExtensionKt.giveItem(player, SItem(GRASS_BLOCK))
                                player.playSound(player.location, Sound.BLOCK_GRASS_PLACE, 1f, 1.5f)
                            } else {
                                item?.let { SItem.removeOne(it) }
                                SExtensionKt.giveItem(player, SItem(GRASS, 1))
                                player.playSound(player.location, Sound.BLOCK_GRASS_HIT, 1f, 0.5f)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
        
        SItem.protect(sItem)
    }),
    
	TIN_POWDER(SItem(GRAY_DYE, "§7锡粉")),
	COPPER_POWDER(SItem(YELLOW_DYE, "§e铜粉")),
	BRONZE_POWDER(SItem(ORANGE_DYE, "§6青铜粉")),
    BRONZE_INGOT(SItem(BRICK, "§6青铜锭")),
    BRONZE_BLOCK(SItem(ORANGE_CONCRETE, "§6青铜块")),
    
    ADHESIVE(SItem(SHULKER_SHELL, "§7粘合剂")),
    FLOUR(SItem(SNOWBALL, "§f面团")),
    CRUCIBLE_TONGS(SItem(BONE, "§d坩埚钳", "§7坩埚标配的坩埚钳","§a拿在主手时能将","§a副手的圆石塞进坩埚").also {
        SItem.addShapedRecipe(
            it, plugin,
            "CRUCIBLE_TONGS",
            mapOf('x' to COBBLESTONE_WALL, 'y' to BONE, 'z' to TERRACOTTA),
            " x ",
            " y ",
            "z z"
        )
    }
    ),
    
    HAMMER_IRON(
        SItem(IRON_AXE, "§f铁锤", "§7基础的锻造锤","§a用我敲击铁制工作台","§a中的锤子格来进行合成！"),
        "HAMMER_IRON",
        mapOf('x' to IRON_INGOT, 'y' to STICK, 'z' to STRING),
        "xx ","xzy","xx "
    ),
    
    ENRICHED_BONE_MEAL(SItem(BONE_MEAL, "&d富集骨粉"))
    
    
    ;

    val item: ItemStack = item
        get() = field.clone()
    
    
    constructor(item: ItemStack, recipe: Recipe) : this(item) {
        SItem.addRecipe(item, plugin, recipe)
    }
    
    constructor(item: ItemStack, vararg recipes: Recipe) : this(item) {
        recipes.forEach { 
            SItem.addRecipe(item, plugin, it)
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
        SItem.addRecipe(
            item, plugin,
            SShapedRecipe(
                NamespacedKey(plugin, key),
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