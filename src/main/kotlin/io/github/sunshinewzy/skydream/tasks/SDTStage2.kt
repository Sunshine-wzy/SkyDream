package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.manual.ClayMaker
import io.github.sunshinewzy.skydream.objects.machine.manual.Crucible
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
        "SourceOfLife",
        "生命之源",
        3 orderWith 3,
        null,
        SItem(waterPotion.clone(), "", "§a>点我查看任务<", "§e制作压榨机，从树苗中压榨出水！","§6按照提示搭建多方块机器","§6并用扳手右键敲击顶部的圆石墙","§6来激活多方块机器", "§b确保背包内有8棵及以上的树苗","§c然后不断右击压榨机顶部的圆石墙来榨出水源！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        Squeezer, 0,
        arrayOf(waterPotion.clone()),
        "§d恭喜你获得了生命之源！","§f任务需要:","§b水瓶 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task1_2 = ItemCraftTask(
        stage2,
        "AgricultureStart",
        "农业起步",
        3 orderWith 2,
        task1_1,
        SItem.createTaskSymbol(Material.WOODEN_HOE, "§e我知道你肯定饿了很久","§c光啃苹果肯定不行","§b是时候发展农业来获得稳定的食物来源了","§d那么，先做一把木锄头吧~"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        SItem(Material.WOODEN_HOE),
        "§f任务需要:","§b木锄 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task2_1 = MachineTask(
        stage2,
        "BuildMillstone",
        "建造磨盘",
        4 orderWith 3,
        task1_1,
        SItem.createTaskSymbol(Material.SMOOTH_STONE_SLAB, "§d磨盘能把圆石磨成沙砾","§6按照提示搭建多方块机器","§6并用扳手右键敲击顶部的圆石墙","§6来激活多方块机器","§2将圆石放在磨盘的石台阶上","§c然后不断右击磨盘顶部的圆石墙来将圆石磨成沙砾！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        Millstone, 0,
        arrayOf(SItem(Material.GRAVEL)),
        "§d恭喜你能生产沙砾了！","§7悄悄告诉你:","§7在磨盘的石台阶下面放一个§c漏斗","§7磨盘的研磨产物会自动进入漏斗","§f任务需要:","§b沙砾 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task2_2 = ItemTask(
        stage2,
        "BurningGlass",
        "烧制玻璃",
        4 orderWith 4,
        task2_1,
        SItem.createTaskSymbol(Material.GLASS, "§e磨盘不止能磨圆石","§c还能把沙砾磨成沙子","§b将沙砾放在磨盘的石台阶上","§d然后不断右击磨盘顶部的圆石墙","§d来将沙砾磨成沙子","§a最后再烧成玻璃吧！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        arrayOf(SItem(Material.GLASS)),
        "§f任务需要:","§b玻璃 x1","§f任务奖励:","§e圆石 x10"
    )
    
    val task3_1 = MachineTask(
        stage2,
        "ClayMaker",
        "粘土制造机",
        5 orderWith 3,
        task2_1,
        SItem.createTaskSymbol(Material.CLAY, "§d粘土是制作陶瓷的重要原料","§d你需要学会如何将沙子制造为粘土","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的圆石墙)","§6来激活多方块机器","§b将沙子放在粘土制造机的圆石墙上","§c然后不断右击底部的圆石墙来制造粘土！"),
        arrayOf(SItem(Material.COBBLESTONE, 10)),
        ClayMaker, 0,
        arrayOf(SItem(Material.CLAY_BALL, 4)),
        "§d恭喜你制造出了粘土！","§f任务需要:","§b黏土球 x4","§f任务奖励:","§e圆石 x10"
    )
    
    val task4_1 = ItemCraftTask(
        stage2,
        "CrucibleTongs",
        "坩埚钳",
        6 orderWith 3,
        task3_1,
        SItem.createTaskSymbol(Material.BONE, "§6坩埚的标配！"),
        arrayOf(SItem(Material.CARROT, 10)),
        SDItem.CRUCIBLE_TONGS.item,
        "§f任务需要:","§b坩埚钳 x1","§f任务奖励:","§e胡萝卜 x10"
    )
    
    val task4_2 = MachineTask(
        stage2,
        "Crucible",
        "坩埚",
        6 orderWith 4,
        task4_1,
        SItem.createTaskSymbol(Material.WHITE_TERRACOTTA, "§d是时候搞点岩浆玩玩了（雾）","§d你可以通过坩埚把圆石变成岩浆","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的圆石墙)","§6来激活多方块机器","§b在四个熔炉里烧点东西","§b有几个熔炉在工作坩埚就是几倍速","§c主手持坩埚钳副手拿圆石","§c然后不断右击坩埚中间的圆石墙来制造岩浆！"),
        arrayOf(SItem(Material.BEETROOT_SOUP)),
        Crucible, 0,
        emptyArray(),
        "§d恭喜你距离刷石机就差一步了！","§f任务奖励:","§e甜菜汤 x1"
    )
    
    val task5_1 = ItemTask(
        stage2,
        "AhStone",
        "啊刷石机",
        7 orderWith 3,
        task4_2,
        SItem.createTaskSymbol(Material.COBBLESTONE, "§e你已经有能力制造水和岩浆了","§c我知道你还没有桶","§c但我相信以你的能力","§c没有桶也能做出刷石机的！","§d做出一个刷石机","§a并刷出一组圆石吧！"),
        arrayOf(SItem(Material.IRON_PICKAXE)),
        arrayOf(SItem(Material.COBBLESTONE, 64)),
        "§f任务需要:","§b圆石 x64","§f任务奖励:","§e铁镐 x1","§e<解锁第三阶段>"
    )
    
}