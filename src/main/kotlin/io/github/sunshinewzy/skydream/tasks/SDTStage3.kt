package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.machine.manual.OreSeparator
import io.github.sunshinewzy.skydream.tasks.SDTask.stage3
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.tasks.MachineUpgradeTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.SItem.Companion.createTaskSymbol
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTStage3 : Initable {

    override fun init() {
        
    }
    
    val task1_1 = MachineUpgradeTask(
        stage3,
        "矿石分离机-石砖升级",
        3 orderWith 3,
        null,
        createTaskSymbol(Material.SMOOTH_BRICK, 0, "§d要想从沙砾中分离出铁粒来","§d必须升级你的矿石分离机","§6将4个石砖放置在原有的羊毛上","§6就能完成升级！","§b将沙砾放在机器底部栅栏的下面","§c然后不断右击机器底部的栅栏来分离出铁粒！"),
        arrayOf(SItem(Material.GRAVEL, 10)),
        OreSeparator, 1,
        arrayOf(SItem(Material.IRON_INGOT)),
        "§d恭喜你走出了铁器时代的第一步！", "§f任务需要:", "§b铁锭 x1", "§f任务奖励:", "§e沙砾 x10"
    )
    
    val task2_1 = MachineUpgradeTask(
        stage3,
        "矿石分离机-铁块升级",
        4 orderWith 3,
        task1_1,
        createTaskSymbol(Material.IRON_BLOCK, 0, "§d从沙砾中分离铁粒效率太低？","§d试试铁块升级吧！","§6将4个铁块和1个橡木栅栏放置在石砖升级上","§6就能完成升级！","§b升级后分离沙砾就能爆7-9个铁粒不等","§c甚至还可以从沙子中分离出金粒来"),
        arrayOf(SItem(Material.GRAVEL, 10)),
        OreSeparator, 2,
        arrayOf(SItem(Material.IRON_BLOCK, 4)),
        "§d恭喜你有了稳定高效的铁粒来源！", "§f任务需要:", "§b铁块 x4", "§f任务奖励:", "§e沙砾 x10"
    )
    
}