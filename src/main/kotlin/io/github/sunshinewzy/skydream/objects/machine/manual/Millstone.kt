package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.*
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Hopper
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Millstone : SMachineManual(
    "Millstone",
    "磨盘",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            b
            
            c
            
            b
        """.trimIndent(),
        mapOf(
            'a' to SBlock(Material.SMOOTH_STONE_SLAB).setItem(SItem(Material.SMOOTH_STONE_SLAB, 2, "§f双层石台阶", "§a记得要把我们叠在一起哦")),
            'b' to SBlock(Material.COBBLESTONE_WALL),
            'c' to SBlock.createAir(SItem(Material.COBBLESTONE_WALL))
        ),
        SCoordinate(0, 2, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc.subtractClone(1)
        val block = loc.block
        val centerBlock = event.loc.block
        val player = event.player
        val inv = player.inventory

        val meta = centerBlock.getSMetadata(SkyDream.plugin, name)
        var cnt = meta.asInt()
        
        cnt = when(block.type) {
            Material.COBBLESTONE ->
                mill(cnt, 8, loc, player, SItem(Material.GRAVEL), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)
            
            Material.GRAVEL ->
                mill(cnt, 8, loc, player, SItem(Material.SAND), Sound.BLOCK_GRAVEL_BREAK, Sound.BLOCK_GRAVEL_HIT)
            
            Material.HAY_BLOCK ->
                mill(cnt, 8, loc, player, SItem(SDItem.FLOUR.item, 9), Sound.BLOCK_GRASS_BREAK, Sound.BLOCK_GRASS_HIT)
            
            Material.STONE ->
                mill(cnt, 8, loc, player, SItem(Material.COBBLESTONE), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)
            
            else -> {
                if(block.type == Material.AIR) {
                    event.loc.addClone(1).block.getChest()?.let { chest ->
                        for(storageItem in chest.blockInventory.storageContents) {
                            val itemType = storageItem?.type ?: continue
                            if(itemType != Material.AIR && itemType.isBlock) {
                                block.type = storageItem.type
                                storageItem.amount--
                                return
                            }
                        }
                    }
                }
                
                player.sendMsg(name, "§4待研磨的方块不正确！")
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                0
            }
        }
        
        meta.data = cnt
        centerBlock.setMetadata(name, meta)
    }

    override fun specialJudge(loc: Location, isFirst: Boolean, level: Short): Boolean {
        if(isFirst){
            val theLoc = loc.addClone(1)
            if(theLoc.block.type == Material.COBBLESTONE_WALL){
                theLoc.block.type = Material.AIR
                return true
            }
            return false
        }
        
        return true
    }
    
    
    private fun mill(
        count: Int,
        maxCnt: Int,
        loc: Location,
        player: Player,
        dropItem: ItemStack,
        breakSound: Sound,
        hitSound: Sound
    ): Int {
        var cnt = count
        val block = loc.block
        
        when {
            cnt >= maxCnt -> {
                cnt = 0
                block.type = Material.AIR
                player.playSound(loc, breakSound, 1f, 2f)
                
                val bottomBlock = loc.subtractClone(2).block
                if(bottomBlock.type == Material.HOPPER) {
                    val state = bottomBlock.state
                    if(state is Hopper) {
                        val inv = state.inventory
                        if(!inv.isFull()) {
                            inv.addItem(dropItem)
                            return cnt
                        }
                    }
                }
                
                loc.world?.dropItemNaturally(loc, dropItem)
            }
            
            cnt >= 1 -> {
                cnt++
                player.playSound(loc, hitSound, 1f, 0f)
            }
            
            else -> {
                cnt = 1
                player.playSound(loc, hitSound, 1f, 0f)
            }
        }
        return cnt
    }
    
}