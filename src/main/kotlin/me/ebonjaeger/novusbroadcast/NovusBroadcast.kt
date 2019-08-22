package me.ebonjaeger.novusbroadcast

import co.aikar.commands.PaperCommandManager
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import me.ebonjaeger.novusbroadcast.commands.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileReader

class NovusBroadcast : JavaPlugin()
{

    val messageLists = HashMap<String, MessageList>()

    private val messagesFile = File(dataFolder, "messages.json")

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

        registerCommands()
        loadMessageLists(messagesFile)

        ConsoleLogger.debug("NovusBroadcast is enabled and debug-mode is active!")
    }

    override fun onDisable()
    {
        server.scheduler.cancelTasks(this)
    }

    private fun registerCommands()
    {
        val commandManager = PaperCommandManager(this)
        commandManager.commandCompletions.registerAsyncCompletion("messageLists") { messageLists.keys }
        commandManager.enableUnstableAPI("help")

        commandManager.registerCommand(InfoCommand(this))
        commandManager.registerCommand(ListCommand(this))
        commandManager.registerCommand(ReloadCommand(this))
        commandManager.registerCommand(SendCommand(this))
        commandManager.registerCommand(HelpCommand(this))
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
