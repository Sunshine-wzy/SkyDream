package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SBlock.Companion.getSBlock
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.cloneRandomAmount
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.randomAmount
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.isInPercent
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
import kotlin.random.Random

object OreSeparator : MachineManual(
    "矿石分离机",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
        a
        
        a
        ba
        
        a
        """.trimIndent(),
        mapOf('a' to SBlock(Material.FENCE), 'b' to SBlock(Material.WOOL), 'c' to SBlock(Material.WOOD)),
        Triple(0, 0, 0)
    ).apply { 
        val fence = SBlock(Material.FENCE)
        
        val brick = SBlock(Material.SMOOTH_BRICK)
        addUpgrade(hashMapOf(
            Triple(1, 2, 0) to brick,
            Triple(-1, 2, 0) to brick,
            Triple(0, 2, 1) to brick,
            Triple(0, 2, -1) to brick
        ))
        
        val iron = SBlock(Material.IRON_BLOCK)
        addUpgrade(hashMapOf(
            Triple(0, 3, 0) to fence,
            Triple(1, 3, 0) to iron,
            Triple(-1, 3, 0) to iron,
            Triple(0, 3, 1) to iron,
            Triple(0, 3, -1) to iron
        ))

        val gold = SBlock(Material.GOLD_BLOCK)
        addUpgrade(hashMapOf(
            Triple(0, 4, 0) to fence,
            Triple(1, 4, 0) to gold,
            Triple(-1, 4, 0) to gold,
            Triple(0, 4, 1) to gold,
            Triple(0, 4, -1) to gold
        ))

        val redstone = SBlock(Material.GOLD_BLOCK)
        addUpgrade(hashMapOf(
            Triple(0, 5, 0) to fence,
            Triple(1, 5, 0) to redstone,
            Triple(-1, 5, 0) to redstone,
            Triple(0, 5, 1) to redstone,
            Triple(0, 5, -1) to redstone
        ))
    }
) {
    override fun manualRun(event: SMachineRunEvent.Manual) {
        val theLoc = event.loc.addClone(0, -1, 0)
        val block = theLoc.block
        val centerBlock = event.loc.block
        val sBlock = theLoc.getSBlock()
        val player = event.player
        val world = theLoc.world
        
        val level = structure.judgeUpgrade(event.loc)
        
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

            (level >= 3 && sBlock.isSimilar(Material.DIRT, 1)) -> {
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
                player.playSound(theLoc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                player.sendMsg(name, "§4待分离的方块不正确！")
                return
            }
        }
        
    }
    
    
}