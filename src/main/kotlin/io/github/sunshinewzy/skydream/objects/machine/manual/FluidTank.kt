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
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Material
import org.bukkit.Sound
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
                Material.AIR -> {
                    getStorageFluid(sLoc)?.let {
                        player.sendMsg(name, "&b${it.name} &f-> &a${it.volume}mL")
                        return
                    }

                    player.sendMsg(name, "&c空")
                }
                
                Material.BUCKET -> {
                    val pair = removeStorageFluid(sLoc)
                    val sFluid = pair.second

                    if(sFluid == null || pair.first == -1) {
                        player.sendMsg(name, "&c空")
                        return
                    }

                    when(pair.first) {
                        0 -> {
                            inv.setItemInMainHand(sFluid.bucket)
                            player.playSound(player.location, Sound.ITEM_BUCKET_FILL, 1f, 1f)
                        }

                        else -> {
                            player.playSound(player.location, Sound.BLOCK_WATER_AMBIENT, 1f, 1f)
                        }
                    }
                }
                
                else -> bucketToFluid[handItem.type]?.forEach { sFluid ->
                    if(handItem.isItemSimilar(sFluid.bucket, checkAmount = false)) {
                        
                    }
                }
            }
        }
    }
    
    
    fun addStorageFluid(sLoc: SLocation, volume: Int = 1000): Pair<Int, SFluid?> {
        getStorageFluid(sLoc)?.let { sFluid ->
            return when(getLevel(sLoc)) {
                0.toShort() -> sFluid.addFluid(volume, LEVEL0)
                1.toShort() -> sFluid.addFluid(volume, LEVEL1)
                2.toShort() -> sFluid.addFluid(volume, LEVEL2)
                3.toShort() -> sFluid.addFluid(volume, LEVEL3)
                
                else -> -1
            } to sFluid
        }
        
        return -1 to null
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

data class SFluid(val name: String, val bucket: ItemStack, val blockType: Material, var volume: Int = 0) {
    
    constructor(name: String, bucket: Material, blockType: Material, volume: Int = 0) : this(name, SItem(bucket), blockType, volume)


    fun addFluid(addVolume: Int, maxVolume: Int): Int {
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
        
        val res = removeVolume - volume
        volume = 0
        return res
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
    }
    
}