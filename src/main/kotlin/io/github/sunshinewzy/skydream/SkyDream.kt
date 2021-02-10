package io.github.sunshinewzy.skydream

import io.github.sunshinewzy.skydream.bstats.Metrics
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.skydream.objects.machine.manual.OreSeparator
import io.github.sunshinewzy.skydream.tasks.SDTask
import io.github.sunshinewzy.sunstcore.modules.machine.MachineWrench
import io.github.sunshinewzy.sunstcore.objects.SItem
import io.github.sunshinewzy.sunstcore.utils.giveItem
import io.github.sunshinewzy.sunstcore.utils.subscribeEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class SkyDream : JavaPlugin() {
    
    companion object {
        private var plugin: JavaPlugin? = null
        val logger: Logger by lazy { 
            getPlugin().logger
        }
        val pluginManager: PluginManager = Bukkit.getServer().pluginManager
        val wrench = MachineWrench(SItem(Material.BONE, "§b扳手", "§7一个普通的扳手", "§a敲击中心方块以构建多方块机器"))
        
        var version: String? = null
        var nms: String? = null
        var obc: String? = null
        
        val resourceNames = arrayOf(
            "MultiBlockMachine/Size3Machine.yml", "MultiBlockMachine/Size5SteamMachine.yml",
            "TaskProgress/Stage1.yml", "TaskProgress/Stage2.yml", "TaskProgress/Stage3.yml", "TaskProgress/Stage4.yml",
            "Others/PlayerHasOpenedSDGuide.yml", "Others/SDBlockPosition.yml"
        )
        
        
        fun getPlugin(): JavaPlugin = plugin!!
    }
    
    override fun onEnable() {
        plugin = this
        
        reflect()
        
        val metrics = Metrics(this, 10213)
        
        logger.info("插件bug反馈/交流Q群：423179929")
        logger.info("作者: Sunshine_wzy")
        
        saveDefaultConfig()
        for (resourceName in resourceNames) {
            if (getResource(resourceName) == null) {
                saveResource(resourceName, false)
            }
        }
        
        //载入配置文件
        loadSDConfig()

        //注册监听器
        registerListeners()
        
        //初始化对象
        init()
        
        subscribeEvent<PlayerJoinEvent> {
//            player.giveItem(SItem(Material.GOLD_SPADE, "§e矿石分离机生成器").addAction {
//                if(clickedBlock != null && action == Action.RIGHT_CLICK_BLOCK && hand == EquipmentSlot.HAND)
//                    OreSeparator().buildMachine(clickedBlock.location)
//            })
//            player.giveItem(SDItem.WRENCH)
        }
        
    }

    override fun onDisable() {
        
    }


    private fun reflect() {
        version = Bukkit.getServer().javaClass.getPackage().name.split("\\.".toRegex()).toTypedArray()[3]
        nms = "net.minecraft.server.$version."
        obc = "org.bukkit.craftbukkit.$version."
    }
    
    private fun registerListeners() {
        
    }
    
    private fun loadSDConfig() {
//        File(dataFolder, "MultiBlockMachine/Size3Machine.yml").loadYamlConfig()
    }
    
    private fun init() {
        SDTask.init()
        SDItem.init()
        SDMachine.init()
    }
}