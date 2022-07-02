package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.skydream.objects.machine.manual.SFluid.Companion.bucketToFluid
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SCoordinate
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.isItemSimilar
import io.github.sunshinewzy.sunstcore.objects.SLocation
import io.github.sunshinewzy.sunstcore.utils.giveItemInMainHand
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.ItemStack

object FluidTank : SMachineManual(
    "FluidTank", "流体储罐",
    SDMachine.wrench,
    SMachineStructure.CentralSymmetry(
        SMachineSize.SIZE3,
        """
            a
            b
            
             
            c
            
            a
            b
        """.trimIndent(),
        mapOf('a' to SBlock(Material.IRON_BLOCK), 'b' to SBlock(Material.IRON_BARS), 'c' to SBlock(Material.GLASS)),
        SCoordinate(0, 2, 0)
    )
) {
    const val LEVEL0 = 16 * 1000
    const val LEVEL1 = 32 * 1000
    const val LEVEL2 = 48 * 1000
    const val LEVEL3 = 64 * 1000
    
    
    override fun manualRun(event: SMachineRunEvent.Manual, level: Short) {
        event.apply { 
            val inv = player.inventory
            val handItem = inv.itemInMainHand

            when (handItem.type) {
                Material.BUCKET -> {
                    val pair = removeStorageFluid(sLoc)
                    val sFluid = pair.second

                    if(sFluid == null || pair.first == -1) {
                        player.sendMsg(name, "&c空")
                        return
                    }

                    when(pair.first) {
                        0 -> {
                            handItem.amount--
                            player.giveItemInMainHand(sFluid.bucket)
                            player.playSound(player.location, Sound.ITEM_BUCKET_FILL, 1f, 1f)
                        }

                        else -> {
                            player.playSound(player.location, Sound.ENTITY_PLAYER_SWIM, 1f, 1f)
                        }
                    }
                }
                
                else -> {
                    getStorageFluid(sLoc)?.let { sFluid->
                        if(handItem.isItemSimilar(sFluid.bucket, checkAmount = false)) {
                            if(addStorageFluid(sLoc, sFluid) != -1) {
                                handItem.amount--
                                player.giveItemInMainHand(SItem(Material.BUCKET))
                            }
                        }

                        if(sFluid.volume != 0) {
                            player.sendMsg(name, "&b${sFluid.name} &f-> &a${sFluid.volume}mL")
                            return
                        }
                    }
                    
                    bucketToFluid[handItem.type]?.forEach { sFluid ->
                        if(handItem.isItemSimilar(sFluid.bucket, checkAmount = false)) {
                            if(addStorageFluid(sLoc, sFluid, true) != -1) {
                                handItem.amount--
                                player.giveItemInMainHand(SItem(Material.BUCKET))
                                return
                            }
                        }
                    }

                    player.sendMsg(name, "&c空")
                }
            }
        }
    }
    
    
    fun addStorageFluid(sLoc: SLocation, fluid: SFluid, isNew: Boolean = false, volume: Int = 1000): Int {
        val sFluid = if(isNew) {
            val newFluid = fluid.copy()
            setData(sLoc, "StorageFluid", newFluid)
            newFluid
        } else fluid

        return when(getLevel(sLoc)) {
            0.toShort() -> sFluid.addFluid(volume, LEVEL0)
            1.toShort() -> sFluid.addFluid(volume, LEVEL1)
            2.toShort() -> sFluid.addFluid(volume, LEVEL2)
            3.toShort() -> sFluid.addFluid(volume, LEVEL3)

            else -> -1
        }
    }
    
    fun removeStorageFluid(sLoc: SLocation, volume: Int = 1000): Pair<Int, SFluid?> {
        getStorageFluid(sLoc)?.let { sFluid ->
            return sFluid.removeFluid(volume) to sFluid
        }
        
        return -1 to null
    }
    
    
    fun getStorageFluid(sLoc: SLocation): SFluid? =
        getDataByType<SFluid>(sLoc, "StorageFluid")
    
}

data class SFluid(var name: String = "空", var bucket: ItemStack = SItem(Material.AIR), var blockType: Material = Material.AIR, var volume: Int = 0) : ConfigurationSerializable {
    
    constructor(name: String, bucket: Material, blockType: Material, volume: Int = 0) : this(name, SItem(bucket), blockType, volume)


    fun addFluid(addVolume: Int, maxVolume: Int): Int {
        if(volume == maxVolume) return -1
        
        val sum = volume + addVolume
        if(sum > maxVolume) {
            volume = maxVolume
            return sum - maxVolume
        }

        volume = sum
        return 0
    }
    
    fun removeFluid(removeVolume: Int): Int {
        if(volume >= removeVolume) {
            volume -= removeVolume
            return 0
        }
        
        return removeVolume - volume
    }
    
    override fun serialize(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = name
        map["bucket"] = bucket
        map["blockType"] = blockType.name
        map["volume"] = volume
        return map
    }

    companion object {
        val bucketToFluid = hashMapOf<Material, ArrayList<SFluid>>()
        
        init {
            arrayOf(
                SFluid("水", Material.WATER_BUCKET, Material.WATER),
                SFluid("岩浆", Material.LAVA_BUCKET, Material.LAVA),
                SFluid("牛奶", Material.MILK_BUCKET, Material.WATER)
            ).forEach { addFluid(it) }
        }
        
        
        fun addFluid(sFluid: SFluid) {
            sFluid.volume = 0

            val listFluid = bucketToFluid[sFluid.bucket.type]
            if(listFluid != null) {
                listFluid += sFluid
            } else bucketToFluid[sFluid.bucket.type] = arrayListOf(sFluid)
        }
        

        @JvmStatic
        fun deserialize(map: Map<String, Any>): SFluid {
            val sFluid = SFluid()
            
            map["name"]?.let { name ->
                if(name is String) {
                    sFluid.name = name
                }
            }

            map["bucket"]?.let { bucket ->
                if(bucket is ItemStack) {
                    sFluid.bucket = bucket
                }
            }

            map["blockType"]?.let { blockType ->
                if(blockType is String) {
                    sFluid.blockType = Material.valueOf(blockType)
                }
            }

            map["volume"]?.let { volume ->
                if(volume is Int) {
                    sFluid.volume = volume
                }
            }
            
            return sFluid
        }
    }
    
}