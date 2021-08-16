package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.*
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.containsItem
import io.github.sunshinewzy.sunstcore.utils.removeSItem
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Dropper

object WaterPouringMachine : SMachineManual(
    "WaterPouringMachine",
    "水流灌注机",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            b
            
            e
            c
            
            d
            c
        """.trimIndent(),
        mapOf(
            'a' to SBlock(Material.COBBLESTONE_WALL),
            'b' to SBlock(Material.SMOOTH_STONE_SLAB),
            'c' to SBlock(Material.GLASS_PANE),
            'd' to SBlock.createAir(SItem(Material.WATER_BUCKET, "§f水", "§a在这里放一桶水","§d我知道你没水桶","§b把压榨机建在粘土制造机的上面","§b然后直接压榨出水...")),
            'e' to SBlock.createAir(SItem(Material.COBBLESTONE_WALL))
        ),
        SCoordinate(0, 0, 0)
    )
) {
    
    init {
        isCancelInteract = false
    }
    

    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        val loc = event.loc.addClone(1)
        val centerLoc = event.loc
        val block = loc.block
        val player = event.player
        
        when(block.type) {
            Material.SAND -> {
                when(addMetaCnt(centerLoc, 8)) {
                    SMachineStatus.START -> {
                        player.playSound(loc, Sound.ITEM_BUCKET_FILL, 1f, 0f)
                    }
                    
                    SMachineStatus.RUNNING -> {
                        player.playSound(loc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                    }
                    
                    SMachineStatus.FINISH -> {
                        block.type = Material.CLAY
                        player.playSound(loc, Sound.BLOCK_GRAVEL_PLACE, 1f, 2f)
                    }
                }
            }

            Material.CLAY -> {
                when(addMetaCnt(centerLoc, 8)) {
                    SMachineStatus.START -> {
                        player.playSound(loc, Sound.ITEM_BUCKET_FILL, 1f, 0f)
                    }

                    SMachineStatus.RUNNING -> {
                        player.playSound(loc, Sound.BLOCK_SAND_HIT, 1f, 0f)
                    }

                    SMachineStatus.FINISH -> {
                        block.type = Material.MYCELIUM
                        player.playSound(loc, Sound.BLOCK_GRAVEL_PLACE, 1f, 2f)
                    }
                }
            }

            Material.NETHERRACK -> {
                when(addMetaCnt(centerLoc, 8)) {
                    SMachineStatus.START -> {
                        player.playSound(loc, Sound.ITEM_BUCKET_FILL, 1f, 0f)
                    }

                    SMachineStatus.RUNNING -> {
                        player.playSound(loc, Sound.BLOCK_STONE_HIT, 1f, 0f)
                    }

                    SMachineStatus.FINISH -> {
                        block.type = Material.SOUL_SAND
                        player.playSound(loc, Sound.BLOCK_SOUL_SAND_PLACE, 1f, 2f)
                    }
                }
            }
            
            Material.DROPPER -> {
                val state = block.state
                if(state is Dropper) {
                    val inv = state.inventory
                    if(inv.containsItem(SItem(Material.NAUTILUS_SHELL, 64), 9)) {
                        when(addMetaCnt(centerLoc, 8)) {
                            SMachineStatus.START -> {
                                player.playSound(loc, Sound.ITEM_BUCKET_FILL, 1f, 0f)
                            }

                            SMachineStatus.RUNNING -> {
                                player.playSound(loc, Sound.ENTITY_PLAYER_SWIM, 1f, 0f)
                            }

                            SMachineStatus.FINISH -> {
                                if(inv.removeSItem(SItem(Material.NAUTILUS_SHELL, 64))) {
                                    inv.setItem(0, SItem(Material.HEART_OF_THE_SEA))
                                }
                                player.playSound(loc, Sound.ITEM_BUCKET_EMPTY, 1f, 2f)
                            }
                        }
                    }
                }
            }
            
            else -> {
                player.sendMsg(name, "§4制造原料不正确！")
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
            }
        }
        
    }

    override fun specialJudge(loc: Location, isFirst: Boolean, level: Short): Boolean {
        val topBlock = loc.addClone(2).block
        if(topBlock.type != Material.WATER)
            return false
        
        if(isFirst){
            val upLoc = loc.addClone(1)
            if(upLoc.block.type == Material.COBBLESTONE_WALL){
                upLoc.block.type = Material.AIR
                return true
            }
            return false
        }

        return true
    }
    
}