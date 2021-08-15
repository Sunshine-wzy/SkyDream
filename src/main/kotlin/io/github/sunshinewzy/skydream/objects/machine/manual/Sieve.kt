package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.*
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.objects.inventoryholder.SPartProtectInventoryHolder
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.putElement
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import io.github.sunshinewzy.sunstcore.utils.subtractClone
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Chest
import java.util.*

object Sieve : SMachineManual(
    "Sieve",
    "筛子",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
         
         a
        
        c
         a
        
         
        ba
        """.trimIndent(),
        mapOf('a' to SBlock(SMaterial.FENCE_WOOD), 'b' to SBlock(SMaterial.WOOL), 'c' to SBlock(SMaterial.TRAPDOOR_WOOD)),
        SCoordinate(0, 1, 0)
    )
) {
    private val sieveRecipes = TreeMap<Short, ArrayList<SieveRecipe>>()
    
    val editItemsMenu = SMenu("Edit Items - $id", "物品组编辑", 6)
    
    
    init {
        isCancelInteract = false
        
        structure.apply { 
            // Level 1 石砖筛子
            addUpgrade(shape, copyIngredients('b' to SBlock(Material.STONE_BRICKS)))
            // Level 2 铁制筛子
            addUpgrade(shape, copyIngredients('b' to SBlock(Material.IRON_BLOCK)))
            // Level 3 金制筛子
            addUpgrade(shape, copyIngredients('b' to SBlock(Material.GOLD_BLOCK)))
            // Level 4 红石筛子
            addUpgrade(shape, copyIngredients('b' to SBlock(Material.REDSTONE_BLOCK)))
            // Level 5 钻石筛子
            addUpgrade(shape, copyIngredients('b' to SBlock(Material.DIAMOND_BLOCK)))
        }
        
        
        addSieveRecipe(
            SieveRecipe(0, SBlock(Material.DIRT), SRandomItems(SRandomItem(100, SDItem.PEBBLE).setRandomAmount(2, 4)), 4),
            SieveRecipe(0, SBlock(Material.MYCELIUM), SRandomItems(
                SRandomItem(25, SItem(Material.BROWN_MUSHROOM)),
                SRandomItem(25, SItem(Material.RED_MUSHROOM))
            ), 4),
            
            SieveRecipe(1, SBlock(Material.GRAVEL), SRandomItems(SRandomItem(100, SItem(Material.IRON_NUGGET)).setRandomAmount(4, 6))),
            SieveRecipe(1, SBlock(Material.DIRT), SRandomItems(
                SRandomItem(100, SDItem.PEBBLE).setRandomAmount(2, 4),
                SRandomItem(10, SItem(Material.WHEAT_SEEDS)),
                SRandomItem(1, SItem(Material.BEETROOT_SEEDS)),
                SRandomItem(5, SItem(Material.PUMPKIN_SEEDS)),
                SRandomItem(5, SItem(Material.MELON_SEEDS)),
                SRandomItem(10, SItem(Material.OAK_SAPLING)),
                SRandomItem(10, SItem(Material.BIRCH_SAPLING)),
                SRandomItem(10, SItem(Material.SPRUCE_SAPLING)),
                SRandomItem(5, SItem(Material.JUNGLE_SAPLING)),
                SRandomItem(5, SItem(Material.ACACIA_SAPLING)),
                SRandomItem(5, SItem(Material.DARK_OAK_SAPLING))
            ), 4),
            
            SieveRecipe(2, SBlock(Material.SAND), SRandomItems(
                SRandomItem(100, SItem(Material.GOLD_NUGGET)).setRandomAmount(4, 6),
                SRandomItem(5, SItem(Material.CACTUS))
            ), sieveSound = Sound.BLOCK_SAND_HIT, completeSound = Sound.BLOCK_SAND_BREAK),
            SieveRecipe(2, SBlock(Material.GRAVEL), SRandomItems(SRandomItem(100, SItem(Material.IRON_NUGGET)).setRandomAmount(7, 9))),
            
            SieveRecipe(3, SBlock(Material.SAND), SRandomItems(
                SRandomItem(100, SItem(Material.GOLD_NUGGET)).setRandomAmount(7, 9),
                SRandomItem(5, SItem(Material.CACTUS))
            ), sieveSound = Sound.BLOCK_SAND_HIT, completeSound = Sound.BLOCK_SAND_BREAK),
            SieveRecipe(3, SBlock(Material.CLAY), SRandomItems(SRandomItem(25, SItem(Material.REDSTONE)).setRandomAmount(2))),
            SieveRecipe(3, SBlock(Material.COARSE_DIRT), SRandomItems(
                SRandomItem(30, SItem(Material.COAL)).setRandomAmount(2),
                SRandomItem(5, SItem(Material.SUGAR_CANE)),
                SRandomItem(1, SItem(Material.BAMBOO)),
                SRandomItem(1, SItem(Material.LILY_PAD)),
                SRandomItem(1, SItem(Material.VINE)),
                SRandomItem(1, SItem(Material.COCOA_BEANS)),
                SRandomItem(1, SItem(Material.SWEET_BERRIES))
            )),
            
            SieveRecipe(4, SBlock(Material.GRAVEL), SRandomItems(SRandomItem(100, SItem(Material.IRON_NUGGET)).setRandomAmount(7, 9), SRandomItem(20, SDItem.TIN_POWDER))),
            SieveRecipe(4, SBlock(Material.SAND), SRandomItems(
                SRandomItem(100, SItem(Material.GOLD_NUGGET)).setRandomAmount(7, 9),
                SRandomItem(30, SDItem.COPPER_POWDER),
                SRandomItem(5, SItem(Material.CACTUS))
            ), sieveSound = Sound.BLOCK_SAND_HIT, completeSound = Sound.BLOCK_SAND_BREAK),
            SieveRecipe(4, SBlock(Material.COARSE_DIRT), SRandomItems(
                SRandomItem(50, SItem(Material.COAL)).setRandomAmount(2),
                SRandomItem(5, SItem(Material.DIAMOND)),
                SRandomItem(5, SItem(Material.SUGAR_CANE)),
                SRandomItem(1, SItem(Material.BAMBOO)),
                SRandomItem(1, SItem(Material.LILY_PAD)),
                SRandomItem(1, SItem(Material.VINE)),
                SRandomItem(1, SItem(Material.COCOA_BEANS)),
                SRandomItem(1, SItem(Material.SWEET_BERRIES))
            )),

            SieveRecipe(5, SBlock(Material.GRAVEL), SRandomItems(
                SRandomItem(100, SItem(Material.IRON_NUGGET)).setRandomAmount(7, 9),
                SRandomItem(25, SDItem.TIN_POWDER),
                SRandomItem(10, SItem(Material.LAPIS_LAZULI))
            ), 4),
            SieveRecipe(5, SBlock(Material.SAND), SRandomItems(
                SRandomItem(100, SItem(Material.GOLD_NUGGET)).setRandomAmount(7, 9),
                SRandomItem(35, SDItem.COPPER_POWDER),
                SRandomItem(5, SItem(Material.CACTUS)),
                SRandomItem(1, SItem(Material.EMERALD))
            ), 4, sieveSound = Sound.BLOCK_SAND_HIT, completeSound = Sound.BLOCK_SAND_BREAK),
            SieveRecipe(5, SBlock(Material.COARSE_DIRT), SRandomItems(
                SRandomItem(50, SItem(Material.COAL)).setRandomAmount(3),
                SRandomItem(5, SItem(Material.DIAMOND)),
                SRandomItem(5, SItem(Material.SUGAR_CANE)),
                SRandomItem(1, SItem(Material.BAMBOO)),
                SRandomItem(1, SItem(Material.LILY_PAD)),
                SRandomItem(1, SItem(Material.VINE)),
                SRandomItem(1, SItem(Material.COCOA_BEANS)),
                SRandomItem(1, SItem(Material.SWEET_BERRIES))
            ), 4),
            SieveRecipe(5, SBlock(Material.SOUL_SAND), SRandomItems(
                SRandomItem(5, SItem(Material.NETHERITE_SCRAP)),
                SRandomItem(1, SItem(Material.GHAST_TEAR)),
                SRandomItem(10, SItem(Material.NETHER_WART)),
                SRandomItem(5, SItem(Material.CRIMSON_FUNGUS)),
                SRandomItem(5, SItem(Material.WARPED_FUNGUS))
            ), 4, sieveSound = Sound.BLOCK_STONE_HIT, completeSound = Sound.BLOCK_STONE_BREAK),
            SieveRecipe(5, SBlock(Material.CLAY), SRandomItems(
                SRandomItem(25, SItem(Material.REDSTONE)).setRandomAmount(3),
                SRandomItem(25, SItem(Material.GLOWSTONE_DUST)).setRandomAmount(3)
            ), 4)

        )
        
        
        editItemsMenu.createEdge(SItem(Material.WHITE_STAINED_GLASS_PANE))
        editItemsMenu.setDefaultTurnPageButton()
        editItemsMenu.holder = SPartProtectInventoryHolder(
            ArrayList<Int>().apply {
                for (i in 2..8) {
                    for (j in 3..4) {
                        add(i orderWith j)
                    }
                }
            }, id
        )
        
    }


    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val theLoc = event.loc.addClone(0, 1, 0)
        val block = theLoc.block
        val centerBlock = event.loc.block
        val sBlock = theLoc.getSBlock()
        val player = event.player
        val world = theLoc.world ?: return
        
        for(i in level downTo 0) {
            val lv = i.toShort()
            sieveRecipes[lv]?.forEach { sieveRecipe ->
                if(sBlock == sieveRecipe.sBlock) {
                    if(addMetaCnt(centerBlock, sieveRecipe.maxCount).flag) {
                        player.playSound(theLoc, sieveRecipe.sieveSound, sieveRecipe.sieveVolume, sieveRecipe.sievePitch)
                    } else {
                        block.type = Material.AIR
                        sieveRecipe.dropItems.takeAll().forEach { 
                            world.dropItemNaturally(theLoc, it)
                        }
                        player.playSound(theLoc, sieveRecipe.completeSound, sieveRecipe.completeVolume, sieveRecipe.completePitch)
                        sieveRecipe.action(event)
                    }
                    
                    return
                }
            }
        }
        
        /*
        when {
            (level >= 0 && sBlock.isSimilar(Material.DIRT)) -> {
                if(addMetaCnt(centerBlock, 4).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    world.dropItemNaturally(theLoc, SDItem.PEBBLE.item.cloneRandomAmount(2, 4))
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            (level >= 5 && sBlock.isSimilar(Material.GRAVEL)) -> {
                if(addMetaCnt(centerBlock, 4).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR

                    if(Random.isInPercent(25))
                        world.dropItemNaturally(theLoc, SDItem.TIN_POWDER.item)
                    else world.dropItemNaturally(theLoc, SItem(Material.IRON_NUGGET).randomAmount(7, 9))

                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            (level >= 5 && sBlock.isSimilar(Material.SAND)) -> {
                if(addMetaCnt(centerBlock, 4).flag){
                    player.playSound(theLoc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR

                    if(Random.isInPercent(25))
                        world.dropItemNaturally(theLoc, SDItem.COPPER_POWDER.item)
                    else world.dropItemNaturally(theLoc, SItem(Material.GOLD_NUGGET).randomAmount(7, 9))

                    player.playSound(theLoc, Sound.BLOCK_SAND_BREAK, 1f, 2f)
                }
            }

            (level >= 4 && sBlock.isSimilar(Material.GRAVEL)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    
                    if(Random.isInPercent(20))
                        world.dropItemNaturally(theLoc, SDItem.TIN_POWDER.item)
                    else world.dropItemNaturally(theLoc, SItem(Material.IRON_NUGGET).randomAmount(7, 9))
                    
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            (level >= 4 && sBlock.isSimilar(Material.SAND)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR

                    if(Random.isInPercent(20))
                        world.dropItemNaturally(theLoc, SDItem.COPPER_POWDER.item)
                    else world.dropItemNaturally(theLoc, SItem(Material.GOLD_NUGGET).randomAmount(7, 9))

                    player.playSound(theLoc, Sound.BLOCK_SAND_BREAK, 1f, 2f)
                }
            }

            (level >= 4 && sBlock.isSimilar(Material.COARSE_DIRT)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR

                    if(Random.isInPercent(50))
                        world.dropItemNaturally(theLoc, SItem(Material.COAL).randomAmount(2))
                    if(Random.isInPercent(5))
                        world.dropItemNaturally(theLoc, SItem(Material.DIAMOND))

                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            
            (level >= 3 && sBlock.isSimilar(Material.SAND)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    world.dropItemNaturally(theLoc, SItem(Material.GOLD_NUGGET).randomAmount(7, 9))
                    player.playSound(theLoc, Sound.BLOCK_SAND_BREAK, 1f, 2f)
                }
            }

            (level >= 3 && sBlock.isSimilar(Material.CLAY)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    if(Random.isInPercent(25))
                        world.dropItemNaturally(theLoc, SItem(Material.REDSTONE).randomAmount(2))
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            (level >= 3 && sBlock.isSimilar(Material.COARSE_DIRT)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    if(Random.isInPercent(30))
                        world.dropItemNaturally(theLoc, SItem(Material.COAL).randomAmount(2))
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }
            
            
            (level >= 2 && sBlock.isSimilar(Material.GRAVEL)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    world.dropItemNaturally(theLoc, SItem(Material.IRON_NUGGET).randomAmount(7, 9))
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }
            
            (level >= 2 && sBlock.isSimilar(Material.SAND)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    world.dropItemNaturally(theLoc, SItem(Material.GOLD_NUGGET).randomAmount(4, 6))
                    player.playSound(theLoc, Sound.BLOCK_SAND_BREAK, 1f, 2f)
                }
            }
            
            
            (level >= 1 && sBlock.isSimilar(Material.GRAVEL)) -> {
                if(addMetaCnt(centerBlock, 8).flag){
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_HIT, 1f, 0f)
                }
                else{
                    block.type = Material.AIR
                    world.dropItemNaturally(theLoc, SItem(Material.IRON_NUGGET).randomAmount(4, 6))
                    player.playSound(theLoc, Sound.BLOCK_GRAVEL_BREAK, 1f, 2f)
                }
            }

            
            else -> {
                if(block.type == Material.AIR || block.type == Material.WATER) {
                    val bottomLoc = event.loc.subtractClone(1)
                    val bottomBlock = bottomLoc.block

                    if(bottomBlock.type == Material.CHEST) {
                        val state = bottomBlock.state
                        if(state is Chest) {
                            val inv = state.blockInventory
                            
                            for(storageItem in inv.storageContents) {
                                val itemType = storageItem.type
                                if(itemType != Material.AIR && itemType.isBlock) {
                                    storageItem.amount--
                                    block.type = storageItem.type
                                    return
                                }
                            }
                        }
                    }
                }
                
                
                player.playSound(theLoc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                player.sendMsg(name, "§4这个方块不能筛！")
                return
            }
        }
        */

        if(block.type == Material.AIR || block.type == Material.WATER) {
            val bottomLoc = event.loc.subtractClone(1)
            val bottomBlock = bottomLoc.block

            if(bottomBlock.type == Material.CHEST) {
                val state = bottomBlock.state
                if(state is Chest) {
                    val inv = state.blockInventory

                    for(storageItem in inv.storageContents) {
                        val itemType = storageItem?.type ?: continue
                        if(itemType != Material.AIR && itemType.isBlock) {
                            storageItem.amount--
                            block.type = storageItem.type
                            return
                        }
                    }
                }
            }
        }


        player.playSound(theLoc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
        player.sendMsg(name, "§4这个方块不能筛！")
    }
    
    
    fun addSieveRecipe(vararg sieveRecipe: SieveRecipe) {
        sieveRecipe.forEach { 
            sieveRecipes.putElement(it.level, it)
        }
    }
    
    
}


class SieveRecipe(
    val level: Short,
    val sBlock: SBlock,
    val dropItems: SRandomItems,
    val maxCount: Int = 8,
    val sieveSound: Sound = Sound.BLOCK_GRAVEL_HIT,
    val sieveVolume: Float = 1f,
    val sievePitch: Float = 0f,
    val completeSound: Sound = Sound.BLOCK_GRAVEL_BREAK,
    val completeVolume: Float = 1f,
    val completePitch: Float = 2f,
    val action: SMachineRunEvent.Manual.() -> Unit = {}
)