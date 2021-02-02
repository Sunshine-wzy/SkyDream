package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.ItemTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTStage1 : Initable {
    override fun init() {
        
    }
    
    val task1_1 = ItemTask(
        SDTask.stage1,
        "制作钩子",
        3 orderWith 3,
        null,
        SItem(Material.STICK, "§f[制作钩子]", "§a>点我查看合成表及提交任务<","§6获得木头后的第一件事"),
        arrayOf(SItem(Material.INK_SACK, 15, 16)),
        arrayOf(SDItem.HOOK_STICK.item),
        "§f任务需要:","§b钩子 x1","§f任务奖励:","§e骨粉 x16"
    )
}