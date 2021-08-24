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
import io.github.sunshinewzy.sunstcore.utils.BlockOperator.Companion.operate
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Chest
import org.bukkit.block.Hopper
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object HeavyMillstone : SMachineManual(
    "HeavyMillstone", "重型磨盘",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE5,
        """
            h
             ss
             s
            
            y
            kyf
             f
            
             
              f
             f
            
            i
            iif
             f
            
            s
            f
        """.trimIndent(),
        mapOf(
            'h' to SBlock(Material.HOPPER),
            's' to SBlock(Material.STONE_BRICK_WALL),
            'y' to SBlock(Material.OBSIDIAN),
            'k' to SBlock(Material.CRYING_OBSIDIAN),
            'f' to SBlock(Material.IRON_BARS),
            'i' to SBlock(Material.IRON_BLOCK),
        ),
        SCoordinate(0, 4, 0)
    )
) {
    
    init {
        isCancelInteract = false
    }

    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc.subtractClone(2)
        val block = loc.block
        val centerBlock = event.loc.block
        val player = event.player
        val hopper = event.loc.subtractClone(4).block.getHopper() ?: return

        val meta = centerBlock.getSMetadata(SkyDream.plugin, name)
        var cnt = meta.asInt()
        val oldCnt = cnt
        var airCnt = 0
        var chestState: Chest? = null

        cnt = when(block.type) {
            Material.COBBLESTONE ->
                mill(cnt, 8, loc, player, hopper, SItem(Material.GRAVEL), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)

            Material.GRAVEL ->
                mill(cnt, 8, loc, player, hopper, SItem(Material.SAND), Sound.BLOCK_GRAVEL_BREAK, Sound.BLOCK_GRAVEL_HIT)

            Material.HAY_BLOCK ->
                mill(cnt, 8, loc, player, hopper, SItem(SDItem.FLOUR.item, 9), Sound.BLOCK_GRASS_BREAK, Sound.BLOCK_GRASS_HIT)

            Material.STONE ->
                mill(cnt, 8, loc, player, hopper, SItem(Material.COBBLESTONE), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)

            else -> {
                if(block.type == Material.AIR) {
                    if(chestState == null) chestState = event.loc.addClone(1).block.getChest()
                    chestState?.let { chest ->
                        for(storageItem in chest.blockInventory.storageContents) {
                            val itemType = storageItem?.type ?: continue
                            if(itemType != Material.AIR && itemType.isBlock) {
                                block.type = storageItem.type
                                storageItem.amount--
                                return@let
                            }
                        }

                        airCnt++
                    }
                } else {
                    player.sendMsg(name, "§4待研磨的方块不正确！")
                    player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                }

                0
            }
        }

        meta.data = cnt
        centerBlock.setMetadata(name, meta)

        block.operate {
            horizontal(true) {
                when(type) {
                    Material.COBBLESTONE ->
                        mill(oldCnt, 8, location, player, hopper, SItem(Material.GRAVEL), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)

                    Material.GRAVEL ->
                        mill(oldCnt, 8, location, player, hopper, SItem(Material.SAND), Sound.BLOCK_GRAVEL_BREAK, Sound.BLOCK_GRAVEL_HIT)

                    Material.HAY_BLOCK ->
                        mill(oldCnt, 8, location, player, hopper, SItem(SDItem.FLOUR.item, 9), Sound.BLOCK_GRASS_BREAK, Sound.BLOCK_GRASS_HIT)

                    Material.STONE ->
                        mill(oldCnt, 8, location, player, hopper, SItem(Material.COBBLESTONE), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)

                    else -> {
                        if(type == Material.AIR) {
                            if(chestState == null) chestState = event.loc.addClone(1).block.getChest()
                            chestState?.let { chest ->
                                for(storageItem in chest.blockInventory.storageContents) {
                                    val itemType = storageItem?.type ?: continue
                                    if(itemType != Material.AIR && itemType.isBlock) {
                                        type = storageItem.type
                                        storageItem.amount--
                                        return@horizontal false
                                    }
                                }

                                airCnt++
                            }
                        } else {
                            player.sendMsg(name, "§4待研磨的方块不正确！")
                            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                        }
                    }
                }

                false
            }
        }
        
        if(airCnt == 9) {
            player.sendMsg(name, "§4没有待研磨的方块了！")
            player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 2f)
        }
    }


    private fun mill(
        count: Int,
        maxCnt: Int,
        loc: Location,
        player: Player,
        hopper: Hopper,
        dropItem: ItemStack,
        breakSound: Sound,
        hitSound: Sound
    ): Int {
        var cnt = count
        val block = loc.block

        when {
            cnt >= maxCnt - 1 -> {
                cnt = 0
                block.type = Material.AIR
                player.playSound(loc, breakSound, 1f, 2f)

                val inv = hopper.inventory
                if(!inv.isFull()) {
                    inv.addItem(dropItem)
                    return cnt
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