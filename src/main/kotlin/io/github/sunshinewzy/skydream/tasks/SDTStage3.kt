package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.machine.manual.Sieve
import io.github.sunshinewzy.skydream.tasks.SDTask.stage3
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.tasks.MachineTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.createTaskSymbol
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTStage3 : Initable {

    override fun init() {
        
    }
    
    val task1_1 = MachineTask(
        stage3,
        "SieveStoneBricks",
        "石砖筛子",
        3 orderWith 3,
        null,
        createTaskSymbol(Material.STONE_BRICKS, "§d要想从沙砾中筛出铁粒来","§d必须升级你的筛子","§6用4个石砖替换原来的羊毛","§6再用扳手敲击活板门，就能完成升级！","§b将沙砾放在活板门的上面","§c然后不断右击活板门来筛出铁粒！"),
        arrayOf(SItem(Material.GRAVEL, 10)),
        Sieve, 1,
        arrayOf(SItem(Material.IRON_INGOT)),
        "§d恭喜你走出了铁器时代的第一步！", "§f任务需要:", "§b铁锭 x1", "§f任务奖励:", "§e沙砾 x10"
    )
    
    val task2_1 = MachineTask(
        stage3,
        "SieveIron",
        "铁制筛子",
        4 orderWith 3,
        task1_1,
        createTaskSymbol(Material.IRON_BLOCK, "§d从沙砾中筛出铁粒效率太低？","§d试试铁块升级吧！","§6用4个铁块替换原来的石砖","§6再用扳手敲击活板门，就能完成升级！","§b升级后筛沙砾就能爆7-9个铁粒不等","§c甚至还可以从沙子中筛出金粒来"),
        arrayOf(SItem(Material.GRAVEL, 10)),
        Sieve, 2,
        arrayOf(SItem(Material.IRON_BLOCK, 4)),
        "§d恭喜你有了稳定高效的铁粒来源！", "§f任务需要:", "§b铁块 x4", "§f任务奖励:", "§e沙砾 x10"
    )
    
}