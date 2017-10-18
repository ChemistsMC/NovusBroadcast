package me.ebonjaeger.novusbroadcast

import me.ebonjaeger.novusbroadcast.commands.ExecutableCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class NovusBroadcast : JavaPlugin()
{

    private val commands:Map<String, ExecutableCommand>

    override fun onEnable()
    {

    }

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean
    {
        if (command?.name.equals("nb", true))
        {
            if (args?.size == 0)
            {

            }
        }
    }

    fun registerCommands()
    {
        //
    }
}
