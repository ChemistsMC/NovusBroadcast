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
                       private val name: String,
                       private val interval: Long,
                       private val randomize: Boolean,
                       private val prefix: String,
                       private val suffix: String,
                       private val messages: List<String>)
{

    private var currentIndex = 0
    private val ticks = interval * 20

    init
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            if (randomize)
            {
                val random = Random()
                val index = random.nextInt(messages.size + 1)

                sendMessage(index)
            }
            else
            {
                sendMessage(currentIndex)
                currentIndex++

                if (currentIndex > messages.size)
                {
                    currentIndex = 0
                }
            }
        }, ticks, ticks)
    }

    /**
     * Sends a message to all online players.
     *
     * This function builds the final message from the prefix,
     * suffix and the message itself. It then sends that final message
     * to the players.
     *
     * @param index The index in the list of the message to send.
     */
    fun sendMessage(index: Int)
    {
        val sb = StringBuilder()
        sb.append(prefix)
        sb.append(messages[index])
        sb.append(suffix)
        val final = sb.toString()

        for (player in Bukkit.getServer().onlinePlayers)
        {
            player.sendMessage(final)
        }
    }
}
