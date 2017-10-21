package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.MessageList
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ListCommand(private val plugin: NovusBroadcast) : ExecutableCommand
{

    private val PAGE_SIZE = 5

    override fun executeCommand(sender: CommandSender?, args: List<String>)
    {
        if (args.size < 2 || args.size > 3)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Invalid arguments! Usage is: "
                    + ChatColor.WHITE + "/nb list <messageList> [page]")
            return
        }

        val messageList = plugin.messageLists[args[1]]
        if (messageList == null)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "No list with name '${args[1]}' found!")
            return
        }

        val pages = (messageList.messages.size + PAGE_SIZE - 1) / PAGE_SIZE

        val page: Int
        if (args.size == 3)
        {
            page = args[2].toIntOrNull()
            if (page == null)
            {
                sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Invalid page '${args[2]}'!")
                return
            }
        }
    }

    private fun buildPage(messageList: MessageList, page: Int, totalPages: Int)
    {

    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.LIST
    }
}