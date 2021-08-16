package io.github.sunshinewzy.skydream.tasks

import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.manual.HeavyMillstone
import io.github.sunshinewzy.skydream.objects.machine.manual.Sieve
import io.github.sunshinewzy.skydream.objects.machine.manual.StoneBarrel
import io.github.sunshinewzy.skydream.tasks.SDTask.stage3
import io.github.sunshinewzy.sunstcore.interfaces.Initable
import io.github.sunshinewzy.sunstcore.modules.task.tasks.ItemTask
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
        createTaskSymbol(Material.STONE_BRICKS, "§d要想从沙砾中筛出铁粒来","§d必须升级你的筛子","§6用4个石砖§e替换§6原来的羊毛","§6再用扳手敲击活板门，就能完成升级","§e中间一定不能碰活板门！","§7(否则只能重新构建羊毛筛子)","§b将沙砾放在活板门的上面","§c然后不断右击活板门来筛出铁粒！"),
        arrayOf(SItem(Material.GRAVEL, 10)),
        Sieve, 1,
        arrayOf(SItem(Material.IRON_INGOT)),
        "§d恭喜你走出了铁器时代的第一步！", "§f任务需要:", "§b铁锭 x1", "§f任务奖励:", "§e沙砾 x10"
    )
    
    val task1_2 = ItemTask(
        stage3,
        "SeedCollector", "种子收藏家",
        3 orderWith 2, task1_1,
        createTaskSymbol(Material.MELON_SEEDS, "§d用石砖筛子筛泥土来集齐四种种子！", "§b并将它们扩大化培养种植"),
        arrayOf(SItem(Material.DIRT, 32), SItem(Material.BONE_MEAL, 64), SItem(Material.DIAMOND_HOE)),
        arrayOf(SItem(Material.WHEAT_SEEDS, 16), SItem(Material.BEETROOT_SEEDS, 16), SItem(Material.MELON_SEEDS, 16), SItem(Material.PUMPKIN_SEEDS, 16)),
        "§d农 业 复 兴","§f任务需要:","§b下列4种种子 x16","§f任务奖励:","§e泥土 x32","§e骨粉 x64","§e钻石锄 x1"
    )
    
    val task1_3 = ItemTask(
        stage3,
        "SaplingCollector-I", "树苗收藏家 I",
        3 orderWith 4, task1_1,
        createTaskSymbol(Material.OAK_SAPLING, "§d用石砖筛子筛泥土来集齐三种树苗！", "§b并将它们扩大化培养种植"),
        arrayOf(SItem(Material.DIRT, 32), SItem(Material.BONE_MEAL, 64), SItem(Material.DIAMOND_AXE)),
        arrayOf(SItem(Material.OAK_SAPLING, 16), SItem(Material.BIRCH_SAPLING, 16), SItem(Material.SPRUCE_SAPLING, 16)),
        "§d林 业 起 步","§f任务需要:","§b下列3种树苗 x16","§f任务奖励:","§e泥土 x32","§e骨粉 x64","§e钻石斧 x1"
    )

    val task1_4 = ItemTask(
        stage3,
        "SaplingCollector-II", "树苗收藏家 II",
        2 orderWith 4, task1_3,
        createTaskSymbol(Material.JUNGLE_SAPLING, "§d用石砖筛子筛泥土来集齐另外三种树苗！", "§b并将它们扩大化培养种植"),
        arrayOf(SItem(Material.CAKE), SItem(Material.GOLDEN_APPLE, 16), SItem(Material.APPLE, 32), SItem(Material.COOKIE, 64)),
        arrayOf(SItem(Material.JUNGLE_SAPLING, 16), SItem(Material.ACACIA_SAPLING, 16), SItem(Material.DARK_OAK_SAPLING, 16)),
        "§d林 业 复 兴","§f任务需要:","§b下列3种树苗 x16","§f任务奖励:","§e蛋糕 x1","§e金苹果 x16","§e苹果 x32","§e曲奇 x64"
    )
    
    
    val task2_1 = MachineTask(
        stage3,
        "SieveIron",
        "铁制筛子",
        4 orderWith 3,
        task1_1,
        createTaskSymbol(Material.IRON_BLOCK, "§d从沙砾中筛出铁粒效率太低？","§d试试铁块升级吧！","§6用4个铁块§e替换§6原来的石砖","§6再用扳手敲击活板门，就能完成升级","§e中间一定不能碰活板门！","§7(否则只能重新构建羊毛筛子)","§b升级后筛沙砾就能爆7-9个铁粒不等","§c甚至还可以从沙子中筛出金粒来"),
        arrayOf(SItem(Material.GRAVEL, 10), SItem(Material.SAND, 10)),
        Sieve, 2,
        arrayOf(SItem(Material.GOLD_INGOT)),
        "§d恭喜你有了稳定高效的铁粒来源！", "§f任务需要:", "§b金锭 x1", "§f任务奖励:", "§e沙砾 x10", "§e沙子 x10"
    )
    
    val task2_2 = ItemTask(
        stage3,
        "CactusCollector", "仙人掌收藏家",
        4 orderWith 2, task2_1,
        createTaskSymbol(Material.CACTUS, "§d用铁制筛子筛沙子来获得仙人掌！"),
        arrayOf(SItem(Material.SAND, 32), SItem(Material.DIAMOND_SHOVEL)),
        arrayOf(SItem(Material.CACTUS, 64)),
        "§d仙人掌！","§f任务需要:","§b仙人掌 x64","§f任务奖励:","§e沙子 x32","§e钻石铲 x1"
    )
    
    val task2_3 = MachineTask(
        stage3,
        "HeavyMillstone", "重型磨盘",
        4 orderWith 4, task2_1,
        createTaskSymbol(Material.STONE_BRICK_WALL, "§d磨盘效率太低？","§d快试试重型磨盘","§e5x5x5大型机器","§a一次研磨9个方块","§a包您满意！"),
        arrayOf(SItem(Material.GRAVEL, 64), SItem(Material.NETHERITE_PICKAXE)),
        HeavyMillstone, 0,
        emptyArray(),
        "§d你的第一台大型机器","§7悄悄告诉你:","§7在重型磨盘顶部的石砖墙上放一个§c小箱子","§7在箱子里放入要研磨的方块","§7重型磨盘就能自动从中抽取方块！","§f任务奖励:","§e沙砾 x64","§e下界合金镐 x1"
    )
    
    val task3_1 = MachineTask(
        stage3,
        "SieveGold", "金制筛子",
        5 orderWith 3, task2_1,
        createTaskSymbol(Material.GOLD_BLOCK, "§d从沙砾中筛金粒效率太低？","§d试试金块升级吧！","§6用4个金块§e替换§6原来的铁块","§6再用扳手敲击活板门，就能完成升级","§e中间一定不能碰活板门！","§7(否则只能重新构建羊毛筛子)","§b升级后筛沙子就能爆4-6个金粒不等","§c甚至还可以从砂土中筛出煤炭","§c从粘土中筛出红石来！"),
        arrayOf(SItem(Material.SAND, 10)),
        Sieve, 3,
        arrayOf(SItem(Material.COAL), SItem(Material.REDSTONE)),
        "§d恭喜你能获得煤炭和红石了！", "§f任务需要:", "§b煤炭 x1", "§b红石 x1", "§f任务奖励:", "§e沙子 x10"
    )
    
    val task3_2 = ItemTask(
        stage3,
        "PlantCollector-I", "植物收藏家 I",
        5 orderWith 2, task3_1,
        createTaskSymbol(Material.SUGAR_CANE, "§d用金制筛子筛砂土来获得", "§e甘蔗 竹子 可可豆"),
        arrayOf(SItem(Material.COARSE_DIRT, 32), SItem(Material.DIAMOND_PICKAXE)),
        arrayOf(SItem(Material.SUGAR_CANE, 16), SItem(Material.BAMBOO, 16), SItem(Material.COCOA_BEANS, 16)),
        "§d种 植 业 起 步","§f任务需要:","§b以下三种植物 x16","§f任务奖励:","§e砂土 x32","§e钻石镐 x1"
    )
    
    val task3_3 = ItemTask(
        stage3,
        "PlantCollector-II", "植物收藏家 II",
        5 orderWith 4, task3_1,
        createTaskSymbol(Material.VINE, "§d用金制筛子筛砂土来获得", "§e藤蔓 甜浆果 睡莲"),
        arrayOf(SItem(Material.COARSE_DIRT, 32), SItem(Material.SHULKER_BOX)),
        arrayOf(SItem(Material.VINE, 16), SItem(Material.SWEET_BERRIES, 16), SItem(Material.LILY_PAD, 4)),
        "§d种 植 业 复 兴","§f任务需要:","§b藤蔓 x16","§b甜浆果 x16","§b睡莲 x4","§f任务奖励:","§e砂土 x32","§e潜影盒 x1"
    )
    
    val task3_4 = ItemTask(
        stage3,
        "HeartOfTheSea", "海洋之心",
        6 orderWith 4, task3_3,
        createTaskSymbol(Material.HEART_OF_THE_SEA, "§b将§e装满鹦鹉螺壳的§c投掷器","§b放在水流灌注机的圆石墙上","§d然后不断右击底部的圆石墙来制造海洋之心！"),
        arrayOf(SItem(Material.TURTLE_EGG)),
        arrayOf(SItem(Material.HEART_OF_THE_SEA)),
        "§d钓 鱼 大 师","§f任务需要:","§b海洋之心 x1","§f任务奖励:","§e海龟蛋 x1"
    )
    
    val task4_1 = MachineTask(
        stage3,
        "SieveRedstone", "红石筛子",
        6 orderWith 3, task3_1,
        createTaskSymbol(Material.REDSTONE_BLOCK, "§d是时候为进入蒸汽时代做准备了","§6用4个红石块§e替换§6原来的金块","§6再用扳手敲击活板门，就能完成升级","§e中间一定不能碰活板门！","§7(否则只能重新构建羊毛筛子)","§b升级后筛沙砾有20%的几率掉锡粉","§b筛沙子有20%的几率掉铜粉","§b筛砂土有50%的几率掉煤","§b5%的几率掉钻石","§c铜和锡是制作青铜的基础材料！"),
        arrayOf(SItem(Material.CLAY, 10)),
        Sieve, 4,
        arrayOf(SDItem.COPPER_POWDER.item, SDItem.TIN_POWDER.item),
        "§d恭喜你能获得铜、锡和钻石了！", "§f任务需要:", "§b铜粉 x1", "§b锡粉 x1", "§f任务奖励:", "§e粘土块 x10"
    )
    
    val task5_1 = MachineTask(
        stage3,
        "SieveDiamond", "钻石筛子",
        7 orderWith 3, task4_1,
        createTaskSymbol(Material.DIAMOND_BLOCK, "§d壕，效率至上！", "§6用4个钻石块§e替换§6原来的红石块","§6再用扳手敲击活板门，就能完成升级","§e中间一定不能碰活板门！","§7(否则只能重新构建羊毛筛子)","§b升级后筛矿速度大大提升","§b爆率略微增加","§e筛沙砾有10%几率掉青金石","§e筛沙子有1%几率掉绿宝石","§e筛粘土有25%几率掉荧石粉","§e筛灵魂沙有1%几率掉恶魂之泪","§e有5%几率掉下界合金碎片","§e有25%几率掉下界石英"),
        arrayOf(SItem(Material.GRAVEL, 32), SItem(Material.SAND, 32), SItem(Material.COARSE_DIRT, 32), SItem(Material.CLAY, 32), SItem(Material.DIRT, 32)),
        Sieve, 5,
        arrayOf(SItem(Material.IRON_NUGGET, 64), SItem(Material.GOLD_NUGGET, 64)),
        "§d顶级筛子！", "§f任务需要:", "§b铁粒 x64", "§b金粒 x64", "§f任务奖励:", "§e沙砾 x32", "§e沙子 x32", "§e砂土 x32", "§e粘土块 x32", "§e泥土 x32"
    )
    
    val task6_1 = MachineTask(
        stage3,
        "StoneBarrel", "石桶",
        8 orderWith 3, task5_1,
        createTaskSymbol(Material.NETHERRACK, "§d木桶无法盛放岩浆", "§d建造一个石桶", "§e将岩浆放在石桶中间的圆石墙上", "§e副手拿红石不断右键", "§e石桶中间的圆石墙来制造地狱岩！"),
        arrayOf(SItem(Material.REDSTONE, 16)),
        StoneBarrel, 0,
        arrayOf(SItem(Material.NETHERRACK)),
        "§d地 狱 岩 工 业", "§f任务需要:", "§b下界岩(地狱岩) x1", "§f任务奖励:", "§e红石 x16"
    )
    
    val task6_2 = ItemTask(
        stage3,
        "EndStone", "制造末地石",
        8 orderWith 2, task6_1,
        createTaskSymbol(Material.END_STONE, "§d将岩浆放在石桶中间的圆石墙上", "§e副手拿荧石粉不断右键", "§e石桶中间的圆石墙来制造末地石！"),
        arrayOf(SItem(Material.GLOWSTONE_DUST, 16)),
        arrayOf(SItem(Material.END_STONE, 4)),
        "§d末 地 石 工 业", "§f任务需要:", "§b末地石 x1", "§f任务奖励:", "§e荧石粉 x16"
    )

    val task6_3 = ItemTask(
        stage3,
        "MakeSoulSand", "制作灵魂沙",
        7 orderWith 2, task6_1,
        createTaskSymbol(Material.SOUL_SAND, "§b将下界岩放在水流灌注机的圆石墙上","§d然后不断右击底部的圆石墙来制造灵魂沙！"),
        arrayOf(SItem(Material.NETHERRACK, 32), SItem(Material.NETHERITE_SHOVEL)),
        arrayOf(SItem(Material.SOUL_SAND, 16)),
        "§d灵 魂 沙 工 业", "§f任务需要:", "§b灵魂沙 x16", "§f任务奖励:", "§e下界岩 x32", "§e下界合金锹 x1"
    )
    
    val task6_4 = ItemTask(
        stage3,
        "NetherPlantCollector", "下界植物收藏家",
        8 orderWith 4, task6_3,
        createTaskSymbol(Material.NETHER_WART, "§d用钻石筛子筛灵魂沙来获得", "§e下界疣(地狱疣) 绯红菌 诡异菌"),
        arrayOf(SItem(Material.NETHERRACK, 16), SItem(Material.DIAMOND_SWORD)),
        arrayOf(SItem(Material.NETHER_WART, 16), SItem(Material.CRIMSON_FUNGUS), SItem(Material.WARPED_FUNGUS)),
        "§d下界种植业复兴", "§f任务需要:", "§b下界疣 x16", "§b绯红菌 x1", "§b诡异菌 x1", "§f任务奖励:", "§e下界岩 x16", "§e钻石剑 x1"
    )
    
    val task6_5 = ItemTask(
        stage3,
        "MakeNylium", "制作菌岩",
        7 orderWith 4, task6_4,
        createTaskSymbol(Material.CRIMSON_NYLIUM, "§d将岩浆放在石桶中间的圆石墙上", "§e副手拿绯红/诡异菌不断右键", "§e中间的圆石墙来制造绯红/诡异菌岩！"),
        arrayOf(SItem(Material.NETHERRACK, 16), SItem(Material.BONE_MEAL, 16)),
        arrayOf(SItem(Material.CRIMSON_NYLIUM, 16), SItem(Material.WARPED_NYLIUM, 16)),
        "§d菌 岩 工 业", "§f任务需要:", "§b绯红菌岩 x16", "§b诡异菌岩 x16", "§f任务奖励:", "§e下界岩 x16", "§e骨粉 x16"
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