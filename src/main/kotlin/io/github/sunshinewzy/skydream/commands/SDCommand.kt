package io.github.sunshinewzy.skydream.commands

import io.github.sunshinewzy.skydream.SkyDream.Companion.colorName
import io.github.sunshinewzy.skydream.tasks.SDTask
import io.github.sunshinewzy.sunstcore.commands.SCommand
import io.github.sunshinewzy.sunstcore.commands.SCommand.Companion.sendSeparator
import io.github.sunshinewzy.sunstcore.interfaces.Registrable
import io.github.sunshinewzy.sunstcore.utils.giveItem
import io.github.sunshinewzy.sunstcore.utils.sendMsg
import org.bukkit.Sound

object SDCommand : Registrable {
    override fun register() {
        SCommand("SkyDream")
            .addCommand("guide") {
                empty { 
                    val player = getPlayer() ?: return@empty
                    player.giveItem(SDTask.taskProject.openItem)

                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.3f)
                    player.sendMsg(colorName, "&a您获得了SkyDream向导书,请打开背包查看！")
                    player.sendMsg(colorName, "&c若向导书丢失可输入/sd guide获得")
                }
            }
            
            .addCommand("open") {
                empty {
                    val player = getPlayer() ?: return@empty
                    SDTask.taskProject.openTaskInv(player)
                }
            }
                
            .addCommand("unlockall", true) {
                empty { 
                    val player = getPlayer() ?: return@empty
                    SDTask.taskProject.completeAllTask(player)
                    player.sendMsg(colorName, "&a任务全解成功！")
                }
            }
        
            .setHelper { sender, page ->
                sender.apply {
                    sendSeparator()
                    sendMsg("&6&lSkyDream &b命令指南 &d第 $page 页")

                    when(page) {
                        1 -> {
                            sendMsg("&e/sd guide &a获取SkyDream向导书")
                            sendMsg("&e/sd open &a打开SkyDream向导书")

                            if(isOp) {
                                sendMsg("&e/sd unlockall &a任务全解&c（需要op权限）")
                            }
                        }

                        else -> sendMsg("&cSkyDream 命令指南没有此页！")
                    }

                    sendSeparator()
                }
            }
    }
}