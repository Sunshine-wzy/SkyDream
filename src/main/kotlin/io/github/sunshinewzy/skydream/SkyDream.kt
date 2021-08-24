package io.github.sunshinewzy.skydream

import io.github.sunshinewzy.skydream.commands.SDCommand
import io.github.sunshinewzy.skydream.listeners.SDSubscriber
import io.github.sunshinewzy.skydream.objects.item.SDItem
import io.github.sunshinewzy.skydream.objects.machine.SDMachine
import io.github.sunshinewzy.skydream.tasks.SDTask
import io.github.sunshinewzy.sunstcore.libs.bstats.bukkit.Metrics
import io.github.sunshinewzy.sunstcore.utils.SunSTTestApi
import io.github.sunshinewzy.sunstcore.utils.subscribeEvent
import org.bukkit.Bukkit
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class SkyDream : JavaPlugin() {
    
    companion object {
        lateinit var plugin: JavaPlugin
        val logger: Logger by lazy { 
            plugin.logger
        }
        val pluginManager: PluginManager = Bukkit.getServer().pluginManager
        
        const val name = "SkyDream"
        const val colorName = "§eSkyDream"
        
        lateinit var version: String
        lateinit var nms: String
        lateinit var obc: String
        
    }
    
    override fun onEnable() {
        plugin = this
        
        reflect()
        val metrics = Metrics(this, 10213)
        
        logger.info("插件bug反馈/交流Q群：423179929")
        logger.info("作者: Sunshine_wzy")
        
        saveDefaultConfig()
        
        //注册监听器
        registerListeners()
        
        //初始化对象
        init()

        if(System.getProperty("SunSTDebug") == "true")
            test()
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
    
    private fun init() {
        SDTask.init()
        SDItem.init()
        SDMachine.init()
        SDSubscriber.init()
        
        SDCommand.register()
    }
    
    @SunSTTestApi
    private fun test() {
        subscribeEvent<PlayerInteractEvent> {
            if(hand == EquipmentSlot.HAND && action == Action.RIGHT_CLICK_BLOCK) {
                
            }
        }
    }
}