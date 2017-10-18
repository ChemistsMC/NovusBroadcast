package me.ebonjaeger.novusbroadcast

import org.bukkit.Bukkit
import java.util.*

/**
 * Holds a list of messages and schedules a task to broadcast
 * messages at the configured interval for the list. The interval is
 * in seconds!
 */
data class MessageList(private val plugin: NovusBroadcast,
                       private val name: String,
                       private val interval: Long,
                       private val randomize: Boolean,
                       private val header: String,
                       private val footer: String,
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

    fun sendMessage(index: Int)
    {
        for (player in Bukkit.getServer().onlinePlayers)
        {
            val sb = StringBuilder()
            sb.append(header)
            sb.append(messages[index])
            sb.append(footer)

            player.sendMessage(sb.toString())
        }
    }
}
