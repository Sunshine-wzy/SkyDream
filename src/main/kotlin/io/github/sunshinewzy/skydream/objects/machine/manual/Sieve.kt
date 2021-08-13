package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.enums.SMaterial
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.modules.machine.custom.SMachineRecipe
import io.github.sunshinewzy.sunstcore.objects.*
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.cloneRandomAmount
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.randomAmount
import io.github.sunshinewzy.sunstcore.objects.inventoryholder.SPartProtectInventoryHolder
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.isInPercent
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import io.github.sunshinewzy.sunstcore.utils.subtractClone
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Chest
import kotlin.random.Random

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
        
        addRecipe(
            "DIRT",
            SMachineRecipe.BlockBreak(SCoordinate(0, 1, 0), SBlock(Material.DIRT)),
            SMachineRecipe.ItemAddGround(SCoordinate(0, 1, 0), SDItem.PEBBLE.item)
        )
    }


    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val theLoc = event.loc.addClone(0, 1, 0)
        val block = theLoc.block
        val centerBlock = event.loc.block
        val sBlock = theLoc.getSBlock()
        val player = event.player
        val world = theLoc.world ?: return
        
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
        
    }
    
    
    
    
    
}