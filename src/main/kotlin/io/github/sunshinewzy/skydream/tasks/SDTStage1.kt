package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.manual.OreSeparator
import io.github.sunshinewzy.skydream.objects.machine.manual.WoodenBarrel
import io.github.sunshinewzy.skydream.tasks.SDTask.stage1
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemCraftTask
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemTask
import io.github.sunshinewzy.sunstcore.modules.task.tasks.MachineTask
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.objects.orderWith
import org.bukkit.Material

object SDTStage1 : Initable {
    override fun init() {
        
    }
    
    val task1_1 = ItemCraftTask(
        stage1,
        "制作钩子",
        3 orderWith 3,
        null,
        SItem.createTaskSymbol(Material.STICK, 0, "§6获得木头后的第一件事"),
        arrayOf(SItem(Material.INK_SACK, 15, 16)),
        SDItem.HOOK_STICK.item,
        "§f任务需要:","§b钩子 x1","§f任务奖励:","§e骨粉 x16"
    )
    
    val task1_2 = ItemCraftTask(
        stage1,
        "制作木钩",
        3 orderWith 4,
        task1_1,
        SItem.createTaskSymbol(Material.WOOD_SWORD, 0, "§6升级你的钩子！"),
        arrayOf(SItem(Material.DIRT, 10)),
        SDItem.HOOK_WOOD.item,
        "§f任务需要:","§b木钩 x1","§f任务奖励:","§e火把 x8"
    )
    
    val task1_3 = ItemCraftTask(
        stage1,
        "制作石钩",
        4 orderWith 4,
        task1_2,
        SItem.createTaskSymbol(Material.STONE_SWORD, 0, "§6升级你的钩子！"),
        arrayOf(SItem(Material.COBBLESTONE, 8)),
        SDItem.HOOK_STONE.item,
        "§f任务需要:","§b石钩 x1","§f任务奖励:","§e圆石 x8"
    )
    
    val task2_1 = ItemTask(
        stage1,
        "养蚕缫丝",
        4 orderWith  3,
        task1_1,
        SItem.createTaskSymbol(Material.STRING, 0, "§e使用钩子破坏橡树树叶有几率掉落蚕和桑叶","§c而且能增加树苗掉落几率","§b副手拿蚕主手拿桑叶右键即可喂蚕","§d每个蚕能喂食5次并吐出数量不等的丝线"),
        arrayOf(SItem(Material.DIRT, 10)),
        arrayOf(SItem(Material.STRING, 16)),
        "§f任务需要:","§b线 x16","§f任务奖励:","§e泥土 x10"
    )
    
    val task3_1 = ItemCraftTask(
        stage1,
        "制作扳手",
        5 orderWith 3,
        task2_1,
        SItem.createTaskSymbol(Material.BONE, 0, "§d怪物掉落的资源是你的SkyDream之旅中必不可少的","§6用你手中的木头搭建一个刷怪平台并打些小白吧~", "§b用小白掉的骨头做把扳手！"),
        arrayOf(SItem(Material.BREAD, 10)),
        SDItem.WRENCH.item,
        "§f任务需要:","§b扳手 x1","§f任务奖励:","§e面包 x10"
    )
    
    val task4_1 = MachineTask(
        stage1,
        "矿石分离机",
        6 orderWith 3,
        task3_1,
        SItem.createTaskSymbol(Material.FENCE, 0, "§d多方块机器是SkyDream的重要组成部分","§6按照提示搭建多方块机器","§6并用扳手右键敲击中心方块","§6(最中间的栅栏)","§6来激活多方块机器", "§b将泥土放在底部栅栏的下面","§b然后右键底部的栅栏来分离泥土！","§c这样你就会得到石子","§c4个石子能合成1个圆石！"),
        arrayOf(SItem(Material.STONE_AXE)),
        OreSeparator,
        arrayOf(SItem(Material.COBBLESTONE)),
        "§d恭喜你迈出了石器时代的第一步！","§f任务需要:","§b圆石 x1","§f任务奖励:","§e石斧 x1"
    )
    
    val task5_1 = MachineTask(
        stage1,
        "生产泥土",
        7 orderWith 3,
        task4_1,
        SItem.createTaskSymbol(Material.DIRT, 0, "§d木桶是生产泥土的重要方式之一","§6按照提示搭建多方块机器","§a(注意橡木台阶是上半砖！)","§6并用扳手右键敲击中心方块","§6(最中间的栅栏)","§6来激活多方块机器","§b将8棵及以上的树苗放在副手","§c然后不断右击木桶中间的栅栏来制造泥土！"),
        arrayOf(SItem(Material.COBBLESTONE, 8), SItem(Material.GLASS_BOTTLE)),
        WoodenBarrel,
        arrayOf(SItem(Material.DIRT, 8)),
        "§d恭喜你能自己造土了！","§f任务需要:","§b泥土 x8","§f任务奖励:","§e圆石 x8","§e玻璃瓶 x1","§e<解锁第二阶段>"
    )
    
    val task5_2 = ItemTask(
        stage1,
        "生产骨粉",
        7 orderWith 4,
        task5_1,
        SItem.createTaskSymbol(Material.INK_SACK, 15, "§e木桶不止能制造泥土","§c还能将8片桑叶变成1个骨粉！","§b将8片及以上的桑叶放在副手","§d然后不断右击木桶中间的栅栏来制造骨粉！"),
        arrayOf(SItem(Material.POTATO_ITEM, 10)),
        arrayOf(SItem(Material.INK_SACK, 15, 8)),
        "§f任务需要:","§b骨粉 x8","§f任务奖励:","§e马铃薯 x10"
    )
    
    val task5_3 = ItemTask(
        stage1,
        "啊草方块",
        7 orderWith 2,
        task5_2,
        SItem.createTaskSymbol(Material.GRASS, 0, "§e草，一种植物","§c没有草方块怎么能活下去呢","§b将1块泥土放在副手","§d主手拿着16片桑叶右击16次就能搓出草方块啦！"),
        arrayOf(SItem(Material.DIRT, 10)),
        arrayOf(SItem(Material.GRASS)),
        "§f任务需要:","§b草方块 x1","§f任务奖励:","§e泥土 x10"
    )
    
    val task5_4 = ItemTask(
        stage1,
        "小麦种子",
        6 orderWith 2,
        task5_3,
        SItem.createTaskSymbol(Material.SEEDS, 0, "§e有了草方块和源源不断的骨粉","§c你就可以刷草了（雾）","§b将你的草地扩大一点","§d然后用骨粉刷草并打草搞点小麦种子吧"),
        arrayOf(SItem(Material.DIRT, 10)),
        arrayOf(SItem(Material.SEEDS)),
        "§f任务需要:","§b小麦种子 x1","§f任务奖励:","§e泥土 x10"
    )
    
}