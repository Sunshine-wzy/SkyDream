package io.github.sunshinewzy.skydream.commands

import io.github.sunshinewzy.skydream.SkyDream.Companion.colorName
import io.github.sunshinewzy.skydream.tasks.SDTask
import io.github.sunshinewzy.sunstcore.commands.SCommand
import io.github.sunshinewzy.sunstcore.interfaces.Registrable
import io.github.sunshinewzy.sunstcore.utils.SExtensionKt
import org.bukkit.Sound

object SDCommand : Registrable {
    override fun register() {
        val command = SCommand("SkyDream", "sd")
            .addCommand("guide", "获取SkyDream向导书") { wrapper ->
                wrapper.apply {
                    empty {
                        val player = getPlayer() ?: return@empty
                        SExtensionKt.giveItem(player, SDTask.taskProject.openItem)

                        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.3f)
                        SExtensionKt.sendMsg(player, colorName, "&a您获得了SkyDream向导书,请打开背包查看！")
                        SExtensionKt.sendMsg(player, colorName, "&c若向导书丢失可输入/sd guide获得")
                    }
                }
            }
            
            .addCommand("open", "打开SkyDream向导书") { wrapper ->
                wrapper.apply {
                    empty {
                        val player = getPlayer() ?: return@empty
                        SDTask.taskProject.openTaskInv(player, SDTask.taskProject.getTaskInv(player))
                    }
                }
            }
        
        command.addCommand("unlockall", "任务全解", "&e/${command.alias} unlockall  &a>> ", true) { wrapper ->
            wrapper.apply {
                empty {
                    val player = getPlayer() ?: return@empty
                    SDTask.taskProject.completeAllTask(player)
                    SExtensionKt.sendMsg(player, colorName, "&a任务全解成功！")
                }
            }
        }
        
    }
}