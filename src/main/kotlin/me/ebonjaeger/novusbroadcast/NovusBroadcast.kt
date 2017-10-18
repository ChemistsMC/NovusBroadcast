package me.ebonjaeger.novusbroadcast

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import me.ebonjaeger.novusbroadcast.commands.ExecutableCommand
import me.ebonjaeger.novusbroadcast.commands.NovusCommand
import me.ebonjaeger.novusbroadcast.permissions.PermissionManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileReader

class NovusBroadcast : JavaPlugin()
{

    private val commands = HashMap<String, ExecutableCommand>()
    private val messageLists = HashSet<MessageList>()
    private val permissionManager = PermissionManager(Bukkit.getPluginManager())

    override fun onEnable()
    {
        ConsoleLogger.setLogger(logger)

        val file = File(dataFolder, "messages.json")
        if (!file.exists())
        {
            saveResource("messages.json", false)
        }

        registerCommands()
        loadMessageLists(file)
    }

    override fun onDisable()
    {
        server.scheduler.cancelTasks(this)
    }

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean
    {
        if (command?.name.equals("nb", true))
        {
            if (args?.size == 0)
            {
                commands["nb"]?.executeCommand(sender, emptyList())
                return true
            }

            val mappedCommand = commands[args?.get(0)?.decapitalize()]
            if (mappedCommand != null)
            {
                if (!permissionManager.hasPermission(sender, mappedCommand.getRequiredPermission()))
                {
                    sender?.sendMessage(ChatColor.DARK_RED.toString() + "» " + ChatColor.GRAY + "You do not have permission to do that.")
                    return true
                }

                // Add all args excluding the first one
                val argsList = mutableListOf(args).removeAt(0)?.toList()

                // Execute the command
                mappedCommand.executeCommand(sender, argsList)
                return true
            }
            else
            {
                commands["nb"]?.executeCommand(sender, emptyList())
                return true
            }
        }

        return false
    }

    private fun registerCommands()
    {
        commands.put("nb", NovusCommand())
    }

    fun loadMessageLists(file: File)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this, {
            JsonReader(FileReader(file)).use {
                val parser = JsonParser()
                val data = parser.parse(it).asJsonObject
                val root = data["root"].asJsonObject

                for (list in root.entrySet())
                {
                    val name = list.key
                    val jsonObject = root[name].asJsonObject
                    val interval = jsonObject["interval"].asLong
                    val randomize = jsonObject["randomize"].asBoolean
                    val array = jsonObject["messages"].asJsonArray
                    val messages = mutableListOf<String>()
                    for (message in array)
                    {
                        messages.add(message.asString)
                    }

                    messageLists.add(MessageList(this, name, interval, randomize, messages))
                }
            }
        })
    }
}
