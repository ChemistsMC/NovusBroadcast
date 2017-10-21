package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class HelpCommand : ExecutableCommand
{

    override fun executeCommand(sender: CommandSender?, args: List<String>)
    {
        val sb = StringBuilder()
        sb.append("\n" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------------------\n")
        sb.append("" + ChatColor.GRAY + "                [ " + ChatColor.BLUE + "NovusBroadcast Commands" + ChatColor.GRAY + " ]\n\n")
        sb.append("" + ChatColor.BLUE + "» " + ChatColor.WHITE + "/nb info <message list>" + ChatColor.BLUE + " - " + ChatColor.GRAY + "Shows information about a message list\n")
        sb.append("" + ChatColor.BLUE + "» " + ChatColor.WHITE + "/nb list <message list> [page]" + ChatColor.BLUE + " - " + ChatColor.GRAY + "Lists messages in a list\n")
        sb.append("" + ChatColor.BLUE + "» " + ChatColor.WHITE + "/nb send <message list> <index>" + ChatColor.BLUE + " - " + ChatColor.GRAY + "Immediately send the message at the given index to all players\n")
        sb.append("" + ChatColor.BLUE + "» " + ChatColor.WHITE + "/nb reload" + ChatColor.BLUE + " - " + ChatColor.GRAY + "Reloads the plugin config and message lists\n")
        sb.append("" + ChatColor.BLUE + "» " + ChatColor.WHITE + "/nb version" + ChatColor.BLUE + " - " + ChatColor.GRAY + "Shows the plugin version\n")
        sb.append("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "-----------------------------------------------------\n")

        sender?.sendMessage(sb.toString())
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.HELP
    }
}