package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.machine.manual.ClayMaker
import io.github.sunshinewzy.skydream.objects.machine.manual.Millstone
import io.github.sunshinewzy.skydream.objects.machine.manual.Squeezer
import io.github.sunshinewzy.skydream.tasks.SDTask.stage2
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemCraftTask
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemTask
import io.github.sunshinewzy.sunstcore.modules.task.tasks.MachineTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

object SDTStage2 : Initable {
    
    override fun init() {
        
    }

    private val waterPotion = SItem(Material.POTION).apply {
        val meta = Bukkit.getItemFactory().getItemMeta(Material.POTION) as PotionMeta
        meta.basePotionData = PotionData(PotionType.WATER)
        itemMeta = meta
    }
    
    
    val task1_1 = MachineTask(
        stage2,
        "生命之源",
        3 orderWith 3,
        null,
        SItem(waterPotion.clone(), "", "§a>点我查看任务<", "§e制作压榨机，从树苗中压榨出水！","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的圆石墙)","§6来激活多方块机器", "§b确保背包内有8棵及以上的树苗","§c然后不断右击压榨机顶部的圆石墙来榨出水源！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        Squeezer,
        arrayOf(waterPotion.clone()),
        "§d恭喜你获得了生命之源！","§f任务需要:","§b水瓶 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task1_2 = ItemCraftTask(
        stage2,
        "农业起步",
        3 orderWith 2,
        task1_1,
        SItem.createTaskSymbol(Material.WOOD_HOE, 0, "§e我知道你肯定饿了很久","§c光啃苹果肯定不行","§b是时候发展农业来获得稳定的食物来源了","§d那么，先做一把木锄头吧~"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        SItem(Material.WOOD_HOE),
        "§f任务需要:","§b木锄 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task2_1 = MachineTask(
        stage2,
        "建造磨盘",
        4 orderWith 3,
        task1_1,
        SItem.createTaskSymbol(Material.STEP, 0, "§d磨盘能把圆石磨成沙砾","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的圆石墙)","§6来激活多方块机器","§2将圆石放在磨盘的石台阶上","§c然后不断右击磨盘顶部的圆石墙来将圆石磨成沙砾！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        Millstone,
        arrayOf(SItem(Material.GRAVEL)),
        "§d恭喜你能生产沙砾了！","§f任务需要:","§b沙砾 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task2_2 = ItemTask(
        stage2,
        "烧制玻璃",
        4 orderWith 4,
        task2_1,
        SItem.createTaskSymbol(Material.GLASS, 0, "§e磨盘不止能磨圆石","§c还能把沙砾磨成沙子","§b将沙砾放在磨盘的石台阶上","§d然后不断右击磨盘顶部的圆石墙","§d来将沙砾磨成沙子","§a最后再烧成玻璃吧！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        arrayOf(SItem(Material.GLASS)),
        "§f任务需要:","§b玻璃 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task3_1 = MachineTask(
        stage2,
        "粘土制造机",
        5 orderWith 3,
        task2_1,
        SItem.createTaskSymbol(Material.CLAY, 0, "§d粘土是制作陶瓷的重要原料","§d你需要学会如何将沙子制造为粘土","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的圆石墙)","§6来激活多方块机器","§b将沙子放在粘土制造机的圆石墙上","§c然后不断右击底部的圆石墙来制造粘土！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        ClayMaker,
        arrayOf(SItem(Material.CLAY_BALL, 4)),
        "§d恭喜你制造出了粘土！","§f任务需要:","§b粘土 x4","§f任务奖励:","§e圆石 x10"
    )
    
}