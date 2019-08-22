package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class InfoCommand(private val plugin: NovusBroadcast) : ExecutableCommand
{

    override fun executeCommand(sender: CommandSender?, args: List<String>)
    {
        if (args.size != 2)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Invalid arguments! Usage is: "
                    + ChatColor.WHITE + "/nb info <messageList>")
            return
        }

        val messageList = plugin.messageLists[args[1]]
        if (messageList == null)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "No list with name '${args[1]}' found!")
            return
        }

        var hasPrefix = false
        if (messageList.prefix.isEmpty())
        {
            hasPrefix = true
        }

        var hasSuffix = false
        if (messageList.suffix.isEmpty())
        {
            hasSuffix = true
        }

        val sb = StringBuilder()
        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}\n ---------------------------------------------------- \n")
        sb.append("${ChatColor.GRAY}Name: ${ChatColor.WHITE}${messageList.name}\n")
        sb.append("${ChatColor.GRAY}Enabled: ${ChatColor.WHITE}${messageList.enabled}\n")
        sb.append("${ChatColor.GRAY}Interval: ${ChatColor.WHITE}${messageList.interval} seconds\n")
        sb.append("${ChatColor.GRAY}Randomized: ${ChatColor.WHITE}${messageList.randomize}\n")
        sb.append("${ChatColor.GRAY}Has prefix: ${ChatColor.WHITE}$hasPrefix\n")
        sb.append("${ChatColor.GRAY}Has suffix: ${ChatColor.WHITE}$hasSuffix\n")
        sb.append("${ChatColor.GRAY}Message count: ${ChatColor.WHITE}${messageList.messages.size}")
        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}\n ---------------------------------------------------- \n")

        sender?.sendMessage(sb.toString())
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.INFO
    }
}
