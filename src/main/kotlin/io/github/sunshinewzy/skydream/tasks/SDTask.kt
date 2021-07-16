package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.TaskProject
import io.github.sunshinewzy.sunstcore.modules.task.TaskStage
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTask : Initable {
    
    override fun init() {
        SDTStage1.init()
        SDTStage2.init()
        SDTStage3.init()
        
        setFinalTasks()
    }
    
    val openItem = SItem(Material.ENCHANTED_BOOK, "§eSkyDream §a向导", "§b>§f由此开始SkyDream之旅§b<")
    val taskProject = TaskProject("SkyDream", "天之梦", openItem, true, "§eSkyDream §a向导")
    
    val stage1 = TaskStage(
        taskProject,
        "Stage1",
        "<第一阶段> §6木器时代",
        3 orderWith 3,
        null,
        SItem(Material.OAK_LOG, "§f<第一阶段>", "§6木器时代","§a在这一阶段你将学会最基本的生存法则","§a并向石器时代迈进"),
        SItem(Material.BROWN_STAINED_GLASS_PANE)
    )
    
    val stage2 = TaskStage(
        taskProject,
        "Stage2",
        "<第二阶段> §7石器时代",
        4 orderWith 3,
        stage1,
        SItem(Material.COBBLESTONE, "§f<第二阶段>", "§7石器时代","§a在这一阶段你将拥有刷石机","§a并获得一些基础材料"),
        SItem(Material.GRAY_STAINED_GLASS_PANE)
    )
    
    val stage3 = TaskStage(
        taskProject,
        "Stage3",
        "<第三阶段> §d铁器时代",
        5 orderWith 3,
        stage2,
        SItem(Material.IRON_INGOT, "§f<第三阶段>","§d铁器时代","§a在这一阶段你将获得简单的矿物","§a并为蒸汽时代做准备"),
        SItem(Material.PINK_STAINED_GLASS_PANE)
    )
    
    
    private fun setFinalTasks() {
        stage1.finalTask = SDTStage1.task5_1
        stage2.finalTask = SDTStage2.task5_1
        
    }
    
}