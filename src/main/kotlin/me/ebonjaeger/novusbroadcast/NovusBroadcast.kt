package me.ebonjaeger.novusbroadcast

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import me.ebonjaeger.novusbroadcast.commands.*
import me.ebonjaeger.novusbroadcast.listeners.PluginListener
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

    val messageLists = HashMap<String, MessageList>()

    private val commands = HashMap<String, ExecutableCommand>()
    private val messagesFile = File(dataFolder, "messages.json")
    private val permissionManager = PermissionManager(Bukkit.getPluginManager())

    override fun onEnable()
    {
        ConsoleLogger.setLogger(logger)

        val config = config
        config.options().header("Configuration file for NovusBroadcast. \n"
                + "Configure the messages to broadcast in the 'messages.json' file.")

        config.addDefault(ConfigStrings.DEBUG_MODE, false)
        config.addDefault(ConfigStrings.SEND_TO_CONSOLE, false)

        config.options().copyDefaults(true)
        saveConfig()

        ConsoleLogger.setUseDebug(config.getBoolean(ConfigStrings.DEBUG_MODE, false))

        if (!messagesFile.exists())
        {
            saveResource("messages.json", false)
        }

        permissionManager.checkForPlugins()
        registerCommands()
        registerListeners()
        loadMessageLists(messagesFile)

        ConsoleLogger.debug("NovusBroadcast is enabled and debug-mode is active!")
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

                // Execute the command
                mappedCommand.executeCommand(sender, args?.asList() ?: emptyList())
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
        commands["nb"] = NovusCommand()
        commands["help"] = HelpCommand()
        commands["info"] = InfoCommand(this)
        commands["list"] = ListCommand(this)
        commands["reload"] = ReloadCommand(this)
        commands["send"] = SendCommand(this)
        commands["version"] = VersionCommand(this)
    }

    private fun registerListeners()
    {
        server.pluginManager.registerEvents(PluginListener(permissionManager), this)
    }

    fun reload()
    {
        reloadConfig()

        Bukkit.getScheduler().cancelTasks(this)
        messageLists.clear()

        ConsoleLogger.setUseDebug(config.getBoolean(ConfigStrings.DEBUG_MODE, false))
        loadMessageLists(messagesFile)
    }

    private fun loadMessageLists(file: File)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this) {
            JsonReader(FileReader(file)).use {
                val parser = JsonParser()
                val data = parser.parse(it).asJsonObject
                val root = data["root"].asJsonObject

                for (list in root.entrySet())
                {
                    val name = list.key
                    val jsonObject = root[name].asJsonObject
                    val enabled = jsonObject["enabled"].asBoolean
                    val interval = jsonObject["interval"].asLong
                    val randomize = jsonObject["randomize"].asBoolean
                    val array = jsonObject["messages"].asJsonArray
                    val prefix = ChatColor.translateAlternateColorCodes('&', jsonObject["prefix"].asString)
                    val suffix = ChatColor.translateAlternateColorCodes('&', jsonObject["suffix"].asString)
                    val messages = array.map { e -> ChatColor.translateAlternateColorCodes('&', e.asString) }

                    messageLists[name] = MessageList(this, name, enabled, interval, randomize, prefix, suffix, messages)
                }
            }
        }
    }
}
