package io.github.sunshinewzy.skydream

import io.github.sunshinewzy.sunstcore.utils.SConfig.loadYamlConfig
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class SkyDream : JavaPlugin() {
    
    companion object {
        var pluginSkyDream: JavaPlugin? = null
        var loggerSkyDream: Logger? = null
        var pluginManager: PluginManager? = null
        
        var version: String? = null
        var nms: String? = null
        var obc: String? = null
        
        val resourceNames = arrayOf(
            "MultiBlockMachine/Size3Machine.yml", "MultiBlockMachine/Size5SteamMachine.yml",
            "TaskProgress/Stage1.yml", "TaskProgress/Stage2.yml", "TaskProgress/Stage3.yml", "TaskProgress/Stage4.yml",
            "Others/PlayerHasOpenedSDGuide.yml", "Others/SDBlockPosition.yml"
        )
    }
    
    override fun onEnable() {
        pluginSkyDream = this
        loggerSkyDream = logger
        pluginManager = Bukkit.getServer().pluginManager
        
        reflect()
        
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
        
    }

    override fun onDisable() {
        
    }


    private fun reflect() {
        version = Bukkit.getServer().javaClass.getPackage().name.split("/.".toRegex()).toTypedArray()[3]
        nms = "net.minecraft.server.$version."
        obc = "org.bukkit.craftbukkit.$version."
    }
    
    private fun registerListeners() {
        
    }
    
    private fun loadSDConfig() {
//        File(dataFolder, "MultiBlockMachine/Size3Machine.yml").loadYamlConfig()
    }
    
}


fun getPlugin(): JavaPlugin {
    return SkyDream.pluginSkyDream!!
}

fun getLogger(): Logger {
    return SkyDream.loggerSkyDream!!
}

fun getPluginManager(): PluginManager {
    return SkyDream.pluginManager!!
}