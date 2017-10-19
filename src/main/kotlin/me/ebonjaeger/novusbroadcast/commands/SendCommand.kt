package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class SendCommand(private val plugin: NovusBroadcast) : ExecutableCommand
{

    override fun executeCommand(sender: CommandSender?, args: List<String>?)
    {
        if (args?.size != 3)
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

        val index = args[2].toIntOrNull()
        if (index == null)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Invalid index argument '${args[2]}'!")
            return
        }

        try
        {
            val message = messageList.messages[index]
            messageList.sendMessage(index)
        }
        catch (ex: IndexOutOfBoundsException)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "No message at index '$index'!")
        }
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.SEND
    }
}
