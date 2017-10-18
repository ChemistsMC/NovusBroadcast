package me.ebonjaeger.novusbroadcast.commands

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class NovusCommand : ExecutableCommand
{
    override fun executeCommand(sender: CommandSender?, args: List<String>?)
    {
        sender?.sendMessage("" + ChatColor.BLUE + "» " + ChatColor.GRAY + "Use the command " + ChatColor.WHITE + "/nb help"
                + ChatColor.GRAY + " for help.")
        sender?.sendMessage("" + ChatColor.BLUE + "» " + ChatColor.GRAY + "Use the command " + ChatColor.WHITE + "/nb version"
                + ChatColor.GRAY + " for version info.")
    }
}
