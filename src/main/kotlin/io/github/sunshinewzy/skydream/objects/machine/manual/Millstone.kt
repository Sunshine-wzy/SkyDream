package io.github.sunshinewzy.skydream.objects.machine.manual

import io.github.sunshinewzy.skydream.SkyDream
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.sunstcore.modules.machine.MachineManual
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineRunEvent
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineSize
import io.github.sunshinewzy.sunstcore.modules.machine.SMachineStructure
import io.github.sunshinewzy.sunstcore.objects.SBlock
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.addClone
import io.github.sunshinewzy.sunstcore.utils.getSMetadata
import io.github.sunshinewzy.sunstcore.utils.removeClone
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Millstone : MachineManual(
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
            'a' to SBlock(Material.DOUBLE_STEP).setDisplayItem(SItem(Material.STEP, 2, "§f双层石台阶", "§a记得要把我们叠在一起哦")),
            'b' to SBlock(Material.COBBLE_WALL),
            'c' to SBlock(Material.AIR).setDisplayItem(SItem(Material.COBBLE_WALL))
        ),
        Triple(0, 2, 0)
    )
) {

    override fun manualRun(event: SMachineRunEvent.Manual) {
        val loc = event.loc.removeClone(1)
        val block = loc.block
        val centerBlock = event.loc.block
        val player = event.player
        val inv = player.inventory

        val meta = centerBlock.getSMetadata(SkyDream.getPlugin(), name)
        var cnt = meta.asInt()
        
        cnt = when(block.type) {
            Material.COBBLESTONE ->
                mill(cnt, 8, loc, player, SItem(Material.GRAVEL), Sound.BLOCK_STONE_BREAK, Sound.BLOCK_STONE_HIT)
            
            Material.GRAVEL ->
                mill(cnt, 8, loc, player, SItem(Material.SAND), Sound.BLOCK_GRAVEL_BREAK, Sound.BLOCK_GRAVEL_HIT)
            
            Material.HAY_BLOCK ->
                mill(cnt, 8, loc, player, SItem(SDItem.FLOUR.item, 9), Sound.BLOCK_GRASS_BREAK, Sound.BLOCK_GRASS_HIT)
            
            else -> {
                player.sendMsg(name, "§4待研磨的方块不正确！")
                player.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1f, 1.8f)
                0
            }
        }
        
        meta.data = cnt
        centerBlock.setMetadata(name, meta)
    }

    override fun specialJudge(loc: Location, isFirst: Boolean): Boolean {
        if(isFirst){
            val theLoc = loc.addClone(1)
            if(theLoc.block.type == Material.COBBLE_WALL){
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
                loc.world.dropItemNaturally(loc, dropItem)
                player.sendMsg(name, "§a研磨成功！")
                player.playSound(loc, breakSound, 1f, 2f)
            }
            cnt >= 1 -> {
                cnt++
                player.sendMsg(name, "§e研磨中...")
                player.playSound(loc, hitSound, 1f, 0f)
            }
            else -> {
                cnt = 1
                player.sendMsg(name, "§b开始研磨:")
                player.playSound(loc, hitSound, 1f, 0f)
            }
        }
        return cnt
    }
    
}