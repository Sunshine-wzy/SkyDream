package io.github.sunshinewzy.skydream.commands

import io.github.sunshinewzy.skydream.SkyDream.Companion.colorName
import io.github.sunshinewzy.skydream.tasks.SDTask
import io.github.sunshinewzy.sunstcore.commands.SCommand
import io.github.sunshinewzy.sunstcore.interfaces.Registrable
import io.github.sunshinewzy.sunstcore.utils.giveItem
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Sound

object SDCommand : Registrable {
    override fun register() {
        SCommand("SkyDream", "sd")
            .addCommand("guide", "获取SkyDream向导书") {
                empty { 
                    val player = getPlayer() ?: return@empty
                    player.giveItem(SDTask.taskProject.openItem)

                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.3f)
                    player.sendMsg(colorName, "&a您获得了SkyDream向导书,请打开背包查看！")
                    player.sendMsg(colorName, "&c若向导书丢失可输入/sd guide获得")
                }
            }
            
            .addCommand("open", "打开SkyDream向导书") {
                empty {
                    val player = getPlayer() ?: return@empty
                    SDTask.taskProject.openTaskInv(player)
                }
            }
                
            .addCommand("unlockall", "任务全解", isOp = true) {
                empty { 
                    val player = getPlayer() ?: return@empty
                    SDTask.taskProject.completeAllTask(player)
                    player.sendMsg(colorName, "&a任务全解成功！")
                }
            }
        
    }
}