package me.ebonjaeger.novusbroadcast

import org.bukkit.Bukkit
import java.util.*

/**
 * Holds a list of messages.
 *
 * This class stores a list of messages, and that list's related
 * parameters. Upon construction, a task is scheduled at the given
 * interval times 20 (for the amount of ticks) to broadcast a message.
 *
 * @param plugin The [NovusBroadcast] plugin instance.
 * @param name The name of this list.
 * @param interval The interval between messages in seconds.
 * @param randomize Whether the messages should be broadcast at random or in order.
 * @param prefix The prefix to be sent with each message.
 * @param suffix The suffix to be sent with each message.
 * @param messages The list of messages to broadcast.
 */
data class MessageList(private val plugin: NovusBroadcast,
                       val name: String,
                       val enabled: Boolean,
                       val interval: Long,
                       val randomize: Boolean,
                       val prefix: String,
                       val suffix: String,
                       val messages: List<String>)
{

    private var currentIndex = 0
    private val ticks = interval * 20

    init
    {
        if (enabled) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
                if (randomize) {
                    val random = Random()
                    val index = random.nextInt(messages.size)

                    sendMessage(index)
                } else {
                    sendMessage(currentIndex)
                    currentIndex++

                    if (currentIndex == messages.size) {
                        currentIndex = 0
                    }
                }
            }, ticks, ticks)
        }
    }

    /**
     * Sends a message to all online players.
     *
     * @param index The index in the list of the message to send.
     */
    fun sendMessage(index: Int)
    {
        if (plugin.config.getBoolean(ConfigStrings.SEND_TO_CONSOLE, false))
        {
            Bukkit.getConsoleSender().sendMessage(prefix)
            Bukkit.getConsoleSender().sendMessage(messages[index])
            Bukkit.getConsoleSender().sendMessage(suffix)
        }

        for (player in Bukkit.getServer().onlinePlayers)
        {
            player.sendMessage(prefix)
            player.sendMessage(messages[index])
            player.sendMessage(suffix)
        }
    }
}
