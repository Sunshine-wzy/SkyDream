package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.BlockOperator
import io.github.sunshinewzy.sunstcore.utils.SExtensionKt
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound

object Composter : SMachineManual(
    "Composter", "堆肥器",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            s
            bg
            
            c
        """.trimIndent(),
        mapOf('s' to SBlock(Material.SMOOTH_STONE), 'b' to SBlock(Material.SMOOTH_STONE_SLAB), 'g' to SBlock(Material.GRASS_BLOCK), 'c' to SBlock(Material.COMPOSTER)),
        SCoordinate(0, 1, 0)
    )
) {
    private val boneMeal = SItem(Material.BONE_MEAL)
    
    val tallFlowers= arrayOf(Material.ROSE_BUSH, Material.LILAC, Material.PEONY, Material.SUNFLOWER)
    val flowers = arrayOf(Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY)
    
    
    init {
        structure.apply { 
            structure[SCoordinate(1, 1, 1)] = SBlock.createAir(SItem(Material.POPPY))
            structure[SCoordinate(1, 1, -1)] = SBlock.createAir(SItem(Material.DANDELION))
            structure[SCoordinate(-1, 1, 1)] = SBlock.createAir(SItem(Material.CORNFLOWER))
            structure[SCoordinate(-1, 1, -1)] = SBlock.createAir(SItem(Material.OXEYE_DAISY))
        }
    }
    
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val player = event.player
        val handItem = player.inventory.itemInMainHand
        val loc = event.loc
        val upLoc = SExtensionKt.addClone(loc, 1)
        
        if(SItem.isItemSimilar(handItem, boneMeal)) {
            if(handItem.amount >= 9) {
                if(addMetaCnt(event, 8).flag) {
                    player.playSound(loc, Sound.BLOCK_COMPOSTER_FILL, 1f, 1.5f)
                } else {
                    handItem.amount -= 9
                    player.world.dropItemNaturally(upLoc, SDItem.ENRICHED_BONE_MEAL.item)
                    player.playSound(loc, Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1f, 1.5f)
                }
            } else {
                SExtensionKt.sendMsg(player, name, "&c至少需要9个骨粉")
                player.playSound(loc, Sound.ENTITY_VILLAGER_NO, 1f, 0.5f)
            }
        } else {
            player.playSound(loc, Sound.ENTITY_VILLAGER_NO, 1f, 0.5f)
        }
    }

    override fun specialJudge(loc: Location, isFirst: Boolean, level: Short): Boolean {
        var poppy = false
        var dandelion = false
        var cornflower = false
        var oxeyeDaisy = false
        
        BlockOperator.operate(SExtensionKt.addClone(loc, 1).block) { operator ->
            operator.apply {
                horizontal(true) { theBlock ->
                    when(theBlock.type) {
                        Material.POPPY -> poppy = true
                        Material.DANDELION -> dandelion = true
                        Material.CORNFLOWER -> cornflower = true
                        Material.OXEYE_DAISY -> oxeyeDaisy = true

                        else -> {}
                    }
                    false
                }
            }
        }
        
        return poppy && dandelion && cornflower && oxeyeDaisy
    }
    
}