package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.TaskBase.Companion.createTaskSymbol
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemCraftTask
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTStage1 : Initable {
    override fun init() {
        
    }
    
    val task1_1 = ItemCraftTask(
        SDTask.stage1,
        "制作钩子",
        3 orderWith 3,
        null,
        SItem.createTaskSymbol(Material.STICK, "§a>点我查看合成表及任务<","§6获得木头后的第一件事"),
        arrayOf(SItem(Material.INK_SACK, 15, 16)),
        SDItem.HOOK_STICK.item,
        "§f任务需要:","§b钩子 x1","§f任务奖励:","§e骨粉 x16"
    )
    
    val task1_2 = ItemCraftTask(
        SDTask.stage1,
        "制作木钩",
        3 orderWith 4,
        task1_1,
        SItem.createTaskSymbol(Material.WOOD_SWORD, "§a>点我查看合成表及提交任务<","§6升级你的钩子！"),
        arrayOf(SItem(Material.DIRT, 10)),
        SDItem.HOOK_WOOD.item,
        "§f任务需要:","§b木钩 x1","§f任务奖励:","§e火把 x8"
    )
    
    val task1_3 = ItemCraftTask(
        SDTask.stage1,
        "制作石钩",
        4 orderWith 4,
        task1_2,
        SItem.createTaskSymbol(Material.STONE_SWORD, "§a>点我查看合成表及提交任务<","§6升级你的钩子！"),
        arrayOf(SItem(Material.COBBLESTONE, 8)),
        SDItem.HOOK_STONE.item,
        "§f任务需要:","§b石钩 x1","§f任务奖励:","§e圆石 x8"
    )
    
    val task2_1 = ItemTask(
        SDTask.stage1,
        "养蚕缫丝",
        4 orderWith  3,
        task1_1,
        SItem.createTaskSymbol(Material.STRING),
        arrayOf(SItem(Material.DIRT, 10)),
        arrayOf(SItem(Material.STRING, 16)),
        "§e使用钩子破坏橡树树叶有几率掉落蚕和桑叶","§c而且能增加树苗掉落几率","§b副手拿蚕主手拿桑叶右键即可喂蚕","§d每个蚕能喂食5次并吐出数量不等的丝线",
        "§f任务需要:","§b线 x16","§f任务奖励:","§e泥土 x10"
    )
    
    val task3_1 = ItemCraftTask(
        SDTask.stage1,
        "制作扳手",
        5 orderWith 3,
        task2_1,
        SItem.createTaskSymbol(Material.BONE, "§a>点我查看合成表及提交任务<","§d怪物掉落的资源是你的SkyDream之旅中必不可少的","§6用你手中的木头搭建一个刷怪平台并打些小白吧~", "§b用小白掉的骨头做把扳手！"),
        arrayOf(SItem(Material.BREAD, 10)),
        SDItem.WRENCH.item,
        "§f任务需要:","§b扳手 x1","§f任务奖励:","§e面包 x10"
    )
    
    
}