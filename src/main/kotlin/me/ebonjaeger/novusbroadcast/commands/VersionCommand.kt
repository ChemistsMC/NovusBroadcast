package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class VersionCommand(private val plugin: NovusBroadcast) : ExecutableCommand
{

    override fun executeCommand(sender: CommandSender?, args: List<String>?)
    {
        val version = plugin.description.version
        val authors = plugin.description.authors.toString()
                .replace("[", "")
                .replace("]", "")

        sender?.sendMessage("" + ChatColor.BLUE + "» " + ChatColor.GRAY + "Version: " + ChatColor.BLUE + version)
        sender?.sendMessage("" + ChatColor.BLUE + "» " + ChatColor.GRAY + "Author: " + ChatColor.BLUE + authors)
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.VERSION
    }
}
