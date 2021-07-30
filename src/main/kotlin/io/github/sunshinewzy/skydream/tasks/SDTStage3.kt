package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
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
        arrayOf(SItem(Material.GRAVEL, 10), SItem(Material.SAND, 10)),
        Sieve, 2,
        arrayOf(SItem(Material.GOLD_INGOT)),
        "§d恭喜你有了稳定高效的铁粒来源！", "§f任务需要:", "§b金锭 x1", "§f任务奖励:", "§e沙砾 x10", "§e沙子 x10"
    )
    
    val task3_1 = MachineTask(
        stage3,
        "SieveGold", "金制筛子",
        5 orderWith 3, task2_1,
        createTaskSymbol(Material.GOLD_BLOCK, "§d从沙砾中筛金粒效率太低？","§d试试金块升级吧！","§6用4个金块替换原来的铁块","§6再用扳手敲击活板门，就能完成升级！", "§b升级后筛沙子就能爆4-6个金粒不等","§c甚至还可以从砂土中筛出煤炭","§c从粘土中筛出红石来！"),
        arrayOf(SItem(Material.SAND, 10)),
        Sieve, 3,
        arrayOf(SItem(Material.COAL), SItem(Material.REDSTONE)),
        "§d恭喜你能获得煤炭和红石了！", "§f任务需要:", "§b煤炭 x1", "§b红石 x1", "§f任务奖励:", "§e沙子 x10"
    )
    
    val task4_1 = MachineTask(
        stage3,
        "SieveRedstone", "红石筛子",
        6 orderWith 3, task3_1,
        createTaskSymbol(Material.REDSTONE_BLOCK, "§d是时候为进入蒸汽时代做准备了","§6用4个红石块替换原来的金块","§6再用扳手敲击活板门，就能完成升级！","§b升级后筛沙砾有20%的几率掉锡粉","§b筛沙子有20%的几率掉铜粉","§b筛砂土有50%的几率掉煤","§b5%的几率掉钻石","§c铜和锡是制作青铜的基础材料！"),
        arrayOf(SItem(Material.CLAY, 10)),
        Sieve, 4,
        arrayOf(SDItem.COPPER_POWDER.item, SDItem.TIN_POWDER.item),
        "§d恭喜你能获得铜、锡和钻石了！", "§f任务需要:", "§b铜粉 x1", "§b锡粉 x1", "§f任务奖励:", "§e粘土块 x10"
    )
    
    val task5_1 = MachineTask(
        stage3,
        "SieveDiamond", "钻石筛子",
        7 orderWith 3, task4_1,
        createTaskSymbol(Material.DIAMOND_BLOCK, "§d壕，效率至上！", "§6用4个钻石块替换原来的红石块","§6再用扳手敲击活板门，就能完成升级！","§b升级后筛矿速度大大提升","§b爆率略微增加"),
        arrayOf(SItem(Material.GRAVEL, 32), SItem(Material.SAND, 32), SItem(Material.COARSE_DIRT, 32), SItem(Material.CLAY, 32), SItem(Material.DIRT, 32)),
        Sieve, 5,
        arrayOf(SItem(Material.IRON_NUGGET, 64), SItem(Material.GOLD_NUGGET, 64)),
        "§d顶级筛子！", "§f任务需要:", "§b铁粒 x64", "§b金粒 x64", "§f任务奖励:", "§e沙砾 x32", "§e沙子 x32", "§e砂土 x32", "§e粘土块 x32", "§e泥土 x32"
    )
    
//    val task3_1 = MachineTask(
//        stage3,
//        "ForgingTable", "锻造台",
//        5 orderWith 3, task2_1,
//        createTaskSymbol(Material.CRAFTING_TABLE, "§d普通工作台无法锻造精密的零件","§d做一个3x3x3的锻造台吧！","§b锻造台构建完成后","§c点击顶层的工作台就能打开锻造界面","§e将合成材料按照合成表摆在左边","§e用锤子敲击右边的格子即可锻造~"),
//        arrayOf(SItem(Material.SUGAR_CANE, 3)),
//        ForgingTable, 0,
//        emptyArray(),
//        "§d恭喜你能锻造更加精密的零件了！", "§f任务奖励:", "§e甘蔗 x3"
//    )
//    
//    val task3_2 = ItemCraftTask(
//        stage3,
//        "CraftIronHammer", "制作铁锤",
//        5 orderWith 4, task3_1,
//        createTaskSymbol(Material.IRON_AXE, "§6铁锤是使用锻造台进行锻造的必需品","§e用铁锤敲击锻造台的","§e锤子格就能进行锻造！"),
//        arrayOf(SItem(Material.GRAVEL, 10), SItem(Material.SAND, 10)),
//        SDItem.HAMMER_IRON.item,
//        "§d砰!砰!砰!","§f任务需要:","§b铁锤 x1","§f任务奖励:","§e沙子 x10"
//    )
    
}